package com.example.shalom.popularmovies.viewholder;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.shalom.popularmovies.data.model.Movie;
import com.example.shalom.popularmovies.data.model.Review;
import com.example.shalom.popularmovies.service.repository.MovieDBRepository;
import com.example.shalom.popularmovies.service.repository.MovieRoomRepository;

import java.util.List;

/*Relates to MovieDetailsFragment.java*/
public class MovieDetailsViewModel extends AndroidViewModel {
    /*Key used to persist data through the received {@code Bundle}*/
    public static final String MOVIE_KEY = "MOVIE_KEY";

    /*Movie details*/
    private String originalTitle;
    private String posterUrl;
    private String synopsis;
    private String rating;
    private String releaseDate;
    private String movieID;

    private MutableLiveData<String> trailerKeyObservable = new MutableLiveData<>();

    /*Location in the ***** */
    private int reviewsIndex = 0;
    /*List of reviews for the movie*/
    private MutableLiveData<List<Review>> reviewsListObservable = new MutableLiveData<>();
    private MutableLiveData<String> displayedReview = new MutableLiveData<>();

    //Access Room database
    private MovieRoomRepository roomRepository;

    //Currently displayed movie
    private Movie displayedMovie;
    //Tracks whether the displayed movie was favorited by the user
    boolean favorited;

    public MovieDetailsViewModel(Application application) {
        super(application);
        roomRepository = new MovieRoomRepository(application);
    }

    public void setMovieDetails(Movie thisMovie) {
        displayedMovie = thisMovie;
        originalTitle = thisMovie.getOriginalTitle();
        posterUrl = "https://image.tmdb.org/t/p/w500" + thisMovie.getPosterPath();
        synopsis = thisMovie.getOverview();
        rating = thisMovie.getVoteAverage().toString();
        releaseDate = thisMovie.getReleaseDate();
        movieID = thisMovie.getId().toString();

        //Since Genre IDs aren't stored in the Room database, if it's null we know the data's source
        favorited = thisMovie.getGenreIds() == null;
    }

    public void previousReview() {
        if (reviewsListObservable == null) {
            return;
        } else if (reviewsIndex == 0) {
            reviewsIndex = reviewsListObservable.getValue().size() - 1;
        } else {
            reviewsIndex--;
        }
        getDisplayedReview();
    }

    public void nextReview() {
        if (reviewsListObservable == null) {
            return;
        } else if (reviewsIndex == reviewsListObservable.getValue().size() - 1) {
            reviewsIndex = 0;
        } else {
            reviewsIndex++;
        }
        getDisplayedReview();
    }

    public void updateDatabase() {
        if (favorited) {
            deleteMovie();
            favorited = false;
        } else {
            insertMovie();
            favorited = true;
        }
    }

    //Inserts favourite movie into Room database
    private void insertMovie() {
        roomRepository.insert(displayedMovie.convertToMovieEntity());
    }

    //Deletes favourite movie from Room database
    private void deleteMovie() {
        roomRepository.delete(displayedMovie.convertToMovieEntity());
    }

    public MutableLiveData<String> getDisplayedReview() {
        if (reviewsListObservable.getValue() == null) {
            displayedReview.setValue("Loading...");
        } else if(reviewsListObservable.getValue().size() == 0) {
           displayedReview.setValue("No reviews found");
        } else {
            String author = reviewsListObservable.getValue().get(reviewsIndex).getAuthor();
            displayedReview.setValue("Review by " + author);
        }
        return displayedReview;
    }

    public static String getMovieKey() {
        return MOVIE_KEY;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getRating() {
        return rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public MutableLiveData<String> getTrailerKeyObservable() {
        trailerKeyObservable = MovieDBRepository.getInstance().getTrailerKey(movieID);
        return trailerKeyObservable;
    }

    public LiveData<List<Review>> getReviewsListObservable() {
        reviewsListObservable = MovieDBRepository.getInstance().getReviews(movieID);
        return reviewsListObservable;
    }

    public String getReviewURL() {
        String url = reviewsListObservable.getValue().get(reviewsIndex).getUrl();
        return url;
    }
}

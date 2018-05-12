package com.example.shalom.popularmovies.service.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.shalom.popularmovies.data.ApiKeys;
import com.example.shalom.popularmovies.service.model.Movie;
import com.example.shalom.popularmovies.service.model.Movies;
import com.example.shalom.popularmovies.service.model.Review;
import com.example.shalom.popularmovies.service.model.Reviews;
import com.example.shalom.popularmovies.service.model.Trailer;
import com.example.shalom.popularmovies.service.model.Trailers;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDBRepository {
    public static final String LOG_TAG = MovieDBRepository.class.getSimpleName();

    /*Require for static instantiation*/
    private static MovieDBRepository movieDBRepository;
    /*Instantiate the Client containing the methods that this repository can call*/
    private TheMovieDBClient theMovieDBClient;
    /*API key for TheMovieDB.org*/
    private final String apiKey = ApiKeys.theMovieDBApiKey;

    /*Enables passing of getMovieList() response to MoviePosterViewModel*/
    private MutableLiveData<List<Movie>> movieList = new MutableLiveData<>();
    private MutableLiveData<String> trailerKey = new MutableLiveData<>();
    private MutableLiveData<List<Review>> reviewList = new MutableLiveData<>();


    public MovieDBRepository() {
        theMovieDBClient = ServiceGenerator.createService(TheMovieDBClient.class);
    }

    /*Creates an instance of this repository*/
    public synchronized static MovieDBRepository getInstance() {
        if (movieDBRepository == null) {
            if (movieDBRepository == null) {
                movieDBRepository = new MovieDBRepository();
            }
        }
        return movieDBRepository;
    }

//    TODO(1) Rm return types since onResponse posts the value

    /**
     * Posts Retrofit2 response to {@code moviesObservable} when onResponse() is called
     *
     * @param path        Name of the list of movies being fetched (either "popular" or "top_rated")
     * @param currentPage The current page being fetched
     */
    public MutableLiveData<List<Movie>> getMovieList(String path, int currentPage) {
        theMovieDBClient.popularMovies(path, apiKey, currentPage).enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                if (response.isSuccessful()) {
                    movieList.postValue(response.body().getMovies());
                    Log.d(LOG_TAG, "Posts loaded from API");
                } else {
                    int statusCode = response.code();
                    //Handle request errors depending on the status code
                    Log.e(LOG_TAG, "Request error code: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Log.e(LOG_TAG, "Error loading from API");
            }
        });
        return movieList;
    }

    public MutableLiveData<String> getTrailerKey(String movieID) {
        theMovieDBClient.trailers(movieID, apiKey).enqueue(new Callback<Trailers>() {
            @Override
            public void onResponse(Call<Trailers> call, Response<Trailers> response) {
                if (response.isSuccessful()) {
                    Log.d(LOG_TAG, "videos() request sucessfull");
                    for (Trailer trailer : response.body().getTrailers()) {
                        /*Select a trailer that is hosted on Youtube*/
                        if (trailer.getSite().equals("YouTube") && trailer.getType().equals("Trailer")) {
                            trailerKey.setValue(trailer.getKey());
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Trailers> call, Throwable t) {
                Log.e(LOG_TAG, "Failure");
            }
        });
        return trailerKey;
    }

    public MutableLiveData<List<Review>> getReviews(String movieID) {
        theMovieDBClient.reviews(movieID, apiKey).enqueue(new Callback<Reviews>() {
            /*Get Reviews result*/
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                if (response.isSuccessful()) {
                    Log.d(LOG_TAG, "Review request successful");
                    reviewList.setValue(response.body().getReviews());
                }
            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {
                Log.e(LOG_TAG, "Failed");
            }
        });
        return reviewList;
    }
}

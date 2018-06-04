package com.example.shalom.popularmovies.viewholder;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.example.shalom.popularmovies.R;
import com.example.shalom.popularmovies.data.model.MovieEntity;
import com.example.shalom.popularmovies.service.repository.MovieDBRepository;
import com.example.shalom.popularmovies.data.model.Movie;
import com.example.shalom.popularmovies.service.repository.MovieRoomRepository;

import java.util.ArrayList;
import java.util.List;

/*Relates to MoviePosterFragment.java*/
public class MoviePosterViewModel extends AndroidViewModel {
    private String path;
    private int currentPage;

    /*Observable object that's updated with a {@code List} of movies*/
    private LiveData<List<Movie>> moviesObservable;
    private LiveData<List<MovieEntity>> favouriteMoviesObservable;

    /*Access room database*/
    private Application application;
    private MovieRoomRepository roomRepository;

    public MoviePosterViewModel(Application application) {
        super(application);
        path = "popular";
        this.currentPage = 1;
        this.application = application;
    }

    public LiveData<List<Movie>> getMoviesObservable() {
        getMoreMovies();
        return moviesObservable;
    }

    public LiveData<List<MovieEntity>> getFavouriteMoviesObservable() {
        roomRepository = new MovieRoomRepository(application);
        favouriteMoviesObservable = roomRepository.getFavoritedMovies();
        return favouriteMoviesObservable;
    }

    /*Calls repository to retrieve a new {@code List} of movies*/
    public void getMoreMovies() {
        moviesObservable = MovieDBRepository.getInstance().getMovieList(path, currentPage++);
    }

    /*Called when the list filter is changed*/
    public void changeFilter() {
        switch (path) {
            case "popular":
                path = "top_rated";
                break;
            case "top_rated":
                path = "favourites";
                break;
            case "favourites":
                path = "popular";
        }
        currentPage = 1;
        getMoviesObservable();
    }

    public String getPath() {
        return path;
    }

    private boolean pathIsFavourites() {
        return path == "favourites";
    }

    private void subscribeToFavouriteMovies() {
        final Observer<List<MovieEntity>> getFavouriteMoviesObserver = new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(@Nullable List<MovieEntity> movieEntities) {
                List<Movie> movies = new ArrayList<>();
                for (MovieEntity movieEntity : movieEntities) {
                    movies.add(movieEntity.convertToMovie());
                }
            }
        };
        vgetFavouriteMoviesObservable().observe(this, getFavouriteMoviesObserver);
    }
}

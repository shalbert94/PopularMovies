package com.example.shalom.popularmovies.viewholder;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.shalom.popularmovies.service.repository.MovieDBRepository;
import com.example.shalom.popularmovies.service.model.Movie;

import java.util.List;

/*Relates to MoviePosterFragment.java*/
public class MoviePosterViewModel extends ViewModel{
    private String path;
    private int currentPage;

    /*Observable object that's updated with a {@code List} of movies*/
    private MutableLiveData<List<Movie>> moviesObservable;

    public MoviePosterViewModel() {
        path = "popular";
        this.currentPage = 1;
    }

    public LiveData<List<Movie>> getMoviesObservable() {
        getMoreMovies();
        return moviesObservable;
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
                path = "popular";
                break;
        }
        currentPage = 1;
        getMoreMovies();
    }

    public String getPath() {
        return path;
    }


}

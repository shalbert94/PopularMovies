package com.example.shalom.popularmovies.viewholder;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.shalom.popularmovies.data.ApiKeys;
import com.example.shalom.popularmovies.service.repository.MovieDBRepository;
import com.example.shalom.popularmovies.service.model.Movie;
import com.example.shalom.popularmovies.service.model.Movies;
import com.example.shalom.popularmovies.service.model.Review;

import java.util.List;

public class SharedMovieViewModel extends ViewModel{
    /*Shared among MoviePosterFragment and MovieDetailsFragment*/
    private String api_key = ApiKeys.theMovieDBApiKey;

    /*Relate to MoviePosterFragment*/
    private String path;
    private int currentPage;

    /*Relate to MovieDetailsFragment*/
    private MutableLiveData<List<Movie>> moviesObservable;
    private List<Review> reviews;
    private int reviewsIndex = 0;

    public SharedMovieViewModel() {
        path = "popular";
        this.currentPage = 1;
    }

    public LiveData<List<Movie>> getMoviesObservable() {
        moviesObservable = MovieDBRepository.getInstance().getMovieList(path, currentPage++);
        return moviesObservable;
    }

    public void getMoreMovies() {
        moviesObservable = MovieDBRepository.getInstance().getMovieList(path, currentPage++);
    }

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

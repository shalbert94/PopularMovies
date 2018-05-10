package com.example.shalom.popularmovies.service.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.shalom.popularmovies.data.ApiKeys;
import com.example.shalom.popularmovies.service.model.Movie;
import com.example.shalom.popularmovies.service.model.Movies;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDBRepository {
    public static final String LOG_TAG = MovieDBRepository.class.getSimpleName();

    private static MovieDBRepository movieDBRepository;

    private TheMovieDBClient theMovieDBClient;

    private final String apiKey = ApiKeys.theMovieDBApiKey;

    private MutableLiveData<List<Movie>> movieList = new MutableLiveData<>();

    public MovieDBRepository() {
        theMovieDBClient = ServiceGenerator.createService(TheMovieDBClient.class);
    }

    public synchronized static MovieDBRepository getInstance() {
        if (movieDBRepository == null) {
            if (movieDBRepository == null) {
                movieDBRepository = new MovieDBRepository();
            }
        }
        return movieDBRepository;
    }


    /**
     * Posts Retrofit2 response to {@code moviesObservable} when onResponse() is called
     * @param path Name of the list of movies being fetched (either "popular" or "top_rated")
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



}

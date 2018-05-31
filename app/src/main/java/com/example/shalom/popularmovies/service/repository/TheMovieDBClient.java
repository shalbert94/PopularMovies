package com.example.shalom.popularmovies.service.repository;

import com.example.shalom.popularmovies.data.model.Movies;
import com.example.shalom.popularmovies.data.model.Reviews;
import com.example.shalom.popularmovies.data.model.Trailers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by shalom on 2018-03-20.
 */

public interface TheMovieDBClient {
    @GET("movie/{movie_id}/videos")
    Call<Trailers> trailers(@Path("movie_id") String movieID, @Query("api_key") String apiKey);

    @GET("movie/{order_by}")
    Call<Movies> popularMovies(@Path("order_by") String orderBy, @Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/{movie_id}/reviews")
    Call<Reviews> reviews(@Path("movie_id") String movieID, @Query("api_key") String apiKey);

}

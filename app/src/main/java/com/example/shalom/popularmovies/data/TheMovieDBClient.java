package com.example.shalom.popularmovies.data;

import com.example.shalom.popularmovies.data.model.MovieListResult;
import com.example.shalom.popularmovies.data.model.VideoResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by shalom on 2018-03-20.
 */

public interface TheMovieDBClient {
    @GET("movie/{movie_id}/videos")
    Call<VideoResults> videos(@Path("movie_id") String movieID, @Query("api_key") String apiKey);

    @GET("movie/{order_by}")
    Call<MovieListResult> popularMovies(@Path("order_by") String orderBy, @Query("api_key") String apiKey, @Query("page") int page);

}

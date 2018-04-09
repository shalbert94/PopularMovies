package com.example.shalom.popularmovies.data;

import com.example.shalom.popularmovies.data.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by shalom on 2018-03-20.
 */

public interface TheMovieDBClient {

//    @GET("movie/top_rated?api_key={api_key}")
//    Call<Result> topRatedMovies(@Path("api_key") String api_key);

    @GET("movie/{order_by}")
    Call<Result> popularMovies(@Path("order_by") String orderBy, @Query("api_key") String api_key, @Query("page") int page);

}

package com.example.shalom.popularmovies.data;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shalom on 2018-03-20.
 */

public class ServiceGenerator {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    /* Supply TheMovieDBClient.class as argument */
    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}

package com.example.shalom.popularmovies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.shalom.popularmovies.data.ServiceGenerator;
import com.example.shalom.popularmovies.data.TheMovieDBClient;
import com.example.shalom.popularmovies.data.model.Movie;
import com.example.shalom.popularmovies.data.model.Result;
import com.example.shalom.popularmovies.recyclerview.EndlessRecyclerViewScrollListener;
import com.example.shalom.popularmovies.recyclerview.MoviePosterRecyclerViewAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviePosterFragment extends Fragment {

    public static final String LOG_TAG = MoviePosterFragment.class.getSimpleName();

    private TheMovieDBClient theMovieDBClient;
    private RecyclerView recyclerView;
    private MoviePosterRecyclerViewAdapter adapter;

    /*Most recently requested page supplied to {@code TheMovieDBClient.java}*/
    private int currentPage;
    private String path;
    private boolean filterChanged;

    public MoviePosterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_poster, container, false);
        theMovieDBClient = ServiceGenerator.createService(TheMovieDBClient.class);

        recyclerView = (RecyclerView) view.findViewById(R.id.movie_poster_recyclerview);

        /*Number of columns displayed by RecyclerView*/
        int numberOfColumns = 2;

        adapter = new MoviePosterRecyclerViewAdapter(getActivity(), new ArrayList<Movie>(0));
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), numberOfColumns);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
//        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 0));
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener((GridLayoutManager) layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadMovies();
            }
        });

        currentPage = 0;
        path = "popular";
        loadMovies();

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movie_poster_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                changeFilter();
                break;
        }
        return true;
    }

    private void loadMovies() {
        final String api_key = getResources().getString(R.string.the_movie_db_api_key);
        currentPage++;
        theMovieDBClient.popularMovies(path, api_key, currentPage).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Log.e(LOG_TAG, "call.request().url().toString(): " + call.request().url().toString());
                    adapter.updateMovies(response.body().getMovies(), filterChanged);

                    if (filterChanged) {
                        /*Return filterChanged to {@code false}*/
                        filterChanged = false;
                    }
                    Log.d(LOG_TAG, "Posts loaded from API");
                } else {
                    int statusCode = response.code();
                    //Handle request errors depending on the status code
                    Log.e(LOG_TAG, "Request error code: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e(LOG_TAG, "Error loading from API");
            }
        });
    }

    private void changeFilter() {
        switch (path) {
            case "popular":
                path = "top_rated";
                getActivity().setTitle("Top Rated Movies");
                break;
            case "top_rated":
                path = "popular";
                getActivity().setTitle("Popular Movies");
                break;
        }
        currentPage = 0;
        filterChanged = true;
        loadMovies();
    }

}

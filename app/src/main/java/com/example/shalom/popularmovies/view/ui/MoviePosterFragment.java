package com.example.shalom.popularmovies.view.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.shalom.popularmovies.R;
import com.example.shalom.popularmovies.viewholder.MoviePosterViewModel;
import com.example.shalom.popularmovies.service.model.Movie;
import com.example.shalom.popularmovies.view.adapter.EndlessRecyclerViewScrollListener;
import com.example.shalom.popularmovies.view.adapter.MoviePosterRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviePosterFragment extends Fragment {

    public static final String LOG_TAG = MoviePosterFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private MoviePosterRecyclerViewAdapter adapter;

    private MoviePosterViewModel viewModel;


    public MoviePosterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_poster, container, false);

        viewModel = ViewModelProviders.of(getActivity()).get(MoviePosterViewModel.class);

        recyclerView = view.findViewById(R.id.movie_poster_recyclerview);
        /*Number of columns displayed by RecyclerView*/
        int numberOfColumns = 2;
        adapter = new MoviePosterRecyclerViewAdapter(getActivity(), new ArrayList<Movie>(0));
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), numberOfColumns);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener((GridLayoutManager) layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                viewModel.getMoreMovies();
            }
        });

        observeViewModel();

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

    private void changeFilter() {
        switch (viewModel.getPath()) {
            case "popular":
                getActivity().setTitle(getContext().getString(R.string.top_rated));
                break;
            case "top_rated":
                getActivity().setTitle(getContext().getString(R.string.popular));
                break;
        }
        viewModel.changeFilter();
        adapter.clearList();
        viewModel.getMoreMovies();
    }

    private void observeViewModel() {
        final Observer<List<Movie>> getMoviesObserver = new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                adapter.updateMovies(movies);
            }
        };

        viewModel.getMoviesObservable().observe(this, getMoviesObserver);
    }



}

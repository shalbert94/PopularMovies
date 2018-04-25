package com.example.shalom.popularmovies.recyclerview;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.shalom.popularmovies.MovieDetailsFragment;
import com.example.shalom.popularmovies.R;
import com.example.shalom.popularmovies.data.model.Movie;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shalom on 2018-03-23.
 */

public class MoviePosterRecyclerViewAdapter extends RecyclerView.Adapter {
    public static final String LOG_TAG = MoviePosterRecyclerViewAdapter.class.getSimpleName();

    private List<Movie> movieList;
    private FragmentActivity fragmentActivity;

    /*{@code MoviePosterRecyclerViewAdapter.java}'s ViewHolder*/
    public class ViewHolder extends RecyclerView.ViewHolder {

        /*The {@code RelativeLayout} enclosing the views of this {@code ViewHolder}*/
        @BindView(R.id.grid_item_movie_poster_relativelayout)
        RelativeLayout parentLayout;
        /*Holds loaded images from movie's {@code posterPath} in {@link Movie.java}*/
        @BindView(R.id.movie_poster_imageview)
        ImageView moviePosterIV;
        /*Holds movie title from {@code title} in {@link Movie.java}*/
        @BindView(R.id.movie_title_textview)
        TextView movieTitleTV;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    /*Constructor: Take a {@code List<Movie>} object which will be adapted*/
    public MoviePosterRecyclerViewAdapter(FragmentActivity fragmentActivity, List<Movie> movieList) {
        this.fragmentActivity = fragmentActivity;
        /*Initialize global variable {@code movieList}*/
        this.movieList = movieList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*Get {@code LayoutInflater} for inflating {@code ViewHolder} {@code View}*/
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        /*Inflate {@link R.layout.grid_item_movie_poster} for {@code ViewHolder}*/
        View view = inflater.inflate(R.layout.grid_item_movie_poster, parent, false);
        /*Initialize {@code ViewHolder}*/
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        /*Create immutable reference to the element at the current position of {@code movieList}*/
        final Movie movie = movieList.get(position);
        /*Cast {@code holder} to type {@code ViewHolder}*/
        ViewHolder viewHolder = (ViewHolder) holder;

//        TODO(1) Implement onClickListener with ButterKnife
        /*Set {@code OnClickListener} for tapping anywhere on the grid item*/
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = fragmentActivity.getSupportFragmentManager();
                MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();

                /*Pass the clicked movie's details to the Fragment*/
                Bundle movieDetails = new Bundle();
                movieDetails.putParcelable(MovieDetailsFragment.MOVIE_KEY, Parcels.wrap(movie));
                movieDetailsFragment.setArguments(movieDetails);

                /*Add Fragment*/
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, movieDetailsFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        /*Form URL for the movie's poster*/
        String url = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
        /*Load movie's poster into {@code viewHolder.moviePosterIV}*/
        Picasso.get().load(url).into(viewHolder.moviePosterIV);
        /*Set {@code TextView} to the movie's title*/
        viewHolder.movieTitleTV.setText(movie.getTitle());

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void updateMovies(List<Movie> movieList, boolean filterChanged) {
        if (filterChanged) {
            this.movieList.clear();
        }
        this.movieList.addAll(movieList);
        notifyDataSetChanged();
    }

}

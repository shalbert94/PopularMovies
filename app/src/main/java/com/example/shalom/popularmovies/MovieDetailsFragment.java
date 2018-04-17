package com.example.shalom.popularmovies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shalom.popularmovies.data.model.Movie;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Fragment controlling screen with movie details
 */
public class MovieDetailsFragment extends Fragment {
    /*Key used to persist data through the received {@code Bundle}*/
    public static final String MOVIE_KEY = "MOVIE_KEY";


    /*Bind child-views*/
    @BindView(R.id.original_title_textview)
    TextView originalTitleTextView;
    @BindView(R.id.details_poster_imageview)
    ImageView posterPathImageView;
    @BindView(R.id.plot_synopsis_textview)
    TextView synopsisTextView;
    @BindView(R.id.user_rating_textview)
    TextView ratingTextView;
    @BindView(R.id.release_date_textview)
    TextView releaseDateTextView;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*Inflate the layout for this fragment*/
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        ButterKnife.bind(this, view);

        /*Unparcel {@code Movie.class} from arguments*/
        Bundle bundle = this.getArguments();
        Movie thisMovie = (Movie) Parcels.unwrap(bundle.getParcelable(MOVIE_KEY));
        String originalTitle = thisMovie.getOriginalTitle();
        String posterPath = thisMovie.getPosterPath();
        String synopsis = thisMovie.getOverview();
        String rating = thisMovie.getVoteAverage().toString();
        String releaseDate = thisMovie.getReleaseDate();

        /*Define what should be displayed*/
        originalTitleTextView.setText(originalTitle);
        synopsisTextView.setText(synopsis);
        ratingTextView.setText(rating + " " + getContext().getString(R.string.rating));
        releaseDateTextView.setText(releaseDate);

        /*Form URL for the movie's poster*/
        String url = "https://image.tmdb.org/t/p/w500" + posterPath;
        Picasso.get().load(url).into(posterPathImageView);

        return view;
    }

}

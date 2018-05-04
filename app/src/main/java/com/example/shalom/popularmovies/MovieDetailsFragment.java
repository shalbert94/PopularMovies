package com.example.shalom.popularmovies;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shalom.popularmovies.data.ServiceGenerator;
import com.example.shalom.popularmovies.data.TheMovieDBClient;
import com.example.shalom.popularmovies.data.model.Movie;
import com.example.shalom.popularmovies.data.model.Review;
import com.example.shalom.popularmovies.data.model.Reviews;
import com.example.shalom.popularmovies.data.model.Trailers;
import com.example.shalom.popularmovies.data.model.Trailer;
import com.example.shalom.popularmovies.databinding.FragmentMovieDetailsBinding;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Fragment controlling screen with movie details
 */

//TODO(1) Add onClick's for review buttons
public class MovieDetailsFragment extends Fragment {
    public static final String LOG_TAG = MovieDetailsFragment.class.getSimpleName();

    /*Key used to persist data through the received {@code Bundle}*/
    public static final String MOVIE_KEY = "MOVIE_KEY";

    private String api_key;

    private Movie thisMovie;
    /*Data binder*/
    private FragmentMovieDetailsBinding binding;

    private TheMovieDBClient theMovieDBClient;

    private List<Review> reviews;
    private int reviewsIndex = 0;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false);
        /*Inflate the layout for this fragment*/
        View view = binding.getRoot();
        /*Api key for movie db*/
        api_key = getResources().getString(R.string.the_movie_db_api_key);

        theMovieDBClient = ServiceGenerator.createService(TheMovieDBClient.class);

        /*Unparcel {@code Movie.class} from arguments*/
        Bundle bundle = this.getArguments();
        thisMovie = Parcels.unwrap(bundle.getParcelable(MOVIE_KEY));

        String originalTitle = thisMovie.getOriginalTitle();
        String posterPath = thisMovie.getPosterPath();
        String synopsis = thisMovie.getOverview();
        String rating = thisMovie.getVoteAverage().toString();
        String releaseDate = thisMovie.getReleaseDate();

        /*Define what should be displayed*/
        binding.originalTitleTextview.setText(originalTitle);
        binding.plotSynopsisTextview.setText(synopsis);
        binding.userRatingTextview.setText(rating + " " + getContext().getString(R.string.rating));
        binding.releaseDateTextview.setText(releaseDate);

        /*Form URL for the movie's poster*/
        String url = "https://image.tmdb.org/t/p/w500" + posterPath;
        Picasso.get().load(url).into(binding.detailsPosterImageview);

        setOnClickListers();
        getReviews();


        return view;
    }

    /*Perform implicit Intent that opens a YouTube video*/
    private void watchYoutubeVideo(Context context, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    private void setOnClickListers() {
        binding.playTrailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(LOG_TAG, "onClick() called");

                theMovieDBClient.videos(thisMovie.getId().toString(), api_key).enqueue(new Callback<Trailers>() {
                    @Override
                    public void onResponse(Call<Trailers> call, Response<Trailers> response) {
                        if (response.isSuccessful()) {
                            Log.d(LOG_TAG, "videos() request sucessfull");
                            for (Trailer trailer : response.body().getTrailers()) {
                                /*Send intent to youtube for a YouTube trailer that's a movie trailer*/
                                if (trailer.getSite().equals("YouTube") && trailer.getType().equals("Trailer")) {
                                    watchYoutubeVideo(getContext(), trailer.getKey());
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Trailers> call, Throwable t) {

                    }
                });
            }
        });

        binding.previousReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reviewsIndex == 0) {
                    reviewsIndex = reviews.size() - 1;
                } else {
                    reviewsIndex--;
                }
                String author = reviews.get(reviewsIndex).getAuthor();
                binding.reviewAuthorTextview.setText("Review by " + author);

            }
        });

        binding.nextReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reviewsIndex == reviews.size() - 1) {
                    reviewsIndex = 0;
                } else {
                    reviewsIndex++;
                }
                String author = reviews.get(reviewsIndex).getAuthor();
                binding.reviewAuthorTextview.setText("Review by " + author);

            }
        });

        binding.reviewAuthorTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reviewUrl = reviews.get(reviewsIndex).getUrl();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(android.net.Uri.parse(reviewUrl));
                startActivity(intent);
            }
        });
    }

    private void getReviews() {
        theMovieDBClient.reviews(thisMovie.getId().toString(), api_key).enqueue(new Callback<Reviews>() {
            /*Get Reviews result*/
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                if (response.isSuccessful()) {
                    Log.d(LOG_TAG, "Review request successful");
                    reviews = response.body().getReviews();

                    String author = reviews.get(reviewsIndex).getAuthor();
                    binding.reviewAuthorTextview.setText("Review by " + author);
                }
            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {

            }
        });

    }
}



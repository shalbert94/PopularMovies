package com.example.shalom.popularmovies.view.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shalom.popularmovies.R;
import com.example.shalom.popularmovies.service.model.Movie;
import com.example.shalom.popularmovies.service.model.Review;
import com.example.shalom.popularmovies.service.model.Reviews;
import com.example.shalom.popularmovies.databinding.FragmentMovieDetailsBinding;
import com.example.shalom.popularmovies.viewholder.MovieDetailsViewModel;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Fragment controlling screen with movie details
 */

public class MovieDetailsFragment extends Fragment {
    public static final String LOG_TAG = MovieDetailsFragment.class.getSimpleName();

    /*Data binder*/
    private FragmentMovieDetailsBinding binding;

    private MovieDetailsViewModel viewModel;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false);
        /*Inflate the layout for this fragment*/
        View view = binding.getRoot();

        viewModel = ViewModelProviders.of(getActivity()).get(MovieDetailsViewModel.class);

        /*Unparcel a {@code Movie} from arguments and pass it to the ViewModel*/
        Bundle bundle = this.getArguments();
        Movie thisMovie =  Parcels.unwrap(bundle.getParcelable(MovieDetailsViewModel.getMovieKey()));
        viewModel.setMovieDetails(thisMovie);

        /*Define what should be displayed*/
        binding.originalTitleTextview.setText(viewModel.getOriginalTitle());
        binding.plotSynopsisTextview.setText(viewModel.getSynopsis());
        binding.userRatingTextview.setText(viewModel.getRating() + " " + getContext().getString(R.string.rating));
        binding.releaseDateTextview.setText(viewModel.getReleaseDate());

        /*Load movie's poster*/
        Picasso.get().load(viewModel.getPosterUrl()).into(binding.detailsPosterImageview);

        subscribeReview();
        setOnClickListers();

        return view;
    }

    private void setOnClickListers() {
        binding.playTrailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subscribeToTrailer();
            }
        });

        binding.previousReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.previousReview();

            }
        });

        binding.nextReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.nextReview();

            }
        });

        binding.reviewAuthorTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openReviewInBrowser();
            }
        });
    }

    private void subscribeReview(){
        /*Once the {@code List} of reviews is fetched, we can begin preparing to display an individual review*/
        Observer<List<Review>> reviewListObserver = new Observer<List<Review>>() {
            @Override
            public void onChanged(@Nullable List<Review> reviews) {
                viewModel.getDisplayedReview();
            }
        };
        viewModel.getReviewsListObservable().observe(this, reviewListObserver);

        /*Changes text in reviewAuthorTextview*/
        Observer<String> reviewObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.reviewAuthorTextview.setText(s);
            }
        };
        viewModel.getDisplayedReview().observe(this, reviewObserver);
    }

    private void subscribeToTrailer() {
        /*Opens trailer in response to {@code playTrailerButton} being clicked*/
        Observer<String> trailerKeyObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String trailerKey) {
                watchYoutubeVideo(getContext(), trailerKey);
            }
        };
        viewModel.getTrailerKeyObservable().observe(this, trailerKeyObserver);
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

    /*Opens the selected review through an implicit intent*/
    private void openReviewInBrowser() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(android.net.Uri.parse(viewModel.getReviewURL()));
        startActivity(intent);
    }
}



package com.example.shalom.popularmovies;


import android.arch.lifecycle.ViewModelProviders;
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
import com.example.shalom.popularmovies.data.model.Video;
import com.example.shalom.popularmovies.data.model.VideoResults;
import com.example.shalom.popularmovies.databinding.FragmentMovieDetailsBinding;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Fragment controlling screen with movie details
 */
public class MovieDetailsFragment extends Fragment {

    public static final String LOG_TAG = MovieDetailsFragment.class.getSimpleName();

    /*Key used to persist data through the received {@code Bundle}*/
    public static final String MOVIE_KEY = "MOVIE_KEY";

    private Movie thisMovie;
    /*Data binder*/
    private FragmentMovieDetailsBinding binding;

    private TheMovieDBClient theMovieDBClient;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false);
        /*Inflate the layout for this fragment*/
        View view = binding.getRoot();

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

        return view;
    }

//    TODO(1) Replace this onClick listener with a databinding OnClickListener
    @OnClick(R.id.play_trailer_button)
    public void onClick() {

        final String api_key = getResources().getString(R.string.the_movie_db_api_key);
        theMovieDBClient.videos(thisMovie.getId().toString(), api_key).enqueue(new Callback<VideoResults>() {
            @Override
            public void onResponse(Call<VideoResults> call, Response<VideoResults> response) {
                if (response.isSuccessful()) {
                    Log.d(LOG_TAG, "videos() request sucessfull");
                    for (Video video : response.body().getVideos()) {
                        /*Send intent to youtube for a YouTube video that's a movie trailer*/
                        if (video.getSite().equals("YouTube") && video.getType().equals("Trailer")) {
                            watchYoutubeVideo(getContext(), video.getKey());
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<VideoResults> call, Throwable t) {

            }
        });
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
}


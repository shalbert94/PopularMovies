package com.example.shalom.popularmovies;

import android.content.Context;

import com.example.shalom.popularmovies.viewholder.MoviePosterViewModel;

import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class MoviePosterUnitTest {

    private static final String FAKE_STRING = "Top Rated Movies";
    @Mock private Context context;

    @Test
    public void moviePosterViewModel_correctArithmetic_returnsSum() {
        MoviePosterViewModel moviePosterViewModel = new MoviePosterViewModel(context);
        assertThat(moviePosterViewModel.simpleArithmetic(2, 3), is(5));
    }

    @Test
    public void moviePosterViewModel_correctString_returnTopRatedMovies() {
        //Given a mocked context injected into the object under test
        when(context.getString(R.string.top_rated)).thenReturn(FAKE_STRING);
        MoviePosterViewModel moviePosterViewModel = new MoviePosterViewModel(context);

        //When the string is returned from the object under test
        String result = moviePosterViewModel.getString();

        //Result should be the expected one
        assertThat(result, is(FAKE_STRING));

    }
}

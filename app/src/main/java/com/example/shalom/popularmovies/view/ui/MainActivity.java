package com.example.shalom.popularmovies.view.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.shalom.popularmovies.R;

public class MainActivity extends AppCompatActivity {
    /*Log tag for this class*/
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    public static final String MOVIE_POSTER_FRAGMENT_TAG = "movies";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Get FragmentManager*/
        FragmentManager fm = getSupportFragmentManager();

        if (savedInstanceState == null) {
            /*Add Fragment*/
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.fragment_container, new MoviePosterFragment(), MOVIE_POSTER_FRAGMENT_TAG);
            ft.commit();
        }
    }
}

package com.example.shalom.popularmovies.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class MovieContract {

    //Required empty constructor
    private MovieContract() {}

    public static final String CONTENT_AUTHORITY = "com.example.shalom.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_FAVORITED_MOVIES = "favourite_movies";

    public static final String DATABASE_NAME = "favourite_movies.db";
    public static final int DATABASE_VERSION = 2;

    public static final class FavoriteMoviesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                Uri.withAppendedPath(BASE_CONTENT_URI, PATH_FAVORITED_MOVIES);

        public static final String TABLE_NAME = PATH_FAVORITED_MOVIES;

        /* Storage class: INTEGER
         * Table constraints: PRIMARY KEY, AUTOINCREMENT */
        public static final String COLUMN_ROW_ID = BaseColumns._ID;
        // Storage class: TEXT
        // Table constraints: NOT NULL
        public static final String COLUMN_ENGLISH_TITLE = "Title";
        // Storage class: TEXT
        // Table constraints: NOT NULL
        public static final String COLUMN_ORIGINAL_TITLE = "Original_Title";
        // Storage class: TEXT
        // Table constraints: NOT NULL
        public static final String COLUMN_POSTER_URL = "Poster_Url";
        // Storage class: TEXT
        // Table constraints: NOT NULL
        public static final String COLUMN_SYNOPSIS = "Synopsis";
        // Storage class: TEXT
        // Table constraints: NOT NULL
        public static final String COLUMN_RATING = "Rating";
        // Storage class: TEXT
        // Table constraints: NOT NULL
        public static final String COLUMN_RELEASE_DATE = "Release_Date";
        // Storage class: TEXT
        // Table constraints: NOT NULL
        public static final String COLUMN_MOVIE_ID = "Movie_ID";

        //MIME type for a list of favorited movies
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_FAVORITED_MOVIES;

        //MIME type for an individual favorited movie
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + CONTENT_AUTHORITY + "/" + PATH_FAVORITED_MOVIES;
    }

}

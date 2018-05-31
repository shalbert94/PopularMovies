package com.example.shalom.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shalom.popularmovies.data.MovieContract.FavoriteMoviesEntry;

public class MovieDBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "favourite_movies.db";
    public static final int DATABASE_VERSION = 1;

    //Create table
    public static final String CREATE_FAVORITED_MOVIES_TABLE = "CREATE TABLE "
            + FavoriteMoviesEntry.TABLE_NAME + " ("
            + FavoriteMoviesEntry.COLUMN_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FavoriteMoviesEntry.COLUMN_ENGLISH_TITLE + " TEXT NOT NULL, "
            + FavoriteMoviesEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, "
            + FavoriteMoviesEntry.COLUMN_POSTER_URL + " TEXT NOT NULL, "
            + FavoriteMoviesEntry.COLUMN_SYNOPSIS + " TEXT NOT NULL, "
            + FavoriteMoviesEntry.COLUMN_RATING + " TEXT NOT NULL, "
            + FavoriteMoviesEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, "
            + FavoriteMoviesEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL);";


    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_FAVORITED_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Unimplemented since there will be no upgrades
    }
}

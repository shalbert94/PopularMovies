package com.example.shalom.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MoviesProvider extends ContentProvider {
    private static final int FAVORITED_MOVIES = 100;
    private static final int FAVORITED_MOVIE_ID = 101;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private MovieDBHelper movieDBHelper;
    private SQLiteDatabase sqLiteDatabase;

    static {
        URI_MATCHER.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_FAVORITED_MOVIES,
                FAVORITED_MOVIES);
        URI_MATCHER.addURI(MovieContract.CONTENT_AUTHORITY,
                MovieContract.PATH_FAVORITED_MOVIES + "/#", FAVORITED_MOVIE_ID);
    }

    @Override
    public boolean onCreate() {
        movieDBHelper = new MovieDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor result;

        switch (URI_MATCHER.match(uri)) {
            case FAVORITED_MOVIES:
                //Get all
                result = sqLiteDatabase.query(MovieContract.FavoriteMoviesEntry.TABLE_NAME, null,
                        null, null, null, null, null);
                break;
            case FAVORITED_MOVIE_ID:
                result = sqLiteDatabase.query(MovieContract.FavoriteMoviesEntry.TABLE_NAME, null,
                        MovieContract.FavoriteMoviesEntry.COLUMN_ENGLISH_TITLE + "=?",
                        selectionArgs, null, null, null, "1");
                break;
            default:
                throw new IllegalArgumentException("Could not succesfully query the database");
        }

        return result;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        sqLiteDatabase.insert(MovieContract.FavoriteMoviesEntry.TABLE_NAME,
                null, contentValues);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int rowsDeleted = sqLiteDatabase.delete(MovieContract.FavoriteMoviesEntry.TABLE_NAME,
                MovieContract.FavoriteMoviesEntry.TABLE_NAME + "=?", selectionArgs);
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}

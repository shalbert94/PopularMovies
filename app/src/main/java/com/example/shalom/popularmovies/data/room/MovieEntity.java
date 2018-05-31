package com.example.shalom.popularmovies.data.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.shalom.popularmovies.data.MovieContract.FavoriteMoviesEntry;
import com.example.shalom.popularmovies.service.model.Movie;

@Entity(tableName = FavoriteMoviesEntry.TABLE_NAME)
public class MovieEntity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = FavoriteMoviesEntry.COLUMN_ROW_ID)
    public int id;

    @NonNull
    @ColumnInfo(name = FavoriteMoviesEntry.COLUMN_POSTER_URL)
    private String posterPath;

    @NonNull
    @ColumnInfo(name = FavoriteMoviesEntry.COLUMN_ENGLISH_TITLE)
    private String englishTitle;

    @NonNull
    @ColumnInfo(name = FavoriteMoviesEntry.COLUMN_ORIGINAL_TITLE)
    private String originalTitle;

    @NonNull
    @ColumnInfo(name = FavoriteMoviesEntry.COLUMN_SYNOPSIS)
    private String synopsis;

    @NonNull
    @ColumnInfo(name = FavoriteMoviesEntry.COLUMN_RATING)
    private String rating;

    @NonNull
    @ColumnInfo(name = FavoriteMoviesEntry.COLUMN_RELEASE_DATE)
    private String releaseDate;

    @NonNull
    @ColumnInfo(name = FavoriteMoviesEntry.COLUMN_MOVIE_ID)
    private String movieID;

    public MovieEntity(Movie movie) {
        this.posterPath = movie.getPosterPath();
        this.englishTitle = movie.getTitle();
        this.originalTitle = movie.getOriginalTitle();
        this.synopsis = movie.getOverview();
        this.rating = movie.getVoteAverage().toString();
        this.releaseDate = movie.getReleaseDate();
        this.movieID = movie.getId().toString();
    }

    @NonNull
    public int getId() {
        return id;
    }

    @NonNull
    public String getPosterPath() {
        return posterPath;
    }

    @NonNull
    public String getEnglishTitle() {
        return englishTitle;
    }

    @NonNull
    public String getOriginalTitle() {
        return originalTitle;
    }

    @NonNull
    public String getSynopsis() {
        return synopsis;
    }

    @NonNull
    public String getRating() {
        return rating;
    }

    @NonNull
    public String getReleaseDate() {
        return releaseDate;
    }

    @NonNull
    public String getMovieID() {
        return movieID;
    }
}

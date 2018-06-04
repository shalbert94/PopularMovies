package com.example.shalom.popularmovies.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.shalom.popularmovies.data.MovieContract.FavoriteMoviesEntry;

@Entity(tableName = FavoriteMoviesEntry.TABLE_NAME)
public class MovieEntity {
    @NonNull
    @PrimaryKey()
    @ColumnInfo(name = FavoriteMoviesEntry.COLUMN_ENGLISH_TITLE)
    private String title;
    @ColumnInfo(name = FavoriteMoviesEntry.COLUMN_MOVIE_ID)
    private Integer id;
    @ColumnInfo(name = FavoriteMoviesEntry.COLUMN_RATING)
    private Double voteAverage;
    @ColumnInfo(name = FavoriteMoviesEntry.COLUMN_POSTER_URL)
    private String posterPath;
    @ColumnInfo(name = FavoriteMoviesEntry.COLUMN_ORIGINAL_TITLE)
    private String originalTitle;
    @ColumnInfo(name = FavoriteMoviesEntry.COLUMN_SYNOPSIS)
    private String overview;
    @ColumnInfo(name = FavoriteMoviesEntry.COLUMN_RELEASE_DATE)
    private String releaseDate;

    public Movie convertToMovie() {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setVoteAverage(voteAverage);
        movie.setTitle(title);
        movie.setPosterPath(posterPath);
        movie.setOriginalTitle(originalTitle);
        movie.setOverview(overview);
        movie.setReleaseDate(releaseDate);

        return movie;
    }

    public Integer getId() {
        return id;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

}

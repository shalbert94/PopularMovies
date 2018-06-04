package com.example.shalom.popularmovies.data.room;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.shalom.popularmovies.data.MovieContract;
import com.example.shalom.popularmovies.data.MovieContract.FavoriteMoviesEntry;
import com.example.shalom.popularmovies.data.model.Movie;
import com.example.shalom.popularmovies.data.model.MovieEntity;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieEntity movieEntity);

    @Delete
    void delete(MovieEntity movieEntity);

    @Query("SELECT * FROM " + FavoriteMoviesEntry.TABLE_NAME)
    LiveData<List<MovieEntity>> allMovies();

}

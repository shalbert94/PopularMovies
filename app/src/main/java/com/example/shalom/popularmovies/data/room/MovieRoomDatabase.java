package com.example.shalom.popularmovies.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.shalom.popularmovies.data.MovieContract;
import com.example.shalom.popularmovies.data.model.Movie;
import com.example.shalom.popularmovies.data.model.MovieEntity;

@Database(entities = {MovieEntity.class}, version = MovieContract.DATABASE_VERSION)
public abstract class MovieRoomDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();

    private static MovieRoomDatabase INSTANCE;

    public static MovieRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MovieRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieRoomDatabase.class, MovieContract.DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}

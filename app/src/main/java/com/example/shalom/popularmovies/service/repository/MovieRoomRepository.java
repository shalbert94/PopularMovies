package com.example.shalom.popularmovies.service.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.example.shalom.popularmovies.data.model.Movie;
import com.example.shalom.popularmovies.data.model.MovieEntity;
import com.example.shalom.popularmovies.data.room.MovieDao;
import com.example.shalom.popularmovies.data.room.MovieRoomDatabase;

import java.util.List;

public class MovieRoomRepository {

    private MovieDao movieDao;

    private LiveData<List<MovieEntity>> favoritedMovies;

    public MovieRoomRepository(Application application) {
        MovieRoomDatabase db = MovieRoomDatabase.getDatabase(application);
        movieDao = db.movieDao();
        favoritedMovies = movieDao.allMovies();
    }

    public LiveData<List<MovieEntity>> getFavoritedMovies() {
        return favoritedMovies;
    }

    public void insert(MovieEntity movieEntity) {
        new InsertAsyncClass(movieDao).execute(movieEntity);
    }

    public void delete(MovieEntity movieEntity) {
        new DeleteAsyncClass(movieDao).execute(movieEntity);
    }

    private static class InsertAsyncClass extends AsyncTask<MovieEntity, Void, Void> {
        private MovieDao asyncTaskDao;

        InsertAsyncClass(MovieDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(MovieEntity... movieEntities) {
            asyncTaskDao.insert(movieEntities[0]);
            return null;
        }

    }

    private static class DeleteAsyncClass extends AsyncTask<MovieEntity, Void, Void> {
        private MovieDao asyncTaskDao;

        DeleteAsyncClass(MovieDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(MovieEntity... movieEntities) {
            asyncTaskDao.delete(movieEntities[0]);
            return null;
        }
    }
}

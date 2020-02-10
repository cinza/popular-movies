package com.azuniga.udacitypopularmovies.database;

import android.app.Application;

import com.azuniga.udacitypopularmovies.database.MovieRoomDataBase;
import com.azuniga.udacitypopularmovies.models.Movie;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MoviesViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> favoriteMovies;
    MovieRoomDataBase database;
    //private LiveData<Movie> movie;

    public MoviesViewModel(@NonNull Application application) {
        super(application);
         database = MovieRoomDataBase.getInstance(this.getApplication());
    }

    public LiveData<List<Movie>> getFavoritesMovies() {
        favoriteMovies = database.movieDao().loadFavorites();

        return  favoriteMovies;}

    public void addFavoriteMovie(Movie m){
        database.movieDao().insert(m);

    }

    public void deleteFavoriteMovie(Movie m){
        database.movieDao().delete(m);
    }
}

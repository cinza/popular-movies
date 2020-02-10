package com.azuniga.udacitypopularmovies.database;

import com.azuniga.udacitypopularmovies.models.Movie;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie movie);

    @Delete
    void delete(Movie movie);

    @Query("SELECT * FROM favorites_movies")
    LiveData<List<Movie>> loadFavorites();


    @Query("SELECT * FROM favorites_movies WHERE id = :id")
    LiveData<Movie> getMovie(int id);

    @Query("SELECT COUNT(ID) FROM favorites_movies")
    int getFavorites();


}

package com.azuniga.udacitypopularmovies.database;

import android.content.Context;
import android.util.Log;

import com.azuniga.udacitypopularmovies.models.Movie;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)

public abstract class MovieRoomDataBase extends RoomDatabase {

     private static final String LOG_TAG = MovieRoomDataBase.class.getSimpleName();
     private static volatile MovieRoomDataBase INSTANCE;
     private static final Object LOCK = new Object();


    public static  MovieRoomDataBase getInstance( Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieRoomDataBase.class, "favorites_database")
                            .build();
                }
            }
        }
        Log.d(LOG_TAG, "Creating db instance");
        return INSTANCE;
    }
     public abstract MovieDao movieDao();
}

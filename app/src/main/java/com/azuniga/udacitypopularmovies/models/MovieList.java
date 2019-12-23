package com.azuniga.udacitypopularmovies.models;

import com.azuniga.udacitypopularmovies.models.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieList {
    @SerializedName("data")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}

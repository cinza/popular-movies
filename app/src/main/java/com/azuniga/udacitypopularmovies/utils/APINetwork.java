package com.azuniga.udacitypopularmovies.utils;

import com.azuniga.udacitypopularmovies.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface APINetwork {
    static final String MOVIE_TOKEN="4810cdb903f6c99d9a2584aa26b24220";

    @GET("{popular}")
    Call<Movie> getPopularMovies(
            @Path("popular") String sortOpt,
            @Query("api_key") String token);

    @GET("{top_rated}")
    Call<Movie> getTopRatedMovies(
            @Path("top_rated") String sortOpt,
            @Query("apy_key") String token);

    @GET("{sortOrder}")
    Call<Movie>getMoviesBySort(
            @Path("sortOrder") String sortOpt,
            @Query("apy_key") String token);
    @GET("{id}")
    Call<Movie> getMovie(
            @Path("id") String id,
            @Query("apy_key") String token);

}

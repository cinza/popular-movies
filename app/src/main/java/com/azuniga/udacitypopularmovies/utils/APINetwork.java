package com.azuniga.udacitypopularmovies.utils;

import com.azuniga.udacitypopularmovies.models.Movie;
import com.azuniga.udacitypopularmovies.MovieAPIResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface APINetwork {
    static final String MOVIE_TOKEN="4810cdb903f6c99d9a2584aa26b24220";

    @GET("popular")
    Call<MovieAPIResponse>getPopularMovies(
            @Query("api_key") String token);

    @GET("top_rated")
    Call<MovieAPIResponse> getTopRatedMovies(
            @Query("api_key") String token);

    @GET("{id}")
    Call<Movie> getMovie(
            @Path("id") String id,
            @Query("api_key") String token);

    @GET("{id}")
    Call<Movie> getTrailers(
            @Path("id") String id,
            @Query("api_key") String token);

    @GET("{id}")
    Call<Movie> getReviews(
            @Path("id") String id,
            @Query("api_key") String token);

}

package com.azuniga.udacitypopularmovies.utils;

import com.azuniga.udacitypopularmovies.models.Movie;
import com.azuniga.udacitypopularmovies.models.MovieAPIResponse;
import com.azuniga.udacitypopularmovies.models.ReviewAPIResponse;
import com.azuniga.udacitypopularmovies.models.VideoAPIResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface APINetwork {
    //ADD YOUR MOVIE KEY HERE
    String MOVIE_TOKEN="";

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

    @GET("{id}/videos")
    Call<VideoAPIResponse> getVideos(
            @Path("id") String id,
            @Query("api_key") String token);

    @GET("{id}/reviews")
    Call<ReviewAPIResponse> getReviews(
            @Path("id") String id,
            @Query("api_key") String token);

}

package com.azuniga.udacitypopularmovies.models;


import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName ("id")
    String id;

    @SerializedName ("title")
    String title;

    @SerializedName ("poster_path")
    String urlImage;

    @SerializedName("backdrop_path")
    String urlBackground;

    @SerializedName ("overview")
    String synopsis;

    @SerializedName ("vote_average")
    double rating;

    @SerializedName ("release_date")
    String release;

    public Movie (String id, String title, String urlImage, String synopsis, double rating, String release){
        this.id = id;
        this.title = title;
        this.urlImage = urlImage;
        this.synopsis = synopsis;
        this.rating = rating;
        this.release = release;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setUrlImage(String urlImage){
        this.urlImage = urlImage;
    }

    public String getUrlImage(){
        return urlImage;
    }

    public void setUrlBackground(String urlBackground){
        this.urlBackground = urlBackground;
    }

    public String getUrlBackground(){
        return urlBackground;
    }
    public void setSynopsis(String synopsis){
        this.synopsis = synopsis;
    }
    public String getSynopsis() {
        return synopsis;
    }

    public void setRating(double rating){
        this.rating = rating;
    }

    public double getRating(){
        return rating;
    }

    public void setRelease(String release){ this.release = release;}

    public String getRelease() {
        return release;
    }
}

package com.azuniga.udacitypopularmovies;


import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName ("id")
    String id;

    @SerializedName ("title")
    String title;

    @SerializedName ("urlImage")
    String urlImage;

    @SerializedName ("synopsis")
    String synopsis;

    @SerializedName ("rating")
    Integer rating;

    @SerializedName ("release")
    String release;

    public Movie (String id, String title, String urlImage, String synopsis, int rating, String release){
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
    public void setSynopsis(){
        
    }
    public String getSynopsis() {
        return synopsis;
    }
}

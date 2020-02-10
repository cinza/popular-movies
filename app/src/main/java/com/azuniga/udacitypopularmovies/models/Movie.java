package com.azuniga.udacitypopularmovies.models;


import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites_movies")
public class Movie {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName ("id")
    String id;

    @ColumnInfo(name = "title")
    @SerializedName ("title")
    String title;

    @ColumnInfo(name = "poster_path")
    @SerializedName ("poster_path")
    String urlImage;

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    String urlBackground;

    @ColumnInfo(name = "overview")
    @SerializedName ("overview")
    String synopsis;

    @ColumnInfo(name = "vote_average")
    @SerializedName ("vote_average")
    double rating;

    @ColumnInfo(name = "release_date")
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

   // overwrite equals method to check from a list if a movie is already on the list
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Movie){
            return this.id.equals(((Movie) obj).id);
        } else return false;
    }


}

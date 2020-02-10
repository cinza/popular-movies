package com.azuniga.udacitypopularmovies.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class VideoAPIResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private List<Video> results;

    public List<Video> getResults(){ return results;}


}

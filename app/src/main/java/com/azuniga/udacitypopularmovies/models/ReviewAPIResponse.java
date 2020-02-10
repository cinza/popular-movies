package com.azuniga.udacitypopularmovies.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewAPIResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<Review> results;

    public List<Review> getResults() {
        return results;
    }


}
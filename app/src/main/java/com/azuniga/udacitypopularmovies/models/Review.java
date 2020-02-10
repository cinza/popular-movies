package com.azuniga.udacitypopularmovies.models;

import com.google.gson.annotations.SerializedName;


public class Review {

    @SerializedName("id")
    String id;

    @SerializedName("author")
    String author;

    @SerializedName("content")
    String content;

    @SerializedName("url")
    String url;

    public Review (String id, String author, String content, String url){
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;

    }


    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getAuthor(){
        return author;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){ return content; }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }


}

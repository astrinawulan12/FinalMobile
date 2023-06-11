package com.example.h071211018_finalmobile.tv.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvResponse {
    @SerializedName("results")
    private List<TvShow> tvShows;
    public List<TvShow> getTvShows(){return tvShows;}
}


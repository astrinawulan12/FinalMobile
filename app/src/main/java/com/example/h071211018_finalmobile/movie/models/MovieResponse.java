package com.example.h071211018_finalmobile.movie.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    @SerializedName("results")
    private List<Movie> movie;

    public List<Movie> getMovie() {
        return movie;
    }
}

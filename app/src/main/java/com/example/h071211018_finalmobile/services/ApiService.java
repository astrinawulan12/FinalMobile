package com.example.h071211018_finalmobile.services;

import com.example.h071211018_finalmobile.movie.models.MovieResponse;
import com.example.h071211018_finalmobile.tv.models.TvResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie/now_playing?")
    Call<MovieResponse> getMovie(@Query("api_key") String apikey, @Query("page") int page);

    @GET("tv/on_the_air?")
    Call<TvResponse> getTvShow(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );
}

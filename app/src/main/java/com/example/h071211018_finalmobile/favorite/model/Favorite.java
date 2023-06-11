package com.example.h071211018_finalmobile.favorite.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Favorite implements Parcelable {
    private int id;

    private int tmdbId;
    private String title;
    private String overview;
    private String voteAverage;
    private String bannerUrl;
    private boolean isMovie;
    private String date;
    private String posterpath;

    public Favorite( int tmdbId, String title, String overview, String voteAverage, String bannerUrl, boolean isMovie, String date, String posterpath) {
        this.tmdbId = tmdbId;
        this.title = title;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.bannerUrl = bannerUrl;
        this.isMovie = isMovie;
        this.date = date;
        this.posterpath = posterpath;
    }

    protected Favorite(Parcel in) {
        id = in.readInt();
        tmdbId = in.readInt();
        title = in.readString();
        overview = in.readString();
        voteAverage = in.readString();
        bannerUrl = in.readString();
        isMovie = in.readByte() != 0;
        date = in.readString();
        posterpath = in.readString();
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    public int getTmdbId() {
        return tmdbId;
    }


    public void setTmdbId(int tmdbId) {
        this.tmdbId = tmdbId;
    }

    public String getYear() {
        try{
            String[] releaseYear = date.split("-");
            return releaseYear[0];
        }catch (Exception e){
            return  "-";
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public boolean isMovie() {
        return isMovie;
    }

    public void setMovie(boolean movie) {
        isMovie = movie;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPosterpath() {
        return posterpath;
    }

    public void setPosterpath(String posterpath) {
        this.posterpath = posterpath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(tmdbId);
        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeString(voteAverage);
        parcel.writeString(bannerUrl);
        parcel.writeByte((byte) (isMovie ? 1 : 0));
        parcel.writeString(date);
        parcel.writeString(posterpath);
    }
}


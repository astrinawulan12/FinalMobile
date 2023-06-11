package com.example.h071211018_finalmobile.tv.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class TvShow implements Parcelable {
    private int id;
    private String name;

    @SerializedName("poster_path")
    private String imgUrl;

    @SerializedName("vote_average")
    private String voteAverage;

    @SerializedName("first_air_date")
    private String releaseDate;

    private String overview;

    @SerializedName("backdrop_path")
    private String bannerUrl;

    protected TvShow(Parcel in) {
        id = in.readInt();
        name = in.readString();
        imgUrl = in.readString();
        voteAverage = in.readString();
        releaseDate = in.readString();
        overview = in.readString();
        bannerUrl = in.readString();
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel in) {
            return new TvShow(in);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };

    public String getReleaseYear() {
        try{
            String[] releaseYear = releaseDate.split("-");
            return releaseYear[0];
        }catch (Exception e){
            return  "-";
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(imgUrl);
        parcel.writeString(voteAverage);
        parcel.writeString(releaseDate);
        parcel.writeString(overview);
        parcel.writeString(bannerUrl);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }
}


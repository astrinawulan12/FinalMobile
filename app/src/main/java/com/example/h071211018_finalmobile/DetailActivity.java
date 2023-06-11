package com.example.h071211018_finalmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.h071211018_finalmobile.favorite.FavoriteFragment;
import com.example.h071211018_finalmobile.favorite.helpers.MapHelper;
import com.example.h071211018_finalmobile.favorite.helpers.TmdbHelper;
import com.example.h071211018_finalmobile.favorite.model.Favorite;
import com.example.h071211018_finalmobile.movie.models.Movie;
import com.example.h071211018_finalmobile.tv.models.TvShow;
import com.google.android.material.card.MaterialCardView;

public class DetailActivity extends AppCompatActivity {

    public static final int DETAIL_RESULT_CODE = 102;
    public static final String EXTRA_DATA = "data";
    public static final String EXTRA_TYPE = "type";

    private ImageView ivPoster, ivBackdrop, ivFavorite;
    private MaterialCardView btnFavorite;
    private TextView tvTitle, tvYear, tvSinopsis, tvRating, tvToolbar;

    private RatingBar rbScore;

    private boolean isFavorite;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ivPoster = findViewById(R.id.iv_mv_poster);
        ivBackdrop = findViewById(R.id.iv_mv_backdrop);
        tvTitle = findViewById(R.id.tv_detail_title);
        tvYear = findViewById(R.id.tv_detail_year);
        tvRating = findViewById(R.id.tv_detail_score);
        tvSinopsis = findViewById(R.id.tv_detail_synopsis);
        rbScore = findViewById(R.id.rb_detail);
        tvToolbar = findViewById(R.id.tv_toolbar);
        toolbar = findViewById(R.id.toolbar);
        btnFavorite = findViewById(R.id.btn_favorite);
        ivFavorite = findViewById(R.id.iv_favorite);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v->onBackPressed());

        String type = getIntent().getStringExtra(EXTRA_TYPE);
        if(type.equals("MOVIE")){
            Movie movie = getIntent().getParcelableExtra(EXTRA_DATA);
            handlingMovieDetailView(movie);
            checkFavoriteStatus(movie.getId());
            btnFavorite.setOnClickListener(v->onFavClick(movie));
        }else if(type.equals("TV")){
            TvShow tv = getIntent().getParcelableExtra(EXTRA_DATA);
            handlingTvDetailView(tv);
            checkFavoriteStatus(tv.getId());
            btnFavorite.setOnClickListener(v->onFavClick(tv));
        }else if(type.equals("FAVORITE")){
            Favorite fav = getIntent().getParcelableExtra(EXTRA_DATA);
            handlingFavoriteDetailView(fav);
            checkFavoriteStatus(fav.getTmdbId());
            btnFavorite.setOnClickListener(v->onFavClick(fav));
        }
    }

    private void checkFavoriteStatus(int tmdbId){
        TmdbHelper favHelper = TmdbHelper.getInstance(this);
        favHelper.open();
        isFavorite = favHelper.isFavorite(tmdbId);
        if(isFavorite){
            ivFavorite.setImageResource(R.drawable.baseline_favorite_24);
        }else{
            ivFavorite.setImageResource(R.drawable.baseline_favorite_border_24);
        }
        favHelper.close();
    }

    private void onFavClick(Movie movie){
        TmdbHelper favHelper = TmdbHelper.getInstance(this);
        favHelper.open();
        Favorite favorite = new Favorite(movie.getId(), movie.getTitle(), movie.getOverview(), movie.getVoteAverage(),movie.getBannerUrl(), true, movie.getDate(), movie.getPosterpath());
        if(isFavorite){
            favHelper.delete(movie.getId());
            FavoriteFragment.favAdapter.removeItem(favorite);
            Toast.makeText(this, movie.getTitle() + " Dihapus dari Favorite", Toast.LENGTH_SHORT).show();
        }else{
            favHelper.create(MapHelper.favToContentValues(favorite));
            FavoriteFragment.favAdapter.addItem(favorite);
            Toast.makeText(this, movie.getTitle() + " Ditambahkan ke Favorite", Toast.LENGTH_SHORT).show();
        }
        checkFavoriteStatus(favorite.getTmdbId());
        favHelper.close();
    }

    private void onFavClick(Favorite fav){
        TmdbHelper favHelper = TmdbHelper.getInstance(this);
        favHelper.open();
        if(isFavorite){
            favHelper.delete(fav.getTmdbId());
            FavoriteFragment.favAdapter.removeItem(fav);
            Toast.makeText(this, fav.getTitle() + " Dihapus dari Favorite", Toast.LENGTH_SHORT).show();

        }else{
            favHelper.create(MapHelper.favToContentValues(fav));
            FavoriteFragment.favAdapter.addItem(fav);
            Toast.makeText(this, fav.getTitle() + " Ditambahkan ke Favorite", Toast.LENGTH_SHORT).show();

        }
        checkFavoriteStatus(fav.getTmdbId());
        favHelper.close();
    }

    private void onFavClick(TvShow tv){
        TmdbHelper favHelper = TmdbHelper.getInstance(this);
        favHelper.open();
        Favorite favorite = new Favorite(tv.getId(), tv.getName(), tv.getOverview(), tv.getVoteAverage(),tv.getBannerUrl(), false, tv.getReleaseDate(), tv.getImgUrl());
        if(isFavorite){
            favHelper.delete(tv.getId());
            FavoriteFragment.favAdapter.removeItem(favorite);
            Toast.makeText(this, tv.getName() + " Dihapus dari Favorite", Toast.LENGTH_SHORT).show();

        }else{
            favHelper.create(MapHelper.favToContentValues(favorite));
            FavoriteFragment.favAdapter.addItem(favorite);
            Toast.makeText(this, tv.getName() + " Ditambahkan ke Favorite", Toast.LENGTH_SHORT).show();

        }
        checkFavoriteStatus(favorite.getTmdbId());
        favHelper.close();
    }



    private void handlingMovieDetailView(Movie movie){
        tvToolbar.setText("Detail Movie");
        tvTitle.setText(movie.getTitle());
        tvYear.setText(movie.getYear());
        tvSinopsis.setText(movie.getOverview().isEmpty() ? "-" : movie.getOverview());
        tvRating.setText(movie.getVoteAverage());
        rbScore.setRating(Float.parseFloat(movie.getVoteAverage()));
        Glide.with(this).load("https://image.tmdb.org/t/p/w200/" + movie.getPosterpath()).into(ivPoster);
        Glide.with(this).load("https://image.tmdb.org/t/p/w200/" + movie.getBannerUrl()).into(ivBackdrop);
    }

    private void handlingTvDetailView(TvShow tvShow){
        tvToolbar.setText("Detail Tv Show");
        tvTitle.setText(tvShow.getName());
        tvYear.setText(tvShow.getReleaseYear());
        rbScore.setRating(Float.parseFloat(tvShow.getVoteAverage()));
        tvSinopsis.setText(tvShow.getOverview().isEmpty() ? "-" : tvShow.getOverview());
        tvRating.setText(tvShow.getVoteAverage());
        Glide.with(this).load("https://image.tmdb.org/t/p/w200/" + tvShow.getImgUrl()).into(ivPoster);
        Glide.with(this).load("https://image.tmdb.org/t/p/w200/" + tvShow.getBannerUrl()).into(ivBackdrop);
    }

    private void handlingFavoriteDetailView(Favorite fav){
        tvToolbar.setText(fav.isMovie() ? "Detail Movie" : "Detail Tv Show");
        tvTitle.setText(fav.getTitle());
        tvYear.setText(fav.getYear());
        tvSinopsis.setText(fav.getOverview().isEmpty() ? "-" : fav.getOverview());
        tvRating.setText(fav.getVoteAverage());
        rbScore.setRating(Float.parseFloat(fav.getVoteAverage()));
        Glide.with(this).load("https://image.tmdb.org/t/p/w200/" + fav.getPosterpath()).into(ivPoster);
        Glide.with(this).load("https://image.tmdb.org/t/p/w200/" + fav.getBannerUrl()).into(ivBackdrop);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getIntent().getStringExtra(EXTRA_TYPE).equals("FAVORITE")){
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
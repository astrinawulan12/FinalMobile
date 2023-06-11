package com.example.h071211018_finalmobile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.h071211018_finalmobile.favorite.FavoriteFragment;
import com.example.h071211018_finalmobile.movie.MovieFragment;
import com.example.h071211018_finalmobile.tv.TvShowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private TextView toolbar;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.tv_toolbar);
        bottomNavigationView = findViewById(R.id.bottomView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menu_tvshow);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_tvshow:
                navigateFragment(new TvShowFragment(), "tv");
                toolbar.setText("Tv Show");
                return true;
            case R.id.menu_movie:
                navigateFragment(new MovieFragment(),"movie");
                toolbar.setText("Movie");
                return true;
            case R.id.menu_favorite:
                navigateFragment(new FavoriteFragment(), "fav");
                toolbar.setText("Favorite");
                return true;
        }
        return false;
    }

    public void navigateFragment(Fragment fragment, String tag){
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,fragment, tag).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DetailActivity.DETAIL_RESULT_CODE) {
            FavoriteFragment favoriteFragment = (FavoriteFragment) getSupportFragmentManager().findFragmentByTag("fav");
            if (favoriteFragment != null) {
                favoriteFragment.fetchData();
            }
        }
    }
}
package com.example.h071211018_finalmobile.movie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.h071211018_finalmobile.R;
import com.example.h071211018_finalmobile.movie.adapter.MovieAdapter;
import com.example.h071211018_finalmobile.movie.models.MovieResponse;
import com.example.h071211018_finalmobile.services.ApiConfig;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {
    private ProgressBar pb;
    private RecyclerView rv;
    private LinearLayout llEmpty;
    private MovieAdapter movieAdapter;
    private int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv = view.findViewById(R.id.rvMovie);
        pb = view.findViewById(R.id.pb_movie);
        llEmpty = view.findViewById(R.id.ll_movie_empty);

        movieAdapter = new MovieAdapter(new ArrayList<>());
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        rv.setLayoutManager(layoutManager);
        fetchData();
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int totalItem = layoutManager.getItemCount();
                int visibleItem = layoutManager.getChildCount();
                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                if (firstVisibleItem + visibleItem >= totalItem / 2) {
                    page++;
                    fetchData();
                }
            }
        });
    }

    private void fetchData(){
        isLoading(true);
        ApiConfig.getApiService().getMovie(ApiConfig.getApikey(), page).enqueue(new Callback<MovieResponse>(){
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    movieAdapter.addMovie(response.body().getMovie());
                    rv.setAdapter(movieAdapter);
                    isLoading(false);
                }
            }
            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                Log.e("MainActivity", "onFailure: " + t.getMessage());
                isError(true);
            }
        });
    }

    private void isLoading(boolean isLoading){
        if(isLoading){
            pb.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
        }else{
            pb.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
        }
        isError(false);
    }


    private void isError(boolean isError){
        if(isError){
            llEmpty.setVisibility(View.VISIBLE);
            pb.setVisibility(View.GONE);
            rv.setVisibility(View.GONE);
        }else{
            llEmpty.setVisibility(View.GONE);
        }
    }


}
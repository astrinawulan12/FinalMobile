package com.example.h071211018_finalmobile.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.h071211018_finalmobile.DetailActivity;
import com.example.h071211018_finalmobile.R;
import com.example.h071211018_finalmobile.movie.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    private final List<Movie> movieList;

    public MovieAdapter(List<Movie> movieList){
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.carditem, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        Context c = holder.itemView.getContext();
        Glide.with(c).load("https://image.tmdb.org/t/p/w200/" + movie.getPosterpath()).into(holder.ivPoster);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvYear.setText(movie.getYear());
        holder.itemView.setOnClickListener(view->{
            Intent toDetail = new Intent(c, DetailActivity.class);
            toDetail.putExtra(DetailActivity.EXTRA_DATA, movie);
            toDetail.putExtra(DetailActivity.EXTRA_TYPE, "MOVIE");
            c.startActivity(toDetail);
        });
    }

    public void appendList(List<Movie> listToAppend) {
        movieList.addAll(listToAppend);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void addMovie(List<Movie> movie) {
        this.movieList.addAll(movie);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPoster;
        private TextView tvTitle;
        private TextView tvYear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvYear = itemView.findViewById(R.id.tvYear);
        }
    }
}


package com.example.h071211018_finalmobile.tv.adapter;

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
import com.example.h071211018_finalmobile.tv.models.TvShow;

import java.util.List;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.ViewHolder>{

    private final List<TvShow> tvList;

    public TvAdapter(List<TvShow> tvList){
        this.tvList = tvList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.carditem, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TvShow tvShow = tvList.get(position);
        Context c = holder.itemView.getContext();
        Glide.with(c).load("https://image.tmdb.org/t/p/w200/" + tvShow.getImgUrl()).into(holder.ivPoster);
        holder.tvTitle.setText(tvShow.getName());
        holder.tvYear.setText(tvShow.getReleaseYear());
        holder.itemView.setOnClickListener(view->{
            Intent toDetail = new Intent(c, DetailActivity.class);
            toDetail.putExtra(DetailActivity.EXTRA_DATA, tvShow);
            toDetail.putExtra(DetailActivity.EXTRA_TYPE, "TV");
            c.startActivity(toDetail);
        });
    }

    public void appendList(List<TvShow> listToAppend) {
        tvList.addAll(listToAppend);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tvList.size();
    }

    public void addMovie(List<TvShow> tvShow) {
        this.tvList.addAll(tvShow);
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


package com.example.h071211018_finalmobile.favorite.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.h071211018_finalmobile.DetailActivity;
import com.example.h071211018_finalmobile.R;
import com.example.h071211018_finalmobile.favorite.model.Favorite;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder>{

    private ActivityResultLauncher<Intent> resultLauncher;

    public void clear(){
        favList.clear();
        notifyDataSetChanged();
    }
    public void addItem(Favorite fav) {
        favList.add(fav);
        notifyItemInserted(favList.size() - 1);
    }
    public void removeItem(Favorite favorite) {
        int position = favList.indexOf(favorite);
        if (position != -1) {
            favList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private final List<Favorite> favList;

    public FavoriteAdapter(List<Favorite> favList){
        this.favList = favList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.carditem, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Favorite favorite = favList.get(position);
        Context c = holder.itemView.getContext();
        Glide.with(c).load("https://image.tmdb.org/t/p/w200/" + favorite.getPosterpath()).into(holder.ivPoster);
        holder.tvTitle.setText(favorite.getTitle());
        holder.tvYear.setText(favorite.getYear());
        holder.itemView.setOnClickListener(view->{
            Intent toDetail = new Intent(c, DetailActivity.class);
            toDetail.putExtra(DetailActivity.EXTRA_DATA, favorite);
            toDetail.putExtra(DetailActivity.EXTRA_TYPE, "FAVORITE");
            ((Activity) c).startActivityForResult(toDetail, DetailActivity.DETAIL_RESULT_CODE);
        });
    }


    @Override
    public int getItemCount() {
        return favList.size();
    }

    public void addFavorite(ArrayList<Favorite> favorite) {
        this.favList.clear();
        this.favList.addAll(favorite);
        notifyDataSetChanged();
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
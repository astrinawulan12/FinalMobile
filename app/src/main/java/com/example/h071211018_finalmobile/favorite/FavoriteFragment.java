package com.example.h071211018_finalmobile.favorite;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.h071211018_finalmobile.R;
import com.example.h071211018_finalmobile.favorite.adapter.FavoriteAdapter;
import com.example.h071211018_finalmobile.favorite.helpers.MapHelper;
import com.example.h071211018_finalmobile.favorite.helpers.TmdbHelper;
import com.example.h071211018_finalmobile.favorite.model.Favorite;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoriteFragment extends Fragment {

    private ProgressBar pb;
    private RecyclerView rv;
    public static FavoriteAdapter favAdapter = new FavoriteAdapter(new ArrayList<>());
    private LinearLayout llEmpty;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv = view.findViewById(R.id.rvMovie);
        pb = view.findViewById(R.id.pb_movie);
        llEmpty = view.findViewById(R.id.ll_movie_empty);

        isLoading(true);

        rv.setAdapter(favAdapter);
        int numberOfColumns = 2;
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
        rv.setLayoutManager(layoutManager);

        fetchData();
    }

    public void fetchData(){
        LoadFavAsync loadFavAsync = new LoadFavAsync(getActivity(), favorites -> {
            if (favorites.size() > 0) {
                favAdapter.addFavorite(favorites);
            } else {
                favAdapter.clear();
            }
            isLoading(false);
        });
        loadFavAsync.execute();
    }

    private static class LoadFavAsync {

        private final WeakReference<Context> weakContext;
        private final LoadFavCallback callback;

        private LoadFavAsync(Context context, LoadFavCallback callback) {
            weakContext = new WeakReference<>(context);
            this.callback = callback;
        }

        void execute() {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(() -> {
                Context context = weakContext.get();
                if (context != null) {
                    TmdbHelper favHelper = TmdbHelper.getInstance(context);
                    favHelper.open();

                    Cursor favCursor = favHelper.readAll();
                    ArrayList<Favorite> favorites = MapHelper.mapCursorToArrayList(favCursor);
                    favHelper.close();

                    handler.post(() -> callback.postExecute(favorites));
                }
            });
        }
    }

    interface LoadFavCallback {
        void postExecute(ArrayList<Favorite> favorites);
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
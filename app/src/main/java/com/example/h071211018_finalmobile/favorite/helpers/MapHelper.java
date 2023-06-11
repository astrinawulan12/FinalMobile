package com.example.h071211018_finalmobile.favorite.helpers;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.h071211018_finalmobile.favorite.db.DatabaseContract;
import com.example.h071211018_finalmobile.favorite.model.Favorite;

import java.util.ArrayList;

public class MapHelper {
    public static ArrayList<Favorite> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<Favorite> notesList = new ArrayList<>();

        while (notesCursor.moveToNext()) {
            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(DatabaseContract.TmdbColumns._ID));
            String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.TmdbColumns.TITLE));
            String backdropPath = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.TmdbColumns.BACKDROP_PATH));
            int tmdbId = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(DatabaseContract.TmdbColumns.TMDB_ID));
            int isMovie = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(DatabaseContract.TmdbColumns.IS_MOVIE));
            String overview = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.TmdbColumns.OVERVIEW));
            String poster = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.TmdbColumns.POSTER_PATH));
            String releaseDate = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.TmdbColumns.RELEASE_DATE));
            String voteAverage = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.TmdbColumns.VOTE_AVERAGE));

            notesList.add(new Favorite(tmdbId, title, overview, voteAverage, backdropPath, isMovie==1?true:false, releaseDate, poster));
        }

        return notesList;
    }

    public static ContentValues favToContentValues(Favorite favorite){
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.TmdbColumns.TMDB_ID, favorite.getTmdbId());
        values.put(DatabaseContract.TmdbColumns.TITLE, favorite.getTitle());
        values.put(DatabaseContract.TmdbColumns.OVERVIEW, favorite.getOverview());
        values.put( DatabaseContract.TmdbColumns.VOTE_AVERAGE, favorite.getVoteAverage());
        values.put(DatabaseContract.TmdbColumns.BACKDROP_PATH, favorite.getBannerUrl());
        values.put(DatabaseContract.TmdbColumns.IS_MOVIE, favorite.isMovie() ? 1 : 0);
        values.put(DatabaseContract.TmdbColumns.RELEASE_DATE, favorite.getDate());
        values.put(DatabaseContract.TmdbColumns.POSTER_PATH, favorite.getPosterpath());


        return values;
    }
}

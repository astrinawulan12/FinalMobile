package com.example.h071211018_finalmobile.favorite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tmdb.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format(
                "CREATE TABLE %s" +
                        " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " %s TEXT NOT NULL," +
                        " %s TEXT ," +
                        " %s TEXT ," +
                        " %s TEXT ," +
                        " %s TEXT ," +
                        " %s TEXT ," +
                        " %s TEXT ," +
                        " %s INTEGER )",
                DatabaseContract.TABLE_NAME,
                DatabaseContract.TmdbColumns._ID,
                DatabaseContract.TmdbColumns.TMDB_ID,
                DatabaseContract.TmdbColumns.TITLE,
                DatabaseContract.TmdbColumns.OVERVIEW,
                DatabaseContract.TmdbColumns.BACKDROP_PATH,
                DatabaseContract.TmdbColumns.POSTER_PATH,
                DatabaseContract.TmdbColumns.RELEASE_DATE,
                DatabaseContract.TmdbColumns.VOTE_AVERAGE,
                DatabaseContract.TmdbColumns.IS_MOVIE
        );

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }
}


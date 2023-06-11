package com.example.h071211018_finalmobile.favorite.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.h071211018_finalmobile.DetailActivity;
import com.example.h071211018_finalmobile.favorite.db.DatabaseContract;
import com.example.h071211018_finalmobile.favorite.db.DatabaseHelper;

public class TmdbHelper {
    private static final String DATABASE_TABLE = DatabaseContract.TABLE_NAME;
    private static DatabaseHelper databaseHelper;
    private static SQLiteDatabase database;
    private static volatile TmdbHelper INSTANCE;

    public static TmdbHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TmdbHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    private TmdbHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    // Open database
    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    // Close database
    public void close() {
        databaseHelper.close();
        if (database.isOpen()) database.close();
    }

    // Create data
    public long create(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }
    public Cursor readAll() {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                DatabaseContract.TmdbColumns._ID + " ASC"
        );
    }

    public int delete(int tmdbId) {
        return database.delete(
                DATABASE_TABLE,
                DatabaseContract.TmdbColumns.TMDB_ID + " = ?",
                new String[]{String.valueOf(tmdbId)}
        );
    }


    public boolean isFavorite(int tmdbId){
        String query = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + DatabaseContract.TmdbColumns.TMDB_ID + " = ?";
        String[] selectionArgs = {String.valueOf(tmdbId)};
        Cursor cursor = database.rawQuery(query, selectionArgs);
        System.out.println(cursor.getCount());
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

}


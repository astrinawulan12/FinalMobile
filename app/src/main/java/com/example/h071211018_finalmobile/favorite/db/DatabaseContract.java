package com.example.h071211018_finalmobile.favorite.db;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String TABLE_NAME = "data";
    public static final class TmdbColumns implements BaseColumns {
        public static final String TMDB_ID = "tmdb_id";
        public static final String TITLE = "title";
        public static final String OVERVIEW = "overview";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String BACKDROP_PATH = "backdrop_path";
        public static final String RELEASE_DATE = "release_date";
        public static final String POSTER_PATH = "poster_path";

        public static final String IS_MOVIE = "is_movie";


    }
}

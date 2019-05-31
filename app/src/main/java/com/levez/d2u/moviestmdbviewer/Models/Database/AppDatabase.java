package com.levez.d2u.moviestmdbviewer.Models.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.levez.d2u.moviestmdbviewer.Models.api.Constant;

public class AppDatabase extends SQLiteOpenHelper {

    private static volatile AppDatabase sInstance;


    //ver a possibilidade de converter pra json

    static final String MOVIE_TABLE = "MovieTable";
    static final String TV_SERIES_TABLE = "TvSeriesTable";
    static final String PEOPLE_TABLE = "TvSeriesTable";

    static final String KEY_ID_SERIES = "idSeries";
    static final String KEY_ID_EPISODE = "idEpisode";
    static final String KEY_ID_MOVIE = "idMovie";
    static final String KEY_ID_PEOPLE = "idPeople";


    private static final String SCRIPT_CREATE_EPISODE_TABLE = "CREATE TABLE EpisodeTable  ( " +
            "idSeries INTEGER, " +
            "idEpisode INTEGER, " +
            "PRIMARY KEY(idEpisode));";

    private static final String SCRIPT_CREATE_MOVIE_TABLE = "CREATE TABLE MovieTable ( " +
            "idMovie INTEGER, " +
            "PRIMARY KEY(idMovie));";

    private static final String SCRIPT_CREATE_TV_SERIES_TABLE = "CREATE TABLE TvSeriesTable (" +
            "idSeries INTEGER, " +
            "PRIMARY KEY(idSeries));";

    private static final String SCRIPT_CREATE_PEOPLE_TABLE = "CREATE TABLE PeopleTable ( " +
            "idPeople INTEGER, " +
            "PRIMARY KEY(idPeople));";


    private static final String SCRIPT_DROP_EPISODE_TABLE = "DROP TABLE EpisodeTable;";
    private static final String SCRIPT_DROP_MOVIE_TABLE = "DROP TABLE MovieTable;";
    private static final String SCRIPT_DROP_TV_SERIES_TABLE = "DROP TABLE TvSeriesTable;";
    private static final String SCRIPT_DROP_PEOPLE_TABLE = "DROP TABLE PeopleTable;";


    public static AppDatabase getInstance(@Nullable Context context) {

        synchronized (AppDatabase.class){
            if(sInstance == null){
                sInstance = new AppDatabase(context);
            }
        }
        return sInstance;
    }

    private AppDatabase(@Nullable Context context) {
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SCRIPT_CREATE_TV_SERIES_TABLE);
        db.execSQL(SCRIPT_CREATE_EPISODE_TABLE);
        db.execSQL(SCRIPT_CREATE_MOVIE_TABLE);
        db.execSQL(SCRIPT_CREATE_PEOPLE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SCRIPT_DROP_EPISODE_TABLE);
        db.execSQL(SCRIPT_DROP_TV_SERIES_TABLE);
        db.execSQL(SCRIPT_DROP_MOVIE_TABLE);
        db.execSQL(SCRIPT_DROP_PEOPLE_TABLE);
        onCreate(db);
    }

}

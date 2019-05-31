package com.levez.d2u.moviestmdbviewer.Models.RoomDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.levez.d2u.moviestmdbviewer.Models.entity.Episode;
import com.levez.d2u.moviestmdbviewer.Models.entity.Movie;
import com.levez.d2u.moviestmdbviewer.Models.entity.People;
import com.levez.d2u.moviestmdbviewer.Models.entity.TvSeries;

@Database(entities = {Movie.class, People.class, TvSeries.class, Episode.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase sInstance;

    public abstract MovieDAO movieDAO();
    public abstract PeopleDAO peopleDAO();
    public abstract TvSeriesDAO tvSeriesDAO();
    public abstract EpisodeDAO episodeDAO();


    public static AppDatabase getInstance(Context context) {

        synchronized (AppDatabase.class){
            if (sInstance == null) {
                sInstance =
                        Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "cinedb1")
                                .build();
            }
        }

        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }
}
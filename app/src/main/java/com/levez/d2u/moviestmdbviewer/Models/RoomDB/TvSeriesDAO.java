package com.levez.d2u.moviestmdbviewer.Models.RoomDB;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.levez.d2u.moviestmdbviewer.Models.entity.TvSeries;

import java.util.List;

@Dao
public interface TvSeriesDAO  {

    @Query("SELECT id FROM tvseries")
    LiveData<List<Integer>> getAllIds();

    @Insert
    void insertAll(TvSeries...tvSeries);

    @Insert
    void insert(TvSeries tvSeries);

    @Delete
    void remove(TvSeries tvSeries);
}
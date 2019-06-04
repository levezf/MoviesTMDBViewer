package com.levez.d2u.moviestmdbviewer.Models.RoomDB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.levez.d2u.moviestmdbviewer.Models.entity.Episode;

import java.util.List;

import retrofit2.http.DELETE;

@Dao
public interface EpisodeDAO  {

    @Query("SELECT id FROM episode WHERE idSerie = :idSerie")
    LiveData<List<Integer>> getAllIds(int idSerie);

    @Insert
    void insertAll(Episode...episodes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Episode episode);


    @Delete
    void remove(Episode episode);

}
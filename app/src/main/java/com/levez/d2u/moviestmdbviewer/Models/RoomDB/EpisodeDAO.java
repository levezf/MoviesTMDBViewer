package com.levez.d2u.moviestmdbviewer.Models.RoomDB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.levez.d2u.moviestmdbviewer.Models.entity.Episode;

import java.util.List;

import retrofit2.http.DELETE;

@Dao
public interface EpisodeDAO  {

    @Query("SELECT id FROM episode")
    LiveData<List<Integer>> getAllIds();

    @Insert
    void insertAll(Episode...episodes);

    @Insert
    void insert(Episode episode);


    @Delete
    void remove(Episode episode);

}
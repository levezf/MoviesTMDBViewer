package com.levez.d2u.moviestmdbviewer.Models.RoomDB;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.levez.d2u.moviestmdbviewer.Models.entity.Movie;

import java.util.List;

@Dao
public interface MovieDAO  {

    @Query("SELECT id FROM movie")
    LiveData<List<Integer>> getAllIds();

    @Insert
    void insertAll(Movie...movies);

    @Insert
    void insert(Movie movie);


    @Delete
    void remove(Movie movie);
}
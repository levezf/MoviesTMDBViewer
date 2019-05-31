package com.levez.d2u.moviestmdbviewer.Models.RoomDB;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.levez.d2u.moviestmdbviewer.Models.entity.People;

import java.util.List;

@Dao
public interface PeopleDAO  {

    @Query("SELECT id FROM people")
    LiveData<List<Integer>> getAllIds();

    @Insert
    void insertAll(People...peoples);

    @Insert
    void insert(People people);

    @Delete
    void remove(People people);
}
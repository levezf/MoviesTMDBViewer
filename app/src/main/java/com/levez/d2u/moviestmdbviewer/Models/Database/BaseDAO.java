package com.levez.d2u.moviestmdbviewer.Models.Database;

interface BaseDAO<T> {
    void insert(T t);
    void update(T t);
    void remove(T t);
    void getById(T t);
    void getAll();
}

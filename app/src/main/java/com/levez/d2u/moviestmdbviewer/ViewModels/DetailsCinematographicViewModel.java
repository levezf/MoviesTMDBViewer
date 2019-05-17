package com.levez.d2u.moviestmdbviewer.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.levez.d2u.moviestmdbviewer.Models.Repositorys.CinematographicDataRepository;
import com.levez.d2u.moviestmdbviewer.Models.Repositorys.GenresDataRepository;
import com.levez.d2u.moviestmdbviewer.Models.Repositorys.MoviesDataRepository;
import com.levez.d2u.moviestmdbviewer.Models.entity.Genre;
import com.levez.d2u.moviestmdbviewer.Models.entity.Movie;

import java.util.List;

public class DetailsCinematographicViewModel extends ViewModel {

    private CinematographicDataRepository<Movie> mRepository;

    public DetailsCinematographicViewModel() {
        mRepository = new MoviesDataRepository();
    }

    public LiveData<Movie> getDetails(int id) {
        return mRepository.getById(id);
    }

    public void clear(){
        if(mRepository!=null)
            mRepository.clear();
    }
    public void dispose(){
        if(mRepository!=null)
            mRepository.dispose();
    }
}

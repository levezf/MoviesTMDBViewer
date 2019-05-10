package com.levez.d2u.moviestmdbviewer.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.levez.d2u.moviestmdbviewer.Models.Repositorys.GenresDataRepository;
import com.levez.d2u.moviestmdbviewer.Models.entity.Genre;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GenresViewModel extends ViewModel {

    private GenresDataRepository mRepository;

    @Inject
    public GenresViewModel(GenresDataRepository repository) {
        mRepository = repository;
    }

    public LiveData<List<Genre>> getGenres(String tagType) {
        return mRepository.getGenres(tagType);
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


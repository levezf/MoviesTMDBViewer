package com.levez.d2u.moviestmdbviewer.ViewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.levez.d2u.moviestmdbviewer.Models.Repositorys.FavoriteRepository;
import com.levez.d2u.moviestmdbviewer.Models.api.Constant;
import com.levez.d2u.moviestmdbviewer.Models.entity.Cinematographic;
import com.levez.d2u.moviestmdbviewer.Models.entity.Episode;
import com.levez.d2u.moviestmdbviewer.Models.entity.Movie;
import com.levez.d2u.moviestmdbviewer.Models.entity.People;
import com.levez.d2u.moviestmdbviewer.Models.entity.TvSeries;

import java.util.List;

import javax.inject.Inject;

public class FavoriteViewModel extends ViewModel {

    private FavoriteRepository mRepository;

    @Inject
    public FavoriteViewModel(FavoriteRepository repository) {
        mRepository = repository;
    }

    public LiveData<List<Integer>> getIdsFavorites(String tagType, Context context){

        return mRepository.getFavorites(tagType, context);
    }

    public <T> void removeFavorite(Context context, T t){

        mRepository.delete(context, t);
    }

    public <T> void insertFavorite(Context context, T t) {

        mRepository.insert(context, t);

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

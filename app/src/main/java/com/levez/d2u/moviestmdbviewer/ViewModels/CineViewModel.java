package com.levez.d2u.moviestmdbviewer.ViewModels;

import androidx.lifecycle.ViewModel;

import com.levez.d2u.moviestmdbviewer.Models.Repositorys.CinematographicDataRepository;
import com.levez.d2u.moviestmdbviewer.Models.Repositorys.MoviesDataRepository;
import com.levez.d2u.moviestmdbviewer.Models.Repositorys.TvSeriesDataRepository;
import com.levez.d2u.moviestmdbviewer.Models.api.Constant;

class CineViewModel extends ViewModel {

    protected String mTagType;
    protected CinematographicDataRepository mRepository;


    public void onAttachTagType(String tagType){
        this.mTagType = tagType;
        if(tagType.equals(Constant.TAG_TYPE_MOVIE)) {
            mRepository = new MoviesDataRepository();
        }else if (tagType.equals(Constant.TAG_TYPE_TV_SERIES)){
            mRepository = new TvSeriesDataRepository();
        }
    }

    public void clear(){
        mRepository.clear();
    }
    public void dispose(){
        mRepository.dispose();
    }
}

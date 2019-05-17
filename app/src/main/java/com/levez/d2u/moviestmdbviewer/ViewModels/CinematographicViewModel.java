package com.levez.d2u.moviestmdbviewer.ViewModels;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.levez.d2u.moviestmdbviewer.Models.Repositorys.CinematographicDataRepository;
import com.levez.d2u.moviestmdbviewer.Models.Repositorys.MoviesDataRepository;
import com.levez.d2u.moviestmdbviewer.Models.Repositorys.TvSeriesDataRepository;
import com.levez.d2u.moviestmdbviewer.Models.api.Constant;
import com.levez.d2u.moviestmdbviewer.Models.entity.Cinematographic;
import com.levez.d2u.moviestmdbviewer.Models.entity.Genre;
import com.levez.d2u.moviestmdbviewer.Models.entity.MutablePagerController;

import java.util.List;


public class CinematographicViewModel extends ViewModel {


    private CinematographicDataRepository mRepository;

    private MutablePagerController mPages = new MutablePagerController();
    private String mTagType;

    public CinematographicViewModel() {

    }

    public void onAttachTagType(String tagType){
        this.mTagType = tagType;
        if(tagType.equals(Constant.TAG_TYPE_MOVIE)) {
            mRepository = new MoviesDataRepository();
        }else if (tagType.equals(Constant.TAG_TYPE_TV_SERIES)){
            mRepository = new TvSeriesDataRepository();
        }else{
            //mRepository = new DatabaseRepository();
        }
        initPages();
    }

    private void initPages() {
        mPages.add(Constant.TAG_MORE_POPULAR);
        mPages.add(Constant.TAG_VOTE_AVERAGE);
        mPages.add(Constant.TAG_VOTE_COUNT);
      //  mPages.initializeValues();
    }

    public void clear(){
        mRepository.clear();
    }
    public void dispose(){
        mRepository.dispose();
    }

    public void incrementPage(String tag){
        mPages.incrementValueByKey(tag);
    }

    public LiveData<List<Cinematographic>> getAllDiscoverByTagSort(String tag){

        switch (tag){

            case Constant.TAG_MORE_POPULAR:
                return Transformations.switchMap(mPages.getValue(tag), new Function<Integer, LiveData<List<Cinematographic>>>() {
                    @Override
                    public LiveData<List<Cinematographic>> apply(Integer page) {
                        return (mRepository.getMorePopularLiveData(page));
                    }
                });
            case Constant.TAG_VOTE_AVERAGE:
                return Transformations.switchMap(mPages.getValue(tag), new Function<Integer, LiveData<List<Cinematographic>>>() {
                    @Override
                    public LiveData<List<Cinematographic>> apply(Integer page) {
                        return mRepository.getVoteAverageLiveData(page);
                    }
                });
            case Constant.TAG_VOTE_COUNT:
                return Transformations.switchMap(mPages.getValue(tag), new Function<Integer, LiveData<List<Cinematographic>>>() {
                    @Override
                    public LiveData<List<Cinematographic>> apply(Integer page) {
                        return mRepository.getVoteCountLiveData(page);
                    }
                });
        }
        return null;
    }

    public LiveData<List<Cinematographic>> getAllByGenre(final Genre genre) {

        mPages.add(genre.getName());


        return Transformations.switchMap(mPages.getValue(genre.getName()), new Function<Integer, LiveData<List<Cinematographic>>>() {
            @Override
            public LiveData<List<Cinematographic>> apply(Integer page) {
                return mRepository.getByGenre(page, genre);
            }
        });
    }



    public LiveData<List<Cinematographic>> getMovieFavorites() {
        return null;
    }

    public LiveData<List<Cinematographic>> getSeriesFavorites() {
        return null;
    }
}

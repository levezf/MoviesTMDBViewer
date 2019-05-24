package com.levez.d2u.moviestmdbviewer.ViewModels;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.levez.d2u.moviestmdbviewer.Models.api.Constant;
import com.levez.d2u.moviestmdbviewer.Models.entity.Cinematographic;
import com.levez.d2u.moviestmdbviewer.Models.entity.Genre;
import com.levez.d2u.moviestmdbviewer.Models.entity.MutablePagerController;

import java.util.List;


public class CinematographicViewModel extends CineViewModel {

    private MutablePagerController mPages = new MutablePagerController();

    public CinematographicViewModel() {

    }

    @Override
    public void onAttachTagType(String tagType) {
        super.onAttachTagType(tagType);
        initPages();
    }

    private void initPages() {
        mPages.add(Constant.TAG_MORE_POPULAR);
        mPages.add(Constant.TAG_VOTE_AVERAGE);
        mPages.add(Constant.TAG_VOTE_COUNT);
      //  mPages.initializeValues();
    }


    public void incrementPage(String tag){
        mPages.incrementValueByKey(tag);
    }

    public LiveData<List<Cinematographic>> getAllDiscoverByTagSort(String tag){

        switch (tag){

            case Constant.TAG_MORE_POPULAR:
                return Transformations.switchMap(mPages.getValue(tag),
                        (Function<Integer, LiveData<List<Cinematographic>>>) page -> (mRepository.getMorePopularLiveData(page)));
            case Constant.TAG_VOTE_AVERAGE:
                return Transformations.switchMap(mPages.getValue(tag),
                        (Function<Integer, LiveData<List<Cinematographic>>>) page -> mRepository.getVoteAverageLiveData(page));
            case Constant.TAG_VOTE_COUNT:
                return Transformations.switchMap(mPages.getValue(tag),
                        (Function<Integer, LiveData<List<Cinematographic>>>) page -> mRepository.getVoteCountLiveData(page));
        }
        return null;
    }

    public LiveData<List<Cinematographic>> getAllByGenre(final Genre genre) {

        mPages.add(genre.getName());

        return Transformations.switchMap(mPages.getValue(genre.getName()),
                (Function<Integer, LiveData<List<Cinematographic>>>) page -> mRepository.getByGenre(page, genre));
    }



    public LiveData<List<Cinematographic>> getMovieFavorites() {
        return null;
    }

    public LiveData<List<Cinematographic>> getSeriesFavorites() {
        return null;
    }
}

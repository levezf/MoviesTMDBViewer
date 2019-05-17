package com.levez.d2u.moviestmdbviewer.Models.Repositorys;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.levez.d2u.moviestmdbviewer.Models.api.Constant;
import com.levez.d2u.moviestmdbviewer.Models.api.RetrofitConfig;
import com.levez.d2u.moviestmdbviewer.Models.entity.Genre;
import com.levez.d2u.moviestmdbviewer.Models.entity.Movie;
import com.levez.d2u.moviestmdbviewer.Models.entity.MutableListMap;
import com.levez.d2u.moviestmdbviewer.Models.entity.TvSeries;

import java.util.List;

public class TvSeriesDataRepository extends CinematographicDataRepository<TvSeries> {


    public TvSeriesDataRepository() {

    }

    @Override
    public LiveData<List<TvSeries>> getMorePopularLiveData(int page){
        return getSeries(Constant.TAG_MORE_POPULAR, Constant.SORT_BY_MORE_POPULARITY,page);

    }

    @Override
    public LiveData<List<TvSeries>> getVoteAverageLiveData(int page){
        return getSeries(Constant.TAG_VOTE_AVERAGE, Constant.SORT_BY_VOTE_AVERAGE,page);

    }

    @Override
    public LiveData<List<TvSeries>> getVoteCountLiveData(int page){
        return getSeries(Constant.TAG_VOTE_COUNT, Constant.SORT_BY_VOTE_COUNT,page);
    }

    @Override
    public LiveData<List<TvSeries>> getByGenre(int page, Genre genre) {

        mLiveDatas.put(genre.getName(), new MutableListMap());
        //noinspection ConstantConditions
        mLiveDatas.get(genre.getName()).initLiveData();

        return getCinematographic(
                RetrofitConfig
                        .getCinematographicService()
                        .getSeriesByGenre(Constant.API_KEY,
                                genre.getId(),
                                Constant.SORT_BY_MORE_POPULARITY,
                                Constant.LANGUAGE,
                                page),
                mLiveDatas.get(genre.getName())
        );
    }

    @Override
    public LiveData<TvSeries> getById(int id) {
        return null;
    }

    private LiveData<List<TvSeries>> getSeries(String TAG, String sortBy, int page){
        return getCinematographic(
                RetrofitConfig.getCinematographicService().getSeries(Constant.API_KEY,
                        sortBy,
                        Constant.LANGUAGE,
                        page),
                mLiveDatas.get(TAG));
    }

   /* @Override
    public LiveData<TvSeries> getById(int id) {

        mLiveDatas.put(String.valueOf(id), new MutableListMap());
        //noinspection ConstantConditions
        mLiveDatas.get(String.valueOf(id)).initLiveData();

        return getDetailsCinematographic(
                RetrofitConfig.getCinematographicService().getDetailsMovie(id, Constant.API_KEY, Constant.LANGUAGE,
                        Constant.APPEND_RESPONSE_DEFAULT));
    }*/
}

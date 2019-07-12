package com.levez.d2u.moviestmdbviewer.Models.Repositorys;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.levez.d2u.moviestmdbviewer.Models.api.Constant;
import com.levez.d2u.moviestmdbviewer.Models.api.RetrofitConfig;
import com.levez.d2u.moviestmdbviewer.Models.entity.Genre;
import com.levez.d2u.moviestmdbviewer.Models.entity.MutableListMap;
import com.levez.d2u.moviestmdbviewer.Models.entity.Season;
import com.levez.d2u.moviestmdbviewer.Models.entity.TvSeries;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

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

        //noinspection unchecked
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

    private LiveData<List<TvSeries>> getSeries(String TAG, String sortBy, int page){
        //noinspection unchecked
        return getCinematographic(
                RetrofitConfig.getCinematographicService().getSeries(Constant.API_KEY,
                        sortBy,
                        Constant.LANGUAGE,
                        page),
                mLiveDatas.get(TAG));
    }


    public LiveData<Season> getEpisodesBySeason(final int idSerie, final int numSeason){

        MutableLiveData<Season> liveData = new MutableLiveData<>();

        mCompositeDisposable.add(
                RetrofitConfig
                        .getCinematographicService()
                        .getSeasonAndEpisodes(idSerie, numSeason, Constant.API_KEY, Constant.LANGUAGE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .retryWhen((Observable<Throwable> errors) -> {
                            errors.subscribe(new DisposableObserver<Throwable>() {
                                @Override
                                public void onNext(Throwable throwable) {
                                    Log.e("TAGG", "ERROR: " + throwable.getMessage());

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });

                            return errors.flatMap(error -> Observable.timer(2, TimeUnit.SECONDS));
                        })
                        .subscribeWith(new DisposableObserver<Season>() {

                            Season s;

                            @Override
                            public void onNext(Season season) {
                                s = season;
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                                liveData.postValue(s);
                            }
                        })
        );
        return liveData;
    }



    @Override
    public LiveData<TvSeries> getById(int id) {

        mLiveDatas.put(String.valueOf(id), new MutableListMap());
        //noinspection ConstantConditions
        mLiveDatas.get(String.valueOf(id)).initLiveData();

        return getDetailsCinematographic(
                RetrofitConfig.getCinematographicService().getDetailsTvSeries(id, Constant.API_KEY, Constant.LANGUAGE,
                        Constant.APPEND_RESPONSE_CINEMATOGRAPHIC_DEFAULT));
    }
}

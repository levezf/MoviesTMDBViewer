package com.levez.d2u.moviestmdbviewer.Models.Repositorys;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.levez.d2u.moviestmdbviewer.Models.api.Constant;
import com.levez.d2u.moviestmdbviewer.Models.api.RetrofitConfig;
import com.levez.d2u.moviestmdbviewer.Models.api.responses.BaseResponse;
import com.levez.d2u.moviestmdbviewer.Models.api.responses.GenresResponse;
import com.levez.d2u.moviestmdbviewer.Models.entity.Cinematographic;
import com.levez.d2u.moviestmdbviewer.Models.entity.Genre;
import com.levez.d2u.moviestmdbviewer.Models.entity.MutableListMap;
import com.levez.d2u.moviestmdbviewer.Models.entity.Season;
import com.levez.d2u.moviestmdbviewer.Models.entity.TvSeries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public abstract class CinematographicDataRepository<T extends Cinematographic> extends BaseRepository{


    protected HashMap<String, MutableListMap> mLiveDatas;

    CinematographicDataRepository() {
        initLiveDatas();
    }

    private void initLiveDatas() {


        mLiveDatas = new HashMap<>();

        mLiveDatas.put(Constant.TAG_MORE_POPULAR, new MutableListMap());
        mLiveDatas.put(Constant.TAG_VOTE_AVERAGE, new MutableListMap());
        mLiveDatas.put(Constant.TAG_VOTE_COUNT, new MutableListMap());

        for (MutableListMap value : mLiveDatas.values()) {
            value.initLiveData();
        }
    }

/*

    MutableLiveData<List<T>> getCinematographic(Observable<BaseResponse<T>> observable,
                                                final MutableListMap<T> mutableListMap) {

        mutableListMap.createNewList();

        mCompositeDisposable.add(
                observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap((Function<BaseResponse<T>, Observable<T>>) cinematographicResponse ->

                                Observable.create(emitter -> {
                            try {
                                for (T item : cinematographicResponse.getResults()) {
                                    emitter.onNext(item);
                                }
                                emitter.onComplete();
                            } catch (Exception e) {
                                emitter.onError(e);
                            }
                        }))
                        .filter(t -> t.getPosterPath()!=null)

                        .subscribeWith(new DisposableObserver<T>(){

                            @Override
                            public void onNext(T t) {
                                mutableListMap.listAdd(t);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                mutableListMap.resultsLiveDataPostValue(mutableListMap.getList());
                            }
                        }));

        return mutableListMap.getResultsLiveData();
    }

*/


    MutableLiveData<List<T>> getCinematographic(Observable<BaseResponse<T>> observable,
                                                final MutableListMap<T> mutableListMap) {

        mutableListMap.createNewList();

        mCompositeDisposable.add(
                observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap((Function<BaseResponse<T>, Observable<T>>) baseResponse ->

                                Observable.create(emitter -> {
                                    try {
                                        for (T item : baseResponse.getResults()) {
                                            emitter.onNext(item);
                                        }
                                        emitter.onComplete();
                                    } catch (Exception e) {
                                        emitter.onError(e);
                                    }
                                }))
                        .filter(t -> t.getPosterPath()!=null)
                        .retryWhen(errors -> errors.flatMap(error -> Observable.timer(300, TimeUnit.MILLISECONDS)))
                        .toFlowable(BackpressureStrategy.BUFFER)
                        .onBackpressureBuffer()
                        .subscribeWith(new DisposableSubscriber<T>() {
                            @Override
                            public void onNext(T t) {
                                mutableListMap.listAdd(t);

                            }

                            @Override
                            public void onError(Throwable t) {

                            }

                            @Override
                            public void onComplete() {
                                mutableListMap.resultsLiveDataPostValue(mutableListMap.getList());

                            }
                        }));

        return mutableListMap.getResultsLiveData();
    }


    MutableLiveData<T> getDetailsCinematographic(Observable<T> observable){

        final MutableLiveData<T> mutable = new MutableLiveData<>();

        mCompositeDisposable.add(
                observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .retryWhen((Observable<Throwable> errors) -> {

                            errors.subscribe(new DisposableObserver<Throwable>() {
                                @Override
                                public void onNext(Throwable throwable) {
                                    Log.e("TAGG", "ERROR: "+ throwable.getMessage() );

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

/*
                        .flatMap(new Function<T, Observable<T>>() {
                            @Override
                            public Observable<T> apply(T baseResponse) throws Exception {
                                return Observable.create(emitter -> {
                                    try {

                                        if (baseResponse instanceof TvSeries) {

                                            TvSeries tv = (TvSeries) baseResponse;
                                            List<Season> newSeasons = new ArrayList<>();

                                            for (int i = 1; i <= tv.getSeasons().size(); i++) {

                                                */
/*TODO REALIZAR REQUEST ANINHADO*//*


                                                mCompositeDisposable.add(
                                                        RetrofitConfig
                                                                .getCinematographicService()
                                                                .getSeasonAndEpisodes(tv.getId(), i, Constant.API_KEY, Constant.LANGUAGE)
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

                                                                    return errors.flatMap(error -> Observable.timer(10, TimeUnit.SECONDS));
                                                                })
                                                                .subscribeWith(new DisposableObserver<Season>() {

                                                                    Season s;

                                                                    @Override
                                                                    public void onNext(Season season) {
                                                                        s = season;
                                                                        newSeasons.add(season);
                                                                      //  Log.d("TAGG", "Name: " + s.getName());

                                                                    }

                                                                    @Override
                                                                    public void onError(Throwable e) {

                                                                    }

                                                                    @Override
                                                                    public void onComplete() {
                                                                        tv.setSeasons(newSeasons);

                                                                        for (Season newSeason : newSeasons) {


                                                                            Log.d("SEASON", "Name: " + newSeason.getName());

                                                                        }
                                                                        Log.d("SEASON", "Size: " + newSeasons.size());


                                                                        emitter.onNext((T) tv);
                                                                        emitter.onComplete();
                                                                    }
                                                                })
                                                );

                                            }

                                        } else {
                                            emitter.onNext(baseResponse);
                                            emitter.onComplete();
                                        }

                                    } catch (Exception e) {
                                        emitter.onError(e);
                                    }
                                });
                            }
                        })
*/


                        .subscribeWith(new DisposableObserver<T>() {

                            T c;

                            @Override
                            public void onNext(T t) {
                                c = t;
//                                if(c instanceof TvSeries)
                                   // Log.d("TAGG", "onNext: " + ((TvSeries)c).getSeasons().get(0).getEpisodes().get(0).getName());

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("TAGG", "onError: "+e.getMessage() );
                            }

                            @Override
                            public void onComplete() {
                                mutable.postValue(c);
                            }
                        })
        );
        return mutable;

    }





    public abstract LiveData<List<T>> getMorePopularLiveData(int page);
    public abstract LiveData<List<T>> getVoteAverageLiveData(int page);
    public abstract LiveData<List<T>> getVoteCountLiveData(int page);
    public abstract LiveData<List<T>> getByGenre(int page, Genre id);
    public abstract LiveData<T> getById(int id);
}

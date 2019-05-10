package com.levez.d2u.moviestmdbviewer.Models.Repositorys;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.levez.d2u.moviestmdbviewer.Models.api.Constant;
import com.levez.d2u.moviestmdbviewer.Models.api.responses.CinematographicResponse;
import com.levez.d2u.moviestmdbviewer.Models.entity.Cinematographic;
import com.levez.d2u.moviestmdbviewer.Models.entity.Genre;
import com.levez.d2u.moviestmdbviewer.Models.entity.MutableListMap;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
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

    MutableLiveData<List<T>> getCinematographic(Observable<CinematographicResponse<T>> observable,
                                                final MutableListMap<T> mutableListMap) {

        mutableListMap.createNewList();

        mCompositeDisposable.add(
                observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap((Function<CinematographicResponse<T>, Observable<T>>) cinematographicResponse ->

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


    MutableLiveData<List<T>> getCinematographic(Observable<CinematographicResponse<T>> observable,
                                      final MutableListMap<T> mutableListMap) {

        mutableListMap.createNewList();

        mCompositeDisposable.add(
                observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap((Function<CinematographicResponse<T>, Observable<T>>) cinematographicResponse ->

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



    public abstract LiveData<List<T>> getMorePopularLiveData(int page);
    public abstract LiveData<List<T>> getVoteAverageLiveData(int page);
    public abstract LiveData<List<T>> getVoteCountLiveData(int page);
    public abstract LiveData<List<T>> getByGenre(int page, Genre id);
}

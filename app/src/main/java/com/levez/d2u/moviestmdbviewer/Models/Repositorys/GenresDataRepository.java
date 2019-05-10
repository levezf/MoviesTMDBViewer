package com.levez.d2u.moviestmdbviewer.Models.Repositorys;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.levez.d2u.moviestmdbviewer.Models.api.Constant;
import com.levez.d2u.moviestmdbviewer.Models.api.RetrofitConfig;
import com.levez.d2u.moviestmdbviewer.Models.api.responses.GenresResponse;
import com.levez.d2u.moviestmdbviewer.Models.entity.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class GenresDataRepository extends BaseRepository{


    private static volatile GenresDataRepository sInstance;

   /* public static GenresDataRepository getInstance() {

       *//* synchronized (GenresDataRepository.class){
            if(sInstance == null){
                sInstance = new GenresDataRepository();
            }
        }

        return sInstance;*//*

    }*/


    @Inject
    public GenresDataRepository() {
    }

    private MutableLiveData<List<Genre>> getGenresMutable(Observable<GenresResponse> observable){

        final MutableLiveData<List<Genre>> mutable = new MutableLiveData<>();

        mCompositeDisposable.add(
                observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .retryWhen(errors -> errors.flatMap(error -> Observable.timer(1, TimeUnit.SECONDS)))
                        .subscribeWith(new DisposableObserver<GenresResponse>() {

                            List<Genre> genres  = new ArrayList<>();

                            @Override
                            public void onNext(GenresResponse genresResponse) {
                                genres = genresResponse.getGenres();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                mutable.postValue(genres);
                            }
                        })
        );
        return mutable;
    }

    public LiveData<List<Genre>> getGenres(String tagType){

        return getGenresMutable(
                RetrofitConfig.getGenresService().getGenres(tagType, Constant.API_KEY));
    }



}

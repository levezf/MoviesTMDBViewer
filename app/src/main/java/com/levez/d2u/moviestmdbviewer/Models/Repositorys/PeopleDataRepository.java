package com.levez.d2u.moviestmdbviewer.Models.Repositorys;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.levez.d2u.moviestmdbviewer.Models.api.Constant;
import com.levez.d2u.moviestmdbviewer.Models.api.RetrofitConfig;
import com.levez.d2u.moviestmdbviewer.Models.api.responses.GenresResponse;
import com.levez.d2u.moviestmdbviewer.Models.entity.Genre;
import com.levez.d2u.moviestmdbviewer.Models.entity.People;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class PeopleDataRepository extends BaseRepository {

    private MutableLiveData<People> getPeopleMutable(Observable<People> observable){

        final MutableLiveData<People> mutable = new MutableLiveData<>();

        mCompositeDisposable.add(
                observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .retryWhen(errors -> errors.flatMap(error -> Observable.timer(1, TimeUnit.SECONDS)))
                        .subscribeWith(new DisposableObserver<People>() {

                            People people;

                            @Override
                            public void onNext(People p) {
                                people = p;
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                mutable.postValue(people);
                            }
                        })
        );
        return mutable;
    }

    public LiveData<People> getPeopleById(int idPeople){

        return getPeopleMutable(
                RetrofitConfig.getPeopleService().getPeopleById(idPeople, Constant.API_KEY, Constant.LANGUAGE, Constant.APPEND_RESPONSE_PEOPLE_DEFAULT));
    }

}

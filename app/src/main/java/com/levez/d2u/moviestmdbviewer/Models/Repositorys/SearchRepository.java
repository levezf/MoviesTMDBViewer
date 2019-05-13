package com.levez.d2u.moviestmdbviewer.Models.Repositorys;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.levez.d2u.moviestmdbviewer.Models.api.Constant;
import com.levez.d2u.moviestmdbviewer.Models.api.RetrofitConfig;
import com.levez.d2u.moviestmdbviewer.Models.api.responses.BaseResponse;
import com.levez.d2u.moviestmdbviewer.Models.entity.Cinematographic;
import com.levez.d2u.moviestmdbviewer.Models.entity.Movie;
import com.levez.d2u.moviestmdbviewer.Models.entity.People;
import com.levez.d2u.moviestmdbviewer.Models.entity.Searchable;
import com.levez.d2u.moviestmdbviewer.Models.entity.TvSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SearchRepository extends BaseRepository{

    public SearchRepository() { }


    public LiveData<List<Searchable>> getSearch(String query, int page){

        return getSearchMutable(
                getObservableMovie(query, page),
                getObservableTvSeries(query, page),
                getObservablePeople(query, page));
    }


    private MutableLiveData<List<Searchable>> getSearchMutable(Observable<BaseResponse<Movie>> observableMovies,
                                                               Observable<BaseResponse<TvSeries>> observableTvSeries,
                                                               Observable<BaseResponse<People>> observablePeople){
        final MutableLiveData<List<Searchable>> mutable = new MutableLiveData<>();



        mCompositeDisposable.add(
                observableMovies
                        .zipWith(observableTvSeries, (BiFunction<BaseResponse<Movie>, BaseResponse<TvSeries>, Object>) (movieBaseResponse, tvSeriesBaseResponse) -> {

                            List<Searchable> results = new ArrayList<>();

                            results.addAll(movieBaseResponse.getResults());
                            results.addAll(tvSeriesBaseResponse.getResults());

                            return results;
                        })
                        .zipWith(observablePeople, (BiFunction<Object, BaseResponse<People>, Object>) (o, peopleBaseResponse) -> {

                            List<Searchable> results = ((List<Searchable>) o);

                            results.addAll(peopleBaseResponse.getResults());

                            return results;

                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap((Function<Object, Observable<Searchable>>) o -> Observable.create(emitter -> {
                            try {
                                for (Searchable item : (List<Searchable>) o) {
                                    emitter.onNext(item);
                                }
                                emitter.onComplete();
                            } catch (Exception e) {
                                emitter.onError(e);
                            }
                        }))
                        .filter(searchable -> {

                            if(searchable instanceof Cinematographic){
                                return ((Cinematographic) searchable).getPosterPath()!=null;
                            }else {
                                return ((People) searchable).getProfilePath()!=null;
                            }

                        })
                        .retryWhen(errors -> errors.flatMap(error -> Observable.timer(1, TimeUnit.SECONDS)))
                        .subscribeWith(new DisposableObserver<Searchable>() {
                            List<Searchable> results  = new ArrayList<>();
                            @Override
                            public void onNext(Searchable s) {
                                results.add(s);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                mutable.postValue(results);
                            }
                        })
        );

        return mutable;
    }

    private Observable<BaseResponse<Movie>> getObservableMovie(String query, int page){
        return RetrofitConfig.getSearchService().getMovieSearch(
                Constant.API_KEY,
                Constant.LANGUAGE,
                query,
                Constant.INCLUDE_ADULT,
                page);
    }


    private Observable<BaseResponse<TvSeries>> getObservableTvSeries(String query, int page){
        return RetrofitConfig.getSearchService().getTvSearch(
                Constant.API_KEY,
                Constant.LANGUAGE,
                query,
                Constant.INCLUDE_ADULT,
                page);
    }


    private Observable<BaseResponse<People>> getObservablePeople(String query, int page){
        return RetrofitConfig.getSearchService().getPeopleSearch(
                Constant.API_KEY,
                Constant.LANGUAGE,
                query,
                Constant.INCLUDE_ADULT,
                page);
    }

}

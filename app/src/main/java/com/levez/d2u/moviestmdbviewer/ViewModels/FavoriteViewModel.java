package com.levez.d2u.moviestmdbviewer.ViewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.levez.d2u.moviestmdbviewer.Models.Repositorys.FavoriteRepository;
import com.levez.d2u.moviestmdbviewer.Models.Repositorys.MoviesDataRepository;
import com.levez.d2u.moviestmdbviewer.Models.Repositorys.PeopleDataRepository;
import com.levez.d2u.moviestmdbviewer.Models.Repositorys.TvSeriesDataRepository;
import com.levez.d2u.moviestmdbviewer.Models.entity.Movie;
import com.levez.d2u.moviestmdbviewer.Models.entity.People;
import com.levez.d2u.moviestmdbviewer.Models.entity.TvSeries;

import java.util.List;

import javax.inject.Inject;

public class FavoriteViewModel extends ViewModel {

    private FavoriteRepository mRepository;
    private MoviesDataRepository mMoviesRepository;
    private TvSeriesDataRepository mTvSeriesRepository;
    private PeopleDataRepository mPeopleRepository;

    @Inject
    public FavoriteViewModel(FavoriteRepository repository) {
        mRepository = repository;
    }

    public LiveData<List<Integer>> getIdsFavorites(String tagType, Context context){

        return mRepository.getFavorites(tagType, context);
    }

    public LiveData<List<Integer>> getEpisodesFavorites(int idSerie, Context context){

        return mRepository.getFavoritesEpisode(idSerie, context);
    }

    public <T> void removeFavorite(Context context, T t){

        mRepository.delete(context, t);
    }

    public <T> void insertFavorite(Context context, T t) {

        mRepository.insert(context, t);

    }

    public LiveData<Movie> getMovieById(int idMovie){

        if(mMoviesRepository==null)
            mMoviesRepository = new MoviesDataRepository();

        return mMoviesRepository.getById(idMovie);
    }


    public LiveData<TvSeries> getTvSeriesById(int idSeries){

        if(mTvSeriesRepository==null)
            mTvSeriesRepository = new TvSeriesDataRepository();

        return mTvSeriesRepository.getById(idSeries);
    }

    public LiveData<People> getPeopleById(int idPeople){

        if(mPeopleRepository==null)
            mPeopleRepository = new PeopleDataRepository();

        return mPeopleRepository.getPeopleById(idPeople);
    }

    public void clear(){
        if(mRepository!=null)
            mRepository.clear();
    }
    public void dispose(){
        if(mRepository!=null)
            mRepository.dispose();
    }


}

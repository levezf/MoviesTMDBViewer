package com.levez.d2u.moviestmdbviewer.Models.Repositorys;

import androidx.lifecycle.LiveData;

import com.levez.d2u.moviestmdbviewer.Models.api.Constant;
import com.levez.d2u.moviestmdbviewer.Models.api.RetrofitConfig;
import com.levez.d2u.moviestmdbviewer.Models.entity.Genre;
import com.levez.d2u.moviestmdbviewer.Models.entity.Movie;
import com.levez.d2u.moviestmdbviewer.Models.entity.MutableListMap;

import java.util.List;


public class MoviesDataRepository extends CinematographicDataRepository<Movie> {



    public MoviesDataRepository() {
    }

    @Override
    public LiveData<List<Movie>> getMorePopularLiveData(int page){
        return getDiscoverMovies(Constant.TAG_MORE_POPULAR, Constant.SORT_BY_MORE_POPULARITY,page);

    }

    @Override
    public LiveData<List<Movie>> getVoteAverageLiveData(int page){
        return getDiscoverMovies(Constant.TAG_VOTE_AVERAGE, Constant.SORT_BY_VOTE_AVERAGE,page);

    }

    @Override
    public LiveData<List<Movie>> getVoteCountLiveData(int page){
        return getDiscoverMovies(Constant.TAG_VOTE_COUNT, Constant.SORT_BY_VOTE_COUNT,page);
    }


    @Override
    public LiveData<List<Movie>> getByGenre(int page, Genre genre) {

        mLiveDatas.put(genre.getName(), new MutableListMap());

        //noinspection ConstantConditions
        mLiveDatas.get(genre.getName()).initLiveData();

        return getCinematographic(
                RetrofitConfig
                        .getCinematographicService()
                        .getMoviesByGenre(Constant.API_KEY, genre.getId(), Constant.SORT_BY_MORE_POPULARITY, page),
                mLiveDatas.get(genre.getName())
        );
    }


    private LiveData<List<Movie>> getDiscoverMovies(String TAG, String sortBy, int page){
        return getCinematographic(
                RetrofitConfig.getCinematographicService().getMovies(Constant.API_KEY, sortBy, page),
                mLiveDatas.get(TAG));
    }


}

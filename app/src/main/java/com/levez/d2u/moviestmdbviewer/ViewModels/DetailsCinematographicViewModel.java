package com.levez.d2u.moviestmdbviewer.ViewModels;

import androidx.lifecycle.LiveData;

import com.levez.d2u.moviestmdbviewer.Models.Repositorys.CinematographicDataRepository;
import com.levez.d2u.moviestmdbviewer.Models.Repositorys.TvSeriesDataRepository;
import com.levez.d2u.moviestmdbviewer.Models.entity.Cinematographic;
import com.levez.d2u.moviestmdbviewer.Models.entity.Movie;
import com.levez.d2u.moviestmdbviewer.Models.entity.Season;

public class DetailsCinematographicViewModel extends CineViewModel {


    public DetailsCinematographicViewModel() { }

    public LiveData<Cinematographic> getDetails(int id) {
        return mRepository.getById(id);
    }

    public LiveData<Season> getSeasonsAndEpisodes(Integer idSerie, Integer seasonNumber) {
        return ((TvSeriesDataRepository)mRepository).getEpisodesBySeason(idSerie, seasonNumber);
    }
}

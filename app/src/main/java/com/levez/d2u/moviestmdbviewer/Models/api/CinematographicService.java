package com.levez.d2u.moviestmdbviewer.Models.api;

import com.levez.d2u.moviestmdbviewer.Models.api.responses.BaseResponse;
import com.levez.d2u.moviestmdbviewer.Models.entity.Movie;
import com.levez.d2u.moviestmdbviewer.Models.entity.Season;
import com.levez.d2u.moviestmdbviewer.Models.entity.TvSeries;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CinematographicService {

    /*---------------------Movies------------------------*/

    @GET("discover/movie")
    Observable<BaseResponse<Movie>> getMovies(
            @Query("api_key") String apiKey,
            @Query("sort_by") String sortBy, // { vote_average, popularity, vote_count }.desc
            @Query("language") String language,
            @Query("page") int page);

    @GET("discover/movie")
    Observable<BaseResponse<Movie>> getMoviesByGenre(
            @Query("api_key") String apiKey,
            @Query("with_genres") Integer id,
            @Query("sort_by") String tag,
            @Query("language") String language,
            @Query("page") int page);


    @GET("movie/{id}")
    Observable<Movie> getDetailsMovie(
            @Path("id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("append_to_response") String append);



    /*-------------------TV Series----------------------*/

    @GET("discover/tv")
    Observable<BaseResponse<TvSeries>> getSeries(
            @Query("api_key") String apiKey,
            @Query("sort_by") String sortBy, // { vote_average, popularity, vote_count }.desc
            @Query("language") String language,
            @Query("page") int page);

    @GET("discover/tv")
    Observable<BaseResponse<TvSeries>> getSeriesByGenre(
            @Query("api_key") String apiKey,
            @Query("with_genres") Integer id,
            @Query("sort_by") String tag,
            @Query("language") String language,
            @Query("page") int page);

    @GET("tv/{id}")
    Observable<TvSeries> getDetailsTvSeries(
            @Path("id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("append_to_response") String append);

    @GET("tv/{idSerie}/season/{season_number}")
    Observable<Season> getSeasonAndEpisodes(
            @Path("idSerie") int idSerie,
            @Path("season_number") int seasonNumber,
            @Query("api_key") String apiKey,
            @Query("language") String language);
}

package com.levez.d2u.moviestmdbviewer.Models.api;

import com.levez.d2u.moviestmdbviewer.Models.api.responses.BaseResponse;
import com.levez.d2u.moviestmdbviewer.Models.entity.Movie;
import com.levez.d2u.moviestmdbviewer.Models.entity.People;
import com.levez.d2u.moviestmdbviewer.Models.entity.TvSeries;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchService {

    @GET("search/movie")
    Observable<BaseResponse<Movie>> getMovieSearch(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("query") String query,
            @Query("include_adult") boolean adult,
            @Query("page") int page
    );

    @GET("search/tv")
    Observable<BaseResponse<TvSeries>> getTvSearch(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("query") String query,
            @Query("include_adult") boolean adult,
            @Query("page") int page
    );

    @GET("search/person")
    Observable<BaseResponse<People>> getPeopleSearch(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("query") String query,
            @Query("include_adult") boolean adult,
            @Query("page") int page
    );

}

package com.levez.d2u.moviestmdbviewer.Models.api;

import com.levez.d2u.moviestmdbviewer.Models.api.responses.GenresResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GenresService {

    @GET("genre/{tagType}/list")
    Observable<GenresResponse> getGenres(
            @Path("tagType") String tagType,
            @Query("api_key") String apiKey
    );

}

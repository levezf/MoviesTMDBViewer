package com.levez.d2u.moviestmdbviewer.Models.api;

import com.levez.d2u.moviestmdbviewer.Models.entity.People;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PeopleService {

    @GET("person/{idPeople}")
    Observable<People> getPeopleById(
            @Path("idPeople") int tagType,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("append_to_response") String appendResponse
    );
}
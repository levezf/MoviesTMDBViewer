package com.levez.d2u.moviestmdbviewer.Models.api;

public class Constant {

    public static final String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500";
    public static final String BASE_URL_YOUTUBE = "http://www.youtube.com/watch?v=";

    public static final String SITE_VIDEOS = "YouTube";

    public static final String DATABASE_NAME = "database_closeup";
    public static final int DATABASE_VERSION = 1;


    static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY = "8ae03112f333d6386161cffe6268c009";
    public static final String APPEND_RESPONSE_CINEMATOGRAPHIC_DEFAULT = "videos,similar,credits";
    public static final String APPEND_RESPONSE_PEOPLE_DEFAULT = "tv_credits,movie_credits";

    public static final String LANGUAGE = "en-US";

    public static final String SORT_BY_MORE_POPULARITY = "popularity.desc";
    public static final String SORT_BY_VOTE_AVERAGE = "vote_average.desc";
    public static final String SORT_BY_VOTE_COUNT = "vote_count.desc";

    public static final String TAG_MORE_POPULAR ="TAG_MORE_POPULAR";
    public static final String TAG_VOTE_AVERAGE = "TAG_VOTE_AVERAGE";
    public static final String TAG_VOTE_COUNT = "TAG_VOTE_COUNT";
    public static final String FAVORITES = "Favorites";

    public static final String TAG_TYPE_TV_SERIES = "tv";
    public static final String TAG_TYPE_EPISODE = "episode";

    public static final String TAG_TYPE_MOVIE = "movie";
    public static final String TAG_TYPE_PEOPLE = "person";

    public static final boolean INCLUDE_ADULT = false;


    public static final String TAG_FRAG_DETAILS_CINEMATOGRAPHIC = "tag_fragment_details_cinematographic";
    public static final String TAG_FRAG_MOVIE = "tag_fragment_movies";
    public static final String TAG_FRAG_SEARCH = "tag_fragment_search";
    public static final String TAG_FRAG_TV_SERIES = "tag_fragment_tv_series";
    public static final String TAG_FRAG_FAVORITES = "tag_fragment_favorites";
    public static final String TAG_FRAG_DETAILS_PEOPLE = "tag_fragment_details_people";

}

package com.levez.d2u.moviestmdbviewer.Models.api;

import android.os.CountDownTimer;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.levez.d2u.moviestmdbviewer.Models.entity.People;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {


    private static volatile Retrofit mRetrofit;

    public RetrofitConfig() {
        throw new  RuntimeException("Use getCinematographicService() or getGenresService()");
    }


    private static <T> T getService(Class<T> service) {


        synchronized (RetrofitConfig.class){


            if(mRetrofit==null){


                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

                OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                        .connectTimeout(30, TimeUnit.MINUTES)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)
                        .addInterceptor(httpLoggingInterceptor)
                        .build();

                mRetrofit = new Retrofit
                        .Builder()
                        .baseUrl(Constant.BASE_URL)
                        .client(okHttpClient)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        }

        return mRetrofit.create(service);
    }

    public static CinematographicService getCinematographicService(){
        return getService(CinematographicService.class);
    }

    public static GenresService getGenresService(){
        return getService(GenresService.class);
    }

    public static SearchService getSearchService(){
        return getService(SearchService.class);
    }

    public static PeopleService getPeopleService(){
        return getService(PeopleService.class);
    }

}

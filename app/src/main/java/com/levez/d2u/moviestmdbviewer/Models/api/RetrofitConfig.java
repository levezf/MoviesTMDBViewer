package com.levez.d2u.moviestmdbviewer.Models.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.levez.d2u.moviestmdbviewer.Dependency.MyApp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
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
                long cacheSize = ( 5 * 1024 * 1024 );
                Cache cache = new Cache(MyApp.getInstance().getCacheDir(), cacheSize);

                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

                OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                        .connectTimeout(30, TimeUnit.MINUTES)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)
                        .cache(cache)
                        .addInterceptor(httpLoggingInterceptor)
                        .addInterceptor(chain -> {

                            Request request = chain.request();

                            if (hasNetwork(MyApp.getInstance())) {
                                //utiliza um cache de no max 1h
                                request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60 * 60).build();
                            }else {
                                //vê se tem cache de até 1 semana
                                request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                            }

                            return chain.proceed(request);
                        })
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


    private static boolean hasNetwork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnected());
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

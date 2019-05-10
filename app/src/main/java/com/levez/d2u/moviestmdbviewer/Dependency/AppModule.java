package com.levez.d2u.moviestmdbviewer.Dependency;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;

import com.levez.d2u.moviestmdbviewer.Models.Repositorys.GenresDataRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Singleton @Provides
    Application providesApplication() {
        return application;
    }

    @Singleton @Provides
    Context providesContext() {
        return application.getApplicationContext();
    }

    @Provides
    ContentResolver providesContentResolver(Application app){
        return app.getContentResolver();
    }

    @Singleton @Provides
    GenresDataRepository providesGenreDataRepository() {
        return new GenresDataRepository();
    }

}
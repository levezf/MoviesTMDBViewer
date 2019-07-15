package com.levez.d2u.moviestmdbviewer.Dependency;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.levez.d2u.moviestmdbviewer.Models.api.Constant;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/*
* Colocar mensagem de q o dispositivo esta sem internet e pode não carregar algumas coisas, e pedir para conectar a internet
* Adicionar tamanhos diferentes para tablet
* Melhorar os gargalos na hora de carregar filmes
* Alterar icones bugados
* Adicionar logo
* Adicionar repartição de mais detalhes
* */
public class MyApp extends Application implements HasActivityInjector
{

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    private static MyApp sInstance;

    private AppComponent appComponent;

    public static Context getInstance() {
        return sInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        appComponent = DaggerAppComponent.builder()
                .application(this)
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);
//        DaggerGenreComponent.builder().build();
    }


    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

}

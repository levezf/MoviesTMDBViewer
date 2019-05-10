package com.levez.d2u.moviestmdbviewer.Dependency;

import com.levez.d2u.moviestmdbviewer.View.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = FragmentBuilder.class)
    abstract MainActivity bindMainActivity();

}
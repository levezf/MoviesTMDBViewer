package com.levez.d2u.moviestmdbviewer.Dependency;

import com.levez.d2u.moviestmdbviewer.View.CinematographicFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract CinematographicFragment contributeCinematographicFragment();

}
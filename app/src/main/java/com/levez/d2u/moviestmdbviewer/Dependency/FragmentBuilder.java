package com.levez.d2u.moviestmdbviewer.Dependency;

import com.levez.d2u.moviestmdbviewer.View.CinematographicFragment;
import com.levez.d2u.moviestmdbviewer.View.DetailsCinematographicFragment;
import com.levez.d2u.moviestmdbviewer.View.DetailsPeopleFragment;
import com.levez.d2u.moviestmdbviewer.View.FavoriteFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract CinematographicFragment contributeCinematographicFragment();

    @ContributesAndroidInjector
    abstract DetailsCinematographicFragment contributeDetailsCinematographicFragment();

    @ContributesAndroidInjector
    abstract DetailsPeopleFragment contributeDetailsPeopleFragment();

    @ContributesAndroidInjector
    abstract FavoriteFragment contributeFavoriteFragment();

}
package com.levez.d2u.moviestmdbviewer.Dependency;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.levez.d2u.moviestmdbviewer.ViewModels.GenresViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


@Module
abstract class ViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(DaggerViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(GenresViewModel.class)
    abstract ViewModel provideGenresViewModel(GenresViewModel genresViewModel);
}
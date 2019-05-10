package com.levez.d2u.moviestmdbviewer.Models.Repositorys;


import io.reactivex.disposables.CompositeDisposable;

public class BaseRepository {

    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public BaseRepository() {
    }

    public void clear(){
        mCompositeDisposable.clear();
    }

    public void dispose(){
        if(!mCompositeDisposable.isDisposed())
            mCompositeDisposable.dispose();
    }

}

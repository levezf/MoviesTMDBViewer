package com.levez.d2u.moviestmdbviewer.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.levez.d2u.moviestmdbviewer.Models.Repositorys.SearchRepository;
import com.levez.d2u.moviestmdbviewer.Models.entity.Searchable;

import java.util.List;


public class SearchViewModel extends ViewModel {


    private SearchRepository mRepository;
    private MutableLiveData<Integer> mPage =  new MutableLiveData<>();
    private MutableLiveData<String> mQuery = new MutableLiveData<>();


    public SearchViewModel() {

        mRepository = new SearchRepository();
        mPage.postValue(1);

    }

    public void incrementPage(){

        if (mPage.getValue()!=null)
            mPage.postValue( mPage.getValue()+1 );

    }

    public LiveData<List<Searchable>> getSearch() {

        return Transformations.switchMap(mPage, this::performSearch);
    }

    public void setQuery(String query) {

        mQuery.postValue(query);
    }

    private LiveData<List<Searchable>> performSearch(int page){

        return  Transformations.switchMap(mQuery, query -> mRepository.getSearch(query, page));

    }


    public void clear(){
        mRepository.clear();
    }
    public void dispose(){
        mRepository.dispose();
    }
}

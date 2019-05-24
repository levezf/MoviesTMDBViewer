package com.levez.d2u.moviestmdbviewer.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.levez.d2u.moviestmdbviewer.Models.Repositorys.PeopleDataRepository;
import com.levez.d2u.moviestmdbviewer.Models.entity.People;

public class DetailsPeopleViewModel  extends ViewModel {

    private PeopleDataRepository mRepository;

    public DetailsPeopleViewModel() {
        mRepository = new PeopleDataRepository();
    }

    public LiveData<People> getDetails(int id) {
        return mRepository.getPeopleById(id);
    }

    public void clear(){
        mRepository.clear();
    }
    public void dispose(){
        mRepository.dispose();
    }

}

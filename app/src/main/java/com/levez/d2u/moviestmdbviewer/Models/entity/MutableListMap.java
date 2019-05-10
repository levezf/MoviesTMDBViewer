package com.levez.d2u.moviestmdbviewer.Models.entity;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class MutableListMap<T extends Cinematographic> {

    private MutableLiveData<List<T>> resultsLiveData;
    private List<T> list = new ArrayList<>();

    public void initLiveData(){
        resultsLiveData = new MutableLiveData<>();
    }

    public List<T> getList() {
        return list;
    }

    public void createNewList() {
        list = new ArrayList<>();
    }

    public void listAdd(T t) {
        if (list != null){
            list.add(t);
        }
    }

    public void resultsLiveDataPostValue(List<T> list) {
        resultsLiveData.postValue(list);
    }

    public MutableLiveData<List<T>> getResultsLiveData() {
        return resultsLiveData;
    }
}

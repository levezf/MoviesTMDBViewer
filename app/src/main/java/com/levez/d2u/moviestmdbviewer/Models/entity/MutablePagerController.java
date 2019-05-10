package com.levez.d2u.moviestmdbviewer.Models.entity;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class MutablePagerController {

    private ArrayList<String> keys = new ArrayList<>();
    private ArrayList<MutableLiveData<Integer>> values = new ArrayList<>();

    public void add(String key){
        keys.add(key);
        values.add(new MutableLiveData<Integer>());
        values.get(values.size()-1).setValue(1);
    }

    public void remove(String key){
        int index = keys.indexOf(key);
        keys.remove(index);
        values.remove(index);
    }

    public void remove(int index){
        keys.remove(index);
        values.remove(index);
    }

    public boolean contains(String key){
        return keys.contains(key);
    }

    public void incrementValueByKey(String key){
        int index = keys.indexOf(key);
        assert values.get(index).getValue()!= null;
        //noinspection ConstantConditions
        values.get(index).postValue(values.get(index).getValue() + 1);

    }

    public MutableLiveData<Integer> getValue(String key) {
        int index = keys.indexOf(key);
        return values.get(index);
    }

    public void initializeValues(){
        for (MutableLiveData<Integer> value : values) {
            value.setValue(1);
        }
    }
}

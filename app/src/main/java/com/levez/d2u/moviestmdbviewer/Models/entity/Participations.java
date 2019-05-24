package com.levez.d2u.moviestmdbviewer.Models.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Participations<T extends Cinematographic> implements Parcelable {

    @SerializedName("cast")
    @Expose
    private List<T> cast = new ArrayList<>();
    @SerializedName("crew")
    @Expose
    private List<T> crew =  new ArrayList<>();

    public final static Creator<Participations> CREATOR = new Creator<Participations>() {


        public Participations createFromParcel(Parcel in) {
            return new Participations(in);
        }

        public Participations[] newArray(int size) {
            return (new Participations[size]);
        }

    };

    private Participations(Parcel in) {

        ClassLoader  classLoader;

        if(cast.get(0) instanceof Movie){
            classLoader = Movie.class.getClassLoader();
        }else{
            classLoader = TvSeries.class.getClassLoader();
        }

        in.readList(this.cast, (classLoader));
        in.readList(this.crew, (classLoader));
    }

    public Participations() {
    }

    public List<T> getParticipations(){

        List<T> all = new ArrayList<>();
        all.addAll(cast);
        all.addAll(crew);
        return all;
    }

    public void writeToParcel(Parcel dest, int flags) {

        dest.writeList(cast);
        dest.writeList(crew);
    }

    public int describeContents() {
        return 0;
    }

}

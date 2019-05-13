package com.levez.d2u.moviestmdbviewer.Models.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class People extends Searchable implements Parcelable
{

    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("profile_path")
    @Expose
    private String profilePath;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("known_for")
    @Expose
    private List<Cinematographic> knownFor = null;
    @SerializedName("adult")
    @Expose
    private Boolean adult;



    public final static Parcelable.Creator<People> CREATOR = new Creator<People>() {

        public People createFromParcel(Parcel in) {
            return new People(in);
        }

        public People[] newArray(int size) {
            return (new People[size]);
        }

    };

    protected People(Parcel in) {
        this.popularity = ((Double) in.readValue((Double.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.profilePath = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.knownFor, (java.lang.Object.class.getClassLoader()));
        this.adult = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public People() {
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Cinematographic> getKnownFor() {
        return knownFor;
    }

    public void setKnownFor(List<Cinematographic> knownFor) {
        this.knownFor = knownFor;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(popularity);
        dest.writeValue(id);
        dest.writeValue(profilePath);
        dest.writeValue(name);
        dest.writeList(knownFor);
        dest.writeValue(adult);
    }

    public int describeContents() {
        return 0;
    }

}
package com.levez.d2u.moviestmdbviewer.Models.entity;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Credits implements Parcelable
{

    @SerializedName("cast")
    @Expose
    private List<Cast> cast = null;
    @SerializedName("crew")
    @Expose
    private List<Crew> crew = null;

    public final static Parcelable.Creator<Credits> CREATOR = new Creator<Credits>() {


        public Credits createFromParcel(Parcel in) {
            return new Credits(in);
        }

        public Credits[] newArray(int size) {
            return (new Credits[size]);
        }

    };

    private Credits(Parcel in) {
        in.readList(this.cast, (Cast.class.getClassLoader()));
        in.readList(this.crew, (Crew.class.getClassLoader()));
    }

    public Credits() {
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

    public void writeToParcel(Parcel dest, int flags) {

        dest.writeList(cast);
        dest.writeList(crew);
    }

    public int describeContents() {
        return 0;
    }

}
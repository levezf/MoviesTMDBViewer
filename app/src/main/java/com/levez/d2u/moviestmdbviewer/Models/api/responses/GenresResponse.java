package com.levez.d2u.moviestmdbviewer.Models.api.responses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.levez.d2u.moviestmdbviewer.Models.entity.Genre;

import java.util.ArrayList;
import java.util.List;

public class GenresResponse implements Parcelable
{

    @SerializedName("genres")
    @Expose
    private List<Genre> genres = new ArrayList<>();
    public final static Parcelable.Creator<GenresResponse> CREATOR = new Creator<GenresResponse>() {

        public GenresResponse createFromParcel(Parcel in) {
            return new GenresResponse(in);
        }

        public GenresResponse[] newArray(int size) {
            return (new GenresResponse[size]);
        }

    };

    private GenresResponse(Parcel in) {
        in.readList(this.genres, (Genre.class.getClassLoader()));
    }

    public GenresResponse() {
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(genres);
    }

    public int describeContents() {
        return 0;
    }


}
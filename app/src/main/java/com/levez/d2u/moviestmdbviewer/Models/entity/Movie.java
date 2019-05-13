package com.levez.d2u.moviestmdbviewer.Models.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie extends Cinematographic implements Parcelable {

    @SerializedName("video")
    @Expose
    private Boolean video;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("original_title")
    @Expose
    private String originalTitle;

    @SerializedName("adult")
    @Expose
    private Boolean adult;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    public final static Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return (new Movie[size]);
        }

    };

    Movie(Parcel in) {
        super(in);
        this.video = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.originalTitle = ((String) in.readValue((String.class.getClassLoader())));
        this.adult = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.releaseDate = ((String) in.readValue((String.class.getClassLoader())));
    }


    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(video);
        dest.writeValue(title);
        dest.writeValue(originalTitle);
        dest.writeValue(adult);
        dest.writeValue(releaseDate);
    }

    public int describeContents() {
        return 0;
    }

}
package com.levez.d2u.moviestmdbviewer.Models.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.levez.d2u.moviestmdbviewer.Models.api.responses.BaseResponse;
import com.levez.d2u.moviestmdbviewer.Models.api.responses.VideoResponse;

import java.util.ArrayList;
import java.util.List;

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

    @SerializedName("production_countries")
    @Expose
    private List<ProductionCountry> productionCountries;

    @SerializedName("similar")
    @Expose
    private BaseResponse<Movie> similarResponse;

    public Movie() {
        super();
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

    public List<ProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(List<ProductionCountry> productionCountries) {
        this.productionCountries = productionCountries;
    }
    public List<Movie> getSimilar() {
        return similarResponse!=null?similarResponse.getResults(): new ArrayList<>();
    }

    public void setSimilar(List<Movie> similar) {
        if(similarResponse!=null){
            similarResponse.setResults(similar);
        }else{
            similarResponse = new BaseResponse<>();
            similarResponse.setResults(similar);
        }
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    private Movie(Parcel in) {
        super(in);
        byte videoVal = in.readByte();
        video = videoVal == 0x02 ? null : videoVal != 0x00;
        title = in.readString();
        originalTitle = in.readString();
        byte adultVal = in.readByte();
        adult = adultVal == 0x02 ? null : adultVal != 0x00;
        releaseDate = in.readString();
        if (in.readByte() == 0x01) {
            productionCountries = new ArrayList<>();
            in.readList(productionCountries, ProductionCountry.class.getClassLoader());
        } else {
            productionCountries = null;
        }
        if (in.readByte() == 0x01) {
            similarResponse = in.readParcelable(VideoResponse.class.getClassLoader());
        } else {
            similarResponse = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);

        if (video == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (video ? 0x01 : 0x00));
        }
        dest.writeString(title);
        dest.writeString(originalTitle);
        if (adult == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (adult ? 0x01 : 0x00));
        }
        dest.writeString(releaseDate);
        if (productionCountries == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(productionCountries);
        }
        if (similarResponse == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeParcelable(similarResponse, flags);
        }
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}
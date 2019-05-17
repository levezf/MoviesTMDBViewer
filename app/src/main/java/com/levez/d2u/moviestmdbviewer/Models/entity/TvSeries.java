package com.levez.d2u.moviestmdbviewer.Models.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.levez.d2u.moviestmdbviewer.Models.api.responses.BaseResponse;
import com.levez.d2u.moviestmdbviewer.Models.api.responses.VideoResponse;

import java.util.ArrayList;
import java.util.List;


public class TvSeries extends Cinematographic implements Parcelable {

    @SerializedName("original_name")
    @Expose
    private String originalName;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("origin_country")
    @Expose
    private List<String> originCountry = new ArrayList<>();

    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;

    @SerializedName("similar")
    @Expose
    private BaseResponse<TvSeries> similarResponse;

    public final static Parcelable.Creator<TvSeries> CREATOR = new Creator<TvSeries>() {

        public TvSeries createFromParcel(Parcel in) {
            return new TvSeries(in);
        }

        public TvSeries[] newArray(int size) {
            return (new TvSeries[size]);
        }

    };

    private TvSeries(Parcel in) {
        super(in);
        this.originalName = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.firstAirDate = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.originCountry, (java.lang.String.class.getClassLoader()));
        if (in.readByte() == 0x01) {
            similarResponse = in.readParcelable(VideoResponse.class.getClassLoader());
        } else {
            similarResponse = null;
        }
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
    }

    public List<TvSeries> getSimilar() {
        return similarResponse!=null?similarResponse.getResults(): new ArrayList<>();
    }

    public void setSimilar(List<TvSeries> similar) {
        if(similarResponse!=null){
            similarResponse.setResults(similar);
        }else{
            similarResponse = new BaseResponse<>();
            similarResponse.setResults(similar);
        }
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(originalName);
        dest.writeValue(name);
        dest.writeValue(firstAirDate);
        dest.writeList(originCountry);
        if (similarResponse == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeParcelable(similarResponse, flags);
        }
    }

    public int describeContents() {
        return 0;
    }

}
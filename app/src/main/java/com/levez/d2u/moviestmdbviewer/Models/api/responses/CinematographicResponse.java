package com.levez.d2u.moviestmdbviewer.Models.api.responses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.levez.d2u.moviestmdbviewer.Models.entity.Cinematographic;
import com.levez.d2u.moviestmdbviewer.Models.entity.Movie;
import com.levez.d2u.moviestmdbviewer.Models.entity.TvSeries;

import java.util.List;

public class CinematographicResponse<T extends Cinematographic> implements Parcelable
{

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private List<T> results = null;

    public final static Parcelable.Creator<CinematographicResponse> CREATOR = new Creator<CinematographicResponse>() {


        public CinematographicResponse createFromParcel(Parcel in) {
            return new CinematographicResponse(in);
        }

        public CinematographicResponse[] newArray(int size) {
            return (new CinematographicResponse[size]);
        }

    };

    protected CinematographicResponse(Parcel in) {

        ClassLoader classLoader;
        if(results.get(0) instanceof Movie){
            classLoader = Movie.class.getClassLoader();
        }else{
            classLoader = TvSeries.class.getClassLoader();
        }

        this.page = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalResults = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalPages = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.results, classLoader);
    }

    public CinematographicResponse() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(page);
        dest.writeValue(totalResults);
        dest.writeValue(totalPages);
        dest.writeList(results);
    }

    public int describeContents() {
        return 0;
    }

}
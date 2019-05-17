package com.levez.d2u.moviestmdbviewer.Models.api.responses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.levez.d2u.moviestmdbviewer.Models.entity.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoResponse implements Parcelable
{

    @SerializedName("results")
    @Expose
    private List<Video> videos;

    public final static Creator<VideoResponse> CREATOR = new Creator<VideoResponse>() {

        public VideoResponse createFromParcel(Parcel in) {
            return new VideoResponse(in);
        }

        public VideoResponse[] newArray(int size) {
            return (new VideoResponse[size]);
        }

    };

    private VideoResponse(Parcel in) {
        in.readList(this.videos, Video.class.getClassLoader());
    }

    public VideoResponse() {
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(videos);
    }

    public int describeContents() {
        return 0;
    }

}
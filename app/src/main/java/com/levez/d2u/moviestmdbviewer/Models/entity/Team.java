package com.levez.d2u.moviestmdbviewer.Models.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Team implements Parcelable, HasProfilePath {


    @SerializedName("credit_id")
    @Expose
    private String creditId;

    @SerializedName("gender")
    @Expose
    private Integer gender;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("profile_path")
    @Expose
    private String profilePath;


    public Team() {
    }

    @Ignore
    protected Team(Parcel in) {
        this.creditId = ((String) in.readValue((String.class.getClassLoader())));
        this.gender = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.profilePath =(String)  in.readValue((Object.class.getClassLoader()));
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(creditId);
        dest.writeValue(gender);
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(profilePath);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(!(obj instanceof Team)) return false;

        return this.id.equals(((Team) obj).id);
    }
}

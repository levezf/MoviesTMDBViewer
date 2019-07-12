package com.levez.d2u.moviestmdbviewer.Models.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Crew extends Team implements Parcelable
{


    @SerializedName("department")
    @Expose
    private String department;

    @SerializedName("job")
    @Expose
    private String job;


    public final static Parcelable.Creator<Crew> CREATOR = new Creator<Crew>() {


        public Crew createFromParcel(Parcel in) {
            return new Crew(in);
        }

        public Crew[] newArray(int size) {
            return (new Crew[size]);
        }

    };

    public Crew() {
    }

    private Crew(Parcel in) {
        super(in);
        this.department = ((String) in.readValue((String.class.getClassLoader())));
        this.job = ((String) in.readValue((String.class.getClassLoader())));
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeValue(department);
        dest.writeValue(job);
    }

    public int describeContents() {
        return 0;
    }

}
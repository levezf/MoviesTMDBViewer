package com.levez.d2u.moviestmdbviewer.Models.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cast extends Team implements Parcelable
{

    @SerializedName("cast_id")
    @Expose
    private Integer castId;
    @SerializedName("character")
    @Expose
    private String character;

    @SerializedName("order")
    @Expose
    private Integer order;

    public final static Parcelable.Creator<Cast> CREATOR = new Creator<Cast>() {


        public Cast createFromParcel(Parcel in) {
            return new Cast(in);
        }

        public Cast[] newArray(int size) {
            return (new Cast[size]);
        }

    };

    private Cast(Parcel in) {
        super(in);
        this.castId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.character = ((String) in.readValue((String.class.getClassLoader())));
        this.order = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }


    public Integer getCastId() {
        return castId;
    }

    public void setCastId(Integer castId) {
        this.castId = castId;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }


    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeValue(castId);
        dest.writeValue(character);
        dest.writeValue(order);
    }

    public int describeContents() {
        return 0;
    }

}
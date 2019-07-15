package com.levez.d2u.moviestmdbviewer.Models.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Entity
public class People extends Searchable implements Parcelable, HasProfilePath
{
    @SerializedName("birthday")
    @Expose
    @Ignore
    private String birthday;

    @SerializedName("known_for_department")
    @Expose
    @Ignore
    private String knownForDepartment;

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private Integer id;

    @SerializedName("movie_credits")
    @Expose
    @Ignore
    private Participations<Movie> movieCredits;

    @SerializedName("profile_path")
    @Expose
    @Ignore
    private String profilePath;

    @SerializedName("tv_credits")
    @Expose
    @Ignore
    private Participations<TvSeries> tvCredits;

    @SerializedName("deathday")
    @Expose
    @Ignore
    private String deathday;

    @SerializedName("name")
    @Expose
    @Ignore
    private String name;

    @SerializedName("also_known_as")
    @Expose
    @Ignore
    private List<String> alsoKnownAs = new ArrayList<>();

    @SerializedName("biography")
    @Expose
    @Ignore
    private String biography;

    @SerializedName("adult")
    @Expose
    @Ignore
    private Boolean adult;

    @SerializedName("gender")
    @Expose
    @Ignore
    private Integer gender;

    @SerializedName("place_of_birth")
    @Expose
    @Ignore
    private String placeOfBirth;

    @SerializedName("popularity")
    @Expose
    @Ignore
    private Double popularity;

    @Expose(deserialize = false, serialize = false)
    @Ignore
    private boolean favorite;


    public final static Parcelable.Creator<People> CREATOR = new Creator<People>() {

        public People createFromParcel(Parcel in) {
            return new People(in);
        }

        public People[] newArray(int size) {
            return (new People[size]);
        }

    };

    @Ignore
    protected People(Parcel in) {
        this.birthday = ((String) in.readValue((String.class.getClassLoader())));
        this.knownForDepartment = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.movieCredits = in.readParcelable((Participations.class.getClassLoader()));
        this.profilePath = ((String) in.readValue((String.class.getClassLoader())));
        this.tvCredits = (in.readParcelable((Participations.class.getClassLoader())));
        this.deathday = (in.readString());
        this.name = in.readString();
        in.readList(this.alsoKnownAs, (java.lang.String.class.getClassLoader()));
        this.biography = ((String) in.readValue((String.class.getClassLoader())));
        this.adult = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.gender = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.placeOfBirth = ( in.readString());
        this.popularity = ((Double) in.readValue((Double.class.getClassLoader())));
        favorite = in.readByte() != 0x00;

    }

    public People() {
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getKnownForDepartment() {
        return knownForDepartment;
    }

    public void setKnownForDepartment(String knownForDepartment) {
        this.knownForDepartment = knownForDepartment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Movie> getMoviesParticipation() {
        return movieCredits.getParticipations();
    }

    public List<TvSeries> getTvSeriesParticipation() {
        return tvCredits.getParticipations();
    }

    @Override
    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }



    public String getDeathday() {
        return deathday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAlsoKnownAs() {
        return alsoKnownAs;
    }

    public void setAlsoKnownAs(List<String> alsoKnownAs) {
        this.alsoKnownAs = alsoKnownAs;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(birthday);
        dest.writeValue(knownForDepartment);
        dest.writeValue(id);
        dest.writeParcelable(movieCredits, flags);
        dest.writeValue(profilePath);
        dest.writeParcelable(tvCredits, flags);
        dest.writeValue(deathday);
        dest.writeValue(name);
        dest.writeList(alsoKnownAs);
        dest.writeValue(biography);
        dest.writeValue(adult);
        dest.writeValue(gender);
        dest.writeValue(placeOfBirth);
        dest.writeValue(popularity);
        dest.writeByte((byte) (favorite ? 0x01 : 0x00));
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(!(obj instanceof People)) return false;

        return this.id.equals(((People) obj).id);
    }

}
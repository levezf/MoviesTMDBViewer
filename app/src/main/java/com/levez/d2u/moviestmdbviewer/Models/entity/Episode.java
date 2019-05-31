package com.levez.d2u.moviestmdbviewer.Models.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static androidx.room.ForeignKey.CASCADE;

@Entity
public class Episode implements Parcelable
{

    @SerializedName("air_date")
    @Expose
    @Ignore
    private String airDate;

    @SerializedName("crew")
    @Expose
    @Ignore
    private List<Crew> crew = new ArrayList<>();

    @SerializedName("episode_number")
    @Expose
    @Ignore
    private Integer episodeNumber;

    @SerializedName("name")
    @Expose
    @Ignore
    private String name;

    @SerializedName("overview")
    @Expose
    @Ignore
    private String overview;

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private Integer id;

    @SerializedName("production_code")
    @Expose
    @Ignore
    private String productionCode;

    @SerializedName("season_number")
    @Expose
    @Ignore
    private Integer seasonNumber;

    @SerializedName("still_path")
    @Expose
    @Ignore
    private String stillPath;

    @SerializedName("vote_average")
    @Expose
    @Ignore
    private Double voteAverage;

    @SerializedName("vote_count")
    @Expose
    @Ignore
    private Integer voteCount;

    @Expose(serialize = false, deserialize = false)
    @Ignore
    private boolean watched;

    @Expose(serialize = false, deserialize = false)
    @ForeignKey(entity = TvSeries.class, parentColumns = {"id"}, childColumns = {"idSerie"}, onDelete = CASCADE)
    private Integer idSerie;

    public final static Creator<Episode> CREATOR = new Creator<Episode>() {

        public Episode createFromParcel(Parcel in) {
            return new Episode(in);
        }

        public Episode[] newArray(int size) {
            return (new Episode[size]);
        }

    };

    @Ignore
    private Episode(Parcel in) {
        this.airDate = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.crew, (Crew.class.getClassLoader()));
        this.episodeNumber = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.overview = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.productionCode = ((String) in.readValue((String.class.getClassLoader())));
        this.seasonNumber = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.stillPath = ((String) in.readValue((String.class.getClassLoader())));
        this.voteAverage = ((Double) in.readValue((Double.class.getClassLoader())));
        this.voteCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.idSerie = ((Integer) in.readValue((Integer.class.getClassLoader())));

    }

    public Episode() {
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductionCode() {
        return productionCode;
    }

    public void setProductionCode(String productionCode) {
        this.productionCode = productionCode;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getStillPath() {
        return stillPath;
    }

    public void setStillPath(String stillPath) {
        this.stillPath = stillPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }



    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(airDate);
        dest.writeList(crew);
        dest.writeValue(episodeNumber);
        dest.writeValue(name);
        dest.writeValue(overview);
        dest.writeValue(id);
        dest.writeValue(productionCode);
        dest.writeValue(seasonNumber);
        dest.writeValue(stillPath);
        dest.writeValue(voteAverage);
        dest.writeValue(voteCount);
        dest.writeValue(idSerie);
    }

    public int describeContents() {
        return 0;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }
    public Integer getIdSerie() {
        return idSerie;
    }

    public void setIdSerie(Integer idSerie) {
        this.idSerie = idSerie;
    }
}
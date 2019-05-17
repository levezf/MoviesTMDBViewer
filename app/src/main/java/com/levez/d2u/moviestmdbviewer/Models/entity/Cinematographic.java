package com.levez.d2u.moviestmdbviewer.Models.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.levez.d2u.moviestmdbviewer.Models.api.responses.BaseResponse;
import com.levez.d2u.moviestmdbviewer.Models.api.responses.VideoResponse;

import java.util.ArrayList;
import java.util.List;

public class Cinematographic extends Searchable implements Parcelable {

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds;

    @SerializedName("original_language")
    @Expose
    private String originalLanguage;

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("popularity")
    @Expose
    private Double popularity;

    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;

    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("genres")
    @Expose
    private List<Genre> genres;

    @SerializedName("production_companies")
    @Expose
    private List<ProductionCompany> productionCompanies;

    @SerializedName("revenue")
    @Expose
    private long revenue;

    @SerializedName("runtime")
    @Expose
    private Integer runtime;

    @SerializedName("videos")
    @Expose
    private VideoResponse videosResponse;

    @SerializedName("credits")
    @Expose
    private Credits credits;


    @Expose(deserialize = false, serialize = false)
    private boolean favorite;

    @Expose(deserialize = false, serialize = false)
    private long idDatabase;


    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public long getIdDatabase() {
        return idDatabase;
    }

    public void setIdDatabase(long idDatabase) {
        this.idDatabase = idDatabase;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public List<Video> getVideos() {

        return videosResponse!=null?videosResponse.getVideos():new ArrayList<>();

    }

    public void setVideos(List<Video> videos) {

        if(videosResponse!=null){
            videosResponse.setVideos(videos);
        }else{
            videosResponse = new VideoResponse();
            videosResponse.setVideos(videos);
        }
    }

    public Credits getCredits() {
        return credits;
    }

    public void setCredits(Credits credits) {
        this.credits = credits;
    }



    public Cinematographic() {
    }

    protected Cinematographic(Parcel in) {
        posterPath = in.readString();
        if (in.readByte() == 0x01) {
            genreIds = new ArrayList<>();
            in.readList(genreIds, Integer.class.getClassLoader());
        } else {
            genreIds = null;
        }
        originalLanguage = in.readString();
        backdropPath = in.readString();
        overview = in.readString();
        popularity = in.readByte() == 0x00 ? null : in.readDouble();
        voteCount = in.readByte() == 0x00 ? null : in.readInt();
        voteAverage = in.readByte() == 0x00 ? null : in.readDouble();
        id = in.readByte() == 0x00 ? null : in.readInt();
        if (in.readByte() == 0x01) {
            genres = new ArrayList<>();
            in.readList(genres, Genre.class.getClassLoader());
        } else {
            genres = null;
        }
        if (in.readByte() == 0x01) {
            productionCompanies = new ArrayList<>();
            in.readList(productionCompanies, ProductionCompany.class.getClassLoader());
        } else {
            productionCompanies = null;
        }
        revenue = in.readByte() == 0x01 ? in.readLong() : 0;
        runtime = in.readByte() == 0x00 ? null : in.readInt();
        if (in.readByte() == 0x01) {
            videosResponse = in.readParcelable(VideoResponse.class.getClassLoader());
        } else {
            videosResponse = null;
        }
        credits = (Credits) in.readValue(Credits.class.getClassLoader());
        favorite = in.readByte() != 0x00;
        idDatabase = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterPath);
        if (genreIds == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(genreIds);
        }
        dest.writeString(originalLanguage);
        dest.writeString(backdropPath);
        dest.writeString(overview);
        if (popularity == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(popularity);
        }
        if (voteCount == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(voteCount);
        }
        if (voteAverage == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(voteAverage);
        }
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        if (genres == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(genres);
        }
        if (productionCompanies == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(productionCompanies);
        }

        dest.writeByte((byte) (0x01));
        dest.writeLong(revenue);

        if (runtime == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(runtime);
        }
        if (videosResponse == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeParcelable(videosResponse, flags);
        }
        dest.writeValue(credits);
        dest.writeByte((byte) (favorite ? 0x01 : 0x00));
        dest.writeLong(idDatabase);
    }

    public static final Creator<Cinematographic> CREATOR = new Creator<Cinematographic>() {
        @Override
        public Cinematographic createFromParcel(Parcel in) {
            return new Cinematographic(in);
        }

        @Override
        public Cinematographic[] newArray(int size) {
            return new Cinematographic[size];
        }
    };
}

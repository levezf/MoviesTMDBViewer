package com.levez.d2u.moviestmdbviewer.Models.Repositorys;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.levez.d2u.moviestmdbviewer.Models.RoomDB.AppDatabase;
import com.levez.d2u.moviestmdbviewer.Models.api.Constant;
import com.levez.d2u.moviestmdbviewer.Models.entity.Episode;
import com.levez.d2u.moviestmdbviewer.Models.entity.Movie;
import com.levez.d2u.moviestmdbviewer.Models.entity.People;
import com.levez.d2u.moviestmdbviewer.Models.entity.TvSeries;

import java.util.List;

import javax.inject.Inject;

public class FavoriteRepository extends BaseRepository{


    @Inject
    public FavoriteRepository() {
    }


    public LiveData<List<Integer>> getFavorites(String tagType, Context context){

        switch (tagType) {
            case Constant.TAG_TYPE_MOVIE:

                return AppDatabase.getInstance(context).movieDAO().getAllIds();

            case Constant.TAG_TYPE_TV_SERIES:

                return AppDatabase.getInstance(context).tvSeriesDAO().getAllIds();

            case Constant.TAG_TYPE_EPISODE:

                return AppDatabase.getInstance(context).episodeDAO().getAllIds();

            default:

                return AppDatabase.getInstance(context).peopleDAO().getAllIds();

        }
    }

    public <T> void delete(Context context, T t) {

        if(t instanceof Movie){

            new Thread(()->AppDatabase.getInstance(context).movieDAO().remove((Movie) t)).start();

        } else if(t instanceof TvSeries){

            new Thread(()->AppDatabase.getInstance(context).tvSeriesDAO().remove((TvSeries) t)).start();

        }else if(t instanceof Episode){

            new Thread(()->AppDatabase.getInstance(context).episodeDAO().remove((Episode) t)).start();

        }else {

            new Thread(()->AppDatabase.getInstance(context).peopleDAO().remove((People) t)).start();

        }
    }

    public <T> void insert(Context context, T t) {
        if(t instanceof Movie){

            new Thread(()->AppDatabase.getInstance(context).movieDAO().insert((Movie) t)).start();

        } else if(t instanceof TvSeries){

            new Thread(()->AppDatabase.getInstance(context).tvSeriesDAO().insert((TvSeries) t)).start();

        }else if(t instanceof Episode){

            new Thread(()->AppDatabase.getInstance(context).episodeDAO().insert((Episode) t)).start();

        }else {

            new Thread(()->AppDatabase.getInstance(context).peopleDAO().insert((People) t)).start();

        }
    }
}

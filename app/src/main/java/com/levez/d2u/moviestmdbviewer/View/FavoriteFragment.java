package com.levez.d2u.moviestmdbviewer.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.levez.d2u.categoryhorizontalview.CategoryHorizontalView;
import com.levez.d2u.moviestmdbviewer.Adapter.CinematographicListAdapter;
import com.levez.d2u.moviestmdbviewer.Adapter.CircleImageAdapterPeople;
import com.levez.d2u.moviestmdbviewer.Dependency.DaggerViewModelFactory;
import com.levez.d2u.moviestmdbviewer.Models.api.Constant;
import com.levez.d2u.moviestmdbviewer.Models.entity.Cinematographic;
import com.levez.d2u.moviestmdbviewer.Models.entity.Movie;
import com.levez.d2u.moviestmdbviewer.Models.entity.Team;
import com.levez.d2u.moviestmdbviewer.Models.entity.TvSeries;
import com.levez.d2u.moviestmdbviewer.R;
import com.levez.d2u.moviestmdbviewer.ViewModels.FavoriteViewModel;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class FavoriteFragment extends Fragment {


    private View mView;

    private FavoriteViewModel mFavoriteViewModel;
    @Inject
    DaggerViewModelFactory viewModelFactory;

    private CategoryHorizontalView mCategoryMovies;
    private CategoryHorizontalView mCategoryPeople;
    private CategoryHorizontalView mCategoryTvSeries;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.favorite_fragment, container, false);

        AndroidSupportInjection.inject(this);
        mFavoriteViewModel = ViewModelProviders.of(this, viewModelFactory).get(FavoriteViewModel.class);

        bindComponentsLayout();

        return mView;
    }

    private void bindComponentsLayout() {

        mCategoryMovies = mView.findViewById(R.id.chv_movie_favorite);
        mCategoryPeople = mView.findViewById(R.id.chv_people_favorite);
        mCategoryTvSeries = mView.findViewById(R.id.chv_tv_series_favorite);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mFavoriteViewModel.getIdsFavorites(Constant.TAG_TYPE_MOVIE, getContext()).observe(this, integers -> {

            for (Integer integer : integers) {

                mFavoriteViewModel.getMovies();
            }

        });

    }


    void setupCategoryView(CategoryHorizontalView categoryHorizontalView){

        categoryHorizontalView.disableShowMore(true);
        categoryHorizontalView.startRecyclerView(configRecyclerView());

    }

    private <T> void setRecyclerView(@IdRes int idRecyler, @IdRes int idCard, List<T> data){
        if(!configRecyclerView(mView.findViewById(idRecyler), data)){
            mView.findViewById(idCard).setVisibility(View.GONE);
        }
    }


    private <T> RecyclerView.Adapter configRecyclerView(LiveData<T> liveData, T data){
        if(data == null){
            return null;
        }

        RecyclerView.Adapter adapter;

        if(data.get(0) instanceof Team) {

            //noinspection unchecked
            adapter = new CircleImageAdapterPeople((List<Team>)data);
            ((CircleImageAdapterPeople) adapter).setClickListener((v, position) -> {

                int id = (((List<Team>) data).get(position)).getId();
                inflate(
                        DetailsPeopleFragment.newInstance(id), Constant.TAG_FRAG_DETAILS_PEOPLE + id);

            });
            liveData.observe(this, ts -> {
                ((CircleImageAdapterPeople) adapter).insertItems((List<Team>)  ts);
            });


        }else {

            //noinspection unchecked
            adapter = new CinematographicListAdapter((List<Cinematographic>) data);
            ((CinematographicListAdapter) adapter).setClickListener((v, position) -> {

                int id = (((List<Cinematographic>)data).get(position) instanceof TvSeries) ?
                        ((TvSeries) data).getId() : ((Movie) data).getId();

                Fragment fragment = DetailsCinematographicFragment.newInstance(
                        id,
                        ((((List<Cinematographic>)data).get(position) instanceof TvSeries) ?
                                Constant.TAG_TYPE_TV_SERIES : Constant.TAG_TYPE_MOVIE));

                inflate(fragment, Constant.TAG_FRAG_DETAILS_CINEMATOGRAPHIC + id);
            });

            liveData.observe(this, ts -> {
                ((CinematographicListAdapter) adapter).insertItems((List<Cinematographic>)  ts);
            });

        }




        return adapter;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mFavoriteViewModel.clear();
    }

/*
    @Override
    public void onClick(View v, int position, Searchable searchable, String tagType) {

        int id;

        switch (tagType){

            case Constant.TAG_TYPE_PEOPLE:
                id  = ((People) searchable).getId();
                inflate(DetailsPeopleFragment.newInstance(id), Constant.TAG_FRAG_DETAILS_PEOPLE + id);
                break;

            case Constant.TAG_TYPE_MOVIE:
            case Constant.TAG_TYPE_TV_SERIES:
                id  = ((Cinematographic) searchable).getId();
                inflate(DetailsCinematographicFragment.newInstance(id, tagType), Constant.TAG_FRAG_DETAILS_CINEMATOGRAPHIC + id);
                break;
        }

    }*/
    private void inflate(Fragment fragment, String tag){
        ((MainActivity) Objects.requireNonNull(getActivity())).inflateFragment(fragment, tag);
    }

}

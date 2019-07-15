package com.levez.d2u.moviestmdbviewer.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.levez.d2u.categoryhorizontalview.CategoryHorizontalView;
import com.levez.d2u.moviestmdbviewer.Adapter.CinematographicListAdapter;
import com.levez.d2u.moviestmdbviewer.Adapter.CircleImageAdapterPeople;
import com.levez.d2u.moviestmdbviewer.Dependency.DaggerViewModelFactory;
import com.levez.d2u.moviestmdbviewer.Models.api.Constant;
import com.levez.d2u.moviestmdbviewer.R;
import com.levez.d2u.moviestmdbviewer.ViewModels.FavoriteViewModel;

import java.util.ArrayList;
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
    private CinematographicListAdapter mMoviesAdapter;
    private CinematographicListAdapter mTvSeriesAdapter;
    private CircleImageAdapterPeople mPeopleAdapter;


    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

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

        mMoviesAdapter = new CinematographicListAdapter();
        mTvSeriesAdapter = new CinematographicListAdapter();
        mPeopleAdapter = new CircleImageAdapterPeople(new ArrayList<>());

        mCategoryMovies.startRecyclerView(mMoviesAdapter);
        mCategoryPeople.startRecyclerView(mPeopleAdapter);
        mCategoryTvSeries.startRecyclerView(mTvSeriesAdapter);

        mCategoryMovies.disableShowMore(true);
        mCategoryPeople.disableShowMore(true);
        mCategoryTvSeries.disableShowMore(true);

        mMoviesAdapter.setClickListener((v, position) -> click(v, position, Constant.TAG_TYPE_MOVIE));
        mTvSeriesAdapter.setClickListener((v, position) -> click(v, position, Constant.TAG_TYPE_TV_SERIES));
        mPeopleAdapter.setClickListener((v, position) -> click(v, position, Constant.TAG_TYPE_PEOPLE));

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mFavoriteViewModel.getIdsFavorites(Constant.TAG_TYPE_MOVIE, getContext()).observe(this, integers -> {

            if(integers != null && !integers.isEmpty()) {
                mCategoryMovies.setVisibility(View.VISIBLE);

                for (Integer id : integers) {
                    mCategoryMovies.hideProgress();

                    mFavoriteViewModel.getMovieById(id).observe(this, movie ->
                            ((CinematographicListAdapter) mCategoryMovies.getAdapter()).insertItem(movie));
                }
            }else{
                mCategoryMovies.setVisibility(View.GONE);
                verifyAllIsGone();
            }

        });

        mFavoriteViewModel.getIdsFavorites(Constant.TAG_TYPE_TV_SERIES, getContext()).observe(this, integers -> {

            if(integers != null && !integers.isEmpty()) {
                mCategoryTvSeries.setVisibility(View.VISIBLE);

                for (Integer id : integers) {
                    mCategoryTvSeries.hideProgress();

                    mFavoriteViewModel.getTvSeriesById(id).observe(this, tvSeries ->
                            ((CinematographicListAdapter) mCategoryTvSeries.getAdapter()).insertItem(tvSeries));
                }

            }else{
                mCategoryTvSeries.setVisibility(View.GONE);
                verifyAllIsGone();
            }

        });


        mFavoriteViewModel.getIdsFavorites(Constant.TAG_TYPE_PEOPLE, getContext()).observe(this, integers -> {

            if(integers != null && !integers.isEmpty()) {

                mCategoryPeople.setVisibility(View.VISIBLE);

                for (Integer id : integers) {

                    mCategoryPeople.hideProgress();

                    mFavoriteViewModel.getPeopleById(id).observe(this, people ->
                            ((CircleImageAdapterPeople) mCategoryPeople.getAdapter()).insertItem(people));
                }

            }else{
                mCategoryPeople.setVisibility(View.GONE);
                verifyAllIsGone();
            }
        });

    }

    private void verifyAllIsGone() {

        if(mCategoryMovies.getVisibility()==View.GONE &&
                mCategoryPeople.getVisibility() == View.GONE &&
                mCategoryTvSeries.getVisibility() == View.GONE){

            if(mView.findViewById(R.id.place_holder_empty).getVisibility() == View.GONE)
                mView.findViewById(R.id.place_holder_empty).setVisibility(View.VISIBLE);

        }else{
            mView.findViewById(R.id.place_holder_empty).setVisibility(View.GONE);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFavoriteViewModel.clear();
    }

    private void click(View v, int position, String tagType) {

        int id = -1;

        switch (tagType){

            case Constant.TAG_TYPE_PEOPLE:
                id  = mPeopleAdapter.getPeople(position).getId();
                inflate(DetailsPeopleFragment.newInstance(id), Constant.TAG_FRAG_DETAILS_PEOPLE + id);
                break;

            case Constant.TAG_TYPE_MOVIE:
                id = mMoviesAdapter.get(position).getId();
            case Constant.TAG_TYPE_TV_SERIES:

                if(id==-1)
                    id  = mTvSeriesAdapter.get(position).getId();

                inflate(DetailsCinematographicFragment.newInstance(id, tagType), Constant.TAG_FRAG_DETAILS_CINEMATOGRAPHIC + id);
                break;
        }

    }

    private void inflate(Fragment fragment, String tag){
        ((MainActivity) Objects.requireNonNull(getActivity())).inflateFragment(fragment, tag);
    }

}

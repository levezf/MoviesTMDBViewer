package com.levez.d2u.moviestmdbviewer.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.levez.d2u.categoryhorizontalview.CategoryHorizontalView;
import com.levez.d2u.moviestmdbviewer.Adapter.CinematographicListAdapter;
import com.levez.d2u.moviestmdbviewer.Adapter.ListCategoryViewAdapter;
import com.levez.d2u.moviestmdbviewer.Dependency.DaggerViewModelFactory;
import com.levez.d2u.moviestmdbviewer.Models.api.Constant;
import com.levez.d2u.moviestmdbviewer.Models.entity.Cinematographic;
import com.levez.d2u.moviestmdbviewer.Models.entity.Genre;
import com.levez.d2u.moviestmdbviewer.R;
import com.levez.d2u.moviestmdbviewer.ViewModels.CinematographicViewModel;
import com.levez.d2u.moviestmdbviewer.ViewModels.GenresViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

import static com.levez.d2u.moviestmdbviewer.Models.api.Constant.TAG_MORE_POPULAR;
import static com.levez.d2u.moviestmdbviewer.Models.api.Constant.TAG_VOTE_AVERAGE;
import static com.levez.d2u.moviestmdbviewer.Models.api.Constant.TAG_VOTE_COUNT;

public class CinematographicFragment extends Fragment {

    private static final String EXTRA_TAG_TYPE = "EXTRA_TAG_TYPE";
    private CinematographicViewModel mViewModel;
    private View mView;
    private String mTagType;


    @Inject
    DaggerViewModelFactory viewModelFactory;

    private GenresViewModel mGenresViewModel;
    private RecyclerView mRecycler;
    private ListCategoryViewAdapter mAdapter;

    public static CinematographicFragment newInstance(String tagType) {

        Bundle args = new Bundle();
        args.putString(EXTRA_TAG_TYPE, tagType);

        CinematographicFragment fragment = new CinematographicFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments()!=null){

            mTagType = getArguments().getString(EXTRA_TAG_TYPE);

            assert mTagType != null;
            if(!mTagType.equals(Constant.FAVORITES)){
                AndroidSupportInjection.inject(this);
                mGenresViewModel = ViewModelProviders.of(this, viewModelFactory).get(GenresViewModel.class);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.cinematographic_fragment, container, false);

        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CinematographicViewModel.class);
        mViewModel.onAttachTagType(mTagType);


        mAdapter = new ListCategoryViewAdapter();
        mRecycler = mView.findViewById(R.id.rv_list);
        mRecycler.setHasFixedSize(true);
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false));


        initDiscover();

    }

    private void initDiscover() {
        initBasicsCinematographicsDiscover();

        mGenresViewModel.getGenres(mTagType).observe(this, new Observer<List<Genre>>() {
            @Override
            public void onChanged(@Nullable List<Genre> genres) {
                assert genres != null;

                for (Genre genre : genres) {
                    startCinematographicList(categoryHorizontalViewCreate(genre.getName()),
                            mViewModel.getAllByGenre(genre), genre.getName());
                }

            }
        });
    }

    private void initBasicsCinematographicsDiscover() {

        startCinematographicList(categoryHorizontalViewCreate(getName(TAG_MORE_POPULAR)),
                mViewModel.getAllDiscoverByTagSort(TAG_MORE_POPULAR), TAG_MORE_POPULAR);
        startCinematographicList(categoryHorizontalViewCreate(getName(TAG_VOTE_AVERAGE)),
                mViewModel.getAllDiscoverByTagSort(TAG_VOTE_AVERAGE), TAG_VOTE_AVERAGE);
        startCinematographicList(categoryHorizontalViewCreate(getName(TAG_VOTE_COUNT)),
                mViewModel.getAllDiscoverByTagSort(TAG_VOTE_COUNT), TAG_VOTE_COUNT);

    }

    private String getName(String tag) {

        switch (tag){

            case TAG_MORE_POPULAR:
                return "More Popular";

            case TAG_VOTE_AVERAGE:
                return "Best Rated";

            case TAG_VOTE_COUNT:
                return "Greater number of votes";

        }
        return null;
    }

    private void startCinematographicList(final CategoryHorizontalView categoryHorizontalView,
                                          final LiveData<List<Cinematographic>> cinematographics, final String tag) {

        final CinematographicListAdapter adapter = new CinematographicListAdapter();

        adapter.setClickListener((v, position) -> {
            //mostra detalhes do cine

            if(getActivity()!=null){
                ((MainActivity) getActivity())
                        .inflateFragment(
                                DetailsCinematographicFragment.newInstance(adapter.get(position).getId(), mTagType), Constant.TAG_FRAG_DETAILS_CINEMATOGRAPHIC + adapter.get(position).getId());
            }
        });

        categoryHorizontalView.startRecyclerView(adapter);
        categoryHorizontalView.setOnCategoryPagingListener(() -> mViewModel.incrementPage(tag));
        mAdapter.insertItem(categoryHorizontalView, tag);

        cinematographics.observe(this, ts -> {

            if(ts ==null|| ts.isEmpty()){
                mAdapter.showProgress(tag);
            }else{
                mAdapter.hideProgress(tag);
                adapter.insertItems(ts);
            }

        });

    }

    private CategoryHorizontalView categoryHorizontalViewCreate(String title){

        assert getContext()!=null;
        CategoryHorizontalView categoryHorizontalView = new CategoryHorizontalView(getContext());
        categoryHorizontalView.setTitle(title);
        categoryHorizontalView.disableShowMore(true);
        return categoryHorizontalView;
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        mViewModel.clear();
        mGenresViewModel.clear();
        super.onDestroy();
    }
}

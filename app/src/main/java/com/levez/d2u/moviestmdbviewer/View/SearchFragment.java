package com.levez.d2u.moviestmdbviewer.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.MediaRouteButton;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.levez.d2u.moviestmdbviewer.Adapter.ListSearchAdapter;
import com.levez.d2u.moviestmdbviewer.Adapter.PaginationScrollListener;
import com.levez.d2u.moviestmdbviewer.Models.api.Constant;
import com.levez.d2u.moviestmdbviewer.Models.entity.Searchable;
import com.levez.d2u.moviestmdbviewer.R;
import com.levez.d2u.moviestmdbviewer.ViewModels.SearchViewModel;
import com.levez.d2u.searchlibrary.SearchView;

import java.util.List;

public class SearchFragment extends Fragment implements ListSearchAdapter.OnSearchItemClickListener {


    private View mView;
    private SearchView mSearchView;
    private RecyclerView mList;
    private SearchViewModel mViewModel;
    private ListSearchAdapter mAdapter;
    private AppCompatTextView mMessageHelper;
    private ProgressBar mProgress;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.search_fragment, container, false);

        mSearchView = mView.findViewById(R.id.searchView);
        mList = mView.findViewById(R.id.rv_list);
        mMessageHelper = mView.findViewById(R.id.tv_helper);
        mProgress = mView.findViewById(R.id.progress);

        return mView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        bindSearchView();
        bindSearchList();

        mViewModel.getSearch().observe(this, searchables -> {
            mProgress.setVisibility(View.GONE);
            mAdapter.refresh(searchables);
        });

    }

    private void bindSearchList() {


        LinearLayoutManager layout = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mAdapter = new ListSearchAdapter();
        mList.setHasFixedSize(true);
        mList.setLayoutManager(layout);
        mList.setAdapter(mAdapter);
        mList.addOnScrollListener(new PaginationScrollListener(layout, () -> mViewModel.incrementPage()));
        mAdapter.setOnSearchItemClickListener(this);
    }

    private void bindSearchView() {
        mSearchView.setHint("Search for movies, series and people...");
        mSearchView.setOnTextChangeListener(new SearchView.OnTextChangeListener() {

            @Override
            public void onSuggestion(String suggestion) {

                mAdapter.clear();
                changeSearchText(suggestion);
                mMessageHelper.setVisibility(View.GONE);
                mList.setVisibility(View.VISIBLE);

                Log.d("tag", "onSuggestion: ");
            }

            @Override
            public void onSubmitted(String submitted) {

            }

            @Override
            public void onCleared() {

                mAdapter.clear();
                mList.setVisibility(View.GONE);
                mMessageHelper.setVisibility(View.VISIBLE);
                //hide recyclerview
                //clear recyclerview
                //show help

            }
        });
    }

    private void changeSearchText(String s) {
        mProgress.setVisibility(View.VISIBLE);
        mViewModel.setQuery(s);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.clear();
    }

    @Override
    public void onClick(View v, int position, String tagType) {
        if(tagType.equals(Constant.TAG_TYPE_MOVIE)){


        }else if(tagType.equals(Constant.TAG_TYPE_TV_SERIES)){


        }else{


        }
    }
}

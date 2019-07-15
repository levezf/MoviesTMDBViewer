package com.levez.d2u.moviestmdbviewer.View;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.levez.d2u.moviestmdbviewer.Adapter.ListSearchAdapter;
import com.levez.d2u.moviestmdbviewer.Adapter.PaginationScrollListener;
import com.levez.d2u.moviestmdbviewer.Models.api.Constant;
import com.levez.d2u.moviestmdbviewer.Models.entity.Cinematographic;
import com.levez.d2u.moviestmdbviewer.Models.entity.People;
import com.levez.d2u.moviestmdbviewer.Models.entity.Searchable;
import com.levez.d2u.moviestmdbviewer.R;
import com.levez.d2u.moviestmdbviewer.ViewModels.SearchViewModel;
import com.levez.d2u.searchlibrary.SearchView;

import java.util.ArrayList;
import java.util.Objects;

/*
*
* TODO
*  * Não perder os itens ao clicar
*  * Bugando a paginação ao voltar
*
*/


public class SearchFragment extends Fragment implements ListSearchAdapter.OnSearchItemClickListener {


    private static final String EXTRA_LIST_SEARCH = "extra_list_searchable";
    private View mView;
    private SearchView mSearchView;
    private RecyclerView mList;
    private SearchViewModel mViewModel;
    private ListSearchAdapter mAdapter;
    private AppCompatTextView mMessageHelper;
    private ProgressBar mProgress;
    private ImageView mImageHelp;
    private LinearLayout mPlaceHolderHelp;
    private ArrayList<Searchable> mSearchables = new ArrayList<>();

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
        mPlaceHolderHelp = mView.findViewById(R.id.place_holder_help);
        mImageHelp = mView.findViewById(R.id.image_help);

        return mView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bindSearchView();
        bindSearchList();
        showHelpSearch();

        if (savedInstanceState != null) {
            mSearchables = savedInstanceState.getParcelableArrayList(EXTRA_LIST_SEARCH);
            if (mSearchables != null && mSearchables.isEmpty()) mAdapter.refresh(mSearchables);
        }else{
            mViewModel.getSearch().observe(this, searchables -> {
                mProgress.setVisibility(View.GONE);
                if((searchables==null || searchables.isEmpty()) && mAdapter.getItemCount()==0){

                  showNotFound();

                }
                assert searchables != null;
                mSearchables.addAll(searchables);
                mAdapter.refresh(searchables);
            });
        }

    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

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
                mPlaceHolderHelp.setVisibility(View.GONE);
                mList.setVisibility(View.VISIBLE);

                Log.d("tag", "onSuggestion: ");
            }

            @Override
            public void onSubmitted(String submitted) {

            }

            @Override
            public void onCleared() {

                mAdapter.clear();
                mAdapter.notifyDataSetChanged();
                mViewModel.setQuery("");
                showHelpSearch();

            }
        });
    }


    private void showHelpSearch(){
        mProgress.setVisibility(View.GONE);
        mList.setVisibility(View.GONE);
        mMessageHelper.setText(R.string.text_help);
        mImageHelp.setImageResource(R.drawable.ic_search_help);
        mImageHelp.setColorFilter(mMessageHelper.getCurrentTextColor());
        mPlaceHolderHelp.setVisibility(View.VISIBLE);
    }

    private void showNotFound(){
        mProgress.setVisibility(View.GONE);
        mList.setVisibility(View.GONE);
        mMessageHelper.setText(getString(R.string.search_not_found));
        mImageHelp.setImageResource(R.drawable.ic_search_not_found);
        mImageHelp.setColorFilter(mMessageHelper.getCurrentTextColor());
        mPlaceHolderHelp.setVisibility(View.VISIBLE);
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
    public void onClick(View v, int position, Searchable searchable,String tagType) {

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

    }
    private void inflate(Fragment fragment, String tag){
        ((MainActivity) Objects.requireNonNull(getActivity())).inflateFragment(fragment, tag);
    }
}

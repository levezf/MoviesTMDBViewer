package com.levez.d2u.moviestmdbviewer.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.levez.d2u.moviestmdbviewer.R;
import com.levez.d2u.moviestmdbviewer.ViewModels.SearchViewModel;
import com.levez.d2u.searchlibrary.SearchView;

public class SearchFragment extends Fragment {


    private View mView;
    private SearchView mSearchView;
    private RecyclerView mList;
    private SearchViewModel mViewModel;


    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.search_fragment, container, false);

        mSearchView = mView.findViewById(R.id.searchView);
        mList = mView.findViewById(R.id.rv_list);

        return mView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);



        mSearchView.setOnTextChangeListener(new SearchView.OnTextChangeListener() {

//TODO fazer consulta por tag -- Artista / Filme / Série / Gênero

            @Override
            public void onSuggestion(String suggestion) {


                //pega suggestion

                Log.d("tag", "onSuggestion: ");
            }

            @Override
            public void onSubmitted(String submitted) {

                //pega consulta certa


                Log.d("tag", "onSubmitted: ");

            }

            @Override
            public void onCleared() {

                Log.d("tag", "onCleared: ");

            }
        });



    }
}

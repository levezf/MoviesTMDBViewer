package com.levez.d2u.moviestmdbviewer.View;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.common.collect.Collections2;
import com.levez.d2u.moviestmdbviewer.Adapter.CinematographicListAdapter;
import com.levez.d2u.moviestmdbviewer.Adapter.CircleImageAdapterPeople;
import com.levez.d2u.moviestmdbviewer.Adapter.OnItemClickListener;
import com.levez.d2u.moviestmdbviewer.Adapter.VideoAdapter;
import com.levez.d2u.moviestmdbviewer.Models.api.Constant;
import com.levez.d2u.moviestmdbviewer.Models.entity.Cinematographic;
import com.levez.d2u.moviestmdbviewer.Models.entity.Genre;
import com.levez.d2u.moviestmdbviewer.Models.entity.Movie;
import com.levez.d2u.moviestmdbviewer.Models.entity.People;
import com.levez.d2u.moviestmdbviewer.Models.entity.ProductionCompany;
import com.levez.d2u.moviestmdbviewer.Models.entity.ProductionCountry;
import com.levez.d2u.moviestmdbviewer.Models.entity.Team;
import com.levez.d2u.moviestmdbviewer.Models.entity.TvSeries;
import com.levez.d2u.moviestmdbviewer.Models.entity.Video;
import com.levez.d2u.moviestmdbviewer.R;
import com.levez.d2u.moviestmdbviewer.ViewModels.DetailsPeopleViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


public class DetailsPeopleView extends Fragment {


    private static final String EXTRA_PEOPLE = "extra_people";
    private View mView;
    private DetailsPeopleViewModel mViewModel;
    private final static String EXTRA_ID = "extra_id";
    private int mId;
    private People mPeople;
    private Toolbar mToolbar;


    public static DetailsPeopleView newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(EXTRA_ID, id);
        DetailsPeopleView fragment = new DetailsPeopleView();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(savedInstanceState != null){
            mId = savedInstanceState.getInt(EXTRA_ID);
            mPeople = savedInstanceState.getParcelable(EXTRA_PEOPLE);
        }else{
            if(getArguments()!=null) {
                mId = getArguments().getInt(EXTRA_ID);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.details_people_fragment, container, false);

        mToolbar = mView.findViewById(R.id.toolbar);
        mToolbar.setTitle("");

        if (null != getActivity()) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);

        }
        mToolbar.setNavigationOnClickListener(view -> getActivity().getSupportFragmentManager().popBackStack());
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(DetailsPeopleViewModel.class);

        if(mPeople==null){
            mViewModel.getDetails(mId).observe(this, this::bindPeople);
        }else{
            bindPeople(mPeople);
        }

    }

    private void bindPeople(People people) {
        mPeople = people;

        setText(mView, R.id.tv_name, mPeople.getName());

        String date = mPeople.getBirthday();
        if(mPeople.getDeathday()!=null && !mPeople.getDeathday().isEmpty()){
            date = date.concat(mPeople.getDeathday());
        }

        setText(mView, R.id.tv_date, date);
        setText(mView, R.id.tv_biography, mPeople.getBiography());

        setText(
                mView.findViewById(R.id.card_movies_credit),
                R.id.title_card_similar,
                getString(R.string.movies)
        );


        setText(
                mView.findViewById(R.id.card_tvseries_credit),
                R.id.title_card_similar,
                getString(R.string.tv_series)
        );


        Glide
                .with(getContext())
                .load(Constant.BASE_URL_IMAGE + mPeople.getProfilePath())
                .centerCrop()
                .into(((AppCompatImageView) mView.findViewById(R.id.iv_backdrop)));

        setRecyclerView(
                mView.findViewById(R.id.card_movies_credit),
                R.id.rv_similar,
                R.id.card_similar,
                mPeople.getMoviesParticipation()
        );


        setRecyclerView(
                mView.findViewById(R.id.card_tvseries_credit),
                R.id.rv_similar,
                R.id.card_similar,
                mPeople.getTvSeriesParticipation()
        );
    }

    private <T> void setRecyclerView(View view, @IdRes int idRecyler, @IdRes int idCard, List<T> data){
        if(!configRecyclerView(view.findViewById(idRecyler), data)){
            view.findViewById(idCard).setVisibility(View.GONE);
        }
    }


    private <T> boolean configRecyclerView(RecyclerView rv, List<T> data){

        if(data == null || data.isEmpty()){

            return false;
        }

        rv.setHasFixedSize(true);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(Objects.requireNonNull(getContext()),
                DividerItemDecoration.HORIZONTAL);
        itemDecorator.setDrawable(getResources().getDrawable(R.drawable.divider_space));

        rv.setLayoutManager( new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false));


        //noinspection unchecked
        Collection<Cinematographic> posteresFiltered = Collections2.filter((List<Cinematographic>)data,
                input -> ((input != null) && input.getPosterPath()!=null && !input.getPosterPath().isEmpty()));

        final List<Cinematographic> dataFiltered =  new ArrayList<>(posteresFiltered);
        final CinematographicListAdapter  adapter = new CinematographicListAdapter(dataFiltered);

        adapter.setClickListener((v, position) -> {

            int id;
            String tagType;
            id = dataFiltered.get(position).getId();

            if (!(data.get(position) instanceof TvSeries)) {
                tagType = Constant.TAG_TYPE_MOVIE;
            } else{
                tagType = Constant.TAG_TYPE_TV_SERIES;
            }

            Fragment fragment= DetailsCinematographicFragment.newInstance(
                    id, tagType);

            ((MainActivity) Objects.requireNonNull(getActivity())).inflateFragment(fragment, Constant.TAG_FRAG_DETAILS_MOVIE + id);
        });

        rv.setAdapter(adapter);
        rv.addItemDecoration(itemDecorator);


        return true;
}

    private void setText(View view, @IdRes int id, String text){
        ((AppCompatTextView) view.findViewById(id)).setText(text);
    }

    @Override
    public void onDestroy() {
        mViewModel.clear();
        super.onDestroy();
    }
}

package com.levez.d2u.moviestmdbviewer.View;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.levez.d2u.categoryhorizontalview.CategoryHorizontalView;
import com.levez.d2u.moviestmdbviewer.Adapter.CinematographicListAdapter;
import com.levez.d2u.moviestmdbviewer.Adapter.CircleImageAdapterPeople;
import com.levez.d2u.moviestmdbviewer.Adapter.TagAdapter;
import com.levez.d2u.moviestmdbviewer.Adapter.VideoAdapter;
import com.levez.d2u.moviestmdbviewer.Models.api.Constant;
import com.levez.d2u.moviestmdbviewer.Models.entity.Cinematographic;
import com.levez.d2u.moviestmdbviewer.Models.entity.Genre;
import com.levez.d2u.moviestmdbviewer.Models.entity.Movie;
import com.levez.d2u.moviestmdbviewer.Models.entity.ProductionCompany;
import com.levez.d2u.moviestmdbviewer.Models.entity.ProductionCountry;
import com.levez.d2u.moviestmdbviewer.Models.entity.Team;
import com.levez.d2u.moviestmdbviewer.Models.entity.Video;
import com.levez.d2u.moviestmdbviewer.R;
import com.levez.d2u.moviestmdbviewer.ViewModels.DetailsCinematographicViewModel;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class DetailsCinematographicFragment extends Fragment {


    private static final String EXTRA_ID = "extra_id";
    private static final String EXTRA_CINEMATOGRAPHIC = "extra_cinematographic";
    private View mView;
    private Cinematographic mCinematographic;
    private DetailsCinematographicViewModel mViewModel;
    private int mId;


    public static DetailsCinematographicFragment newInstance(int idCinematographic) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_ID, idCinematographic);
        DetailsCinematographicFragment fragment = new DetailsCinematographicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState!=null){
            mCinematographic = savedInstanceState.getParcelable(EXTRA_CINEMATOGRAPHIC);
        }else{
            if(getArguments() != null){
                mId = (getArguments().getInt(EXTRA_ID));
            }
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_CINEMATOGRAPHIC, mCinematographic);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.details_cinematographic_fragment, container, false);
        Toolbar toolbar = mView.findViewById(R.id.toolbar);
        toolbar.setTitle("");

        if (null != getActivity()) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);

        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //  getFragmentManager().beginTransaction().remove(DetailsCinematographicFragment.this).commit();
                getActivity().getSupportFragmentManager().popBackStack();

            /*
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().remove(DetailsCinematographicFragment.this);
                Log.d("TAGG", "onClick: backstack");
*/
            }
        });

        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(DetailsCinematographicViewModel.class);
        mViewModel.getDetails(mId).observe(this, this::bindFindViewById);

    }

    private void bindFindViewById(Cinematographic c) {



        if(c instanceof Movie){
            ((AppCompatTextView) mView.findViewById(R.id.tv_title)).setText(((Movie) c).getTitle());
            ((AppCompatTextView) mView.findViewById(R.id.tv_date_release)).setText(((Movie) c).getReleaseDate());
            configRecyclerView(mView.findViewById(R.id.rv_p_country), ((Movie) c).getProductionCountries());
            configRecyclerView(mView.findViewById(R.id.rv_similar), ((Movie) c).getSimilar());
        }

        ((AppCompatTextView) mView.findViewById(R.id.tv_overview)).setText(c.getOverview());
        ((AppCompatTextView) mView.findViewById(R.id.tv_rate_value)).setText(String.valueOf(c.getVoteAverage()));
        ((AppCompatTextView) mView.findViewById(R.id.result_revenue)).setText(NumberFormat.getInstance(Locale.US).format(c.getRevenue()));
        ((AppCompatTextView) mView.findViewById(R.id.result_o_language)).setText(c.getOriginalLanguage());
        ((AppCompatTextView) mView.findViewById(R.id.result_popularity)).setText(NumberFormat.getInstance(Locale.US).format(c.getPopularity()));
        ((AppCompatTextView) mView.findViewById(R.id.result_avg)).setText(NumberFormat.getInstance(Locale.US).format(c.getVoteAverage()));
        ((AppCompatTextView) mView.findViewById(R.id.result_vote_count)).setText(NumberFormat.getInstance(Locale.US).format(c.getVoteCount()));
        configRecyclerView(mView.findViewById(R.id.rv_cast), c.getCredits().getCast());
        configRecyclerView(mView.findViewById(R.id.rv_crew), c.getCredits().getCrew());
        configRecyclerView(mView.findViewById(R.id.rv_genres), c.getGenres());
        configRecyclerView(mView.findViewById(R.id.rv_p_companies), c.getProductionCompanies());

        AppCompatRatingBar rate = mView.findViewById(R.id.rb_rate);

        rate.setRating((float) (c.getVoteAverage()/2));
        rate.setStepSize(0.01f);
        rate.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> { });
        rate.getProgressDrawable()
                .setColorFilter(getResources().getColor(R.color.colorYellow), PorterDuff.Mode.SRC_IN);
        rate.invalidate();

        Glide
                .with(getContext())
                .load(Constant.BASE_URL_IMAGE + c.getBackdropPath())
                .centerCrop()
                .into(((AppCompatImageView) mView.findViewById(R.id.iv_backdrop)));



        if(c.getVideos()!=null && !c.getVideos().isEmpty()) {
            configRecyclerView(mView.findViewById(R.id.rv_videos), c.getVideos());
        }else {
            (mView.findViewById(R.id.card_videos)).setVisibility(View.GONE);
        }



        /*
         * TODO :
         *  * Fazer essa tela dinamica para séries e filmes
         *  * Se estiver nos detalhes e clicar no bottomNavigation novamente, ele volta pra principal
         *  * Criar telas para abrir as tags e mostrar os detalhes
         *  * Criar uma estrutura "recursiva" para abrir os filmes similares
         *  * Alterar as cores das estrelas dos itens de busca
         *  * Adicionar player do youtube para ver os videos disponiveis
         */

    }



    private <T> void configRecyclerView(RecyclerView rv, List<T> data){

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false));

        RecyclerView.Adapter adapter;

        DividerItemDecoration itemDecorator = new DividerItemDecoration(Objects.requireNonNull(getContext()),
                DividerItemDecoration.HORIZONTAL);
        itemDecorator.setDrawable(getResources().getDrawable(R.drawable.divider_space));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.HORIZONTAL, false);

        if(data.get(0) instanceof Team) {

            //noinspection unchecked
            adapter = new CircleImageAdapterPeople((List<Team>)data);

        }else if(data.get(0) instanceof Genre ||
                data.get(0) instanceof ProductionCountry ||
                data.get(0) instanceof  ProductionCompany){

            adapter = configureForTagAdapter(data);

        }else if (data.get(0) instanceof Cinematographic){

            //noinspection unchecked
            Collection<Cinematographic> posteresFiltered = Collections2.filter((List<Cinematographic>)data,
                    input -> ((input != null) && input.getPosterPath()!=null && !input.getPosterPath().isEmpty()));

            adapter = new CinematographicListAdapter(new ArrayList<>(posteresFiltered));

        }else{
            //noinspection unchecked
            Collection<Video> videosFiltered = Collections2.filter((List<Video>)data,
                    input -> ((input != null) && (input).getSite().equals(Constant.SITE_VIDEOS)));

            adapter = new VideoAdapter(new ArrayList<>(videosFiltered));
            itemDecorator = new DividerItemDecoration(Objects.requireNonNull(getContext()),
                    DividerItemDecoration.VERTICAL);

            layoutManager.setOrientation(RecyclerView.VERTICAL);
        }

        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        rv.addItemDecoration(itemDecorator);
    }



    private <T> TagAdapter configureForTagAdapter(List<T> data){

        Function<T, String> function =  new Function<T, String>() {
            @NullableDecl
            @Override
            public String apply(@NullableDecl T input) {

                if(input instanceof ProductionCompany)
                    return ((ProductionCompany) input).getName();
                else if(input instanceof ProductionCountry)
                    return ((ProductionCountry) input).getName();
                else
                    return  ((Genre) Objects.requireNonNull(input)).getName();
            }
        };

        Collection<String> names= Collections2.transform(data, function);
        Collection<String> namesFiltered = Collections2.filter(names, input -> input!=null && !input.isEmpty());



        return new TagAdapter(new ArrayList<>(namesFiltered));
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.clear();
    }
}

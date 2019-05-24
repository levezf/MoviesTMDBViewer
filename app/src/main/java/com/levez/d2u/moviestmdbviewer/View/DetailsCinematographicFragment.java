package com.levez.d2u.moviestmdbviewer.View;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
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
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.levez.d2u.moviestmdbviewer.Adapter.CinematographicListAdapter;
import com.levez.d2u.moviestmdbviewer.Adapter.CircleImageAdapterPeople;
import com.levez.d2u.moviestmdbviewer.Adapter.EpisodesAdapter;
import com.levez.d2u.moviestmdbviewer.Adapter.OnItemClickListener;
import com.levez.d2u.moviestmdbviewer.Adapter.SeasonsAdapter;
import com.levez.d2u.moviestmdbviewer.Adapter.TagAdapter;
import com.levez.d2u.moviestmdbviewer.Adapter.VideoAdapter;
import com.levez.d2u.moviestmdbviewer.Models.api.Constant;
import com.levez.d2u.moviestmdbviewer.Models.entity.Cinematographic;
import com.levez.d2u.moviestmdbviewer.Models.entity.Genre;
import com.levez.d2u.moviestmdbviewer.Models.entity.Movie;
import com.levez.d2u.moviestmdbviewer.Models.entity.ProductionCompany;
import com.levez.d2u.moviestmdbviewer.Models.entity.ProductionCountry;
import com.levez.d2u.moviestmdbviewer.Models.entity.Team;
import com.levez.d2u.moviestmdbviewer.Models.entity.TvSeries;
import com.levez.d2u.moviestmdbviewer.Models.entity.Video;
import com.levez.d2u.moviestmdbviewer.R;
import com.levez.d2u.moviestmdbviewer.Utils.FormatNumberUtils;
import com.levez.d2u.moviestmdbviewer.ViewModels.DetailsCinematographicViewModel;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


/*
 * TODO :
 *  * Fazer esquema para reduzir a quantidade de request (tester: colocar um delay no for de genres)
 *  * Melhorar o item de Videos
 *  * Buscar mais infromações das séries
 *  * Se estiver nos detalhes e clicar no bottomNavigation novamente, ele volta pra principal
 *  * Adicionar player do youtube para ver os videos disponiveis
 */


public class DetailsCinematographicFragment extends Fragment {


    private static final String EXTRA_ID = "extra_id";
    private static final String EXTRA_CINEMATOGRAPHIC = "extra_cinematographic";
    private static final String EXTRA_TAG_TYPE = "extra_tag_type";
    private View mView;
    private Cinematographic mCinematographic;
    private DetailsCinematographicViewModel mViewModel;
    private int mId;
    private Toolbar mToolbar;
    private String mTagType;
    private BottomSheetBehavior mBottomSheetBehaviorSeason;
    private BottomSheetBehavior mBottomSheetBehaviorEpisode;

    private SeasonsAdapter mAdapterSeasonsBottomSheet;
    private EpisodesAdapter mAdapterEpisodesBottomSheet;


    public static DetailsCinematographicFragment newInstance(int idCinematographic, String tagType) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_ID, idCinematographic);
        args.putString(EXTRA_TAG_TYPE, tagType);
        DetailsCinematographicFragment fragment = new DetailsCinematographicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState!=null){
            mCinematographic = savedInstanceState.getParcelable(EXTRA_CINEMATOGRAPHIC);
            mTagType = savedInstanceState.getString(EXTRA_TAG_TYPE);
        }else{
            if(getArguments() != null){
                mId = (getArguments().getInt(EXTRA_ID));
                mTagType = getArguments().getString(EXTRA_TAG_TYPE);
            }
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_CINEMATOGRAPHIC, mCinematographic);
        outState.putString(EXTRA_TAG_TYPE, mTagType);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.details_cinematographic_fragment, container, false);
        mToolbar = mView.findViewById(R.id.toolbar);
        mToolbar.setTitle("");

        if (null != getActivity()) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);

        }
        mToolbar.setNavigationOnClickListener(view -> getActivity().getSupportFragmentManager().popBackStack());

        mBottomSheetBehaviorSeason = setupBottomSheet(R.id.bottom_sheet_seasons);
        mBottomSheetBehaviorEpisode = setupBottomSheet(R.id.bottom_sheet_episodes);



        return mView;
    }

    private BottomSheetBehavior setupBottomSheet(@IdRes int id) {
        BottomSheetBehavior bottomSheet = BottomSheetBehavior.from(mView.findViewById(id));
        bottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheet.setPeekHeight(0);
        bottomSheet.setHideable(true);
        return bottomSheet;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(DetailsCinematographicViewModel.class);
        mViewModel.onAttachTagType(mTagType);

        if(mCinematographic == null){
            mViewModel.getDetails(mId).observe(this, this::bindFindViewById);
        }else{
            bindFindViewById(mCinematographic);
        }

    }

    private void bindFindViewById(Cinematographic c) {

        mCinematographic = c;

        if(c instanceof Movie){

            setText(R.id.tv_title, ((Movie) c).getTitle());
            setText(R.id.tv_date_release, ((Movie) c).getReleaseDate());

            setRecyclerView(R.id.rv_p_country, R.id.card_country, ((Movie) c).getProductionCountries());
            setRecyclerView(R.id.rv_similar, R.id.card_similar, ((Movie) c).getSimilar());

            mBottomSheetBehaviorSeason.setState(BottomSheetBehavior.STATE_COLLAPSED);
            mBottomSheetBehaviorEpisode.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }else{

            setText(R.id.tv_title, ((TvSeries) c).getName());
            setText(R.id.tv_date_release, ((TvSeries) c).getFirstAirDate());
            setText(R.id.tv_country, getString(R.string.orig_country));

            mView.findViewById(R.id.group_revenue).setVisibility(View.GONE);

            setRecyclerView(R.id.rv_p_country, R.id.card_country,  ((TvSeries) c).getOriginCountry());
            setRecyclerView(R.id.rv_similar, R.id.card_similar, ((TvSeries) c).getSimilar());

            ColorStateList color =  ((AppCompatTextView)mView.findViewById(R.id.text_season_episode)).getTextColors();
            mView.findViewById(R.id.open_image).getBackground().setColorFilter(color.getDefaultColor(), PorterDuff.Mode.SRC_IN);
            mView.findViewById(R.id.exit_image_bottom_sheet).getBackground().setColorFilter(color.getDefaultColor(), PorterDuff.Mode.SRC_IN);

            mView.findViewById(R.id.card_see_seasons).setVisibility(View.VISIBLE);
            mView.findViewById(R.id.card_see_seasons).setOnClickListener(v -> mBottomSheetBehaviorSeason.setState(BottomSheetBehavior.STATE_HALF_EXPANDED));
            mView.findViewById(R.id.card_exit_season).setOnClickListener(v -> mBottomSheetBehaviorSeason.setState(BottomSheetBehavior.STATE_COLLAPSED));
            mView.findViewById(R.id.card_exit_episode).setOnClickListener(v -> mBottomSheetBehaviorEpisode.setState(BottomSheetBehavior.STATE_COLLAPSED));

            setupBottomSheetSeasons();
        }

        setText(R.id.tv_overview, c.getOverview());
        setText(R.id.tv_rate_value, FormatNumberUtils.format(c.getVoteAverage()));
        setText(R.id.result_revenue, FormatNumberUtils.format(c.getRevenue()));
        setText(R.id.result_o_language, c.getOriginalLanguage());
        setText(R.id.result_popularity, FormatNumberUtils.format(c.getPopularity()));
        setText(R.id.result_avg, FormatNumberUtils.format(c.getVoteAverage()));
        setText(R.id.result_vote_count, FormatNumberUtils.format(c.getVoteCount()));

        setRecyclerView(R.id.rv_genres, R.id.card_genres,  c.getGenres());
        setRecyclerView(R.id.rv_p_companies, R.id.card_companies,  c.getProductionCompanies());
        setRecyclerView(R.id.rv_videos, R.id.card_videos,  c.getVideos());

        if(!configRecyclerView(mView.findViewById(R.id.rv_cast), c.getCredits().getCast())  ||
                !configRecyclerView(mView.findViewById(R.id.rv_crew), c.getCredits().getCrew())){
            mView.findViewById(R.id.card_credits).setVisibility(View.GONE);
        }

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



    }

    private void setupBottomSheetEpisodes() {

        RecyclerView mListEpisodes  = mView.findViewById(R.id.rv_episodes);
        mListEpisodes.setHasFixedSize(true);
        mListEpisodes.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        mListEpisodes.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), RecyclerView.VERTICAL));
        mListEpisodes.setAdapter(mAdapterEpisodesBottomSheet);
    }

    private void setupBottomSheetSeasons() {

        RecyclerView mListSeasons  = mView.findViewById(R.id.rv_seasons);
        mListSeasons.setHasFixedSize(true);
        mListSeasons.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));


        mAdapterSeasonsBottomSheet = new SeasonsAdapter(((TvSeries) mCinematographic).getSeasons());
        mAdapterSeasonsBottomSheet.setOnItemClickListener((v, position) -> mViewModel.getSeasonsAndEpisodes(mCinematographic.getId(),
                (((TvSeries) mCinematographic)).getSeasons().get(position).getSeasonNumber())
                .observe(
                        DetailsCinematographicFragment.this, season -> {
                            ((TvSeries) mCinematographic).getSeasons().set(position, season);
                            mAdapterEpisodesBottomSheet = new EpisodesAdapter(season.getEpisodes());
                            setupBottomSheetEpisodes();
                            expandBottomSheetEpisodes();
                        }));

        mListSeasons.setAdapter(mAdapterSeasonsBottomSheet);

    }

    private void expandBottomSheetEpisodes() {
        mBottomSheetBehaviorEpisode.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
    }


    private void setText(@IdRes int id, String text){
        ((AppCompatTextView) mView.findViewById(id)).setText(text);
    }

    private <T> void setRecyclerView(@IdRes int idRecyler, @IdRes int idCard, List<T> data){
        if(!configRecyclerView(mView.findViewById(idRecyler), data)){
            mView.findViewById(idCard).setVisibility(View.GONE);
        }
    }


    private <T> boolean configRecyclerView(RecyclerView rv, List<T> data){

        if(data == null || data.isEmpty()){

            return false;
        }

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
            ((CircleImageAdapterPeople) adapter).setClickListener((v, position) -> {

                int id = ((Team) data.get(position)).getId();
                inflate(
                        DetailsPeopleView.newInstance(id), Constant.TAG_FRAG_DETAILS_PEOPLE + id);

            });

        }else if(data.get(0) instanceof Genre ||
                data.get(0) instanceof ProductionCountry ||
                data.get(0) instanceof  ProductionCompany ||
                data.get(0) instanceof  String){

            adapter = configureForTagAdapter(data);

        }else if (data.get(0) instanceof Cinematographic){

            //noinspection unchecked
            Collection<Cinematographic> posteresFiltered = Collections2.filter((List<Cinematographic>)data,
                    input -> ((input != null) && input.getPosterPath()!=null && !input.getPosterPath().isEmpty()));

            adapter = new CinematographicListAdapter(new ArrayList<>(posteresFiltered));
            ((CinematographicListAdapter) adapter).setClickListener((v, position) -> {

                int id = ((mCinematographic instanceof TvSeries)?
                        ((TvSeries)mCinematographic).getSimilar().get(position).getId():
                        ((Movie)mCinematographic).getSimilar().get(position).getId());

                Fragment fragment= DetailsCinematographicFragment.newInstance(
                        id,
                        mTagType);
                inflate(fragment, Constant.TAG_FRAG_DETAILS_MOVIE + id);
            });


        }else{

            //noinspection unchecked
            Collection<Video> videosFiltered = Collections2.filter((List<Video>)data,
                    input -> ((input != null) && (input).getSite().equals(Constant.SITE_VIDEOS)));

            adapter = new VideoAdapter(new ArrayList<>(videosFiltered));
            ((VideoAdapter) adapter).setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onClick(View v, int position) {

                }
            });
            itemDecorator = new DividerItemDecoration(Objects.requireNonNull(getContext()),
                    DividerItemDecoration.VERTICAL);

            layoutManager.setOrientation(RecyclerView.VERTICAL);


        }

        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        rv.addItemDecoration(itemDecorator);

        return true;
    }


    private void inflate(Fragment fragment, String tag){
        ((MainActivity) Objects.requireNonNull(getActivity())).inflateFragment(fragment, tag);

    }

    private <T> TagAdapter configureForTagAdapter(List<T> data){

        if(!(data.get(0) instanceof String)) {

            Function<T, String> function = new Function<T, String>() {
                @NullableDecl
                @Override
                public String apply(@NullableDecl T input) {

                    if (input instanceof ProductionCompany)
                        return ((ProductionCompany) input).getName();
                    else if (input instanceof ProductionCountry)
                        return ((ProductionCountry) input).getName();
                    else
                        return ((Genre) Objects.requireNonNull(input)).getName();
                }
            };

            Collection<String> names = Collections2.transform(data, function);
            Collection<String> namesFiltered = Collections2.filter(names, input -> input != null && !input.isEmpty());
            return new TagAdapter(new ArrayList<>(namesFiltered));
        }

        return new TagAdapter((List<String>)data);

    }

    @Override
    public void onDestroy() {
        mViewModel.clear();
        super.onDestroy();
    }


}

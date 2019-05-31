package com.levez.d2u.moviestmdbviewer.Adapter;

import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.levez.d2u.moviestmdbviewer.Models.api.Constant;
import com.levez.d2u.moviestmdbviewer.Models.entity.Cinematographic;
import com.levez.d2u.moviestmdbviewer.Models.entity.Movie;
import com.levez.d2u.moviestmdbviewer.Models.entity.People;
import com.levez.d2u.moviestmdbviewer.Models.entity.Searchable;
import com.levez.d2u.moviestmdbviewer.Models.entity.TvSeries;
import com.levez.d2u.moviestmdbviewer.R;

import java.util.ArrayList;
import java.util.List;

public class ListSearchAdapter  extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {



    public interface OnSearchItemClickListener {
        void onClick(View v, int position, Searchable searchable,String tagType);
    }

    private static final int MOVIE = 0;
    private static final int TV_SERIES = 1;
    private static final int PEOPLE = 2;

    private List<Searchable> mSearchables;
    private OnSearchItemClickListener mListener;


    public ListSearchAdapter() {
        this.mSearchables = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){

            case MOVIE:
            case TV_SERIES:{
                viewHolder = new ViewHolderCinematographic(
                        inflater.inflate(R.layout.item_list_cinematographic, parent, false));
                break;
            }
            case PEOPLE:{
                viewHolder = new ViewHolderPeople(
                        inflater.inflate(R.layout.item_list_people, parent, false));
                break;
            }
            default:{
                viewHolder = new RecyclerViewSimpleTextViewHolder(
                        inflater.inflate(android.R.layout.simple_list_item_1, parent, false));
                break;
            }
        }

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){


            case MOVIE:
            case TV_SERIES:{ // TvSeries

                buildItemCinematographic((ViewHolderCinematographic) holder, position);

                break;
            }
            case PEOPLE:{ // People

                buildItemPeople((ViewHolderPeople) holder, position);

                break;
            }
        }
    }

    private void buildItemPeople(ViewHolderPeople holder, int position) {

        People p = ((People) mSearchables.get(position));

        holder.tv_title.setText(p.getName());
        Glide
                .with(holder.itemView.getContext())
                .load(Constant.BASE_URL_IMAGE + p.getProfilePath())
                .centerCrop()
                .into(holder.iv_poster);

        holder.rb_popularity.setRating(p.getPopularity().floatValue());

        holder.rb_popularity.setStepSize(0.01f);
        holder.rb_popularity.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> { });
        holder.rb_popularity.invalidate();

        holder.bindClick(position);
    }

    private void buildItemCinematographic(ViewHolderCinematographic holder, int position) {

        holder.attachTagType((getItemViewType(position) == MOVIE)?Constant.TAG_TYPE_MOVIE : Constant.TAG_TYPE_TV_SERIES);

        Cinematographic c;
        if(getItemViewType(position) == MOVIE){
            holder.attachTagType(Constant.TAG_TYPE_MOVIE);
            c = ((Movie) mSearchables.get(position));
            holder.tv_type.setText("Movie");
            holder.tv_title.setText(((Movie) c).getTitle());

        }else{
            holder.attachTagType(Constant.TAG_TYPE_TV_SERIES);
            c = ((TvSeries) mSearchables.get(position));
            holder.tv_title.setText(((TvSeries) c).getName());
            holder.tv_type.setText("Tv Series");
        }

        if(c.getOverview()==null || c.getOverview().isEmpty()){

            holder.tv_overview.setText(R.string.msg_overview_empty);

        }else {

            holder.tv_overview.setText(c.getOverview());

        }

        holder.rb_popularity.setRating(((float)(c.getVoteAverage()/2)));

        holder.rb_popularity.setStepSize(0.01f);
        holder.rb_popularity.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> { });
        holder.rb_popularity.getProgressDrawable()
                .setColorFilter(holder.itemView.getResources().getColor(R.color.colorYellow), PorterDuff.Mode.SRC_IN);
        holder.rb_popularity.invalidate();

        Glide
                .with(holder.itemView.getContext())
                .load(Constant.BASE_URL_IMAGE + c.getPosterPath())
                .centerCrop()
                .into(holder.iv_poster);

        holder.bindClick(position);
    }

    public void setOnSearchItemClickListener(OnSearchItemClickListener onSearchItemClickListener) {
        this.mListener = onSearchItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mSearchables !=null ? mSearchables.size():0;
    }

    @Override
    public int getItemViewType(int position) {


        Searchable searchable = mSearchables.get(position);

        if (searchable instanceof Movie){
            return MOVIE;
        }else if(searchable instanceof TvSeries){
            return TV_SERIES;
        }else{
            return PEOPLE;
        }
    }

    public void refresh(List<Searchable> searchables) {
        if(searchables!=null){
            this.mSearchables.addAll(searchables);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        this.mSearchables.clear();
    }

    class ViewHolderCinematographic extends BaseViewHolder {

        AppCompatTextView tv_title;
        AppCompatTextView tv_overview;
        AppCompatImageView iv_poster;
        AppCompatRatingBar rb_popularity;
        AppCompatTextView tv_type;


        ViewHolderCinematographic(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            tv_overview = itemView.findViewById(R.id.tv_overview);
            iv_poster = itemView.findViewById(R.id.iv_poster);
            rb_popularity = itemView.findViewById(R.id.rb_popularity);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_overview.setText("");
        }

    }

    class ViewHolderPeople extends BaseViewHolder {

        AppCompatTextView tv_title;
        AppCompatRatingBar rb_popularity;
        AppCompatImageView iv_poster;

        ViewHolderPeople(@NonNull View itemView) {
            super(itemView);

            this.attachTagType(Constant.TAG_TYPE_PEOPLE);
            tv_title = itemView.findViewById(R.id.tv_title);
            rb_popularity = itemView.findViewById(R.id.rb_popularity);
            iv_poster = itemView.findViewById(R.id.iv_poster);
        }



    }

    class BaseViewHolder extends RecyclerView.ViewHolder {

        private String mTagType;

        BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }


        void attachTagType(String tagType){
            this.mTagType = tagType;
        }

        void bindClick(int position){

            itemView.setOnClickListener(v -> {
                if(mListener!=null){
                    mListener.onClick(itemView, position, mSearchables.get(position),mTagType);
                }
            });

        }
    }

    private class RecyclerViewSimpleTextViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatTextView tv_label;

        RecyclerViewSimpleTextViewHolder(View itemView) {
            super(itemView);

            tv_label = itemView.findViewById(android.R.id.text1);
        }
    }
}

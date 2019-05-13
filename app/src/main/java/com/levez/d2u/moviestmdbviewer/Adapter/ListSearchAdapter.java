package com.levez.d2u.moviestmdbviewer.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.levez.d2u.moviestmdbviewer.Models.api.Constant;
import com.levez.d2u.moviestmdbviewer.Models.entity.Movie;
import com.levez.d2u.moviestmdbviewer.Models.entity.People;
import com.levez.d2u.moviestmdbviewer.Models.entity.Searchable;
import com.levez.d2u.moviestmdbviewer.Models.entity.TvSeries;
import com.levez.d2u.moviestmdbviewer.R;

import java.util.ArrayList;
import java.util.List;

public class ListSearchAdapter  extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {



    public interface OnSearchItemClickListener {
        void onClick(View v, int position, String tagType);
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

            case MOVIE:{
                viewHolder = new ViewHolderMovie(
                        inflater.inflate(R.layout.item_list_movie, parent, false));
                break;
            }
            case TV_SERIES:{
                viewHolder = new ViewHolderSeries(
                        inflater.inflate(R.layout.item_list_serie, parent, false));
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


            case MOVIE:{ // Movie

                buildItemMovie((ViewHolderMovie) holder, position);

                break;
            }
            case TV_SERIES:{ // TvSeries

                buildItemTvSerie((ViewHolderSeries) holder, position);

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

        holder.bindClick(position);
    }

    private void buildItemTvSerie(ViewHolderSeries holder, int position) {

        TvSeries ts = ((TvSeries) mSearchables.get(position));

        holder.tv_title.setText(ts.getName());
        holder.tv_overview.setText(ts.getOverview());

        Glide
                .with(holder.itemView.getContext())
                .load(Constant.BASE_URL_IMAGE + ts.getPosterPath())
                .centerCrop()
                .into(holder.iv_poster);

        holder.bindClick(position);
    }

    private void buildItemMovie(ViewHolderMovie holder, int position) {

        Movie m = ((Movie) mSearchables.get(position));

        holder.tv_title.setText(m.getTitle());
        holder.tv_overview.setText(m.getOverview());

        Glide
                .with(holder.itemView.getContext())
                .load(Constant.BASE_URL_IMAGE + m.getPosterPath())
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

    class ViewHolderMovie extends BaseViewHolder {

        AppCompatTextView tv_title;
        AppCompatTextView tv_overview;
        AppCompatImageView iv_poster;

        ViewHolderMovie(@NonNull View itemView) {
            super(itemView, Constant.TAG_TYPE_MOVIE);

            tv_title = itemView.findViewById(R.id.tv_title);
            tv_overview = itemView.findViewById(R.id.tv_overview);
            iv_poster = itemView.findViewById(R.id.iv_poster);
        }
    }

    class ViewHolderSeries extends BaseViewHolder {


        AppCompatTextView tv_title;
        AppCompatTextView tv_overview;
        AppCompatImageView iv_poster;

        ViewHolderSeries(@NonNull View itemView) {
            super(itemView, Constant.TAG_TYPE_TV_SERIES);

            tv_title = itemView.findViewById(R.id.tv_title);
            tv_overview = itemView.findViewById(R.id.tv_overview);
            iv_poster = itemView.findViewById(R.id.iv_poster);
        }
    }

    class ViewHolderPeople extends BaseViewHolder {

        AppCompatTextView tv_title;
        AppCompatTextView tv_overview;
        AppCompatImageView iv_poster;

        ViewHolderPeople(@NonNull View itemView) {
            super(itemView, Constant.TAG_TYPE_PEOPLE);


            tv_title = itemView.findViewById(R.id.tv_title);
            tv_overview = itemView.findViewById(R.id.tv_overview);
            iv_poster = itemView.findViewById(R.id.iv_poster);

            tv_overview.setText("");
        }


    }

    class BaseViewHolder extends RecyclerView.ViewHolder {

        private String mTagType;

        BaseViewHolder(@NonNull View itemView, String tagType) {
            super(itemView);
            mTagType = tagType;
        }

        void bindClick(int position){

            itemView.setOnClickListener(v -> {
                if(mListener!=null){
                    mListener.onClick(itemView, position, mTagType);
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

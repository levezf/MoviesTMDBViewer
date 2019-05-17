package com.levez.d2u.moviestmdbviewer.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.levez.d2u.moviestmdbviewer.Models.api.Constant;
import com.levez.d2u.moviestmdbviewer.Models.entity.Cinematographic;
import com.levez.d2u.moviestmdbviewer.R;

import java.util.ArrayList;
import java.util.List;

public class CinematographicListAdapter extends RecyclerView.Adapter<CinematographicListAdapter.ViewHolder> {

    private List<Cinematographic> mCinematographics;
    private OnItemClickListener mClickListener;

    public CinematographicListAdapter(List<Cinematographic> cinematographics) {
        this.mCinematographics = cinematographics;
    }

    public CinematographicListAdapter() {
        this.mCinematographics = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_poster_cinematographic,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide
                .with(viewHolder.itemView.getContext())
                .load(Constant.BASE_URL_IMAGE + mCinematographics.get(i).getPosterPath())
                .centerCrop()
                .into(viewHolder.iv_poster);

        viewHolder.bindClick();
    }

    public void setClickListener(OnItemClickListener onItemClickListener) {
        this.mClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return (mCinematographics !=null)? mCinematographics.size():0;
    }

    public void insertItems(List<Cinematographic> ts) {
        int size = mCinematographics.size();
        mCinematographics.addAll(ts);
        notifyItemRangeInserted(size, mCinematographics.size());
    }

    public Cinematographic get(int position) {
        return mCinematographics.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_poster;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_poster = itemView.findViewById(R.id.iv_poster);
        }
        void bindClick() {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mClickListener!=null)
                        mClickListener.onClick(v, getAdapterPosition());
                }
            });
        }
    }
}

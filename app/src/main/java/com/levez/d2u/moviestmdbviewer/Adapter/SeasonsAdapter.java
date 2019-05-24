package com.levez.d2u.moviestmdbviewer.Adapter;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.levez.d2u.moviestmdbviewer.Models.entity.Season;
import com.levez.d2u.moviestmdbviewer.R;

import java.util.ArrayList;
import java.util.List;

public class SeasonsAdapter  extends RecyclerView.Adapter<SeasonsAdapter.ViewHolder> {

    private List<Season> mSeasons;
    private OnItemClickListener mListener;


    public SeasonsAdapter(List<Season> seasons) {
        this.mSeasons = seasons;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_bottom_sheet_seasons,
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ColorStateList color =  holder.seasonTitle.getTextColors();
        holder.imageOpen.getBackground().setColorFilter(color.getDefaultColor(), PorterDuff.Mode.SRC_IN);
        holder.seasonTitle.setText(mSeasons.get(position).getName());
        holder.bindClick(position);
    }

    @Override
    public int getItemCount() {
        return mSeasons != null? mSeasons.size() : 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView imageOpen;
        AppCompatTextView seasonTitle;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageOpen = itemView.findViewById(R.id.open_image);
            seasonTitle = itemView.findViewById(R.id.season_title);

        }

        void bindClick(final int position) {
            itemView.setOnClickListener(v -> {
                if(mListener!=null){
                    mListener.onClick(v, position);
                }
            });
        }
    }
}

package com.levez.d2u.moviestmdbviewer.Adapter;

import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;

import com.levez.d2u.moviestmdbviewer.Models.entity.Episode;
import com.levez.d2u.moviestmdbviewer.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.ViewHolder> {

    private List<Episode> mEpisodes;
    private OnItemChangeState mOnItemChangeState;

    public interface OnItemChangeState{
        void change(boolean isCheked, View v, int position);
    }


    public EpisodesAdapter(List<Episode> episodes) {
        this.mEpisodes = episodes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bottom_sheet_episodes, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



        Episode ep = mEpisodes.get(position);

        holder.tv_locale.setText(String.format(Locale.US,"T%dE%d", ep.getSeasonNumber(), ep.getEpisodeNumber()));
        holder.tv_overview.setText(ep.getOverview());
        holder.tv_title.setText(ep.getName());

        holder.vote_avg.setRating((float)(ep.getVoteAverage()/2));

        holder.vote_avg.setStepSize(0.01f);
        holder.vote_avg.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> { });
        holder.vote_avg.getProgressDrawable()
                .setColorFilter(holder.itemView.getResources().getColor(R.color.colorYellow), PorterDuff.Mode.SRC_IN);
        holder.vote_avg.invalidate();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {

            if(format.parse(ep.getAirDate()).after(Calendar.getInstance().getTime())){
                holder.tv_overview.setVisibility(View.GONE);
                holder.group_check.setVisibility(View.GONE);
            }else{
                holder.tv_overview.setVisibility(View.VISIBLE);
                holder.group_check.setVisibility(View.VISIBLE);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(v -> holder.btn_check.performClick());

        holder.btn_check.setChecked(ep.isWatched());

        holder.btn_check.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mOnItemChangeState.change(isChecked, buttonView, position);
        });

        holder.tv_date.setText(ep.getAirDate());

    }

    public void setOnItemChangeState(OnItemChangeState onItemChangeState) {
        this.mOnItemChangeState = onItemChangeState;
    }

    @Override
    public int getItemCount() {
        return mEpisodes!=null ? mEpisodes.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final AppCompatTextView tv_date;
        final CheckBox btn_check;
        final AppCompatTextView tv_locale;
        final AppCompatRatingBar vote_avg;
        final AppCompatTextView tv_title;
        final AppCompatTextView tv_overview;
        final Group group_check;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_locale = itemView.findViewById(R.id.tv_locale);
            vote_avg = itemView.findViewById(R.id.rb_popularity);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_overview = itemView.findViewById(R.id.tv_overview);
            tv_date = itemView.findViewById(R.id.tv_date_release);
            btn_check = itemView.findViewById(R.id.btnCheck);
            group_check = itemView.findViewById(R.id.group_check);
        }
    }
}

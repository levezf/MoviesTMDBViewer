package com.levez.d2u.moviestmdbviewer.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.levez.d2u.moviestmdbviewer.Models.api.Constant;
import com.levez.d2u.moviestmdbviewer.Models.entity.HasProfilePath;
import com.levez.d2u.moviestmdbviewer.Models.entity.People;
import com.levez.d2u.moviestmdbviewer.R;

import java.util.List;

public class CircleImageAdapterPeople extends RecyclerView.Adapter<CircleImageAdapterPeople.ViewHolder> {

    private List<HasProfilePath> mTeam;
    private OnItemClickListener mClickListener;


    public CircleImageAdapterPeople(List<HasProfilePath> team) {
        this.mTeam = team;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_profile_team,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


        Glide
                .with(viewHolder.itemView.getContext())
                .load(
                        (mTeam.get(i).getProfilePath() == null|| mTeam.get(i).getProfilePath().isEmpty()) ?
                                R.drawable.no_photo :
                                Constant.BASE_URL_IMAGE + mTeam.get(i).getProfilePath()

                )
                .centerCrop()
                .into(viewHolder.iv_profile);

        viewHolder.bindClick();
    }

    public void setClickListener(OnItemClickListener onItemClickListener) {
        this.mClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return (mTeam !=null)? mTeam.size():0;
    }

    public void insertItem(HasProfilePath team) {
        mTeam.add(team);
        notifyItemInserted(getItemCount()-1);
    }

    public People getPeople(int position) {
        return (People) mTeam.get(position);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_profile;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_profile = itemView.findViewById(R.id.iv_profile);
        }
        void bindClick() {

            itemView.setOnClickListener(v -> {
                if(mClickListener!=null)
                    mClickListener.onClick(v, getAdapterPosition());
            });
        }
    }
}
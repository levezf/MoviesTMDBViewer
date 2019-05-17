package com.levez.d2u.moviestmdbviewer.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.levez.d2u.moviestmdbviewer.Models.entity.Video;
import com.levez.d2u.moviestmdbviewer.R;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private List<Video> mVideos;
    private OnItemClickListener mListener;

    public VideoAdapter(List<Video> videos) {
        this.mVideos = videos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.title.setText(mVideos.get(position).getName());
        holder.type.setText(mVideos.get(position).getType());

        holder.bindClick(position);
    }

    @Override
    public int getItemCount() {
        return mVideos != null ? mVideos.size() : 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView title;
        AppCompatTextView type;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            type = itemView.findViewById(R.id.type);
        }

        void bindClick(int position){

            itemView.setOnClickListener((v) -> {if( mListener!=null) mListener.onClick(v, position);});
        }
    }
}

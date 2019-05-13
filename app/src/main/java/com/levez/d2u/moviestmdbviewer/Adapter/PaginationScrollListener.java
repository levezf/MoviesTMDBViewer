package com.levez.d2u.moviestmdbviewer.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PaginationScrollListener extends RecyclerView.OnScrollListener {

    public interface OnScrollListener{

        void loadMoreItems();
    }


    private boolean mIsScrolling;
    private LinearLayoutManager mLayoutManager;
    private com.levez.d2u.categoryhorizontalview.PaginationScrollListener.OnScrollListener mListener;

    public PaginationScrollListener(LinearLayoutManager layoutManager, com.levez.d2u.categoryhorizontalview.PaginationScrollListener.OnScrollListener onScrollListener) {
        this.mLayoutManager = layoutManager;
        mListener = onScrollListener;
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        mIsScrolling = true;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);


        int visibleItemCount = mLayoutManager.getChildCount();
        int totalItemCount = mLayoutManager.getItemCount();
        int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

        if (mIsScrolling &&
                (visibleItemCount + firstVisibleItemPosition + 5) >= totalItemCount &&
                firstVisibleItemPosition >= 0) {

            mIsScrolling = false;

            if(mListener!=null)
                mListener.loadMoreItems();
        }

    }
}
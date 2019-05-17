package com.levez.d2u.moviestmdbviewer.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.levez.d2u.categoryhorizontalview.CategoryHorizontalView;
import com.levez.d2u.moviestmdbviewer.Models.entity.Cinematographic;
import com.levez.d2u.moviestmdbviewer.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListCategoryViewAdapter  extends RecyclerView.Adapter<ListCategoryViewAdapter.ViewHolder> {

    private ArrayList<CategoryHorizontalView> mCategories = new ArrayList<>();
    private ArrayList<String> mTags = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_category_list_view,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final CategoryHorizontalView c = mCategories.get(i);

        viewHolder.category.setTitle(c.getTitle());
        viewHolder.category.setOnCategoryPagingListener(c.getOnCategoryPagingListener());
        viewHolder.category.disableShowMore(true);

        viewHolder.category.startRecyclerView(c.getAdapter());

        if(c.isProgressShow()){
            viewHolder.category.showProgress();
        }else{
            viewHolder.category.hideProgress();
        }

    }

    @Override
    public int getItemCount() {
        return (mCategories !=null)? mCategories.size():0;
    }

    public void insertItem(CategoryHorizontalView categoryHorizontalView, String tag) {
        mTags.add(tag);
        mCategories.add(categoryHorizontalView);
        notifyItemInserted(getItemCount()-1);
    }

    public void notifyItemChange(String tag) {
        notifyItemChanged(mTags.indexOf(tag));
    }

    public void showProgress(String tag) {
        int index = mTags.indexOf(tag);
        mCategories.get(index).showProgress();
        notifyItemChanged(index);
    }

    public void hideProgress(String tag, List<Cinematographic> cs) {


        if(!mTags.isEmpty()) {

            int index = mTags.indexOf(tag);
            if(index!=-1) {
                mCategories.get(index).hideProgress();
                notifyItemChanged(index);
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CategoryHorizontalView category;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category);
        }
    }
}

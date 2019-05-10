package com.levez.d2u.moviestmdbviewer.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.levez.d2u.categoryhorizontalview.CategoryHorizontalView;
import com.levez.d2u.moviestmdbviewer.R;

import java.util.ArrayList;

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
        mCategories.add(categoryHorizontalView);
        mTags.add(tag);
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

    public void hideProgress(String tag) {
        int index = mTags.indexOf(tag);

        if(mCategories.get(index).getAdapter().getItemCount() == 0){
            mCategories.get(index).hideProgress();
            notifyItemChanged(index);
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

package com.levez.d2u.categoryhorizontalview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.levez.d2u.categoryhorizontallibrary.R;

import java.util.Objects;

public class CategoryHorizontalView extends FrameLayout {


    private String mTitle;
    private AppCompatTextView mTextViewTitle;
    private RecyclerView mRecyclerViewList;
    private AppCompatTextView mBtnShowMore;
    private setOnCategoryPagingListener mListener;
    private RecyclerView.Adapter mAdapter;
    private ProgressBar mProgress;
    private View mView;
    private ConstraintLayout mHeader;

    public CategoryHorizontalView(@NonNull Context context) {
        super(context);
        initView();
    }

    public CategoryHorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CategoryHorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CategoryHorizontalView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CategoryHorizontalView);

        mTitle = typedArray.getString ( R.styleable.CategoryHorizontalView_title);

        typedArray.recycle();

        initView();
    }

    private void initView() {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.component_category_view, this, true);

        mTextViewTitle = mView.findViewById(R.id.tv_title);
        mRecyclerViewList = mView.findViewById(R.id.rv_list);
        mBtnShowMore = mView.findViewById(R.id.btn_show_more);
        mProgress = mView.findViewById(R.id.progress_circular);

        if(mTitle!=null)
            mTextViewTitle.setText(mTitle);
    }

    public void startRecyclerView(RecyclerView.Adapter adapter){

        mRecyclerViewList.setHasFixedSize(true);
        this.mAdapter = adapter;



        LinearLayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        mRecyclerViewList.setLayoutManager(layout);
        mRecyclerViewList.setAdapter(adapter);


        if(mRecyclerViewList.getItemDecorationCount()==0) {
            DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(),
                    DividerItemDecoration.HORIZONTAL);
            itemDecorator.setDrawable(getResources().getDrawable(R.drawable.divider_space));
            mRecyclerViewList.addItemDecoration(itemDecorator);
        }

        mRecyclerViewList.addOnScrollListener(new PaginationScrollListener(layout, new PaginationScrollListener.OnScrollListener() {
            @Override
            public void loadMoreItems() {
                if(mListener!=null){
                    mListener.loadMoreItems();
                }
            }
        }));
    }

    public void setShowMoreClickListener(View.OnClickListener listener){
        mBtnShowMore.setOnClickListener(listener);
    }

    public void setTitle(String title){
        mTitle = title;
        mTextViewTitle.setText(mTitle);
    }

    public void  setTitle(@StringRes int title){
        mTitle = getResources().getString(title);
        mTextViewTitle.setText(mTitle);
    }

    public void disableShowMore(boolean disable){
        if(disable)
            mBtnShowMore.setVisibility(GONE);
    }

    public void setOnCategoryPagingListener(setOnCategoryPagingListener onCategoryPagingListener) {
        this.mListener = onCategoryPagingListener;
    }

    public String getTitle() {
        return mTitle;
    }

    public setOnCategoryPagingListener getOnCategoryPagingListener() {
        return mListener;
    }

    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    public void showProgress() {
        mProgress.setVisibility(VISIBLE);
    }

    public void hideProgress() {
        mProgress.setVisibility(INVISIBLE);
    }

    public boolean isProgressShow() {
        return mProgress.getVisibility()==VISIBLE;
    }



    public interface setOnCategoryPagingListener {
        void loadMoreItems();
    }
}

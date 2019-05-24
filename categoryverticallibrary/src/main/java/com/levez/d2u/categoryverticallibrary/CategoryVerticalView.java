package com.levez.d2u.categoryverticallibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.levez.d2u.categoryverticallibrary.AnimationUtils.collapse;
import static com.levez.d2u.categoryverticallibrary.AnimationUtils.expand;


public class CategoryVerticalView extends FrameLayout implements View.OnClickListener {


    public interface OnChangeStateListener {

        void changeState();

        void expanded();

        void collapsed();
    }

    private String mTitle;
    private AppCompatTextView mTextViewTitle;
    private RecyclerView mRecyclerViewList;
    private AppCompatCheckBox mBtnCheckAll;
    private RecyclerView.Adapter mAdapter;
    private ProgressBar mProgress;
    private AppCompatImageView mButtonExpand;
    private OnChangeStateListener mOnChangeStateListener;
    private boolean mIsExpand = false;
    private View mView;
    private OnCategoryVerticalListener mListener;
    private ConstraintLayout mContainerHeaderView;
    private FrameLayout mContainerChildView;

    public CategoryVerticalView(@NonNull Context context) {
        super(context);
        initView();
    }

    public CategoryVerticalView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CategoryVerticalView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CategoryVerticalView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CategoryVerticalView);

        mTitle = typedArray.getString ( R.styleable.CategoryVerticalView_title);

        typedArray.recycle();

        initView();
        initActions();

    }


    private void initView() {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.component_category_vertical_view, this, true);

        mTextViewTitle = mView.findViewById(R.id.tv_title);
        mRecyclerViewList = mView.findViewById(R.id.rv_list);
        //mBtnCheckAll = mView.findViewById(R.id.btnCheckAll);
        mProgress = mView.findViewById(R.id.progress_circular);
        mContainerHeaderView =  mView.findViewById(R.id.container_header);
        mContainerChildView = mView.findViewById(R.id.container_child);
        mButtonExpand = mView.findViewById(R.id.btn_expand);

        if(mTitle!=null)
            mTextViewTitle.setText(mTitle);

       /* mBtnCheckAll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null)
                    mListener.onCheckAll(v);
            }
        });*/
        mContainerChildView.setVisibility(GONE);

    }

    public void startRecyclerView(RecyclerView.Adapter adapter){

        mRecyclerViewList.setHasFixedSize(true);
        this.mAdapter = adapter;

        LinearLayoutManager layout = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        mRecyclerViewList.setLayoutManager(layout);
        mRecyclerViewList.setAdapter(adapter);
        mRecyclerViewList.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
    }

    private void initActions() {

        mButtonExpand.setOnClickListener(this);


        mContainerHeaderView.setFocusable(true);
        mContainerHeaderView.setClickable(true);
        mContainerHeaderView.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        changeState();

    }

    private void changeState(){

        if(mOnChangeStateListener!=null)
            mOnChangeStateListener.changeState();

        if(mIsExpand){
            mIsExpand = false;

            if(mOnChangeStateListener !=null)
                mOnChangeStateListener.collapsed();
            animCollapse();

        }else{
            mIsExpand = true;
            mContainerChildView.setVisibility(VISIBLE);

            if(mOnChangeStateListener !=null)
                mOnChangeStateListener.expanded();
            animExpand();
        }
    }

    private void animExpand(){
        mButtonExpand.animate().rotation(180F).setInterpolator(new AccelerateDecelerateInterpolator());
        expand(mContainerHeaderView,mContainerChildView);
    }


    private void animCollapse(){
        mButtonExpand.animate().rotation(0).setInterpolator(new AccelerateDecelerateInterpolator());
        collapse(mContainerHeaderView,mContainerChildView);
    }


    private int getPixelsFromDPs(float dp){
        return (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics()));
    }


    public void setIsExpand(boolean isExpand) {
        this.mIsExpand = isExpand;
        if(isExpand){
            animExpand();
        }else{
            animCollapse();
        }
    }

    public void setTitle(String title){
        mTitle = title;
        mTextViewTitle.setText(mTitle);
    }

    public void  setTitle(@StringRes int title){
        mTitle = getResources().getString(title);
        mTextViewTitle.setText(mTitle);
    }

    public String getTitle() {
        return mTitle;
    }

    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    public void showProgress() {
        mProgress.setVisibility(VISIBLE);
    }

    public void hideProgress() {
       // mProgress.setVisibility(INVISIBLE);
       // mAdapter.notifyDataSetChanged();
        animExpand();

    }

    public boolean isProgressShow() {
        return mProgress.getVisibility()==VISIBLE;
    }

    public OnCategoryVerticalListener getOnCategoryVerticalListener() {
        return mListener;
    }

    public void setOnCategoryVerticalListener(OnCategoryVerticalListener onCategoryVerticalListener) {
        this.mListener = onCategoryVerticalListener;
    }

    public OnChangeStateListener getOnChangeStateListener() {
        return mOnChangeStateListener;
    }

    public void setOnChangeStateListener(OnChangeStateListener onChangeStateListener) {
        this.mOnChangeStateListener = onChangeStateListener;
    }
}

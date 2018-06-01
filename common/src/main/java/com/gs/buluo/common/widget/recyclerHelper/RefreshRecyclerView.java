package com.gs.buluo.common.widget.recyclerHelper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.gs.buluo.common.R;
import com.gs.buluo.common.widget.StatusLayout;


/**
 * Created by hjn on 2017/7/10.
 */

public class RefreshRecyclerView extends FrameLayout {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private BaseQuickAdapter mAdapter;
    private StatusLayout statusLayout;

    public RefreshRecyclerView(Context context) {
        this(context, null);
    }

    public RefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StateLayout, 0, 0);
        Drawable errorDrawable;
        Drawable emptyDrawable;
        Drawable loginDrawable;
        try {
            errorDrawable = a.getDrawable(R.styleable.RefreshRecyclerView_refreshErrorDrawable);
            emptyDrawable = a.getDrawable(R.styleable.RefreshRecyclerView_refreshEmptyDrawable);
            loginDrawable = a.getDrawable(R.styleable.RefreshRecyclerView_refreshLoginDrawable);
        } finally {
            a.recycle();
        }
        statusLayout.getEmptyImageView().setImageDrawable(emptyDrawable);
        statusLayout.getErrorImageView().setImageDrawable(errorDrawable);
        statusLayout.getLoginImageView().setImageDrawable(loginDrawable);
    }

    public RefreshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = inflate(context, R.layout.common_status_refresh_recycler, this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.recycler_swipe);
        statusLayout = (StatusLayout) view.findViewById(R.id.recycler_status);
        mSwipeRefreshLayout.setEnabled(false);
        setSwipeRefreshColorsFromRes(R.color.common_custom_color, R.color.common_custom_color_shallow, R.color.common_night_blue
        );
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setSwipeRefreshColorsFromRes(@ColorRes int... colors) {
        mSwipeRefreshLayout.setColorSchemeResources(colors);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.white);
    }

    public void setAdapter(BaseQuickAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
        mAdapter = adapter;
        mAdapter.bindToRecyclerView(mRecyclerView);
    }

    public void setRefreshAction(final OnRefreshListener action) {
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                action.onAction();
            }
        });
    }

    //刷新完成先调
    public void setRefreshFinished() {
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.setEnableLoadMore(true);
        mAdapter.clearData();
    }

    public SwipeRefreshLayout getRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public void showEmptyView() {
        statusLayout.showEmptyView();
    }

    public void showEmptyView(String msg) {
        statusLayout.showEmptyView(msg);
    }

    public void showErrorView() {
        statusLayout.showErrorView();
    }

    public void showErrorView(String msg) {
        statusLayout.showErrorView(msg);
    }

    public void showContentView() {
        statusLayout.showContentView();
    }

    public StatusLayout getStatusLayout() {
        return statusLayout;
    }

    public void showProgressView() {
        statusLayout.showProgressView();
    }
}

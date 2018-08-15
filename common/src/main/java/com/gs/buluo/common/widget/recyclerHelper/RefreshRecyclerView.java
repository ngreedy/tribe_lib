package com.gs.buluo.common.widget.recyclerHelper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.gs.buluo.common.R;
import com.gs.buluo.common.widget.StatusLayout;
import com.gs.buluo.common.widget.recyclerHelper.refreshLayout.EasyRefreshLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by hjn on 2017/7/10.
 */

public class RefreshRecyclerView extends FrameLayout {
    private EasyRefreshLayout mSwipeRefreshLayout;
    private EaeRecyclerView mRecyclerView;
    private BaseQuickAdapter mAdapter;

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
        mRecyclerView.setEmptyDrawable(emptyDrawable);
        mRecyclerView.setErrorDrawable(errorDrawable);
        mRecyclerView.setLoginDrawable(loginDrawable);
    }

    public RefreshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = inflate(context, R.layout.common_status_refresh_recycler, this);
        mRecyclerView = (EaeRecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeRefreshLayout = (EasyRefreshLayout) view.findViewById(R.id.recycler_swipe);
        mSwipeRefreshLayout.setEnabled(false);
        setSwipeRefreshColorsFromRes(R.color.common_custom_color, R.color.common_custom_color_shallow, R.color.common_night_blue
        );
    }

    public EaeRecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setSwipeRefreshColorsFromRes(@ColorRes int... colors) {
//        mSwipeRefreshLayout.setColorSchemeResources(colors);
//        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.white);
    }

    public void setAdapter(BaseQuickAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
        mAdapter = adapter;
        mAdapter.bindToRecyclerView(mRecyclerView);
    }

    public void setRefreshAction(final OnRefreshListener action) {
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        action.onAction();
                        mSwipeRefreshLayout.refreshComplete();
                    }
                }, 1000);

            }
        });
    }

    //刷新完成先调
    public void setRefreshFinished() {
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.setEnableLoadMore(true);
        mAdapter.clearData();
    }

    public EasyRefreshLayout getRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public void showEmptyView() {
        mRecyclerView.showEmptyView();
    }

    public void showEmptyView(String msg) {
        mRecyclerView.showEmptyView(msg);
    }

    public void showErrorView() {
        mRecyclerView.showErrorView();
    }

    public void showErrorView(String msg) {
        mRecyclerView.showErrorView(msg);
    }

    public void showContentView() {
        mRecyclerView.showContentView();
    }

    public void showProgressView() {
        mRecyclerView.showProgressView();
    }
    public void showProgressView(String msg) {
        mRecyclerView.showProgressView(msg);
    }
}

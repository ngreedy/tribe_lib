package com.gs.buluo.common.widget.loadMoreRecycle;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gs.buluo.common.R;


/**
 * Created by hjn on 2016/11/16.
 */
public class RefreshRecyclerView extends FrameLayout {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private boolean loadMoreAble = false;
    private View empty;
    private TextView msg;

    public RefreshRecyclerView(Context context) {
        this(context, null);
    }

    public RefreshRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = inflate(context, R.layout.view_refresh_recycler, this);
        empty = view.findViewById(R.id.empty_view);
        msg = (TextView) view.findViewById(R.id.empty_view_text);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycle);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.my_swipe);
        mSwipeRefreshLayout.setEnabled(false);
        setSwipeRefreshColorsFromRes(R.color.main_tab,R.color.custom_color,R.color.custom_color_shallow);
//        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener(){
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                int topRowVerticalPosition =
//                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
//                mSwipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
//            }
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//        });
    }

    public void showNoData(int message){
        empty.setVisibility(VISIBLE);
        msg.setText(message);
    }
    public void showNoData(String message){
        empty.setVisibility(VISIBLE);
        msg.setText(message);
    }

    public void setAdapter(RecyclerAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
        mAdapter = adapter;
        mAdapter.loadMoreAble = loadMoreAble;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void setRefreshAction(final Action action) {
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.isRefreshing = true;
                action.onAction();
            }
        });
    }

    public void setLoadMoreAction(final Action action) {
        loadMoreAble=true;
        if (mAdapter.isShowNoMore) {
            return;
        }
        mAdapter.loadMoreAble = true;
        mAdapter.setLoadMoreAction(action);
    }

    public void showNoMore() {
        mAdapter.showNoMore();
    }


    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public TextView getNoMoreView(){
        return mAdapter.mNoMoreView;
    }

    public void setSwipeRefreshColorsFromRes(@ColorRes int... colors) {
        mSwipeRefreshLayout.setColorSchemeResources(colors);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.white);
    }

    /**
     * 8位16进制数 ARGB
     */
    public void setSwipeRefreshColors(@ColorInt int... colors) {
        mSwipeRefreshLayout.setColorSchemeColors(colors);
    }

    public void showSwipeRefresh() {
        mSwipeRefreshLayout.setEnabled(true);
    }

    public void dismissSwipeRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
        empty.setVisibility(GONE);
    }

}

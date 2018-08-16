package com.gs.buluo.common.widget.recyclerHelper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.gs.buluo.common.R;
import com.gs.buluo.common.widget.recyclerHelper.statusAdapter.EaeEntity;
import com.gs.buluo.common.widget.recyclerHelper.statusAdapter.StatusEmptyAdapter;
import com.gs.buluo.common.widget.recyclerHelper.statusAdapter.StatusErrorAdapter;
import com.gs.buluo.common.widget.recyclerHelper.statusAdapter.StatusLoginAdapter;
import com.gs.buluo.common.widget.recyclerHelper.statusAdapter.StatusProgressAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Solang on 2018/8/14.
 */

public class EaeRecyclerView extends RecyclerView {
    private StatusEmptyAdapter emptyViewAdapter;
    private StatusErrorAdapter errorViewAdapter;
    private StatusProgressAdapter progressViewAdapter;
    private StatusLoginAdapter loginViewAdapter;
    private Adapter dataAdapter;
    int progressViewId;
    Drawable errorDrawable;
    Drawable emptyDrawable;
    Drawable loginDrawable;
    private boolean isLoginActVisible;
    private BaseQuickAdapter.OnItemClickListener loginClickListener;
    private boolean isErrorActVisible;
    private BaseQuickAdapter.OnItemClickListener errorClickListener;
    private boolean isEmptyActVisible;
    private BaseQuickAdapter.OnItemClickListener emptyClickListener;
    private int errBacId;
    private int empBacId;
    private int[] empMargin;
    private int[] logMargin;
    private int[] errMargin;
    private int[] proMargin;
    private String empActText;
    private String errActText;
    private String logActText;

    public EaeRecyclerView(Context context) {
        this(context, null);
    }

    public EaeRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EaeRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StateLayout, 0, 0);
        try {
            errorDrawable = a.getDrawable(R.styleable.StateLayout_errorDrawable);
            emptyDrawable = a.getDrawable(R.styleable.StateLayout_emptyDrawable);
            loginDrawable = a.getDrawable(R.styleable.StateLayout_loginDrawable);
            progressViewId = a.getResourceId(R.styleable.StateLayout_progressView, -1);
        } finally {
            a.recycle();
        }
    }


    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        this.dataAdapter = adapter;
    }

    public void resetEaeView() {
        super.setAdapter(dataAdapter);
    }

    public void showEmptyView() {
        showEmptyView(null);
    }

    public void showEmptyView(String msg) {
        List<EaeEntity> data = new ArrayList<>();
        EaeEntity entity = new EaeEntity(msg, emptyDrawable, isEmptyActVisible,empActText, empBacId, empMargin);
        data.add(entity);
        emptyViewAdapter = new StatusEmptyAdapter(R.layout.status_view_empty, data);
        super.setAdapter(emptyViewAdapter);
        if (isEmptyActVisible) {
            emptyViewAdapter.setOnItemClickListener(emptyClickListener);
        }
    }

    public void showErrorView() {
        showErrorView(null);
    }

    public void showErrorView(String msg) {
        List<EaeEntity> data = new ArrayList<>();
        EaeEntity entity = new EaeEntity(msg, errorDrawable, isErrorActVisible, errActText, errBacId, errMargin);
        data.add(entity);
        errorViewAdapter = new StatusErrorAdapter(R.layout.status_view_error, data);
        super.setAdapter(errorViewAdapter);
        if (isErrorActVisible) {
            errorViewAdapter.setOnItemClickListener(errorClickListener);
        }
    }

    public void showProgressView() {
        showProgressView(null);
    }

    public void showProgressView(String msg) {
        List<EaeEntity> data = new ArrayList<>();
        EaeEntity entity = new EaeEntity(msg, null, false, null, 0, proMargin);
        data.add(entity);
        if (progressViewId != -1) {
            progressViewAdapter = new StatusProgressAdapter(progressViewId, data);
        } else {
            progressViewAdapter = new StatusProgressAdapter(R.layout.status_view_progress, data);
        }
        super.setAdapter(progressViewAdapter);
    }

    public void showLoginView() {
        showLoginView(null);
    }

    public void showLoginView(String msg) {
        List<EaeEntity> data = new ArrayList<>();
        EaeEntity entity = new EaeEntity(msg, loginDrawable, isLoginActVisible, logActText, 0, logMargin);
        data.add(entity);
        loginViewAdapter = new StatusLoginAdapter(R.layout.status_view_login, data);
        super.setAdapter(loginViewAdapter);
        if (isLoginActVisible) {
            loginViewAdapter.setOnItemClickListener(loginClickListener);
        }
    }

    public void setLoginAction(final OnClickListener onLoginButtonClickListener) {
        isLoginActVisible = true;
        loginClickListener = new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                onLoginButtonClickListener.onClick(view);
            }
        };
    }

    public void setErrorAction(final OnClickListener onErrorButtonClickListener) {
        isErrorActVisible = true;
        errorClickListener = new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                onErrorButtonClickListener.onClick(view);
            }
        };
    }

    public void setEmptyAction(final OnClickListener onEmptyButtonClickListener) {
        isEmptyActVisible = true;
        emptyClickListener = new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                onEmptyButtonClickListener.onClick(view);
            }
        };
    }


    public void setErrorAndEmptyAction(final OnClickListener errorAndEmptyAction) {
        isEmptyActVisible = true;
        emptyClickListener = new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                errorAndEmptyAction.onClick(view);
            }
        };
        isErrorActVisible = true;
        errorClickListener = new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                errorAndEmptyAction.onClick(view);
            }
        };
    }


    public void setErrorActionBackgroud(int resId) {
        errBacId = resId;
    }

    public void setEmptyActionBackground(int resId) {
        empBacId = resId;
    }

    public void setEmptyActViewVisible(boolean isVisible) {
        isEmptyActVisible = isVisible;
    }

    public void setErrorActViewVisible(boolean isVisible) {
        isErrorActVisible = isVisible;
    }

    public void setLoginActViewVisible(boolean isVisible) {
        isLoginActVisible = isVisible;
    }
    public void setEmptyActViewText(String text) {
        empActText = text;
    }

    public void setErrorActViewText(String text) {
        errActText = text;
    }

    public void setLoginActViewText(String text) {
        logActText = text;
    }


    public void setEmptyContentViewMargin(int left, int top, int right, int bottom) {
        empMargin = new int[]{left, top, right, bottom};
    }

    public void setLoginContentViewMargin(int left, int top, int right, int bottom) {
        logMargin = new int[]{left, top, right, bottom};
    }

    public void setErrorContentViewMargin(int left, int top, int right, int bottom) {
        errMargin = new int[]{left, top, right, bottom};
    }

    public void setProgressContentViewMargin(int left, int top, int right, int bottom) {
        proMargin = new int[]{left, top, right, bottom};
    }

    public void setInfoContentViewMargin(int left, int top, int right, int bottom) {
        setEmptyContentViewMargin(left, top, right, bottom);
        setErrorContentViewMargin(left, top, right, bottom);
        setProgressContentViewMargin(left, top, right, bottom);
    }

    public void showContentView() {
        resetEaeView();
    }

    public void setEmptyDrawable(Drawable emptyDrawable) {
        this.emptyDrawable = emptyDrawable;
    }

    public void setErrorDrawable(Drawable emptyDrawable) {
        this.errorDrawable = emptyDrawable;
    }

    public void setLoginDrawable(Drawable emptyDrawable) {
        this.loginDrawable = emptyDrawable;
    }
}

package com.gs.buluo.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gs.buluo.common.R;

/**
 * Created by hjn on 2017/5/3.
 */

public class StatusLayout extends FrameLayout {
    private View contentView;

    private View emptyView;
    private View emptyContentView;

    private View errorView;
    private View errorContentView;

    private View loginView;
    private View loginContentView;


    private View progressView;
    private View progressContentView;

    private TextView emptyTextView;
    private TextView emptyActView;
    private TextView errorTextView;
    private TextView errorActView;
    private TextView loginTextView;
    private TextView loginActView;
    private TextView progressTextView;

    private ImageView errorImageView;
    private ImageView emptyImageView;
    private ImageView loginImageView;
    private ProgressBar progressBar;


    private View currentShowingView;

    public StatusLayout(@NonNull Context context) {
        this(context, null);
    }

    public StatusLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        parseAttrs(context, attrs);

        emptyView.setVisibility(View.GONE);

        errorView.setVisibility(View.GONE);

        progressView.setVisibility(View.GONE);

        loginView.setVisibility(View.GONE);

        currentShowingView = contentView;
    }

    private void parseAttrs(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(context);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StateLayout, 0, 0);
        int progressViewId;
        Drawable errorDrawable;
        Drawable emptyDrawable;
        Drawable loginDrawable;
        try {
            errorDrawable = a.getDrawable(R.styleable.StateLayout_errorDrawable);
            emptyDrawable = a.getDrawable(R.styleable.StateLayout_emptyDrawable);
            loginDrawable = a.getDrawable(R.styleable.StateLayout_loginDrawable);
            progressViewId = a.getResourceId(R.styleable.StateLayout_progressView, -1);
        } finally {
            a.recycle();
        }

        if (progressViewId != -1) {
            progressView = inflater.inflate(progressViewId, this, false);
        } else {
            progressView = inflater.inflate(R.layout.status_view_progress, this, false);
            progressBar = (ProgressBar) progressView.findViewById(R.id.progress_wheel);
            progressTextView = (TextView) progressView.findViewById(R.id.progressTextView);
            progressContentView = progressView.findViewById(R.id.progress_content);
        }

        addView(progressView);

        errorView = inflater.inflate(R.layout.status_view_error, this, false);
        errorContentView = errorView.findViewById(R.id.error_content);
        errorTextView = (TextView) errorView.findViewById(R.id.errorTextView);
        errorActView = (TextView) errorView.findViewById(R.id.error_click_view);
        errorImageView = (ImageView) errorView.findViewById(R.id.errorImageView);
        if (errorDrawable != null) {
            errorImageView.setImageDrawable(errorDrawable);
        }

        addView(errorView);

        emptyView = inflater.inflate(R.layout.status_view_empty, this, false);
        emptyContentView = emptyView.findViewById(R.id.empty_content);
        emptyTextView = (TextView) emptyView.findViewById(R.id.emptyTextView);
        emptyImageView = (ImageView) emptyView.findViewById(R.id.emptyImageView);
        emptyActView = (TextView) emptyView.findViewById(R.id.empty_click_view);
        if (emptyDrawable != null) {
            emptyImageView.setImageDrawable(emptyDrawable);
        }


        addView(emptyView);

        loginView = inflater.inflate(R.layout.status_view_login, this, false);
        loginContentView = loginView.findViewById(R.id.login_content);
        loginTextView = (TextView) loginView.findViewById(R.id.loginTextView);
        loginImageView = (ImageView) loginView.findViewById(R.id.loginImageView);
        loginActView = (TextView) loginView.findViewById(R.id.login_click_view);
        if (loginDrawable != null) {
            loginImageView.setImageDrawable(loginDrawable);
        }
        addView(loginView);
    }

    private void checkIsContentView(View view) {
        if (contentView == null && view != errorView && view != progressView && view != emptyView && view != loginView) {
            contentView = view;
            currentShowingView = contentView;
        }
    }

    public ImageView getErrorImageView() {
        return errorImageView;
    }

    public ImageView getLoginImageView() {
        return loginImageView;
    }
    public ImageView getEmptyImageView() {
        return emptyImageView;
    }

    public void setViewSwitchAnimProvider(ViewAnimProvider viewSwitchAnimProvider) {
        if (viewSwitchAnimProvider != null) {
            this.showAnimation = viewSwitchAnimProvider.showAnimation();
            this.hideAnimation = viewSwitchAnimProvider.hideAnimation();
        }
    }

    public boolean isShouldPlayAnim() {
        return shouldPlayAnim;
    }

    public void setShouldPlayAnim(boolean shouldPlayAnim) {
        this.shouldPlayAnim = shouldPlayAnim;
    }

    private boolean shouldPlayAnim = true;
    private Animation hideAnimation;
    private Animation showAnimation;

    public Animation getShowAnimation() {
        return showAnimation;
    }

    public void setShowAnimation(Animation showAnimation) {
        this.showAnimation = showAnimation;
    }

    public Animation getHideAnimation() {
        return hideAnimation;
    }

    public void setHideAnimation(Animation hideAnimation) {
        this.hideAnimation = hideAnimation;
    }

    private void switchWithAnimation(final View toBeShown) {
        final View toBeHided = currentShowingView;
        if (toBeHided == toBeShown)
            return;
        if (shouldPlayAnim) {
            if (toBeHided != null) {
                if (hideAnimation != null) {
                    hideAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            toBeHided.setVisibility(GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    hideAnimation.setFillAfter(false);
                    toBeHided.startAnimation(hideAnimation);
                } else
                    toBeHided.setVisibility(GONE);
            }
            if (toBeShown != null) {
                if (toBeShown.getVisibility() != VISIBLE)
                    toBeShown.setVisibility(VISIBLE);
                currentShowingView = toBeShown;
                if (showAnimation != null) {
                    showAnimation.setFillAfter(false);
                    toBeShown.startAnimation(showAnimation);
                }
            }
        } else {
            if (toBeHided != null) {
                toBeHided.setVisibility(GONE);
            }
            if (toBeShown != null) {
                currentShowingView = toBeShown;
                toBeShown.setVisibility(VISIBLE);
            }
        }

    }

    public void setEmptyContentViewMargin(int left, int top, int right, int bottom) {
        ((LinearLayout.LayoutParams) emptyImageView.getLayoutParams()).setMargins(left, top, right, bottom);
    }

    public void setLoginContentViewMargin(int left, int top, int right, int bottom) {
        ((LinearLayout.LayoutParams) loginImageView.getLayoutParams()).setMargins(left, top, right, bottom);
    }

    public void setErrorContentViewMargin(int left, int top, int right, int bottom) {
        ((LinearLayout.LayoutParams) errorImageView.getLayoutParams()).setMargins(left, top, right, bottom);
    }

    public void setProgressContentViewMargin(int left, int top, int right, int bottom) {
        if (progressBar != null)
            ((LinearLayout.LayoutParams) progressBar.getLayoutParams()).setMargins(left, top, right, bottom);
    }

    public void setInfoContentViewMargin(int left, int top, int right, int bottom) {
        setEmptyContentViewMargin(left, top, right, bottom);
        setErrorContentViewMargin(left, top, right, bottom);
        setProgressContentViewMargin(left, top, right, bottom);
    }

    public void showContentView() {
        switchWithAnimation(contentView);
    }

    public void showEmptyView() {
        showEmptyView(null);
    }

    public void showEmptyView(String msg) {
        onHideContentView();
        if (!TextUtils.isEmpty(msg))
            emptyTextView.setText(msg);
        switchWithAnimation(emptyView);
    }

    public void showErrorView() {
        showErrorView(null);
    }

    public void showErrorView(String msg) {
        onHideContentView();
        if (msg != null)
            errorTextView.setText(msg);
        switchWithAnimation(errorView);
    }

    public void showLoginView(String msg) {
        onHideContentView();
        if (msg != null)
            loginTextView.setText(msg);
        switchWithAnimation(loginView);
    }

    public void showLoginView() {
        showLoginView(null);
    }


    public void showProgressView() {
        showProgressView(null);
    }

    public void showProgressView(String msg) {
        onHideContentView();
        if (msg != null)
            progressTextView.setText(msg);
        switchWithAnimation(progressView);
    }


    public void setLoginAction(final OnClickListener onLoginButtonClickListener) {
        findViewById(R.id.login_click_view).setVisibility(VISIBLE);
        loginView.setOnClickListener(onLoginButtonClickListener);
        loginActView.setOnClickListener(onLoginButtonClickListener);
    }

    public void setErrorAction(final OnClickListener onErrorButtonClickListener) {
        findViewById(R.id.error_click_view).setVisibility(VISIBLE);
        errorView.setOnClickListener(onErrorButtonClickListener);
        errorActView.setOnClickListener(onErrorButtonClickListener);
    }

    public void setEmptyAction(final OnClickListener onEmptyButtonClickListener) {
        findViewById(R.id.empty_click_view).setVisibility(VISIBLE);
        emptyView.setOnClickListener(onEmptyButtonClickListener);
        emptyActView.setOnClickListener(onEmptyButtonClickListener);
    }


    public void setErrorAndEmptyAction(final OnClickListener errorAndEmptyAction) {
        findViewById(R.id.error_click_view).setVisibility(VISIBLE);
        findViewById(R.id.empty_click_view).setVisibility(VISIBLE);
        errorView.setOnClickListener(errorAndEmptyAction);
        emptyView.setOnClickListener(errorAndEmptyAction);
        emptyActView.setOnClickListener(errorAndEmptyAction);
        errorActView.setOnClickListener(errorAndEmptyAction);
    }


    public void setErrorActionBackgroud(int resId) {
        errorActView.setBackgroundResource(resId);
    }

    public void setEmptyActionBackground(int resId) {
        emptyActView.setBackgroundResource(resId);
    }

    public TextView getEmptyActView() {
        return emptyActView;
    }

    public TextView getErrorActView() {
        return errorActView;
    }

    public TextView getLoginActView() {
        return loginActView;
    }

    protected void onHideContentView() {
        //Override me
    }

    /**
     * addView
     */
    @Override
    public void addView(View child) {
        checkIsContentView(child);
        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        checkIsContentView(child);
        super.addView(child, index);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        checkIsContentView(child);
        super.addView(child, index, params);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        checkIsContentView(child);
        super.addView(child, params);
    }

    @Override
    public void addView(View child, int width, int height) {
        checkIsContentView(child);
        super.addView(child, width, height);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params) {
        checkIsContentView(child);
        return super.addViewInLayout(child, index, params);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params, boolean preventRequestLayout) {
        checkIsContentView(child);
        return super.addViewInLayout(child, index, params, preventRequestLayout);
    }
}

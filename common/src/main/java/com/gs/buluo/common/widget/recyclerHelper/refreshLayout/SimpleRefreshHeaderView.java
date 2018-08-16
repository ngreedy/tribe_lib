package com.gs.buluo.common.widget.recyclerHelper.refreshLayout;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.gs.buluo.common.R;

public class SimpleRefreshHeaderView extends FrameLayout implements IRefreshHeader {
    private ImageView arrowIcon;
    private ImageView successIcon;
    AnimationDrawable frameAnim;

    public SimpleRefreshHeaderView(Context context) {
        this(context, null);
    }

    public SimpleRefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        frameAnim=(AnimationDrawable) getResources().getDrawable(R.drawable.animation_list);

        inflate(context, R.layout.refresh_header, this);

        arrowIcon = (ImageView) findViewById(R.id.arrowIcon);
        successIcon = (ImageView) findViewById(R.id.successIcon);
//        successIcon.setBackgroundDrawable(frameAnim);
        successIcon.setImageDrawable(frameAnim);
    }

    @Override
    public void reset() {
        successIcon.setVisibility(INVISIBLE);
        arrowIcon.setVisibility(VISIBLE);
    }

    @Override
    public void pull() {

    }

    @Override
    public void refreshing() {
        arrowIcon.setVisibility(INVISIBLE);
        successIcon.setVisibility(VISIBLE);
        frameAnim.start();
    }

    @Override
    public void onPositionChange(float currentPos, float lastPos, float refreshPos, boolean isTouch, State state) {
        // 往上拉
//        if (currentPos < refreshPos && lastPos >= refreshPos) {
//            Log.i("", ">>>>up");
//            if (isTouch && state == State.PULL) {
//                arrowIcon.clearAnimation();
//                arrowIcon.startAnimation(rotate_down);
//            }
//            // 往下拉
//        } else if (currentPos > refreshPos && lastPos <= refreshPos) {
//            Log.i("", ">>>>down");
//            if (isTouch && state == State.PULL) {
//                arrowIcon.clearAnimation();
//                arrowIcon.startAnimation(rotate_up);
//            }
//        }
    }

    @Override
    public void complete() {
        frameAnim.stop();
    }
}
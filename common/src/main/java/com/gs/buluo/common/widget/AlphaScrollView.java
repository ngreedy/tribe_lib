package com.gs.buluo.common.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by hjn on 2016/12/20.
 */

public class AlphaScrollView extends ScrollView {
    private OnAlphaScrollListener onAlphaScrollListener;

    public AlphaScrollView(Context context) {
        this(context,null);
    }

    public AlphaScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AlphaScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScrollListener(OnAlphaScrollListener onAlphaScrollListener){
        this.onAlphaScrollListener=onAlphaScrollListener;
    }

    private int lastScrollY;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int scrollY = AlphaScrollView.this.getScrollY();
            if(onAlphaScrollListener != null){
                onAlphaScrollListener.onScroll(scrollY);
            }
            //此时的距离和记录下的距离不相等，在隔5毫秒给handler发送消息
            if(lastScrollY != scrollY){
                lastScrollY = scrollY;
                handler.sendMessageDelayed(handler.obtainMessage(), 5);
            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(onAlphaScrollListener != null){
            onAlphaScrollListener.onScroll(lastScrollY = this.getScrollY());
        }
        if(ev.getAction() == MotionEvent.ACTION_UP){
            handler.sendMessageDelayed(handler.obtainMessage(), 20);
        }
        return super.onTouchEvent(ev);
    }

    public interface OnAlphaScrollListener{
        void onScroll(int scrollY);
    }
}

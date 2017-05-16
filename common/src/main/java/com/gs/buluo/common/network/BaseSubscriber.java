package com.gs.buluo.common.network;


import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.gs.buluo.common.BaseApplication;
import com.gs.buluo.common.R;
import com.gs.buluo.common.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by hjn on 2017/3/20.
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {
    private static final String TAG = "Network";
    private boolean showLoading = true;
    private WindowManager mWindowManager;
    private View mFloatLayout;
    private WindowManager.LayoutParams wmParams;

    public BaseSubscriber(boolean showLoading) {
        this.showLoading = showLoading;
    }

    public BaseSubscriber() {
        wmParams = new WindowManager.LayoutParams();
//获取的是WindowManagerImpl.CompatModeWrapper
        mWindowManager = (WindowManager) BaseApplication.getInstance().getSystemService(BaseApplication.getInstance().WINDOW_SERVICE);
//设置window type
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
//设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
// 以屏幕左上角为原点，设置x、y初始值，相对于gravity
        wmParams.x = 0;
        wmParams.y = 0;

//设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        LayoutInflater inflater = LayoutInflater.from(BaseApplication.getInstance());
//获取浮动窗口视图所在布局
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.simple_dialog, null);
//添加mFloatLayout

    }

    @Override
    public void onStart() {
//        if (showLoading) BaseApplication.showDialog(R.string.loading);
        mWindowManager.addView(mFloatLayout, wmParams);
    }

    @Override
    public void onCompleted() {
//        if (showLoading) BaseApplication.dismissDialog();
        mFloatLayout.setVisibility(View.GONE);
    }

    @Override
    public void onError(Throwable e) {
        if (showLoading) {
//            BaseApplication.dismissDialog();
            mFloatLayout.setVisibility(View.GONE);
        }
        if (e instanceof HttpException) {       //http返回异常
            //http error
            if (((HttpException) e).code() == 401) {  //token 过期，重新登录
                ToastUtils.ToastMessage(BaseApplication.getInstance().getApplicationContext(), R.string.login_again);
                EventBus.getDefault().post(new TokenEvent());
            } else if (((HttpException) e).code() == 500) {
                ToastUtils.ToastMessage(BaseApplication.getInstance().getApplicationContext(), R.string.connect_fail);
            } else {
                ToastUtils.ToastMessage(BaseApplication.getInstance().getApplicationContext(), "Http异常，返回码" + ((HttpException) e).code());
            }
        } else if (e instanceof IOException) {
            ToastUtils.ToastMessage(BaseApplication.getInstance().getApplicationContext(), R.string.convert_fail);
        } else if (e instanceof ApiException) {
            ApiException exception = (ApiException) e;
            if (exception.getCode() == 500) {
                ToastUtils.ToastMessage(BaseApplication.getInstance().getApplicationContext(), R.string.connect_fail);
            } else {
                onFail(exception);
            }
        } else {
            ToastUtils.ToastMessage(BaseApplication.getInstance().getApplicationContext(), "未知错误,请稍后重试" );
        }
    }

    public void onFail(ApiException e) {
        ToastUtils.ToastMessage(BaseApplication.getInstance().getApplicationContext(), "连接错误,错误码" + e.getCode());
    }

    @Override
    public void onNext(T t) {

    }
}

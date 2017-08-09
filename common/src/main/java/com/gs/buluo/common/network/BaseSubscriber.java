package com.gs.buluo.common.network;


import com.gs.buluo.common.BaseApplication;
import com.gs.buluo.common.R;
import com.gs.buluo.common.UpdateEvent;
import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.widget.LoadingDialog;

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

    public BaseSubscriber(boolean showLoading) {
        this.showLoading = showLoading;
    }

    public BaseSubscriber() {
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onCompleted() {
        LoadingDialog.getInstance().dismissDialog();
    }


    @Override
    public void onError(Throwable e) {
        LoadingDialog.getInstance().dismissDialog();
        if (e instanceof HttpException) {       //http返回异常
            //http error
            if (((HttpException) e).code() == 401) {  //token 过期，重新登录
                ToastUtils.ToastMessage(BaseApplication.getInstance().getApplicationContext(), R.string.login_again);
                EventBus.getDefault().post(new TokenEvent());
            } else {
                onFail(new ApiException(((HttpException) e).code(), "", "HttpException"));
            }
        } else if (e instanceof IOException) {
            onFail(new ApiException(554, "", "IOException"));
        } else if (e instanceof ApiException) {
            ApiException exception = (ApiException) e;
            exception.setType("ApiException)");
            if (exception.getCode() == 505) {//强制更新
                EventBus.getDefault().post(new UpdateEvent(false));
            } else {
                onFail(exception);
            }
        } else {
            onFail(new ApiException(555, "未知错误,请稍后重试", "Exception"));
        }
    }

    public void onFail(ApiException e) {
        ToastUtils.ToastMessage(BaseApplication.getInstance().getApplicationContext(),e.getType()+" : "+ e.getCode() + " : " + e.getDisplayMessage());
    }

    @Override
    public void onNext(T t) {

    }
}

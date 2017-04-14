package com.gs.buluo.common.network;



import com.gs.buluo.common.BaseApplication;
import com.gs.buluo.common.R;
import com.gs.buluo.common.utils.ToastUtils;

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
        if (showLoading) BaseApplication.showDialog(R.string.loading);
    }

    @Override
    public void onCompleted() {
        if (showLoading) BaseApplication.dismissDialog();
    }

    @Override
    public void onError(Throwable e) {
        if (showLoading) BaseApplication.dismissDialog();
        if (e instanceof HttpException) {       //http返回异常
            //http error
            if (((HttpException) e).code() == 401) {  //token 过期，重新登录
                ToastUtils.ToastMessage(BaseApplication.getInstance().getApplicationContext(), R.string.login_again);
            }else if (((HttpException) e).code() == 500){
                ToastUtils.ToastMessage(BaseApplication.getInstance().getApplicationContext(), R.string.connect_fail);
            }
        } else if (e instanceof IOException) {
            ToastUtils.ToastMessage(BaseApplication.getInstance().getApplicationContext(), R.string.convert_fail);
        } else if (e instanceof ApiException) {
            ApiException exception = (ApiException) e;
            if (exception.getCode() == 401){
                ToastUtils.ToastMessage(BaseApplication.getInstance().getApplicationContext(), R.string.login_again);
            }else if (exception.getCode() == 409) {
                //forbidden
                ToastUtils.ToastMessage(BaseApplication.getInstance().getApplicationContext(), R.string.forbidden);
            } else if (exception.getCode() == 500){
                ToastUtils.ToastMessage(BaseApplication.getInstance().getApplicationContext(), R.string.connect_fail);
            }
        }else {
            ToastUtils.ToastMessage(BaseApplication.getInstance().getApplicationContext(), R.string.connect_fail);
        }
    }

    @Override
    public void onNext(T t) {

    }
}

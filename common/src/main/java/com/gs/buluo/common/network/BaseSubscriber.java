package com.gs.buluo.common.network;


import android.util.Log;

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

    @Override
    public void onStart() {
        BaseApplication.showDialog(R.string.loading);
    }

    @Override
    public void onCompleted() {
        BaseApplication.dismissDialog();
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException){
            //http error
            if (((HttpException) e).code()==401){  //token 过期，重新登录
                ToastUtils.ToastMessage(BaseApplication.getInstance().getApplicationContext(),R.string.login_again);
            }
        }else if (e instanceof IOException){
            ToastUtils.ToastMessage(BaseApplication.getInstance().getApplicationContext(), R.string.convert_fail);
        }else if (e instanceof ApiException){  //服务器返回异常
            ApiException exception = (ApiException) e;
            Log.e(TAG, "onError:  "+((ApiException) e).getDisplayMessage() );

             if (exception.getCode() == 409){
                 //forbidden

            }else if (exception.getCode() == 404){
                 //not find

             }else {
                 ToastUtils.ToastMessage(BaseApplication.getInstance().getApplicationContext(), R.string.connect_fail);
            }
        }
    }

    @Override
    public void onNext(T t) {

    }
}

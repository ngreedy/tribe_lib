package com.gs.buluo.common.network;


import android.util.Log;

import com.google.gson.JsonParseException;
import com.gs.buluo.common.BaseApplication;
import com.gs.buluo.common.R;
import com.gs.buluo.common.UpdateEvent;
import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.widget.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.io.IOException;

import io.reactivex.observers.ResourceObserver;
import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

/**
 * Created by hjn on 2017/3/20.
 */

public abstract class BaseSubscriber<T> extends ResourceSubscriber<T> {
    private static final String TAG = "Network";

    @Override
    public void onStart() {

    }

    @Override
    public void onComplete() {
        LoadingDialog.getInstance().dismissDialog();
    }


    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError: " + e);
        LoadingDialog.getInstance().dismissDialog();
        if (e instanceof HttpException) {       //http返回异常
            //http error
//            if (((HttpException) e).err_code() == 401) {  //token 过期，重新登录
//                ToastUtils.ToastMessage(BaseApplication.getInstance().getApplicationContext(), R.string.login_again);
//                EventBus.getDefault().post(new TokenEvent());
//            } else {
            onFail(new ApiException(((HttpException) e).code(), "出现HttpException异常", "HttpException"));
//            }
        } else if (e instanceof IOException) {
            onFail(new ApiException(554, "出现IOException异常", "IOException"));
        } else if (e instanceof JSONException || e instanceof JsonParseException) {
            onFail(new ApiException(554, "数据解析异常", "JSONException"));
        } else if (e instanceof ApiException) {
            ApiException exception = (ApiException) e;
            onFail(exception);
        } else {
            onFail(new ApiException(509, "未知错误,请稍后重试", "Exception"));
        }
    }

    protected void onFail(ApiException e) {
        ToastUtils.ToastMessage(BaseApplication.getInstance().getApplicationContext(), e.getType() + " : " + e.getCode() + " : " + e.getDisplayMessage());
    }

    abstract void onSuccess(T t);

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }
}

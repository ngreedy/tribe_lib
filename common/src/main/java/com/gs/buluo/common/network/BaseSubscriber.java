package com.gs.buluo.common.network;


import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.gs.buluo.common.BaseApplication;
import com.gs.buluo.common.R;
import com.gs.buluo.common.utils.ToastUtils;
import com.gs.buluo.common.widget.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.io.IOException;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * Created by hjn on 2017/3/20.
 */

public abstract class BaseSubscriber<T> extends DisposableObserver<T> {
    private static final String TAG = "Network";

    @Override
    public void onComplete() {
        LoadingDialog.getInstance().dismissDialog();
    }

    @Override
    public void onError(Throwable e) {
        LoadingDialog.getInstance().dismissDialog();
        if (e instanceof HttpException) {       //http返回异常
            ResponseBody errorBody = ((HttpException) e).response().errorBody();
            try {
                String string = errorBody != null ? errorBody.string() : "";
                ErrorBody error = new GsonBuilder().create().fromJson(string, ErrorBody.class);
                if (error.error.details != null && error.error.details.size() > 0) {
                    onNodeFail(error.error.code, error.error.details.get(0));
                } else {
                    throw new IOException();
                }
            } catch (IOException e1) {
                onFail(new ApiException(((HttpException) e).code(), BaseApplication.getInstance().getApplicationContext().getResources().getString(R.string.http_fail), "HttpException"));
            }
        } else if (e instanceof IOException) {
            onFail(new ApiException(555, BaseApplication.getInstance().getApplicationContext().getResources().getString(R.string.connect_fail), "IOException"));
        } else if (e instanceof JSONException || e instanceof JsonParseException) {
            onFail(new ApiException(555, BaseApplication.getInstance().getApplicationContext().getResources().getString(R.string.convert_fail), "JSONException"));
        } else if (e instanceof ApiException) {
            ApiException exception = (ApiException) e;
            if (exception.getCode() == 10003 || exception.getCode() == 10011) {
                EventBus.getDefault().post(new TokenEvent());
            }
            onFail(exception);
        } else {
            onFail(new ApiException(509, BaseApplication.getInstance().getApplicationContext().getResources().getString(R.string.connect_fail), e.getMessage()));
        }
    }

    protected void onNodeFail(int code, ErrorBody.DetailErrorBean message) {
        ToastUtils.ToastMessage(BaseApplication.getInstance().getApplicationContext(), message.message);
    }

    protected void onFail(ApiException e) {
        ToastUtils.ToastMessage(BaseApplication.getInstance().getApplicationContext(), e.getDisplayMessage());
    }

    public abstract void onSuccess(T t);

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }
}

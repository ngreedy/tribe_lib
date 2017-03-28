package com.gs.buluo.common;

import android.app.Application;
import android.os.Environment;

/**
 * Created by hjn on 2016/11/1.
 */
public abstract class BaseApplication extends Application {
    private static BaseApplication instance;
    private String baseUrl;
    private Object userInfo;
    public  String path = Environment.getExternalStorageDirectory().toString() + "/tribe/";

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }


    public static synchronized BaseApplication getInstance(){
        return instance;
    }

    public abstract void onInitialize();


    public String getBaseUrl() {
        return baseUrl;
    }

    public abstract void setBaseUrl(String url);
    public abstract void setUserInfo(Object userInfo);

    public void setCustomFilePath(String path){
        this.path=path;
    }

    public String getFilePath(){
        return path;
    }

    public Object getUserInfo() {
        return userInfo;
    }
}

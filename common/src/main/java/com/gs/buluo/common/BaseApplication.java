package com.gs.buluo.common;

import android.app.Application;

/**
 * Created by hjn on 2016/11/1.
 */
public abstract class BaseApplication extends Application {
    private static BaseApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }

    public static synchronized BaseApplication getInstance(){
        return instance;
    }

    public abstract void onInitialize();

    public abstract String getFilePath();


}

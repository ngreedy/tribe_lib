package com.gs.buluo.common;

import android.app.Application;
import android.content.Context;

/**
 * Created by hjn on 2016/11/1.
 */
public abstract class BaseApplication extends Application {
    private static BaseApplication instance;

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        onInitialize();
    }

    public abstract void onInitialize();

    public abstract String getFilePath();


}

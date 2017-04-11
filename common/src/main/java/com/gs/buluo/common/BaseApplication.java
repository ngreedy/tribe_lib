package com.gs.buluo.common;

import android.app.Application;
import android.os.Environment;

import com.gs.buluo.common.widget.dialog.DialogObservable;
import com.gs.buluo.common.widget.dialog.GlobalDialogService;

/**
 * Created by hjn on 2016/11/1.
 */
public abstract class BaseApplication extends Application {
    private static BaseApplication instance;
    private static DialogObservable mDialogObservable;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        mDialogObservable=new DialogObservable();//新建被观察者
        mDialogObservable.addObserver(new GlobalDialogService());//添加观察者
    }


    public static synchronized BaseApplication getInstance(){
        return instance;
    }

    public abstract void onInitialize();

    public abstract String getFilePath();

    public static void showDialog(String msg){
        mDialogObservable.showDialog(msg);
    }

    public static void dismissDialog(){
        mDialogObservable.showDialog(null);
    }


}

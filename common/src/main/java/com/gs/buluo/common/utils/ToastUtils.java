package com.gs.buluo.common.utils;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

public class ToastUtils {

    /**
     * @param msg
     */
    static Toast toast1 = null;
    public static void ToastMessage(Context cont, String msg) {
        try {
            if (toast1 == null) {
                //			toast1 = Toast.makeText(cont, msg, Toast.LENGTH_LONG);
                toast1 = Toast.makeText(cont, msg, Toast.LENGTH_SHORT);
            } else {
                toast1.setText(msg);
            }
            toast1.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static Toast toast = null;
    public static void ToastMessage(Context cont, int msg) {
        try {
            if (toast == null) {
                toast = Toast.makeText(cont, cont.getResources().getString(msg), Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            toast.show();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void ToastMessage(Context cont, int msg, int time) {
        try {
            Toast.makeText(cont, msg, time).show();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void ToastMessage(Context cont, String msg, int time) {
        try {
            Toast.makeText(cont, msg, time).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
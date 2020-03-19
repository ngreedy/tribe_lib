package com.gs.buluo.common.widget.util

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics

/**
 *  @Author:        hjn
 * @CreateDate:     2019/6/17 15:36
 * @Description: 屏幕相关
 *
 */
object ScreenUtils {

    // 屏幕宽度（像素）
    fun getWindowWidth(context: Activity): Int {
        val metric = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(metric)
        return metric.widthPixels
    }

    // 屏幕高度（像素）
    fun getWindowHeight(activity: Activity): Int {
        val metric = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metric)
        return metric.heightPixels
    }

    /**
     * 获取屏幕横向(宽度)分辨率
     */
    fun getResolutionX(context: Context): Int {
        val mDisplayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(mDisplayMetrics)

        return mDisplayMetrics.widthPixels
    }

    /**
     * 获取屏幕纵向(高度)分辨率
     */
    fun getResolutionY(context: Context): Int {
        val mDisplayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(mDisplayMetrics)

        return mDisplayMetrics.heightPixels
    }

}
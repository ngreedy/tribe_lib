package com.gs.buluo.common.widget.util

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics

/**
 *  @Author:        hjn
 * @CreateDate:     2019/6/17 15:41
 * @Description:转换工具
 *
 */
object ConvertUtils {
    // 根据手机的分辨率将dp的单位转成px(像素)
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    // 根据手机的分辨率将px(像素)的单位转成dp
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    // 将px值转换为sp值
    fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    // 将sp值转换为px值
    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }


}
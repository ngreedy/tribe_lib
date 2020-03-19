package com.gs.buluo.common.widget.util

import android.Manifest.permission.ACCESS_NETWORK_STATE
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.annotation.RequiresPermission
import java.util.regex.Pattern

/**
 * des:网络管理工具
 * Created by xsf
 * on 2016.04.10:34
 */
object NetUtils {

    /**
     * 检查网络是否可用
     *
     * @param paramContext
     * @return
     */
    @RequiresPermission(allOf = arrayOf(ACCESS_NETWORK_STATE))
    fun isNetConnected(paramContext: Context): Boolean {
        val localNetworkInfo = (paramContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return if (localNetworkInfo != null && localNetworkInfo.isAvailable) true else false
    }

    /**
     * 检测wifi是否连接
     */
    @RequiresPermission(allOf = arrayOf(ACCESS_NETWORK_STATE))
    fun isWifiConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm != null) {
            val networkInfo = cm.activeNetworkInfo
            if (networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_WIFI) {
                return true
            }
        }
        return false
    }

    /**
     * 检测3G是否连接
     */
    @RequiresPermission(allOf = arrayOf(ACCESS_NETWORK_STATE))
    fun is3gConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm != null) {
            val networkInfo = cm.activeNetworkInfo
            if (networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                return true
            }
        }
        return false
    }

    /**
     * 判断网址是否有效
     */
    fun isLinkAvailable(link: String): Boolean {
        val pattern = Pattern.compile(
            "^(http://|https://)?((?:[A-Za-z0-9]+-[A-Za-z0-9]+|[A-Za-z0-9]+)\\.)+([A-Za-z]+)[/\\?\\:]?.*$",
            Pattern.CASE_INSENSITIVE
        )
        val matcher = pattern.matcher(link)
        return if (matcher.matches()) {
            true
        } else false
    }

    /**
     * 打开网络设置界面
     */
    fun openSetting(activity: Activity) {
        val intent = Intent("/")
        val cm = ComponentName(
            "com.android.settings",
            "com.android.settings.WirelessSettings"
        )
        intent.component = cm
        intent.action = "android.intent.action.VIEW"
        activity.startActivityForResult(intent, 0)
    }
}

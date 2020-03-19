package com.gs.buluo.common.widget.util

import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Process
import android.text.TextUtils
import com.gs.buluo.common.BaseApplication
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException


/**
 *  @Author:        hjn
 * @CreateDate:     2019/6/17 15:50
 * @Description: app相关工具类
 *
 */
object AppUtils {

    /**
     * 获取 App 包名
     *
     * @return the application's package name
     */
    fun getAppPackageName(): String {
        return BaseApplication.getInstance().getPackageName()
    }


    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    fun getVerName(): String {
        var verName = ""
        try {
            verName = BaseApplication.getInstance().packageManager.getPackageInfo(
                BaseApplication.getInstance().packageName, 0
            ).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return verName
    }

    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    fun getVerCode(): Int {
        var verCode = -1
        try {
            //注意："com.example.try_downloadfile_progress"对应AndroidManifest.xml里的package="……"部分
            verCode = BaseApplication.getInstance().packageManager.getPackageInfo(
                BaseApplication.getInstance().packageName, 0
            ).versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return verCode
    }

    /**
     * 版本号对比
     */
    fun compareVersion(version1: String?, version2: String?): Int {
        if (version1 == null || version2 == null) {
            return 0;
        }
        val versionArray1 =
            version1.trim().split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()// 注意此处为正则匹配，不能用"."；
        val versionArray2 = version2.trim().split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var idx = 0
        val minLength = Math.min(versionArray1.size, versionArray2.size)// 取最小长度值
        var diff = 0

        while (idx < minLength) {
            diff = versionArray1[idx].length - versionArray2[idx].length
            if (diff == 0) {
                diff = versionArray1[idx].compareTo(versionArray2[idx])
                if (diff == 0) {
                    ++idx
                } else {
                    break
                }
            } else {
                break
            }
        }
        // 如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = if (diff != 0) diff else versionArray1.size - versionArray2.size
        return diff
    }

    /**
     *  与当前应用Version对比
     *  @return 当前应用是否需要更新
     */
    fun compareCurrentVersion(newVersion: String?): Boolean {
        var currentVersion = getVerName()
        var diff = compareVersion(currentVersion, newVersion)
        if (diff < 0) {
            return true
        }
        return false
    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    fun getProcessName(pid: Int): String? {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
            var processName = reader.readLine()
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim { it <= ' ' }
            }
            return processName
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            try {
                reader?.close()
            } catch (exception: IOException) {
                exception.printStackTrace()
            }

        }
        return null
    }

    fun getProcessName(context: Context?): String? {
        if (context == null) return null
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (processInfo in manager.runningAppProcesses) {
            if (processInfo.pid == Process.myPid()) {
                return processInfo.processName
            }
        }
        return null
    }
}
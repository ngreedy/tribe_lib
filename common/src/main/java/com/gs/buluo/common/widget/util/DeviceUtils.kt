package com.gs.buluo.common.widget.util

import android.Manifest.permission.ACCESS_WIFI_STATE
import android.Manifest.permission.INTERNET
import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import androidx.annotation.RequiresPermission
import com.gs.buluo.common.BaseApplication
import java.io.File
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException

/**
 *  @Author:        hjn
 * @CreateDate:     2019/6/17 15:55
 * @Description: 设备相关
 *
 */
object DeviceUtils {

    /**
     * 判断设备是否 rooted
     *
     * @return `true`: yes
     */
    fun isDeviceRooted(): Boolean {
        val su = "su"
        val locations = arrayOf(
            "/system/bin/",
            "/system/xbin/",
            "/sbin/",
            "/system/sd/xbin/",
            "/system/bin/failsafe/",
            "/data/local/xbin/",
            "/data/local/bin/",
            "/data/local/"
        )
        for (location in locations) {
            if (File(location + su).exists()) {
                return true
            }
        }
        return false
    }

    /**
     * 获取设备系统版本名
     *
     * @return the version name of device's system
     */
    fun getSDKVersionName(): String {
        return android.os.Build.VERSION.RELEASE
    }

    /**
     * 获取设备系统版本号
     *
     * @return version code of device's system
     */
    fun getSDKVersionCode(): Int {
        return android.os.Build.VERSION.SDK_INT
    }

    /**
     * 获取mac(网卡)地址
     *
     * Must hold
     * `<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />`,
     * `<uses-permission android:name="android.permission.INTERNET" />`
     *
     * @return the MAC address
     */
    @RequiresPermission(allOf = arrayOf(ACCESS_WIFI_STATE, INTERNET))
    fun getMacAddress(): String {
        return getMacAddress()
    }

    /**
     * 获取设备 MAC 地址
     *
     * Must hold
     * `<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />`,
     * `<uses-permission android:name="android.permission.INTERNET" />`
     *
     * @return the MAC address
     */
    @RequiresPermission(allOf = arrayOf(ACCESS_WIFI_STATE, INTERNET))
    fun getMacAddress(vararg excepts: String): String {
        var macAddress = getMacAddressByWifiInfo()
        if (isAddressNotInExcepts(macAddress, *excepts)) {
            return macAddress
        }
        macAddress = getMacAddressByNetworkInterface()
        if (isAddressNotInExcepts(macAddress, *excepts)) {
            return macAddress
        }
        macAddress = getMacAddressByInetAddress()
        if (isAddressNotInExcepts(macAddress, *excepts)) {
            return macAddress
        }
        macAddress = getMacAddressByFile()
        return if (isAddressNotInExcepts(macAddress, *excepts)) {
            macAddress
        } else ""
    }

    /**
     * https://developer.android.com/about/versions/marshmallow/android-6.0-changes.html?hl=zh-cn#behavior-hardware-id
     * 02:00:00:00:00:00
     */
    private fun isAddressNotInExcepts(address: String, vararg excepts: String): Boolean {
        if (excepts == null || excepts.size == 0) {
            return "02:00:00:00:00:00" != address
        }
        for (filter in excepts) {
            if (address == filter) {
                return false
            }
        }
        return true
    }

    @SuppressLint("HardwareIds", "MissingPermission")
    private fun getMacAddressByWifiInfo(): String {
        try {
            val context = BaseApplication.getInstance().getApplicationContext()
            val wifi = context.getSystemService(Context.WIFI_SERVICE) as WifiManager?
            if (wifi != null) {
                val info = wifi.connectionInfo
                if (info != null) return info.macAddress
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return "02:00:00:00:00:00"
    }

    private fun getMacAddressByNetworkInterface(): String {
        try {
            val nis = NetworkInterface.getNetworkInterfaces()
            while (nis.hasMoreElements()) {
                val ni = nis.nextElement()
                if (ni == null || !ni.name.equals("wlan0", ignoreCase = true)) continue
                val macBytes = ni.hardwareAddress
                if (macBytes != null && macBytes.size > 0) {
                    val sb = StringBuilder()
                    for (b in macBytes) {
                        sb.append(String.format("%02x:", b))
                    }
                    return sb.substring(0, sb.length - 1)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return "02:00:00:00:00:00"
    }

    private fun getMacAddressByInetAddress(): String {
        try {
            val inetAddress = getInetAddress()
            if (inetAddress != null) {
                val ni = NetworkInterface.getByInetAddress(inetAddress)
                if (ni != null) {
                    val macBytes = ni.hardwareAddress
                    if (macBytes != null && macBytes.size > 0) {
                        val sb = StringBuilder()
                        for (b in macBytes) {
                            sb.append(String.format("%02x:", b))
                        }
                        return sb.substring(0, sb.length - 1)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return "02:00:00:00:00:00"
    }

    private fun getInetAddress(): InetAddress? {
        try {
            val nis = NetworkInterface.getNetworkInterfaces()
            while (nis.hasMoreElements()) {
                val ni = nis.nextElement()
                // To prevent phone of xiaomi return "10.0.2.15"
                if (!ni.isUp) continue
                val addresses = ni.inetAddresses
                while (addresses.hasMoreElements()) {
                    val inetAddress = addresses.nextElement()
                    if (!inetAddress.isLoopbackAddress) {
                        val hostAddress = inetAddress.hostAddress
                        if (hostAddress.indexOf(':') < 0) return inetAddress
                    }
                }
            }
        } catch (e: SocketException) {
            e.printStackTrace()
        }

        return null
    }

    private fun getMacAddressByFile(): String {
        var result = ShellUtils.execCmd("getprop wifi.interface", false)
        if (result.result === 0) {
            val name = result.successMsg
            if (name != null) {
                result = ShellUtils.execCmd("cat /sys/class/net/$name/address", false)
                if (result.result === 0) {
                    val address = result.successMsg
                    if (address != null && address!!.length > 0) {
                        return address
                    }
                }
            }
        }
        return "02:00:00:00:00:00"
    }
}
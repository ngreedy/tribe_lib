package com.gs.buluo.common.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.gs.buluo.common.BaseApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

/**
 * Created by hjn on 2017/2/8.
 */

public class TribeCrashCollector implements Thread.UncaughtExceptionHandler {
    private Context mContext;
    private static volatile String sFileExtension = ".stacktrace";
    private static TribeCrashCollector ins;

    private TribeCrashCollector(Context context) {
        mContext = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static TribeCrashCollector getIns(Context context) {

        if (null == ins) {
            synchronized (TribeCrashCollector.class) {
                ins = new TribeCrashCollector(context);
            }
        }
        return ins;
    }


    public File getCrashLogDir() {
        try {
            File dir = new File(Environment.getExternalStorageDirectory().toString() + "/tribe/", "crash");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            return dir;
        } catch (Exception e) {
            e.printStackTrace();
            return new File(mContext.getFilesDir(), "crash");
        }

    }

    public File getJNICrashLogDir() {
        File dir = new File(mContext.getFilesDir(), "jnicrash");
        dir.mkdirs();
        return dir;
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        ex.printStackTrace(System.err);
        saveErrorReportAsFile(ex);
        AppManager.getAppManager().finishAllActivityAndExit();
        System.exit(0);
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(0);
    }

    private long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    private long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    public String createCrashInfo(Throwable e) {
        try {
            JSONObject crashInfo = new JSONObject();
            // crash stack
            String stackTrack = collectStackTrace(e);
            crashInfo.put("stack", stackTrack);
            JSONObject crashContext = new JSONObject();
            // uid
            crashContext.put("uid", 1);
            // Platform
            crashContext.put("platform", "Android");
            // Version
            crashContext.put("machineId", Build.MODEL.replace(" ", ""));
            // PlatformVersion
            crashContext.put("platformVersion", Build.VERSION.RELEASE);
            // ssize
            DisplayMetrics metric = new DisplayMetrics();
            Display display = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            display.getMetrics(metric);
            crashContext.put("ssize", String.format("%s*%s", String.valueOf(metric.widthPixels / metric.density), String.valueOf(metric.heightPixels / metric.density)));
            // Screen Resolution
            crashContext.put("screenResolutio", display.getWidth() + "x" + display.getHeight());
            // RAM
            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            ((ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryInfo(mi);
            crashContext.put("ram", mi.availMem);
            // ROM
            crashContext.put("rom", getTotalInternalMemorySize());
            // CPU
            crashContext.put("cpu", Build.CPU_ABI);
            // time
            crashContext.put("ctime", System.currentTimeMillis());
            // ui
            crashContext.put("ui", Build.HOST);
            // net status
            try {
                if (CommonUtils.isConnected(mContext)) {
                    if (CommonUtils.isConnectedMobile(mContext)) {
                        crashContext.put("net", "mobile");
                    } else if (CommonUtils.isConnectedWifi(mContext)) {
                        crashContext.put("net", "wifi");
                    }
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            JSONObject json = new JSONObject();
            json.put("crashInfo", crashInfo);
            json.put("crashContext", crashContext);
            return json.toString();
        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        } catch (Exception e2) {
            return null;
        }
    }

    public String collectDeviceInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("==== Device Info ====\n");
        sb.append("Model: ").append(Build.MODEL).append('\n');
        sb.append("OS Version: ").append(Build.VERSION.RELEASE).append('\n');
        sb.append("SDK Version: ").append(Build.VERSION.SDK).append('\n');
        sb.append("Device Name: ").append(Build.DEVICE).append('\n');
        sb.append("Product Name: ").append(Build.PRODUCT).append('\n');
        sb.append("Display: ").append(Build.DISPLAY).append('\n');
        sb.append("Brand: ").append(Build.BRAND).append('\n');
        sb.append("-------------------------------\n");
        sb.append("Available ROM: ").append(getAvailableInternalMemorySize()).append('\n');
        sb.append("Total ROM: ").append(getTotalInternalMemorySize()).append('\n');
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ((ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryInfo(mi);
        sb.append("Available RAM: ").append(mi.availMem).append('\n');
        sb.append("PlatformVersion=").append(Build.VERSION.RELEASE).append('\n');
        sb.append("MachineId=").append(Build.MODEL.replace(" ", "")).append('\n');
        Display display = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        sb.append("Screen Resolution: ").append(display.getWidth()).append('x').append(display.getHeight()).append('\n');
        DisplayMetrics metric = new DisplayMetrics();
        try {
            ((WindowManager) BaseApplication.getInstance().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metric);
            sb.append("ssize=").append(String.format("%s*%s", String.valueOf(metric.widthPixels / metric.density), String.valueOf(metric.heightPixels / metric.density))).append('\n');//屏幕尺寸;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String collectPackageInfo() {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {

        }

        if (pi == null) {
            return "FATEL: PackageInfo is null, can't collect package info.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("==== Package Info ====\n");
        sb.append("Time: ").append(new Date());
        sb.append("Pkg Name: ").append(pi.packageName).append('\n');
        sb.append("Version Name: ").append(pi.versionName).append('\n');
        sb.append("Version Code: ").append(pi.versionCode).append('\n');
//		sb.append("Build: ").append(mContext.getString(R.string.build)).append('\n');

        if (Build.VERSION.SDK_INT >= 9) { // Android2.3
            sb.append("First Install Time: ").append(new Date(pi.firstInstallTime)).append('\n');
            sb.append("Last Update Time: ").append(new Date(pi.lastUpdateTime)).append('\n');
        } else { // Android 2.2
            File f = new File(pi.applicationInfo.sourceDir);
            sb.append("Last Update Time: ").append(new Date(f.lastModified())).append('\n');
        }

        return sb.toString();
    }

    private String collectStackTrace(Throwable e) {
        StringBuilder sb = new StringBuilder();
        sb.append("==== Stack Trace ====\n");
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        e.printStackTrace(printWriter);
        sb.append(result.toString());
        sb.append('\n');

        // If the exception was thrown in a background thread inside
        // AsyncTask, then the actual exception can be found with getCause
        Throwable cause = e.getCause();
        if (cause != null) {
            cause.printStackTrace(printWriter);
            sb.append("==== Root Cause ====\n");
            sb.append(result.toString());
            sb.append('\n');
        }
        printWriter.close();

        return sb.toString();
    }

    public void saveErrorReportAsFile(Throwable e) {
        FileOutputStream fos = null;
        try {
            String fileName = System.currentTimeMillis() + sFileExtension;
            File file = new File(getCrashLogDir(), fileName);
            fos = new FileOutputStream(file);
            fos.write(createCrashInfo(e).getBytes("utf-8"));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception ex) {
                    // do nothing
                }
            }
        }
    }


    public void deleteFiles(File[] files) {
        for (File f : files) {
            f.delete();
        }
    }

    public File[] locateCrashLogFiles(File dir) {
        return dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                return filename.endsWith(sFileExtension);
            }
        });
    }

    public String readFileAsString(File file) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (Exception e) {
            // do nothing
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
        return sb.toString();
    }
}

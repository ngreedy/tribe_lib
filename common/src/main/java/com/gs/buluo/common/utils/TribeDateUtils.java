package com.gs.buluo.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TribeDateUtils {
    private final static Object mLock = new Object();

    public static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static SimpleDateFormat SDF2 = new SimpleDateFormat("MM月dd日"); // 精编时间格式
    public static SimpleDateFormat SDF3 = new SimpleDateFormat("MM-dd HH:mm");// 非精编时间格式
    public static SimpleDateFormat SDF4 = new SimpleDateFormat("HH:mm:ss");
    public static SimpleDateFormat SDF5 = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat SDF6 = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat SDF7 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat SDF8 = new SimpleDateFormat("MM-dd"); // 精编时间格式
    public static SimpleDateFormat SDF_BUILD_VERSION = new SimpleDateFormat("yyyy.MM.dd.HH");
    public static SimpleDateFormat SDF9 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static SimpleDateFormat SDF10 = new SimpleDateFormat("MM月dd日HH时");

    public static Date parse(String date) throws ParseException {
        synchronized (mLock) {
            return SDF.parse(date);
        }
    }

    public static String buildTimeFormat(Date date) {
        synchronized (mLock) {
            return SDF_BUILD_VERSION.format(date);
        }
    }

    public static String dateFormat(Date date) {
        synchronized (mLock) {
            return SDF.format(date);
        }
    }

    public static String dateFormat2(Date date) {
        synchronized (mLock) {
            return SDF2.format(date);
        }
    }

    public static String dateFormat3(Date date) {
        synchronized (mLock) {
            return SDF3.format(date);
        }
    }

    public static String dateFormat4(long date) {
        synchronized (mLock) {
            return SDF4.format(date);
        }
    }

    public static String dateFormat4(Date date) {
        synchronized (mLock) {
            return SDF4.format(date);
        }
    }

    public static String dateFormat5(Date date) {
        synchronized (mLock) {
            return SDF5.format(date);
        }
    }

    public static String dateFormat6(Date date) {
        synchronized (mLock) {
            return SDF6.format(date);
        }
    }

    public static String dateFormat7(Date date) {
        synchronized (mLock) {
            return SDF7.format(date);
        }
    }

    public static String dateFormat8(Date date) {
        synchronized (mLock) {
            return SDF8.format(date);
        }
    }

    public static String dateFormat9(Date date) {
        synchronized (mLock) {
            return SDF9.format(date);
        }
    }

    public static String dateFormat10(long date) {
        synchronized (mLock) {
            return SDF10.format(date);
        }
    }

    /**
     * 获取time1时间与time2时间间隔（相对日期）
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int getTimeIntervalByDate(long time1, long time2) {
        int intervalDays = 0;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTimeInMillis(time1);
        c2.setTimeInMillis(time2);

        intervalDays = (int) ((c2.getTimeInMillis() - c1.getTimeInMillis()) / (1000 * 60 * 60 * 24));
        int days = getTimeIntervalByDay(time1, time2);

        if (intervalDays == 0) {
            if (days == 0) {
                intervalDays = 0;
            } else if (days > 0) {
                intervalDays = 1;
            } else if (days < 0) {
                intervalDays = -1;
            }
        } else if (intervalDays > 0) {
            if (intervalDays < days)
                intervalDays += 1;
        } else if (intervalDays < 0) {
            if (intervalDays > days)
                intervalDays -= 1;
        }
        return intervalDays;
    }

    /**
     * 获取time1时间与time2时间间隔（绝对天数）
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int getTimeIntervalByDay(long time1, long time2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTimeInMillis(time1);
        c2.setTimeInMillis(time2);

        int days = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
        if (c2.get(Calendar.YEAR) > c1.get(Calendar.YEAR)) {
            days += 365;
        } else if (c2.get(Calendar.YEAR) < c1.get(Calendar.YEAR)) {
            days -= 365;
        }
        return days;

    }

    //定义下拉刷新时间格式>>>>几分钟前更新，刚刚更新，几小时前更新;
    public static String getPullRefreshTime(String refreshTime)
    {
        try {
//			LogUtils.i("getPullRefreshTime", refreshTime);
            String time = "";
            Date oldData = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(refreshTime);
            Date nowData = new Date();
            long res = (nowData.getTime() - oldData.getTime()) / (60* 1000);

            if(res <= 1){
                time = "刚刚更新";
            }else if(1 < res && res< 60){
                time = (int)res +"分钟前更新";
            }else if(60 < res && res < (60 *24)){
                time = (int)res/60 +"小时前更新";
            }else{
                time = dateFormat5(oldData)+"更新";
            }
//            LogUtils.i("getPullRefreshTime", "time = " + time);
            return time;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static int stringGetWeek(String time) {
        Calendar cal = Calendar.getInstance();
        int i = -1;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date;
            date = dateFormat.parse(time);
            cal.setTime(date);
            i = cal.get(Calendar.DAY_OF_WEEK);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return i-1;

    }

    public static String hourCounter(long counterTime){
        counterTime/=1000;
        StringBuilder sb = new StringBuilder();
        String hour  = counterTime/3600 +"";
        String mini = counterTime%3600/60+"";
        String second = counterTime%3600%60+"";
        return sb.append(hour).append("小时").append(mini).append("分钟").append(second).append("秒").toString();
    }
}

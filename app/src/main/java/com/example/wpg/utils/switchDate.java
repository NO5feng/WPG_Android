package com.example.wpg.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class switchDate {
    private static final String TAG = "switchDate";

    public static long switchTimestamp(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.applyPattern("yyyy-MM-dd"); // 设置日期格式为"yyyy-MM-dd"
        try {
            Date date = sdf.parse(str);
            long timestamp = date.getTime();
            return timestamp;
        } catch (Exception e) {
            Log.e(TAG, "字符串转时间戳发生错误：" + e);
            return -1;
        }
    }

    public static String convertTimestampToString(long timestamp) {
        try {
            // 创建一个 SimpleDateFormat 对象，设置日期格式为"yyyy-MM-dd"
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // 使用给定的时间戳创建一个 Date 对象
            Date date = new Date(timestamp);
            // 使用 SimpleDateFormat 对象将 Date 对象格式化为字符串
            String dateString = sdf.format(date);
            return dateString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date convertTimestampToDate(long timestamp) {
        try {
            Date date = new Date(timestamp);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long getDaysBetweenTimestamps(long timestamp1, long timestamp2) {
        long millisecondsPerDay = 24 * 60 * 60 * 1000; // 一天的毫秒数
        long diffInMillis = Math.abs(timestamp1 - timestamp2);
        long days = diffInMillis / millisecondsPerDay;
        return days;
    }

}

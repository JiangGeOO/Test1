package com.yidao.myutils.log;

import android.util.Log;

/**
 * Created by Lee on 2017/5/30.
 */

public class LogUtils {
    private static final boolean isDebug = true;
    private static final String TAG = "YunTu2Log";
    // 下面四个是默认tag的函数
    public static void i(String msg)
    {
        if (isDebug) {
            Log.i(TAG, msg);
        }
    }
    public static void d(String msg)
    {
        if (isDebug) {
            Log.d(TAG, msg);
        }
    }
    public static void e(String msg)
    {
        if (isDebug) {
            Log.e(TAG, msg);
        }
    }
    public static void v(String msg)
    {
        if (isDebug) {
            Log.v(TAG, msg);
        }
    }
    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg)
    {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }
    public static void d(String tag, String msg)
    {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }
    public static void e(String tag, String msg)
    {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }
    public static void v(String tag, String msg)
    {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }
}

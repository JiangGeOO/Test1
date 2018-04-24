package com.ijustyce.fastkotlin.utils

import android.util.Log

/**
 * Created by yc on 16-2-6. 再次封装的LogCat类，实现在release版本不打印logcat
 */
object ILog {

    var isShowLog = true  //  是否显示，用于release版本不打印log
    private val tag = "FastAndroidDev"   //  默认的

    fun i(tag: String, msg: Any?) {

        if (isShowLog) {
            Log.i(tag, "" + msg)
        }
    }

    fun i(msg: Any?) {

        if (isShowLog) {
            Log.i(tag, "" + msg)
        }
    }

    fun d(tag: String, msg: Any?) {

        if (isShowLog) {
            Log.d(tag, "" + msg)
        }
    }

    fun d(msg: Any?) {

        if (isShowLog) {
            Log.d(tag, "" + msg)
        }
    }

    fun e(tag: String, msg: Any?) {

        if (isShowLog) {
            Log.e(tag, "" + msg)
        }
    }

    fun e(msg: Any?) {

        if (isShowLog) {
            Log.e(tag, "" + msg)
        }
    }
}

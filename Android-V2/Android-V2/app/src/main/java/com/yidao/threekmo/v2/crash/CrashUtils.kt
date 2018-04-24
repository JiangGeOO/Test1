package com.yidao.threekmo.v2.crash

import com.ijustyce.fastkotlin.utils.ICrashReport

/**
 * Created by deepin on 17-12-19.
 */
object CrashUtils {

    init {
        ICrashReport.crashUploader = BuglyUploader()
    }

    fun onCrash(exception: Exception) {
        ICrashReport.crashUploader?.onCrash(exception)
    }

    fun onCrash(throwable: Throwable) {
        ICrashReport.crashUploader?.onCrash(throwable)
    }

    fun onCrash(exception: Exception, map: HashMap<String, String>) {
        ICrashReport.crashUploader?.onCrash(exception, map)
    }

    fun onCrash(throwable: Throwable, map: HashMap<String, String>) {
        ICrashReport.crashUploader?.onCrash(throwable, map)
    }
}
package com.ijustyce.fastkotlin.utils

/**
 * Created by deepin on 17-12-19.
 */
object ICrashReport : CrashUploader {

    var crashUploader : CrashUploader? = null

    override fun onCrash(exception: Exception) {
        exception.printStackTrace()
        crashUploader?.onCrash(exception)
    }

    override fun onCrash(throwable: Throwable) {
        throwable.printStackTrace()
        crashUploader?.onCrash(throwable)
    }

    override fun onCrash(exception: Exception, map: HashMap<String, String>) {
        exception.printStackTrace()
        crashUploader?.onCrash(exception, map)
    }

    override fun onCrash(throwable: Throwable, map: HashMap<String, String>) {
        throwable.printStackTrace()
        crashUploader?.onCrash(throwable, map)
    }
}
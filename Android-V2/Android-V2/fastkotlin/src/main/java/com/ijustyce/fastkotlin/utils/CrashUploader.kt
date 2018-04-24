package com.ijustyce.fastkotlin.utils

/**
 * Created by deepin on 17-12-19.
 */
open interface CrashUploader{
    fun onCrash(exception: Exception)
    fun onCrash(throwable: Throwable)
    fun onCrash(exception: Exception, map: HashMap<String, String>)
    fun onCrash(throwable: Throwable, map: HashMap<String, String>)
}
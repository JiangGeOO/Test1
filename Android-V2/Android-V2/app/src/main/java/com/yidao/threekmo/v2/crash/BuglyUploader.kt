package com.yidao.threekmo.v2.crash

import com.ijustyce.fastkotlin.utils.CrashUploader

/**
 * Created by deepin on 17-12-19.
 */
class BuglyUploader : CrashUploader {

    override fun onCrash(exception: Exception) {

    }

    override fun onCrash(throwable: Throwable) {

    }

    override fun onCrash(exception: Exception, map: HashMap<String, String>) {

    }

    override fun onCrash(throwable: Throwable, map: HashMap<String, String>) {

    }
}
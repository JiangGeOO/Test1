package com.ijustyce.fastkotlin.utils

import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by yangchun on 2017/1/3.
 */

object ThreadUtils {

    private val service = ThreadPoolExecutor(0, 64,
            60L, TimeUnit.SECONDS, SynchronousQueue())

    fun execute(runnable: Runnable) {
        service.execute(runnable)
    }

    interface ICallBack {
        fun <T> onResult(t: T)
    }
}
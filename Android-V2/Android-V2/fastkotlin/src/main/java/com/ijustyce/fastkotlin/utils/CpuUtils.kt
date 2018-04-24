package com.ijustyce.fastkotlin.utils

import java.io.File
import java.io.FileFilter
import java.util.regex.Pattern

/**
 * Created by yangchun on 2017/4/26.
 */

object CpuUtils {

    fun getCoreNumber(callBack: ThreadUtils.ICallBack) {
        class CpuFilter : FileFilter {
            override fun accept(pathname: File): Boolean {
               return Pattern.matches("cpu([0-9])+", pathname.name)
            }
        }

        ThreadUtils.execute(object : Runnable{
            override fun run() {
                try {
                    val dir = File("/sys/devices/system/cpu/")
                    val files = dir.listFiles(CpuFilter())
                    callBack.onResult(files.size)
                } catch (e: Exception) {
                    e.printStackTrace()
                    callBack.onResult(1)
                }
            }
        } )
    }
}

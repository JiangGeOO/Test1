package com.ijustyce.fastkotlin.base

import android.os.Handler
import com.ijustyce.fastkotlin.R
import com.ijustyce.fastkotlin.utils.ToastUtil

/**
 * Created by deepin on 17-12-18.
 */
open class IBaseEvent {

    var handler: Handler? = null

    open fun onResume(){}
    open fun afterCreate(){}
    open fun onPause(){}
    open fun onStop(){}
    open fun onDestroy(){
        handler?.removeCallbacksAndMessages(null)
        handler = null
    }

    private var exitApp = false
    private val pressDelay = 2000L

    private val clearTime = Runnable{
        exitApp = false
    }

    fun doublePressToExit(activity: IBaseActivity<*>?, resId: Int = R.string.index_hint_exit) {
        if (exitApp) {
            activity?.finish()
            return
        }
        exitApp = true
        ToastUtil.show(resId)
        handler = handler?: Handler()
        handler?.postDelayed(clearTime, pressDelay)
    }
}
package com.yidao.threekmo.v2.event

import android.support.annotation.CallSuper
import com.ijustyce.fastkotlin.base.IBaseEvent

/**
 * Created by deepin on 17-12-18.
 */

interface SplashClick {
    fun skip()
    fun viewAds()
}

open class SplashEvent(private val clickEvent: SplashClick?) : IBaseEvent(), SplashClick {

    @CallSuper
    override fun skip() {
        clickEvent?.skip()
    }

    @CallSuper
    override fun viewAds() {
        clickEvent?.viewAds()
    }
}
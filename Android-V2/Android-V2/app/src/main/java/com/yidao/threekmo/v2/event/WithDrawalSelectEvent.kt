package com.yidao.threekmo.v2.event

import android.support.annotation.CallSuper
import com.ijustyce.fastkotlin.base.IBaseEvent

/**
 * Created by manjaro on 18-1-29.
 */
interface WithDrawalSelectClick {
    fun aliPay()
    fun weChat()
}

open class WithDrawalSelectEvent(val click: WithDrawalSelectClick? = null) : WithDrawalSelectClick, IBaseEvent() {

    @CallSuper
    override fun aliPay() {
        click?.aliPay()
    }

    @CallSuper
    override fun weChat() {
        click?.weChat()
    }
}
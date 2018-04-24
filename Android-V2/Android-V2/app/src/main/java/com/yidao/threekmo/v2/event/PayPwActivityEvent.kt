package com.yidao.threekmo.v2.event

import com.ijustyce.fastkotlin.base.IBaseEvent

/**
 * Created by deepin on 18-1-12.
 */

interface PayPwClick {
    fun cancelOrConfirm()
    fun showInput()
}

open class PayPwActivityEvent(private val analytics: PayPwClick?) : PayPwClick, IBaseEvent() {

    override fun cancelOrConfirm() {
        analytics?.cancelOrConfirm()
    }

    override fun showInput() {
        analytics?.showInput()
    }
}
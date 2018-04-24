package com.yidao.threekmo.v2.event

import com.ijustyce.fastkotlin.base.IBaseEvent

/**
 * Created by manjaro on 18-1-31.
 */
interface WithdrawalDetailClick {
    fun contactUs()
}

open class WithdrawalDetailEvent(val click: WithdrawalDetailClick?):
        WithdrawalDetailClick, IBaseEvent() {

    override fun contactUs() {
        click?.contactUs()
    }
}
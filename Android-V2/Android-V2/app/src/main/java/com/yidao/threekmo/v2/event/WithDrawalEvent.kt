package com.yidao.threekmo.v2.event

import com.ijustyce.fastkotlin.base.IBaseEvent

/**
 * Created by manjaro on 18-1-30.
 */
interface WithDrawalClick {
    fun switchAccount()
    fun withdrawalAll()
    fun doWithdrawal()
}

open class WithDrawalEvent(val click: WithDrawalClick? = null): WithDrawalClick, IBaseEvent() {

    override fun switchAccount() {
        click?.switchAccount()
    }

    override fun withdrawalAll() {
        click?.withdrawalAll()
    }

    override fun doWithdrawal() {
        click?.doWithdrawal()
    }
}
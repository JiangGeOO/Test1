package com.yidao.threekmo.v2.event

import android.support.annotation.CallSuper
import com.ijustyce.fastkotlin.base.IBaseEvent
import com.yidao.threekmo.bean.MyRedBoxListBean

/**
 * Created by manjaro on 18-1-29.
 */

interface RedPocketClick {
    fun withdrawal()
    fun aboutMoney()
    fun viwWithdrawalDetail(bean: MyRedBoxListBean.RedPocketBean)
}

open class RedPocketEvent(val click: RedPocketClick? = null): RedPocketClick, IBaseEvent() {

    @CallSuper
    override fun withdrawal() {
        click?.withdrawal()
    }

    override fun viwWithdrawalDetail(bean: MyRedBoxListBean.RedPocketBean) {
        click?.viwWithdrawalDetail(bean)
    }

    override fun aboutMoney() {
        click?.aboutMoney()
    }
}
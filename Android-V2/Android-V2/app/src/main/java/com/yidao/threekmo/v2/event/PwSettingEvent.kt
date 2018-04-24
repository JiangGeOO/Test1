package com.yidao.threekmo.v2.event

import com.ijustyce.fastkotlin.base.IBaseEvent

/**
 * Created by deepin on 18-1-13.
 */
interface PwSettingClick {
    fun resetLoginPw()
    fun resetPayPw()
}

open class PwSettingEvent(private val analytics: PwSettingClick?): PwSettingClick, IBaseEvent() {

    override fun resetLoginPw() {

    }

    override fun resetPayPw() {

    }
}
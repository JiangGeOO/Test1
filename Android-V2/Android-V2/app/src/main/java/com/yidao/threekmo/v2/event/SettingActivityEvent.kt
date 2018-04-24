package com.yidao.threekmo.v2.event

import com.ijustyce.fastkotlin.base.IBaseEvent

/**
 * Created by deepin on 18-1-13.
 */
interface SettingClick {
    fun logout()
    fun passwordSetting()
    fun about()
    fun cleanCache()
}

open class SettingActivityEvent(private val analytics: SettingClick?) : SettingClick, IBaseEvent() {

    override fun logout() {
        analytics?.logout()
    }

    override fun passwordSetting() {
        analytics?.passwordSetting()
    }

    override fun about() {
        analytics?.about()
    }

    override fun cleanCache() {
        analytics?.cleanCache()
    }
}
package com.yidao.threekmo.v2.event

import com.ijustyce.fastkotlin.base.IBaseEvent

/**
 * Created by deepin on 18-1-15.
 */
interface PayDialogClick {
    fun close()
}

open class PayDialogEvent(private val analytics: PayDialogClick?): PayDialogClick, IBaseEvent() {

    override fun close() {
        analytics?.close()
    }
}
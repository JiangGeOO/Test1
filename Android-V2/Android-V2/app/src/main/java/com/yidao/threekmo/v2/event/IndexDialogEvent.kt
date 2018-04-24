package com.yidao.threekmo.v2.event

import com.ijustyce.fastkotlin.base.IBaseEvent
import com.yidao.threekmo.bean.DialogSecondResult
import com.yidao.threekmo.customview.IndexEaseDialog

/**
 * Created by deepin on 17-12-26.
 */
interface IndexDialogClick {
    fun share(data: DialogSecondResult?)
    fun view(data: DialogSecondResult?)
    fun close(data: DialogSecondResult)
}

open class IndexDialogEvent(private val analytics: IndexDialogClick?) :IBaseEvent(), IndexDialogClick {
    override fun share(data: DialogSecondResult?) {
        analytics?.share(data)
    }

    override fun view(data: DialogSecondResult?) {
        analytics?.view(data)
    }

    override fun close(data: DialogSecondResult) {
        analytics?.close(data)
    }
}
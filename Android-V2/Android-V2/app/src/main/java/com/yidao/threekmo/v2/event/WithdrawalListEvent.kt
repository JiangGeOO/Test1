package com.yidao.threekmo.v2.event

import com.ijustyce.fastkotlin.base.IBaseEvent

/**
 * Created by deepin on 18-2-6.
 */

interface withdrawalListClick {
    fun clickItem(id: String)
}

open class withdrawalListEvent(val click: withdrawalListClick? = null) : withdrawalListClick, IBaseEvent() {
    override fun clickItem(id: String) {
        click?.clickItem(id);
    }
}
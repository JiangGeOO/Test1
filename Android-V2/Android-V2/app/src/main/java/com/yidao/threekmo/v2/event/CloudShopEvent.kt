package com.yidao.threekmo.v2.event

import com.ijustyce.fastkotlin.base.IBaseEvent

/**
 * Created by deepin on 18-1-16.
 */
interface CloudShopClick{
    fun search()
}

open class CloudShopEvent(private val analytics: CloudShopClick?): IBaseEvent(), CloudShopClick {

    override fun search() {
        analytics?.search()
    }
}
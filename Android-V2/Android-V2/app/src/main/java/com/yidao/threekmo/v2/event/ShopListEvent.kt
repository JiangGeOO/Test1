package com.yidao.threekmo.v2.event

import com.ijustyce.fastkotlin.base.IBaseEvent
import com.yidao.threekmo.v2.model.ShopItem

/**
 * Created by deepin on 17-12-21.
 */

interface ShopListClick {
    fun viewShop(shopDetail: ShopItem)
}

open class ShopListEvent(private val analytics: ShopListClick?) : IBaseEvent(), ShopListClick {

    override fun viewShop(shopDetail: ShopItem) {
        analytics?.viewShop(shopDetail)
    }
}
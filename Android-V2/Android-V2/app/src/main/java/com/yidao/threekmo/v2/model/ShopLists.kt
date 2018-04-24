package com.yidao.threekmo.v2.model

import com.ijustyce.fastkotlin.base.BaseViewModel
import com.ijustyce.fastkotlin.utils.StringUtils
import java.util.ArrayList

data class ShopLists(val rspCode: Int = 0, val rspMsg: String = "", val data: ArrayList<ShopItem>?) : HttpResultList<ShopItem>() {
    override val list: ArrayList<ShopItem> get() = if (data == null) ArrayList() else data
}

data class ShopItem(val photoList: String? = null,
                    val isPanorama: Int = 0,
                    val gmtModified: Long = 0,
                    val labelNames: String = "",
                    val distance: String? = null,
                    val city: String? = null,
                    val cityCode: String? = null,
                    val modifier: String? = null,
                    val latitude: String = "",
                    val jumpUrl: String = "",
                    val sequencing: Int = 0,
                    val province: String? = null,
                    val labelId: String = "",
                    val enable: Int = 0,
                    val isPlane: Int = 0,   //  是否为平面
                    val id: Int = 0,
                    val longitude: String = "",
                    val area: String? = null,
                    val creator: String? = null,
                    val address: String? = null,
                    val provinceCode: String? = null,
                    val profile: String = "",
                    val gmtCreate: Long = 0,
                    val frontCover: String = "",
                    val coolect: String? = null,
                    val pageView: Int = 0,
                    val areaCode: String? = null,
                    val name: String = "",
                    val subjectTrainId: Int = 0) : BaseViewModel() {
    fun distance() : String {
        val distanceInt = StringUtils.getInt(distance)
        return if (distanceInt > 1000) (distanceInt / 1000).toString() + "KM" else distance + "M"
    }
}
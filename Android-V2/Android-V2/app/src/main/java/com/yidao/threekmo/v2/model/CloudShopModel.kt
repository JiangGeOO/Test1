package com.yidao.threekmo.v2.model

import com.ijustyce.fastkotlin.base.BaseViewModel
import com.ijustyce.fastkotlin.irecyclerview.IResponseData
import kotlin.collections.ArrayList

/**
 * Created by deepin on 18-1-10.
 */

data class CloudShopTopData(val listBurst: List<ListBurstItem>?,
                val listHandpick: List<ListHandpickItem>?,
                val listHot: ArrayList<ListHotItem>?,
                val listBanner: ArrayList<ListBannerItem>?)

data class CloudShopList(val data: CloudShopListData?): IResponseData<ListHotItem> {

    override val list: ArrayList<ListHotItem>
        get() = data?.datas ?: ArrayList()
}

data class CloudShopListData(val datas: ArrayList<ListHotItem>?)

data class ListBannerItem(val gmtModified: Long = 0,
                          val creator: String? = null,
                          val originalPrice: Int = 0,
                          val modifier: String? = null,
                          val photo: String = "",
                          val gmtCreate: Long = 0,
                          val title: String = "",
                          val type: String = "",
                          val sellingPrice: Int = 0,
                          val merchandiseId: Int = 0,
                          val enable: Int = 0,
                          val name: String = "",
                          val rank: Int = 0,
                          val id: Int = 0,
                          val describe: String = "")

//  三公里爆款  已废弃
data class ListBurstItem(val gmtModified: Long = 0,
                         val creator: String? = null,
                         val originalPrice: Int = 0,
                         val modifier: String? = null,
                         val photo: String = "",
                         val gmtCreate: Long = 0,
                         val title: String = "",
                         val type: String = "",
                         val sellingPrice: Int = 0,
                         val merchandiseId: Int = 0,
                         val enable: Int = 0,
                         val name: String = "",
                         val rank: Int = 0,
                         val id: Int = 0,
                         val describe: String = "")

//  优选商品
data class ListHandpickItem(val gmtModified: Long = 0,
                            val creator: String? = null,
                            val originalPrice: Int = 0,
                            val modifier: String? = null,
                            val photo: String = "",
                            val gmtCreate: Long = 0,
                            val title: String = "",
                            val type: String = "",
                            val sellingPrice: Int = 0,
                            val merchandiseId: Int = 0,
                            val enable: Int = 0,
                            val name: String = "",
                            val rank: Int = 0,
                            val id: Int = 0,
                            val describe: String = ""): BaseViewModel() {
    fun marginLeft(): Int {
        return if (position == 0) 30 else 0
    }

    fun getPrice(): String {
        return "${sellingPrice}元"
    }
}

//  全部商品    网星推荐
data class ListHotItem(val gmtModified: Long = 0,
                       val creator: String? = null,
                       val originalPrice: Int = 0,
                       val modifier: String? = null,
                       val photoUrl: String? = null,    //  used for all goods
                       val photo: String? = null,       //  used for star suggest
                       val gmtCreate: Long = 0,
                       val title: String = "",
                       val type: String = "",
                       val sellingPrice: String? = null,
                       val merchandiseId: String? = null,
                       val enable: Int = 0,
                       val name: String = "",
                       val rank: Int = 0,
                       val id: String? = null,
                       val describe: String? = ""): BaseViewModel() {

    fun getPrice(): String {
        return "${sellingPrice}元"
    }

    fun paddingLeft(): Int {
        return if (position % 2 == 0) 30 else 0
    }
}



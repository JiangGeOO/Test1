package com.yidao.threekmo.v2.activity

import android.databinding.ViewDataBinding
import com.ijustyce.fastkotlin.base.BaseListActivity
import com.ijustyce.fastkotlin.databinding.ListActivityView
import com.ijustyce.fastkotlin.http.HttpManager
import com.ijustyce.fastkotlin.irecyclerview.BindingInfo
import com.ijustyce.fastkotlin.irecyclerview.IAdapter
import com.ijustyce.fastkotlin.irecyclerview.IRecyclerView
import com.ijustyce.fastkotlin.utils.StringUtils
import com.yidao.threekmo.BR
import com.yidao.threekmo.R
import com.yidao.threekmo.application.MyApplication
import com.yidao.threekmo.databinding.NearByShopListView
import com.yidao.threekmo.retrofit_server.SubjectServer
import com.yidao.threekmo.v2.model.ShopItem
import com.yidao.threekmo.v2.model.ShopLists
import com.yidao.threekmo.v2.viewmodel.ShopListVm
import retrofit2.Call
import java.util.*

/**
 * Created by deepin on 17-12-20.
 */
class ShopListActivity : BaseListActivity<ListActivityView, ShopItem, ShopLists>() {

    private var shopIds : String? = null

    override fun cancelCreate(): Boolean {
        val bundle = intent.extras
        shopIds = bundle.getString("ids")
        return StringUtils.isEmpty(shopIds)
    }

    override fun bindingInfo(): BindingInfo? {
        val afterCreate = object : BindingInfo.ItemCreate {
            override fun <T> onCreated(item: T, position: Int, binding: ViewDataBinding?) {
                if (item is ShopItem && binding is NearByShopListView) {
                    setUpTags(binding, item)
                }
            }
        }
        return BindingInfo.fromLayoutIdAndBindName(R.layout.item_shop_list, BR.item)
                .add(BR.event, ShopListVm(this)).setAfterCreateCallBack(afterCreate)
    }

    private fun setUpTags(shopView: NearByShopListView, item: ShopItem) {
        val data = if (item.labelNames.length < 2) null else item.labelNames
                .substring(1, item.labelNames.length -1).split(",")
        shopView.tagList.layoutManager = IRecyclerView.buildHorizontalLinearLayout(this)
        val list = ArrayList<String>()
        if (data != null) list.addAll(data)
        val adapter = IAdapter<String>(this, list, BindingInfo
                .fromLayoutIdAndBindName(R.layout.item_shop_list_tag, BR.item))
        shopView.tagList.adapter = adapter
    }

    override fun getListCall(pageNo: Int): Call<ShopLists>? {
        return HttpManager.service(SubjectServer::class.java).goodsShopList(shopIds,
                MyApplication.getInstance().longitude.toString(),
                MyApplication.getInstance().latitude.toString())
    }

    override fun afterCreate() {
        titleBarView?.titleText?.set("体验店列表")
        contentView?.recyclerView?.list?.setBackGround(R.color.d8d8d8)
    }
}
package com.yidao.threekmo.v2.fragment

import com.ijustyce.fastkotlin.base.BaseListFragment
import com.ijustyce.fastkotlin.base.IBaseEvent
import com.ijustyce.fastkotlin.http.HttpManager
import com.ijustyce.fastkotlin.irecyclerview.BindingInfo
import com.ijustyce.fastkotlin.irecyclerview.IRecyclerView
import com.yidao.threekmo.BR
import com.yidao.threekmo.R
import com.yidao.threekmo.application.MyApplication
import com.yidao.threekmo.bean.FirstListResult
import com.yidao.threekmo.bean.MainHomeListItem
import com.yidao.threekmo.databinding.SeeShopFragmentView
import com.yidao.threekmo.parameter.WebParameters
import com.yidao.threekmo.retrofit_server.SubjectServer
import com.yidao.threekmo.v2.viewmodel.IndexFragmentVm
import retrofit2.Call

/**
 * Created by deepin on 18-1-8.
 */
class SeeShopFragment : BaseListFragment<SeeShopFragmentView, MainHomeListItem, FirstListResult>() {

    private var indexFragmentVm: IndexFragmentVm? = null

    override fun bindingInfo(): BindingInfo? {
        return BindingInfo.fromLayoutIdAndBindName(R.layout.item_fragm
                ent_see_shop, BR.item).add(BR.event, indexFragmentVm)
    }

    override fun viewModel(): IBaseEvent? {
        indexFragmentVm = indexFragmentVm ?: IndexFragmentVm(activity)
        return indexFragmentVm
    }

    override fun getListCall(pageNo: Int): Call<FirstListResult>? {
        val from = if (pageNo == 1) 0 else iDataInterface?.data?.size?: 0
        return HttpManager.service(SubjectServer::class.java).getActivityList("1", "", "",
                "", "", 0, 0,
                MyApplication.getInstance().longitude.toString(), MyApplication.getInstance().latitude.toString(),
                from, WebParameters.PAGE_SIZE)
    }

    override fun recyclerView(): IRecyclerView? {
        return contentView?.recyclerView?.list
    }

    override val layoutId: Int get() = R.layout.fragment_see_shop
}
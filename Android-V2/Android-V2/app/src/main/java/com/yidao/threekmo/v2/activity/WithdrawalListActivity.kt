package com.yidao.threekmo.v2.activity

import android.graphics.Color
import android.os.Build
import android.view.View
import com.ijustyce.fastkotlin.base.BaseListActivity
import com.ijustyce.fastkotlin.base.IBaseEvent
import com.ijustyce.fastkotlin.http.HttpManager
import com.ijustyce.fastkotlin.irecyclerview.BindingInfo
import com.ijustyce.fastkotlin.irecyclerview.IRecyclerView
import com.yidao.threekmo.BR
import com.yidao.threekmo.R
import com.yidao.threekmo.bean.WithdrawalList
import com.yidao.threekmo.databinding.WithdrawalListView
import com.yidao.threekmo.parameter.WebParameters
import com.yidao.threekmo.retrofit_server.UserServer
import com.yidao.threekmo.utils.LoginUtils
import com.yidao.threekmo.v2.viewmodel.WithDrawalListVm
import retrofit2.Call

/**
 * Created by manjaro on 18-1-29.
 */
class WithdrawalListActivity : BaseListActivity<WithdrawalListView, WithdrawalList.DataBean.DatasBean, WithdrawalList>() {

    override fun layoutId(): Int {
        return R.layout.activity_withdrawal_list
    }

    override fun viewModel(): IBaseEvent? {
        viewModel = WithDrawalListVm(this)
        return viewModel
    }

    override fun afterCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.parseColor("#ffffff")
        }
    }

    override fun bindingInfo(): BindingInfo? {
        return BindingInfo.fromLayoutIdAndBindName(R.layout.item_withdrawal_list, BR.item).add(BR.viewModel, viewModel)
    }

    override fun getListCall(pageNo: Int): Call<WithdrawalList>? {
        val from = if (pageNo == 1) 0 else iDataInterface?.data?.size?: 0
        return HttpManager.service(UserServer::class.java).withdrawalList(LoginUtils.getToken(this),
                from, WebParameters.PAGE_SIZE)
    }

    override fun emptyView(): View? {
        return contentView?.noDataView
    }

    override fun recyclerView(): IRecyclerView? {
        return contentView?.list
    }
}
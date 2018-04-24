package com.yidao.threekmo.v2.activity

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import com.ijustyce.fastkotlin.base.BaseListActivity
import com.ijustyce.fastkotlin.base.IBaseEvent
import com.ijustyce.fastkotlin.http.HttpManager
import com.ijustyce.fastkotlin.irecyclerview.BindingInfo
import com.ijustyce.fastkotlin.irecyclerview.IRecyclerView
import com.yidao.threekmo.BR
import com.yidao.threekmo.R
import com.yidao.threekmo.bean.MyRedBoxListBean
import com.yidao.threekmo.databinding.RedPacketActivityView
import com.yidao.threekmo.databinding.RedPacketTopView
import com.yidao.threekmo.parameter.WebParameters
import com.yidao.threekmo.retrofit_server.UserServer
import com.yidao.threekmo.utils.LoginUtils
import com.yidao.threekmo.v2.viewmodel.PwSettingVm
import com.yidao.threekmo.v2.viewmodel.RedPocketVm
import retrofit2.Call

/**
 * Created by manjaro on 18-1-29.
 */
class RedPocketsActivity : BaseListActivity<RedPacketActivityView, MyRedBoxListBean.RedPocketBean, MyRedBoxListBean>() {

    override fun layoutId(): Int {
        return R.layout.activity_red_pocket
    }

    override fun viewModel(): IBaseEvent? {
        viewModel = RedPocketVm(this)
        return viewModel
    }

    override fun afterCreate() {
        val topView = DataBindingUtil.inflate<RedPacketTopView>(
                LayoutInflater.from(activity), R.layout.view_red_pocket_top, null, false)
        topView.viewModel = viewModel as? RedPocketVm
        recyclerView?.addHeaderView(topView.root)
    }

    override fun bindingInfo(): BindingInfo? {
        return BindingInfo.fromLayoutIdAndBindName(R.layout.item_red_pocket, BR.item).add(BR.viewModel, viewModel)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        when (requestCode) {
            113 -> settingPayPw(data)
            115 -> {
                startActivity(Intent(activity, WithDrawalSelectActivity::class.java))
            }
        }
    }

    private fun settingPayPw(data: Intent?) {
        if (data == null || data.extras == null) return
        val intent = Intent(this, PayPwActivity::class.java)
        intent.putExtras(data)
        startActivityForResult(intent, PwSettingVm.RESULT_SET_PAY_PW)
        overridePendingTransition(R.anim.bottom_to_top, R.anim.anim_null)
    }

    override fun getListCall(pageNo: Int): Call<MyRedBoxListBean>? {
        val from = if (pageNo == 1) 0 else iDataInterface?.data?.size?: 0
        return HttpManager.service(UserServer::class.java).getRedBoxList(LoginUtils.getToken(this),
                from, WebParameters.PAGE_SIZE)
    }

    override fun recyclerView(): IRecyclerView? {
        return contentView?.list
    }
}
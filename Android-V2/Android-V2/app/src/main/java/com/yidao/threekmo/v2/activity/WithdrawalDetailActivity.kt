package com.yidao.threekmo.v2.activity

import android.graphics.Color
import android.os.Build
import com.ijustyce.fastkotlin.BR
import com.ijustyce.fastkotlin.base.IBaseActivity
import com.ijustyce.fastkotlin.base.IBaseEvent
import com.ijustyce.fastkotlin.http.HttpManager
import com.ijustyce.fastkotlin.irecyclerview.BindingInfo
import com.ijustyce.fastkotlin.irecyclerview.IAdapter
import com.ijustyce.fastkotlin.irecyclerview.IRecyclerView
import com.yidao.threekmo.R
import com.yidao.threekmo.bean.WithdrawalBean
import com.yidao.threekmo.bean.WithdrawalDetail
import com.yidao.threekmo.databinding.WithdrawalDetailView
import com.yidao.threekmo.retrofit_server.UserServer
import com.yidao.threekmo.utils.LoginUtils
import com.yidao.threekmo.v2.viewmodel.WithdrawalDetailVm
import retrofit2.Call
import retrofit2.Response

/**
 * Created by manjaro on 18-1-31.
 */

class WithdrawalDetailActivity: IBaseActivity<WithdrawalDetailView>() {

    private val listSize = 8

    override fun layoutId(): Int {
        return R.layout.activity_withdrawal_detail
    }

    override fun viewModel(): IBaseEvent? {
        viewModel = viewModel?: WithdrawalDetailVm(this)
        return viewModel
    }

    override fun afterCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.parseColor("#ffffff")
        }
        val id = intent.getStringExtra("id")
        id ?: kotlin.run {
            finish()
            return
        }
        getDetail(id)
    }

    private fun getDetail(id : String) {
        getNetData(object: HttpManager.HttpResult<WithdrawalDetail>{
            override fun success(call: Call<WithdrawalDetail>?, response: Response<WithdrawalDetail>?) {
                val detail = response?.body()?.data?.data
                detail?: return
                val valueList = arrayOf<String>(detail.type(), detail.account(), detail.payeeRealName,
                        detail.amount(), detail.fee(), detail.real(),
                        detail.orderNumber, detail.date(), detail.errorMsg?:"无")
                (viewModel as?WithdrawalDetailVm)?.onStatus(detail.status)
                setUpList(valueList)
            }
        }, HttpManager.service(UserServer::class.java).withdrawalDetail(LoginUtils.getToken(this), id))
    }

    private fun buildData(values: Array<String>): ArrayList<WithdrawalBean> {
        val array = ArrayList<WithdrawalBean>()
        val name = arrayOf("提现方式", "提现账号", "真实姓名", "提现金额", "手续费", "实际到账",
                "订单编号", "申请时间", "说明")
        for (index in 0..listSize) {
            val bean = WithdrawalBean(name[index], values[index], index)
            array.add(bean)
        }
        return array
    }

    private fun setUpList(values: Array<String>) {
        contentView?.list?.layoutManager = IRecyclerView.buildLinearLayout(this)
        contentView?.list?.setHasFixedSize(true)
        val bindInfo = BindingInfo.fromLayoutIdAndBindName(R.layout.item_withdrawal_detail, BR.viewModel)
        val adapter = IAdapter(this, buildData(values), bindInfo)
        contentView?.list?.adapter = adapter
    }
}

package com.yidao.threekmo.v2.activity

import com.ijustyce.fastkotlin.base.IBaseEvent
import com.yidao.threekmo.R
import com.yidao.threekmo.databinding.PayActivityView
import com.yidao.threekmo.v2.viewmodel.PayPwVm

/**
 * Created by deepin on 18-1-12.
 */
class PayPwActivity : BaseActivity<PayActivityView>() {

    override fun layoutId(): Int {
        return R.layout.activity_pay_password
    }

    override fun viewModel(): IBaseEvent? {
        return PayPwVm(this)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.anim_null, R.anim.top_to_bottom)
    }
}
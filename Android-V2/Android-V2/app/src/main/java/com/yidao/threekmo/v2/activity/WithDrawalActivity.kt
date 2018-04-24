package com.yidao.threekmo.v2.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import com.ijustyce.fastkotlin.base.IBaseActivity
import com.ijustyce.fastkotlin.base.IBaseEvent
import com.yidao.threekmo.R
import com.yidao.threekmo.databinding.WithdrawalView
import com.yidao.threekmo.v2.viewmodel.WithdrawalVm

/**
 * Created by manjaro on 18-1-29.
 */
class WithDrawalActivity : IBaseActivity<WithdrawalView>() {

    companion object {
        val TYPE_ALI_PAY = 101
        val TYPE_WE_CHAT = 102
        val TYPE_PAY = 103
    }

    override fun layoutId(): Int {
        return R.layout.activity_withdrawal
    }

    override fun viewModel(): IBaseEvent? {
        viewModel = viewModel?: WithdrawalVm(this)
        return viewModel
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        if (requestCode == TYPE_PAY) {
            (viewModel as? WithdrawalVm)?.doPay(data?.getStringExtra("code"), data?.getStringExtra("payPw"))
        }
    }

    override fun afterCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.parseColor("#ffffff")
        }
    }
}
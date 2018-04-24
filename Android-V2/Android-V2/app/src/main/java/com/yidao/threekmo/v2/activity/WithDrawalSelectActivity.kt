package com.yidao.threekmo.v2.activity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.View
import com.ijustyce.fastkotlin.base.IBaseActivity
import com.ijustyce.fastkotlin.base.IBaseEvent
import com.ijustyce.fastkotlin.event.TitleBarEvent
import com.yidao.threekmo.R
import com.yidao.threekmo.databinding.WithdrawalSelectView
import com.yidao.threekmo.v2.viewmodel.WithDrawalSelectVm

/**
 * Created by manjaro on 18-1-29.
 */
class WithDrawalSelectActivity : IBaseActivity<WithdrawalSelectView>() {

    override fun layoutId(): Int {
        return R.layout.activity_withdrawal_select
    }

    override fun viewModel(): IBaseEvent? {
        return WithDrawalSelectVm(this)
    }

    override fun afterCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.parseColor("#ffffff")
        }
        contentView?.titlebar?.setClickEvent(object: TitleBarEvent(){
            override fun rightTextClick(view: View) {
                startActivity(Intent(activity, WithdrawalListActivity::class.java))
            }
        })
    }
}
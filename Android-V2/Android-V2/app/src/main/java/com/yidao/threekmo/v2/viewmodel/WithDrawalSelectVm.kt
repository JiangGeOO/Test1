package com.yidao.threekmo.v2.viewmodel

import android.content.Intent
import com.yidao.threekmo.v2.activity.WithDrawalActivity
import com.yidao.threekmo.v2.activity.WithDrawalSelectActivity
import com.yidao.threekmo.v2.event.WithDrawalSelectClick
import com.yidao.threekmo.v2.event.WithDrawalSelectEvent

/**
 * Created by manjaro on 18-1-29.
 */
class WithDrawalSelectVm(val activity: WithDrawalSelectActivity?,
                         click: WithDrawalSelectClick? = null): WithDrawalSelectEvent(click) {

    override fun aliPay() {
        super.aliPay()
        goWithdrawal(WithDrawalActivity.TYPE_ALI_PAY)
    }

    override fun weChat() {
        super.weChat()
        goWithdrawal(WithDrawalActivity.TYPE_WE_CHAT)
    }

    private fun goWithdrawal(type: Int) {
        val intent = Intent(activity, WithDrawalActivity::class.java)
        intent.putExtra("type", type)
        activity?.startActivity(intent)
        activity?.finish()
    }
}
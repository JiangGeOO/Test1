package com.yidao.threekmo.v2.viewmodel

import android.app.Activity
import android.content.Intent
import com.yidao.threekmo.v2.activity.WithdrawalDetailActivity
import com.yidao.threekmo.v2.event.withdrawalListClick
import com.yidao.threekmo.v2.event.withdrawalListEvent

/**
 * Created by deepin on 18-2-6.
 */

class WithDrawalListVm(val activity: Activity?, click: withdrawalListClick? = null): withdrawalListEvent(click) {

    override fun clickItem(id: String) {
        val intent = Intent(activity, WithdrawalDetailActivity::class.java)
        intent.putExtra("id", id)
        activity?.startActivity(intent)
    }
}
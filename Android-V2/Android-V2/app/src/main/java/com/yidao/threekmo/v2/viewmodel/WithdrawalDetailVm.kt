package com.yidao.threekmo.v2.viewmodel

import android.databinding.ObservableField
import android.databinding.ObservableInt
import com.ijustyce.fastkotlin.utils.CommonTools
import com.yidao.threekmo.R
import com.yidao.threekmo.v2.activity.WithdrawalDetailActivity
import com.yidao.threekmo.v2.event.WithdrawalDetailClick
import com.yidao.threekmo.v2.event.WithdrawalDetailEvent

/**
 * Created by manjaro on 18-1-31.
 */
class WithdrawalDetailVm(val activity: WithdrawalDetailActivity, click: WithdrawalDetailClick? = null): WithdrawalDetailEvent(click) {

    var imgId = ObservableInt()
    var statusText = ObservableField<String>()
    var statusHint = ObservableField<String>()

    override fun contactUs() {
        super.contactUs()
        CommonTools.callPhone(activity, "4000888329")
    }

    fun onStatus(status: Int) {
        when(status) {
            1 -> {
                imgId.set(R.mipmap.icon_processing)
                statusText.set("提现申请处理中")
                statusHint.set("预计2个工作日到账，请注意查收")
            }
            2 -> {
                imgId.set(R.mipmap.icon_success)
                statusText.set("提现申请已成功")
                statusHint.set("您的提现已处理完毕，请查收")
            }
            else -> {
                imgId.set(R.mipmap.icon_failed)
                statusText.set("提现申请失败")
                statusHint.set("提现金额已返回余额中，原因详见提现说明")
            }
        }
    }
}
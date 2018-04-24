package com.yidao.threekmo.v2.viewmodel

import android.content.Intent
import android.os.Bundle
import com.yidao.threekmo.activitys.FindPsdActivity
import com.yidao.threekmo.v2.activity.PwSettingActivity
import com.yidao.threekmo.v2.event.PwSettingClick
import com.yidao.threekmo.v2.event.PwSettingEvent

/**
 * Created by deepin on 18-1-13.
 */
class PwSettingVm(private val activity: PwSettingActivity?, analytics: PwSettingClick? = null) : PwSettingEvent(analytics) {

    companion object {
        val SET_PAY_PW = 113
        val RESULT_SET_PAY_PW = 115
        val SET_LOGIN_PW = 114
    }

    override fun resetLoginPw() {
        checkCode(SET_LOGIN_PW, FindPsdActivity.TYPE_FIND_PW)
    }

    override fun resetPayPw() {
        checkCode(SET_PAY_PW, FindPsdActivity.TYPE_PAY_PW)
    }

    private fun checkCode(requestCode: Int, type: Int) {
        val bundle = Bundle()
        bundle.putInt("type", type)
        val intent = Intent(activity, FindPsdActivity::class.java)
        intent.putExtras(bundle)
        activity?.startActivityForResult(intent, requestCode)
    }
}
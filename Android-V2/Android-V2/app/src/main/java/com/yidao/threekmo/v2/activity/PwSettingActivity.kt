package com.yidao.threekmo.v2.activity

import android.app.Activity
import android.content.Intent
import com.ijustyce.fastkotlin.base.IBaseEvent
import com.ijustyce.fastkotlin.utils.ToastUtil
import com.yidao.threekmo.R
import com.yidao.threekmo.activitys.ModifyPsdActivity
import com.yidao.threekmo.databinding.ActivityPwSettingView
import com.yidao.threekmo.v2.viewmodel.PwSettingVm

/**
 * Created by deepin on 18-1-13.
 */
class PwSettingActivity : BaseActivity<ActivityPwSettingView>() {

    private lateinit var pwSettingVm: PwSettingVm

    override fun layoutId(): Int {
        return R.layout.activity_pw_setting
    }

    override fun viewModel(): IBaseEvent? {
        pwSettingVm = PwSettingVm(this)
        return pwSettingVm
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        when (requestCode) {
            PwSettingVm.SET_PAY_PW -> settingPayPw(data)
            PwSettingVm.SET_LOGIN_PW -> goResetPwView(data)
            PwSettingVm.RESULT_SET_PAY_PW -> setPayPwFinish(data)
        }
    }

    private fun setPayPwFinish(data: Intent?) {
        val bundle = data?.extras
        bundle ?: return
        val result = bundle.getBoolean("success")
        val msg = bundle.getString("msg")
        ToastUtil.show(if (result) "支付密码设置成功" else msg)
    }

    private fun goResetPwView(data: Intent?) {
        if (data == null || data.extras == null) return
        val intent = Intent(this, ModifyPsdActivity::class.java)
        intent.putExtras(data.extras!!)
        startActivity(intent)
        finish()
    }

    private fun settingPayPw(data: Intent?) {
        if (data == null || data.extras == null) return
        val intent = Intent(this, PayPwActivity::class.java)
        intent.putExtras(data)
        startActivityForResult(intent, PwSettingVm.RESULT_SET_PAY_PW)
        overridePendingTransition(R.anim.bottom_to_top, R.anim.anim_null)
    }
}
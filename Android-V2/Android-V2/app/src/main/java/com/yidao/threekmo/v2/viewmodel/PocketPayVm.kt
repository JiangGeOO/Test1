package com.yidao.threekmo.v2.viewmodel

import android.app.Activity
import android.content.Intent
import android.databinding.ObservableField
import com.ijustyce.fastkotlin.http.HttpManager
import com.ijustyce.fastkotlin.utils.Base64
import com.ijustyce.fastkotlin.utils.RSAUtils
import com.ijustyce.fastkotlin.utils.ToastUtil
import com.ijustyce.fastkotlin.viewmodel.PwKeyBoardClick
import com.yidao.threekmo.R
import com.yidao.threekmo.bean.BaseResult
import com.yidao.threekmo.parameter.WebParameters
import com.yidao.threekmo.retrofit_server.RegServer
import com.yidao.threekmo.utils.LoginUtils
import com.yidao.threekmo.v2.activity.PocketPayActivity
import com.yidao.threekmo.v2.event.PayDialogClick
import com.yidao.threekmo.v2.event.PayDialogEvent
import retrofit2.Call
import retrofit2.Response

/**
 * Created by deepin on 18-1-15.
 */

class PocketPayVm(private val activity: PocketPayActivity?, analytics: PayDialogClick? = null) : PayDialogEvent(analytics), PwKeyBoardClick {

    var money = ObservableField<String>("")
    var hint = ObservableField<String>("")
    var inputText = ObservableField<String>("")

    private val passwordLen = 6
    private var orderCode: String? = null

    override fun afterCreate() {
        val bundle = activity?.intent?.extras
        orderCode = bundle?.getString("orderCode", null)
        money.set(bundle?.getString("money", null))
        hint.set(bundle?.getString("hint", "") ?: "")
    }

    override fun close() {
        setResult(false)
    }

    override fun clickNumber(number: Int) {
        if (!canInputMore()) return
        inputText.set(inputText.get() + number)
        canInputMore()
    }

    private fun canInputMore(): Boolean {
        var canInputMore = true
        when (inputText.get()?.length) {
            1 -> updateColor(R.color.color_input_board)
            passwordLen -> canInputMore = false
        }
        if (!canInputMore) onInputFinish()
        return canInputMore
    }

    private fun onInputFinish() {
        val payPw = Base64.encode(RSAUtils.encryptByPublicKey(inputText.get()?.toByteArray(), WebParameters.publicKeyStr))
        if (orderCode == null) {
            setCode(inputText.get(), payPw)
            return
        }
        activity?.getNetData(object : HttpManager.HttpResult<BaseResult> {
            override fun success(call: Call<BaseResult>?, response: Response<BaseResult>?) {
                val baseBean = response?.body()
                if (baseBean?.rspCode == 0) {
                    setResult(true)
                    return
                }
                inputText.set("")
                ToastUtil.show(if (baseBean?.rspMsg != null) baseBean.rspMsg else "未知错误")
            }

            override fun failed(call: Call<BaseResult>?, t: Throwable?) {
                setResult(false)
            }
        }, HttpManager.service(RegServer::class.java).packetPay(LoginUtils.getToken(activity), orderCode, payPw))
    }

    private fun setCode(payPw: String?, code: String?) {
        val intent = Intent()
        intent.putExtra("code", code)
        intent.putExtra("payPw", payPw)
        activity?.setResult(Activity.RESULT_OK, intent)
        activity?.finish()
    }

    fun setResult(success: Boolean) {
        val intent = Intent()
        intent.putExtra("success", success)
        activity?.setResult(Activity.RESULT_OK, intent)
        activity?.finish()
    }

    override fun deleteInput() {
        val inputLen = inputText.get()?.length ?: 0
        inputText.set(inputText.get()?.substring(0, if (inputLen > 0) inputLen - 1 else 0))
    }

    private fun updateColor(colorRes: Int) {
        activity ?: return
        val color = activity.resources.getColor(colorRes)
        activity.contentView?.payPassword?.borderColor = color
        activity.contentView?.payPassword?.dividerColor = color
    }

    override fun hideKeyboard() {

    }
}
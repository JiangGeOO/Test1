package com.yidao.threekmo.v2.viewmodel

import android.app.Activity
import android.content.Intent
import android.databinding.ObservableField
import android.view.View
import com.ijustyce.fastkotlin.http.HttpManager
import com.ijustyce.fastkotlin.utils.*
import com.ijustyce.fastkotlin.viewmodel.PwKeyBoardClick
import com.yidao.threekmo.R
import com.yidao.threekmo.bean.BaseResult
import com.yidao.threekmo.retrofit_server.RegServer
import com.yidao.threekmo.utils.LoginUtils
import com.yidao.threekmo.v2.activity.PayPwActivity
import com.yidao.threekmo.v2.event.PayPwActivityEvent
import com.yidao.threekmo.v2.event.PayPwClick
import retrofit2.Call
import retrofit2.Response

/**
 * Created by deepin on 18-1-12.
 */
class PayPwVm(private val activity: PayPwActivity?, analytics: PayPwClick? = null, private val
pwKeyBoardClick: PwKeyBoardClick?  = null): PayPwActivityEvent(analytics), PwKeyBoardClick {

    private var isConfirmPage = false

    var title = ObservableField<String>("设置支付密码")
    var hint = ObservableField<String>("请输入6位不重复的数字密码")
    var inputText = ObservableField<String>("")
    var nextText = ObservableField<String>("取消")

    private val passwordLen = 6
    private var password: String? = null
    private var code: String? = null
    private var phone: String? = null

    override fun cancelOrConfirm() {
        if (!isConfirmPage) activity?.finish()
        else checkInput()
    }

    override fun showInput() {
        activity?.contentView?.keyboardView?.root?.visibility = View.VISIBLE
        activity?.contentView?.cancel?.visibility = View.GONE
    }

    private fun canInputMore():Boolean {
        var canInputMore = true
        when (inputText.get()?.length){
            1 -> updateColor(R.color.color_input_board)
            passwordLen -> canInputMore = false
        }
        if (!canInputMore) onInputFinish()
        return canInputMore
    }

    private fun onInputFinish() {
        if (password?.length == passwordLen) checkInput() else repeatInput()
    }

    override fun clickNumber(number: Int) {
        if (!canInputMore()) return
        pwKeyBoardClick?.clickNumber(number)
        inputText.set(inputText.get() + number)
        canInputMore()
    }

    private fun checkInput() {
        val repeatText = inputText.get()
        when {
            repeatText != password -> inputNotEquals()
            else -> setPassword()
        }
    }

    private fun updateConfirmStatus(isConfirmPage: Boolean) {
        this.isConfirmPage = isConfirmPage
        nextText.set(if (isConfirmPage) "确定" else "取消")
    }

    private fun inputNotEquals() {
        title.set("设置支付密码")
        inputError("两次输入不一致")
        updateConfirmStatus(false)
    }

    override fun afterCreate() {
        val bundle = activity?.intent?.extras
        code = bundle?.getString("code")
        phone = bundle?.getString("phone")
    }

    private fun setPassword() {
        val encrypt = Base64.encode(generatePw())
        activity?.getNetData(object: HttpManager.HttpResult<BaseResult> {
            override fun success(call: Call<BaseResult>?, response: Response<BaseResult>?) {
                val result = response?.body()
                result ?: return
                returnData(result.rspCode == 0, result.rspMsg)
            }
        }, HttpManager.service(RegServer::class.java).setPayPw(LoginUtils.getToken(activity), encrypt, code))
    }

    private fun returnData(success: Boolean, msg: String) {
        val intent = Intent()
        intent.putExtra("success", success)
        intent.putExtra("msg", msg)
        activity?.setResult(Activity.RESULT_OK, intent)
        activity?.finish()
    }

    private fun generatePw(): ByteArray? {
        val publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC1JHASpGA//mrq4z2HoRdfmzCv" +
                "QnNwm2aNdfBcA0eKxZm6yCpBBjhORDdslsa58eq6uMhLhyGags4PNOgZfhYdzmMxi2lTX5D9" +
                "00WLPNO5qdxQCiaN4Zcc/BwsFuc23F2X/R6mKRfNCbAzhIqcFDHQMJiloLT9iS7EGGPwIxd" +
                "NhwIDAQAB"
        return RSAUtils.encryptByPublicKey(password?.toByteArray(), publicKeyStr)
    }

    private fun updateColor(colorRes: Int) {
        activity?: return
        val color = activity.resources.getColor(colorRes)
        activity.contentView?.payPassword?.borderColor = color
        activity.contentView?.payPassword?.dividerColor = color
        activity.contentView?.hint?.setTextColor(color)
    }

    private fun inputError(msg: String) {
        password = null
        inputText.set("")
        ToastUtil.show(msg)
        updateColor(R.color.color_input_board_error)
    }

    private fun repeatInput() {
        password = inputText.get()
        inputText.set("")
        val number = StringUtils.getInt(password)
        if (number % 111111 == 0) {
            inputError("密码不能全部重复")
        }else {
            title.set("请再次输入")
            updateConfirmStatus(true)
        }
    }

    override fun deleteInput() {
        pwKeyBoardClick?.deleteInput()
        val inputLen = inputText.get()?.length ?: 0
        inputText.set(inputText.get()?.substring(0, if (inputLen > 0) inputLen -1 else 0))
    }

    override fun hideKeyboard() {
        pwKeyBoardClick?.hideKeyboard()
        activity?.contentView?.keyboardView?.root?.visibility = View.GONE
        activity?.contentView?.cancel?.visibility = View.VISIBLE
    }
}
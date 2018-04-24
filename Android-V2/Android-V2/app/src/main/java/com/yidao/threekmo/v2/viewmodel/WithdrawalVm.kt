package com.yidao.threekmo.v2.viewmodel

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import com.ijustyce.fastkotlin.http.HttpManager
import com.ijustyce.fastkotlin.utils.StringUtils
import com.ijustyce.fastkotlin.utils.ToastUtil
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.yidao.threekmo.R
import com.yidao.threekmo.bean.RedBoxBean
import com.yidao.threekmo.bean.WeChatBindInfo
import com.yidao.threekmo.bean.WithdrawlResult
import com.yidao.threekmo.retrofit_server.UserServer
import com.yidao.threekmo.utils.LoginUtils
import com.yidao.threekmo.utils.ShareUtil
import com.yidao.threekmo.v2.activity.PocketPayActivity
import com.yidao.threekmo.v2.activity.WithDrawalActivity
import com.yidao.threekmo.v2.activity.WithdrawalDetailActivity
import com.yidao.threekmo.v2.event.WithDrawalClick
import com.yidao.threekmo.v2.event.WithDrawalEvent
import retrofit2.Call
import retrofit2.Response

/**
 * Created by manjaro on 18-1-30.
 */

class WithdrawalVm(val activity: WithDrawalActivity, click: WithDrawalClick? = null) : WithDrawalEvent(click) {

    var switchBtVisible = ObservableInt(View.GONE)
    var aliAccountVisible = ObservableInt(View.VISIBLE)
    var weChatAccountVisible = ObservableInt(View.GONE)

    var accountHint = ObservableField<String>("输入支付宝账户")
    var money = ObservableField<String>("可提现余额")

    var account = ObservableField<String>()
    var name = ObservableField<String>()
    var withdrawalMoney = ObservableField<String>()

    private var totalMoney: String? = null
    private var type: Int? = null

    private var api: IWXAPI? = null
    private var receiver: BroadcastReceiver? = null
    private var weChatInfo: WeChatBindInfo.DataBeanX.DataBean? = null

    override fun onResume() {
        HttpManager.send(object : HttpManager.HttpResult<RedBoxBean> {
            override fun success(call: Call<RedBoxBean>?, response: Response<RedBoxBean>?) {
                totalMoney = response?.body()?.data?.data ?: ""
                money.set("可提现余额 ￥$totalMoney")
            }
        }, HttpManager.service(UserServer::class.java).getRedAllMoney(LoginUtils.getToken(activity)))
    }

    private fun checkWechat() {
        activity.getNetData(object : HttpManager.HttpResult<WeChatBindInfo> {
            override fun success(call: Call<WeChatBindInfo>?, response: Response<WeChatBindInfo>?) {
                val data = response?.body()?.data?.data
                if (data == null) {
                    bindWeChat()
                    return
                }
                updateWeChat(data)
            }
        }, HttpManager.service(UserServer::class.java).getWeChatInfo(LoginUtils.getToken(activity)))
    }

    private fun updateWeChat(weChatInfo: WeChatBindInfo.DataBeanX.DataBean) {
        account.set(weChatInfo.wxNickname)
        this.weChatInfo = weChatInfo
    }

    private fun bindWeChat() {
        val req = SendAuth.Req()
        req.scope = "snsapi_userinfo"
        req.state = "wechat_sdk_demo_test"
        IWXAPI(activity)?.sendReq(req)

        val intentFilter = IntentFilter(activity.packageName + ".weChatLogin")
        LocalBroadcastManager.getInstance(activity).registerReceiver(broadcastReceiver(), intentFilter)
    }

    override fun onDestroy() {
        if (receiver != null) {
            LocalBroadcastManager.getInstance(activity).unregisterReceiver(receiver!!)
            receiver = null
        }
    }

    private fun broadcastReceiver(): BroadcastReceiver {
        receiver = receiver ?: object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val code = intent?.getStringExtra("code")
                updateWeChat(code)
            }
        }
        return receiver!!
    }

    private fun updateWeChat(code: String?) {
        activity.getNetData(object : HttpManager.HttpResult<WeChatBindInfo> {
            override fun success(call: Call<WeChatBindInfo>?, response: Response<WeChatBindInfo>?) {
                val data = response?.body()?.data?.data
                data ?: return
                updateWeChat(data)
            }
        }, HttpManager.service(UserServer::class.java).bindWeChatInfo(LoginUtils.getToken(activity), code))
    }

    private fun IWXAPI(context: Context): IWXAPI? {
        api ?: kotlin.run {
            api = WXAPIFactory.createWXAPI(context.applicationContext, ShareUtil.WX_APP_ID, true)
            api?.registerApp(ShareUtil.WX_APP_ID)
        }
        return api
    }

    override fun switchAccount() {
        super.switchAccount()
        bindWeChat()
    }

    override fun withdrawalAll() {
        super.withdrawalAll()
        withdrawalMoney.set(totalMoney)
    }

    private fun checkInput(): Boolean {
        if (type == WithDrawalActivity.TYPE_ALI_PAY && account.get()?.length?:0 < 1) {
            ToastUtil.show("请输入支付宝账号")
            return false
        }
        if (name.get()?.length ?: 0 < 2) {
            ToastUtil.show("姓名不能少于2字符")
            return false
        }
        val money = StringUtils.getDouble(withdrawalMoney.get()?:"")
        if (money < 10 || money > 500) {
            ToastUtil.show("提现金额不能小于10或大于500")
            return false
        }
        return true
    }

    override fun doWithdrawal() {
        if (!checkInput()) return
        super.doWithdrawal()
        val bundle = Bundle()
        bundle.putString("money", "￥${withdrawalMoney.get()}")
        val intent = Intent(activity, PocketPayActivity::class.java)
        intent.putExtras(bundle)
        activity.startActivityForResult(intent, WithDrawalActivity.TYPE_PAY)
        activity.overridePendingTransition(R.anim.bottom_to_top, R.anim.anim_null)
    }

    fun doPay(code: String?, payPw: String?) {
        code ?: return
        if (type == WithDrawalActivity.TYPE_ALI_PAY) {
            aliPay(code)
            return
        }
        weChat(code)
    }

    private fun onResponse(response: WithdrawlResult) {
        if (response.rspCode == 0) {
            val intent = Intent(activity, WithdrawalDetailActivity::class.java)
            intent.putExtra("id", response.data?.data?.id)
            activity.startActivity(intent)
            activity.finish()
            return
        }
        ToastUtil.show(response.rspMsg)
    }

    private fun callBack(): HttpManager.HttpResult<WithdrawlResult> {
        return object : HttpManager.HttpResult<WithdrawlResult> {
            override fun success(call: Call<WithdrawlResult>?, response: Response<WithdrawlResult>?) {
                val bean = response?.body()
                bean ?: return
                onResponse(bean)
            }
        }
    }

    private fun aliPay(code: String) {
        activity.getNetData(callBack(), HttpManager.service(UserServer::class.java).withdrawalAlipay(
                LoginUtils.getToken(activity), account.get(), withdrawalMoney.get(), name.get(), code))
    }

    private fun weChat(code: String) {
        activity.getNetData(callBack(), HttpManager.service(UserServer::class.java).withdrawalWeChat(
                LoginUtils.getToken(activity), weChatInfo?.openId, withdrawalMoney.get(),
                name.get(), code, account.get(), "192.168.31.2"))
    }

    override fun afterCreate() {
        type = activity.intent?.getIntExtra("type", WithDrawalActivity.TYPE_ALI_PAY)
        if (type == WithDrawalActivity.TYPE_ALI_PAY) {
            switchBtVisible.set(View.GONE)
            aliAccountVisible.set(View.VISIBLE)
            weChatAccountVisible.set(View.GONE)
            accountHint.set("输入支付宝账户")
        } else {
            switchBtVisible.set(View.VISIBLE)
            aliAccountVisible.set(View.INVISIBLE)
            weChatAccountVisible.set(View.VISIBLE)
            accountHint.set("")
            checkWechat()
        }
    }
}
package com.yidao.threekmo.v2.viewmodel

import android.app.Dialog
import android.content.Intent
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.ijustyce.fastkotlin.http.HttpManager
import com.yidao.threekmo.R
import com.yidao.threekmo.activitys.FindPsdActivity
import com.yidao.threekmo.application.MyApplication
import com.yidao.threekmo.bean.HasSetPayPw
import com.yidao.threekmo.bean.MyRedBoxListBean
import com.yidao.threekmo.bean.RedBoxBean
import com.yidao.threekmo.retrofit_server.UserServer
import com.yidao.threekmo.utils.LoginUtils
import com.yidao.threekmo.v2.activity.RedPocketsActivity
import com.yidao.threekmo.v2.activity.WithDrawalSelectActivity
import com.yidao.threekmo.v2.activity.WithdrawalDetailActivity
import com.yidao.threekmo.v2.event.RedPocketClick
import com.yidao.threekmo.v2.event.RedPocketEvent
import retrofit2.Call
import retrofit2.Response

/**
 * Created by manjaro on 18-1-29.
 */
class RedPocketVm(val activity: RedPocketsActivity?, click: RedPocketClick? = null): RedPocketEvent(click) {

    var money = ObservableField<String>()
    var showWithdrawal = ObservableInt(View.GONE)

    override fun onResume() {
        HttpManager.send(object: HttpManager.HttpResult<RedBoxBean>{
            override fun success(call: Call<RedBoxBean>?, response: Response<RedBoxBean>?) {
                money.set(response?.body()?.data?.data ?:"")
            }
        }, HttpManager.service(UserServer::class.java).getRedAllMoney(LoginUtils.getToken(activity)))
    }

    override fun afterCreate() {
      //  showWithdrawal.set(if(MyApplication.isWithdraw) View.VISIBLE else View.VISIBLE)
        showWithdrawal.set(if(MyApplication.isWithdraw) View.VISIBLE else View.GONE)
    }

    override fun viwWithdrawalDetail(bean: MyRedBoxListBean.RedPocketBean) {
        bean.withdrawId?: return
        if ("withdraw".equals(bean.type)) {
            val intent = Intent(activity, WithdrawalDetailActivity::class.java)
            intent.putExtra("id", bean.withdrawId)
            activity?.startActivity(intent)
        }
    }

    override fun aboutMoney() {
        super.aboutMoney()
        val dialog = Dialog(activity, R.style.MoneyStyle)
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_money_show, null)
        val btnKnow = view.findViewById<View>(R.id.btn_know) as Button
        btnKnow.setOnClickListener { dialog.dismiss() }
        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
    }

    override fun withdrawal() {
        super.withdrawal()
        activity?.getNetData(object : HttpManager.HttpResult<HasSetPayPw>{
            override fun success(call: Call<HasSetPayPw>?, response: Response<HasSetPayPw>?) {
                val hasSetPayPw = response?.body()?.data
                val hasSet = "1".equals(hasSetPayPw?.data)
                if (!hasSet) {
                    val bundle = Bundle()
                    bundle.putInt("type", FindPsdActivity.TYPE_PAY_PW)
                    val intent = Intent(activity, FindPsdActivity::class.java)
                    intent.putExtras(bundle)
                    activity.startActivityForResult(intent, PwSettingVm.SET_PAY_PW)
                    return
                }
                activity.startActivity(Intent(activity, WithDrawalSelectActivity::class.java))
            }
        }, HttpManager.service(UserServer::class.java).hasSetPayPw(LoginUtils.getToken(activity)))
    }
}
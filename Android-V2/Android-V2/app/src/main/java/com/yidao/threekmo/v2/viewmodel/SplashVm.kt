package com.yidao.threekmo.v2.viewmodel

import android.content.Intent
import android.databinding.ObservableInt
import android.os.Handler
import android.util.DisplayMetrics
import android.view.View
import com.ijustyce.fastkotlin.http.FileAPI
import com.ijustyce.fastkotlin.http.HttpManager
import com.ijustyce.fastkotlin.utils.CommonTools
import com.ijustyce.fastkotlin.utils.StringUtils
import com.ijustyce.fastkotlin.utils.ToastUtil
import com.yidao.threekmo.R
import com.yidao.threekmo.activitys.IndexActivity
import com.yidao.threekmo.application.MyApplication
import com.yidao.threekmo.bean.UpdateBean
import com.yidao.threekmo.customview.CopyEaseDialog
import com.yidao.threekmo.retrofit_server.RegServer
import com.yidao.threekmo.utils.CommonUtil
import com.yidao.threekmo.v2.activity.SplashActivity
import com.yidao.threekmo.v2.event.SplashClick
import com.yidao.threekmo.v2.event.SplashEvent
import retrofit2.Call
import retrofit2.Response

/**
 * Created by deepin on 17-12-18.
 */
class SplashVm(val activity: SplashActivity, analytics: SplashClick? = null) : SplashEvent(analytics) {

    val url = "http://www.3kmo.cn/app/candidate/appEnroll"
    private var advertising = 0
    var imgResId = ObservableInt(R.mipmap.startup)
    var skipVisible = ObservableInt(View.GONE)
    var updateBean : UpdateBean? = null;

    override fun afterCreate() {
        activity.getNetData(object : HttpManager.HttpResult<UpdateBean> {
            override fun success(call: Call<UpdateBean>?, response: Response<UpdateBean>?) {
                updateBean = response?.body()
                updateBean?.data ?: return
                if (updateBean?.rspCode != 0) return
                handler?.removeCallbacksAndMessages(null)
                dealUpdate(updateBean)
                parseData(updateBean)
                MyApplication.isWithdraw = updateBean?.data?.isWithdraw?: false
            }
        }, HttpManager.service(RegServer::class.java).getVersion("android"), false)
        showSplash()

        val metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        CommonUtil.screenWidth = metrics.widthPixels
        CommonUtil.screenHeight = metrics.heightPixels
    }

    private fun dealUpdate(updateBean: UpdateBean?) {
        val appVersionName = CommonTools.versionName(activity)
        if (updateBean != null && updateBean.data != null &&
                StringUtils.isNewVersion(updateBean.data.versionDisplay, appVersionName)) {
            showNewVersion(updateBean)
        }else {
            showSplash(2000)
        }
    }

    override fun skip() {
        super.skip()
        doSkip()
    }

    private fun doSkip() {
        goMainView(false)
    }

    private fun goMainView(viewAds: Boolean) {
        val intent = Intent(activity, IndexActivity::class.java)
        intent.putExtra("showCloudShop", updateBean?.data?.button == 1)
        intent.putExtra("showExperience", updateBean?.data?.button1 == 1)
        intent.putExtra("drogue", updateBean?.data?.drogue == true)
        intent.putExtra("isNearby", updateBean?.data?.isNearby == true)
        if (viewAds) {
            intent.putExtra("url", url)
        }
        activity.startActivity(intent)
        activity.finish()
    }

    override fun viewAds() {
        super.viewAds()
        goMainView(true)
    }

    private fun showNewVersion(updateBean: UpdateBean?) {
        val alert = CopyEaseDialog.AlertDialogUser { confirmed, _ ->
            kotlin.run {
                if (confirmed) {
                    updateApk(updateBean)
                } else {
                    doSkip()
                }
            }
        }
        ToastUtil.show("正在下载新版本")
        CopyEaseDialog(activity, CommonTools.resString(activity, R.string.update_hint),
                updateBean?.data?.content, null, alert, "0".equals(updateBean?.data?.force)).show()
    }

    private fun updateApk(updateBean: UpdateBean?) {
        updateBean ?: return
        val fileApi = FileAPI.initByUrlAndListener(updateBean.data.url, null)
        fileApi.autoInstall(true).setNotifyView(CommonTools.resString(activity, R.string.down_title),
                CommonTools.resString(activity, R.string.down_hint), R.mipmap.ic_launcher)
                .startDownload(null)
    }

    private fun showSplash(delay : Long = 5000) {
        handler = Handler()
        handler?.postDelayed({
            doSkip()
        }, delay)
    }

    private fun parseData(updateBean: UpdateBean?) {
        updateBean ?: return
        advertising = updateBean.data.advertising
        if (advertising != 0) {
            imgResId.set(R.mipmap.ditu)
            skipVisible.set(View.VISIBLE)
        }
    }
}
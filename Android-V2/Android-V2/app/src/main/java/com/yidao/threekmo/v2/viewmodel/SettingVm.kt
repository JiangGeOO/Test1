package com.yidao.threekmo.v2.viewmodel

import android.content.Intent
import android.databinding.ObservableField
import com.ijustyce.fastkotlin.utils.CommonTools
import com.yidao.myutils.file.FileUtils
import com.yidao.myutils.file.SPUtils
import com.yidao.threekmo.activitys.IndexActivity
import com.yidao.threekmo.application.MyApplication
import com.yidao.threekmo.customview.CopyEaseDialog
import com.yidao.threekmo.customview.SuccessToast
import com.yidao.threekmo.v2.activity.PwSettingActivity
import com.yidao.threekmo.v2.activity.SettingActivity
import com.yidao.threekmo.v2.event.SettingActivityEvent
import com.yidao.threekmo.v2.event.SettingClick

/**
 * Created by deepin on 18-1-13.
 */
class SettingVm(val activity: SettingActivity, analytics: SettingClick? = null) : SettingActivityEvent(analytics) {

    var cacheSize = ObservableField<String>("")
    var version = ObservableField<String>("关于三公里")

    override fun afterCreate() {
        cacheSize.set(FileUtils.getTotalCacheSize(activity))
        version.set("关于三公里（V${CommonTools.versionName(activity)})")
    }

    override fun logout() {
        SPUtils.clear(activity)
        SuccessToast.showToast("退出登录", false, activity)
        MyApplication.getInstance().isNeedRefresh = true
        activity.startActivity(Intent(activity, IndexActivity::class.java))
    }

    override fun passwordSetting() {
        activity.startActivity(Intent(activity, PwSettingActivity::class.java))
    }

    override fun about() {
        super.about()
    }

    override fun cleanCache() {
        val alert = CopyEaseDialog.AlertDialogUser { confirmed, _ ->
            if (confirmed) {
                try {
                    FileUtils.clearAllCache(activity)
                    val allCacheSize = FileUtils.getTotalCacheSize(activity)
                    cacheSize.set(allCacheSize)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        CopyEaseDialog(activity, "是否要清理缓存", null, null, alert, true).show()
    }
}
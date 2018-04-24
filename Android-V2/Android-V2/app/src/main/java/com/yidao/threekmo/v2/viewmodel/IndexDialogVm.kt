package com.yidao.threekmo.v2.viewmodel

import android.app.Activity
import android.content.Intent
import android.view.View
import com.yidao.threekmo.activitys.WebViewScriptActivity
import com.yidao.threekmo.bean.DialogSecondResult
import com.yidao.threekmo.customview.IndexEaseDialog
import com.yidao.threekmo.v2.event.IndexDialogClick
import com.yidao.threekmo.v2.event.IndexDialogEvent
import com.yidao.threekmo.v2.ui.ShareUi

/**
 * Created by deepin on 17-12-26.
 */
class IndexDialogVm (val activity: Activity, val dialog: IndexEaseDialog?, val rootView: View,
                     analytics: IndexDialogClick?) : IndexDialogEvent(analytics) {

    private var shareUi: ShareUi? = null

    override fun share(data: DialogSecondResult?) {
        if (shareUi == null) {
            shareUi = ShareUi.getInstance(activity)
        }
        dialog?.dismiss()
        shareUi?.showPopwindow(rootView, data?.fenxiang,
                data?.candidate?.photo, data?.title, data?.desc)
    }

    override fun onDestroy() {
        shareUi?.onDestroy()
        shareUi = null
    }

    override fun view(data: DialogSecondResult?) {
        val intent = Intent(activity, WebViewScriptActivity::class.java)
        intent.putExtra("url", data?.url)
        activity.startActivity(intent)
        dialog?.dismiss()
    }

    override fun close(data: DialogSecondResult) {
        dialog?.dismiss()
    }
}
package com.yidao.threekmo.v2.viewmodel

import android.app.Activity
import android.content.Intent
import com.yidao.threekmo.activitys.ExperienceDetailActivity
import com.yidao.threekmo.activitys.WebViewScriptActivity
import com.yidao.threekmo.v2.event.ShopListClick
import com.yidao.threekmo.v2.event.ShopListEvent
import com.yidao.threekmo.v2.model.ShopItem

/**
 * Created by deepin on 17-12-21.
 */
class ShopListVm(val activity: Activity, analytics: ShopListClick? = null) : ShopListEvent(analytics) {

    override fun viewShop(shopDetail: ShopItem) {
        if (shopDetail.isPlane == 1) {
            val intent = Intent(activity, ExperienceDetailActivity::class.java)
            intent.putExtra("experId", shopDetail.id)
            activity.startActivity(intent)
        } else {
            val intent = Intent(activity, WebViewScriptActivity::class.java)
            intent.putExtra("url", shopDetail.jumpUrl)
            activity.startActivity(intent)
        }
    }
}
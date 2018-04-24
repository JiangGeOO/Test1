package com.yidao.threekmo.v2.viewmodel

import android.app.Activity
import android.content.Intent
import com.yidao.threekmo.activitys.ShopSearchActivity
import com.yidao.threekmo.v2.event.CloudShopClick
import com.yidao.threekmo.v2.event.CloudShopEvent

/**
 * Created by deepin on 18-1-16.
 */
class CloudShopVm(val activity: Activity?, analytics: CloudShopClick? = null): CloudShopEvent(analytics) {

    override fun search() {
        val intent = Intent(activity, ShopSearchActivity::class.java)
        activity?.startActivity(intent)
    }
}
package com.yidao.threekmo.v2.viewmodel

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.ijustyce.fastkotlin.utils.StringUtils
import com.yidao.threekmo.R
import com.yidao.threekmo.activitys.*
import com.yidao.threekmo.bean.AchieveBinnerResult
import com.yidao.threekmo.bean.MainHomeListItem
import com.yidao.threekmo.utils.LoginUtils
import com.yidao.threekmo.v2.IndexNewModel
import com.yidao.threekmo.v2.event.IndexFragmentClick
import com.yidao.threekmo.v2.event.IndexFragmentEvent
import com.yidao.threekmo.v2.ui.ShareUi

/**
 * Created by deepin on 18-1-10.
 */
class IndexFragmentVm (val activity: Activity?, analytics: IndexFragmentClick? = null) :
        IndexFragmentEvent (analytics) {

    private var indexNews: IndexNewModel? = null

    fun setNewsData(indexNews: IndexNewModel?) {
        this.indexNews = indexNews
    }

    override fun viewMoreNews() {
        activity?: return
        indexNews?.url ?: return
        val intent = Intent(activity, WebViewScriptActivity::class.java)
        intent.putExtra("url", indexNews?.url)
        activity.startActivity(intent)
    }

    override fun clickHowToWin() {
        activity?: return
        indexNews?.strategyLinkUrl ?: return
        val intent = Intent(activity, WebViewScriptActivity::class.java)
        intent.putExtra("url", indexNews?.strategyLinkUrl)
        activity.startActivity(intent)
    }

    override fun clickTopItem(item: AchieveBinnerResult.DataBean?) {
        item ?: return
        if (item.url.contains("token=")) {
            if (LoginUtils.isLogin(true, activity)) {
                if (!TextUtils.isEmpty(item.url)) {
                    val url = item.url + LoginUtils.getToken(activity)
                    val intent = Intent(activity, WebViewScriptActivity::class.java)
                    intent.putExtra("url", url)
                    activity?.startActivity(intent)
                }
            }
        } else {
            if (!TextUtils.isEmpty(item.url)) {
                val url = item.url
                val intent = Intent(activity, WebViewScriptActivity::class.java)
                intent.putExtra("url", url)
                activity?.startActivity(intent)
            }
        }
    }

    override fun clickLocation() {
        val intent = Intent(activity, CityMapActivity::class.java)
        activity?.startActivity(intent)
    }

    override fun search() {
        val intent = Intent(activity, ActiveSearchActivity::class.java)
        activity?.startActivity(intent)
    }

    private var shareUi: ShareUi? = null

    override fun onDestroy() {
        shareUi?.onDestroy()
    }

    fun share(item: MainHomeListItem?) {
        item?: return
        val indexActivity = activity as IndexActivity?
        indexActivity?: return
        if (shareUi == null) shareUi = ShareUi.getInstance(activity)
        var content = item.name
        if (StringUtils.isEmpty(content)) {
            content = "三公里APP"
        }
        shareUi?.showPopwindow(indexActivity.findViewById<View>(R.id.bottom_navigation_bar),
                item.activityUrl, item.photo, content, content)
    }

    override fun clickListItem(item: MainHomeListItem?) {
        item ?: return
        if (item.type == 0) {
            val id = item.id
            //活动走的activity
            val intent = Intent(activity, ActiveActivity::class.java)
            intent.putExtra("id", id)
            activity?.startActivity(intent)
        } else {
            val url = item.activityUrl
            val name = item.name
            val intent = Intent(activity, WebViewScriptActivity::class.java)
            intent.putExtra("url", url)
            intent.putExtra("title", name)
            activity?.startActivity(intent)
        }
    }
}
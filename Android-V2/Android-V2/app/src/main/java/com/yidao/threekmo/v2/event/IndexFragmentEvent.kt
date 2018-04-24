package com.yidao.threekmo.v2.event

import com.ijustyce.fastkotlin.base.IBaseEvent
import com.yidao.threekmo.bean.MainHomeListItem
import com.yidao.threekmo.bean.AchieveBinnerResult.DataBean

/**
 * Created by deepin on 18-1-10.
 */

interface IndexFragmentClick {
    fun viewMoreNews()
    fun clickHowToWin()
    fun clickTopItem(item: DataBean?)
    fun clickListItem(item: MainHomeListItem?)
    fun clickLocation()
    fun search()
}

open class IndexFragmentEvent(private val analytics: IndexFragmentClick?) : IBaseEvent(), IndexFragmentClick {
    override fun viewMoreNews() {
        analytics?.viewMoreNews()
    }

    override fun clickLocation() {
        analytics?.clickLocation()
    }

    override fun search() {
        analytics?.search()
    }

    override fun clickHowToWin() {
        analytics?.clickHowToWin()
    }

    override fun clickTopItem(item: DataBean?) {
        analytics?.clickTopItem(item)
    }

    override fun clickListItem(item: MainHomeListItem?) {
        analytics?.clickListItem(item)
    }
}
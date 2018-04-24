package com.yidao.threekmo.v2.viewmodel

import com.yidao.threekmo.v2.activity.IndexActivity
import com.yidao.threekmo.v2.event.IndexActivityEvent
import com.yidao.threekmo.v2.event.IndexClick

/**
 * Created by deepin on 17-12-20.
 */
class IndexActivityVm(val activity: IndexActivity, analytics: IndexClick? = null) : IndexActivityEvent(analytics) {
}
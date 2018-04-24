package com.yidao.threekmo.v2.event

import com.ijustyce.fastkotlin.base.IBaseEvent

/**
 * Created by deepin on 17-12-20.
 */

interface IndexClick {

}

open class IndexActivityEvent(private val analytics: IndexClick?) : IBaseEvent(), IndexClick {
}
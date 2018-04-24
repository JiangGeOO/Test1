package com.ijustyce.fastkotlin.viewmodel

import com.ijustyce.fastkotlin.base.IBaseEvent

/**
 * Created by deepin on 18-1-12.
 */
interface PwKeyBoardClick {
    fun clickNumber(number: Int)
    fun deleteInput()
    fun hideKeyboard()
}

open class PwKeyBoardEvent (private val analytics: PwKeyBoardClick?) : PwKeyBoardClick, IBaseEvent() {

    override fun clickNumber(number: Int) {
        analytics?.clickNumber(number)
    }

    override fun deleteInput() {
        analytics?.deleteInput()
    }

    override fun hideKeyboard() {
        analytics?.hideKeyboard()
    }
}
package com.ijustyce.fastkotlin.viewmodel

import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.view.View

import com.ijustyce.fastkotlin.R
import com.ijustyce.fastkotlin.base.BaseViewModel

/**
 * Created by yangchun on 2016/11/12.ss
 */

class CommonTitleBarView : BaseViewModel() {

    var leftText = ObservableField<String>()
    var leftTextColor = ObservableInt()

    var titleText = ObservableField<String>()
    var titleTextColor = ObservableInt()

    var rightText = ObservableField<String>()
    var rightTextColor = ObservableInt()

    var leftIcon = ObservableInt()
    var rightIcon = ObservableInt()

    var showLine = ObservableInt()
    var titleBg = ObservableInt()

    init {
        leftTextColor.set(R.color.color_title_bar_title)
        rightTextColor.set(R.color.color_title_bar_title)
        titleTextColor.set(R.color.color_title_bar_title)
        leftIcon.set(R.mipmap.icon_back)
        rightIcon.set(-100)
        titleBg.set(R.color.white)
        showLine.set(View.GONE)
    }
}
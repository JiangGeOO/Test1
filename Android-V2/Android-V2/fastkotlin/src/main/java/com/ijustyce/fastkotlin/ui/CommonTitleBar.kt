package com.ijustyce.fastkotlin.ui

import android.content.Context
import android.databinding.DataBindingUtil
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.ijustyce.fastkotlin.R
import com.ijustyce.fastkotlin.databinding.TitleBarView
import com.ijustyce.fastkotlin.event.TitleBarEvent
import com.ijustyce.fastkotlin.utils.getStatusHeight
import com.ijustyce.fastkotlin.viewmodel.CommonTitleBarView

/**
 * Created by yangchun on 2016/11/12.
 */

class CommonTitleBar : RelativeLayout {

    private var leftIconId: Int = 0
    private var leftText: String? = null
    private var leftTxtColor: Int = 0
    private var titleTxt: String? = null
    private var rightText: String? = null
    private var rightTxtColor: Int = 0
    private var rightIconId: Int = 0

    private var titleColor: Int = 0
    private var bgColor: Int = 0

    private var showLine: Boolean = true

    var viewModel: CommonTitleBarView? = null
        private set
    var titleBarView: TitleBarView? = null

    constructor(context: Context) : super(context) {}

    @JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyle: Int = 0) : super(context, attrs, R.style.commonTitleStyle) {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.CommonTitleBar, defStyle, 0)
        leftText = arr.getString(R.styleable.CommonTitleBar_leftTxt)
        leftIconId = arr.getResourceId(R.styleable.CommonTitleBar_leftIcon, -100)
        rightIconId = arr.getResourceId(R.styleable.CommonTitleBar_rightIcon, -100)
        bgColor = arr.getResourceId(R.styleable.CommonTitleBar_titleBg, -100)
        titleTxt = arr.getString(R.styleable.CommonTitleBar_titleTxt)
        rightText = arr.getString(R.styleable.CommonTitleBar_rightTxt)
        showLine = arr.getBoolean(R.styleable.CommonTitleBar_showLine, false)

        val fitWindows = arr.getBoolean(R.styleable.CommonTitleBar_fitWindows, false)

        titleColor = arr.getColor(R.styleable.CommonTitleBar_titleColor, resources.getColor(R.color.color_title_bar_title))
        rightTxtColor = arr.getColor(R.styleable.CommonTitleBar_rightTxtColor, resources.getColor(R.color.color_title_bar_title))
        leftTxtColor = arr.getColor(R.styleable.CommonTitleBar_leftTextColor, resources.getColor(R.color.color_title_bar_title))
        arr.recycle()

        titleBarView = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.fastandroiddev3_common_header, this, true)
        if (fitWindows) {
            val view = titleBarView?.topView
            view ?: return
            val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams?
            layoutParams?.height = getStatusHeight(context) + (layoutParams?.height?: 0)
            view.setPadding(view.paddingLeft, view.paddingTop + getStatusHeight(context),
                    view.paddingRight, view.paddingBottom)
        }
    }

    fun setClickEvent(event: TitleBarEvent?) {
        event ?: return
        titleBarView ?: return
        titleBarView?.clickEvent = event
    }

    private fun initView() {
        viewModel = CommonTitleBarView()
        viewModel?.showLine?.set(if (showLine) View.VISIBLE else View.GONE)
        if (leftIconId != -100) {
            viewModel?.leftIcon?.set(leftIconId)
        }
        viewModel?.rightIcon?.set(rightIconId)
        if (bgColor != -100) {      //  防止删除默认值
            viewModel?.titleBg?.set(bgColor)
        }
        viewModel?.leftText?.set(leftText)
        viewModel?.leftTextColor?.set(leftTxtColor)
        viewModel?.titleText?.set(titleTxt)
        viewModel?.titleTextColor?.set(titleColor)
        viewModel?.rightTextColor?.set(rightTxtColor)
        viewModel?.rightText?.set(rightText)

        titleBarView?.viewModel = viewModel
        super.onFinishInflate()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (isInEditMode || titleBarView == null) {
            return
        }
        initView()
    }
}
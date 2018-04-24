package com.ijustyce.fastkotlin.ui

import android.app.Activity
import android.app.Dialog
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import com.ijustyce.fastkotlin.callback.ActivityLifeCall

/**
 * Created by yangchun on 16/8/24.
 */

abstract class FloatDialog<Bind : ViewDataBinding> (val activity: Activity) : Dialog(activity) {

    var viewLife: FloatViewLife? = FloatViewLife()
    var view: View? = null
    private var shouldShow: Boolean = false

    private var isViewAdded = false
    var contentView: Bind? = null

    private var lastX: Float = 0.toFloat()
    private var lastY: Float = 0.toFloat()
    private var nowX: Float = 0.toFloat()
    private var nowY: Float = 0.toFloat()
    private var tranX: Float = 0.toFloat()
    private var tranY: Float = 0.toFloat()
    private var onClickListener: View.OnClickListener? = null
    private var hasMove = false

    inner class FloatViewLife : ActivityLifeCall {
        override fun onResume(activity: Activity) {
            show()
        }

        override fun onPause(activity: Activity) {
            hide()
        }

        override fun onCreate(activity: Activity) {

        }

        override fun onStop(activity: Activity) {

        }

        override fun onDestroy(activity: Activity) {

        }

        override fun dispatchTouchEvent(event: MotionEvent, activity: Activity) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contentView = DataBindingUtil.inflate(LayoutInflater.from(activity), getLayoutId(),
                null, false)
        setContentView(contentView?.root)
        contentView?.root?.setOnTouchListener { _, motionEvent -> onTouchEvent(motionEvent) }
    }

    abstract fun getLayoutId(): Int

    //  是否应该显示悬浮窗!
    fun setShouldShow(shouldShow: Boolean) {
        this.shouldShow = shouldShow
        if (shouldShow) show() else cancel()
    }

    fun destroy() {
        if (isShowing) cancel()
        view = null
        onClickListener = null
        shouldShow = false
        viewLife = null
    }

    private fun updateView() {
//        if (view != null && wm != null && wmParams != null) {
//            wm!!.updateViewLayout(view, wmParams)
//        }
    }

    fun setOnClickListener(onClickListener: View.OnClickListener) {
        this.onClickListener = onClickListener
    }

    public override fun onTouchEvent(event: MotionEvent): Boolean {
        var ret = false
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // 获取按下时的X，Y坐标
                lastX = event.rawX
                lastY = event.rawY
                hasMove = false
                ret = true
            }
            MotionEvent.ACTION_MOVE -> {
                // 获取移动时的X，Y坐标
                nowX = event.rawX
                nowY = event.rawY
                if (lastY == nowY && lastX == nowX) {
                    hasMove = false    //      设置没有移动过
                    return ret
                }
                // 计算XY坐标偏移量
                tranX = nowX - lastX
                tranY = nowY - lastY
                // 移动悬浮窗
                //更新悬浮窗位置
                updateView()
                //记录当前坐标作为下一次计算的上一次移动的位置坐标
                lastX = nowX
                lastY = nowY
                hasMove = true     //      设置已经移动过了
            }
            MotionEvent.ACTION_UP -> if (!hasMove && onClickListener != null) {
                onClickListener!!.onClick(view)
            }
        }
        return ret
    }
}

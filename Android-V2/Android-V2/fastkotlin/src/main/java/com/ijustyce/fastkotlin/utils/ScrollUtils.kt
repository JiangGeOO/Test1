package com.ijustyce.fastkotlin.utils

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent

import java.lang.ref.WeakReference

/**
 * Created by yangchun on 16/8/5.
 */

class ScrollUtils(context: Context?, scrollListener: ScrollListener) {

    private val listener: WeakReference<ScrollListener>?
    private val mGestureDetector: GestureDetector?

    interface ScrollListener {
        fun scrollUp(value: Float)
        fun scrollDown(value: Float)
    }

    private fun onScrollDown(value: Float) {
        val listener = this.listener?.get()
        listener?.scrollDown(value)
    }

    private fun onScrollUp(value: Float) {
        val listener = this.listener?.get()
        listener?.scrollUp(value)
    }

    init {

        val gestureListener = object : GestureDetector.OnGestureListener {
            override fun onDown(e: MotionEvent): Boolean {
                return false
            }

            override fun onShowPress(e: MotionEvent) {

            }

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                ILog.e("===scroll===", "onSingleTapUp")
                onScrollUp(-1f)
                return false
            }

            override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
                Log.e("e2", distanceY.toString() + "")
                if (distanceY > distanceX || -distanceY > -distanceX) {
                    if (distanceY > -5 && distanceY < 5) return false  //  -5 到 5不处理，防止抖动！
                    if (distanceY < 0)
                        onScrollDown(distanceY)
                    else
                        onScrollUp(distanceY)
                }
                return false
            }

            override fun onLongPress(e: MotionEvent) {

            }

            /**
             *
             * @param e1            首次手势点的移动事件
             * @param e2            当前手势点的移动事件
             * @param velocityX     每秒x轴方向的移动速度
             * @param velocityY     每秒y轴方向的移动速度
             * @return
             */
            override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
                //                if (listener == null || listener.get() == null) return false;
                //                if (e1 == null || e2 == null) return false;
                //                float distance = e1.getY() - e2.getY();
                //                if (distance > FLING_MIN_DISTANCE
                //                        && Math.abs(velocityY) > FLING_MIN_VELOCITY) {
                //                    listener.get().scrollUp(distance);
                //                } else if (e2.getY()-e1.getY() > FLING_MIN_DISTANCE
                //                        && Math.abs(velocityY) > FLING_MIN_VELOCITY) {
                //                    listener.get().scrollDown(-distance);
                //                }
                return false
            }
        }

        listener = WeakReference(scrollListener)
        mGestureDetector = GestureDetector(context, gestureListener)
    }

    fun onScroll(event: MotionEvent) {
        mGestureDetector?.onTouchEvent(event)
        if (event.action == MotionEvent.ACTION_UP) {
            onScrollUp(-1f)
        }
    }
}

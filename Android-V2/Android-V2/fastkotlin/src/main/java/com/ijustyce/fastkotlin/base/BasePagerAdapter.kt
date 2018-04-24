package com.ijustyce.fastkotlin.base

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ijustyce.fastkotlin.irecyclerview.BindingInfo
import java.util.ArrayList

/**
 * Created by yangchun on 2017/2/17.
 */

class BasePagerAdapter<T>(context: Context, private val data: ArrayList<T>?,
                          private val bindingInfo: BindingInfo) : PagerAdapter() {

    private val view: ViewDataBinding? = null
    private val layoutInflater: LayoutInflater

    private var mChildCount = 0

    init {
        mChildCount = data?.size ?: 0
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return data?.size ?: 0
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(paramView: ViewGroup, paramInt: Int, paramObject: Any) {
        if (paramObject is View) {
            paramView.removeView(paramObject)
        }
    }

    override fun instantiateItem(paramView: ViewGroup, paramInt: Int): Any {
        val localView = buildView(paramView)
        val bean = getObject(paramInt)
        for (i in 0 until bindingInfo.size) {
            val value = bindingInfo.info.valueAt(i)
            localView.setVariable(bindingInfo.info.keyAt(i), value ?: bean)
        }
        localView.executePendingBindings()
        paramView.addView(localView.root)
        return localView.root
    }

    private fun buildView(viewGroup: ViewGroup): ViewDataBinding {
        return DataBindingUtil.inflate(layoutInflater, bindingInfo.layoutId, viewGroup, false)
    }

    fun getObject(position: Int): T? {
        return if (data == null || position < 0 || position >= data.size) {
            null
        } else data[position]
    }

    override fun notifyDataSetChanged() {
        mChildCount = data?.size ?: 0
        super.notifyDataSetChanged()
    }

    override fun getItemPosition(`object`: Any): Int {
        if (mChildCount > 0) {
            mChildCount--  //  这里不能直接 = 0 否则第二个依旧不会变
            return PagerAdapter.POSITION_NONE
        }
        return super.getItemPosition(`object`)
    }
}

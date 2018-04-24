package com.ijustyce.fastkotlin.base

import android.databinding.ViewDataBinding
import android.support.annotation.CallSuper
import android.support.v4.view.ViewPager
import com.ijustyce.fastkotlin.irecyclerview.BindingInfo
import java.util.ArrayList

/**
 * Created by yangchun on 2017/2/17.
 */

abstract class BasePageActivity<Bind : ViewDataBinding, Bean> : IBaseActivity<Bind>() {

    var data: ArrayList<Bean>? = null
    var adapter: BasePagerAdapter<Bean>? = null

    abstract fun viewPager(): ViewPager?
    abstract fun bindingInfo() : BindingInfo

    @CallSuper
    override fun afterCreate() {
        data = ArrayList()
        val pager = viewPager()
        pager?.offscreenPageLimit = 3
        adapter = BasePagerAdapter(this, data, bindingInfo())
        pager?.adapter = adapter
        pager?.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
                pageScrollStateChanged(state)
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                pageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                pageSelect(position)
            }
        })
    }

    open fun pageSelect(position: Int) {

    }

    open fun pageScrollStateChanged(state: Int) {

    }

    open fun pageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    fun dataChanged() {
        if (data != null && adapter != null) {
            adapter!!.notifyDataSetChanged()
        }
    }
}

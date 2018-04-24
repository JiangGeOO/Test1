package com.ijustyce.fastkotlin.irecyclerview

import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.util.SparseArray

/**
 * Created by yangchun on 16/7/19.
 */

class BindingInfo private constructor(@param:LayoutRes var layoutId: Int, key: Int) {

    var info = SparseArray<Any>()
    var size = 1
    var callBack: ItemCreate? = null
    var viewHolderCallBack: ViewHolderCallBack? = null
    var viewHolderCreate: ViewHolderCreate? = null

    init {
        info.put(key, null)
    }

    interface ItemCreate {
        fun <T> onCreated(item: T, position: Int, binding: ViewDataBinding?)
    }

    interface ViewHolderCallBack {
        fun <T> getViewType(item: T?): Int
        fun getLayoutId(type: Int): Int
    }

    interface ViewHolderCreate {
        fun onCreate(binding: ViewDataBinding)
    }

    fun setAfterViewHolderCreate(viewHolderCreate: ViewHolderCreate): BindingInfo {
        this.viewHolderCreate = viewHolderCreate
        return this
    }

    fun setAfterCreateCallBack(afterCreateCallBack: ItemCreate): BindingInfo {
        this.callBack = afterCreateCallBack
        return this
    }

    fun setViewHolderCallBack(callBack: ViewHolderCallBack): BindingInfo {
        this.viewHolderCallBack = callBack
        return this
    }

    fun add(key: Int, value: Any?): BindingInfo {
        info.put(key, value)
        size = info.size()
        return this
    }

    companion object {
        fun fromLayoutIdAndBindName(@LayoutRes layoutId: Int, key: Int): BindingInfo {
            return BindingInfo(layoutId, key)
        }
    }
}

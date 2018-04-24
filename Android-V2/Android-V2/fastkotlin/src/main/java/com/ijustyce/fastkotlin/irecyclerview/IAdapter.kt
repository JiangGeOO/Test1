package com.ijustyce.fastkotlin.irecyclerview

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ijustyce.fastkotlin.base.BaseViewModel
import com.ijustyce.fastkotlin.utils.ILog
import java.util.*

/**
 * Created by yangchun on 16/4/15.  通用的RecyclerView adapter
 */
class IAdapter<T>(mContext: Context?, private var mData: ArrayList<T>?, val bindingInfos: BindingInfo?) : RecyclerView.Adapter<IBindingHolder>() {
    private var header: ArrayList<View>? = null
    private var footer: ArrayList<View>? = null
    var context: Context? = null
        private set
    /**
     * 返回最后可见的item的position
     */
    var lastPosition: Int = 0
    var headerSize: Int = 0
    var footerSize: Int = 0
    private var recyclerView: RecyclerView? = null
    private var handler: Handler? = null
    var layoutId: Int = 0
    var scrollFinished = true

    val isFooterVisible: Boolean
        get() = lastPosition >= itemCount - 2

    val dataSize: Int get() = mData?.size ?: 0

    private val run = Runnable { doEvent() }

    private val changedItem = SparseIntArray()

    init {
        this.context = mContext
        this.layoutId = bindingInfos?.layoutId ?: 0
        handler = Handler()
    }

    override fun onViewAttachedToWindow(holder: IBindingHolder?) {
        super.onViewAttachedToWindow(holder)
        holder ?: return
        val lp = holder.itemView.layoutParams
        lp ?: return
        if (lp !is StaggeredGridLayoutManager.LayoutParams) {
            return
        }
        if (holder.header || holder.footer) {
            lp.isFullSpan = true
        }
    }

    fun removeItem(position: Int) {
        if (position < 0 || dataSize < 1) return
        mData?.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addFooterView(footerView: View) {
        if (footer == null) {
            footer = ArrayList()
            footerSize = 0
        }
        var position = footerSize - 1
        position = if (position > -1) position else 0
        footer?.add(position, footerView)
        footerSize++
    }

    fun addHeaderView(headerView: View) {
        if (header == null) {
            header = ArrayList()
            headerSize = 0
        }
        header?.add(headerSize, headerView)
        headerSize++
    }

    override fun getItemViewType(position: Int): Int {
        if (position > -1 && position < headerSize) {
            return position
        }
        return if (position >= dataSize + headerSize) {
            position
        } else {
            if (bindingInfos?.viewHolderCallBack != null) {
                bindingInfos.viewHolderCallBack!!.getViewType<T>(getObject(position - headerSize))
            } else {
                TYPE_NORMAL
            }
        }
    }

    fun setRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
    }

    override fun getItemCount(): Int {
        val size = dataSize
        return size + headerSize + footerSize
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IBindingHolder {
        val commonHolder: IBindingHolder
        when (viewType) {
            TYPE_NORMAL -> commonHolder = createViewHolder(layoutId, context, parent)

            else ->
                //  header
                if (viewType > -1 && viewType < headerSize) {
                    commonHolder = IBindingHolder(header!![viewType])
                    commonHolder.header = true
                } else if (viewType >= dataSize + headerSize) {
                    commonHolder = IBindingHolder(footer!![viewType - headerSize - dataSize])
                    commonHolder.footer = true
                } else {
                    //  use very layout
                    if (bindingInfos?.viewHolderCallBack != null) {
                        commonHolder = createViewHolder(bindingInfos.viewHolderCallBack!!.getLayoutId(viewType), context, parent)
                    } else {
                        commonHolder = createViewHolder(layoutId, context, parent)    //  use default layout
                    }
                }//  normal item
        //  footer
        }
        return commonHolder
    }

    fun createViewHolder(layoutId: Int, mContext: Context?, parent: ViewGroup): IBindingHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(mContext), layoutId, parent, false)
        bindingInfos?.viewHolderCreate?.onCreate(binding)
        val holder = IBindingHolder(binding?.root)
        holder.binding = binding
        return holder
    }

    private fun setFullSpan(holder: IBindingHolder?, value: Boolean) {
        val layout = holder?.itemView?.layoutParams
        if (layout is StaggeredGridLayoutManager.LayoutParams) {
            layout.isFullSpan = value
        }
    }

    override fun onBindViewHolder(holder: IBindingHolder?, position: Int) {

        this.lastPosition = position
        if (position < headerSize || position >= dataSize + headerSize) {
            ILog.i("===object===", "is footer or header not OnBinding ...")
            return
        }

        if (!scrollFinished) {
            ILog.e("===scroll===", "scrolling , not init...")
            return
        }

        if (holder == null) return
        if (holder.binding != null) {
            holder.itemPosition = position.toLong()
            val index = position - headerSize
            val item = getObject(index) ?: return//  扣除header占用的位置
            if (item is BaseViewModel) (item as BaseViewModel).position = index
            val binding = holder.binding
            for (i in 0 until bindingInfos!!.size) {
                val value = bindingInfos.info.valueAt(i)
                binding?.setVariable(bindingInfos.info.keyAt(i), value ?: item)
            }
            binding?.executePendingBindings()
            if (bindingInfos.callBack != null) bindingInfos.callBack!!.onCreated<Any>(item, position, binding)
        }
    }

    /**
     * 处理之前的任务，比如itemchanged、datachanged、itemremoved等 一般不需要手动触发
     */
    fun doEvent() {
        if (recyclerView == null || recyclerView!!.isComputingLayout) {
            doDelayEvent()
            return
        }
        if (handler != null) handler!!.removeCallbacksAndMessages(null)
        val size = changedItem.size()
        for (i in 0 until size) {
            val position = changedItem.indexOfKey(i)
            val type = changedItem.indexOfValue(i)
            when (type) {
                1 -> itemChanged(position)
                2 -> removeItem(position)
                3 -> itemInsert(position)
                4 -> dataChanged()
            }
        }
    }

    private fun doDelayEvent() {
        if (mData == null || context == null) {
            ILog.i("===IAdapter===", "mData or mContext is null, destroy handler ...")
            mData = null
            context = null
            if (handler != null) {
                handler?.removeCallbacksAndMessages(null)
                handler = null
            }
            return
        }
        if (handler == null) return
        if (changedItem.size() < 1)
            handler?.removeCallbacksAndMessages(null)
        else
            handler?.postDelayed(run, 737)
    }
    //  key is position and value is type, 1->changed, 2->remove, 3->insert, 4->DataChanged

    fun itemChanged(position: Int) {
        if (recyclerView == null || !recyclerView!!.isComputingLayout)
            notifyItemChanged(position)
        else {
            changedItem.put(position, 1)
            doDelayEvent()
        }
    }

    fun itemRemove(position: Int) {
        if (recyclerView == null || !recyclerView!!.isComputingLayout)
            notifyItemRemoved(position)
        else {
            changedItem.put(position, 2)
            doDelayEvent()
        }
    }

    fun itemInsert(position: Int) {
        if (recyclerView == null || !recyclerView!!.isComputingLayout)
            notifyItemInserted(position)
        else {
            changedItem.put(position, 3)
            doDelayEvent()
        }
    }

    fun dataChanged() {
        if (recyclerView == null || !recyclerView!!.isComputingLayout)
            notifyDataSetChanged()
        else {
            changedItem.put(4, 0)
            doDelayEvent()
        }
    }

    fun getObject(position: Int): T? {
        return if (position < 0 || position >= dataSize) {
            null
        } else mData!![position]
    }

    companion object {
        val TYPE_NORMAL = -10000
    }
}
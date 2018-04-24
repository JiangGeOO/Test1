package com.ijustyce.fastkotlin.base;

import android.databinding.ViewDataBinding
import android.support.annotation.CallSuper
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.view.View
import android.view.ViewStub
import android.widget.ImageView
import android.widget.TextView
import com.ijustyce.fastkotlin.R
import com.ijustyce.fastkotlin.databinding.ListActivityView
import com.ijustyce.fastkotlin.http.HttpManager
import com.ijustyce.fastkotlin.irecyclerview.BindingInfo
import com.ijustyce.fastkotlin.irecyclerview.IDataInterface
import com.ijustyce.fastkotlin.irecyclerview.IRecyclerView
import com.ijustyce.fastkotlin.irecyclerview.IResponseData
import com.ijustyce.fastkotlin.utils.CommonTools
import retrofit2.Call
import java.util.*

/**
 * Created by yangchun on 2016/11/12.
 */
abstract class BaseListFragment<Bind : ViewDataBinding, Bean, Model : IResponseData<Bean>> : IBaseFragment<Bind>() {

    var refreshOnResume = false
    var showAutoRefreshAnim = false
    var showRefreshAnim = true
    var autoRefresh = true
    var recyclerView: IRecyclerView? = null
    var iDataInterface: IDataInterface<Bean, Model>? = null

    @Volatile
    private var emptyView: View? = null
    private var onClickListener: View.OnClickListener? = null

    //  以下方法是必须要子类完成的

    abstract fun bindingInfo() : BindingInfo?

    override fun initViewAndData() {
        recyclerView = recyclerView()
        recyclerView ?: return

        val bindingInfo = bindingInfo()
        bindingInfo ?: return

        iDataInterface = object : IDataInterface<Bean, Model>(activity, bindingInfo) {
            override fun loadData(pageNo: Int, httpResult: HttpManager.HttpResult<Model>?): Boolean {
                val httpCall = getListCall(pageNo)
                return httpCall == null || HttpManager.send(httpResult, httpCall)
            }

            override fun dataLoadFailed(throwable: Throwable) {
                if (activity == null || contentView == null) return
                        this@BaseListFragment.dataLoadFailed(throwable)
            }

            override fun onDataGet(data: Model?): Boolean {
                return if (activity == null) false else this@BaseListFragment.onDataGet(data)
            }

            override fun onDataShow(data: ArrayList<Bean>?) {
                if (activity == null) return
                        this@BaseListFragment.onDataShow(data)
            }

            override fun afterBindToView(model: Model?) {
                this@BaseListFragment.afterBindToView(model)
            }

            override fun addHeader() {
                this@BaseListFragment.addHeader()
            }

            override fun addFooter() {
                this@BaseListFragment.addFooter()
            }
        }
        recyclerView?.dataInterface(iDataInterface as IDataInterface<Bean, Model>)
    }

    open fun afterBindToView(model: Model?) {

    }

    open fun dataLoadFailed(throwable: Throwable) {

    }

    open fun addHeader(){}
    open fun addFooter(){}

    open fun onDataGet(data: Model?): Boolean {
        return true
    }

    override fun viewModel(): IBaseEvent? {
        return null
    }

    open fun emptyViewStub(): ViewStub? {
        if (contentView is ListActivityView && (contentView as ListActivityView).recyclerView != null) {
           return (contentView as ListActivityView).recyclerView?.noData?.viewStub
        }
        return null
    }

    open fun emptyView(): View? {
        if (emptyView != null) return emptyView
        val viewStub = emptyViewStub()
        viewStub?: return emptyView
        synchronized(this) {
            if (emptyView == null) {
                emptyView = viewStub.inflate()
            }
        }
        return emptyView
    }

    private fun showOrHideEmptyView(show: Boolean) {
        if (activity == null || contentView == null) return
        val view = emptyView()
        if (view != null) {
            view.visibility = if (show) View.VISIBLE else View.GONE
            if (onClickListener == null) {
                onClickListener = View.OnClickListener { refresh(showRefreshAnim) }
                view.setOnClickListener(onClickListener)
            }
        }
    }

    fun showEmptyView(): Boolean {
        return !recyclerView!!.hasFooter() && !recyclerView!!.hasHeader()
    }

    @CallSuper
    open fun onDataShow(data: ArrayList<Bean>?) {
        showOrHideEmptyView(data != null && data.isEmpty() && showEmptyView())
    }

    override fun hasNull(): Boolean {
        return contentView == null || recyclerView == null
    }

    @JvmOverloads
    fun refresh(showAnim: Boolean = false) {
        if (!hasNull() && iDataInterface != null) {
            recyclerView!!.onRefresh(showAnim)
        }
    }

    override fun onResume() {
        super.onResume()
        if (contentView == null || !autoRefresh || iDataInterface == null) return
        if (refreshOnResume || iDataInterface!!.hasNoData()) {
            refresh(showAutoRefreshAnim)
        }
    }

    fun setNoDataMsg(@StringRes id: Int) {
        setNoDataMsg(CommonTools.resString(context, id))
    }

    fun setNoDataMsg(text: String?) {
        text ?: return
        val emptyView = emptyView()
        val textView = emptyView?.findViewById<TextView>(R.id.noDataMsg)
        textView?.text = text
    }

    fun setNoDataImg(@DrawableRes res: Int) {
        val emptyView = emptyView()
        val imageView = emptyView!!.findViewById<ImageView>(R.id.noDataImg)
        if (imageView is ImageView) {
            (imageView as ImageView).setImageResource(res)
        }
    }

    open fun recyclerView(): IRecyclerView? {
        return if (contentView is ListActivityView) {
            (contentView as ListActivityView).recyclerView?.list
        } else null
    }

    override fun onDestroy() {
        super.onDestroy()
        if (recyclerView != null) {
            recyclerView!!.onDestroy()
            recyclerView = null
        }
    }

    abstract fun getListCall(pageNo: Int): Call<Model>?
}
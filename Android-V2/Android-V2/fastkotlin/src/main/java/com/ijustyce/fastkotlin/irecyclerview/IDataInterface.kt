package com.ijustyce.fastkotlin.irecyclerview

import android.content.Context
import com.ijustyce.fastkotlin.http.HttpManager
import com.ijustyce.fastkotlin.utils.ILog
import retrofit2.Call
import retrofit2.Response
import java.util.*

/**
 * Created by yangchun on 2016/12/29.
 */

abstract class IDataInterface<Bean, Model : IResponseData<Bean>>(val context: Context?, val bindingInfo: BindingInfo?) {

    var pageNo = 1
    var startPage = 1

    var data: ArrayList<Bean>? = null
    var adapter: IAdapter<Bean>? = null
    private var iRecyclerView: IRecyclerView? = null
    private var callback: HttpManager.HttpResult<Model>? = null

    abstract fun loadData(pageNo: Int, httpResult: HttpManager.HttpResult<Model>?): Boolean

    abstract fun addHeader()
    abstract fun addFooter()

    open fun dataLoadFailed(throwable: Throwable) {}
    open fun onDataGet(data: Model?): Boolean {
        return true
    }

    open fun onDataShow(data: ArrayList<Bean>?) {}
    open fun afterBindToView(model: Model?) {}

    init {
        initData()
    }

    private fun initData() {
        if (context == null || bindingInfo == null) return
        data = ArrayList()
        adapter = IAdapter(context, data, bindingInfo)
        callback = object : HttpManager.HttpResult<Model> {

            override fun success(call: Call<Model>?, response: Response<Model>?) {
                if (onDataGet(response?.body())) {
                    bindDataToView(response?.body())
                }
                onDataShow(data)
            }

            override fun failed(call: Call<Model>?, t: Throwable?) {
                t?.printStackTrace()
                if (t != null) dataLoadFailed(t)
                onDataGet(null)
                onDataShow(data)
                if (iRecyclerView != null) {
                    ILog.e("===load===", "failed " + t?.message)
                    iRecyclerView!!.loadFinish(true, false, false)
                }
            }
        }
    }

    fun bindDataToView(data: Model?) {
        if (hasNull()) return
        val arrayList = data?.list
        val isRefresh = pageNo == startPage
        if (arrayList == null) {
            ILog.e("===load===", "failed list is null")
            iRecyclerView!!.loadFinish(isRefresh, false, false)
        } else {
            if (isRefresh) this.data!!.clear()
            if (!arrayList.isEmpty()) this.data!!.addAll(arrayList)
            adapter!!.dataChanged()
            val size = arrayList.size
            ILog.e("===load===", "load finish , size is " + size)
            if (iRecyclerView!!.pageSize <= size) {
                pageNo++
                iRecyclerView!!.loadFinish(isRefresh, true, size > 0)
            } else {
                iRecyclerView!!.loadFinish(isRefresh, false, size > 0)
            }
        }
        afterBindToView(data)
    }

    fun hasNull(): Boolean {
        return adapter == null || data == null || callback == null || iRecyclerView == null
    }

    internal fun refresh() {
        if (hasNull()) return
        pageNo = startPage
        if (!loadData(pageNo, callback) && !hasNull()) {
            ILog.e("===load===", "failed , network")
            iRecyclerView!!.loadFinish(true, false, false)
            onDataGet(null)
            onDataShow(data)
        }
    }

    internal fun loadMore() {
        if (hasNull()) return
        if (!loadData(pageNo, callback) && !hasNull()) {
            ILog.e("===load===", "failed , network")
            iRecyclerView!!.loadFinish(false, true, false)
            onDataGet(null)
            onDataShow(data)
        }
    }

    fun hasNoData(): Boolean {
        return data != null && data!!.isEmpty()
    }

    fun getByPosition(position: Int):Bean? {
        val size = data?.size?: -1
        if (position < 0 || position >= size) return null
        return data?.get(position)
    }

    internal fun destroy() {
        if (data != null) data!!.clear()
        if (adapter != null) adapter!!.dataChanged()
        data = null
        adapter = null
        callback = null
        iRecyclerView = null
    }

    internal fun setIRecyclerView(iRecyclerView: IRecyclerView?) {
        if (iRecyclerView == null || data == null || adapter == null || callback == null) return
        this.iRecyclerView = iRecyclerView
    }

}

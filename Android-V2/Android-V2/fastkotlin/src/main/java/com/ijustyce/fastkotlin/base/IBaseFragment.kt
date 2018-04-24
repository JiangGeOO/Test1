package com.ijustyce.fastkotlin.base

import android.app.Activity
import android.app.Dialog
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ijustyce.fastkotlin.BR
import com.ijustyce.fastkotlin.R
import com.ijustyce.fastkotlin.callback.CallBackManager
import com.ijustyce.fastkotlin.http.HttpManager
import com.ijustyce.fastkotlin.ui.IProgressDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by yangchun on 2016/11/12.
 */

abstract class IBaseFragment<Bind : ViewDataBinding> : Fragment() {

    var contentView: Bind? = null
    var activity: Activity? = null
    var mView: View? = null
    private var viewModel : IBaseEvent? = null

    abstract val layoutId: Int

    private var dialog: IProgressDialog? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity = getActivity()
        if (mView != null) return mView
        if (cancelCreate()) {
            getActivity()?.finish()
            return null
        }
        contentView = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mView = contentView?.root
        val parent = mView?.parent as? ViewGroup
        parent?.removeView(mView)
        viewModel = viewModel()
        contentView?.setVariable(BR.viewModel, viewModel)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewAndData()
        afterCreate()
        viewModel?.afterCreate()
    }

    override fun onResume() {
        super.onResume()
        CallBackManager.onResume(activity)
        viewModel?.onResume()
    }

    override fun onPause() {
        super.onPause()
        CallBackManager.onPause(activity)
        dismissProcess()
        viewModel?.onPause()
    }

    override fun onStop() {
        super.onStop()
        CallBackManager.onStop(activity)
        viewModel?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissProcess()
        CallBackManager.onDestroy(activity)
        viewModel?.onDestroy()
        dialog = null
        contentView = null
        activity = null
    }

    open fun afterCreate() {}
    open internal fun initViewAndData() {}

    fun cancelCreate(): Boolean {
        return false
    }

    fun getDialog(): Dialog? {
        val context = activity
        context ?: return null
        dialog = dialog?: IProgressDialog(context, getResString(R.string.net_loading_hint))
        return dialog
    }

    fun showProcess() {
        val dialog = getDialog()
        dialog?.show()
    }

    //  for java
    fun <T> getNetData(callback: HttpManager.HttpResult<T>?, call: Call<T>) {
        getNetData(callback, call, true)
    }

    fun <T> getNetData(callback: HttpManager.HttpResult<T>?, call: Call<T>, showDialog: Boolean = true) {
        if (showDialog) {
            showProcess()
        }
        val result = HttpManager.send(object : HttpManager.HttpResult<T> {
            override fun success(call: Call<T>?, response: Response<T>?) {
                if (activity == null || contentView == null) {
                    return
                }
                if (showDialog) {
                    dismissProcess()
                }
                if (callback != null) callback.success(call, response)
            }

            override fun failed(call: Call<T>?, t: Throwable?) {
                if (activity == null || contentView == null) {
                    return
                }
                if (showDialog) {
                    dismissProcess()
                }
                if (callback != null) callback.failed(call, t)
            }
        }, call)
        if (!result && showDialog) dismissProcess()
    }

    fun dismissProcess() {
        if (dialog != null && dialog!!.isShowing()) dialog!!.cancel()
    }

    fun backClick(view: View) {
        if (activity != null) activity!!.finish()
    }

    fun getResString(@StringRes stringId: Int): String {
        try {
            return resources.getString(stringId)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    open fun hasNull(): Boolean {
        return contentView == null
    }

    abstract fun viewModel(): IBaseEvent?

    fun getResColor(color: Int): Int {
        return this.resources.getColor(color)
    }
}
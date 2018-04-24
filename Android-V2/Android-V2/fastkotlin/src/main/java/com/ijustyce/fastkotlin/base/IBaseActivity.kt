package com.ijustyce.fastkotlin.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Build
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import com.ijustyce.fastkotlin.BR
import com.ijustyce.fastkotlin.R
import com.ijustyce.fastkotlin.callback.CallBackManager
import com.ijustyce.fastkotlin.databinding.ListActivityView
import com.ijustyce.fastkotlin.http.HttpManager
import com.ijustyce.fastkotlin.manager.AppManager

import com.ijustyce.fastkotlin.ui.CommonTitleBar
import com.ijustyce.fastkotlin.ui.IProgressDialog
import com.ijustyce.fastkotlin.viewmodel.CommonTitleBarView

import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

/**
 * Created by yangchun on 2016/11/12.
 */

abstract class IBaseActivity<Bind : ViewDataBinding> : AppCompatActivity() {

    var contentView: Bind? = null
    var context: Context? = null
    var activity: Activity? = null
    private var key: String? = null
    var viewModel : IBaseEvent? = null

    abstract fun layoutId(): Int
    open fun viewModel(): IBaseEvent? = null

    private var dialog: IProgressDialog? = null

    val titleBarView: CommonTitleBarView?
        get() {
            val titleBar = titleBar
            return if (titleBar == null) null else titleBar.viewModel
        }

    val titleBar: CommonTitleBar?
        get() = if (contentView is ListActivityView) {
            (contentView as ListActivityView).titleBar
        } else null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (cancelCreate()) {
            finish()
            return
        }
        context = baseContext
        activity = this
        contentView = DataBindingUtil.setContentView(this, layoutId())
        viewModel = viewModel()
        contentView?.setVariable(BR.viewModel, viewModel)
        initViewAndData()
        afterCreate()
        CallBackManager.onCreate(activity)
        key = javaClass.name + System.currentTimeMillis()
        AppManager.add(key, activity)
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
        viewModel?.onDestroy()
        dialog = null
        contentView = null
        CallBackManager.onDestroy(activity)
        AppManager.delete(key)
        context = null
        activity = null
    }

    open fun afterCreate(){}
    open internal fun initViewAndData() {}

    open fun cancelCreate(): Boolean {
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
                afterRequest(response?.body())
                callback?: return
                callback.success(call, response)
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

    open fun <T> afterRequest(data: T){}

    fun dismissProcess() {
        if (dialog?.isShowing == true) {
            dialog?.dismiss()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backPress()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    fun backClick(view: View) {
        backPress()
    }

    open fun backPress() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition()
        }else {
            finish()
        }
    }

    fun getResString(@StringRes stringId: Int): String {
        try {
            return resources.getString(stringId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getResColor(color: Int): Int {
        return resources.getColor(color)
    }

    open fun hasNull(): Boolean {
        return contentView == null
    }

    fun setTitleLabel(@StringRes stringId: Int) {
        setTitleLabel(getResString(stringId))
    }

    fun setTitleLabel(title: String) {
        titleBarView?.titleText?.set(title)
    }
}
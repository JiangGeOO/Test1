package com.ijustyce.fastkotlin.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.webkit.WebView
import android.widget.AbsoluteLayout
import android.widget.ProgressBar
import com.ijustyce.fastkotlin.R
import com.ijustyce.fastkotlin.http.FileAPI

/**
 * Created by yangchun on 16/4/13.  带进度条的WebView，
 * 如果你调用了  setWebChromeClient  请务必在 onProgressChanged 里调用这个类的onProgressChanged
 */
class ProgressWebView(private var mContext: Context?, attrs: AttributeSet) : WebView(mContext, attrs) {

    private val progressbar: ProgressBar?

    val isLoadFinish: Boolean
        get() = progressbar != null && progressbar.visibility == View.GONE

    private var onPageFinish: OnPageFinish? = null

    init {
        progressbar = ProgressBar(mContext, null, android.R.attr.progressBarStyleHorizontal)
        progressbar.layoutParams = AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.FILL_PARENT, 10, 0, 0)
        addView(progressbar)
        settings.setSupportZoom(true)
        settings.setAppCacheEnabled(true)
        settings.javaScriptEnabled = true
        webChromeClient = WebChromeClient()
        setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            val fileAPI = FileAPI.initByUrlAndListener(url, null)
            fileAPI.showNotify(true).autoInstall(true).setNotifyView("文件下载中", "文件下载中 ", R.mipmap.ic_launcher)
            fileAPI.startDownload(null)
        }
    }

    override fun destroy() {
        super.destroy()
        setDownloadListener(null)
        mContext = null
    }

    fun setProgressDrawable(drawable: Drawable) {

        progressbar!!.progressDrawable = drawable
    }

    //  重写这个方法可以设置
    fun setProgressbarHeight(height: Int) {

        if (height > 5) {
            progressbar!!.layoutParams = AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.FILL_PARENT, height, 0, 0)
        }
    }

    fun setOnPageFinish(onPageFinish: OnPageFinish) {
        this.onPageFinish = onPageFinish
    }

    interface OnPageFinish {
        fun onPageFinish()
    }

    //  如果你调用了 setWebChromeClient ，请务必调用这个方法！！！
    fun onProgressChanged(view: WebView, newProgress: Int) {

        if (newProgress == 100) {
            if (onPageFinish != null) {
                onPageFinish!!.onPageFinish()
            }
            progressbar!!.visibility = View.GONE
        } else {
            if (progressbar!!.visibility == View.GONE)
                progressbar.visibility = View.VISIBLE
            progressbar.progress = newProgress
        }
    }

    inner class WebChromeClient : android.webkit.WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {

            this@ProgressWebView.onProgressChanged(view, newProgress)
            super.onProgressChanged(view, newProgress)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        requestDisallowInterceptTouchEvent(true)
        return super.onTouchEvent(event)
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        val lp = progressbar!!.layoutParams as AbsoluteLayout.LayoutParams
        lp.x = l
        lp.y = t
        progressbar.layoutParams = lp
        super.onScrollChanged(l, t, oldl, oldt)
    }
}

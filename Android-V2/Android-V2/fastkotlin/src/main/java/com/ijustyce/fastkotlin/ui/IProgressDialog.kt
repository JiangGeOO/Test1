package com.ijustyce.fastkotlin.ui

import android.app.Dialog
import android.content.Context
import android.widget.TextView
import com.ijustyce.fastkotlin.R

class IProgressDialog(context: Context) : Dialog(context, R.style.Dialog) {

    private var msgView: TextView? = null

    init {
        setContentView(R.layout.fastandroiddev3_dialog_progress)
        setCanceledOnTouchOutside(true)
    }

    constructor(context: Context, msg: String) : this(context) {
        setMsg(msg)
    }

    fun setMsg(msg: String) {
        msgView = msgView ?: findViewById(R.id.msg)
        msgView?.text = msg
    }
}
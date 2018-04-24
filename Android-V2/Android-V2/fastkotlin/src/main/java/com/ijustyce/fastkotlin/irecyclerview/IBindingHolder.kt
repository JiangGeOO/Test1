package com.ijustyce.fastkotlin.irecyclerview

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by yangchun on 16/7/19.
 */

class IBindingHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    var binding: ViewDataBinding? = null
    var itemPosition: Long = 0
    var header = false
    var footer = false
}

package com.yidao.threekmo.v2.activity

import com.ijustyce.fastkotlin.base.IBaseEvent
import com.yidao.threekmo.R
import com.yidao.threekmo.databinding.IndexActivityView
import com.yidao.threekmo.v2.viewmodel.IndexActivityVm

/**
 * Created by deepin on 17-12-20.
 */
class IndexActivity : BaseActivity<IndexActivityView>() {

    override fun viewModel(): IBaseEvent? {
        return IndexActivityVm(this)
    }

    override fun layoutId(): Int {
        return R.layout.activity_index_mvvm
    }
}
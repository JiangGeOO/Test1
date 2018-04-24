package com.yidao.threekmo.v2.activity

import com.ijustyce.fastkotlin.base.IBaseEvent
import com.yidao.threekmo.R
import com.yidao.threekmo.databinding.DialogPayPwView
import com.yidao.threekmo.v2.viewmodel.PocketPayVm

/**
 * Created by deepin on 18-1-15.
 */
class PocketPayActivity: BaseActivity<DialogPayPwView>() {

    override fun layoutId(): Int {
        return R.layout.activity_pocket_pay
    }

    override fun viewModel(): IBaseEvent? {
        return PocketPayVm(this)
    }

    override fun backPress() {
        contentView?.viewModel?.setResult(false)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.anim_null, R.anim.top_to_bottom)
    }
}
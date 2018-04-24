package com.yidao.threekmo.v2.activity

import com.ijustyce.fastkotlin.base.IBaseEvent
import com.yidao.threekmo.R
import com.yidao.threekmo.databinding.ActivitySettingView
import com.yidao.threekmo.v2.viewmodel.SettingVm

/**
 * Created by deepin on 18-1-13.
 */
class SettingActivity: BaseActivity<ActivitySettingView>() {

    override fun layoutId(): Int {
        return R.layout.activity_setting_mvvm
    }

    override fun viewModel(): IBaseEvent? {
        return SettingVm(this)
    }
}
package com.yidao.threekmo.v2.activity

import android.os.Build
import android.view.View
import com.ijustyce.fastkotlin.base.IBaseEvent
import com.yidao.threekmo.R
import com.yidao.threekmo.databinding.SplashView
import com.yidao.threekmo.v2.viewmodel.SplashVm

/**
 * Created by deepin on 17-12-18.
 */
class SplashActivity : BaseActivity <SplashView>() {

    override fun layoutId(): Int = R.layout.activity_splash_mvvm
    override fun viewModel(): IBaseEvent? = SplashVm(this)

    override fun afterCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            contentView?.root?.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        } else {
            contentView?.root?.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
    }
}
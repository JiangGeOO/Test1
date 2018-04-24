package com.ijustyce.fastkotlin.base

import android.app.IntentService
import android.app.Service
import android.content.Context
import android.content.Intent

abstract class IBaseService(name: String) : IntentService(name) {

    var mContext: Context? = null

    override fun onHandleIntent(intent: Intent?) {

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return Service.START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
    }
}

package com.ijustyce.fastkotlin

import android.app.Application

/**
 * Created by arch on 17-11-6.
 */
open class IApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    companion object {
        lateinit var application: Application
        fun instance() : Application = application
    }
}
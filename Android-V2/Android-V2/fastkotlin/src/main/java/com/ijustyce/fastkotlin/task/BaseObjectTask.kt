package com.ijustyce.fastkotlin.task

import java.lang.ref.WeakReference

/**
 * Created by arch on 17-11-17.
 */
abstract class BaseObjectTask<Result, Caller>: BaseTask<Result>() {
    lateinit var weakReference : WeakReference<Caller>
    fun init(caller: Caller) : BaseObjectTask<Result, Caller> {
        weakReference = WeakReference(caller)
        return this
    }
    fun caller() : Caller? = weakReference.get()
}
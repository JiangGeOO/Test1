package com.ijustyce.fastkotlin.task

import android.os.AsyncTask

/**
 * Created by arch on 17-11-17.
 */
abstract class BaseTask <Result> : AsyncTask<Int, Int, Result>() {

    override fun onPreExecute() {
        //  do what you need, in the mainThread
    }

    override fun onPostExecute(result: Result) {
        //  do what you need, in the mainThread
    }

    override fun onProgressUpdate(vararg values: Int?) {
        //  do what you need, in the mainThread
    }

    override fun onCancelled(result: Result) {
        //  do what you need, in the mainThread
    }

    override fun onCancelled() {
        //  do what you need, in the mainThread
    }
}
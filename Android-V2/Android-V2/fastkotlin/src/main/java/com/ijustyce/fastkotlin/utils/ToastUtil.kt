/**
 * date:2014-04-21
 * rewrite ToastUtil
 */
package com.ijustyce.fastkotlin.utils

import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.ijustyce.fastkotlin.IApplication
import com.ijustyce.fastkotlin.R
import java.util.*

object ToastUtil {

    private var notShowList = ArrayList<String>()

    fun addNotShowWord(word: String) {
        if (!notShowList.contains(word)) {
            notShowList.add(word)
        }
    }

    private fun shouldShow(text: String): Boolean {
        return !StringUtils.isEmpty(text) && !notShowList.contains(text)
    }

    fun show(id: Int) {

        val text = IApplication.instance().resources.getString(id)
        if (!shouldShow(text)) {
            return
        }
        try {
            val mInflater = LayoutInflater.from(IApplication.instance())
            val toastView = mInflater.inflate(R.layout.fastandroiddev3_toast, null)
            val toastText = toastView.findViewById<TextView>(R.id.message) as TextView
            toastText.text = text
            val toastStart = Toast(IApplication.instance())
            toastStart.setGravity(Gravity.CENTER, 0, 0)
            toastStart.duration = Toast.LENGTH_SHORT
            toastStart.view = toastView
            toastStart.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun show(text: String) {

        if (!shouldShow(text)) {
            return
        }

        try {
            val mInflater = LayoutInflater.from(IApplication.instance())
            val toastView = mInflater.inflate(R.layout.fastandroiddev3_toast, null)
            val toastText = toastView.findViewById<TextView>(R.id.message) as TextView
            toastText.text = text
            val toastStart = Toast(IApplication.instance())
            toastStart.setGravity(Gravity.CENTER, 0, 0)
            toastStart.duration = Toast.LENGTH_SHORT
            toastStart.view = toastView
            toastStart.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * @param id
     */
    fun showTop(id: Int) {

        val text = IApplication.instance().getResources().getString(id)
        if (!shouldShow(text)) {
            return
        }

        try {
            val mInflater = LayoutInflater.from(IApplication.instance())
            val toastTopView = mInflater.inflate(R.layout.fastandroiddev3_toast_top, null)
            val toastTopText = toastTopView.findViewById<TextView>(R.id.message) as TextView
            toastTopText.text = text
            val toastStart = Toast(IApplication.instance())
            toastStart.setGravity(Gravity.TOP, 0, 90)
            toastStart.duration = Toast.LENGTH_SHORT
            toastStart.view = toastTopView
            toastStart.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun showTop(text: String) {

        if (!shouldShow(text)) {
            return
        }
        try {
            val mInflater = LayoutInflater.from(IApplication.instance())
            val toastTopView = mInflater.inflate(R.layout.fastandroiddev3_toast_top, null)
            val toastTopText = toastTopView.findViewById<TextView>(R.id.message) as TextView
            toastTopText.text = text
            val toastStart = Toast(IApplication.instance())
            toastStart.setGravity(Gravity.TOP, 0, 90)
            toastStart.duration = Toast.LENGTH_SHORT
            toastStart.view = toastTopView
            toastStart.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * @param id
     * @param yOffset dp , height of ToastUnit
     */
    fun showTop(id: Int, yOffset: Int) {

        val text = IApplication.instance().getResources().getString(id)
        if (!shouldShow(text)) {
            return
        }

        try {
            val mInflater = LayoutInflater.from(IApplication.instance())
            val toastTopView = mInflater.inflate(R.layout.fastandroiddev3_toast_top, null)
            val toastTopText = toastTopView.findViewById<TextView>(R.id.message) as TextView
            toastTopText.setText(text)
            val toastStart = Toast(IApplication.instance())
            toastStart.setGravity(Gravity.TOP, 0, yOffset)
            toastStart.duration = Toast.LENGTH_SHORT
            toastStart.view = toastTopView
            toastStart.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun showTop(text: String, yOffset: Int) {
        if (!shouldShow(text)) {
            return
        }

        try {
            val mInflater = LayoutInflater.from(IApplication.instance())
            val toastTopView = mInflater.inflate(R.layout.fastandroiddev3_toast_top, null)
            val toastTopText = toastTopView.findViewById<TextView>(R.id.message) as TextView
            toastTopText.text = text
            val toastStart = Toast(IApplication.instance())
            toastStart.setGravity(Gravity.TOP, 0, yOffset)
            toastStart.duration = Toast.LENGTH_SHORT
            toastStart.view = toastTopView
            toastStart.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

package com.ijustyce.fastkotlin.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

/**
 * Created by yangchun on 16/8/22.
 */

class RunTimePermission(private val activity: Activity?) {

    fun checkPermissionForRecord(): Boolean {
        if (activity == null) return false
        val result = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun checkPermissionForExternalStorage(): Boolean {
        if (activity == null) return false
        val result = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun checkPermissionForCamera(): Boolean {
        if (activity == null) return false
        val result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun checkPermissionForCall(): Boolean {
        if (activity == null) return false
        val result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun checkPermissionForContacts(): Boolean {
        if (activity == null) return false
        val result = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun checkPermissionForLocation(): Boolean {
        if (activity == null) return false
        val result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
        val result2 = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
        return result == PackageManager.PERMISSION_GRANTED && result == result2
    }

    fun checkPermissionByValue(permission: String): Boolean {
        if (activity == null) return false
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(activity, permission)
    }

    fun requestPermissionForRecord() {
        if (activity == null) return
        try {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.RECORD_AUDIO)) {
                ToastUtil.show("请允许录音")
            }
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.RECORD_AUDIO), RECORD_PERMISSION_REQUEST_CODE)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun requestPermissions(permissions: ArrayList<String>?) {
        if (activity == null || permissions == null || permissions.size < 1) return
        val array = Array(permissions.size, {
            index -> permissions.get(index)
        })
        try {
            ActivityCompat.requestPermissions(activity, array, DIY_PERMISSION_REQUEST_CODE)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun requestPermissionForExternalStorage() {
        if (activity == null) return
        try {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ToastUtil.show("请允许写入文件")
            }
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun requestPermissionForCamera() {
        if (activity == null) return
        try {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
                ToastUtil.show("请允许拍照")
            }
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun requestPermissionForCall() {
        if (activity == null) return
        try {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CALL_PHONE)) {
                ToastUtil.show("请允许拨打电话")
            }
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CALL_PHONE), CALL_PERMISSION_REQUEST_CODE)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun requestPermissionForContacts() {
        if (activity == null) return
        try {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_CONTACTS)) {
                ToastUtil.show("请允许读取通讯录")
            }
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_CONTACTS), CONTACTS_PERMISSION_REQUEST_CODE)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun requestPermissionForLocation() {
        if (activity == null) return
        try {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ToastUtil.show("请允许定位")
            }
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    companion object {

        private val RECORD_PERMISSION_REQUEST_CODE = 1
        private val EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 2
        private val CAMERA_PERMISSION_REQUEST_CODE = 3
        private val CALL_PERMISSION_REQUEST_CODE = 4
        private val CONTACTS_PERMISSION_REQUEST_CODE = 5
        private val LOCATION_PERMISSION_REQUEST_CODE = 5
        private val DIY_PERMISSION_REQUEST_CODE = 6
    }
}

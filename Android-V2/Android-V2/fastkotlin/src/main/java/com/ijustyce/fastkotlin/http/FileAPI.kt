package com.ijustyce.fastkotlin.http

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.ijustyce.fastkotlin.IApplication
import com.ijustyce.fastkotlin.utils.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * Created by yangchun on 2016/11/10.
 */

class FileAPI private constructor() {
    private val retrofit: Retrofit

    private var url: String? = null
    private var listener: ProgressListener? = null
    private var mBuilder: NotificationCompat.Builder? = null
    private var notificationManager: NotificationManager? = null
    private var notifyId: Int = 0
    private var notifyContent: String? = null
    var downloadedFile: File? = null
    private var autoInstall: Boolean = false

    private val defaultListener = ProgressListener { percent, isFinish ->
        if (mBuilder != null) {
            if (isFinish) {
                afterTransferFinish()
                return@ProgressListener
            }
            mBuilder!!.setProgress(100, percent, false)
            mBuilder!!.setContentText(notifyContent + percent + "%")
            notificationManager!!.notify(notifyId, mBuilder!!.build())
        }
        if (listener != null) {
            listener!!.onProgress(percent, isFinish)
        }
    }

    fun autoInstall(value: Boolean): FileAPI {
        autoInstall = value
        return this
    }

    private fun afterTransferFinish() {

        if (downloadedFile != null && downloadedFile!!.exists()) {
            if (autoInstall && downloadedFile!!.name.endsWith(".apk")) {
                FileUtils.setupApk(downloadedFile!!, IApplication.application)
            }
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.fromFile(downloadedFile), FileUtils.getFileType(downloadedFile!!))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val pi = PendingIntent.getActivity(IApplication.application, notifyId, intent, 0)
            mBuilder!!.setContentIntent(pi)
        }

        mBuilder!!.setContentTitle("文件传输已完成")
        mBuilder!!.setContentText("文件传输已完成")
        mBuilder!!.setProgress(0, 100, false)
        notificationManager!!.notify(notifyId, mBuilder!!.build())
    }

    fun showNotify(openSettingIfNoRight: Boolean): FileAPI {
        if (Build.VERSION.SDK_INT >= 19 && openSettingIfNoRight &&
                !CommonTools.isNotificationEnabled(IApplication.application)) {
            CommonTools.setNotify(IApplication.application)
        }
        if (notifyId <= 0) notifyId = System.currentTimeMillis().toInt()
        if (notificationManager == null) {
            notificationManager = IApplication.application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        if (mBuilder == null) {
            mBuilder = NotificationCompat.Builder(IApplication.application)
        }
        return this
    }

    fun setNotifyView(title: String?, content: String?, iconRes: Int): FileAPI {
        if (mBuilder == null) showNotify(false)
        mBuilder?.setTicker(title)?.setContentTitle(title)?.setContentText(content)?.setSmallIcon(iconRes)
        mBuilder?.setAutoCancel(true)
        notificationManager!!.notify(notifyId, mBuilder!!.build())
        notifyContent = content
        return this
    }

    init {

        val interceptor = DownloadInterceptor(defaultListener)
        val client = HttpManager.getBuilder().addInterceptor(interceptor).build()

        retrofit = Retrofit.Builder()
                .baseUrl("http://ijustyce.win/")
                .client(client)
                .build()
    }

    fun startUpload(parameter: String, uploadFile: Array<File>?, result: HttpManager.HttpResult<ResponseBody>): Boolean {
        if (!RegularUtils.isUrl(url)) {
            ILog.e("===FileAPI===", "url is error while upload file ")
            return false
        }
        val size = uploadFile?.size ?: 0
        if (size < 1) {
            ILog.e("===FileAPI===", "file is null or not exists")
            return false
        }
        val upload = arrayOfNulls<MultipartBody.Part>(size)
        var index = 0
        for (file in uploadFile!!) {
            if (file == null) continue
            val fileRequestBody = UploadRequestBody(file, defaultListener)
            val body = MultipartBody.Part.createFormData(parameter,
                    file.name, fileRequestBody)
            upload[index] = body
            index++
        }
        val call = retrofit.create(HttpService::class.java).upload(url, upload)
        HttpManager.send(result, call)
        return true
    }

    fun startUpload(parameter: String, uploadFile: File, callback: HttpManager.HttpResult<ResponseBody>): Boolean {
        val files = arrayOf(uploadFile)
        return startUpload(parameter, files, callback)
    }

    private fun save(response: Response<ResponseBody>?, file: File) {
        try {
            val `is` = (if (response == null || response.body() == null) null else response.body()!!.byteStream()) ?: return
            val fos = FileOutputStream(file)
            val bis = BufferedInputStream(`is`)
            val buffer = ByteArray(1024)
            var len = bis.read(buffer)
            while (len != -1) {
                fos.write(buffer, 0, len)
                fos.flush()
                len = bis.read(buffer)
            }
            fos.close()
            bis.close()
            `is`.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun getSavedFile(url: String?): File? {
        val fullFileName = getFileNameFromUrl(url)
        fullFileName?: return null
        val fileName = FileUtils.getFileName(fullFileName)
        if (StringUtils.isEmpty(fullFileName) || StringUtils.isEmpty(fileName)) return null
        val extraName = FileUtils.getFileExtraName(fullFileName)
        return FileUtils.getUnExistsFile(fileName!! + extraName)
    }

    fun startDownload(callBack: Callback<ResponseBody>?, saveToFile: File? = null): Boolean {
        if (!RegularUtils.isUrl(url)) {
            ILog.e("===FileAPI===", "url is error while download file ")
            return false
        }
        val savedFile = saveToFile?: getSavedFile(url)
        savedFile?: return false
        if (!RegularUtils.isUrl(url)) {
            ILog.e("===FileAPI===", "url is error while download file ")
            return false
        }
        if (savedFile.exists()) {
            ILog.e("===FileAPI===", "file is null or already exists, please call FileUtils.renameIfExists if need")
            return false
        }
        downloadedFile = savedFile
        val call = retrofit.create(HttpService::class.java).download(url)
        HttpManager.send(object : HttpManager.HttpResult<ResponseBody> {

            override fun success(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                ThreadUtils.execute(Runnable {
                    save(response, savedFile)
                })
                if (call != null && response != null) callBack?.onResponse(call, response)
            }

            override fun failed(call: Call<ResponseBody>?, t: Throwable?) {
                if (call != null && t != null) callBack?.onFailure(call, t)
                t?.printStackTrace()
            }
        }, call)
        return true
    }

    companion object {

        fun getFileNameFromUrl(url: String?): String? {
            url?: return null
            return url.substring(url.lastIndexOf("/") + 1).split("\\?".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        }

        fun initByUrlAndListener(url: String, listener: ProgressListener?): FileAPI {
            val api = FileAPI()
            api.url = url
            api.listener = listener
            return api
        }
    }
}

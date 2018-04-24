package com.ijustyce.fastkotlin.utils

import android.annotation.TargetApi
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.Bitmap.CompressFormat
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import android.support.v4.app.NotificationCompat
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ScrollView
import android.widget.TextView
import com.ijustyce.fastkotlin.IApplication
import com.ijustyce.fastkotlin.R
import java.io.ByteArrayOutputStream
import java.io.File

/**
 * Created by arch on 17-11-4.
 */
object CommonTools {

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    fun calculateInSampleSize(options: BitmapFactory.Options,
                              reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        return inSampleSize
    }

    fun isWifi(context: Context?): Boolean {
        if (context == null) return false
        val connectMgr = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val info = connectMgr?.activeNetworkInfo
        return info != null && info.type == ConnectivityManager.TYPE_WIFI
    }

    fun isMobile(context: Context?): Boolean {
        if (context == null) return false
        val connectMgr = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val info = connectMgr?.activeNetworkInfo
        return info != null && info.type == ConnectivityManager.TYPE_MOBILE
    }

    fun viewBitmap(view: View?): Bitmap? {
        view ?: return null
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        return view.drawingCache
    }

    fun saveViewToJpg(view: View?, filePath: String) {
        FileUtils.savBitmapToJpg(viewBitmap(view), filePath)
        view?.destroyDrawingCache()
    }

    fun scrollViewBitmap(scrollView: ScrollView?): Bitmap? {
        scrollView ?: return null
        val height = scrollView.getChildAt(0).height
        val bitmap = Bitmap.createBitmap(scrollView.width, height, Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        scrollView.draw(canvas)
        return bitmap
    }

    @JvmOverloads
    fun compressImageFromFile(filePath: String?, width: Int = 480, height: Int = 800): Bitmap? {
        if (StringUtils.isEmpty(filePath) || !File(filePath).exists()) return null
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(filePath, options)
        options.inSampleSize = calculateInSampleSize(options, width, height)
        options.inJustDecodeBounds = false

        var bitmap = BitmapFactory.decodeFile(filePath, options)
        val angle = readPictureDegree(filePath)
        if (angle != 0) {
            // 下面的方法主要作用是把图片转一个角度，也可以放大缩小等
            val m = Matrix()
            m.setRotate(angle.toFloat()) // 旋转angle度
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, m, true)// 从新生成图片
        }
        return bitmap
    }

    fun bitmapFromFile(filePath: String?): Bitmap? {
        filePath ?: return null
        if (StringUtils.isEmpty(filePath) || !File(filePath).exists()) return null
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(filePath, options)
        options.inSampleSize = 1
        options.inJustDecodeBounds = false
        var bitmap = BitmapFactory.decodeFile(filePath, options)
        val angle = readPictureDegree(filePath)
        if (angle != 0) {
            // 下面的方法主要作用是把图片转一个角度，也可以放大缩小等
            val m = Matrix()
            m.setRotate(angle.toFloat()) // 旋转angle度
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, m, true)// 从新生成图片
        }
        return bitmap
    }

    fun readPictureDegree(path: String?): Int {
        path?: return 0
        var degree = 0
        try {
            val exifInterface = ExifInterface(path)
            val orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return degree
    }

    /**
     * bitmap中的透明色用白色替换
     *
     * @param bitmap
     * @return
     */
    fun changeColor(bitmap: Bitmap?): Bitmap? {
        if (bitmap == null) {
            return null
        }
        val w = bitmap.width
        val h = bitmap.height
        val colorArray = IntArray(w * h)
        var n = 0
        for (i in 0 until h) {
            for (j in 0 until w) {
                val color = getMixtureWhite(bitmap.getPixel(j, i))
                colorArray[n++] = color
            }
        }
        return Bitmap.createBitmap(colorArray, w, h, Bitmap.Config.ARGB_8888)
    }

    /**
     * 获取和白色混合颜色
     *
     * @return
     */
    private fun getMixtureWhite(color: Int): Int {
        val alpha = Color.alpha(color)
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.rgb(getSingleMixtureWhite(red, alpha), getSingleMixtureWhite(green, alpha),
                getSingleMixtureWhite(blue, alpha))
    }

    /**
     * 获取单色的混合值
     *
     * @param color
     * @param alpha
     * @return
     */
    private fun getSingleMixtureWhite(color: Int, alpha: Int): Int {
        val newColor = color * alpha / 255 + 255 - alpha
        return if (newColor > 255) 255 else newColor
    }

    fun bmpToByteArray(bmp: Bitmap?, needRecycle: Boolean = true): ByteArray? {
        bmp ?: return null
        val output = ByteArrayOutputStream()
        bmp.compress(CompressFormat.PNG, 100, output)
        if (needRecycle) {
            bmp.recycle()
        }
        val result = output.toByteArray()
        try {
            output.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    /**
     * @param drawable drawable
     * @return bitmap bitmap
     */
    fun drawableToBitmap(drawable: Drawable?): Bitmap? {
        drawable ?: return null
        val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                if (drawable.opacity != PixelFormat.OPAQUE)
                    Bitmap.Config.ARGB_8888
                else
                    Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        return bitmap
    }

    /**
     * 获取android当前可用内存大小,以M为单位
     */
    fun getAvailMemory(context: Context): Int {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val mi = ActivityManager.MemoryInfo()
        am.getMemoryInfo(mi)
        val avail = mi.availMem.toInt() / (1024 * 1024)
        return avail
    }

    fun getMetaData(context: Context, key: String): String? {
        if (TextUtils.isEmpty(key)) {
            return null
        }
        var res: Any? = null
        try {
            val packageManager = context.packageManager
            if (packageManager != null) {
                val applicationInfo = packageManager
                        .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
                if (applicationInfo?.metaData != null) {
                    res = applicationInfo.metaData.get(key)
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        // 有可能是String, 有可能是Integer
        return res.toString()
    }

    /**
     * get screen width
     *
     * @param context context
     * @return
     */
    fun getScreenWidth(context: Context?): Int = getScreenSize(context).x

    fun getScreenSize(context: Context?): Point {
        if (context == null) return Point()
        val vm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        vm.defaultDisplay.getSize(point)
        return point
    }

    /**
     * get screen height
     *
     * @param context context
     * @return
     */
    fun getScreenHeight(context: Context?): Int = getScreenSize(context).y

    /**
     * 对double 类型数据进行四舍五入，并保留两位小数
     */
    fun getShortDouble(value: Double): Double {

        return Math.round(value * 100) / 100.0
    }

    fun versionName(context: Context?): String {
        val packageManager = context?.packageManager
        val packageInfo = packageManager?.getPackageInfo(context.packageName, 0)
        return packageInfo?.versionName ?: ""
    }

    fun versionCode(context: Context): Int {
        val packageManager = context.packageManager
        val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
        return packageInfo.versionCode
    }

    fun resString(context: Context?, resId: Int) = context?.resources?.getString(resId)

    fun showPw(showPw: Boolean, view: TextView?) {
        if (view == null) {
            ILog.e("===CommonTool===", "showPw view can not be null ...")
            return
        }
        if (showPw) {
            //如果选中，显示密码
            view.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            //否则隐藏密码
            view.transformationMethod = PasswordTransformationMethod.getInstance()
        }
    }

    fun closeIme(context: Activity?): Boolean {
        if (context == null) return false
        val view = context.window.peekDecorView()
        context.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        if (view != null) {
            val inputManger = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return inputManger.hideSoftInputFromWindow(view.windowToken, 0)
        }
        return false
    }

    /**
     * ==============显示一个通知，并注册清除事件=======================
     */
    val ACTION_NOTIFY_DELETE = "fastandroiddev_action_notify_delete"
    val NOTIFY_ID = "fastandroiddev_notify_id"

    fun showNotify(title: String, msg: String, intent: Intent?,
                   context: Context, resSmallIcon: Int, largeIcon: Int? = null, autoCancel: Boolean = true): Int {
        var pendIntent = intent
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(msg)) return -1

        val id = System.currentTimeMillis().toInt()
        if (pendIntent == null) pendIntent = Intent()
        pendIntent.putExtra(NOTIFY_ID, id)
        val deleteIntent = PendingIntent.getBroadcast(context, id,
                Intent(ACTION_NOTIFY_DELETE).putExtra(NOTIFY_ID, id), 0)
        val pi = PendingIntent.getActivity(context, id, pendIntent, 0)
        val builder = NotificationCompat.Builder(context)
                .setTicker(title)
                .setSmallIcon(resSmallIcon)
                .setContentTitle(title)
                .setContentText(msg)
                .setContentIntent(pi)
                .setDeleteIntent(deleteIntent)
                .setAutoCancel(autoCancel)
                .setDefaults(Notification.DEFAULT_SOUND) //设置声音，此为默认声音
        //    .setVibrate(vT) //设置震动，此震动数组为：long vT[]={300,100,300,100};
        //   .setOngoing(true)      //true，用户不能手动清除
        //  .setNumber(3)         //设置信息条数
        if (largeIcon != null)
            builder.setLargeIcon(BitmapFactory.decodeResource(IApplication.application.resources, largeIcon))
        val notification = builder.build()
        val notificationManager = context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(id, notification)
        return id
    }

    @TargetApi(19)
    fun isNotificationEnabled(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < 19) return true
        try {
            val CHECK_OP_NO_THROW = "checkOpNoThrow"
            val OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION"
            val mAppOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            val appInfo = context.applicationInfo
            val pkg = context.applicationContext.packageName
            val uid = appInfo.uid
            var appOpsClass: Class<*>? = null /* Context.APP_OPS_MANAGER */
            appOpsClass = Class.forName(AppOpsManager::class.java.name)
            val checkOpNoThrowMethod = appOpsClass!!.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String::class.java)
            val opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION)
            val value = opPostNotificationValue.get(Int::class.java) as Int
            return checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) as Int == AppOpsManager.MODE_ALLOWED
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    @TargetApi(19)
    fun setNotify(context: Context?): Boolean {
        if (context == null || Build.VERSION.SDK_INT < 19) return false
        val intent = Intent()
        intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
        intent.putExtra("app_package", context.packageName)
        intent.putExtra("app_uid", context.applicationInfo.uid)
        if (intent.resolveActivity(context.packageManager) == null) {
            return viewCurrentAppDetail(context)
        }
        try {
            val chooser = Intent.createChooser(intent, "请选择您需要打开的软件！")
            chooser.addFlags(FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(chooser)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun viewCurrentAppDetail(context: Context?): Boolean {
        if (context == null) return false
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.data = Uri.fromParts("package", context.packageName, null)
        if (intent.resolveActivity(context.packageManager) == null) {
            return false
        }
        try {
            val chooser = Intent.createChooser(intent, "请选择您需要打开的软件！")
            chooser.addFlags(FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(chooser)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    /**
     * 直接呼叫指定的号码
     *
     * @param mContext    上下文Context
     * @param phoneNumber 需要呼叫的手机号码
     */
    fun callPhone(mContext: Context?, phoneNumber: String): Boolean {
        if (mContext == null || StringUtils.isEmpty(phoneNumber)) return false
        val uri = Uri.parse("tel:" + phoneNumber)
        val call = Intent(Intent.ACTION_CALL, uri)
        try {
            mContext.startActivity(call)
        } catch (e: Exception) {
            e.printStackTrace()
            return toCallPhoneActivity(mContext, phoneNumber)
        }
        return true
    }

    /**
     * 跳转至拨号界面
     *
     * @param mContext    上下文Context
     * @param phoneNumber 需要呼叫的手机号码
     */
    fun toCallPhoneActivity(mContext: Context?, phoneNumber: String): Boolean {
        if (mContext == null || StringUtils.isEmpty(phoneNumber)) return false
        val uri = Uri.parse("tel:" + phoneNumber)
        val call = Intent(Intent.ACTION_DIAL, uri)
        try {
            mContext.startActivity(call)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun isMainProcess(context: Context?): Boolean {
        context ?: return false
        val packageName = context.packageName ?: ""
        return packageName.equals(getCurProcessName(context) ?: "")
    }

    const val selectPicture = 1001
    const val cropPhoto = 1002

    fun selectPicture(activity: Activity?, requestCode: Int = selectPicture) {
        val mIntent = Intent(Intent.ACTION_PICK, null)
        mIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        if (mIntent.resolveActivity(activity?.packageManager) == null) {
            ToastUtil.show(R.string.image_pick_failed)
            return
        }
        val chooserIntent = Intent.createChooser(mIntent, null)    //  没有软件可执行这个操作
        activity?.startActivityForResult(chooserIntent, requestCode)
    }

    fun cropPhoto(uri: Uri, activity: Activity?, requestCode: Int = cropPhoto) {
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(uri, "image/*")
        intent.putExtra("crop", "true")
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 96)
        intent.putExtra("outputY", 96)
        //            intent.putExtra("outputX", DensityUtil.dip2px(getActivity(), 150));   //  太大了
        //            intent.putExtra("outputY", DensityUtil.dip2px(getActivity(), 150));
        intent.putExtra("return-data", true)
        if (intent.resolveActivity(activity?.packageManager) == null) {
            ToastUtil.show(R.string.crop_failed)
            return
        }
        val chooserIntent = Intent.createChooser(intent, null)    //  没有软件可执行这个操作
        activity?.startActivityForResult(chooserIntent, requestCode)
    }

    fun getSelectedPicPath(activity: Activity?, intent: Intent?): String? {
        val selectedImage = intent?.data
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = activity?.contentResolver?.query(selectedImage,
                filePathColumn, null, null, null)
        if (cursor?.moveToFirst() == false) {
            cursor.close()
            return null
        }
        val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
        columnIndex ?: kotlin.run {
            cursor?.close()
            return null
        }
        val path = cursor.getString(columnIndex)
        cursor.close()
        return path
    }

    fun getCropPicPath(intent: Intent?): String? {
        if (intent?.hasExtra("data") == true) {
            val bitmap = intent.getParcelableExtra<Bitmap>("data")
            val cropFile = FileUtils.availablePath() + "/crop.jpg"
            ILog.i("===url===", cropFile)
            FileUtils.savBitmapToJpg(bitmap, cropFile)
            bitmap.recycle()
            return cropFile
        }
        return null
    }

    fun getCurProcessName(context: Context?): String? {
        val pid = android.os.Process.myPid()
        val mActivityManager = context?.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
        mActivityManager ?: return null
        for (appProcess in mActivityManager.runningAppProcesses) {
            if (appProcess.pid == pid) {
                return appProcess.processName
            }
        }
        return null
    }
}
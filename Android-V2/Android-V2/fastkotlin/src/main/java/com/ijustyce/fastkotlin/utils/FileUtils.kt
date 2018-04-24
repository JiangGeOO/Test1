package com.ijustyce.fastkotlin.utils

import android.content.Context
import android.content.CursorLoader
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.ijustyce.fastkotlin.IApplication
import okhttp3.ResponseBody
import java.io.*

/**
 * Created by yc on 17-11-8.
 */
object FileUtils {

    /**
     * @param file 文件夹或者文件名
     * @return 文件大小 以bites 为单位
     */
    fun getDirSize(file: File?): Double {
        //判断文件是否存在
        if (file != null && file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory) {
                val children = file.listFiles()
                var size = 0.0
                if (children != null && children.isNotEmpty()) {
                    for (f in children)
                        size += getDirSize(f)
                }
                return CommonTools.getShortDouble(size)
            } else {//如果是文件则直接返回其大小,以“bites”为单位
                return CommonTools.getShortDouble(file.length().toDouble())
            }
        } else {
            println("文件或者文件夹不存在，请检查路径是否正确！")
            return 0.0
        }
    }

    /**
     * 返回不存在的文件，一般用于文件下载
     * @param baseName  文件名称，带扩展，比如  xxx.apk 或者  xxx.png
     * @param basePath  可能需要的二级目录，随意即可，也可以不传
     * @return  一个不存在的文件
     */
    fun getUnExistsFile(baseName: String): File {
        val availablePath = availablePath()
        val tmp = File(availablePath + "/" + baseName)
        return renameIfExists(tmp)
    }

    /**
     * 获取可用的 文件目录，先尝试sdcard、然后尝试内部存储空间，最后则是data目录了，如果是sdcard，则会创建name这个文件夹
     *
     * @param name    文件夹名字,如果sdcard可用，会在sdcard创建这个目录
     * @return 成功返回true，失败返回false
     */
    fun availablePath(): String? {
        var f = IApplication.instance().getExternalFilesDir(null)
        if (f == null) {
            f = Environment.getExternalStorageDirectory()
            if (f == null) {
                f = IApplication.instance().filesDir
            }
        }
        return if (f == null) null else f.absolutePath
    }

    /**
     * 复制文件
     *
     * @param oldPath 要复制的文件路径
     * @param newPath 目标文件路径
     * @return 成功返回true，失败返回false
     */
    fun copyFile(oldPath: String?, newPath: String?): Boolean {

        if (oldPath == null || newPath == null) return false
        try {
            var bytesum = 0
            var byteread: Int
            val oldfile = File(oldPath)
            if (oldfile.exists()) { //文件存在时
                val inStream = FileInputStream(oldPath) //读入原文件
                val fs = FileOutputStream(newPath)
                val buffer = ByteArray(1444)
                byteread = inStream.read(buffer)
                while (byteread != -1) {
                    bytesum += byteread //字节数 文件大小
                    println(bytesum)
                    fs.write(buffer, 0, byteread)
                    byteread = inStream.read(buffer)
                }
                fs.close()
                inStream.close()
            } else {
                return false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false

        }

        return true
    }

    /**
     * 复制 assets 下的文件到 toPath 目录
     *
     * @param context  mContext
     * @param toPath   目标文件路径, 如果存在，将会被覆盖
     * @param fileName assets 下的文件名称
     * @return 成功返回true，失败返回false
     */
    fun copyAssets(context: Context?, toPath: String?, fileName: String?): Boolean {

        if (context == null || toPath == null || fileName == null) return false
        try {
            val toFile = File(toPath)
            if (toFile.exists()) {
                toFile.delete()
            }
            val myInput: InputStream
            val myOutput: OutputStream
            myOutput = FileOutputStream(toPath)
            myInput = context.assets.open(fileName)
            val buffer = ByteArray(1024)
            var length = myInput.read(buffer)
            while (length > 0) {
                myOutput.write(buffer, 0, length)
                length = myInput.read(buffer)
            }
            myOutput.flush()
            myInput.close()
            myOutput.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return false
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }

        return true
    }

    /**
     * 保存bitmap 为 jpg文件
     *
     * @param mBitmap bitmap
     * @param bitName 文件路径 , 必须 .jpg 结尾 比如 /sdcard/tmp/1.jpg
     */
    fun savBitmapToPng(mBitmap: Bitmap?, bitName: String?): Boolean {

        if (mBitmap == null || bitName == null) return false

        val f = File(bitName)
        val fOut: FileOutputStream
        try {
            fOut = FileOutputStream(f)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return false
        }

        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)
        try {
            fOut.flush()
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }

        try {
            fOut.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }

        return true
    }

    /**
     * 保存 bitmap 为 .jpg 文件
     *
     * @param mBitmap bitmap
     * @param bitName 文件路径 , 必须以 .jpg 结尾 比如 /sdcard/tmp/1.jpg
     */
    fun savBitmapToJpg(mBitmap: Bitmap?, bitName: String?): Boolean {
        if (mBitmap == null || bitName == null) return false
        val f = File(bitName)
        val fOut: FileOutputStream
        try {
            fOut = FileOutputStream(f)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return false
        }
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
        try {
            fOut.flush()
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
        try {
            fOut.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
        return true
    }

    /**
     * 读取文件
     *
     * @param file 文本文件
     * @return 文件内容 发生异常时，返回null
     */
    fun readTextFile(file: File?): String? {

        if (file == null || !file.exists()) return null

        var text: String? = null
        var `is`: InputStream? = null
        try {
            `is` = FileInputStream(file)
            text = readTextInputStream(`is`)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                if (`is` != null) {
                    `is`.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return text
    }

    /**
     * 从流中读取文件
     *
     * @param is 流对象
     * @return 文件内容
     */
    fun readTextInputStream(`is`: InputStream?): String? {

        if (`is` == null) return null
        val stringBuilder = StringBuilder()
        var line: String?
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(InputStreamReader(`is`))
            line = reader.readLine()
            while (line != null) {
                stringBuilder.append(line).append("\r\n")
                line = reader.readLine()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        return stringBuilder.toString()
    }

    /**
     * 将文本内容写入文件
     *
     * @param file 要写入的文件
     * @param str  要写入的内容
     */
    fun writeTextFile(file: File?, str: String?) {

        if (file == null || str == null) return
        val out: DataOutputStream
        try {
            out = DataOutputStream(FileOutputStream(file))
            out.write(str.toByteArray())
            out.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    /**
     * 遍历删除文件 如果是目录，则删除目录下的一切内容，目录不会删除，如果是文件，则删除文件
     */
    fun deleteFile(path: String) {

        if (StringUtils.isEmpty(path)) {
            return
        }

        val file = File(path)
        if (!file.exists()) {
            return
        }
        if (file.isFile) {
            file.delete()
            return
        }
        val list = if (file.isDirectory) file.listFiles() else null
        if (list != null && list.size > 0) {
            for (delete in list) {
                deleteFile(delete.absolutePath)
            }
        }
    }

    /**
     * 返回选择文件时 选定的文件路径
     *
     * @param context context
     * @param uri     onActivityResult里 Intent 对象 getData
     * @return 如果存在，返回路径，否则返回null
     */
    fun getPath(context: Context?, uri: Uri?): String? {
        if (context == null || uri == null) {
            return null
        }
        if ("content".equals(uri.scheme, ignoreCase = true)) {
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val loader = CursorLoader(context, uri, projection, null, null, null)
            val cursor = loader.loadInBackground() ?: return null
            try {
                val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                if (cursor.moveToFirst()) {
                    val result = cursor.getString(column_index)
                    cursor.close()
                    return result
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    fun writeInputStream(inputStream: InputStream?, file: File): Boolean {
        var outputStream: OutputStream? = null

        try {
            val fileReader = ByteArray(4096)
            outputStream = FileOutputStream(file)
            while (true) {
                val read = inputStream!!.read(fileReader)
                if (read == -1) {
                    break
                }
                outputStream.write(fileReader, 0, read)
            }
            outputStream.flush()
            return true
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        } finally {
            try {
                inputStream?.close()
                if (outputStream != null) {
                    outputStream.close()
                }
            } catch (e: Exception) {

            }
        }
    }

    fun setupApk(file: File, context: Context) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
            val chooser = Intent.createChooser(intent, "请选择您需要打开的软件！")
            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(chooser)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun getFileName(fullName: String): String? {
        if (StringUtils.isEmpty(fullName)) return null
        val index = fullName.lastIndexOf(".")
        return if (index < 0) null else fullName.substring(0, index)
    }

    fun getFileExtraName(fullName: String): String {
        return fullName.substring(fullName.lastIndexOf(".")).split("\\?".toRegex())
                .dropLastWhile { it.isEmpty() }.toTypedArray()[0]
    }

    private fun autoRenameFile(file: File): File {
        var fileName = getFileName(file.name)
        val extraName = getFileExtraName(file.name)
        val firstIndex = fileName!!.lastIndexOf("(")
        val lastIndex = fileName.lastIndexOf(")")
        var value = 1
        if (firstIndex != -1 && lastIndex != -1 && lastIndex > firstIndex + 1) {
            value = StringUtils.getInt(fileName.substring(firstIndex + 1, lastIndex)) + 1
            fileName = fileName.substring(0, firstIndex)
        }
        return File(file.parent + "/" + fileName + "(" + value + ")" + extraName)
    }

    /**
     * 重命名文件，如果文件已经存在，一般用于下载，不过推荐使用 getUnExistsFile
     * @param file  一个文件
     * @return  返回一个不存在的文件
     */
    fun renameIfExists(file: File): File {
        var tmp = file
        if (tmp.exists()) {
            tmp = renameIfExists(autoRenameFile(file))
        }
        return tmp
    }

    private val FileTypeMap = arrayOf(
            //{后缀名，MIME类型}
            arrayOf(".3gp", "video/3gpp"), arrayOf(".apk", "application/vnd.android.package-archive"),
            arrayOf(".asf", "video/x-ms-asf"), arrayOf(".avi", "video/x-msvideo"),
            arrayOf(".bin", "application/octet-stream"), arrayOf(".bmp", "image/bmp"),
            arrayOf(".c", "text/plain"), arrayOf(".class", "application/octet-stream"),
            arrayOf(".conf", "text/plain"), arrayOf(".cpp", "text/plain"),
            arrayOf(".doc", "application/msword"),
            arrayOf(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
            arrayOf(".xls", "application/vnd.ms-excel"),
            arrayOf(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
            arrayOf(".exe", "application/octet-stream"), arrayOf(".gif", "image/gif"),
            arrayOf(".gtar", "application/x-gtar"), arrayOf(".gz", "application/x-gzip"),
            arrayOf(".h", "text/plain"), arrayOf(".htm", "text/html"), arrayOf(".html", "text/html"),
            arrayOf(".jar", "application/java-archive"), arrayOf(".java", "text/plain"),
            arrayOf(".jpeg", "image/jpeg"), arrayOf(".jpg", "image/jpeg"),
            arrayOf(".js", "application/x-javascript"), arrayOf(".log", "text/plain"),
            arrayOf(".m3u", "audio/x-mpegurl"), arrayOf(".m4a", "audio/mp4a-latm"),
            arrayOf(".m4b", "audio/mp4a-latm"), arrayOf(".m4p", "audio/mp4a-latm"),
            arrayOf(".m4u", "video/vnd.mpegurl"), arrayOf(".m4v", "video/x-m4v"),
            arrayOf(".mov", "video/quicktime"), arrayOf(".mp2", "audio/x-mpeg"),
            arrayOf(".mp3", "audio/x-mpeg"), arrayOf(".mp4", "video/mp4"),
            arrayOf(".mpc", "application/vnd.mpohun.certificate"), arrayOf(".mpe", "video/mpeg"),
            arrayOf(".mpeg", "video/mpeg"), arrayOf(".mpg", "video/mpeg"), arrayOf(".mpg4", "video/mp4"),
            arrayOf(".mpga", "audio/mpeg"), arrayOf(".msg", "application/vnd.ms-outlook"),
            arrayOf(".ogg", "audio/ogg"), arrayOf(".pdf", "application/pdf"), arrayOf(".png", "image/png"),
            arrayOf(".pps", "application/vnd.ms-powerpoint"), arrayOf(".ppt", "application/vnd.ms-powerpoint"),
            arrayOf(".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"),
            arrayOf(".prop", "text/plain"), arrayOf(".rc", "text/plain"), arrayOf(".rmvb", "audio/x-pn-realaudio"),
            arrayOf(".rtf", "application/rtf"), arrayOf(".sh", "text/plain"), arrayOf(".tar", "application/x-tar"),
            arrayOf(".tgz", "application/x-compressed"), arrayOf(".txt", "text/plain"), arrayOf(".wav", "audio/x-wav"),
            arrayOf(".wma", "audio/x-ms-wma"), arrayOf(".wmv", "audio/x-ms-wmv"), arrayOf(".wps", "application/vnd.ms-works"),
            arrayOf(".xml", "text/plain"), arrayOf(".z", "application/x-compress"),
            arrayOf(".zip", "application/x-zip-compressed"), arrayOf("", "*/*"))

    fun getFileType(file: File): String {
        var type = "*/*"
        val fName = file.name
        val dotIndex = fName.lastIndexOf(".")
        if (dotIndex < 0) {
            return type
        }
        val end = fName.substring(dotIndex, fName.length).toLowerCase()
        if (StringUtils.isEmpty(end)) return type
        for (tmp in FileTypeMap) {
            if (end == tmp[0]) {
                type = tmp[1]
            }
        }
        return type
    }

    fun writeResponseBodyToFile(body: ResponseBody, file: File): Boolean {
        return writeInputStream(body.byteStream(), file)
    }

    /**
     * 调用文件管理器显示某个文件
     *
     * @param path    文件路径
     * @param context Context
     * @return 如果参数有误或者文件不存在，返回0，如果没有文件管理器返回-1，如果成功返回1
     */
    fun showFile(path: String?, context: Context?): Int {
        if (path == null || context == null || !File(path).exists()) {
            return 0
        }
        val selectedUri = Uri.parse(path)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(selectedUri, "resource/folder")

        if (intent.resolveActivityInfo(context.packageManager, 0) != null) {
            val chooser = Intent.createChooser(intent, "请选择您需要打开的软件！")
            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(chooser)
            return 1
        }
        return -1
    }
}
package com.ijustyce.fastkotlin.utils

import java.util.*
import java.util.regex.Pattern

/**
 * Created by arch on 17-11-6.
 */
object StringUtils {

    fun isEmpty(value: String?): Boolean {
        if (value == null || value.isEmpty()) return true
        val p = Pattern.compile("([\\s]+)")
        val matcher = p.matcher(value)
        return matcher.matches()
    }

    fun allEmpty(vararg values: String): Boolean {
        if (values.size < 1) return true
        for (tmp in values) {
            if (!isEmpty(tmp)) return false
        }
        return true
    }

    fun getAfterHostUrl(url: String?): String? {
        if (!RegularUtils.isCommonUrl(url)) return null
        val deleteHttp = url?.replace("https://", "")?.replace("http://", "")
        val array = deleteHttp?.split("/".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
        array ?: return null
        return if (array.size <= 1) null else deleteHttp.substring(array[0].length)
    }

    fun isIpSiteUrl(url: String): Boolean {
        val host = getOriginDomain(url) ?: return false
        return RegularUtils.isNumber(host.replace("\\.".toRegex(), ""))
    }

    fun getOriginDomain(url: String): String? {
        val host = getHost(url) ?: return null
        var lastIndex = host.lastIndexOf(".")
        val end = if (lastIndex != -1) host.substring(lastIndex) else host
        var begin = host.substring(0, lastIndex)
        lastIndex = begin.lastIndexOf(".")
        if (lastIndex != -1) {
            begin = begin.substring(lastIndex + 1)
        }
        return begin + end
    }

    fun getHost(url: String): String? {
        if (!RegularUtils.isCommonUrl(url)) return null
        val delteHttp = url.replace("https://", "").replace("http://", "")
        val array = delteHttp.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return if (array.size == 0) null else array[0]
    }

    fun getInt(value: String?): Int {
        return getInt(value, 0)
    }

    fun getInt(value: String?, defaultValue: Int): Int {
        if (!RegularUtils.isInt(value)) return defaultValue
        try {
            return Integer.parseInt(value)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return defaultValue
    }

    fun getDouble(value: String): Double {
        return getDouble(value, 0.0)
    }

    /**
     * 返回几千，保留两位小数！
     * @param v
     */
    fun getKValue(v: Any): String {
        val s = v.toString()
        val value = getInt(s)
        if (value > 1000) {
            val result = value / 1000 + if (value % 1000 == 0) 0 else 1
            return result.toString() + "k"
        }
        return s
    }

    fun getDouble(value: String, defaultValue: Double): Double {
        if (!RegularUtils.isNumber(value)) return defaultValue
        try {
            return java.lang.Double.parseDouble(value)
        } catch (e: Exception) {
            e.printStackTrace()
            return defaultValue
        }

    }

    fun getFloat(value: String): Float {
        return getFloat(value, 0f)
    }

    fun getFloat(value: String, defaultValue: Float): Float {
        if (!RegularUtils.isNumber(value)) return defaultValue
        try {
            return java.lang.Float.parseFloat(value)
        } catch (e: Exception) {
            e.printStackTrace()
            return defaultValue
        }

    }

    fun getLong(value: String, defaultValue: Long): Long {
        if (!RegularUtils.isInt(value)) return defaultValue
        try {
            return java.lang.Long.parseLong(value)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return defaultValue
    }

    fun getLong(value: String): Long {
        return getLong(value, 0)
    }

    /**
     * 解析并返回url的参数, url 必须为uft-8编码
     * @param url url 比如： https://mclient.alipay.com/home/exterfaceAssign.htm?alipay_exterface
     * _invoke_assign_client_ip=115.192.220.130&body=测试
     */
    fun getUrlParams(url: String): HashMap<String, String> {
        return getUrlParams(url, "utf-8")
    }

    /**
     * 解析并返回url的参数
     * @param encode url 编码
     * @param url url 比如： https://mclient.alipay.com/home/exterfaceAssign.htm?alipay_exterface
     * _invoke_assign_client_ip=115.192.220.130&body=测试
     */
    fun getUrlParams(url: String, encode: String): HashMap<String, String> {
        var decodeUrl = url
        val map = HashMap<String, String>()
        try {
            decodeUrl = java.net.URLDecoder.decode(url, encode)
        } catch (ignore: Exception) {
        }

        val urls = decodeUrl.split("\\?".toRegex(), 2).toTypedArray()
        return if (urls.size < 2) map else getKeyValue(urls[1])
    }

    fun getKeyValue(keyValues: String): HashMap<String, String> {
        val map = HashMap<String, String>()
        val keyAndValue = keyValues.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (keyAndValue.isEmpty()) return map
        for (keyValue in keyAndValue) {
            val tmp = keyValue.split("=".toRegex(), 2).toTypedArray()
            if (tmp.size < 2) continue
            map.put(tmp[0], tmp[1])
        }
        return map

        // 也可以用以下的正则，可是正则慢了不少！
        //        HashMap<String, String> map = new HashMap<>();
        //        Pattern p = Pattern.compile("([^?&=]+)([=])([^&]+)");
        //        Matcher m = p.matcher(url);
        //        while(m.find()){
        //            map.put(m.group(1), m.group(3));
        //        }
        //        return map;
    }

    /**
     * 判断是否为新版本
     * @param net   网络返回的版本 比如 0.5.09
     * @param local 本地版本      比如 0.5.1
     * @return  是否为新版本
     */
    fun isNewVersion(net: String, local: String): Boolean {
        val NoNumberNew = RegularUtils.deleteNoNumber(net)
        val NoNumberLocal = RegularUtils.deleteNoNumber(local)
        if (StringUtils.isEmpty(NoNumberNew) || StringUtils.isEmpty(NoNumberLocal)) return false
        val netVersion = NoNumberNew.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val localVersion = NoNumberLocal.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        val size = if (netVersion.size > localVersion.size) localVersion.size else netVersion.size
        for (i in 0 until size) {
            val netTmp = getDouble("0." + netVersion[i])
            val localTmp = getDouble("0." + localVersion[i])
            if (netTmp > localTmp) {
                return true
            }
            if (netTmp < localTmp) {
                return false
            }
        }
        return netVersion.size > localVersion.size //  除非长度不一且前面每位都相等，否则不可能到这步，
    }

    fun arabicNumberToChinese(number: Int): String {
        var fuhao = ""
        if (number < 0) {
            fuhao = "负"
        }
        if (number < 100000) {
            return getChineseNumberLessShiWan(number.toString())
        }
        if (number < 100000000) {
            val s1 = arabicNumberToChinese(number / 10000)
            val s2 = arabicNumberToChinese(number % 10000)
            return s1 + "万零" + s2
        }
        var index = 1  //  int 类型最大 十亿
        val value = number.toString()
        if (number > 1000000000) {
            index = 2
        }
        val value1 = value.substring(0, index)
        val value2 = value.substring(index)
        val s1 = arabicNumberToChinese(Integer.parseInt(value1))
        val s2 = arabicNumberToChinese(Integer.parseInt(value2))
        return fuhao + s1 + "亿" + s2
    }

    private fun getLessThanYiValue(number: Int): Int {
        if (number > 1000000000) {
            return Integer.parseInt(number.toString().substring(2))
        } else if (number > 100000000) {
            return Integer.parseInt(number.toString().substring(1))
        }
        return number
    }

    private fun getChineseNumberLessShiWan(value: String): String {
        val stringBuilder = StringBuilder()
        val size = value.length
        for (i in 0 until size) {
            stringBuilder.append(getChineseNumberByLength(value.substring(i, size)))
        }
        return stringBuilder.toString()
    }

    private fun getChineseNumberByLength(value: String?): String {
        if (value == null) return ""
        val s = getChineseNumber(value) ?: return ""
        when (value.length) {

            2 -> return if (s == "一") "十" else s + "十"

            3 -> return s + "百"

            4 -> return s + "千"

            5 -> return s + "万"

            else -> return s
        }
    }

    private fun getChineseNumber(value: String?): String? {
        if (value == null || value.length < 1) return ""
        when (value.substring(0, 1)) {

            "1" -> return "一"

            "2" -> return "二"

            "3" -> return "三"

            "4" -> return "四"

            "5" -> return "五"

            "6" -> return "六"

            "7" -> return "七"

            "8" -> return "八"

            "9" -> return "九"
            else -> return null
        }
    }
}
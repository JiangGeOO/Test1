package com.ijustyce.fastkotlin.utils

import java.util.regex.Pattern

/**
 * Created by arch on 17-11-6.
 */
object RegularUtils {

    fun isMobilePhone(phone: String?): Boolean {
        phone ?: return false
        val p = Pattern
                .compile("^(\\+86)?((13[0-9])|(15[^4,\\D])|(18[0-9])|(145)|(147)|(17[0-9]))\\d{8}$")
        val m = p.matcher(phone)
        return m.matches()
    }

    fun isInt(value: String?): Boolean {
        if (StringUtils.isEmpty(value)) return false
        val p = Pattern.compile("^(-)?([0-9])+$")
        val m = p.matcher(value)
        return m.matches()
    }

    fun isNumber(value: String?): Boolean {
        if (StringUtils.isEmpty(value)) return false
        val p = Pattern
                .compile("^(-)?(([0-9])|(\\.))+$")
        val m = p.matcher(value)
        return m.matches()
    }

    /**
     * 删除不是0-9 以及. 的内容
     * @param value 原始字符串
     * @return  删除不是0-9以及.剩余的部分
     */
    fun deleteNoNumber(value: String?): String {
        if (StringUtils.isEmpty(value)) return ""
        val p = Pattern.compile("[^0-9.]")
        val m = p.matcher(value)
        return m.replaceAll("").trim { it <= ' ' }
    }

    /**
     * 删除不是0-9 的内容
     * @param value 原始字符串
     * @return  删除不是0-9 剩余的部分
     */
    fun deleteNoInt(value: String?): String {

        if (StringUtils.isEmpty(value)) return ""
        val p = Pattern.compile("[^0-9]")
        val m = p.matcher(value)
        return m.replaceAll("").trim { it <= ' ' }
    }

    /**
     * 判断是否是图片，即判断扩展名是否为 .jpg, .jpeg, .png
     */
    fun isImage(file: String?): Boolean {
        return file != null && (file.endsWith(".jpg") || file.endsWith(".jpeg")
                || file.endsWith(".png"))
    }

    /**
     * 判断是否是身份证号，包括生日校验
     * @param cardNum   身份证号
     */
    fun isCard(cardNum: String?): Boolean {
        if (StringUtils.isEmpty(cardNum) || cardNum?.length != 15 && cardNum?.length != 18) return false
        //定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
        val idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])")
        val idNumMatcher = idNumPattern.matcher(cardNum)
        if (idNumMatcher.matches()) {
            println("您的出生年月日是：")
            val birthDatePattern = Pattern.compile("\\d{6}(\\d{4})(\\d{2})(\\d{2}).*")//身份证上的前6位以及出生年月日
            val birthDateMather = birthDatePattern.matcher(cardNum)
            if (birthDateMather.find()) {
                val year = birthDateMather.group(1)
                val month = birthDateMather.group(2)
                val date = birthDateMather.group(3)
                println(year + "年 " + month + "月 " + date + "日")
                return true
            }
        } else {
            println("您输入的并不是身份证号")
            return false
        }
        return false
    }

    /**
     * 判断是否为域名，类似：http://22.com 不能是  http://22.22/22,  暂不支持中文域名
     * @param string    一个url
     * @return  true or false
     */
    fun isHost(string: String?): Boolean {
        if (string == null) return false
        val p = Pattern.compile("^((http://)|(https://))([^./]+)(((\\.)([^./]+))+)(((:)([0-9])+)?)((/)?)$")
        return p.matcher(string).matches()
    }

    /**
     * 判断是否为网址，类似：http://22.com/222 暂不支持中文域名
     * @param string    一个url
     * @return  true or false
     */
    fun isUrl(string: String?): Boolean {
        if (string == null) return false
        val p = Pattern.compile("^((http://)|(https://))([^./]+)(((\\.)([^./]+))+)(((:)([0-9])+)?)(/)(.+)$")
        return p.matcher(string).matches()
    }

    /**
     * 判断是否为网址  包括域名、url
     * @param text  url
     */
    fun isCommonUrl(text: String?): Boolean {
        return isUrl(text) || isHost(text)
    }

    /**
     * 判断是否是通用手机号，即：正常11位手机号、6位城市短号、通用固话号码
     * @param s String 字符串
     */
    fun isCommonPhone(s: String?): Boolean {
        return isMobilePhone(s) || isFixedPhone(s) || isShortPhone(s)
    }

    fun isShortPhone(s: String?): Boolean {
        if (s == null) return false
        val p = Pattern.compile("^([0-9]{6})$")
        val m = p.matcher(s)
        return m.matches()
    }

    fun isFixedPhone(s: String?): Boolean {
        if (s == null) return false
        val p = Pattern.compile("^([0-9]{3,5}(-)?)?([0-9]{7,8})((-)?[0-9]{1,4})?$")
        val m = p.matcher(s)
        return m.matches()
    }

    fun isEmail(s: String?): Boolean {
        if (s == null) return false
        val p = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\" +
                ".[0-9]{1,3}" + "\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")
        val m = p.matcher(s)
        return m.matches()
    }

    fun delStringBlank(s: String?): String? {
        return s?.replace(" ".toRegex(), "")
    }
}
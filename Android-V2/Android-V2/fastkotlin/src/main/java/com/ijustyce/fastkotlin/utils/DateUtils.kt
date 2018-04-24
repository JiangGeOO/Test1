package com.ijustyce.fastkotlin.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by arch on 17-11-22.
 */
object DateUtils {

    /**
     * get current date like yyyy/MM/dd HH:mm:ss
     *
     * @param formatter like yyyy/MM/dd HH:mm:ss
     */
    fun getDate(formatter: String): Date? {

        if (StringUtils.isEmpty(formatter)) return null
        val ft = SimpleDateFormat(formatter, Locale.getDefault())
        val dd = Date()
        return stringToDate(ft.format(dd), formatter)
    }

    /**
     * convert a date string to date
     *
     * @param value  value of date , like 2014/04/27 11:42:00
     * @param format like yyyy/MM/dd HH:mm:ss
     * @return date
     */

    fun stringToDate(value: String, format: String): Date? {

        if (StringUtils.isEmpty(format) || StringUtils.isEmpty(value)) return null
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        try {
            return sdf.parse(value)
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return null
    }

    /**
     * get current timesTamp , date formatted like yyyy/MM/dd HH:mm:ss
     */
    fun getTimesTamp(): Long {
        return dateToTimesTamp(getDate("yyyy/MM/dd HH:mm:ss"))
    }

    /**
     * convert a certain date to Unix timestamp
     *
     * @param date , like 2014-04-27 11:42:00
     * @return Unix timestamp
     */
    fun dateToTimesTamp(date: Date?): Long {
        return if (date == null) 0 else date.time / 1000
    }

    fun subDate(newDate: Date?, oldDate: Date?): Int {
        newDate ?: return 0
        oldDate ?: return 0
        val newDays = getDaysOfMonth(newDate.month, isLeapYear(newDate.year))
        val oldDays = getDaysOfMonth(oldDate.month, isLeapYear(oldDate.year))
        val subYear = subYear(newDate.year, oldDate.year)
        val subDays = newDate.date - oldDate.date
        return newDays - oldDays + subYear + subDays
    }

    fun getDaysOfMonth(month: Int, leapYear: Boolean): Int {
        var days = 0
        val daysInFebruary = if (leapYear) 29 else 28
        val array = arrayOf(31, daysInFebruary, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
        //  这个月到下个月经过的日期是这个月，所以包含这个月但不包含下个月
        (0 until month).forEach { current -> days += array[current] }
        return days
    }

    fun isLeapYear(year: Int): Boolean = if (year % 100 == 0) year % 400 == 0 else year % 4 == 0

    private fun subYear(newYear: Int, oldYear: Int): Int {
        when {
            newYear == oldYear -> return 0
            newYear < oldYear -> return -subYear(oldYear, newYear)
        }
        var days = 0
        //  今年到明年经过的时间其实是今年，所以包括今年但不包括明年
        (oldYear until newYear).forEach { current -> days += if (isLeapYear(current)) 366 else 365 }
        return days
    }

    /**
     * convert Unix timestamp to a certain date
     * @param timesTamp Unix timestamp
     * @return date , like 2014-04-27 11:42:00
     */
    fun timesTampToDate(timesTamp: String?, format: String?, locale: Locale = Locale.getDefault()): String? {
        if (format == null || timesTamp == null) return null
        val sdf = SimpleDateFormat(format, locale)

        val lcc_time = StringUtils.getLong(timesTamp)
        return if (lcc_time == 0L) null else sdf.format(Date(lcc_time * 1000))
    }
}
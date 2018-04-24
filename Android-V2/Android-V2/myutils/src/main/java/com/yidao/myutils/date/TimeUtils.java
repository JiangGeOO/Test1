package com.yidao.myutils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lee on 2017/5/17.
 */

public class TimeUtils {
    public static String getTime1(){
        long time=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        Date d1=new Date(time);
        String t1=format.format(d1);
        return t1;
    }

    public static String getTime2(long time){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1=new Date(time);
        String t1=format.format(d1);
        return t1;
    }

    public static String getTime3(long time){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Date d1=new Date(time);
        String t1=format.format(d1);
        return t1;
    }

    //yy-MM-dd
    public static long dataToMs(String date){
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

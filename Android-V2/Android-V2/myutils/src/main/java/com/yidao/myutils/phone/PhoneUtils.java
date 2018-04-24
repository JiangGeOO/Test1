package com.yidao.myutils.phone;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * Created by Lee on 2017/5/30.
 */

public class PhoneUtils {
    private static TelephonyManager telephonyManager;
    //获取设备id
    public static String getDeviceID(Context context){
        if(null == telephonyManager){
            telephonyManager = (TelephonyManager) (context.getSystemService(Context.TELEPHONY_SERVICE));
        }
        String deviceId = telephonyManager.getDeviceId();
        return deviceId;
    }
    //获取序列卡，只有装有sim卡的手机才能使用，CDMA返回空值
    public static String getSIMNumber(Context context){
        if(null == telephonyManager){
            telephonyManager = (TelephonyManager) (context.getSystemService(Context.TELEPHONY_SERVICE));
        }
        String simSerialNumber = telephonyManager.getSimSerialNumber();
        return simSerialNumber;
    }
    //获取android id
    public static String getAndroidId(Context context){
        String ANDROID_ID = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        return ANDROID_ID;
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    //获取设备机型
    public static String getDeviceModel(){
        return android.os.Build.MODEL;
    }

    //获取系统版本
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    //获取versoncode
    public static String getVersionCode(Context context){
        PackageManager packageManager=context.getPackageManager();
        PackageInfo packageInfo;
        String versionCode="";
        try {
            packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            versionCode=packageInfo.versionCode+"";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
}

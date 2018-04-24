package com.yidao.myutils.network;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Lee on 2017/5/30.
 */

public class NetWorkUtils {
    public static int NO_NETWORK = 1;
    public static int MOBILE_NETWORK = 2;
    public static int WIFI_NETWORK = 3;
    /**
     * 获取当前网络状态
     * @param context
     * @return
     */
    public static int getNetWorkStatus(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(null != activeNetworkInfo){
            int type = activeNetworkInfo.getType();
            switch (type) {
                case ConnectivityManager.TYPE_MOBILE:
                    return MOBILE_NETWORK;
                case ConnectivityManager.TYPE_WIFI:
                    return WIFI_NETWORK;
                default:
                    return NO_NETWORK;
            }
        }else {
            return NO_NETWORK;
        }
    }
    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity)
    {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }
}

package com.yidao.myutils.toast;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Lee on 2017/7/11.
 */

public class ToastUtil {
    private static Toast toast;

    public static void showToast(String msg, boolean longTime, Context context) {
        if (context == null) return;
        if (null == toast) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }
        if (longTime) {
            toast.setDuration(Toast.LENGTH_LONG);
        } else {
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.setText(msg);
        toast.show();
    }
}

package com.yidao.threekmo.customview;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yidao.threekmo.R;

/**
 * Created by Administrator on 2017\8\25 0025.
 */

public class SuccessToast {
    private static Toast toast;
    private static TextView tvTitle;

    public static void showToast(String msg, boolean longTime,Context context){
        if(null == toast){
            View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.layout_success_toast, null);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            toast = Toast.makeText(context,msg,Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.setView(view);
        }
        if(longTime){
            toast.setDuration(Toast.LENGTH_LONG);
        }else {
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        tvTitle.setText(msg);
        toast.show();
    }
}

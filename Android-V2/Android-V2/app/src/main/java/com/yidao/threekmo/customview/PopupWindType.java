package com.yidao.threekmo.customview;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.yidao.threekmo.R;
import com.yidao.threekmo.application.MyApplication;

/**
 * Created by Administrator on 2017\8\22 0022.
 */

public class PopupWindType {
    private final LayoutInflater layoutInflater;
    private final PopupWindow popupWindow;
    private Context context;

    public PopupWindType(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_popupwind_type, null);
        int pHeigh = MyApplication.getInstance().getScreenHeight()/5*3;
        int pWidth = MyApplication.getInstance().getScreenWidth()/2;
        popupWindow = new PopupWindow(view,pWidth,pHeigh);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.popupwind_type);

    }

    public void show(View view){
        popupWindow.showAsDropDown(view);
    }
}

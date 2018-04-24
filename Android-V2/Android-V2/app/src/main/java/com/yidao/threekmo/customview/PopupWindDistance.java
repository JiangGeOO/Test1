package com.yidao.threekmo.customview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.yidao.threekmo.R;

/**
 * Created by Administrator on 2017\8\22 0022.
 */

public class PopupWindDistance {
    private final LayoutInflater layoutInflater;
    private final PopupWindow popupWindow;
    private Context context;

    public PopupWindDistance(Context context) {
        this.context = context;
        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setBackgroundColor(Color.WHITE);
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_popupwind_distance, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.popupwind_type);

    }

    public void show(View view){
        popupWindow.showAsDropDown(view);
    }
}

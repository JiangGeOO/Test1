package com.yidao.threekmo.bean;

import android.view.View;

/**
 * Created by manjaro on 18-1-31.
 */

public class WithdrawalBean {
    public int position = -1;
    public String name, value;

    public WithdrawalBean(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public WithdrawalBean(String name, String value, int position) {
        this.name = name;
        this.value = value;
        this.position = position;
    }

    public int textSize() {
        return position == 6 ? 28: 32;
    }

    public int lineVisible() {
        return position == 0 ? View.GONE : View.VISIBLE;
    }
}

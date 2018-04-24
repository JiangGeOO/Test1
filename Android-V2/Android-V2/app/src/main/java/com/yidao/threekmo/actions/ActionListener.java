package com.yidao.threekmo.actions;

import java.io.Serializable;

/**
 * Created by Lee on 2017/6/8.
 */

public interface ActionListener {
    public void onSuccess(Serializable data);
    public void onFailed(String errMsg);
    public void onPreTask();

}

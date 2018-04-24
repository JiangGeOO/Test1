package com.yidao.threekmo.actions;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Lee on 2017/6/8.
 */

public abstract class BaseAction {
    protected static final int ONSUCCESS = 1;
    protected static final int ONFAILED = 2;

    public abstract void start(Map<String, String> paras, Context context);

    protected Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data;
            switch (msg.what) {
                case ONSUCCESS:
                    if(null!=actionListener){
                        data = msg.getData();
                        Serializable serializable = data.getSerializable("data");
                        actionListener.onSuccess(serializable);
                    }
                    break;

                case ONFAILED:
                    if(null!=actionListener){
                        data = msg.getData();
                        String errMsg = data.getString("data");

                        actionListener.onFailed(errMsg);
                    }
                    break;
                default:
                    break;
            }

        }
    };


    protected ActionListener actionListener;

    public ActionListener getActionListener() {
        return actionListener;
    }

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }
}

package com.yidao.threekmo.bean;

import java.io.Serializable;

/**
 * Created by Smart~ on 2017/10/13.
 */

public class ActivityDetailsResult implements Serializable {

    private int rspCode;
    private String rspMsg;
    private MainHomeListItem data;


    public int getRspCode() {
        return rspCode;
    }

    public void setRspCode(int rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspMsg() {
        return rspMsg;
    }

    public void setRspMsg(String rspMsg) {
        this.rspMsg = rspMsg;
    }

    public MainHomeListItem getData() {
        return data;
    }

    public void setData(MainHomeListItem data) {
        this.data = data;
    }

}

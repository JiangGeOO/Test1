package com.yidao.threekmo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Smart~ on 2017/11/25.
 */

public class ExperienceConnectResult implements Serializable {

    private int rspCode;
    private String rspMsg;
    private List<ShopDetailResult> data;

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

    public List<ShopDetailResult> getData() {
        return data;
    }

    public void setData(List<ShopDetailResult> data) {
        this.data = data;
    }
}

package com.yidao.threekmo.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017\8\23 0023.
 */

public class BaseResultModel<T> implements Serializable{

    /**
     * data : null
     * rspCode : 0
     * rspMsg : 操作成功
     */

    private int rspCode;
    private String rspMsg;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

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
}

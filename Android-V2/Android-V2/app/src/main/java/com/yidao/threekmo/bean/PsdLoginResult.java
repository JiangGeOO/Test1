package com.yidao.threekmo.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017\9\8 0008.
 */

public class PsdLoginResult implements Serializable {

    private DataBean data;
    private int rspCode;
    private String rspMsg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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

    public static class DataBean {
        private UserBean TkmUserInfo;
        private String token;

        public UserBean getTkmUserInfo() {
            return TkmUserInfo;
        }

        public void setTkmUserInfo(UserBean tkmUserInfo) {
            TkmUserInfo = tkmUserInfo;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}

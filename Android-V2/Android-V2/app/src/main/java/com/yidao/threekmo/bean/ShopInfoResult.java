package com.yidao.threekmo.bean;

import java.io.Serializable;

/**
 * Created by Smart~ on 2017/11/15.
 */

public class ShopInfoResult implements Serializable {

    private int rspCode;
    private String rspMsg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{
        private boolean success;
        private ShopDetailResult data;
        private int errorCode;
        private String errorMsg;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public ShopDetailResult getData() {
            return data;
        }

        public void setData(ShopDetailResult data) {
            this.data = data;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }
    }

}

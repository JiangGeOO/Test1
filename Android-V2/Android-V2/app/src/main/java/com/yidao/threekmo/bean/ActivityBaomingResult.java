package com.yidao.threekmo.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/28.
 */

public class ActivityBaomingResult implements Serializable{

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

        private String success;
        private MainHomeListItem data;
        private int errorCode;
        private String errorMsg;

        public String isSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public MainHomeListItem getData() {
            return data;
        }

        public void setData(MainHomeListItem data) {
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

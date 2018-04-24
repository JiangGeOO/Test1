package com.yidao.threekmo.bean;

/**
 * Created by manjaro on 18-1-30.
 */

public class HasSetPayPw {
    public DataBean data;
    public int rspCode;
    public String rspMsg;

    public static class DataBean {
        public boolean success;
        public String data;
        public int errorCode;
        public Object errorMsg;
    }
}

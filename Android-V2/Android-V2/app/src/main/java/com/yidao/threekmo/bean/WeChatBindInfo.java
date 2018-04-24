package com.yidao.threekmo.bean;

/**
 * Created by manjaro on 18-1-30.
 */

public class WeChatBindInfo {

    public DataBeanX data;
    public int rspCode;
    public String rspMsg;

    public static class DataBeanX {
        public boolean success;
        public DataBean data;
        public int errorCode;

        public static class DataBean {
            public int id;
            public Object gmtCreate;
            public Object gmtModified;
            public Object creator;
            public Object modifier;
            public int userId;
            public String openId;
            public String wxNickname;
            public Object enable;
        }
    }
}

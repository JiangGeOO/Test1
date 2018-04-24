package com.yidao.threekmo.bean;

/**
 * Created by deepin on 18-2-2.
 */

public class WithdrawlResult {

    public DataBeanX data;
    public int rspCode;
    public String rspMsg;

    public static class DataBeanX {
        public boolean success;
        public DataBean data;
        public int errorCode;
        public Object errorMsg;

        public static class DataBean {
            public String id;
            public Object gmtCreate;
            public Object gmtModified;
            public Object creator;
            public Object modifier;
            public String orderNumber;
            public Object orderId;
            public int userId;
            public String openId;
            public String wxNickname;
            public double amount;
            public double poundage;
            public String errorMsg;
            public Object payDate;
            public int status;
            public String spbillCreateIp;
            public String type;
            public Object enable;
            public Object payeeAccount;
            public String payeeRealName;
        }
    }
}

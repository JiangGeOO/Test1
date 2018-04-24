package com.yidao.threekmo.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WithdrawalDetail {

    public DataBeanX data;
    public int rspCode;
    public String rspMsg;

    public static class DataBeanX {
        public boolean success;
        public DataBean data;
        public int errorCode;
        public Object errorMsg;

        public static class DataBean {
            public int id;
            public long gmtCreate;
            public long gmtModified;
            public Object creator;
            public Object modifier;
            public String orderNumber;
            public String orderId;
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
            public int enable;
            public String payeeAccount;
            public String payeeRealName;

            public String amount() {
                return "" + amount;
            }

            public String fee() {
                return poundage + "";
            }

            public String real() {
                return amount - poundage + "";
            }

            public String date() {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                return format.format(new Date(gmtCreate));
            }

            public String account() {
                return payeeAccount == null? wxNickname : payeeAccount;
            }

            public String type() {
                return "wx".equals(type) ? "微信" : "支付宝";
            }
        }
    }
}

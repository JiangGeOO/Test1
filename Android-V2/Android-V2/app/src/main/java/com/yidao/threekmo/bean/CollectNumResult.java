package com.yidao.threekmo.bean;

import java.io.Serializable;

/**
 * Created by Smart~ on 2017/11/22.
 */

public class CollectNumResult implements Serializable {

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
        private int ordersSum;
        private int merchandiseSum;
        private int subjectTrainSum;

        public int getOrdersSum() {
            return ordersSum;
        }

        public void setOrdersSum(int ordersSum) {
            this.ordersSum = ordersSum;
        }

        public int getMerchandiseSum() {
            return merchandiseSum;
        }

        public void setMerchandiseSum(int merchandiseSum) {
            this.merchandiseSum = merchandiseSum;
        }

        public int getSubjectTrainSum() {
            return subjectTrainSum;
        }

        public void setSubjectTrainSum(int subjectTrainSum) {
            this.subjectTrainSum = subjectTrainSum;
        }
    }

}

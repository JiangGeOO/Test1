package com.yidao.threekmo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Smart~ on 2017/11/14.
 */

public class ShopIndexResult implements Serializable {

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

    public static class DataBean {
        private List<ShopDetailResult> listHot;
        private List<ShopDetailResult> listBurst;
        private List<ShopDetailResult> listHandpick;
        private List<ShopDetailResult> listBanner;

        public List<ShopDetailResult> getListHot() {
            return listHot;
        }

        public void setListHot(List<ShopDetailResult> listHot) {
            this.listHot = listHot;
        }

        public List<ShopDetailResult> getListBurst() {
            return listBurst;
        }

        public void setListBurst(List<ShopDetailResult> listBurst) {
            this.listBurst = listBurst;
        }

        public List<ShopDetailResult> getListHandpick() {
            return listHandpick;
        }

        public void setListHandpick(List<ShopDetailResult> listHandpick) {
            this.listHandpick = listHandpick;
        }

        public List<ShopDetailResult> getListBanner() {
            return listBanner;
        }

        public void setListBanner(List<ShopDetailResult> listBanner) {
            this.listBanner = listBanner;
        }
    }

}

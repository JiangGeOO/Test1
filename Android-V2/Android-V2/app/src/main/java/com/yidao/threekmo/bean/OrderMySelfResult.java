package com.yidao.threekmo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Smart~ on 2017/11/20.
 */

public class OrderMySelfResult implements Serializable {

    private String rspMsg;
    private int rspCode;
    private DataBean data;

    public String getRspMsg() {
        return rspMsg;
    }

    public void setRspMsg(String rspMsg) {
        this.rspMsg = rspMsg;
    }

    public int getRspCode() {
        return rspCode;
    }

    public void setRspCode(int rspCode) {
        this.rspCode = rspCode;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{

        private int start;
        private int pageIndex;
        private int pageSize;
        private int pageCount;
        private int totalCount;
        private List<OrderDetailResult> datas;

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<OrderDetailResult> getDatas() {
            return datas;
        }

        public void setDatas(List<OrderDetailResult> datas) {
            this.datas = datas;
        }
    }

}

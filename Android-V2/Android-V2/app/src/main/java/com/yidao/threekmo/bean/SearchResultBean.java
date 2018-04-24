package com.yidao.threekmo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017\8\28 0028.
 */

public class SearchResultBean implements Serializable {

    /**
     * data : {"start":0,"pageIndex":1,"pageSize":15,"pageCount":1,"totalCount":1,"datas":[{"id":1,"gmtCreate":1503646455000,"gmtModified":1503646458000,"creator":null,"modifier":null,"name":"111","typeId":2,"businessStart":0,"businessEnd":3600,"longitude":"","latitude":null,"elevation":null,"floor":null,"number":null,"address":null,"province":null,"provinceCode":null,"city":null,"cityCode":null,"area":null,"areaCode":null,"individualitySignature":null,"labelNames":null,"labelIds":null,"statusIds":null,"photoId":null,"photoUrl":null,"phone":null,"telephone":null,"grade":null,"webchatNum":null,"subjectId":null,"subjectTrainId":null,"subjectNumber":null,"enable":1,"distance":null,"complete":null,"typeName":null,"isTrain":null,"inferiorsCount":null}]}
     * rspCode : 0
     * rspMsg : 操作成功
     */

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
        /**
         * start : 0
         * pageIndex : 1
         * pageSize : 15
         * pageCount : 1
         * totalCount : 1
         * datas : [{"id":1,"gmtCreate":1503646455000,"gmtModified":1503646458000,"creator":null,"modifier":null,"name":"111","typeId":2,"businessStart":0,"businessEnd":3600,"longitude":"","latitude":null,"elevation":null,"floor":null,"number":null,"address":null,"province":null,"provinceCode":null,"city":null,"cityCode":null,"area":null,"areaCode":null,"individualitySignature":null,"labelNames":null,"labelIds":null,"statusIds":null,"photoId":null,"photoUrl":null,"phone":null,"telephone":null,"grade":null,"webchatNum":null,"subjectId":null,"subjectTrainId":null,"subjectNumber":null,"enable":1,"distance":null,"complete":null,"typeName":null,"isTrain":null,"inferiorsCount":null}]
         */

        private int start;
        private int pageIndex;
        private int pageSize;
        private int pageCount;
        private int totalCount;
        private List<MainHomeListItem> datas;

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

        public List<MainHomeListItem> getDatas() {
            return datas;
        }

        public void setDatas(List<MainHomeListItem> datas) {
            this.datas = datas;
        }

    }
}

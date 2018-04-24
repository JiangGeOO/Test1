package com.yidao.threekmo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017\8\25 0025.
 */

public class SecondlyResultBean implements Serializable {

    /**
     * data : {"start":0,"pageIndex":1,"pageSize":15,"pageCount":1,"totalCount":1,"datas":[{"id":16,"gmtCreate":1504070189000,"gmtModified":1504070189000,"creator":null,"modifier":null,"name":"小猫店户","typeId":33,"businessStart":2160,"businessEnd":8280,"longitude":"120.208257","latitude":"30.182745","elevation":"46.86","floor":"25楼","number":"a座","address":"信誉距离图距离","province":"浙江省","provinceCode":null,"city":"杭州市","cityCode":null,"area":"滨江区","areaCode":null,"individualitySignature":"主体描述","labelNames":null,"labelIds":",29,30,","statusIds":null,"photoId":null,"photoUrl":"http://tkm.oss-cn-hangzhou.aliyuncs.com/454839ED-1614-4E06-9739-40B18A20E7AA.jpg","phone":"1236888788","telephone":null,"grade":5,"webchatNum":null,"subjectId":12,"subjectTrainId":null,"subjectNumber":2,"enable":1,"distance":538,"complete":null,"typeName":null,"isTrain":1,"inferiorsCount":null,"secondaryImage":null,"subjectName":null}]}
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
         * datas : [{"id":16,"gmtCreate":1504070189000,"gmtModified":1504070189000,"creator":null,"modifier":null,"name":"小猫店户","typeId":33,"businessStart":2160,"businessEnd":8280,"longitude":"120.208257","latitude":"30.182745","elevation":"46.86","floor":"25楼","number":"a座","address":"信誉距离图距离","province":"浙江省","provinceCode":null,"city":"杭州市","cityCode":null,"area":"滨江区","areaCode":null,"individualitySignature":"主体描述","labelNames":null,"labelIds":",29,30,","statusIds":null,"photoId":null,"photoUrl":"http://tkm.oss-cn-hangzhou.aliyuncs.com/454839ED-1614-4E06-9739-40B18A20E7AA.jpg","phone":"1236888788","telephone":null,"grade":5,"webchatNum":null,"subjectId":12,"subjectTrainId":null,"subjectNumber":2,"enable":1,"distance":538,"complete":null,"typeName":null,"isTrain":1,"inferiorsCount":null,"secondaryImage":null,"subjectName":null}]
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

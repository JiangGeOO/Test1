package com.yidao.threekmo.bean;

import android.graphics.Color;

import com.ijustyce.fastkotlin.irecyclerview.IResponseData;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/28.
 */

public class MyRedBoxListBean implements Serializable, IResponseData<MyRedBoxListBean.RedPocketBean> {

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

    @NotNull
    @Override
    public ArrayList<RedPocketBean> getList() {
        return data == null || data.datas == null ? new ArrayList<RedPocketBean>() : data.datas;
    }

    public static class RedPocketBean {
        public int id;
        public long gmtCreate;
        public long gmtModified;
        public int userId;
        public String amount;
        public String withdrawId;
        public String type;

        public String date() {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format.format(new Date(gmtCreate));
        }

        public String getAmount() {
            if (amount.startsWith("-")) return amount;
            return "+" + amount;
        }

        public String getReason() {
            if (type == null) return "网星投票红包收入";
            switch (type) {
                case "withdraw":
                    return "提现";
                case "tpAcquire":
                    return "网星投票红包收入";
                default:
                    return "网星投票支付";
            }
        }

        public int moneyColor() {
            if (amount.startsWith("-")) return Color.parseColor("#4c4c4c");
            return Color.parseColor("#14c47e");
        }
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
        private ArrayList<RedPocketBean> datas;

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

        public List<RedPocketBean> getDatas() {
            return datas;
        }

        public void setDatas(ArrayList<RedPocketBean> datas) {
            this.datas = datas;
        }

    }
}

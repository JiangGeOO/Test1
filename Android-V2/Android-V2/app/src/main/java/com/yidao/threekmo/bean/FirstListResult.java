package com.yidao.threekmo.bean;

import com.ijustyce.fastkotlin.irecyclerview.IResponseData;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017\8\23 0023.
 */

public class FirstListResult implements Serializable, IResponseData<MainHomeListItem> {

    /**
     * data : {"start":0,"pageIndex":1,"pageSize":15,"pageCount":1,"totalCount":11,"datas":[{"id":8,"gmtCreate":1503429953000,"gmtModified":1503429953000,"creator":null,"modifier":null,"name":"11","typeId":1,"businessStart":1,"businessEnd":1,"longitude":"1","latitude":"1","elevation":null,"floor":null,"number":null,"address":"1","province":"1","provinceCode":null,"city":"1","cityCode":null,"area":"1","areaCode":null,"individualitySignature":"1","labelNames":null,"labelIds":",11,12,","statusIds":null,"photoId":6,"photoUrl":null,"phone":null,"telephone":null,"grade":null,"webchatNum":null,"subjectId":null,"subjectTrainId":null,"subjectNumber":null,"enable":1,"distance":157426},{"id":9,"gmtCreate":1503430166000,"gmtModified":1503430166000,"creator":null,"modifier":null,"name":"11","typeId":1,"businessStart":1,"businessEnd":1,"longitude":"1","latitude":"1","elevation":null,"floor":null,"number":null,"address":"1","province":"1","provinceCode":null,"city":"1","cityCode":null,"area":"1","areaCode":null,"individualitySignature":"1","labelNames":null,"labelIds":",11,12,","statusIds":null,"photoId":7,"photoUrl":null,"phone":null,"telephone":null,"grade":null,"webchatNum":null,"subjectId":null,"subjectTrainId":null,"subjectNumber":null,"enable":1,"distance":157426},{"id":10,"gmtCreate":1503472981000,"gmtModified":1503472981000,"creator":null,"modifier":null,"name":"Shy","typeId":5,"businessStart":0,"businessEnd":3960,"longitude":"0","latitude":"37.785834","elevation":null,"floor":null,"number":null,"address":"Werwer","province":"北京","provinceCode":null,"city":"通州","cityCode":null,"area":null,"areaCode":null,"individualitySignature":"Were","labelNames":null,"labelIds":",8,6,","statusIds":null,"photoId":13,"photoUrl":"201708/623ec8fb5a90411196820549b2f261ed_828x549_42838.jpg","phone":null,"telephone":null,"grade":null,"webchatNum":null,"subjectId":null,"subjectTrainId":null,"subjectNumber":null,"enable":1,"distance":4206300},{"id":11,"gmtCreate":1503473387000,"gmtModified":1503473387000,"creator":null,"modifier":null,"name":"34534","typeId":5,"businessStart":0,"businessEnd":0,"longitude":"0","latitude":"37.785834","elevation":null,"floor":null,"number":null,"address":null,"province":null,"provinceCode":null,"city":null,"cityCode":null,"area":null,"areaCode":null,"individualitySignature":null,"labelNames":null,"labelIds":null,"statusIds":null,"photoId":16,"photoUrl":null,"phone":null,"telephone":null,"grade":null,"webchatNum":null,"subjectId":null,"subjectTrainId":null,"subjectNumber":null,"enable":1,"distance":4206300},{"id":12,"gmtCreate":1503473812000,"gmtModified":1503473812000,"creator":null,"modifier":null,"name":"We're","typeId":5,"businessStart":0,"businessEnd":4320,"longitude":"0","latitude":"37.785834","elevation":null,"floor":null,"number":null,"address":"We're","province":"北京","provinceCode":null,"city":"通州","cityCode":null,"area":null,"areaCode":null,"individualitySignature":"Sdfasdfsd","labelNames":null,"labelIds":",5,2,","statusIds":null,"photoId":17,"photoUrl":"201708/999e56a43f9849cbbf6d620392c1fc77_828x552_39515.jpg","phone":null,"telephone":null,"grade":null,"webchatNum":null,"subjectId":null,"subjectTrainId":null,"subjectNumber":null,"enable":1,"distance":4206300},{"id":13,"gmtCreate":1503473916000,"gmtModified":1503473916000,"creator":null,"modifier":null,"name":"Shuts","typeId":5,"businessStart":0,"businessEnd":360,"longitude":"0","latitude":"37.785834","elevation":null,"floor":null,"number":null,"address":"Ewrqefdrsf","province":"重庆","provinceCode":null,"city":"开县","cityCode":null,"area":null,"areaCode":null,"individualitySignature":"Qwerewfds","labelNames":null,"labelIds":",4,7,2,","statusIds":null,"photoId":18,"photoUrl":"201708/dda0a1fe2b4c43ea8ad2584d233f1938_450x800_24754.jpg","phone":null,"telephone":null,"grade":null,"webchatNum":null,"subjectId":null,"subjectTrainId":null,"subjectNumber":null,"enable":1,"distance":4206300},{"id":14,"gmtCreate":1503473945000,"gmtModified":1503473945000,"creator":null,"modifier":null,"name":"Shuts","typeId":5,"businessStart":0,"businessEnd":360,"longitude":"0","latitude":"37.785834","elevation":null,"floor":null,"number":null,"address":"Ewrqefdrsf","province":"重庆","provinceCode":null,"city":"开县","cityCode":null,"area":null,"areaCode":null,"individualitySignature":"Qwerewfds","labelNames":null,"labelIds":",4,7,2,","statusIds":null,"photoId":19,"photoUrl":"201708/3bc6a65be11c4ea0afd9727af3547be8_828x549_45240.jpg","phone":null,"telephone":null,"grade":null,"webchatNum":null,"subjectId":null,"subjectTrainId":null,"subjectNumber":null,"enable":1,"distance":4206300},{"id":15,"gmtCreate":1503474639000,"gmtModified":1503474639000,"creator":null,"modifier":null,"name":"Werwer","typeId":5,"businessStart":0,"businessEnd":8280,"longitude":"0","latitude":"37.785834","elevation":null,"floor":null,"number":null,"address":null,"province":null,"provinceCode":null,"city":null,"cityCode":null,"area":null,"areaCode":null,"individualitySignature":null,"labelNames":null,"labelIds":null,"statusIds":null,"photoId":22,"photoUrl":null,"phone":null,"telephone":null,"grade":null,"webchatNum":null,"subjectId":null,"subjectTrainId":null,"subjectNumber":null,"enable":1,"distance":4206300},{"id":16,"gmtCreate":1503474790000,"gmtModified":1503474790000,"creator":null,"modifier":null,"name":"Wrwerwe","typeId":5,"businessStart":0,"businessEnd":1080,"longitude":"0","latitude":"37.785834","elevation":null,"floor":null,"number":null,"address":null,"province":"北京","provinceCode":null,"city":"通州","cityCode":null,"area":null,"areaCode":null,"individualitySignature":null,"labelNames":null,"labelIds":null,"statusIds":null,"photoId":23,"photoUrl":null,"phone":null,"telephone":null,"grade":null,"webchatNum":null,"subjectId":null,"subjectTrainId":null,"subjectNumber":null,"enable":1,"distance":4206300},{"id":17,"gmtCreate":1503475451000,"gmtModified":1503475451000,"creator":null,"modifier":null,"name":"Wead","typeId":5,"businessStart":null,"businessEnd":null,"longitude":"0","latitude":"37.785834","elevation":null,"floor":null,"number":null,"address":null,"province":null,"provinceCode":null,"city":null,"cityCode":null,"area":null,"areaCode":null,"individualitySignature":null,"labelNames":null,"labelIds":null,"statusIds":null,"photoId":24,"photoUrl":"201708/92889f6a33f346f2a62b17291df6b883_828x549_42838.jpg","phone":null,"telephone":null,"grade":null,"webchatNum":null,"subjectId":null,"subjectTrainId":null,"subjectNumber":null,"enable":1,"distance":4206300},{"id":6,"gmtCreate":1503412752000,"gmtModified":1503430640000,"creator":null,"modifier":null,"name":"123123121","typeId":5,"businessStart":0,"businessEnd":3600,"longitude":"120.218884","latitude":"30.186326","elevation":null,"floor":null,"number":null,"address":"1111","province":"浙江","provinceCode":null,"city":"杭州","cityCode":null,"area":null,"areaCode":null,"individualitySignature":"Queerest","labelNames":null,"labelIds":",1,2,3,4,","statusIds":null,"photoId":null,"photoUrl":null,"phone":null,"telephone":null,"grade":null,"webchatNum":null,"subjectId":null,"subjectTrainId":null,"subjectNumber":null,"enable":1,"distance":12889545}]}
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

    @NotNull
    @Override
    public ArrayList<MainHomeListItem> getList() {
        return data == null || data.datas == null ? new ArrayList<MainHomeListItem>() : data.datas;
    }

    public static class DataBean {
        /**
         * start : 0
         * pageIndex : 1
         * pageSize : 15
         * pageCount : 1
         * totalCount : 11
         * datas : [{"id":8,"gmtCreate":1503429953000,"gmtModified":1503429953000,"creator":null,"modifier":null,"name":"11","typeId":1,"businessStart":1,"businessEnd":1,"longitude":"1","latitude":"1","elevation":null,"floor":null,"number":null,"address":"1","province":"1","provinceCode":null,"city":"1","cityCode":null,"area":"1","areaCode":null,"individualitySignature":"1","labelNames":null,"labelIds":",11,12,","statusIds":null,"photoId":6,"photoUrl":null,"phone":null,"telephone":null,"grade":null,"webchatNum":null,"subjectId":null,"subjectTrainId":null,"subjectNumber":null,"enable":1,"distance":157426},{"id":9,"gmtCreate":1503430166000,"gmtModified":1503430166000,"creator":null,"modifier":null,"name":"11","typeId":1,"businessStart":1,"businessEnd":1,"longitude":"1","latitude":"1","elevation":null,"floor":null,"number":null,"address":"1","province":"1","provinceCode":null,"city":"1","cityCode":null,"area":"1","areaCode":null,"individualitySignature":"1","labelNames":null,"labelIds":",11,12,","statusIds":null,"photoId":7,"photoUrl":null,"phone":null,"telephone":null,"grade":null,"webchatNum":null,"subjectId":null,"subjectTrainId":null,"subjectNumber":null,"enable":1,"distance":157426},{"id":10,"gmtCreate":1503472981000,"gmtModified":1503472981000,"creator":null,"modifier":null,"name":"Shy","typeId":5,"businessStart":0,"businessEnd":3960,"longitude":"0","latitude":"37.785834","elevation":null,"floor":null,"number":null,"address":"Werwer","province":"北京","provinceCode":null,"city":"通州","cityCode":null,"area":null,"areaCode":null,"individualitySignature":"Were","labelNames":null,"labelIds":",8,6,","statusIds":null,"photoId":13,"photoUrl":"201708/623ec8fb5a90411196820549b2f261ed_828x549_42838.jpg","phone":null,"telephone":null,"grade":null,"webchatNum":null,"subjectId":null,"subjectTrainId":null,"subjectNumber":null,"enable":1,"distance":4206300},{"id":11,"gmtCreate":1503473387000,"gmtModified":1503473387000,"creator":null,"modifier":null,"name":"34534","typeId":5,"businessStart":0,"businessEnd":0,"longitude":"0","latitude":"37.785834","elevation":null,"floor":null,"number":null,"address":null,"province":null,"provinceCode":null,"city":null,"cityCode":null,"area":null,"areaCode":null,"individualitySignature":null,"labelNames":null,"labelIds":null,"statusIds":null,"photoId":16,"photoUrl":null,"phone":null,"telephone":null,"grade":null,"webchatNum":null,"subjectId":null,"subjectTrainId":null,"subjectNumber":null,"enable":1,"distance":4206300},{"id":12,"gmtCreate":1503473812000,"gmtModified":1503473812000,"creator":null,"modifier":null,"name":"We're","typeId":5,"businessStart":0,"businessEnd":4320,"longitude":"0","latitude":"37.785834","elevation":null,"floor":null,"number":null,"address":"We're","province":"北京","provinceCode":null,"city":"通州","cityCode":null,"area":null,"areaCode":null,"individualitySignature":"Sdfasdfsd","labelNames":null,"labelIds":",5,2,","statusIds":null,"photoId":17,"photoUrl":"201708/999e56a43f9849cbbf6d620392c1fc77_828x552_39515.jpg","phone":null,"telephone":null,"grade":null,"webchatNum":null,"subjectId":null,"subjectTrainId":null,"subjectNumber":null,"enable":1,"distance":4206300},{"id":13,"gmtCreate":1503473916000,"gmtModified":1503473916000,"creator":null,"modifier":null,"name":"Shuts","typeId":5,"businessStart":0,"businessEnd":360,"longitude":"0","latitude":"37.785834","elevation":null,"floor":null,"number":null,"address":"Ewrqefdrsf","province":"重庆","provinceCode":null,"city":"开县","cityCode":null,"area":null,"areaCode":null,"individualitySignature":"Qwerewfds","labelNames":null,"labelIds":",4,7,2,","statusIds":null,"photoId":18,"photoUrl":"201708/dda0a1fe2b4c43ea8ad2584d233f1938_450x800_24754.jpg","phone":null,"telephone":null,"grade":null,"webchatNum":null,"subjectId":null,"subjectTrainId":null,"subjectNumber":null,"enable":1,"distance":4206300},{"id":14,"gmtCreate":1503473945000,"gmtModified":1503473945000,"creator":null,"modifier":null,"name":"Shuts","typeId":5,"businessStart":0,"businessEnd":360,"longitude":"0","latitude":"37.785834","elevation":null,"floor":null,"number":null,"address":"Ewrqefdrsf","province":"重庆","provinceCode":null,"city":"开县","cityCode":null,"area":null,"areaCode":null,"individualitySignature":"Qwerewfds","labelNames":null,"labelIds":",4,7,2,","statusIds":null,"photoId":19,"photoUrl":"201708/3bc6a65be11c4ea0afd9727af3547be8_828x549_45240.jpg","phone":null,"telephone":null,"grade":null,"webchatNum":null,"subjectId":null,"subjectTrainId":null,"subjectNumber":null,"enable":1,"distance":4206300},{"id":15,"gmtCreate":1503474639000,"gmtModified":1503474639000,"creator":null,"modifier":null,"name":"Werwer","typeId":5,"businessStart":0,"businessEnd":8280,"longitude":"0","latitude":"37.785834","elevation":null,"floor":null,"number":null,"address":null,"province":null,"provinceCode":null,"city":null,"cityCode":null,"area":null,"areaCode":null,"individualitySignature":null,"labelNames":null,"labelIds":null,"statusIds":null,"photoId":22,"photoUrl":null,"phone":null,"telephone":null,"grade":null,"webchatNum":null,"subjectId":null,"subjectTrainId":null,"subjectNumber":null,"enable":1,"distance":4206300},{"id":16,"gmtCreate":1503474790000,"gmtModified":1503474790000,"creator":null,"modifier":null,"name":"Wrwerwe","typeId":5,"businessStart":0,"businessEnd":1080,"longitude":"0","latitude":"37.785834","elevation":null,"floor":null,"number":null,"address":null,"province":"北京","provinceCode":null,"city":"通州","cityCode":null,"area":null,"areaCode":null,"individualitySignature":null,"labelNames":null,"labelIds":null,"statusIds":null,"photoId":23,"photoUrl":null,"phone":null,"telephone":null,"grade":null,"webchatNum":null,"subjectId":null,"subjectTrainId":null,"subjectNumber":null,"enable":1,"distance":4206300},{"id":17,"gmtCreate":1503475451000,"gmtModified":1503475451000,"creator":null,"modifier":null,"name":"Wead","typeId":5,"businessStart":null,"businessEnd":null,"longitude":"0","latitude":"37.785834","elevation":null,"floor":null,"number":null,"address":null,"province":null,"provinceCode":null,"city":null,"cityCode":null,"area":null,"areaCode":null,"individualitySignature":null,"labelNames":null,"labelIds":null,"statusIds":null,"photoId":24,"photoUrl":"201708/92889f6a33f346f2a62b17291df6b883_828x549_42838.jpg","phone":null,"telephone":null,"grade":null,"webchatNum":null,"subjectId":null,"subjectTrainId":null,"subjectNumber":null,"enable":1,"distance":4206300},{"id":6,"gmtCreate":1503412752000,"gmtModified":1503430640000,"creator":null,"modifier":null,"name":"123123121","typeId":5,"businessStart":0,"businessEnd":3600,"longitude":"120.218884","latitude":"30.186326","elevation":null,"floor":null,"number":null,"address":"1111","province":"浙江","provinceCode":null,"city":"杭州","cityCode":null,"area":null,"areaCode":null,"individualitySignature":"Queerest","labelNames":null,"labelIds":",1,2,3,4,","statusIds":null,"photoId":null,"photoUrl":null,"phone":null,"telephone":null,"grade":null,"webchatNum":null,"subjectId":null,"subjectTrainId":null,"subjectNumber":null,"enable":1,"distance":12889545}]
         */

        private int start;
        private int pageIndex;
        private int pageSize;
        private int pageCount;
        private int totalCount;
        private ArrayList<MainHomeListItem> datas;

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

        public ArrayList<MainHomeListItem> getDatas() {
            return datas;
        }

        public void setDatas(ArrayList<MainHomeListItem> datas) {
            this.datas = datas;
        }


    }
}

package com.yidao.threekmo.bean;

import java.util.List;

/**
 * Created by Administrator on 2017\9\5 0005.
 */


//楼栋选择
public class ChooseSecondListBean {

    private int rspCode;
    private String rspMsg;
    private List<MainHomeListItem> data;


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

    public List<MainHomeListItem> getData() {
        return data;
    }

    public void setData(List<MainHomeListItem> data) {
        this.data = data;
    }
}

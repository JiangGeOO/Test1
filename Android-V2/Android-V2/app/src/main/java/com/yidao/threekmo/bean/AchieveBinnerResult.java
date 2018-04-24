package com.yidao.threekmo.bean;

import com.ijustyce.fastkotlin.base.BaseViewModel;
import com.ijustyce.fastkotlin.irecyclerview.IResponseData;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Smart~ on 2017/11/10.
 */

public class AchieveBinnerResult implements Serializable, IResponseData<AchieveBinnerResult.DataBean> {

    private int rspCode;
    private String rspMsg;
    private ArrayList<DataBean> data;

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
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

    @NotNull
    @Override
    public ArrayList<DataBean> getList() {
        return data;
    }

    public void setRspMsg(String rspMsg) {
        this.rspMsg = rspMsg;
    }

    public static class DataBean extends BaseViewModel {

        private long id;
        private String photo;
        private String url;
        private String type;

        public int paddingLeft() {
            return getPosition() == 0 ? 30 : 0;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}

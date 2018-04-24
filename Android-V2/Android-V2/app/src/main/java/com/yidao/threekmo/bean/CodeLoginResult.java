package com.yidao.threekmo.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017\8\25 0025.
 */

public class CodeLoginResult implements Serializable {

    /**
     * data : {"mobile":"15757855231","init":true,"type":3,"TkmUserInfo":{"id":15,"gmtCreate":1504086331000,"gmtModified":1504086331000,"creator":null,"modifier":null,"userId":15,"nickname":null,"individualitySignature":null,"face":null,"grade":null,"webchatNum":null,"qqNum":null,"birthday":null,"province":null,"provinceCode":null,"city":null,"cityCode":null,"area":null,"areaCode":null,"height":null,"weight":null,"profession":null,"interest":null,"name":null,"card":null,"phone":"15757855231","phoneAuth":null,"invitationCode":null,"invitationSUserId":null,"enable":1,"sex":null,"notification":0,"scope":0},"token":"9480c7d5-29ae-4fe9-8ed5-83923c735754"}
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
         * mobile : 15757855231
         * init : true
         * type : 3
         * TkmUserInfo : {"id":15,"gmtCreate":1504086331000,"gmtModified":1504086331000,"creator":null,"modifier":null,"userId":15,"nickname":null,"individualitySignature":null,"face":null,"grade":null,"webchatNum":null,"qqNum":null,"birthday":null,"province":null,"provinceCode":null,"city":null,"cityCode":null,"area":null,"areaCode":null,"height":null,"weight":null,"profession":null,"interest":null,"name":null,"card":null,"phone":"15757855231","phoneAuth":null,"invitationCode":null,"invitationSUserId":null,"enable":1,"sex":null,"notification":0,"scope":0}
         * token : 9480c7d5-29ae-4fe9-8ed5-83923c735754
         */

        private String mobile;
        private boolean init;
        private int type;
        private UserBean TkmUserInfo;
        private String token;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public boolean isInit() {
            return init;
        }

        public void setInit(boolean init) {
            this.init = init;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public UserBean getTkmUserInfo() {
            return TkmUserInfo;
        }

        public void setTkmUserInfo(UserBean TkmUserInfo) {
            this.TkmUserInfo = TkmUserInfo;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

    }
}

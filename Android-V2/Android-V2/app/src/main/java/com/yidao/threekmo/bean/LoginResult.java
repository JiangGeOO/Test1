package com.yidao.threekmo.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017\8\23 0023.
 */

public class LoginResult implements Serializable {

    /**
     * data : {"success":true,"data":{"token":"62b37049-c81f-49b3-ab1a-c1cd12526a2a","is_pwd":false,"user":{"id":6,"gmtCreate":1503626692000,"gmtModified":1503626692000,"creator":null,"modifier":null,"userId":6,"nickname":null,"individualitySignature":null,"face":null,"grade":null,"webchatNum":null,"qqNum":null,"birthday":null,"province":null,"provinceCode":null,"city":null,"cityCode":null,"area":null,"areaCode":null,"height":null,"weight":null,"profession":null,"interest":null,"name":null,"card":null,"phone":"15757855231","phoneAuth":null,"invitationCode":null,"invitationSUserId":null,"enable":1,"sex":null,"notification":0,"scope":0}},"errorCode":0,"errorMsg":null}
     * rspCode : 0
     * rspMsg : 操作成功
     */

    private DataBeanX data;
    private int rspCode;
    private String rspMsg;

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
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

    public static class DataBeanX {
        /**
         * success : true
         * data : {"token":"62b37049-c81f-49b3-ab1a-c1cd12526a2a","is_pwd":false,"user":{"id":6,"gmtCreate":1503626692000,"gmtModified":1503626692000,"creator":null,"modifier":null,"userId":6,"nickname":null,"individualitySignature":null,"face":null,"grade":null,"webchatNum":null,"qqNum":null,"birthday":null,"province":null,"provinceCode":null,"city":null,"cityCode":null,"area":null,"areaCode":null,"height":null,"weight":null,"profession":null,"interest":null,"name":null,"card":null,"phone":"15757855231","phoneAuth":null,"invitationCode":null,"invitationSUserId":null,"enable":1,"sex":null,"notification":0,"scope":0}}
         * errorCode : 0
         * errorMsg : null
         */

        private boolean success;
        private DataBean data;
        private int errorCode;
        private String errorMsg;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public static class DataBean {
            /**
             * token : 62b37049-c81f-49b3-ab1a-c1cd12526a2a
             * is_pwd : false
             * user : {"id":6,"gmtCreate":1503626692000,"gmtModified":1503626692000,"creator":null,"modifier":null,"userId":6,"nickname":null,"individualitySignature":null,"face":null,"grade":null,"webchatNum":null,"qqNum":null,"birthday":null,"province":null,"provinceCode":null,"city":null,"cityCode":null,"area":null,"areaCode":null,"height":null,"weight":null,"profession":null,"interest":null,"name":null,"card":null,"phone":"15757855231","phoneAuth":null,"invitationCode":null,"invitationSUserId":null,"enable":1,"sex":null,"notification":0,"scope":0}
             */

            private String token;
            private boolean is_pwd;
            private UserBean user;

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public boolean isIs_pwd() {
                return is_pwd;
            }

            public void setIs_pwd(boolean is_pwd) {
                this.is_pwd = is_pwd;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

        }
    }
}

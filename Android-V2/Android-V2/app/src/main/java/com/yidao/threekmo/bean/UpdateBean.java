package com.yidao.threekmo.bean;

/**
 * Created by Administrator on 2017\9\26 0026.
 */

public class UpdateBean {

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

        private String ossTailDive;
        private String versionDisplay;
        public int button;
        public int button1;
        private boolean audit;
        private String force;
        private int version;
        private String content;
        private String url;
        private int advertising;
        public boolean isWithdraw;
        public boolean isNearby;
        public boolean drogue;  //  是否显示中奖攻略

        public int getAdvertising() {
            return advertising;
        }

        public void setAdvertising(int advertising) {
            this.advertising = advertising;
        }

        public String getOssTailDive() {
            return ossTailDive;
        }

        public void setOssTailDive(String ossTailDive) {
            this.ossTailDive = ossTailDive;
        }

        public String getVersionDisplay() {
            return versionDisplay;
        }

        public void setVersionDisplay(String versionDisplay) {
            this.versionDisplay = versionDisplay;
        }

        public boolean getAudit() {
            return audit;
        }

        public void setAudit(boolean audit) {
            this.audit = audit;
        }

        public String getForce() {
            return force;
        }

        public void setForce(String force) {
            this.force = force;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}

package com.yidao.threekmo.bean;

import java.io.Serializable;

/**
 * Created by Smart~ on 2017/11/28.
 */

public class DialogSecondResult implements Serializable {

    private DialogDetailResult candidate;
    private String url;
    private int showcasing;
    public String desc, title, fenxiang;

    public DialogDetailResult getCandidate() {
        return candidate;
    }

    public void setCandidate(DialogDetailResult candidate) {
        this.candidate = candidate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getShowcasing() {
        return showcasing;
    }

    public void setShowcasing(int showcasing) {
        this.showcasing = showcasing;
    }
}

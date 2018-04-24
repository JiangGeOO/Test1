package com.yidao.threekmo.bean;

import java.io.Serializable;

/**
 * Created by Smart~ on 2017/11/15.
 */

public class ExperiencePhotoXyResult implements Serializable {

    private long id;//id
    private long gmtCreate;//创建时间
    private long gmtModified;//修改时间
    private String creator;//创建人
    private String modifier;//修改人
    private long tyPhotoId;//图片id
    private double xshaft;//x轴
    private double yShaft;//y轴
    private String merchandiseId;//商品id
    private int enable;//作废标志
    private long experienceId;//体验id

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public long getTyPhotoId() {
        return tyPhotoId;
    }

    public void setTyPhotoId(long tyPhotoId) {
        this.tyPhotoId = tyPhotoId;
    }

    public double getXshaft() {
        return xshaft;
    }

    public void setXshaft(double xshaft) {
        this.xshaft = xshaft;
    }

    public double getyShaft() {
        return yShaft;
    }

    public void setyShaft(double yShaft) {
        this.yShaft = yShaft;
    }

    public String getMerchandiseId() {
        return merchandiseId;
    }

    public void setMerchandiseId(String merchandiseId) {
        this.merchandiseId = merchandiseId;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public long getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(long experienceId) {
        this.experienceId = experienceId;
    }
}

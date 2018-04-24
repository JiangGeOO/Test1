package com.yidao.threekmo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Smart~ on 2017/11/15.
 */

public class ExperiencePhotoListResult implements Serializable{

    private long id;//id
    private long gmtCreate;//创建时间
    private long gmtModified;//修改时间
    private String creator;//创建人
    private String modifier;//修改人
    private long experienceId;//体验店id
    private String photoUrl;//图片链接
    private int enable;//作废标志（1：正常 2：作废）
    private String pixels;
    private List<ExperiencePhotoXyResult> coordinateList;//所有图片路径

    public String getPixels() {
        return pixels;
    }

    public void setPixels(String pixels) {
        this.pixels = pixels;
    }

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

    public long getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(long experienceId) {
        this.experienceId = experienceId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public List<ExperiencePhotoXyResult> getCoordinateList() {
        return coordinateList;
    }

    public void setCoordinateList(List<ExperiencePhotoXyResult> coordinateList) {
        this.coordinateList = coordinateList;
    }
}

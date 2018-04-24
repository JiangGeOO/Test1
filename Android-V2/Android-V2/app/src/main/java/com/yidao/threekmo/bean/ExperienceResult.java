package com.yidao.threekmo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Smart~ on 2017/11/15.
 */

public class ExperienceResult implements Serializable {

    private long id;//id
    private long gmtCreate;//创建时间
    private long gmtModified;//修改时间
    private String creator;//创建人
    private String modifier;//修改人
    private String name;//活动名称
    private String frontCover;//封面图片url
    private String longitude;//经度
    private String latitude;//纬度
    private String address;//地址
    private String province;//省
    private String provinceCode;//省code
    private String city;//市
    private String cityCode;//市code
    private String area;//区
    private String areaCode;//区code
    private String labelId;//标签id集合
    private String profile;//简介
    private long pageView;//浏览量
    private int enable;//作废标志（1：正常 2：作废）
    private long sequencing;//排序
    private long subjectTrainId;//归属主体id
    private String labelNames;
    private List<ExperiencePhotoListResult> photoList;//所有图片集合
    private Long isPanorama;//是否是全景(0:不是，1:是)
    private String jumpUrl;//是全景图，跳转的URL
    private int coolect;//是否收藏
    private long beCollectId;//体验id
    private long soldOut;//是否下架
    private int isPlane;    //  是否平面    1 for yes 0 for no
    public String distance;

    public int getIsPlane() {
        return isPlane;
    }

    public void setIsPlane(int isPlane) {
        this.isPlane = isPlane;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public long getSoldOut() {
        return soldOut;
    }

    public void setSoldOut(long soldOut) {
        this.soldOut = soldOut;
    }

    public long getBeCollectId() {
        return beCollectId;
    }

    public void setBeCollectId(long beCollectId) {
        this.beCollectId = beCollectId;
    }

    public int getCoolect() {
        return coolect;
    }

    public void setCoolect(int coolect) {
        this.coolect = coolect;
    }

    public Long getIsPanorama() {
        return isPanorama;
    }

    public void setIsPanorama(Long isPanorama) {
        this.isPanorama = isPanorama;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrontCover() {
        return frontCover;
    }

    public void setFrontCover(String frontCover) {
        this.frontCover = frontCover;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public long getPageView() {
        return pageView;
    }

    public void setPageView(long pageView) {
        this.pageView = pageView;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public long getSequencing() {
        return sequencing;
    }

    public void setSequencing(long sequencing) {
        this.sequencing = sequencing;
    }

    public long getSubjectTrainId() {
        return subjectTrainId;
    }

    public void setSubjectTrainId(long subjectTrainId) {
        this.subjectTrainId = subjectTrainId;
    }

    public String getLabelNames() {
        return labelNames;
    }

    public void setLabelNames(String labelNames) {
        this.labelNames = labelNames;
    }

    public List<ExperiencePhotoListResult> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<ExperiencePhotoListResult> photoList) {
        this.photoList = photoList;
    }
}

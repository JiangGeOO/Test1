package com.yidao.threekmo.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017\8\21 0021.
 * 次级主体item
 */

public class SecondaryListItem implements Serializable {
    private Long id; // 主键
    private long gmtCreate; // 创建时间
    private long gmtModified; // 修改时间
    private String creator; // 创建者
    private String modifier; // 修改者
    private String name; // 名称
    private Long typeId; // 类别主键
    private Long businessStart; // 营业开始时间
    private Long businessEnd; // 营业结束时间
    private String longitude; // 经度
    private String latitude; // 纬度
    private String elevation; // 海拔
    private String floor; // 楼层
    private String number; // 序号
    private String address; // 地址
    private String province; // 省
    private String provinceCode; // 省code
    private String city; // 市
    private String cityCode; // 市code
    private String area; // 区
    private String areaCode; // 区code
    private String individualitySignature; // 描述
    private String labelNames; // 标签名称
    private String labelIds; // 标签主键
    private String statusIds; // 状态主键
    private Long photoId; // 图片主键
    private String photoUrl; // 相册地址
    private String phone; // 手机号码
    private String telephone; // 电话号码
    private double grade; // 评分
    private String webchatNum; // 微信号
    private Long subjectId; // 归属主键
    private Long subjectTrainId; // 归属次主键
    private Long subjectNumber; // 主体序号:2即开始
    private Long enable=1L; // 是否启用
    private Long distance;	//距离
    private String complete;//是否成功  YES成功  NO失败
    private String typeName;//type名称
    private Long isTrain;//最底层
    private Long inferiorsCount;//下一级数据量

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getBusinessStart() {
        return businessStart;
    }

    public void setBusinessStart(Long businessStart) {
        this.businessStart = businessStart;
    }

    public Long getBusinessEnd() {
        return businessEnd;
    }

    public void setBusinessEnd(Long businessEnd) {
        this.businessEnd = businessEnd;
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

    public String getElevation() {
        return elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getIndividualitySignature() {
        return individualitySignature;
    }

    public void setIndividualitySignature(String individualitySignature) {
        this.individualitySignature = individualitySignature;
    }

    public String getLabelNames() {
        return labelNames;
    }

    public void setLabelNames(String labelNames) {
        this.labelNames = labelNames;
    }

    public String getLabelIds() {
        return labelIds;
    }

    public void setLabelIds(String labelIds) {
        this.labelIds = labelIds;
    }

    public String getStatusIds() {
        return statusIds;
    }

    public void setStatusIds(String statusIds) {
        this.statusIds = statusIds;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getWebchatNum() {
        return webchatNum;
    }

    public void setWebchatNum(String webchatNum) {
        this.webchatNum = webchatNum;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getSubjectTrainId() {
        return subjectTrainId;
    }

    public void setSubjectTrainId(Long subjectTrainId) {
        this.subjectTrainId = subjectTrainId;
    }

    public Long getSubjectNumber() {
        return subjectNumber;
    }

    public void setSubjectNumber(Long subjectNumber) {
        this.subjectNumber = subjectNumber;
    }

    public Long getEnable() {
        return enable;
    }

    public void setEnable(Long enable) {
        this.enable = enable;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getIsTrain() {
        return isTrain;
    }

    public void setIsTrain(Long isTrain) {
        this.isTrain = isTrain;
    }

    public Long getInferiorsCount() {
        return inferiorsCount;
    }

    public void setInferiorsCount(Long inferiorsCount) {
        this.inferiorsCount = inferiorsCount;
    }
}

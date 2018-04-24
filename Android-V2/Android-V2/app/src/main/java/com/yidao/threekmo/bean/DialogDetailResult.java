package com.yidao.threekmo.bean;

import java.io.Serializable;

/**
 * Created by Smart~ on 2017/11/28.
 */

public class DialogDetailResult implements Serializable {

    private long id;//
    private String creator;
    private String modifier;
    private long  gmtCreate;//创建时间
    private long gmtModified;//修改时间
    private long activityId;//活动id
    private long number;//活动编号
    private String name;//候选人姓名
    private String photo;//候选人照片
    private String job;//职务
    private String manifesto;//候选人宣言
    private String intro;//候选人简介
    private long vote;//得票数
    private long enable;//是否被锁定（0：锁定 无法的票  1：未锁定）
    private long candidateSum;//候选人总数
    private long voteSum;// 累计投票
    private long voterSum;//投票人数
    private long activityStart;//投票开始时间
    private long activityEnd;//投票结束时间
    private String type;//候选人归属
    private long pageviews;//浏览量
    private int isPackaging;//是否包装
    private String province;//省
    private String provinceCode;//省code
    private String city;//市
    private String cityCode;//市code
    private String area;//区
    private String areaCode;//区code
    private long enrolId;//
    private String photoUrls;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getManifesto() {
        return manifesto;
    }

    public void setManifesto(String manifesto) {
        this.manifesto = manifesto;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public long getVote() {
        return vote;
    }

    public void setVote(long vote) {
        this.vote = vote;
    }

    public long getEnable() {
        return enable;
    }

    public void setEnable(long enable) {
        this.enable = enable;
    }

    public long getCandidateSum() {
        return candidateSum;
    }

    public void setCandidateSum(long candidateSum) {
        this.candidateSum = candidateSum;
    }

    public String getVoteSum() {
        return "得票： " + voteSum;
    }

    public void setVoteSum(long voteSum) {
        this.voteSum = voteSum;
    }

    public long getVoterSum() {
        return voterSum;
    }

    public void setVoterSum(long voterSum) {
        this.voterSum = voterSum;
    }

    public long getActivityStart() {
        return activityStart;
    }

    public void setActivityStart(long activityStart) {
        this.activityStart = activityStart;
    }

    public long getActivityEnd() {
        return activityEnd;
    }

    public void setActivityEnd(long activityEnd) {
        this.activityEnd = activityEnd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPageviews() {
        return "关注： " + pageviews;
    }

    public void setPageviews(long pageviews) {
        this.pageviews = pageviews;
    }

    public int getIsPackaging() {
        return isPackaging;
    }

    public void setIsPackaging(int isPackaging) {
        this.isPackaging = isPackaging;
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

    public long getEnrolId() {
        return enrolId;
    }

    public void setEnrolId(long enrolId) {
        this.enrolId = enrolId;
    }

    public String getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(String photoUrls) {
        this.photoUrls = photoUrls;
    }

}

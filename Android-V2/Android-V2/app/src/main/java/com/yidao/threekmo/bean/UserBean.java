package com.yidao.threekmo.bean;

import android.graphics.Color;

import com.ijustyce.fastkotlin.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017\8\25 0025.
 */

public class UserBean {
    private Long id; // 主键
    private long gmtCreate; // 创建时间
    private long gmtModified; // 修改时间
    private String creator; // 创建者
    private String modifier; // 修改者
    private Long userId; // 用户主键
    private String nickname; // 昵称
    private String individualitySignature; // 个性签名
    private String face; // 头像
    private String grade; // 等级
    private String webchatNum; // 微信号
    private String qqNum; // qq号
    private long birthday; // 生日
    private String province; // 省
    private String provinceCode; // 省code
    private String city; // 市
    private String cityCode; // 市code
    private String area; // 区
    private String areaCode; // 区code
    private String height; // 身高(cm)
    private String weight; // 体重(kg)
    private String profession; // 职业
    private String interest; // 爱好
    private String name; // 真实姓名
    private String card; // 身份证
    private String phone; // 手机号码
    private Integer phoneAuth; // 手机认证
    private String invitationCode; // 邀请码
    private Long invitationSUserId; // 邀请人主键
    private Long enable = 1L; // 是否启用
    private Long sex; // 性别  0:男 1:女
    private Long notification; // 是否启用通知
    private Long scope; // 范围自定义
    private Integer accountId;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIndividualitySignature() {
        return individualitySignature;
    }

    public void setIndividualitySignature(String individualitySignature) {
        this.individualitySignature = individualitySignature;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getWebchatNum() {
        return webchatNum;
    }

    public void setWebchatNum(String webchatNum) {
        this.webchatNum = webchatNum;
    }

    public String getQqNum() {
        return qqNum;
    }

    public void setQqNum(String qqNum) {
        this.qqNum = qqNum;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
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

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getPhoneAuth() {
        return phoneAuth;
    }

    public void setPhoneAuth(Integer phoneAuth) {
        this.phoneAuth = phoneAuth;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public Long getInvitationSUserId() {
        return invitationSUserId;
    }

    public void setInvitationSUserId(Long invitationSUserId) {
        this.invitationSUserId = invitationSUserId;
    }

    public Long getEnable() {
        return enable;
    }

    public void setEnable(Long enable) {
        this.enable = enable;
    }

    public Long getSex() {
        return sex;
    }

    public void setSex(Long sex) {
        this.sex = sex;
    }

    public Long getNotification() {
        return notification;
    }

    public void setNotification(Long notification) {
        this.notification = notification;
    }

    public Long getScope() {
        return scope;
    }

    public void setScope(Long scope) {
        this.scope = scope;
    }
}

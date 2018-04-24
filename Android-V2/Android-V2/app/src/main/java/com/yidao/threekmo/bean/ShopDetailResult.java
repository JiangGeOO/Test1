package com.yidao.threekmo.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Smart~ on 2017/11/14.
 */

public class ShopDetailResult implements Serializable {

    private long id;//id
    private long gmtCreate;//创建时间
    private long gmtModified;//修改时间
    private String creator;//创建者
    private String modifier;//修改者
    private String title;//标题
    private String photo;//商城首页图片
    private double originalPrice;//原价
    private double sellingPrice;//限时价
    private long merchandiseId;//商城头部Id
    private long rank;//
    private String type;//类型
    private int enable;//有效标志 1有效 0失效
    private String name;//商品名称
    private String describe;//商品描述
    private String category;//品类
    private int weight;//重量
    private String brands;//品牌
    private String photoUrl;//封面图片
    private int stock;//库存
    private String particulars;//商品详情
    private String productionPlace;//产地
    private String texture;//材质
    private String specification;//规格
    private long pageView;//浏览量
    private long monthlySales;//月销量
    private String photoList;//所有图片路径
    public ArrayList<PhotoItem> photoLists;//所有图片路径
    private long subjectId;//一级主体id
    private long subjectTrainId;//二级主体id
    private String shopName;
    private int coolect;
    private long beCollectId;//id
    private String labelNames;//标签
    private String address;//地址
    private long soldOut;//是否下架
    public int experienceSum;
    public String experienceIds;

    public class PhotoItem implements Serializable {
        public int isPanorama;
        public String panoramaUrl;
        public String photoUrl;
    }

    public long getSoldOut() {
        return soldOut;
    }

    public void setSoldOut(long soldOut) {
        this.soldOut = soldOut;
    }

    public String getLabelNames() {
        return labelNames;
    }

    public void setLabelNames(String labelNames) {
        this.labelNames = labelNames;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getBeCollectId() {
        return beCollectId;
    }

    public void setBeCollectId(long beCollectId) {
        this.beCollectId = beCollectId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getCoolect() {
        return coolect;
    }

    public void setCoolect(int coolect) {
        this.coolect = coolect;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public long getSubjectTrainId() {
        return subjectTrainId;
    }

    public void setSubjectTrainId(long subjectTrainId) {
        this.subjectTrainId = subjectTrainId;
    }

    public String getProductionPlace() {
        return productionPlace;
    }

    public void setProductionPlace(String productionPlace) {
        this.productionPlace = productionPlace;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public long getPageView() {
        return pageView;
    }

    public void setPageView(long pageView) {
        this.pageView = pageView;
    }

    public long getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(long monthlySales) {
        this.monthlySales = monthlySales;
    }

    public String getPhotoList() {
        return photoList;
    }

    public void setPhotoList(String photoList) {
        this.photoList = photoList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getBrands() {
        return brands;
    }

    public void setBrands(String brands) {
        this.brands = brands;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public long getMerchandiseId() {
        return merchandiseId;
    }

    public void setMerchandiseId(long merchandiseId) {
        this.merchandiseId = merchandiseId;
    }

    public long getRank() {
        return rank;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}

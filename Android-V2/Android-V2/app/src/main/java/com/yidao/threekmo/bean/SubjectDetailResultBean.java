package com.yidao.threekmo.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017\8\29 0029.
 */

public class SubjectDetailResultBean implements Serializable {

    /**
     * data : {"success":true,"data":{"id":14,"gmtCreate":1504063911000,"gmtModified":1504063911000,"creator":null,"modifier":null,"name":"德德","typeId":25,"businessStart":0,"businessEnd":0,"longitude":"120.209326","latitude":"30.182074","elevation":"47.12","floor":"18层","number":"456","address":"爸爸啊","province":"广东","provinceCode":null,"city":"珠海","cityCode":null,"area":"香洲区","areaCode":null,"individualitySignature":"健健康康快乐","labelNames":null,"labelIds":null,"statusIds":null,"photoId":null,"photoUrl":"http://tkm.oss-cn-hangzhou.aliyuncs.com/23269514-C826-4198-AE74-FFF49DB60B86.jpg","phone":"11111111","telephone":null,"grade":5,"webchatNum":null,"subjectId":2,"subjectTrainId":null,"subjectNumber":2,"enable":1,"distance":null,"complete":null,"typeName":null,"isTrain":1,"inferiorsCount":null,"secondaryImage":"http://tkm.oss-cn-hangzhou.aliyuncs.com/23269514-C826-4198-AE74-FFF49DB60B86.jpg,http://tkm.oss-cn-hangzhou.aliyuncs.com/8EF678E5-FEC9-409F-8550-947E89135268.jpg,http://tkm.oss-cn-hangzhou.aliyuncs.com/021811EB-5117-400B-9124-3594D72E0F02.jpg","subjectName":"测试主体"},"errorCode":0,"errorMsg":null}
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
         * data : {"id":14,"gmtCreate":1504063911000,"gmtModified":1504063911000,"creator":null,"modifier":null,"name":"德德","typeId":25,"businessStart":0,"businessEnd":0,"longitude":"120.209326","latitude":"30.182074","elevation":"47.12","floor":"18层","number":"456","address":"爸爸啊","province":"广东","provinceCode":null,"city":"珠海","cityCode":null,"area":"香洲区","areaCode":null,"individualitySignature":"健健康康快乐","labelNames":null,"labelIds":null,"statusIds":null,"photoId":null,"photoUrl":"http://tkm.oss-cn-hangzhou.aliyuncs.com/23269514-C826-4198-AE74-FFF49DB60B86.jpg","phone":"11111111","telephone":null,"grade":5,"webchatNum":null,"subjectId":2,"subjectTrainId":null,"subjectNumber":2,"enable":1,"distance":null,"complete":null,"typeName":null,"isTrain":1,"inferiorsCount":null,"secondaryImage":"http://tkm.oss-cn-hangzhou.aliyuncs.com/23269514-C826-4198-AE74-FFF49DB60B86.jpg,http://tkm.oss-cn-hangzhou.aliyuncs.com/8EF678E5-FEC9-409F-8550-947E89135268.jpg,http://tkm.oss-cn-hangzhou.aliyuncs.com/021811EB-5117-400B-9124-3594D72E0F02.jpg","subjectName":"测试主体"}
         * errorCode : 0
         * errorMsg : null
         */

        private boolean success;
        private MainHomeListItem data;
        private int errorCode;
        private String errorMsg;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public MainHomeListItem getData() {
            return data;
        }

        public void setData(MainHomeListItem data) {
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
             * id : 14
             * gmtCreate : 1504063911000
             * gmtModified : 1504063911000
             * creator : null
             * modifier : null
             * name : 德德
             * typeId : 25
             * businessStart : 0
             * businessEnd : 0
             * longitude : 120.209326
             * latitude : 30.182074
             * elevation : 47.12
             * floor : 18层
             * number : 456
             * address : 爸爸啊
             * province : 广东
             * provinceCode : null
             * city : 珠海
             * cityCode : null
             * area : 香洲区
             * areaCode : null
             * individualitySignature : 健健康康快乐
             * labelNames : null
             * labelIds : null
             * statusIds : null
             * photoId : null
             * photoUrl : http://tkm.oss-cn-hangzhou.aliyuncs.com/23269514-C826-4198-AE74-FFF49DB60B86.jpg
             * phone : 11111111
             * telephone : null
             * grade : 5.0
             * webchatNum : null
             * subjectId : 2
             * subjectTrainId : null
             * subjectNumber : 2
             * enable : 1
             * distance : null
             * complete : null
             * typeName : null
             * isTrain : 1
             * inferiorsCount : null
             * secondaryImage : http://tkm.oss-cn-hangzhou.aliyuncs.com/23269514-C826-4198-AE74-FFF49DB60B86.jpg,http://tkm.oss-cn-hangzhou.aliyuncs.com/8EF678E5-FEC9-409F-8550-947E89135268.jpg,http://tkm.oss-cn-hangzhou.aliyuncs.com/021811EB-5117-400B-9124-3594D72E0F02.jpg
             * subjectName : 测试主体
             */

            private int id;
            private long gmtCreate;
            private long gmtModified;
            private Object creator;
            private Object modifier;
            private String name;
            private int typeId;
            private int businessStart;
            private int businessEnd;
            private String longitude;
            private String latitude;
            private String elevation;
            private String floor;
            private String number;
            private String address;
            private String province;
            private Object provinceCode;
            private String city;
            private Object cityCode;
            private String area;
            private Object areaCode;
            private String individualitySignature;
            private Object labelNames;
            private Object labelIds;
            private Object statusIds;
            private Object photoId;
            private String photoUrl;
            private String phone;
            private Object telephone;
            private double grade;
            private Object webchatNum;
            private int subjectId;
            private Object subjectTrainId;
            private int subjectNumber;
            private int enable;
            private Object distance;
            private Object complete;
            private Object typeName;
            private int isTrain;
            private Object inferiorsCount;
            private String secondaryImage;
            private String subjectName;

            public int getId() {
                return id;
            }

            public void setId(int id) {
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

            public Object getCreator() {
                return creator;
            }

            public void setCreator(Object creator) {
                this.creator = creator;
            }

            public Object getModifier() {
                return modifier;
            }

            public void setModifier(Object modifier) {
                this.modifier = modifier;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getTypeId() {
                return typeId;
            }

            public void setTypeId(int typeId) {
                this.typeId = typeId;
            }

            public int getBusinessStart() {
                return businessStart;
            }

            public void setBusinessStart(int businessStart) {
                this.businessStart = businessStart;
            }

            public int getBusinessEnd() {
                return businessEnd;
            }

            public void setBusinessEnd(int businessEnd) {
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

            public Object getProvinceCode() {
                return provinceCode;
            }

            public void setProvinceCode(Object provinceCode) {
                this.provinceCode = provinceCode;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public Object getCityCode() {
                return cityCode;
            }

            public void setCityCode(Object cityCode) {
                this.cityCode = cityCode;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public Object getAreaCode() {
                return areaCode;
            }

            public void setAreaCode(Object areaCode) {
                this.areaCode = areaCode;
            }

            public String getIndividualitySignature() {
                return individualitySignature;
            }

            public void setIndividualitySignature(String individualitySignature) {
                this.individualitySignature = individualitySignature;
            }

            public Object getLabelNames() {
                return labelNames;
            }

            public void setLabelNames(Object labelNames) {
                this.labelNames = labelNames;
            }

            public Object getLabelIds() {
                return labelIds;
            }

            public void setLabelIds(Object labelIds) {
                this.labelIds = labelIds;
            }

            public Object getStatusIds() {
                return statusIds;
            }

            public void setStatusIds(Object statusIds) {
                this.statusIds = statusIds;
            }

            public Object getPhotoId() {
                return photoId;
            }

            public void setPhotoId(Object photoId) {
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

            public Object getTelephone() {
                return telephone;
            }

            public void setTelephone(Object telephone) {
                this.telephone = telephone;
            }

            public double getGrade() {
                return grade;
            }

            public void setGrade(double grade) {
                this.grade = grade;
            }

            public Object getWebchatNum() {
                return webchatNum;
            }

            public void setWebchatNum(Object webchatNum) {
                this.webchatNum = webchatNum;
            }

            public int getSubjectId() {
                return subjectId;
            }

            public void setSubjectId(int subjectId) {
                this.subjectId = subjectId;
            }

            public Object getSubjectTrainId() {
                return subjectTrainId;
            }

            public void setSubjectTrainId(Object subjectTrainId) {
                this.subjectTrainId = subjectTrainId;
            }

            public int getSubjectNumber() {
                return subjectNumber;
            }

            public void setSubjectNumber(int subjectNumber) {
                this.subjectNumber = subjectNumber;
            }

            public int getEnable() {
                return enable;
            }

            public void setEnable(int enable) {
                this.enable = enable;
            }

            public Object getDistance() {
                return distance;
            }

            public void setDistance(Object distance) {
                this.distance = distance;
            }

            public Object getComplete() {
                return complete;
            }

            public void setComplete(Object complete) {
                this.complete = complete;
            }

            public Object getTypeName() {
                return typeName;
            }

            public void setTypeName(Object typeName) {
                this.typeName = typeName;
            }

            public int getIsTrain() {
                return isTrain;
            }

            public void setIsTrain(int isTrain) {
                this.isTrain = isTrain;
            }

            public Object getInferiorsCount() {
                return inferiorsCount;
            }

            public void setInferiorsCount(Object inferiorsCount) {
                this.inferiorsCount = inferiorsCount;
            }

            public String getSecondaryImage() {
                return secondaryImage;
            }

            public void setSecondaryImage(String secondaryImage) {
                this.secondaryImage = secondaryImage;
            }

            public String getSubjectName() {
                return subjectName;
            }

            public void setSubjectName(String subjectName) {
                this.subjectName = subjectName;
            }
        }
    }
}

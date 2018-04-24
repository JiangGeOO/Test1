package com.yidao.threekmo.retrofit_server;

import com.yidao.threekmo.bean.AchieveBinnerResult;
import com.yidao.threekmo.bean.ActivityBaomingResult;
import com.yidao.threekmo.bean.ActivityDetailsResult;
import com.yidao.threekmo.bean.AliPayResult;
import com.yidao.threekmo.bean.BaseResult;
import com.yidao.threekmo.bean.BaseResultModel;
import com.yidao.threekmo.bean.BinnerResult;
import com.yidao.threekmo.bean.ChooseSecondListBean;
import com.yidao.threekmo.bean.CollectNumResult;
import com.yidao.threekmo.bean.DialogResult;
import com.yidao.threekmo.bean.ExperienceConnectResult;
import com.yidao.threekmo.bean.ExperienceDetailResult;
import com.yidao.threekmo.bean.ExperienceListResult;
import com.yidao.threekmo.bean.FirstListResult;
import com.yidao.threekmo.bean.GaoDeMapBean;
import com.yidao.threekmo.bean.OrderDetailMySelfResult;
import com.yidao.threekmo.bean.OrderMySelfResult;
import com.yidao.threekmo.bean.OrderRelaResult;
import com.yidao.threekmo.bean.SearchResultBean;
import com.yidao.threekmo.bean.SecondlyResultBean;
import com.yidao.threekmo.bean.ShopIndexResult;
import com.yidao.threekmo.bean.ShopInfoResult;
import com.yidao.threekmo.bean.ShopListResult;
import com.yidao.threekmo.bean.SubjectDetailResultBean;
import com.yidao.threekmo.bean.WxPayResult;
import com.yidao.threekmo.v2.IndexNewModel;
import com.yidao.threekmo.v2.ResponseModel;
import com.yidao.threekmo.v2.model.CloudShopList;
import com.yidao.threekmo.v2.model.CloudShopTopData;
import com.yidao.threekmo.v2.model.ShopLists;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017\8\24 0024.
 */

public interface SubjectServer {

    /*
    * 获得主体内容
    * */
    @POST("app/subjectSearch/findCAllTkmSubjectList")
    Call<FirstListResult> getFirstlySubject(@Query("longitude") String longitude, @Query("latitude") String latitude, @Query("start") int start, @Query("limit") int limit);


    /*
    * 获得二级列表以及之后的数据
    * */
    @FormUrlEncoded
    @POST("app/subjectTrainSearch/bottomTkmSubjectTrainList")
    Call<SecondlyResultBean> getSecondSubject(
            @Query("token") String token,
            @Field("method") long method, //显示方法 1按照层级分 2 全部展示
            @Field("longitude") String longitude,
            @Field("latitude") String latitude,
            @Field("name") String name,
            @Field("numberName") String numberName,
            @Field("floorName") String floorName,
            @Field("id") long id,
            @Field("type") long type,//上级主体类型1：一级主体 2为二级、三级等主体
            @Field("start") int start,
            @Field("limit") int limit
    );

    @GET("app/dynamics/exchangePrize")
    Call<ResponseModel<IndexNewModel>> getIndexNews();

    @FormUrlEncoded
    @POST("app/subjectTrainSearch/allTkmSubjectTrain")
    Call<SearchResultBean> searchSubjects(
            @Field("longitude") String longitude,
            @Field("latitude") String latitude,
            @Field("name") String name, @Query("start") int start,
            @Field("limit") int limit
    );

    //二级主体详情
    @POST("app/subjectTrainSearch/findTkmSubject")
    Call<SubjectDetailResultBean> getSubjectDetail(
            @Query("type") long type,//主体类型 1为一级主题 2为二级、三级等主体
            @Query("id") long id,//主体ID
            @Query("token") String token
    );

    //获取楼栋网络信息
    @POST("app/cDictionary/list")
    Call<ChooseSecondListBean> getBanNum(
            @Query("subjectId") long subjectId
    );

    //获取层高网络信息
    @POST("app/cDictionary/floorList")
    Call<ChooseSecondListBean> getFloorNum(
            @Query("numberId") long numberId
    );

    //地图获取三公里内所有一级主体
    @POST("app/subjectSearch/findMapTkmSubjectList")
    Call<GaoDeMapBean> getGaoDeall(
            @Query("longitude") String longitude,
            @Query("latitude") String latitude,
            @Query("distance") long distance
    );

    //地图获取三公里内所有二级体
    @POST("app/subjectTrainSearch/findMapBottonTkmSubjectTrainList")
    Call<GaoDeMapBean> getGaoDeSecond(
            @Query("longitude") String longitude,
            @Query("latitude") String latitude,
            @Query("numberName") String numberName,
            @Query("floorName") String floorName,
            @Query("id") long id

    );

    //活动列表
    @FormUrlEncoded
    @POST("app/apply/findActivityList")
    Call<FirstListResult> getActivityList(
            @Field("isAgentShop") String isAgentShop,
            @Field("cityName") String cityName,
            @Field("acName") String acName,
            @Field("startDate") String startDate,
            @Field("endDate") String endDate,
            @Field("distance") long distance,
            @Field("sortRule") long sortRule,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude,
            @Field("start") int start,
            @Field("limit") int limit
    );

    @POST("app/apply/get")
    Call<ActivityDetailsResult> getActivityDetails(
            @Query("activityId") long activityId,
            @Query("longitude") String longitude,
            @Query("latitude") String latitude
    );

    //报名活动
    @POST("app/apply/intercept/create")
    Call<ActivityBaomingResult> getSuccess(
            @Query("token") String token,
            @Query("activityId") long activityId,
            @Query("nickName") String nickName,
            @Query("mobile") String mobile,
            @Query("receiveMobieList") String receiveMobieList
    );

    //我的活动列表
    @POST("app/apply/intercept/myActivityList")
    Call<FirstListResult> getMyActiveList(
            @Query("token") String token,
            @Query("status") long status,
            @Query("start") int start,
            @Query("limit") int limit
    );

    //获取binner
    @GET("app/dynamics/banner")
    Call<AchieveBinnerResult> getBinnerImage(
            @Query("token") String token
    );

    //获取binner
    @GET("/app/dynamics/wxLink")
    Call<BinnerResult> getBinnerSingleImage(

    );

    //隐藏二级主题
    @POST("app/subjectTrainHide/intercept/creat")
    Call<GaoDeMapBean> yinCang(
            @Query("token") String token,
            @Query("subjectId") long subjectId,
            @Query("subjectTrainId") long subjectTrainId
    );

    //商城主页 获取商城banner、爆款、热门、精选的商品列表
    @GET("app/merchandise/list")
    Call<ShopIndexResult> getShopShow(

    );

    //获取商品列表
    @FormUrlEncoded
    @POST("app/merchandise/listAll")
    Call<ShopListResult> shopList(
            @Field("name") String name,
            @Field("start") int start,
            @Field("limit") int limit
    );

    @GET("app/merchandise/list")
    Call<BaseResultModel<CloudShopTopData>> getShopTop(

    );

    //获取商品列表
    @FormUrlEncoded
    @POST("app/merchandise/listAll")
    Call<CloudShopList> shopListMvvm(
            @Field("name") String name,
            @Field("start") int start,
            @Field("limit") int limit
    );

    @FormUrlEncoded
    @POST("app/experience/listByIds")
    Call<ShopLists> goodsShopList(@Field("experienceId") String shopIds,
                                  @Field("longitude") String longitude,
                                  @Field("latitude") String latitude);

    //获取商品详情
    @GET("app/merchandise/getDetail")
    Call<ShopInfoResult> shopDetail(
            @Query("token") String token,
            @Query("id") long id,
            @Query("longitude") String longitude,
            @Query("latitude") String latitude
    );

    //收藏商品
    @POST("app/collect/intercept/create")
    Call<ActivityBaomingResult> shopCoolect(
            @Query("token") String token,
            @Query("beCollectId") long beCollectId,
            @Query("type") String type
    );

    //取消收藏商品
    @POST("app/collect/intercept/cancel")
    Call<ActivityBaomingResult> shopCancle(
            @Query("token") String token,
            @Query("beCollectId") long beCollectId,
            @Query("type") String type
    );

    //订单确认
    @POST("app/orders/intercept/creat")
    Call<OrderRelaResult> orderRela(
            @Query("token") String token,
            @Query("merchandiseId") long merchandiseId
    );

    //订单状态
    @POST("app/orders/intercept/payState")
    Call<OrderRelaResult> orderStatus(
            @Query("token") String token,
            @Query("orderNumber") String orderNumber
    );

    //体验店列表
    @POST("app/experience/experienceList")
    Call<ExperienceListResult> experienceList(
            @Query("start") int start,
            @Query("limit") int limit,
            @Query("longitude") String longitude,
            @Query("latitude") String latitude
    );

    //体验详情
    @POST("app/experience/getDetail")
    Call<ExperienceDetailResult> experienceDeatil(
            @Query("token") String token,
            @Query("experienceId") long experienceId
    );

    //关联体验店
    @POST("app/experience/related_products")
    Call<ExperienceConnectResult> experienceConnect(
            @Query("experienceId") String experienceId
    );

    //阿里支付请求参数
    @FormUrlEncoded
    @POST("app/orders/intercept/pay/zfb_order")
    Call<AliPayResult> aliPayServer(
            @Field("consigneeName") String consigneeName,
            @Field("consigneePhone") String consigneePhone,
            @Field("consigneeAddress") String consigneeAddress,
            @Field("token") String token,
            @Field("merchandiseId") String merchandiseId
    );

    //微信支付请求参数
    @FormUrlEncoded
    @POST("app/orders/intercept/pay/wx_order")
    Call<WxPayResult> wxPayServer(
            @Field("consigneeName") String consigneeName,
            @Field("consigneePhone") String consigneePhone,
            @Field("consigneeAddress") String consigneeAddress,
            @Field("token") String token,
            @Field("merchandiseId") String merchandiseId
    );

    //我的订单
    @POST("app/orders/intercept/myOrderList")
    Call<OrderMySelfResult> myOderList(
            @Query("token") String token,
            @Query("start") int start,
            @Query("limit") int limit
    );

    //订单详情
    @POST("app/orders/intercept/orderDetail")
    Call<OrderDetailMySelfResult> myOrderDetail(
            @Query("token")String token,
            @Query("orderId") long orderId
    );

    //收藏列表
    @POST("app/collect/intercept/myCollectList")
    Call<ShopListResult> myCollect(
            @Query("token") String token,
            @Query("type") String type,
            @Query("start") int start,
            @Query("limit") int limit
    );

    //我的体验
    @POST("app/collect/intercept/myCollectList")
    Call<ExperienceListResult> myExperience(
            @Query("token") String token,
            @Query("type") String type,
            @Query("start") int start,
            @Query("limit") int limit
    );

    //是否显示dialog
    @POST("app/dynamics/bounced")
    Call<DialogResult> dialogFlag(
            @Query("token") String token

    );

    //获取收藏数量
    @POST("app/collect/intercept/getNum")
    Call<CollectNumResult> collectNum(
            @Query("token") String token
    );


}

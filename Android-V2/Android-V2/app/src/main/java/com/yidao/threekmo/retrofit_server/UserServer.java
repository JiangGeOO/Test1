package com.yidao.threekmo.retrofit_server;

import com.yidao.threekmo.bean.BaseResult;
import com.yidao.threekmo.bean.CodeLoginResult;
import com.yidao.threekmo.bean.HasSetPayPw;
import com.yidao.threekmo.bean.MyRedBoxListBean;
import com.yidao.threekmo.bean.PsdLoginResult;
import com.yidao.threekmo.bean.RedBoxBean;
import com.yidao.threekmo.bean.WeChatBindInfo;
import com.yidao.threekmo.bean.WithdrawalDetail;
import com.yidao.threekmo.bean.WithdrawalList;
import com.yidao.threekmo.bean.WithdrawlResult;
import com.yidao.threekmo.parameter.WebParameters;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017\8\25 0025.
 */

public interface UserServer {

    @POST(WebParameters.LOGIN_REG)
    Call<CodeLoginResult> codeLogin(@Query("mobile")String mobile,@Query("code")String code);

    @POST(WebParameters.LOGIN_PSD)
    Call<PsdLoginResult> psdLogin(@Query("mobile")String mobile, @Query("password")String password);


//    @POST(WebParameters.SETPSD_FIRST)
//    Call<BaseResult> setPsdFirst(@Query("token")String token, @Query("password")String password);

    @POST("app/reg/reset")
    Call<BaseResult> setPsdFirst(
      @Query("mobile")String mobile,
      @Query("password")String password,
      @Query("code")String code
    );

    @FormUrlEncoded
    @POST("/app/redEnvelope/intercept/getBurse")
    Call<HasSetPayPw> hasSetPayPw(@Field("token") String token);

    @FormUrlEncoded
    @POST("/app/withdraw/intercept/getWeChatInfo")
    Call<WeChatBindInfo> getWeChatInfo(@Field("token") String token);

    @FormUrlEncoded
    @POST("/app/withdraw/intercept/bindWechat")
    Call<WeChatBindInfo> bindWeChatInfo(@Field("token") String token, @Field("code") String code);

    @FormUrlEncoded
    @POST("/app/withdraw/intercept/withdrawAli")
    Call<WithdrawlResult> withdrawalAlipay(@Field("token") String token, @Field("payeeAccount")String payeeAccount,
                                           @Field("amount") String amount, @Field("payeeReaName") String payeeReaName,
                                           @Field("paymentCode") String paymentCode);

    @FormUrlEncoded
    @POST("/app/withdraw/intercept/withdrawList")
    Call<WithdrawalList> withdrawalList(@Field("token") String token, @Field("start")Integer start, @Field("limit")Integer limit);

    @FormUrlEncoded
    @POST("/app/withdraw/intercept/withdrawWx")
    Call<WithdrawlResult> withdrawalWeChat(@Field("token") String token, @Field("openId")String openId,
                                      @Field("amount") String amount, @Field("payeeReaName") String payeeReaName,
                                      @Field("paymentCode") String paymentCode, @Field("wxNickName") String wxNickName,
                                      @Field("spbillCreateIp") String spbillCreateIp);

    @FormUrlEncoded
    @POST("/app/withdraw/intercept/get")
    Call<WithdrawalDetail> withdrawalDetail(@Field("token") String token, @Field("withdrawId")String withdrawId);

    @FormUrlEncoded
    @POST("app/userinfo/intercept/update_userinfo")
    Call<BaseResult> changeUserInfo(@Field("token") String token, @Field("face")String face, @Field("nickname")String nickname, @Field("birthday")String birthday, @Field("individualitySignature")String individualitySignature, @Field("sex")String sex);

    //用户获取红包总金额
    @POST("app/redEnvelope/intercept/myRedEnvelopeSum")
    Call<RedBoxBean> getRedAllMoney(
            @Query("token")String token
    );

    //用户获取我的红包列表
    @POST("app/redEnvelope/intercept/myRedEnvelope")
    Call<MyRedBoxListBean> getRedBoxList(
            @Query("token")String token,
            @Query("start")Integer start,
            @Query("limit")Integer limit
    );

}

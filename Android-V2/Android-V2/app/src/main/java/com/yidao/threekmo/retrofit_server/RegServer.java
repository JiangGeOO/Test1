package com.yidao.threekmo.retrofit_server;

import com.yidao.threekmo.bean.BaseResult;
import com.yidao.threekmo.bean.UpdateBean;
import com.yidao.threekmo.parameter.WebParameters;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017\8\24 0024.
 */

public interface RegServer {
    @POST(WebParameters.GETREGCODE)
    Call<BaseResult> getRegCode(@Query("mobile") String mobile);


    @POST(WebParameters.GETLOGCODE)
    Call<BaseResult> getLoginCode(@Query("mobile") String mobile);

    //获取修改密码的验证码
    @POST("app/reg/send_find_code")
    Call<BaseResult> getModifyCode(@Query("mobile") String mobile);

    /*
    * 检测验证码
    *
    * type:1.注册2.修改密码3.登录
    * */
    @POST(WebParameters.CHECKCODE)
    Call<BaseResult> checkCode(@Query("mobile") String mobile, @Query("type") int type, @Query("code") String code);

    @POST("app/burse/intercept/setPayPwd")
    Call<BaseResult> setPayPw(@Query("token")String token, @Query("encodedData") String encodedData, @Query("code") String code);

    @POST("app/burse/intercept/bursePay")
    Call<BaseResult> packetPay(@Query("token")String token, @Query("accountId") String orderNumber, @Query("paymentCode") String paymentCode);

    //修改密码
    @POST("app/reg/reset")
    Call<BaseResult> restPsd(
            @Query("mobile") String mobile,
            @Query("password") String password,
            @Query("code") String code
    );

    //获取版本号用于App升级
    @GET("app/about/version")
    Call<UpdateBean> getVersion(@Query("app_type") String app_type);
}

package com.yidao.threekmo.utils;

import com.yidao.myutils.log.LogUtils;
import com.yidao.threekmo.parameter.WebParameters;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017\8\24 0024.
 */

public class RetrofitUtils {
    private static Retrofit mRetrofit;

    public static Retrofit getRetrofit(){
        synchronized (RetrofitUtils.class){
            if(null == mRetrofit){
                mRetrofit =  new Retrofit.Builder()
                        .baseUrl(WebParameters.SERVERURL)
                        .client(new OkHttpClient.Builder().addNetworkInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                            @Override
                            public void log(String message) {
                                LogUtils.e("网络请求:" + message);
                            }
                        }).setLevel(HttpLoggingInterceptor.Level.BODY)).build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return mRetrofit;
        }
    }
}

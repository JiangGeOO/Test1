package com.yidao.threekmo.utils;

import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.yidao.threekmo.application.MyApplication;

/**
 * Created by Lee on 2016/11/7.
 * 必须先在application中初始化ossclient
 */

public class OSSUtils {
    /**
     * 上传文件
     * @param bucketName
     * @param objectKey：文件名称
     * @param uploadFilePath:文件路径
     * @param ossProgressCallback:进度回调
     * @param ossCompletedCallback:完成回调
     */
    public static void upLoadFile(String bucketName, String objectKey, String uploadFilePath, OSSProgressCallback ossProgressCallback, OSSCompletedCallback ossCompletedCallback){
        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(bucketName, objectKey, uploadFilePath);
        //设置进度监听
        if(null != ossProgressCallback){
            put.setProgressCallback(ossProgressCallback);
        }
        //设置完成监听
        OSSAsyncTask task = MyApplication.getInstance().getOss().asyncPutObject(put,ossCompletedCallback);
    }

    /**
     * 下载文件
     * @param bucketName
     * @param objectKey：文件名称
     * @param ossCompletedCallback：下载回调
     */
    public static void downLoadFile(String bucketName, String objectKey, OSSCompletedCallback ossCompletedCallback){
        // 构造下载文件请求
        GetObjectRequest get = new GetObjectRequest(bucketName, objectKey);
        OSSAsyncTask task = MyApplication.getInstance().getOss().asyncGetObject(get,ossCompletedCallback);
    }
}

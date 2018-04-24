package com.yidao.threekmo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Smart~ on 2017/10/17.
 */

public class ShareUtil {

    //QQ分享
    private static Tencent mTencent;
    private static String QQ_APP_ID = "1106380917";

    //微信
    public static final String WX_APP_ID = "wx527758ac3fedd1e6";    //这个APP_ID就是注册APP的时候生成的
    private static final String WX_APP_SECRET = "3e9a1957f7396befa48dbd681c4b9a4b";
    public static IWXAPI api;

    private static Context qqContext;


    public static void initQQ(Context mContext) {
        if (mTencent == null) {
            mTencent = Tencent.createInstance(QQ_APP_ID, mContext);
        }
    }

    public static void initWeixin(Context mContext) {
        if (api == null) {
            api = WXAPIFactory.createWXAPI(mContext, WX_APP_ID, true);
            api.registerApp(WX_APP_ID);
        }
    }

    //微信
    // 0-分享给朋友  1-分享到朋友圈
    public static void weiChat(final int flag, Context mContext, String url, final String imgUrl, String title, String main) {
        initWeixin(mContext);
        if (!api.isWXAppInstalled()) {
            Toast.makeText(mContext, "您还未安装微信", Toast.LENGTH_SHORT).show();
            return;
        }

        //创建一个WXWebPageObject对象，用于封装要发送的Url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        //创建一个WXMediaMessage对象
        final WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title ;
        msg.description = main;
//        try {
//            Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
//            Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
//            msg.setThumbImage(thumbBmp);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = String.valueOf(System.currentTimeMillis());//transaction字段用于唯一标识一个请求，这个必须有，否则会出错
//        req.message = msg;
//        //表示发送给朋友圈  WXSceneTimeline  表示发送给朋友  WXSceneSession
//        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
//        api.sendReq(req);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap thumb = null;
                try {
                    thumb = BitmapFactory.decodeStream(new URL(imgUrl).openStream());
                    Bitmap thumbBmp = Bitmap.createScaledBitmap(thumb,120,150,true);
                    thumb.recycle();
                    msg.thumbData= CommonUtil.bmpToByteArray(thumbBmp,true);
                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = String.valueOf(System.currentTimeMillis());//transaction字段用于唯一标识一个请求，这个必须有，否则会出错
                    req.message = msg;
                    //表示发送给朋友圈  WXSceneTimeline  表示发送给朋友  WXSceneSession
                    req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
                    api.sendReq(req);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }



    //type 0:分享到qq 1:分享到空间
    public static void shareToQQ(int type,Context mContext, String url, String imgUrl, String title, String main) {
        initQQ(mContext);
        qqContext = mContext;
        ShareQqListener myListener = new ShareQqListener();
        Bundle params = new Bundle();
//        imgUrl = "http://tkm.oss-cn-hangzhou.aliyuncs.com/1507517339543_867451026014297.png";
        if (type == 0) {
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, main);
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);//网页地址
//        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, mainHomeListBean.getImages().get(0));
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgUrl);
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "三公里");
            mTencent.shareToQQ((Activity) mContext, params, myListener);

        } else {
            params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);//类型
            params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);//标题
            params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, main);//概要
            params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);
            //下面这个必须加上  不然无法调动 qq空间
            ArrayList<String> imageUrls = new ArrayList<String>();
            imageUrls.add(imgUrl);
            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
            mTencent.shareToQzone((Activity) mContext, params, myListener);

        }
    }

    /**
     * QQ分享相关
     *
     * @author Administrator
     *
     */
    private static class ShareQqListener implements IUiListener {

        @Override
        public void onCancel() {
            // TODO Auto-generated method stub
            Toast.makeText(qqContext, "分享取消", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete(Object arg0) {
            // TODO Auto-generated method stub
            Toast.makeText(qqContext, "分享成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(UiError arg0) {
            // TODO Auto-generated method stub
            Toast.makeText(qqContext, "分享出错", Toast.LENGTH_SHORT).show();
        }

    }

}

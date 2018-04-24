package com.yidao.threekmo.v2.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ijustyce.fastkotlin.utils.ToastUtil;
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
import com.yidao.threekmo.R;
import com.yidao.threekmo.utils.CommonUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by deepin on 17-12-26.
 */

public class ShareUi {

    //QQ分享
    private Tencent mTencent;
    private String QQ_APP_ID = "1106380917";

    //微信
    private String WX_APP_ID = "wx527758ac3fedd1e6";    //这个APP_ID就是注册APP的时候生成的
  //  private final String WX_APP_SECRET = "3e9a1957f7396befa48dbd681c4b9a4b";
    private IWXAPI api;
    private Activity activity;

    public void onDestroy() {
        activity = null;
        api = null;
        mTencent = null;
    }

    private ShareUi(Activity activity){
        this.activity = activity;
    }

    public static ShareUi getInstance(Activity activity) {
        return new ShareUi(activity);
    }

    public void showPopwindow(View parent, final String url, final String imgUrl, final String title, final String main) {
        //   View parent = ((ViewGroup) context.findViewById(R.id.bottomRela));
        View popView = View.inflate(activity, R.layout.share_popup_window, null);

        LinearLayout sharePopWindow = (LinearLayout) popView.findViewById(R.id.sharePopWindow);
        RelativeLayout.LayoutParams llpSharePopWindow = (RelativeLayout.LayoutParams) sharePopWindow.getLayoutParams();
        llpSharePopWindow.height = CommonUtil.getRealWidth(300);

        ImageView shareToQq = (ImageView) popView.findViewById(R.id.shareToQq);
        RelativeLayout.LayoutParams llpShareToQq = (RelativeLayout.LayoutParams) shareToQq.getLayoutParams();
        llpShareToQq.width = CommonUtil.getRealWidth(100);
        llpShareToQq.height = llpShareToQq.width;
        llpShareToQq.topMargin = CommonUtil.getRealWidth(45);

        ImageView shareToQZone = (ImageView) popView.findViewById(R.id.shareToQZone);
        RelativeLayout.LayoutParams llpShareToQZone = (RelativeLayout.LayoutParams) shareToQZone.getLayoutParams();
        llpShareToQZone.width = CommonUtil.getRealWidth(100);
        llpShareToQZone.height = llpShareToQZone.width;
        llpShareToQZone.topMargin = CommonUtil.getRealWidth(45);

        ImageView shareToWx = (ImageView) popView.findViewById(R.id.shareToWx);
        RelativeLayout.LayoutParams llpShareToWx = (RelativeLayout.LayoutParams) shareToWx.getLayoutParams();
        llpShareToWx.width = CommonUtil.getRealWidth(100);
        llpShareToWx.height = llpShareToWx.width;
        llpShareToWx.topMargin = CommonUtil.getRealWidth(45);

        ImageView shareToPyq = (ImageView) popView.findViewById(R.id.shareToPyq);
        RelativeLayout.LayoutParams llpShareToPyq = (RelativeLayout.LayoutParams) shareToPyq.getLayoutParams();
        llpShareToPyq.width = CommonUtil.getRealWidth(100);
        llpShareToPyq.height = llpShareToPyq.width;
        llpShareToPyq.topMargin = CommonUtil.getRealWidth(45);

        TextView qqText = (TextView) popView.findViewById(R.id.qqText);
        qqText.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llpQqText = (RelativeLayout.LayoutParams) qqText.getLayoutParams();
        llpQqText.topMargin = CommonUtil.getRealWidth(20);

        TextView qzoneText = (TextView) popView.findViewById(R.id.qzoneText);
        qzoneText.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llpQzoneText = (RelativeLayout.LayoutParams) qzoneText.getLayoutParams();
        llpQzoneText.topMargin = CommonUtil.getRealWidth(20);

        TextView wxText = (TextView) popView.findViewById(R.id.wxText);
        wxText.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llpWxText = (RelativeLayout.LayoutParams) wxText.getLayoutParams();
        llpWxText.topMargin = CommonUtil.getRealWidth(20);

        TextView pyqText = (TextView) popView.findViewById(R.id.pyqText);
        pyqText.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llpPyqText = (RelativeLayout.LayoutParams) pyqText.getLayoutParams();
        llpPyqText.topMargin = CommonUtil.getRealWidth(20);


        int width = activity.getResources().getDisplayMetrics().widthPixels;
        int height = activity.getResources().getDisplayMetrics().heightPixels;

        final PopupWindow popWindow = new PopupWindow(popView, width, height);
        ColorDrawable dw = new ColorDrawable(0x30000000);
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//        popWindow.setAnimationStyle(R.style.AnimBottom);
//        popWindow.setFocusable(true);
//        popWindow.setOutsideTouchable(false);// 设置同意在外点击消失

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.shareToQq:
                        shareToQQ(0, url, imgUrl, title, main);
                        break;
                    case R.id.shareToQZone:
                        shareToQQ(1, url, imgUrl, title, main);
                        break;
                    case R.id.shareToWx:
                        weiChat(0, url, imgUrl, title, main);
                        break;
                    case R.id.shareToPyq:
                        weiChat(1, url, imgUrl, title, main);
                        break;

                    default:
                        break;
                }
                popWindow.dismiss();
            }
        };

        popView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int downY = (int) event.getY();
                    if (downY < CommonUtil.getRealWidth(900)) {
                        popWindow.dismiss();
                    }
                }
                return true;
            }
        });

        shareToQq.setOnClickListener(listener);
        shareToQZone.setOnClickListener(listener);
        shareToWx.setOnClickListener(listener);
        shareToPyq.setOnClickListener(listener);
    }

    //QQ回调监听
    public IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
            ToastUtil.INSTANCE.show("分享取消");
            Toast.makeText(activity, "分享失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete(Object response) {
            ToastUtil.INSTANCE.show("分享成功");
        }

        @Override
        public void onError(UiError e) {
            ToastUtil.INSTANCE.show("分享失败");
        }
    };

    //type 0:分享到qq 1:分享到空间
    private void shareToQQ(int type, String url, String imgUrl, String title, String main) {
        Bundle params = new Bundle();
        if (mTencent == null) {
            mTencent = Tencent.createInstance(QQ_APP_ID, activity);
        }
//        imgUrl = "http://tkm.oss-cn-hangzhou.aliyuncs.com/1507517339543_867451026014297.png";
        if (type == 0) {
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, main);
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);//网页地址
//        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, mainHomeListBean.getImages().get(0));
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgUrl);
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "三公里");
            mTencent.shareToQQ(activity, params, qqShareListener);

        } else {
            params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);//类型
            params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);//标题
            params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, main);//概要
            params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);
            //下面这个必须加上  不然无法调动 qq空间
            ArrayList<String> imageUrls = new ArrayList<String>();
            imageUrls.add(imgUrl);
            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
            mTencent.shareToQzone(activity, params, qqShareListener);

        }
    }

    //微信
    // 0-分享给朋友  1-分享到朋友圈
    private void weiChat(final int flag, String url, final String imgUrl, String title, String main) {
        if (api == null) {
            api = WXAPIFactory.createWXAPI(activity, WX_APP_ID, true);
            api.registerApp(WX_APP_ID);
        }

        if (!api.isWXAppInstalled()) {
            Toast.makeText(activity, "您还未安装微信", Toast.LENGTH_SHORT).show();
            return;
        }

        //创建一个WXWebPageObject对象，用于封装要发送的Url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        //创建一个WXMediaMessage对象
        final WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = main;
//        try {
////            Bitmap bmp = BitmapFactory.decodeResource(WebViewScriptActivity.this.getResources(), R.mipmap.wangxingsuo);
////            Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
////            msg.setThumbImage(thumbBmp);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap thumb = null;
                try {
                    thumb = BitmapFactory.decodeStream(new URL(imgUrl).openStream());
                    Bitmap thumbBmp = Bitmap.createScaledBitmap(thumb, 150, 150, true);
                    msg.thumbData = CommonUtil.bmpToByteArray(thumbBmp, true);
                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = String.valueOf(System.currentTimeMillis());//transaction字段用于唯一标识一个请求，这个必须有，否则会出错
                    req.message = msg;
                    //表示发送给朋友圈  WXSceneTimeline  表示发送给朋友  WXSceneSession
                    req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
                    api.sendReq(req);
                    thumb.recycle();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}

package com.yidao.threekmo.activitys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.OSSRequest;
import com.alibaba.sdk.android.oss.model.OSSResult;
import com.alipay.sdk.pay.demo.PayDemoActivity;
import com.github.lzyzsd.jsbridge.AppPayCallBack;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.ijustyce.fastkotlin.utils.CommonTools;
import com.ijustyce.fastkotlin.utils.FileUtils;
import com.ijustyce.fastkotlin.utils.IJson;
import com.ijustyce.fastkotlin.utils.ILog;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.tauth.Tencent;
import com.yidao.myutils.log.LogUtils;
import com.yidao.threekmo.BuildConfig;
import com.yidao.threekmo.R;
import com.yidao.threekmo.actions.CompressAction;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.LoginUtils;
import com.yidao.threekmo.utils.OSSUtils;
import com.yidao.threekmo.utils.ShareUtil;
import com.yidao.threekmo.v2.PayInfo;
import com.yidao.threekmo.v2.activity.PayPwActivity;
import com.yidao.threekmo.v2.activity.PocketPayActivity;
import com.yidao.threekmo.v2.ui.ShareUi;
import com.yidao.threekmo.v2.viewmodel.PwSettingVm;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class WebViewScriptActivity extends BaseActivity implements View.OnClickListener {

    private BridgeWebView webview;
    private RelativeLayout titleRela;
    private ImageView ivBack;
    private TextView back;
    private TextView finish;
    private TextView title;
    private String url = "";
    private String webTitle = "";

    private ShareUi shareUi;

    private static final int IMAGE_PICKER = 1;

    private String currentGetImgId;
    private String currentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_script);

        if (getIntent() != null) {
            url = getIntent().getStringExtra("url");
            webTitle = getIntent().getStringExtra("title");
        }
        initViews();
    }

    @Override
    public boolean cancelAutoNavigationBar() {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentId != null && LoginUtils.isLogin(false, WebViewScriptActivity.this)) {
            webview.callHandler("getUserInfos",
                    LoginUtils.getToken(WebViewScriptActivity.this) + "," + currentId, new CallBackFunction() {
                        @Override
                        public void onCallBack(String data) {

                        }
                    });
            currentId = null;
        }
    }

    private void initViews() {
        titleRela = (RelativeLayout) findViewById(R.id.titleRela);
        RelativeLayout.LayoutParams tlpTitleRela = (RelativeLayout.LayoutParams) titleRela.getLayoutParams();
        tlpTitleRela.height = CommonUtil.getRealWidth(89);

        ivBack = (ImageView) findViewById(R.id.ivBack);
        RelativeLayout.LayoutParams llpIvback = (RelativeLayout.LayoutParams) ivBack.getLayoutParams();
        llpIvback.width = CommonUtil.getRealWidth(44);
        llpIvback.height = llpIvback.width;
        llpIvback.leftMargin = CommonUtil.getRealWidth(20);

        back = (TextView) findViewById(R.id.back);
        back.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llpBack = (RelativeLayout.LayoutParams) back.getLayoutParams();
        llpBack.leftMargin = CommonUtil.getRealWidth(64);

        finish = (TextView) findViewById(R.id.finish);
        finish.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llpFinish = (RelativeLayout.LayoutParams) finish.getLayoutParams();
        llpFinish.leftMargin = CommonUtil.getRealWidth(146);

        title = (TextView) findViewById(R.id.title);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(36));
        RelativeLayout.LayoutParams llp_title = (RelativeLayout.LayoutParams) title.getLayoutParams();

        ivBack.setOnClickListener(this);
        back.setOnClickListener(this);
        finish.setOnClickListener(this);
        webview = (BridgeWebView) findViewById(R.id.webview);

        if (url != null && url.endsWith("token=")) {
            url += LoginUtils.getToken(this);
        }

        title.setText(webTitle);

        webview.registerHandler("goToShare", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    String shareString = new String(data);
                    JSONTokener jsonParser = new JSONTokener(shareString);
                    JSONObject person = (JSONObject) jsonParser.nextValue();
                    String url = person.getString("url");
                    String imgUrl = person.getString("imgUrl");
                    String title = person.getString("title");
                    String main = person.getString("main");
                    if (shareUi == null) shareUi = ShareUi.getInstance(WebViewScriptActivity.this);
                    shareUi.showPopwindow(findViewById(R.id.bottomRela), url, imgUrl, title, main);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        webview.registerHandler("goToLand", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (LoginUtils.isLogin(true, WebViewScriptActivity.this)) {
                    function.onCallBack(LoginUtils.getToken(WebViewScriptActivity.this) + "," + data);
                } else {
                    currentId = data;
                }
            }
        });

        webview.registerHandler("getImg", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                currentGetImgId = data;
                ImagePicker imagePicker = ImagePicker.getInstance();
                imagePicker.setCrop(false);
                Intent intent = new Intent(WebViewScriptActivity.this, ImageGridActivity.class);
                startActivityForResult(intent, IMAGE_PICKER);
            }
        });

        webview.registerHandler("getVersion", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                String versionName = CommonTools.INSTANCE.versionName(WebViewScriptActivity.this);
                versionName = versionName.replaceAll("\\.", "");
                function.onCallBack(versionName);
            }
        });

        webview.registerHandler("goHoiPay", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                doPay(data);
            }
        });

        webview.registerHandler("setPaypass", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", FindPsdActivity.TYPE_PAY_PW);
                Intent intent = new Intent(WebViewScriptActivity.this, FindPsdActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, PwSettingVm.Companion.getSET_PAY_PW());
            }
        });

        webview.setAppPayCallBack(new AppPayCallBack() {
            @Override
            public void payCall(String url) {
                try {
                    Uri uri = Uri.parse(url);
                    Intent intent = Intent.parseUri(url,
                            Intent.URI_INTENT_SCHEME);
                    intent.addCategory("android.intent.category.BROWSABLE");
//                    intent.setComponent(null);
//                     intent.setSelector(null);
                    startActivity(intent);
                } catch (Exception e) {

                }
            }
        });

        /***打开本地缓存提供JS调用**/
        webview.getSettings().setDomStorageEnabled(true);
        // Set cache size to 8 mb by default. should be more than enough
        webview.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.getSettings().setDatabaseEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setGeolocationEnabled(true);
        // This next one is crazy. It's the DEFAULT location for your app's cache
        // But it didn't work for me without this line.
        // UPDATE: no hardcoded path. Thanks to Kevin Hawkins
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webview.getSettings().setAppCachePath(appCachePath);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setAppCacheEnabled(true);

        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webview.getSettings().setGeolocationDatabasePath(dir);
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });

        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webview.loadUrl(url);
    }

    private void doPay(String data) {
        ILog.INSTANCE.e("===pay===", data);
        PayInfo payInfo = IJson.INSTANCE.fromJson(data, PayInfo.class);
        if (payInfo == null) return;
        switch (payInfo.getW()) {
            case "1":
                aliPay(payInfo);
                break;
            case "2":
                callWeChatPay(payInfo);
                break;
            case "3":
                pocketPay(payInfo);
                break;
        }
    }

//    private HashMap<String, String> getParams(String data) {
//        String[] result = data.split()
//    }

    private final static int REQUEST_PAY = 10010;
    private BroadcastReceiver wxPayReceiver;

    private void aliPay(PayInfo payInfo) {
//        bundle.putString("rsa_private", JniUtils.getInstance().getRsaPrivate());
//        bundle.putString("partner", JniUtils.getInstance().getSellerId());
//        bundle.putString("seller", JniUtils.getInstance().getSellerId());
//        bundle.putString("notify_url", aliPay.notify_url);
//        bundle.putString("total_fee", aliPay.total_fee );
//        bundle.putString("body", aliPay.body);
//        bundle.putString("subject", aliPay.subject);
        Bundle bundle = new Bundle();
        bundle.putString("payInfo", payInfo.getOrderString());
        startActivityForResult(new Intent(this, PayDemoActivity.class).putExtras(bundle), REQUEST_PAY);
    }

    private void callWeChatPay(PayInfo payInfo) {
        PayReq req = new PayReq();
        req.appId = ShareUtil.WX_APP_ID;
        req.partnerId = payInfo.getPartnerid();
        req.prepayId = payInfo.getPrepayid();
        req.nonceStr = payInfo.getNoncestr();
        req.timeStamp = payInfo.getTimestamp();
        req.packageValue = "Sign=WXPay";
        req.sign = payInfo.getSign();
        req.extData = payInfo.getOrderNumber(); // optional
        ShareUtil.initWeixin(this);
        ShareUtil.api.sendReq(req);

        if (wxPayReceiver != null) return;
        wxPayReceiver = wxPayReceiver();
        IntentFilter intentFilter = new IntentFilter(getPackageName() + ".wxpay");
        LocalBroadcastManager.getInstance(this).registerReceiver(wxPayReceiver, intentFilter);
    }

    private BroadcastReceiver wxPayReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                payFinish();
            }
        };
    }

    private void pocketPay(PayInfo payInfo) {
        Bundle bundle = new Bundle();
        bundle.putString("money", "￥1");
        bundle.putString("orderCode", payInfo.getOrderNumber());
        Intent intent = new Intent(this, PocketPayActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_PAY);
        overridePendingTransition(R.anim.bottom_to_top, R.anim.anim_null);
    }

    private void payFinish() {
        webview.callHandler("payBack", "", null);
    }

    @Override
    // 设置回退
    // 5、覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //按下返回键并且webview界面可以返回
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //QQ回调
        if (shareUi != null && shareUi.qqShareListener != null) {
            Tencent.onActivityResultData(requestCode, resultCode, data, shareUi.qqShareListener);
        }
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                String faceOriginal = images.get(0).path;
                compressPic(faceOriginal);
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }

        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode) {
            case 113:
                settingPayPw(data);
                break;
            case 115:
                setPayPwFinish(data);
                break;
            case REQUEST_PAY:
                payFinish();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                if (webview.canGoBack()) {
                    if (webview.getUrl().equals(url)) {
                        finish();
                    } else {
                        webview.goBack();
                    }
                } else {
                    finish();
                }
                break;
            case R.id.back:
                if (webview.canGoBack()) {
                    if (webview.getUrl().equals(url)) {
                        finish();
                    } else {
                        webview.goBack();
                    }
                } else {
                    finish();
                }
                break;
            case R.id.finish:
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.destroy();
        if (shareUi != null) {
            shareUi.onDestroy();
            shareUi = null;
        }
        if (wxPayReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(wxPayReceiver);
        }
    }

    private ProgressDialog waitingDialog;

    private void showDialog(String msg) {
        if (waitingDialog != null) waitingDialog.cancel();
        else waitingDialog = new ProgressDialog(WebViewScriptActivity.this);
        waitingDialog.setMessage(msg);
        waitingDialog.setCanceledOnTouchOutside(false);
        waitingDialog.show();
    }

    private void compressPic(String faceOriginal) {
        CompressAction compressAction = new CompressAction(WebViewScriptActivity.this);
        ArrayList<String> strings = new ArrayList<>();
        strings.add(faceOriginal);
        compressAction.setCompressListener(new CompressAction.CompressListener() {
            @Override
            public void onPreExecute() {
                showDialog("图片压缩中...");
            }

            @Override
            public void onUpdate(int index) {

            }

            @Override
            public void onPostExecute(List<String> imageList) {
                if (imageList != null && !imageList.isEmpty()) {
                    String path = imageList.get(0);
                    upload(path);
                }
                if (waitingDialog != null && waitingDialog.isShowing()) waitingDialog.cancel();
            }

            @Override
            public void onCancle() {
                if (waitingDialog != null && waitingDialog.isShowing()) {
                    waitingDialog.cancel();
                }
            }
        });
        compressAction.execute(strings);
    }

    private void upload(String picPath) {
        showDialog("图片上传中...");
        String extras = FileUtils.INSTANCE.getFileExtraName(picPath);
        final String savedFile = System.currentTimeMillis() + extras;
        OSSUtils.upLoadFile(getString(R.string.oss_bucketname), savedFile, picPath, null, new OSSCompletedCallback() {
            @Override
            public void onSuccess(OSSRequest ossRequest, OSSResult ossResult) {
                onResult(currentGetImgId + "," + savedFile);
                if (waitingDialog != null && waitingDialog.isShowing()) {
                    waitingDialog.cancel();
                }
            }

            @Override
            public void onFailure(OSSRequest ossRequest, ClientException e, ServiceException e1) {
                LogUtils.e("ClientException:" + e.getMessage() + "  ServiceException:" + e1.getMessage());
                if (waitingDialog != null && waitingDialog.isShowing()) {
                    waitingDialog.cancel();
                }
            }
        });
    }

    private void settingPayPw(Intent data) {
        if (data == null || data.getExtras() == null) return;
        Intent intent = new Intent(this, PayPwActivity.class);
        intent.putExtras(data);
        startActivityForResult(intent, PwSettingVm.Companion.getRESULT_SET_PAY_PW());
        overridePendingTransition(R.anim.bottom_to_top, R.anim.anim_null);
    }

    private void setPayPwFinish(Intent data) {
        if (data == null || data.getExtras() == null) return;
        if (!data.getExtras().getBoolean("success", false)) return;
        webview.callHandler("setPayPassBack", "", null);
    }

    private void onResult(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webview.callHandler("setImg", result, new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) {
                        ILog.INSTANCE.e("===data===", data);
                    }
                });
            }
        });
    }
}

package com.yidao.threekmo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.widget.RelativeLayout;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.yidao.threekmo.R;
import com.yidao.threekmo.activitys.IndexActivity;
import com.yidao.threekmo.parameter.WebParameters;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.LoginUtils;

/**
 * Created by Smart~ on 2017/10/12.
 */

public class ShopFragment extends BaseFragment{

    private BridgeWebView webview;
    private String url = "";

    public static final ShopFragment newInstance(String title) {
        ShopFragment f = new ShopFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(title, title);
        f.setArguments(bdl);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }

    private void initViews() {

        webview = (BridgeWebView) getView().findViewById(R.id.webview);
        RelativeLayout.LayoutParams llp_webview = (RelativeLayout.LayoutParams)webview.getLayoutParams();
        llp_webview.topMargin = CommonUtil.getStatusBarHeight(getActivity());
        //http://192.168.0.83:8081/interior/manage/activity/lifeRelated
        String token = LoginUtils.getToken(getActivity());
        url = WebParameters.SERVERURL + "interior/manage/activity/mall";
        webview.loadUrl(url);

        webview.registerHandler("goToShare", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
//                Toast.makeText(WebViewScriptActivity.this, "pay--->，"+ data, Toast.LENGTH_SHORT).show();
//                function.onCallBack("测试blog");

            }
        });

        /***打开本地缓存提供JS调用**/
        webview.getSettings().setDomStorageEnabled(true);
        // Set cache size to 8 mb by default. should be more than enough
        webview.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.getSettings().setDatabaseEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setGeolocationEnabled(true);
        // This next one is crazy. It's the DEFAULT location for your app's cache
        // But it didn't work for me without this line.
        // UPDATE: no hardcoded path. Thanks to Kevin Hawkins
        String appCachePath = ((IndexActivity)getActivity()).getApplicationContext().getCacheDir().getAbsolutePath();
        webview.getSettings().setAppCachePath(appCachePath);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setAppCacheEnabled(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //清空所有Cookie
        CookieSyncManager.createInstance(getActivity());  //Create a singleton CookieSyncManager within a context
        CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
        cookieManager.removeAllCookie();// Removes all cookies.
        CookieSyncManager.getInstance().sync(); // forces sync manager to sync now

        webview.setWebChromeClient(null);
        webview.setWebViewClient(null);
        webview.getSettings().setJavaScriptEnabled(false);
        webview.clearCache(true);
    }

}

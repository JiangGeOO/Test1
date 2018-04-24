package com.github.lzyzsd.jsbridge;

import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by bruce on 10/28/15.
 */
public class BridgeWebViewClient extends WebViewClient {
    private BridgeWebView webView;
    private Context mContext;

    public BridgeWebViewClient(BridgeWebView webView, Context mContext) {
        this.webView = webView;
        this.mContext = mContext;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//        try {
//            url = URLDecoder.decode(url, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        if (url.contains("alipays://platformapi/startApp") || url.contains("weixin://wap/pay")) {
            webView.appPay(url);
            return true;
        } else if (url.startsWith(BridgeUtil.YY_RETURN_DATA)) { // 如果是返回数据
            try {
                url = URLDecoder.decode(url, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            webView.handlerReturnData(url);
            return true;
        } else if (url.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) { //
            try {
                url = URLDecoder.decode(url, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            webView.flushMessageQueue();
            return true;
        } else {
            return super.shouldOverrideUrlLoading(view, url);
        }
//        if (url.contains("alipays://platformapi/startApp") || url.contains("weixin://wap/pay") || url.startsWith(BridgeUtil.YY_RETURN_DATA) || url.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) {
//            if (url.contains("alipays://platformapi/startApp") || url.contains("weixin://wap/pay")) {
////                try {
////                    Uri uri = Uri.parse(url);
////                    Intent intent = Intent.parseUri(url,Intent.URI_INTENT_SCHEME);
////                    intent.addCategory("android.intent.category.BROWSABLE");
////                    intent.setComponent(null);
////                    // intent.setSelector(null);
////                    mContext.startActivity(intent);
////                } catch (Exception e) {
////                    Toast.makeText(mContext,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
////                }
//                webView.appPay(url);
//            } else if (url.startsWith(BridgeUtil.YY_RETURN_DATA)) {
//                webView.handlerReturnData(url);
//            } else if (url.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) {
//                webView.flushMessageQueue();
//            }
//            return true;
//        } else {
//            return super.shouldOverrideUrlLoading(view, url);
//        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        if (BridgeWebView.toLoadJs != null) {
            BridgeUtil.webViewLoadLocalJs(view, BridgeWebView.toLoadJs);
        }

        //
        if (webView.getStartupMessage() != null) {
            for (Message m : webView.getStartupMessage()) {
                webView.dispatchMessage(m);
            }
            webView.setStartupMessage(null);
        }
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    
}
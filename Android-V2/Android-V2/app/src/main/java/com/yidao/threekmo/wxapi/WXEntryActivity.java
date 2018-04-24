package com.yidao.threekmo.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yidao.threekmo.activitys.BaseActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Smart~ on 2017/9/29.
 */

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IWXAPI api = WXAPIFactory.createWXAPI(this, "wx527758ac3fedd1e6", false);
        api.handleIntent(getIntent(),this);
        finish();
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    private void weChatSuccess(BaseResp baseResp) {
        if (!(baseResp instanceof SendAuth.Resp)) {
            Toast.makeText(WXEntryActivity.this,"分享成功",Toast.LENGTH_SHORT).show();
            return;
        }
        SendAuth.Resp sendAuth = (SendAuth.Resp) baseResp;
        Intent intent = new Intent(getPackageName() + ".weChatLogin");
        intent.putExtra("code", sendAuth.code);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) { //根据需要的情况进行处理
            case BaseResp.ErrCode.ERR_OK:
                weChatSuccess(baseResp);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //用户取消
                Toast.makeText(WXEntryActivity.this,"分享取消",Toast.LENGTH_SHORT).show();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //认证被否决
                Toast.makeText(WXEntryActivity.this,"认证失败",Toast.LENGTH_SHORT).show();
                break;
            case BaseResp.ErrCode.ERR_SENT_FAILED:
                //发送失败
                Toast.makeText(WXEntryActivity.this,"分享失败",Toast.LENGTH_SHORT).show();
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                //不支持错误
                Toast.makeText(WXEntryActivity.this,"分享错误",Toast.LENGTH_SHORT).show();
                break;
            case BaseResp.ErrCode.ERR_COMM:
                //一般错误
                Toast.makeText(WXEntryActivity.this,"分享失败",Toast.LENGTH_SHORT).show();
                break;
            default:
                //其他不可名状的情况
                break;
        }
    }
}

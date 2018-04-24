package com.yidao.threekmo.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yidao.threekmo.activitys.OrderPaySucessOrFailActivity;
import com.yidao.threekmo.activitys.OrderPayingActivity;
import com.yidao.threekmo.utils.CommonUtil;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, "wx527758ac3fedd1e6");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        Log.e("**********", "onPayFinish, errCode = " + resp.errCode);
//        if(resp.errCode == -2){
//            Intent intent = new Intent(WXPayEntryActivity.this, OrderPaySucessOrFailActivity.class);
//            intent.putExtra("orderNumStr", CommonUtil.wxOrderNum);
//            startActivity(intent);
//            finish();
//        }

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            Intent intent = new Intent(getPackageName() + ".wxpay");
            intent.putExtra("success", resp.errCode == 0);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            finish();
        }
    }
}
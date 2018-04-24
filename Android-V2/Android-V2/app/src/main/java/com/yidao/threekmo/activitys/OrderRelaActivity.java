package com.yidao.threekmo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.ijustyce.fastkotlin.glide.ImageLoader;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yidao.threekmo.R;
import com.yidao.threekmo.bean.AliPayResult;
import com.yidao.threekmo.bean.ShopDetailResult;
import com.yidao.threekmo.bean.WxPayResult;
import com.yidao.threekmo.customview.ChoosePopupWindow;
import com.yidao.threekmo.retrofit_server.SubjectServer;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.LoginUtils;
import com.yidao.threekmo.utils.RetrofitUtils;
import com.yidao.threekmo.v2.utils.AppImage;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRelaActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout order_rela;
    private ImageView order_back;
    private TextView order_title;
    private RelativeLayout order_info_rela;
    private RelativeLayout order_info_human;
    private TextView order_info_human_text;
    private EditText order_info_human_edit;
    private RelativeLayout order_info_phone;
    private TextView order_info_phone_text;
    private EditText order_info_phone_edit;
    private RelativeLayout order_info_address;
    private TextView order_info_address_text;
    private EditText order_info_address_edit;
    private RelativeLayout shop_info;
    private RelativeLayout shop_house_rela;
    private ImageView shop_house_img;
    private TextView shop_house_text;
    private RelativeLayout shop_order_rela;
    private ImageView order_image;
    private TextView order_name;
    private TextView order_num;
    private TextView order_one;
    private RelativeLayout shop_song;
    private TextView shop_song_text;
    private TextView shop_song_other;
    private RelativeLayout shop_num;
    private TextView shop_num_num;
    private TextView shop_num_scale;
    private TextView shop_num_money;
    private RelativeLayout rela_rela;
    private TextView rela_ok;
    private ChoosePopupWindow choosePopupWindow;

    private long id;
    private ShopDetailResult shopDetailResult;
    private String orderNumStr = "";

    private static final int SDK_PAY_FLAG = 1;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    Map<String,String> result = (Map<String, String>) msg.obj;
                    String resultStatus = result.get("resultStatus");
                    String memo = result.get("memo");
                    if(resultStatus.equals("9000")){
                        Toast.makeText(OrderRelaActivity.this,"支付成功",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(OrderRelaActivity.this,OrderPayingActivity.class);
                        intent.putExtra("orderNumStr",orderNumStr);
                        Log.e("&&&&&&&",orderNumStr);
                        startActivity(intent);
                        finish();
                    }else if(resultStatus.equals("6001")){
                        Intent intent = new Intent(OrderRelaActivity.this, OrderPaySucessOrFailActivity.class);
                        intent.putExtra("orderNumStr", orderNumStr);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(OrderRelaActivity.this,memo,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(OrderRelaActivity.this,OrderPayingActivity.class);
                        intent.putExtra("orderNumStr",orderNumStr);
                        Log.e("&&&&&&&&&&&&",orderNumStr);
                        startActivity(intent);
                        finish();
                    }

                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_rela);

        if (getIntent() != null) {
            id = getIntent().getLongExtra("id", 0L);
            shopDetailResult = (ShopDetailResult) getIntent().getSerializableExtra("shopDetailResult");
        }

        initViews();
        setDta();

    }

    private void setDta() {

        String shopName = shopDetailResult.getShopName();
        String name = shopDetailResult.getName();
        String price = String.valueOf(shopDetailResult.getSellingPrice());
        String photo = shopDetailResult.getPhotoUrl();

        shop_house_text.setText("店铺名称：" + shopName);
        order_name.setText(name);
        shop_num_money.setText("￥" + price);

        AppImage.INSTANCE.load(order_image, photo, 12, ImageLoader.TYPE_ALL);

        choosePopupWindow = new ChoosePopupWindow(OrderRelaActivity.this, shopDetailResult.getSellingPrice());
        choosePopupWindow.setOnButtonClickListener(new ChoosePopupWindow.OnButtonClickListener() {
            @Override
            public void itemButtonListener(int scaleType) {
//                Toast.makeText(OrderRelaActivity.this,scaleType + "",Toast.LENGTH_SHORT).show();
                //等待回掉确认支付完成
                if (scaleType == 1) {
                    //支付宝
                    aliPayServer();
                } else if (scaleType == 2) {
                    //微信
                    wxPaySever();
                }
//                Intent intent = new Intent(OrderRelaActivity.this,OrderPayingActivity.class);
//                intent.putExtra("orderNumber",orderDetailResult.getOrderNumber());
//                startActivity(intent);
            }
        });
    }

    private void initViews() {

        order_rela = (RelativeLayout) findViewById(R.id.order_rela);
        RelativeLayout.LayoutParams llp_order_rela = (RelativeLayout.LayoutParams) order_rela.getLayoutParams();
        llp_order_rela.height = CommonUtil.getRealWidth(130);

        order_back = (ImageView) findViewById(R.id.order_back);
        RelativeLayout.LayoutParams llp_order_back = (RelativeLayout.LayoutParams) order_back.getLayoutParams();
        llp_order_back.width = CommonUtil.getRealWidth(48);
        llp_order_back.height = llp_order_back.width;
        llp_order_back.leftMargin = CommonUtil.getRealWidth(30);
        llp_order_back.topMargin = CommonUtil.getRealWidth(60);

        order_title = (TextView) findViewById(R.id.order_title);
        order_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(36));
        RelativeLayout.LayoutParams llp_order_title = (RelativeLayout.LayoutParams) order_title.getLayoutParams();
        llp_order_title.topMargin = CommonUtil.getRealWidth(58);

        order_info_rela = (RelativeLayout) findViewById(R.id.order_info_rela);
        RelativeLayout.LayoutParams llp_order_info_rela = (RelativeLayout.LayoutParams) order_info_rela.getLayoutParams();
        llp_order_info_rela.height = CommonUtil.getRealWidth(260);

        order_info_human = (RelativeLayout) findViewById(R.id.order_info_human);
        RelativeLayout.LayoutParams llp_order_info_human = (RelativeLayout.LayoutParams) order_info_human.getLayoutParams();
        llp_order_info_human.height = CommonUtil.getRealWidth(84);

        order_info_human_text = (TextView) findViewById(R.id.order_info_human_text);
        order_info_human_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_order_info_human_text = (RelativeLayout.LayoutParams) order_info_human_text.getLayoutParams();
        llp_order_info_human_text.leftMargin = CommonUtil.getRealWidth(30);

        order_info_human_edit = (EditText) findViewById(R.id.order_info_human_edit);
        order_info_human_edit.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_order_info_human_edit = (RelativeLayout.LayoutParams) order_info_human_edit.getLayoutParams();
        llp_order_info_human_edit.leftMargin = CommonUtil.getRealWidth(202);
        llp_order_info_human_edit.rightMargin = CommonUtil.getRealWidth(30);

        order_info_phone = (RelativeLayout) findViewById(R.id.order_info_phone);
        RelativeLayout.LayoutParams llp_order_info_phone = (RelativeLayout.LayoutParams) order_info_phone.getLayoutParams();
        llp_order_info_phone.height = CommonUtil.getRealWidth(84);

        order_info_phone_text = (TextView) findViewById(R.id.order_info_phone_text);
        order_info_phone_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_order_info_phone_text = (RelativeLayout.LayoutParams) order_info_phone_text.getLayoutParams();
        llp_order_info_phone_text.leftMargin = CommonUtil.getRealWidth(30);

        order_info_phone_edit = (EditText) findViewById(R.id.order_info_phone_edit);
        order_info_phone_edit.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_order_info_phone_edit = (RelativeLayout.LayoutParams) order_info_phone_edit.getLayoutParams();
        llp_order_info_phone_edit.leftMargin = CommonUtil.getRealWidth(202);
        llp_order_info_phone_edit.rightMargin = CommonUtil.getRealWidth(30);

        order_info_address = (RelativeLayout) findViewById(R.id.order_info_address);
        RelativeLayout.LayoutParams llp_order_info_address = (RelativeLayout.LayoutParams) order_info_address.getLayoutParams();
        llp_order_info_address.height = CommonUtil.getRealWidth(84);

        order_info_address_text = (TextView) findViewById(R.id.order_info_address_text);
        order_info_address_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_order_info_address_text = (RelativeLayout.LayoutParams) order_info_address_text.getLayoutParams();
        llp_order_info_address_text.leftMargin = CommonUtil.getRealWidth(30);

        order_info_address_edit = (EditText) findViewById(R.id.order_info_address_edit);
        order_info_address_edit.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_order_info_address_edit = (RelativeLayout.LayoutParams) order_info_address_edit.getLayoutParams();
        llp_order_info_address_edit.leftMargin = CommonUtil.getRealWidth(202);
        llp_order_info_address_edit.rightMargin = CommonUtil.getRealWidth(30);

        shop_info = (RelativeLayout) findViewById(R.id.shop_info);
        RelativeLayout.LayoutParams llp_shop_info = (RelativeLayout.LayoutParams) shop_info.getLayoutParams();
        llp_shop_info.height = CommonUtil.getRealWidth(560);
        llp_shop_info.topMargin = CommonUtil.getRealWidth(20);

        shop_house_rela = (RelativeLayout) findViewById(R.id.shop_house_rela);
        RelativeLayout.LayoutParams llp_shop_house_rela = (RelativeLayout.LayoutParams) shop_house_rela.getLayoutParams();
        llp_shop_house_rela.height = CommonUtil.getRealWidth(80);

        shop_house_img = (ImageView) findViewById(R.id.shop_house_img);
        RelativeLayout.LayoutParams llp_shop_house_img = (RelativeLayout.LayoutParams) shop_house_img.getLayoutParams();
        llp_shop_house_img.width = CommonUtil.getRealWidth(40);
        llp_shop_house_img.height = llp_shop_house_img.width;
        llp_shop_house_img.leftMargin = CommonUtil.getRealWidth(30);

        shop_house_text = (TextView) findViewById(R.id.shop_house_text);
        shop_house_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_shop_house_text = (RelativeLayout.LayoutParams) shop_house_text.getLayoutParams();
        llp_shop_house_text.leftMargin = CommonUtil.getRealWidth(86);

        shop_order_rela = (RelativeLayout) findViewById(R.id.shop_order_rela);
        RelativeLayout.LayoutParams llp_shop_order_rela = (RelativeLayout.LayoutParams) shop_order_rela.getLayoutParams();
        llp_shop_order_rela.height = CommonUtil.getRealWidth(298);

        order_image = (ImageView) findViewById(R.id.order_image);
        RelativeLayout.LayoutParams llp_order_image = (RelativeLayout.LayoutParams) order_image.getLayoutParams();
        llp_order_image.width = CommonUtil.getRealWidth(160);
        llp_order_image.height = CommonUtil.getRealWidth(160);
        llp_order_image.leftMargin = CommonUtil.getRealWidth(30);
        llp_order_image.topMargin = CommonUtil.getRealWidth(20);

        order_name = (TextView) findViewById(R.id.order_name);
        order_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_order_name = (RelativeLayout.LayoutParams) order_name.getLayoutParams();
        llp_order_name.leftMargin = CommonUtil.getRealWidth(214);
        llp_order_name.topMargin = CommonUtil.getRealWidth(20);

        order_num = (TextView) findViewById(R.id.order_num);
        order_num.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_order_num = (RelativeLayout.LayoutParams) order_num.getLayoutParams();
        llp_order_num.leftMargin = CommonUtil.getRealWidth(30);
        llp_order_num.topMargin = CommonUtil.getRealWidth(212);

        order_one = (TextView) findViewById(R.id.order_one);
        order_one.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_order_one = (RelativeLayout.LayoutParams) order_one.getLayoutParams();
        llp_order_one.leftMargin = CommonUtil.getRealWidth(626);
        llp_order_one.topMargin = CommonUtil.getRealWidth(212);

        shop_song = (RelativeLayout) findViewById(R.id.shop_song);
        RelativeLayout.LayoutParams llp_shop_song = (RelativeLayout.LayoutParams) shop_song.getLayoutParams();
        llp_shop_song.height = CommonUtil.getRealWidth(84);

        shop_song_text = (TextView) findViewById(R.id.shop_song_text);
        shop_song_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_shop_song_text = (RelativeLayout.LayoutParams) shop_song_text.getLayoutParams();
        llp_shop_song_text.leftMargin = CommonUtil.getRealWidth(30);

        shop_song_other = (TextView) findViewById(R.id.shop_song_other);
        shop_song_other.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_shop_song_other = (RelativeLayout.LayoutParams) shop_song_other.getLayoutParams();
        llp_shop_song_other.rightMargin = CommonUtil.getRealWidth(30);

        shop_num = (RelativeLayout) findViewById(R.id.shop_num);
        RelativeLayout.LayoutParams llp_shop_num = (RelativeLayout.LayoutParams) shop_num.getLayoutParams();
        llp_shop_num.height = CommonUtil.getRealWidth(90);

        shop_num_num = (TextView) findViewById(R.id.shop_num_num);
        shop_num_num.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_shop_num_num = (RelativeLayout.LayoutParams) shop_num_num.getLayoutParams();
        llp_shop_num_num.leftMargin = CommonUtil.getRealWidth(30);

        shop_num_scale = (TextView) findViewById(R.id.shop_num_scale);
        shop_num_scale.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_shop_num_scale = (RelativeLayout.LayoutParams) shop_num_scale.getLayoutParams();
        llp_shop_num_scale.rightMargin = CommonUtil.getRealWidth(5);

        shop_num_money = (TextView) findViewById(R.id.shop_num_money);
        shop_num_money.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_shop_num_money = (RelativeLayout.LayoutParams) shop_num_money.getLayoutParams();
        llp_shop_num_money.rightMargin = CommonUtil.getRealWidth(30);

        rela_rela = (RelativeLayout) findViewById(R.id.rela_rela);
        RelativeLayout.LayoutParams llp_rela_rela = (RelativeLayout.LayoutParams) rela_rela.getLayoutParams();
        llp_rela_rela.height = CommonUtil.getRealWidth(98);

        rela_ok = (TextView) findViewById(R.id.rela_ok);
        rela_ok.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));

        rela_rela.setOnClickListener(this);
        order_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rela_rela: {
                String name = order_info_human_edit.getText().toString();
                String phone = order_info_phone_edit.getText().toString();
                String address = order_info_address_edit.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address)) {
                    if (TextUtils.isEmpty(name)) {
                        Toast.makeText(OrderRelaActivity.this, "收货人不能为空", Toast.LENGTH_SHORT).show();
                    }
                    if (TextUtils.isEmpty(phone)) {
                        Toast.makeText(OrderRelaActivity.this, "联系电话不能为空", Toast.LENGTH_SHORT).show();
                    }
                    if (TextUtils.isEmpty(address)) {
                        Toast.makeText(OrderRelaActivity.this, "详细地址不能为空", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if(CommonUtil.isMobileNum(phone)){
                        choosePopupWindow.showAtLocation(OrderRelaActivity.this.findViewById(R.id.blank_rela),
                                Gravity.CENTER_HORIZONTAL, 0, 0);
                    }else{
                        Toast.makeText(OrderRelaActivity.this,"请输入正确手机号码",Toast.LENGTH_SHORT).show();
                    }

                }
            }
            break;

            case R.id.order_back: {
                finish();
            }
            break;

            default:
                break;
        }
    }

    private void aliPayServer() {

        String name = order_info_human_edit.getText().toString();
        String phone = order_info_phone_edit.getText().toString();
        String address = order_info_address_edit.getText().toString();

        Call<AliPayResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).aliPayServer(name, phone, address, LoginUtils.getToken(OrderRelaActivity.this), String.valueOf(shopDetailResult.getId()));
        addCall(call);
        call.enqueue(new Callback<AliPayResult>() {
            @Override
            public void onResponse(Call<AliPayResult> call, Response<AliPayResult> response) {
                AliPayResult body = response.body();
                if (body.getRspCode() == 0) {
                    String aliPayStr = body.getData().getOrderString();
                    orderNumStr = body.getData().getOrderNumber();

                    final String orderInfo = aliPayStr;   // 订单信息

                    Runnable payRunnable = new Runnable() {

                        @Override
                        public void run() {
                            // 构造PayTask 对象
                            PayTask alipay = new PayTask(OrderRelaActivity.this);
                            // 调用支付接口，获取支付结果
                            Map<String, String> result = alipay.payV2(orderInfo, true);


                            Log.e("^^^^^^^^^^^",result+"");
                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            mHandler.sendMessage(msg);

                        }
                    };
                    // 必须异步调用
                    Thread payThread = new Thread(payRunnable);
                    payThread.start();

                }

            }

            @Override
            public void onFailure(Call<AliPayResult> call, Throwable t) {
                Log.e("*********************", t.getMessage().toString());
            }
        });
    }

    private void wxPaySever() {

        String name = order_info_human_edit.getText().toString();
        String phone = order_info_phone_edit.getText().toString();
        String address = order_info_address_edit.getText().toString();

        Call<WxPayResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).wxPayServer(name, phone, address, LoginUtils.getToken(OrderRelaActivity.this), String.valueOf(shopDetailResult.getId()));
        addCall(call);
        call.enqueue(new Callback<WxPayResult>() {
            @Override
            public void onResponse(Call<WxPayResult> call, Response<WxPayResult> response) {
                WxPayResult body = response.body();
                if (body.getRspCode() == 0) {
                    //三个地方用到appid
                    orderNumStr = body.getData().getOrderNumber();
                    CommonUtil.wxOrderNum = orderNumStr;
                    IWXAPI api = WXAPIFactory.createWXAPI(OrderRelaActivity.this, "wx527758ac3fedd1e6");
                    api.registerApp("wx527758ac3fedd1e6");
                    PayReq payReq = new PayReq();
                    payReq.appId = "wx527758ac3fedd1e6";
                    payReq.partnerId = body.getData().getPartnerid();
                    payReq.prepayId = body.getData().getPrepayid();
                    payReq.packageValue = "Sign=WXPay";
                    payReq.nonceStr = body.getData().getNoncestr();
                    payReq.timeStamp = body.getData().getTimestamp();
                    payReq.sign = body.getData().getSign();
                    api.sendReq(payReq);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<WxPayResult> call, Throwable t) {
                Toast.makeText(OrderRelaActivity.this,t.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}

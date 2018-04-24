package com.yidao.threekmo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ijustyce.fastkotlin.glide.ImageLoader;
import com.yidao.threekmo.R;
import com.yidao.threekmo.bean.OrderDetailResult;
import com.yidao.threekmo.bean.OrderRelaResult;
import com.yidao.threekmo.retrofit_server.SubjectServer;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.LoginUtils;
import com.yidao.threekmo.utils.RetrofitUtils;
import com.yidao.threekmo.v2.utils.AppImage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderPaySucessOrFailActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout title_rela;
    private ImageView title_back;
    private TextView title_text;
    private RelativeLayout pay_result_rela;
    private TextView pay_result_text;
    private ImageView order_image;
    private RelativeLayout people_info_rela;
    private RelativeLayout shop_info_rela;
    private RelativeLayout people_pay;
    private TextView people_pay_text;
    private TextView people_pay_name;
    private RelativeLayout people_phone;
    private TextView people_phone_text;
    private TextView people_phone_name;
    private RelativeLayout people_address;
    private TextView people_address_text;
    private TextView people_address_name;
    private RelativeLayout people_order;
    private TextView people_order_text;
    private TextView people_order_name;
    private RelativeLayout shop_shop;
    private ImageView shop_image;
    private TextView shop_name;
    private ImageView shop_right;
    private RelativeLayout shop_info;
    private ImageView shop_info_image;
    private TextView shop_info_name;
    private TextView shop_info_money;
    private TextView shop_info_money_num;
    private RelativeLayout shop_pay;
    private TextView shop_pay_text;
    private TextView shop_pay_money;
    private RelativeLayout go;
    private RelativeLayout go_on;
    private TextView go_on_text;
    private RelativeLayout my_order;
    private TextView my_order_text;
    private RelativeLayout repay;
    private TextView repay_text;

    private String orderNumStr = "";
    private OrderDetailResult orderDetailResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay_sucess_or_fail);

        if(getIntent() != null){
            orderNumStr = getIntent().getStringExtra("orderNumStr");
//            Log.e("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&",orderNumStr);
        }

        initViews();
        achieveData();

    }

    private void achieveData() {
        String token = LoginUtils.getToken(OrderPaySucessOrFailActivity.this);
        Call<OrderRelaResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).orderStatus(token,orderNumStr);
        addCall(call);
        call.enqueue(new Callback<OrderRelaResult>() {
            @Override
            public void onResponse(Call<OrderRelaResult> call, Response<OrderRelaResult> response) {
                OrderRelaResult body = response.body();
                if(body != null){
                    if(body.getRspCode() == 0){
                        orderDetailResult = body.getData().getData();
                        setData();
                    }
                }

            }

            @Override
            public void onFailure(Call<OrderRelaResult> call, Throwable t) {
                Log.e("**********************",t.getMessage().toString());
            }
        });
    }

    private void setData(){
        if(orderDetailResult.getStatus().equals("waitFor")){
            title_text.setText("支付成功");
            pay_result_text.setText("支付成功");
            go_on.setVisibility(View.VISIBLE);
            my_order.setVisibility(View.VISIBLE);
            repay.setVisibility(View.GONE);
            order_image.setImageResource(R.mipmap.order_success);
        }else if(orderDetailResult.getStatus().equals("payment")){
            title_text.setText("支付失败");
            pay_result_text.setText("支付失败");
            go_on.setVisibility(View.GONE);
            my_order.setVisibility(View.GONE);
            repay.setVisibility(View.VISIBLE);
            order_image.setImageResource(R.mipmap.order_fail);
        }else if(orderDetailResult.getStatus().equals("payment")){
            title_text.setText("支付失败");
            pay_result_text.setText("支付失败");
            go_on.setVisibility(View.GONE);
            my_order.setVisibility(View.GONE);
            repay.setVisibility(View.VISIBLE);
            order_image.setImageResource(R.mipmap.order_fail);
        }

        people_pay_name.setText(orderDetailResult.getConsigneeName());
        people_phone_name.setText(orderDetailResult.getConsigneePhone());
        people_address_name.setText(orderDetailResult.getConsigneeAddress());
        people_order_name.setText(orderDetailResult.getOrderNumber());

        shop_name.setText(orderDetailResult.getShopName());
        AppImage.INSTANCE.load(shop_info_image, orderDetailResult.getPhoto(), 12, ImageLoader.TYPE_ALL);

        shop_info_name.setText(orderDetailResult.getName());
        shop_info_money.setText("￥"+orderDetailResult.getSellingPrice());

        shop_pay_money.setText("￥"+orderDetailResult.getActualPayment());



    }

    private void initViews() {
        title_rela = (RelativeLayout) findViewById(R.id.title_rela);
        RelativeLayout.LayoutParams llp_title_rela = (RelativeLayout.LayoutParams)title_rela.getLayoutParams();
        llp_title_rela.height = CommonUtil.getRealWidth(130);

        title_back = (ImageView) findViewById(R.id.title_back);
        RelativeLayout.LayoutParams llp_title_back = (RelativeLayout.LayoutParams)title_back.getLayoutParams();
        llp_title_back.width = CommonUtil.getRealWidth(48);
        llp_title_back.height = llp_title_back.width;
        llp_title_back.leftMargin = CommonUtil.getRealWidth(30);
        llp_title_back.topMargin = CommonUtil.getRealWidth(60);

        title_text = (TextView) findViewById(R.id.title_text);
        title_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(36));
        RelativeLayout.LayoutParams llp_title_text = (RelativeLayout.LayoutParams)title_text.getLayoutParams();
        llp_title_text.topMargin = CommonUtil.getRealWidth(58);

        pay_result_rela = (RelativeLayout) findViewById(R.id.pay_result_rela);
        RelativeLayout.LayoutParams llp_pay_result_rela = (RelativeLayout.LayoutParams)pay_result_rela.getLayoutParams();
        llp_pay_result_rela.height = CommonUtil.getRealWidth(216);

        pay_result_text = (TextView) findViewById(R.id.pay_result_text);
        pay_result_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(36));
        RelativeLayout.LayoutParams llp_pay_result_text = (RelativeLayout.LayoutParams)pay_result_text.getLayoutParams();
        llp_pay_result_text.leftMargin = CommonUtil.getRealWidth(70);

        order_image = (ImageView) findViewById(R.id.order_image);
        RelativeLayout.LayoutParams llp_order_image = (RelativeLayout.LayoutParams)order_image.getLayoutParams();
        llp_order_image.width = CommonUtil.getRealWidth(258);
        llp_order_image.height = CommonUtil.getRealWidth(118);
        llp_order_image.rightMargin = CommonUtil.getRealWidth(62);

        people_info_rela = (RelativeLayout) findViewById(R.id.people_info_rela);
        RelativeLayout.LayoutParams llp_people_info_rela = (RelativeLayout.LayoutParams)people_info_rela.getLayoutParams();
        llp_people_info_rela.height = CommonUtil.getRealWidth(388);
        llp_people_info_rela.topMargin = CommonUtil.getRealWidth(20);

        shop_info_rela = (RelativeLayout) findViewById(R.id.shop_info_rela);
        RelativeLayout.LayoutParams llp_shop_info_rela = (RelativeLayout.LayoutParams)shop_info_rela.getLayoutParams();
        llp_shop_info_rela.height = CommonUtil.getRealWidth(516);
        llp_shop_info_rela.topMargin = CommonUtil.getRealWidth(20);

        people_pay = (RelativeLayout) findViewById(R.id.people_pay);
        RelativeLayout.LayoutParams llp_people_pay = (RelativeLayout.LayoutParams)people_pay.getLayoutParams();
        llp_people_pay.height = CommonUtil.getRealWidth(84);

        people_pay_text = (TextView) findViewById(R.id.people_pay_text);
        people_pay_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_people_pay_text = (RelativeLayout.LayoutParams)people_pay_text.getLayoutParams();
        llp_people_pay_text.leftMargin = CommonUtil.getRealWidth(30);

        people_pay_name = (TextView) findViewById(R.id.people_pay_name);
        people_pay_name.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_people_pay_name = (RelativeLayout.LayoutParams)people_pay_name.getLayoutParams();
        llp_people_pay_name.leftMargin = CommonUtil.getRealWidth(200);

        people_phone = (RelativeLayout) findViewById(R.id.people_phone);
        RelativeLayout.LayoutParams llp_people_phone = (RelativeLayout.LayoutParams)people_phone.getLayoutParams();
        llp_people_phone.height = CommonUtil.getRealWidth(84);

        people_phone_text = (TextView) findViewById(R.id.people_phone_text);
        people_phone_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_people_phone_text = (RelativeLayout.LayoutParams)people_phone_text.getLayoutParams();
        llp_people_phone_text.leftMargin = CommonUtil.getRealWidth(30);

        people_phone_name = (TextView) findViewById(R.id.people_phone_name);
        people_phone_name.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_people_phone_name = (RelativeLayout.LayoutParams)people_phone_name.getLayoutParams();
        llp_people_phone_name.leftMargin = CommonUtil.getRealWidth(200);

        people_address = (RelativeLayout) findViewById(R.id.people_address);
        people_address.setMinimumHeight(CommonUtil.getRealWidth(84));
        RelativeLayout.LayoutParams llp_people_address = (RelativeLayout.LayoutParams)people_address.getLayoutParams();

        people_address_text = (TextView) findViewById(R.id.people_address_text);
        people_address_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_people_address_text = (RelativeLayout.LayoutParams)people_address_text.getLayoutParams();
        llp_people_address_text.leftMargin = CommonUtil.getRealWidth(30);

        people_address_name = (TextView) findViewById(R.id.people_address_name);
        people_address_name.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_people_address_name = (RelativeLayout.LayoutParams)people_address_name.getLayoutParams();
        llp_people_address_name.leftMargin = CommonUtil.getRealWidth(200);
        llp_people_address_name.topMargin = CommonUtil.getRealWidth(20);
        llp_people_address_name.bottomMargin = CommonUtil.getRealWidth(20);
        llp_people_address_name.rightMargin = CommonUtil.getRealWidth(36);

        people_order = (RelativeLayout) findViewById(R.id.people_order);
        RelativeLayout.LayoutParams llp_people_order = (RelativeLayout.LayoutParams)people_order.getLayoutParams();
        llp_people_order.height = CommonUtil.getRealWidth(120);

        people_order_text = (TextView) findViewById(R.id.people_order_text);
        people_order_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_people_order_text = (RelativeLayout.LayoutParams)people_order_text.getLayoutParams();
        llp_people_order_text.leftMargin = CommonUtil.getRealWidth(30);

        people_order_name = (TextView) findViewById(R.id.people_order_name);
        people_order_name.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_people_order_name = (RelativeLayout.LayoutParams)people_order_name.getLayoutParams();
        llp_people_order_name.leftMargin = CommonUtil.getRealWidth(200);

        shop_shop = (RelativeLayout) findViewById(R.id.shop_shop);
        RelativeLayout.LayoutParams llp_shop_shop = (RelativeLayout.LayoutParams)shop_shop.getLayoutParams();
        llp_shop_shop.height = CommonUtil.getRealWidth(84);

        shop_image = (ImageView) findViewById(R.id.shop_image);
        RelativeLayout.LayoutParams llp_shop_image = (RelativeLayout.LayoutParams)shop_image.getLayoutParams();
        llp_shop_image.width = CommonUtil.getRealWidth(40);
        llp_shop_image.height = llp_shop_image.width;
        llp_shop_image.leftMargin = CommonUtil.getRealWidth(30);

        shop_name = (TextView) findViewById(R.id.shop_name);
        shop_name.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_shop_name = (RelativeLayout.LayoutParams)shop_name.getLayoutParams();
        llp_shop_name.leftMargin = CommonUtil.getRealWidth(86);

        shop_right = (ImageView) findViewById(R.id.shop_right);
        RelativeLayout.LayoutParams llp_shop_right = (RelativeLayout.LayoutParams)shop_right.getLayoutParams();
        llp_shop_right.width = CommonUtil.getRealWidth(36);
        llp_shop_right.height = llp_shop_right.width;
        llp_shop_right.rightMargin = CommonUtil.getRealWidth(30);

        shop_info = (RelativeLayout) findViewById(R.id.shop_info);
        RelativeLayout.LayoutParams llp_shop_info = (RelativeLayout.LayoutParams)shop_info.getLayoutParams();
        llp_shop_info.height = CommonUtil.getRealWidth(216);

        shop_info_image = (ImageView) findViewById(R.id.shop_info_image);
        RelativeLayout.LayoutParams llp_shop_info_image = (RelativeLayout.LayoutParams)shop_info_image.getLayoutParams();
        llp_shop_info_image.width = CommonUtil.getRealWidth(160);
        llp_shop_info_image.height = llp_shop_info_image.width;
        llp_shop_info_image.leftMargin = CommonUtil.getRealWidth(30);

        shop_info_name = (TextView) findViewById(R.id.shop_info_name);
        shop_info_name.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_shop_info_name = (RelativeLayout.LayoutParams)shop_info_name.getLayoutParams();
        llp_shop_info_name.leftMargin = CommonUtil.getRealWidth(214);
        llp_shop_info_name.topMargin = CommonUtil.getRealWidth(28);

        shop_info_money = (TextView) findViewById(R.id.shop_info_money);
        shop_info_money.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_shop_info_money = (RelativeLayout.LayoutParams)shop_info_money.getLayoutParams();
        llp_shop_info_money.leftMargin = CommonUtil.getRealWidth(214);
        llp_shop_info_money.topMargin = CommonUtil.getRealWidth(144);

        shop_info_money_num = (TextView) findViewById(R.id.shop_info_money_num);
        shop_info_money_num.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_shop_info_money_num = (RelativeLayout.LayoutParams)shop_info_money_num.getLayoutParams();
        llp_shop_info_money_num.leftMargin = CommonUtil.getRealWidth(688);
        llp_shop_info_money_num.topMargin = CommonUtil.getRealWidth(144);

        shop_pay = (RelativeLayout) findViewById(R.id.shop_pay);
        RelativeLayout.LayoutParams llp_shop_pay = (RelativeLayout.LayoutParams)shop_pay.getLayoutParams();
        llp_shop_pay.height = CommonUtil.getRealWidth(84);

        shop_pay_text = (TextView) findViewById(R.id.shop_pay_text);
        shop_pay_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_shop_pay_text = (RelativeLayout.LayoutParams)shop_pay_text.getLayoutParams();
        llp_shop_pay_text.leftMargin = CommonUtil.getRealWidth(30);

        shop_pay_money = (TextView) findViewById(R.id.shop_pay_money);
        shop_pay_money.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_shop_pay_money = (RelativeLayout.LayoutParams)shop_pay_money.getLayoutParams();
        llp_shop_pay_money.rightMargin = CommonUtil.getRealWidth(30);

        go = (RelativeLayout) findViewById(R.id.go);
        RelativeLayout.LayoutParams llp_go = (RelativeLayout.LayoutParams)go.getLayoutParams();
        llp_go.height = CommonUtil.getRealWidth(128);

        go_on = (RelativeLayout) findViewById(R.id.go_on);
        RelativeLayout.LayoutParams llp_go_on = (RelativeLayout.LayoutParams)go_on.getLayoutParams();
        llp_go_on.width = CommonUtil.getRealWidth(188);
        llp_go_on.height = CommonUtil.getRealWidth(76);
        llp_go_on.leftMargin = CommonUtil.getRealWidth(324);

        go_on_text = (TextView) findViewById(R.id.go_on_text);
        go_on_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));

        my_order = (RelativeLayout) findViewById(R.id.my_order);
        RelativeLayout.LayoutParams llp_my_order = (RelativeLayout.LayoutParams)my_order.getLayoutParams();
        llp_my_order.width = CommonUtil.getRealWidth(188);
        llp_my_order.height = CommonUtil.getRealWidth(76);
        llp_my_order.leftMargin = CommonUtil.getRealWidth(532);

        my_order_text = (TextView) findViewById(R.id.my_order_text);
        my_order_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));

        repay = (RelativeLayout) findViewById(R.id.repay);
        RelativeLayout.LayoutParams llp_repay = (RelativeLayout.LayoutParams)repay.getLayoutParams();
        llp_repay.width = CommonUtil.getRealWidth(188);
        llp_repay.height = CommonUtil.getRealWidth(76);
        llp_repay.leftMargin = CommonUtil.getRealWidth(532);

        repay_text = (TextView) findViewById(R.id.repay_text);
        repay_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));

        title_back.setOnClickListener(this);
        go_on.setOnClickListener(this);
        my_order.setOnClickListener(this);
        repay.setOnClickListener(this);
        shop_shop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:{
                finish();
            }
            break;

            case R.id.shop_shop:{
                Intent intent = new Intent(OrderPaySucessOrFailActivity.this,SecondlyDetailActivity.class);
                intent.putExtra("id",orderDetailResult.getSubjectTrainId());
                startActivity(intent);
            }
            break;

            case R.id.go_on:{
                setResult(202);
                Log.e("**************************",202+"");
                finish();
            }
            break;

            case R.id.repay:{
                Intent intent = new Intent(OrderPaySucessOrFailActivity.this,ShopDetailActivity.class);
                intent.putExtra("id", orderDetailResult.getMerchandiseId());
                startActivity(intent);
                finish();
            }
            break;

            case R.id.my_order:{
                Intent intent = new Intent(OrderPaySucessOrFailActivity.this,OrderMySelfActivity.class);
                startActivity(intent);
            }
            break;
        }
    }
}

package com.yidao.threekmo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ijustyce.fastkotlin.glide.ImageLoader;
import com.yidao.threekmo.R;
import com.yidao.threekmo.bean.OrderDetailMySelfResult;
import com.yidao.threekmo.bean.OrderDetailResult;
import com.yidao.threekmo.retrofit_server.SubjectServer;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.LoginUtils;
import com.yidao.threekmo.utils.RetrofitUtils;
import com.yidao.threekmo.v2.utils.AppImage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailMySelfActivity extends BaseActivity implements View.OnClickListener{

    private long orderId;
    private OrderDetailResult orderDetailResult;

    private RelativeLayout title_rela;
    private ImageView back;
    private TextView title;
    private RelativeLayout person_info_rela;
    private ImageView person_info_image;
    private TextView person_info_name;
    private TextView person_info_phone;
    private TextView person_info_address;

    private LinearLayout shop_info_rela;
    private RelativeLayout shop_name_rela;
    private ImageView shop_name_image;
    private TextView shop_name_text;
    private ImageView shop_name_go;
    private RelativeLayout order_detail_rela;
    private ImageView order_detail_image;
    private TextView order_detail_name;
    private TextView order_detail_money;
    private TextView order_detail_num;
    private RelativeLayout all_money_rela;
    private TextView all_money_money;

    private LinearLayout order_info_rela;
    private RelativeLayout order_info_first;
    private TextView order_info_first_text;
    private RelativeLayout order_info_second;
    private TextView order_info_second_text;
    private RelativeLayout order_info_third;
    private TextView order_info_third_text;
    private RelativeLayout order_info_forth;
    private TextView order_info_forth_text;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_my_self);

        if(getIntent()!= null){
            orderId = getIntent().getLongExtra("orderId",0L);
        }

        initViews();
        achieveData();

    }

    private void setData() {
        person_info_name.setText("收货人："+orderDetailResult.getConsigneeName());
        person_info_phone.setText(orderDetailResult.getConsigneePhone());
        person_info_address.setText("收货地址：" + orderDetailResult.getConsigneeAddress());
        shop_name_text.setText(orderDetailResult.getShopName());
        AppImage.INSTANCE.load(order_detail_image, orderDetailResult.getPhoto(), 16, ImageLoader.TYPE_ALL);


        order_detail_name.setText(orderDetailResult.getName());
        order_detail_money.setText("￥"+orderDetailResult.getSellingPrice());
        all_money_money.setText("合计金额：￥" + orderDetailResult.getActualPayment());
        order_info_first_text.setText("订单编号："+orderDetailResult.getOrderNumber());
        if(orderDetailResult.getPayType().equals("alipay")){
            order_info_second_text.setText("支付方式：支付宝");
        }else{
            order_info_second_text.setText("支付方式：微信");
        }

        order_info_third_text.setText("付款时间："+CommonUtil.getDateToString(orderDetailResult.getPaymentTime(),"yyyy-MM-dd HH:mm:ss"));
        order_info_forth_text.setText("成交时间："+CommonUtil.getDateToString(orderDetailResult.getTurnoverTime(),"yyyy-MM-dd HH:mm:ss"));
    }

    private void achieveData() {
        Call<OrderDetailMySelfResult> call  = RetrofitUtils.getRetrofit().create(SubjectServer.class).myOrderDetail(LoginUtils.getToken(this),orderId);
        addCall(call);
        call.enqueue(new Callback<OrderDetailMySelfResult>() {
            @Override
            public void onResponse(Call<OrderDetailMySelfResult> call, Response<OrderDetailMySelfResult> response) {
                OrderDetailMySelfResult body = response.body();
                if(body.getRspCode() == 0){
                    orderDetailResult = body.getData().getData();
                    setData();
                }
            }

            @Override
            public void onFailure(Call<OrderDetailMySelfResult> call, Throwable t) {

            }
        });
    }

    private void initViews() {
        title_rela = (RelativeLayout) findViewById(R.id.title_rela);
        RelativeLayout.LayoutParams llp_title_rela = (RelativeLayout.LayoutParams)title_rela.getLayoutParams();
        llp_title_rela.height = CommonUtil.getRealWidth(130);

        back = (ImageView) findViewById(R.id.back);
        RelativeLayout.LayoutParams llp_back = (RelativeLayout.LayoutParams)back.getLayoutParams();
        llp_back.width = CommonUtil.getRealWidth(52);
        llp_back.height = llp_back.width;
        llp_back.leftMargin = CommonUtil.getRealWidth(30);
        llp_back.topMargin = CommonUtil.getRealWidth(58);

        title = (TextView) findViewById(R.id.title);
        RelativeLayout.LayoutParams llp_title = (RelativeLayout.LayoutParams)title.getLayoutParams();
        llp_title.topMargin = CommonUtil.getRealWidth(60);

        person_info_rela = (RelativeLayout) findViewById(R.id.person_info_rela);
        RelativeLayout.LayoutParams llp_person_info_rela = (RelativeLayout.LayoutParams)person_info_rela.getLayoutParams();
        llp_person_info_rela.height = CommonUtil.getRealWidth(184);

        person_info_image = (ImageView) findViewById(R.id.person_info_image);
        RelativeLayout.LayoutParams llp_person_info_image = (RelativeLayout.LayoutParams)person_info_image.getLayoutParams();
        llp_person_info_image.width = CommonUtil.getRealWidth(40);
        llp_person_info_image.height = llp_person_info_image.width;
        llp_person_info_image.leftMargin = CommonUtil.getRealWidth(30);
        llp_person_info_image.topMargin = CommonUtil.getRealWidth(28);

        person_info_name = (TextView) findViewById(R.id.person_info_name);
        person_info_name.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_person_info_name = (RelativeLayout.LayoutParams)person_info_name.getLayoutParams();
        llp_person_info_name.leftMargin = CommonUtil.getRealWidth(82);
        llp_person_info_name.topMargin = CommonUtil.getRealWidth(24);

        person_info_phone = (TextView) findViewById(R.id.person_info_phone);
        person_info_phone.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_person_info_phone = (RelativeLayout.LayoutParams)person_info_phone.getLayoutParams();
        llp_person_info_phone.rightMargin = CommonUtil.getRealWidth(30);
        llp_person_info_phone.topMargin = CommonUtil.getRealWidth(24);

        person_info_address = (TextView) findViewById(R.id.person_info_address);
        person_info_address.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_person_info_address = (RelativeLayout.LayoutParams)person_info_address.getLayoutParams();
        llp_person_info_address.leftMargin = CommonUtil.getRealWidth(82);
        llp_person_info_address.topMargin = CommonUtil.getRealWidth(80);
        llp_person_info_address.rightMargin = CommonUtil.getRealWidth(30);


        shop_info_rela = (LinearLayout) findViewById(R.id.shop_info_rela);
        RelativeLayout.LayoutParams llp_shop_info_rela = (RelativeLayout.LayoutParams)shop_info_rela.getLayoutParams();
        llp_shop_info_rela.height = CommonUtil.getRealWidth(390);
        llp_shop_info_rela.topMargin = CommonUtil.getRealWidth(20);

        shop_name_rela = (RelativeLayout) findViewById(R.id.shop_name_rela);
        LinearLayout.LayoutParams llp_shop_name_rela = (LinearLayout.LayoutParams)shop_name_rela.getLayoutParams();
        llp_shop_name_rela.height = CommonUtil.getRealWidth(88);

        shop_name_image = (ImageView) findViewById(R.id.shop_name_image);
        RelativeLayout.LayoutParams llp_shop_name_image= (RelativeLayout.LayoutParams)shop_name_image.getLayoutParams();
        llp_shop_name_image.width = CommonUtil.getRealWidth(40);
        llp_shop_name_image.height = llp_shop_name_image.width;
        llp_shop_name_image.leftMargin = CommonUtil.getRealWidth(30);

        shop_name_text = (TextView) findViewById(R.id.shop_name_text);
        shop_name_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_shop_name_text = (RelativeLayout.LayoutParams)shop_name_text.getLayoutParams();
        llp_shop_name_text.leftMargin = CommonUtil.getRealWidth(86);

        shop_name_go = (ImageView) findViewById(R.id.shop_name_go);
        RelativeLayout.LayoutParams llp_shop_name_go = (RelativeLayout.LayoutParams)shop_name_go.getLayoutParams();
        llp_shop_name_go.width = CommonUtil.getRealWidth(32);
        llp_shop_name_go.height = llp_shop_name_go.width;
        llp_shop_name_go.rightMargin = CommonUtil.getRealWidth(30);

        order_detail_rela = (RelativeLayout) findViewById(R.id.order_detail_rela);
        LinearLayout.LayoutParams llp_order_detail_rela = (LinearLayout.LayoutParams)order_detail_rela.getLayoutParams();
        llp_order_detail_rela.height = CommonUtil.getRealWidth(216);

        order_detail_image = (ImageView) findViewById(R.id.order_detail_image);
        RelativeLayout.LayoutParams llp_order_detail_image = (RelativeLayout.LayoutParams)order_detail_image.getLayoutParams();
        llp_order_detail_image.width = CommonUtil.getRealWidth(160);
        llp_order_detail_image.height = CommonUtil.getRealWidth(160);
        llp_order_detail_image.leftMargin = CommonUtil.getRealWidth(30);

        order_detail_name = (TextView) findViewById(R.id.order_detail_name);
        order_detail_name.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_order_detail_name = (RelativeLayout.LayoutParams)order_detail_name.getLayoutParams();
        llp_order_detail_name.leftMargin = CommonUtil.getRealWidth(214);
        llp_order_detail_name.topMargin = CommonUtil.getRealWidth(28);
        llp_order_detail_name.rightMargin = CommonUtil.getRealWidth(90);

        order_detail_money = (TextView) findViewById(R.id.order_detail_money);
        order_detail_money.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_order_detail_money = (RelativeLayout.LayoutParams)order_detail_money.getLayoutParams();
        llp_order_detail_money.leftMargin = CommonUtil.getRealWidth(214);
        llp_order_detail_money.topMargin = CommonUtil.getRealWidth(144);

        order_detail_num = (TextView) findViewById(R.id.order_detail_num);
        order_detail_num.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_order_detail_num = (RelativeLayout.LayoutParams)order_detail_num.getLayoutParams();
        llp_order_detail_num.leftMargin = CommonUtil.getRealWidth(688);
        llp_order_detail_num.topMargin = CommonUtil.getRealWidth(144);

        all_money_rela = (RelativeLayout) findViewById(R.id.all_money_rela);
        LinearLayout.LayoutParams llp_all_money_rela = (LinearLayout.LayoutParams)all_money_rela.getLayoutParams();
        llp_all_money_rela.height = CommonUtil.getRealWidth(86);

        all_money_money = (TextView) findViewById(R.id.all_money_money);
        all_money_money.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_all_money_money = (RelativeLayout.LayoutParams)all_money_money.getLayoutParams();
        llp_all_money_money.rightMargin = CommonUtil.getRealWidth(30);

        order_info_rela = (LinearLayout) findViewById(R.id.order_info_rela);
        RelativeLayout.LayoutParams llp_order_info_rela = (RelativeLayout.LayoutParams)order_info_rela.getLayoutParams();
        llp_order_info_rela.height = CommonUtil.getRealWidth(326);
        llp_order_info_rela.topMargin = CommonUtil.getRealWidth(20);

        order_info_first = (RelativeLayout) findViewById(R.id.order_info_first);
        LinearLayout.LayoutParams llp_order_info_first = (LinearLayout.LayoutParams)order_info_first.getLayoutParams();
        llp_order_info_first.height = CommonUtil.getRealWidth(80);

        order_info_first_text = (TextView) findViewById(R.id.order_info_first_text);
        order_info_first_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_order_info_first_text = (RelativeLayout.LayoutParams)order_info_first_text.getLayoutParams();
        llp_order_info_first_text.leftMargin = CommonUtil.getRealWidth(30);

        order_info_second = (RelativeLayout) findViewById(R.id.order_info_second);
        LinearLayout.LayoutParams llp_order_info_second = (LinearLayout.LayoutParams)order_info_second.getLayoutParams();
        llp_order_info_second.height = CommonUtil.getRealWidth(80);

        order_info_second_text = (TextView) findViewById(R.id.order_info_second_text);
        order_info_second_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_order_info_second_text = (RelativeLayout.LayoutParams)order_info_second_text.getLayoutParams();
        llp_order_info_second_text.leftMargin = CommonUtil.getRealWidth(30);

        order_info_third = (RelativeLayout) findViewById(R.id.order_info_third);
        LinearLayout.LayoutParams llp_order_info_third = (LinearLayout.LayoutParams)order_info_third.getLayoutParams();
        llp_order_info_third.height = CommonUtil.getRealWidth(80);

        order_info_third_text = (TextView) findViewById(R.id.order_info_third_text);
        order_info_third_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_order_info_third_text = (RelativeLayout.LayoutParams)order_info_third_text.getLayoutParams();
        llp_order_info_third_text.leftMargin = CommonUtil.getRealWidth(30);

        order_info_forth = (RelativeLayout) findViewById(R.id.order_info_forth);
        LinearLayout.LayoutParams llp_order_info_forth = (LinearLayout.LayoutParams)order_info_forth.getLayoutParams();
        llp_order_info_forth.height = CommonUtil.getRealWidth(80);

        order_info_forth_text = (TextView) findViewById(R.id.order_info_forth_text);
        order_info_forth_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_order_info_forth_text = (RelativeLayout.LayoutParams)order_info_forth_text.getLayoutParams();
        llp_order_info_forth_text.leftMargin = CommonUtil.getRealWidth(30);

        shop_name_rela.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shop_name_rela:{
                Intent intent = new Intent(OrderDetailMySelfActivity.this,SecondlyDetailActivity.class);
                intent.putExtra("id",orderDetailResult.getSubjectTrainId());
                startActivity(intent);
            }
            break;

            case R.id.back:{
                finish();
            }
            break;
        }
    }
}

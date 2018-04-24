package com.yidao.threekmo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidao.threekmo.R;
import com.yidao.threekmo.utils.CommonUtil;

public class OrderPayingActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout title_rela;
    private ImageView title_back;
    private TextView title_text;
    private TextView paying_text;
    private TextView paying_refresh;
    private RelativeLayout refresh;
    private TextView refresh_text;

    private String orderNumStr = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_paying);

        if(getIntent() != null){
            orderNumStr = getIntent().getStringExtra("orderNumStr");
//            Log.e("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&",orderNumStr);
        }

        initViews();

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
        llp_title_back.topMargin= CommonUtil.getRealWidth(60);

        title_text = (TextView) findViewById(R.id.title_text);
        title_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(36));
        RelativeLayout.LayoutParams llp_title_text = (RelativeLayout.LayoutParams)title_text.getLayoutParams();
        llp_title_text.topMargin = CommonUtil.getRealWidth(58);

        paying_text = (TextView) findViewById(R.id.paying_text);
        paying_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(36));
        RelativeLayout.LayoutParams llp_paying_text = (RelativeLayout.LayoutParams)paying_text.getLayoutParams();
        llp_paying_text.topMargin = CommonUtil.getRealWidth(82);

        paying_refresh = (TextView) findViewById(R.id.paying_refresh);
        paying_refresh.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_paying_refresh = (RelativeLayout.LayoutParams)paying_refresh.getLayoutParams();
        llp_paying_refresh.topMargin = CommonUtil.getRealWidth(178);

        refresh = (RelativeLayout) findViewById(R.id.refresh);
        RelativeLayout.LayoutParams llp_refresh = (RelativeLayout.LayoutParams)refresh.getLayoutParams();
        llp_refresh.width = CommonUtil.getRealWidth(320);
        llp_refresh.height = CommonUtil.getRealWidth(84);
        llp_refresh.topMargin = CommonUtil.getRealWidth(826);

        refresh_text = (TextView) findViewById(R.id.refresh_text);
        refresh_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));

        refresh.setOnClickListener(this);
        title_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.refresh:{
                Intent intent = new Intent(OrderPayingActivity.this,OrderPaySucessOrFailActivity.class);
                intent.putExtra("orderNumStr",orderNumStr);
                startActivity(intent);
                finish();
            }
            break;

            case R.id.title_back:{
                finish();
            }

            default:
                break;
        }
    }
}

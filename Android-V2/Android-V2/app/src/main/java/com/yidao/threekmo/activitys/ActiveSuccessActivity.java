package com.yidao.threekmo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidao.threekmo.R;
import com.yidao.threekmo.bean.MainHomeListItem;
import com.yidao.threekmo.utils.CommonUtil;

public class ActiveSuccessActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout active_rela;
    private ImageView active_back;
    private TextView active_text;
    private LinearLayout active_success_linear;
    private RelativeLayout active_success_name_rela;
    private TextView active_success_name_rela_text;
    private RelativeLayout active_success_company_rela;
    private ImageView active_success_company_rela_image;
    private TextView active_success_company_rela_text;
    private RelativeLayout active_success_date_rela;
    private ImageView active_success_date_rela_image;
    private TextView active_success_date_rela_text;
    private RelativeLayout active_success_address_rela;
    private ImageView active_success_address_rela_image;
    private TextView active_success_address_rela_text;
    private LinearLayout bottom_rela;
    private RelativeLayout index_rela;
    private TextView index_rela_text;
    private RelativeLayout back_rela;
    private TextView back_rela_text;

    private MainHomeListItem mainHomeListItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_success);

        if (getIntent() != null) {
            mainHomeListItem = (MainHomeListItem) getIntent().getSerializableExtra("mainHomeListItem");
        }

        initViews();
        setData();

    }

    private void setData() {
        if(mainHomeListItem != null){
            if(mainHomeListItem.getName() != null){
                active_success_name_rela_text.setText(mainHomeListItem.getName());
            }
            active_success_company_rela_text.setText(mainHomeListItem.getSponsor());
            active_success_date_rela_text.setText(CommonUtil.getDateToString(mainHomeListItem.getActivityStart(), "yyyy-MM-dd HH:mm") + "" + CommonUtil.getDateToString(mainHomeListItem.getActivityEnd(), "yyyy-MM-dd HH:mm"));
            active_success_address_rela_text.setText(mainHomeListItem.getAddress());
        }
    }

    private void initViews() {
        active_rela = (RelativeLayout) findViewById(R.id.active_rela);
        RelativeLayout.LayoutParams llp_active_rela = (RelativeLayout.LayoutParams) active_rela.getLayoutParams();
        llp_active_rela.height = CommonUtil.getRealWidth(128);

        active_back = (ImageView) findViewById(R.id.active_back);
        RelativeLayout.LayoutParams llp_active_back = (RelativeLayout.LayoutParams) active_back.getLayoutParams();
        llp_active_back.width = CommonUtil.getRealWidth(52);
        llp_active_back.height = llp_active_back.width;
        llp_active_back.leftMargin = CommonUtil.getRealWidth(30);
        llp_active_back.topMargin = CommonUtil.getRealWidth(62);

        active_text = (TextView) findViewById(R.id.active_text);
        active_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(36));
        RelativeLayout.LayoutParams llp_active_text = (RelativeLayout.LayoutParams) active_text.getLayoutParams();
        llp_active_text.topMargin = CommonUtil.getRealWidth(63);

        active_success_linear = (LinearLayout) findViewById(R.id.active_success_linear);
        RelativeLayout.LayoutParams llp_active_success_linear = (RelativeLayout.LayoutParams) active_success_linear.getLayoutParams();
        llp_active_success_linear.height = CommonUtil.getRealWidth(336);
        llp_active_success_linear.topMargin = CommonUtil.getRealWidth(20);

        active_success_name_rela = (RelativeLayout) findViewById(R.id.active_success_name_rela);
        LinearLayout.LayoutParams llp_active_success_name_rela = (LinearLayout.LayoutParams) active_success_name_rela.getLayoutParams();
        llp_active_success_name_rela.height = CommonUtil.getRealWidth(84);

        active_success_name_rela_text = (TextView) findViewById(R.id.active_success_name_rela_text);
        active_success_name_rela_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_active_success_name_rela_text = (RelativeLayout.LayoutParams) active_success_name_rela_text.getLayoutParams();
        llp_active_success_name_rela_text.leftMargin = CommonUtil.getRealWidth(30);

        active_success_company_rela = (RelativeLayout) findViewById(R.id.active_success_company_rela);
        LinearLayout.LayoutParams llp_active_success_company_rela = (LinearLayout.LayoutParams) active_success_company_rela.getLayoutParams();
        llp_active_success_company_rela.height = CommonUtil.getRealWidth(84);

        active_success_company_rela_image = (ImageView) findViewById(R.id.active_success_company_rela_image);
        RelativeLayout.LayoutParams llp_active_success_company_rela_image = (RelativeLayout.LayoutParams) active_success_company_rela_image.getLayoutParams();
        llp_active_success_company_rela_image.width = CommonUtil.getRealWidth(36);
        llp_active_success_company_rela_image.height = llp_active_success_company_rela_image.width;
        llp_active_success_company_rela_image.leftMargin = CommonUtil.getRealWidth(34);

        active_success_company_rela_text = (TextView) findViewById(R.id.active_success_company_rela_text);
        active_success_company_rela_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(24));
        RelativeLayout.LayoutParams llp_active_success_company_rela_text = (RelativeLayout.LayoutParams) active_success_company_rela_text.getLayoutParams();
        llp_active_success_company_rela_text.leftMargin = CommonUtil.getRealWidth(86);

        active_success_date_rela = (RelativeLayout) findViewById(R.id.active_success_date_rela);
        LinearLayout.LayoutParams llp_active_success_date_rela = (LinearLayout.LayoutParams) active_success_date_rela.getLayoutParams();
        llp_active_success_date_rela.height = CommonUtil.getRealWidth(84);

        active_success_date_rela_image = (ImageView) findViewById(R.id.active_success_date_rela_image);
        RelativeLayout.LayoutParams llp_active_success_date_rela_image = (RelativeLayout.LayoutParams) active_success_date_rela_image.getLayoutParams();
        llp_active_success_date_rela_image.width = CommonUtil.getRealWidth(44);
        llp_active_success_date_rela_image.height = llp_active_success_date_rela_image.width;
        llp_active_success_date_rela_image.leftMargin = CommonUtil.getRealWidth(30);

        active_success_date_rela_text = (TextView) findViewById(R.id.active_success_date_rela_text);
        active_success_date_rela_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(24));
        RelativeLayout.LayoutParams llp_active_success_date_rela_text = (RelativeLayout.LayoutParams) active_success_date_rela_text.getLayoutParams();
        llp_active_success_date_rela_text.leftMargin = CommonUtil.getRealWidth(90);

        active_success_address_rela = (RelativeLayout) findViewById(R.id.active_success_address_rela);
        LinearLayout.LayoutParams llp_active_success_address_rela = (LinearLayout.LayoutParams) active_success_address_rela.getLayoutParams();
        llp_active_success_address_rela.height = CommonUtil.getRealWidth(84);

        active_success_address_rela_image = (ImageView) findViewById(R.id.active_success_address_rela_image);
        RelativeLayout.LayoutParams llp_active_success_address_rela_image = (RelativeLayout.LayoutParams) active_success_address_rela_image.getLayoutParams();
        llp_active_success_address_rela_image.width = CommonUtil.getRealWidth(44);
        llp_active_success_address_rela_image.height = llp_active_success_address_rela_image.width;
        llp_active_success_address_rela_image.leftMargin = CommonUtil.getRealWidth(30);

        active_success_address_rela_text = (TextView) findViewById(R.id.active_success_address_rela_text);
        active_success_address_rela_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(24));
        RelativeLayout.LayoutParams llp_active_success_address_rela_text = (RelativeLayout.LayoutParams) active_success_address_rela_text.getLayoutParams();
        llp_active_success_address_rela_text.leftMargin = CommonUtil.getRealWidth(90);

        bottom_rela = (LinearLayout) findViewById(R.id.bottom_rela);
        RelativeLayout.LayoutParams llp_bottom_rela = (RelativeLayout.LayoutParams) bottom_rela.getLayoutParams();
        llp_bottom_rela.height = CommonUtil.getRealWidth(102);

        index_rela = (RelativeLayout) findViewById(R.id.index_rela);

        index_rela_text = (TextView) findViewById(R.id.index_rela_text);
        index_rela_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));

        back_rela = (RelativeLayout) findViewById(R.id.back_rela);

        back_rela_text = (TextView) findViewById(R.id.back_rela_text);
        back_rela_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));

        index_rela.setOnClickListener(this);
        back_rela.setOnClickListener(this);
        active_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.index_rela: {
                setResult(102);
                finish();
            }
            break;
            case R.id.back_rela: {
                Intent intent = new Intent(ActiveSuccessActivity.this,MyActiveListActivity.class);
                startActivity(intent);
                setResult(102);
                finish();
            }
            break;
            case R.id.active_back: {
                finish();
            }
            break;
            default:
                break;
        }
    }
}

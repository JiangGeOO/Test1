package com.yidao.threekmo.activitys;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidao.myutils.file.SPUtils;
import com.yidao.threekmo.R;
import com.yidao.threekmo.events.EventCity;
import com.yidao.threekmo.parameter.SPParameters;
import com.yidao.threekmo.utils.CommonUtil;

import org.greenrobot.eventbus.EventBus;

public class ChooseCityActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout active_rela;
    private ImageView active_back;
    private TextView active_text;

    private TextView new_city_text;
    private RelativeLayout shape_rela;
    private ImageView shape_rela_image;
    private TextView shape_rela_text;
    private TextView all_city;
    private TextView city_hz;
    private TextView city_sx;
    private TextView city_jh;
    private TextView city_nb;
    private String oldcity = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);

        if(getIntent() != null){
            oldcity = getIntent().getStringExtra("oldcity");
        }

        initViews();
        setData();

    }

    private void setData() {
        String oldLocation = (String) SPUtils.get(ChooseCityActivity.this,SPParameters.OLD_CITY,"杭州");
        if(!TextUtils.isEmpty(oldLocation)){
            shape_rela_text.setText(oldLocation);
        }else{
            shape_rela_text.setText("杭州");
        }

    }

    private void initViews() {
        active_rela = (RelativeLayout) findViewById(R.id.active_rela);
        RelativeLayout.LayoutParams llp_active_rela = (RelativeLayout.LayoutParams)active_rela.getLayoutParams();
        llp_active_rela.height = CommonUtil.getRealWidth(128);

        active_back = (ImageView) findViewById(R.id.active_back);
        RelativeLayout.LayoutParams llp_active_back = (RelativeLayout.LayoutParams)active_back.getLayoutParams();
        llp_active_back.width = CommonUtil.getRealWidth(52);
        llp_active_back.height = llp_active_back.width;
        llp_active_back.leftMargin = CommonUtil.getRealWidth(30);
        llp_active_back.topMargin = CommonUtil.getRealWidth(62);

        active_text = (TextView) findViewById(R.id.active_text);
        active_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(36));
        RelativeLayout.LayoutParams llp_active_text = (RelativeLayout.LayoutParams)active_text.getLayoutParams();
        llp_active_text.topMargin = CommonUtil.getRealWidth(63);

        new_city_text = (TextView) findViewById(R.id.new_city_text);
        new_city_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_new_city_text = (RelativeLayout.LayoutParams)new_city_text.getLayoutParams();
        llp_new_city_text.leftMargin = CommonUtil.getRealWidth(40);
        llp_new_city_text.topMargin = CommonUtil.getRealWidth(20);

        shape_rela = (RelativeLayout) findViewById(R.id.shape_rela);
        RelativeLayout.LayoutParams llp_shape_rela = (RelativeLayout.LayoutParams)shape_rela.getLayoutParams();
        llp_shape_rela.width = CommonUtil.getRealWidth(182);
        llp_shape_rela.height = CommonUtil.getRealWidth(72);
        llp_shape_rela.leftMargin = CommonUtil.getRealWidth(40);
        llp_shape_rela.topMargin = CommonUtil.getRealWidth(28);

        shape_rela_image = (ImageView) findViewById(R.id.shape_rela_image);
        RelativeLayout.LayoutParams llp_shape_rela_image = (RelativeLayout.LayoutParams)shape_rela_image.getLayoutParams();
        llp_shape_rela_image.width = CommonUtil.getRealWidth(36);
        llp_shape_rela_image.height = CommonUtil.getRealWidth(36);
        llp_shape_rela_image.leftMargin = CommonUtil.getRealWidth(40);

        shape_rela_text = (TextView) findViewById(R.id.shape_rela_text);
        shape_rela_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_shape_rela_text = (RelativeLayout.LayoutParams)shape_rela_text.getLayoutParams();
        llp_shape_rela_text.leftMargin = CommonUtil.getRealWidth(8);

        all_city = (TextView) findViewById(R.id.all_city);
        all_city.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_all_city = (RelativeLayout.LayoutParams)all_city.getLayoutParams();
        llp_all_city.leftMargin = CommonUtil.getRealWidth(40);
        llp_all_city.topMargin = CommonUtil.getRealWidth(28);

        city_hz = (TextView) findViewById(R.id.city_hz);
        city_hz.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_city_hz = (RelativeLayout.LayoutParams)city_hz.getLayoutParams();
        llp_city_hz.width = CommonUtil.getRealWidth(202);
        llp_city_hz.height = CommonUtil.getRealWidth(72);
        llp_city_hz.leftMargin = CommonUtil.getRealWidth(40);
        llp_city_hz.topMargin = CommonUtil.getRealWidth(28);

        city_sx = (TextView) findViewById(R.id.city_sx);
        city_sx.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_city_sx = (RelativeLayout.LayoutParams)city_sx.getLayoutParams();
        llp_city_sx.width = CommonUtil.getRealWidth(202);
        llp_city_sx.height = CommonUtil.getRealWidth(72);
        llp_city_sx.leftMargin = CommonUtil.getRealWidth(34);
        llp_city_sx.topMargin = CommonUtil.getRealWidth(28);

        city_jh = (TextView) findViewById(R.id.city_jh);
        city_jh.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_city_jh = (RelativeLayout.LayoutParams)city_jh.getLayoutParams();
        llp_city_jh.width = CommonUtil.getRealWidth(202);
        llp_city_jh.height = CommonUtil.getRealWidth(72);
        llp_city_jh.leftMargin = CommonUtil.getRealWidth(34);
        llp_city_jh.topMargin = CommonUtil.getRealWidth(28);

        city_nb = (TextView) findViewById(R.id.city_nb);
        city_nb.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_city_nb = (RelativeLayout.LayoutParams)city_nb.getLayoutParams();
        llp_city_nb.width = CommonUtil.getRealWidth(202);
        llp_city_nb.height = CommonUtil.getRealWidth(72);
        llp_city_nb.leftMargin = CommonUtil.getRealWidth(40);
        llp_city_nb.topMargin = CommonUtil.getRealWidth(28);

        city_hz.setOnClickListener(this);
        city_sx.setOnClickListener(this);
        city_jh.setOnClickListener(this);
        city_nb.setOnClickListener(this);
        active_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        EventCity city = new EventCity();

        switch (v.getId()){

            case R.id.active_back:{
                finish();
            }
            break;

            case R.id.city_hz:{
                SPUtils.put(ChooseCityActivity.this, SPParameters.OLD_CITY,oldcity);
                SPUtils.put(ChooseCityActivity.this, SPParameters.NEW_CITY,city_hz.getText().toString());
                city.setCity(city_hz.getText().toString());
            }
            break;
            case R.id.city_sx:{
                SPUtils.put(ChooseCityActivity.this, SPParameters.OLD_CITY,oldcity);
                SPUtils.put(ChooseCityActivity.this, SPParameters.NEW_CITY,city_sx.getText().toString());
                city.setCity(city_sx.getText().toString());
            }
            break;
            case R.id.city_jh:{
                SPUtils.put(ChooseCityActivity.this, SPParameters.OLD_CITY,oldcity);
                SPUtils.put(ChooseCityActivity.this, SPParameters.NEW_CITY,city_jh.getText().toString());
                city.setCity(city_jh.getText().toString());
            }
            break;
            case R.id.city_nb:{
                SPUtils.put(ChooseCityActivity.this, SPParameters.OLD_CITY,oldcity);
                SPUtils.put(ChooseCityActivity.this, SPParameters.NEW_CITY,city_nb.getText().toString());
                city.setCity(city_nb.getText().toString());
            }
            break;
            default:
                break;
        }
        finish();
        EventBus.getDefault().post(city);

    }
}

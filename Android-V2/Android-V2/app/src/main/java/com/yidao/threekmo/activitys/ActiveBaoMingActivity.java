package com.yidao.threekmo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yidao.threekmo.R;
import com.yidao.threekmo.bean.ActivityBaomingResult;
import com.yidao.threekmo.bean.MainHomeListItem;
import com.yidao.threekmo.bean.UserBean;
import com.yidao.threekmo.retrofit_server.SubjectServer;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.LoginUtils;
import com.yidao.threekmo.utils.RetrofitUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActiveBaoMingActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout active_rela;
    private ImageView active_back;
    private TextView active_text;
    private LinearLayout info_linear;
    private RelativeLayout info_linear_name;
    private EditText info_linear_name_text;
    private ImageView info_linear_name_image;
    private RelativeLayout info_linear_email;
    private View view1;
    private EditText info_linear_email_text;
    private ImageView info_linear_email_image;
    private RelativeLayout info_linear_phone;
    private View view2;
    private EditText info_linear_phone_text;
    private ImageView info_linear_phone_image;
    private RelativeLayout next_rela;
    private TextView next_text;
    private long id;
    private String mHtmlText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_bao_ming);

        if(getIntent() != null){
            id = getIntent().getLongExtra("id",0);
            mHtmlText = getIntent().getStringExtra("mHtmlText");
        }

        initViews();
        setData();

    }

    private void setData() {
        info_linear_email_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s)){
                    info_linear_email_image.setVisibility(View.VISIBLE);
                }else{
                    info_linear_email_image.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        info_linear_phone_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s)){
                    info_linear_phone_image.setVisibility(View.VISIBLE);
                }else{
                    info_linear_phone_image.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        UserBean userBean = LoginUtils.getUserBean(ActiveBaoMingActivity.this);
        String phone = userBean.getPhone();
        info_linear_phone_text.setText(phone + "");

        info_linear_email_image.setOnClickListener(this);
        info_linear_phone_image.setOnClickListener(this);

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

        info_linear = (LinearLayout) findViewById(R.id.info_linear);
        RelativeLayout.LayoutParams llp_info_linear = (RelativeLayout.LayoutParams)info_linear.getLayoutParams();
//        llp_info_linear.height = CommonUtil.getRealWidth(270);
        llp_info_linear.topMargin = CommonUtil.getRealWidth(20);

        info_linear_name = (RelativeLayout) findViewById(R.id.info_linear_name);
        LinearLayout.LayoutParams llp_info_linear_name = (LinearLayout.LayoutParams)info_linear_name.getLayoutParams();
        llp_info_linear_name.height = CommonUtil.getRealWidth(90);

        info_linear_name_text = (EditText) findViewById(R.id.info_linear_name_text);
        info_linear_name_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_info_linear_name_text = (RelativeLayout.LayoutParams)info_linear_name_text.getLayoutParams();
        llp_info_linear_name_text.leftMargin = CommonUtil.getRealWidth(30);
        llp_info_linear_name_text.rightMargin = CommonUtil.getRealWidth(10);

        info_linear_name_image = (ImageView) findViewById(R.id.info_linear_name_image);
        RelativeLayout.LayoutParams llp_info_linear_name_image = (RelativeLayout.LayoutParams)info_linear_name_image.getLayoutParams();
        llp_info_linear_name_image.width = CommonUtil.getRealWidth(32);
        llp_info_linear_name_image.height = llp_info_linear_name_image.width;
        llp_info_linear_name_image.rightMargin = CommonUtil.getRealWidth(50);

        info_linear_email = (RelativeLayout) findViewById(R.id.info_linear_email);
        LinearLayout.LayoutParams llp_info_linear_email = (LinearLayout.LayoutParams)info_linear_email.getLayoutParams();
        llp_info_linear_email.height = CommonUtil.getRealWidth(90);

        view1 = findViewById(R.id.view1);
        RelativeLayout.LayoutParams llp_view1 = (RelativeLayout.LayoutParams)view1.getLayoutParams();
        llp_view1.leftMargin = CommonUtil.getRealWidth(30);
        llp_view1.rightMargin = CommonUtil.getRealWidth(30);

        info_linear_email_text = (EditText) findViewById(R.id.info_linear_email_text);
        info_linear_email_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_info_linear_email_text = (RelativeLayout.LayoutParams)info_linear_email_text.getLayoutParams();
        llp_info_linear_email_text.leftMargin = CommonUtil.getRealWidth(30);
        llp_info_linear_email_text.rightMargin = CommonUtil.getRealWidth(10);

        info_linear_email_image = (ImageView) findViewById(R.id.info_linear_email_image);
        RelativeLayout.LayoutParams llp_info_linear_email_image = (RelativeLayout.LayoutParams)info_linear_email_image.getLayoutParams();
        llp_info_linear_email_image.width = CommonUtil.getRealWidth(32);
        llp_info_linear_email_image.height = llp_info_linear_email_image.width;
        llp_info_linear_email_image.rightMargin = CommonUtil.getRealWidth(50);

        info_linear_phone = (RelativeLayout) findViewById(R.id.info_linear_phone);
        LinearLayout.LayoutParams llp_info_linear_phone = (LinearLayout.LayoutParams)info_linear_phone.getLayoutParams();
        llp_info_linear_phone.height = CommonUtil.getRealWidth(90);

        view2 = findViewById(R.id.view2);
        RelativeLayout.LayoutParams llp_view2 = (RelativeLayout.LayoutParams)view2.getLayoutParams();
        llp_view2.leftMargin = CommonUtil.getRealWidth(30);
        llp_view2.rightMargin = CommonUtil.getRealWidth(30);

        info_linear_phone_text = (EditText) findViewById(R.id.info_linear_phone_text);
        info_linear_phone_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_info_linear_phone_text = (RelativeLayout.LayoutParams)info_linear_phone_text.getLayoutParams();
        llp_info_linear_phone_text.leftMargin = CommonUtil.getRealWidth(30);
        llp_info_linear_phone_text.rightMargin = CommonUtil.getRealWidth(10);

        info_linear_phone_image = (ImageView) findViewById(R.id.info_linear_phone_image);
        RelativeLayout.LayoutParams llp_info_linear_phone_image = (RelativeLayout.LayoutParams)info_linear_phone_image.getLayoutParams();
        llp_info_linear_phone_image.width = CommonUtil.getRealWidth(32);
        llp_info_linear_phone_image.height = llp_info_linear_phone_image.width;
        llp_info_linear_phone_image.rightMargin = CommonUtil.getRealWidth(50);

        next_rela = (RelativeLayout) findViewById(R.id.next_rela);
        RelativeLayout.LayoutParams llp_next_rela = (RelativeLayout.LayoutParams)next_rela.getLayoutParams();
        llp_next_rela.width = CommonUtil.getRealWidth(690);
        llp_next_rela.height = CommonUtil.getRealWidth(78);
        llp_next_rela.topMargin = CommonUtil.getRealWidth(64);

        next_text = (TextView) findViewById(R.id.next_text);
        next_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(36));

        next_rela.setOnClickListener(this);
        active_back.setOnClickListener(this);
    }

    private void getSuccess(){
        SubjectServer subjectServer = RetrofitUtils.getRetrofit().create(SubjectServer.class);
        Call<ActivityBaomingResult> call = subjectServer.getSuccess(LoginUtils.getToken(ActiveBaoMingActivity.this),id,info_linear_email_text.getText().toString(),info_linear_phone_text.getText().toString(),mHtmlText);
        addCall(call);
        call.enqueue(new Callback<ActivityBaomingResult>() {
            @Override
            public void onResponse(Call<ActivityBaomingResult> call, Response<ActivityBaomingResult> response) {
                ActivityBaomingResult body = response.body();
                if(body != null){
                    if(body.getRspCode() == 0){
                        if("false".equals(body.getData().isSuccess())){
                            Toast.makeText(ActiveBaoMingActivity.this,body.getData().getErrorMsg()+"",Toast.LENGTH_SHORT).show();
                        }else{
                            MainHomeListItem mainHomeListItem = body.getData().getData();
                            Bundle bundle = new Bundle();
                            Intent intent = new Intent(ActiveBaoMingActivity.this,ActiveSuccessActivity.class);
                            bundle.putSerializable("mainHomeListItem",mainHomeListItem);
                            intent.putExtras(bundle);
                            startActivityForResult(intent,101);
                        }

                    }
                }else{
                    Toast.makeText(ActiveBaoMingActivity.this,"当前数据错误!",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ActivityBaomingResult> call, Throwable t) {
                Toast.makeText(ActiveBaoMingActivity.this,t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.active_back:{
                finish();
            }
            break;
            case R.id.next_rela:{
                if(TextUtils.isEmpty(info_linear_phone_text.getText().toString()) || TextUtils.isEmpty(info_linear_email_text.getText().toString())){
                    Toast.makeText(ActiveBaoMingActivity.this,"所填信息不能为空",Toast.LENGTH_SHORT).show();
                }else if(!CommonUtil.isMobileNum(info_linear_phone_text.getText().toString())){
                    Toast.makeText(ActiveBaoMingActivity.this,"请正确填写电话号码",Toast.LENGTH_SHORT).show();
                }else{
                    getSuccess();
                }

            }
            break;
            case R.id.info_linear_email_image:{
                info_linear_email_text.setText("");
            }
            break;
            case R.id.info_linear_phone_image:{
                info_linear_phone_text.setText("");
            }
            break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 101){
            if(resultCode == 102){
                setResult(102);
                finish();
            }
        }
    }
}

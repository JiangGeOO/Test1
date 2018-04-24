package com.yidao.threekmo.activitys;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yidao.myutils.toast.ToastUtil;
import com.yidao.threekmo.R;
import com.yidao.threekmo.bean.BaseResult;
import com.yidao.threekmo.retrofit_server.RegServer;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.RetrofitUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyPsdActivity extends BaseActivity implements View.OnClickListener {

    private String psd1 = "";
    private String psd2 = "";
    private String num;
    private String code;
    private boolean oneLookFlag = false;
    private boolean twoLookFlag = false;

    private RelativeLayout all_rela;
    private TextView logo_name;
    private EditText edit_psd1;
    private EditText edit_psd2;
    private RelativeLayout deng_rela;
    private TextView deng_text;
    private TextView fail_info;
    private RelativeLayout cancle_rela;
    private TextView cancle_text;
    private ImageView look1;
    private ImageView look2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_psd);

        if (getIntent() != null) {
            num = getIntent().getStringExtra("num");
            code = getIntent().getStringExtra("code");
        }

        viewInit();

    }

    private void viewInit() {
        all_rela = (RelativeLayout) findViewById(R.id.all_rela);
        RelativeLayout.LayoutParams llp_all_rela = (RelativeLayout.LayoutParams) all_rela.getLayoutParams();
        llp_all_rela.height = CommonUtil.getRealWidth(682);
        llp_all_rela.width = CommonUtil.screenWidth;
        llp_all_rela.topMargin = CommonUtil.getRealWidth(200);
        llp_all_rela.leftMargin = CommonUtil.getRealWidth(20);
        llp_all_rela.rightMargin = CommonUtil.getRealWidth(20);

        logo_name = (TextView) findViewById(R.id.logo_name);
        logo_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(44));
        RelativeLayout.LayoutParams llp_logo_name = (RelativeLayout.LayoutParams) logo_name.getLayoutParams();
        llp_logo_name.topMargin = CommonUtil.getRealWidth(80);

        edit_psd1 = (EditText) findViewById(R.id.edit_psd1);
        edit_psd1.setPadding(CommonUtil.getRealWidth(40), 0, 0, 0);
        edit_psd1.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_edit_psd1 = (RelativeLayout.LayoutParams) edit_psd1.getLayoutParams();
        llp_edit_psd1.height = CommonUtil.getRealWidth(100);
        llp_edit_psd1.leftMargin = CommonUtil.getRealWidth(54);
        llp_edit_psd1.rightMargin = CommonUtil.getRealWidth(54);
        llp_edit_psd1.topMargin = CommonUtil.getRealWidth(204);

        edit_psd2 = (EditText) findViewById(R.id.edit_psd2);
        edit_psd2.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        edit_psd2.setPadding(CommonUtil.getRealWidth(40), 0, 0, 0);
        RelativeLayout.LayoutParams llp_edit_psd2 = (RelativeLayout.LayoutParams) edit_psd2.getLayoutParams();
        llp_edit_psd2.height = CommonUtil.getRealWidth(100);
        llp_edit_psd2.leftMargin = CommonUtil.getRealWidth(54);
        llp_edit_psd2.rightMargin = CommonUtil.getRealWidth(54);
        llp_edit_psd2.topMargin = CommonUtil.getRealWidth(326);

        deng_rela = (RelativeLayout) findViewById(R.id.deng_rela);
        RelativeLayout.LayoutParams llp_deng_rela = (RelativeLayout.LayoutParams) deng_rela.getLayoutParams();
        llp_deng_rela.width = CommonUtil.getRealWidth(624);
        llp_deng_rela.height = CommonUtil.getRealWidth(80);
        llp_deng_rela.topMargin = CommonUtil.getRealWidth(498);

        deng_text = (TextView) findViewById(R.id.deng_text);
        deng_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(36));

        fail_info = (TextView) findViewById(R.id.fail_info);
        fail_info.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(24));
        RelativeLayout.LayoutParams llp_fail_info = (RelativeLayout.LayoutParams) fail_info.getLayoutParams();
        llp_fail_info.topMargin = CommonUtil.getRealWidth(450);
        llp_fail_info.leftMargin = CommonUtil.getRealWidth(72);

        cancle_rela = (RelativeLayout) findViewById(R.id.cancle_rela);
        RelativeLayout.LayoutParams llp_cancle_rela = (RelativeLayout.LayoutParams) cancle_rela.getLayoutParams();
        llp_cancle_rela.width = CommonUtil.screenWidth;
        llp_cancle_rela.height = CommonUtil.getRealWidth(140);
        llp_cancle_rela.topMargin = CommonUtil.getRealWidth(870);
        llp_cancle_rela.leftMargin = CommonUtil.getRealWidth(20);
        llp_cancle_rela.rightMargin = CommonUtil.getRealWidth(20);

        cancle_text = (TextView) findViewById(R.id.cancle_text);
        cancle_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(36));


        look1 = (ImageView) findViewById(R.id.look1);
        RelativeLayout.LayoutParams llp_look1 = (RelativeLayout.LayoutParams) look1.getLayoutParams();
        llp_look1.width = CommonUtil.getRealWidth(40);
        llp_look1.height = llp_look1.width;
        llp_look1.topMargin = CommonUtil.getRealWidth(236);
        llp_look1.rightMargin = CommonUtil.getRealWidth(86);

        look2 = (ImageView) findViewById(R.id.look2);
        RelativeLayout.LayoutParams llp_look2 = (RelativeLayout.LayoutParams) look2.getLayoutParams();
        llp_look2.width = CommonUtil.getRealWidth(40);
        llp_look2.height = llp_look2.width;
        llp_look2.topMargin = CommonUtil.getRealWidth(356);
        llp_look2.rightMargin = CommonUtil.getRealWidth(86);

        edit_psd2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    fail_info.setVisibility(View.GONE);
                    deng_rela.setBackgroundResource(R.drawable.shape_crcle_green);
                } else {
                    deng_rela.setBackgroundResource(R.drawable.spinner_gray_back);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        look1.setOnClickListener(this);
        look2.setOnClickListener(this);
        deng_rela.setOnClickListener(this);
        cancle_rela.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.look1: {
                if (!oneLookFlag) {
                    //如果选中，显示密码
                    look1.setImageResource(R.mipmap.look_psd);
                    edit_psd1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    oneLookFlag = true;
                } else {
                    //否则隐藏密码
                    look1.setImageResource(R.mipmap.unlook_psd);
                    edit_psd1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    oneLookFlag = false;
                }
            }
            break;

            case R.id.look2: {
                if (!twoLookFlag) {
                    //如果选中，显示密码
                    look2.setImageResource(R.mipmap.look_psd);
                    edit_psd2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    twoLookFlag = true;
                } else {
                    //否则隐藏密码
                    look2.setImageResource(R.mipmap.unlook_psd);
                    edit_psd2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    twoLookFlag = false;
                }
            }
            break;

            case R.id.deng_rela: {
                psd1 = edit_psd1.getText().toString();
                psd2 = edit_psd2.getText().toString();
                if (checkParameters()) {
                    setPsd();
                }
            }
            break;

            case R.id.cancle_rela: {
                finish();
            }
            break;

            default:
                break;
        }
    }

    private void setPsd() {
        Call<BaseResult> call = RetrofitUtils.getRetrofit().create(RegServer.class).restPsd(num, psd1, code);
        addCall(call);
        call.enqueue(new Callback<BaseResult>() {
            @Override
            public void onResponse(Call<BaseResult> call, Response<BaseResult> response) {
                BaseResult body = response.body();
                if (body != null && body.getRspCode() == 0) {
                    Toast.makeText(ModifyPsdActivity.this, "修改成功", Toast.LENGTH_LONG).show();
                    finish();
                } else if (fail_info != null){
                    fail_info.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<BaseResult> call, Throwable t) {
                ToastUtil.showToast(t.getMessage(), false, ModifyPsdActivity.this);
            }
        });
    }

    private boolean checkParameters() {
        if (psd1.length() >= 0 && psd1.equals(psd2)) {
            return true;
        } else {
            fail_info.setVisibility(View.VISIBLE);
            return false;
        }
    }
}

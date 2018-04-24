package com.yidao.threekmo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
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

import com.yidao.threekmo.R;
import com.yidao.threekmo.application.MyApplication;
import com.yidao.threekmo.bean.PsdLoginResult;
import com.yidao.threekmo.bean.UserBean;
import com.yidao.threekmo.retrofit_server.UserServer;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.LoginUtils;
import com.yidao.threekmo.utils.RetrofitUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PsdLoginActivity extends BaseActivity implements View.OnClickListener {
    private String num = "";
    private String psd = "";

    private RelativeLayout all_rela;
    private EditText phone_edit;
    private EditText psd_edit;
    private TextView logo_text;
    private RelativeLayout denglu_rela;
    private TextView denglu;
    private TextView user_info;
    private TextView code_login;
    private TextView forget_psd;
    private RelativeLayout cancle_rela;
    private TextView cancle_text;
    private ImageView clear;
    private ImageView look;
    private TextView fail_text;

    private boolean isCheck = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psd_login);

        viewInit();
    }

    private void viewInit() {
        all_rela = (RelativeLayout) findViewById(R.id.all_rela);
        RelativeLayout.LayoutParams llp_all_rela = (RelativeLayout.LayoutParams) all_rela.getLayoutParams();
        llp_all_rela.height = CommonUtil.getRealWidth(862);
        llp_all_rela.width = CommonUtil.screenWidth;
        llp_all_rela.topMargin = CommonUtil.getRealWidth(200);
        llp_all_rela.leftMargin = CommonUtil.getRealWidth(20);
        llp_all_rela.rightMargin = CommonUtil.getRealWidth(20);

        logo_text = (TextView) findViewById(R.id.logo_text);
        logo_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(44));
        RelativeLayout.LayoutParams llp_logo_text = (RelativeLayout.LayoutParams) logo_text.getLayoutParams();
        llp_logo_text.topMargin = CommonUtil.getRealWidth(80);

        phone_edit = (EditText) findViewById(R.id.phone_edit);
        phone_edit.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        phone_edit.setPadding(CommonUtil.getRealWidth(40), 0, 0, 0);
        RelativeLayout.LayoutParams llp_phone_edit = (RelativeLayout.LayoutParams) phone_edit.getLayoutParams();
        llp_phone_edit.leftMargin = CommonUtil.getRealWidth(54);
        llp_phone_edit.rightMargin = CommonUtil.getRealWidth(54);
        llp_phone_edit.height = CommonUtil.getRealWidth(100);
        llp_phone_edit.topMargin = CommonUtil.getRealWidth(204);

        psd_edit = (EditText) findViewById(R.id.psd_edit);
        psd_edit.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        psd_edit.setPadding(CommonUtil.getRealWidth(40), 0, 0, 0);
        RelativeLayout.LayoutParams llp_psd_edit = (RelativeLayout.LayoutParams) psd_edit.getLayoutParams();
        llp_psd_edit.leftMargin = CommonUtil.getRealWidth(54);
        llp_psd_edit.rightMargin = CommonUtil.getRealWidth(54);
        llp_psd_edit.height = CommonUtil.getRealWidth(100);
        llp_psd_edit.topMargin = CommonUtil.getRealWidth(316);

        denglu_rela = (RelativeLayout) findViewById(R.id.denglu_rela);
        RelativeLayout.LayoutParams llp_denglu_rela = (RelativeLayout.LayoutParams) denglu_rela.getLayoutParams();
        llp_denglu_rela.height = CommonUtil.getRealWidth(80);
        llp_denglu_rela.width = CommonUtil.getRealWidth(624);
        llp_denglu_rela.topMargin = CommonUtil.getRealWidth(488);

        denglu = (TextView) findViewById(R.id.denglu);
        denglu.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(36));

        user_info = (TextView) findViewById(R.id.user_info);
        String str = "<font color='#c0c0d1'>点击登陆即表示您同意</font><font color='#1aba6e'>《用户协议》</font>";
        user_info.setText(Html.fromHtml(str));
        user_info.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(24));
        RelativeLayout.LayoutParams llp_user_info = (RelativeLayout.LayoutParams) user_info.getLayoutParams();
        llp_user_info.topMargin = CommonUtil.getRealWidth(598);
        llp_user_info.leftMargin = CommonUtil.getRealWidth(72);

        code_login = (TextView) findViewById(R.id.code_login);
        code_login.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(24));
        RelativeLayout.LayoutParams llp_code_login = (RelativeLayout.LayoutParams) code_login.getLayoutParams();
        llp_code_login.topMargin = CommonUtil.getRealWidth(658);
        llp_code_login.leftMargin = CommonUtil.getRealWidth(72);

        forget_psd = (TextView) findViewById(R.id.forget_psd);
        forget_psd.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(24));
        RelativeLayout.LayoutParams llp_forget_psd = (RelativeLayout.LayoutParams) forget_psd.getLayoutParams();
        llp_forget_psd.topMargin = CommonUtil.getRealWidth(658);
        llp_forget_psd.rightMargin = CommonUtil.getRealWidth(72);

        cancle_rela = (RelativeLayout) findViewById(R.id.cancle_rela);
        RelativeLayout.LayoutParams llp_cancle_rela = (RelativeLayout.LayoutParams) cancle_rela.getLayoutParams();
        llp_cancle_rela.width = CommonUtil.screenWidth;
        llp_cancle_rela.height = CommonUtil.getRealWidth(140);
        llp_cancle_rela.topMargin = CommonUtil.getRealWidth(1040);
        llp_cancle_rela.leftMargin = CommonUtil.getRealWidth(20);
        llp_cancle_rela.rightMargin = CommonUtil.getRealWidth(20);

        cancle_text = (TextView) findViewById(R.id.cancle_text);
        cancle_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(36));

        clear = (ImageView) findViewById(R.id.clear);
        RelativeLayout.LayoutParams llp_clear = (RelativeLayout.LayoutParams) clear.getLayoutParams();
        llp_clear.width = CommonUtil.getRealWidth(40);
        llp_clear.height = llp_clear.width;
        llp_clear.topMargin = CommonUtil.getRealWidth(234);
        llp_clear.rightMargin = CommonUtil.getRealWidth(86);

        look = (ImageView) findViewById(R.id.look);
        RelativeLayout.LayoutParams llp_look = (RelativeLayout.LayoutParams) look.getLayoutParams();
        llp_look.width = CommonUtil.getRealWidth(40);
        llp_look.height = llp_look.width;
        llp_look.topMargin = CommonUtil.getRealWidth(356);
        llp_look.rightMargin = CommonUtil.getRealWidth(86);

        fail_text = (TextView) findViewById(R.id.fail_text);
        fail_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(24));
        RelativeLayout.LayoutParams llp_fail_text = (RelativeLayout.LayoutParams) fail_text.getLayoutParams();
        llp_fail_text.leftMargin = CommonUtil.getRealWidth(80);
        llp_fail_text.topMargin = CommonUtil.getRealWidth(440);

        psd_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    fail_text.setVisibility(View.GONE);
                    denglu_rela.setBackgroundResource(R.drawable.shape_crcle_green);
                } else {
                    denglu_rela.setBackgroundResource(R.drawable.spinner_gray_back);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        clear.setOnClickListener(this);
        look.setOnClickListener(this);
        cancle_rela.setOnClickListener(this);
        code_login.setOnClickListener(this);
        forget_psd.setOnClickListener(this);
        denglu_rela.setOnClickListener(this);
        user_info.setOnClickListener(this);
    }

    private static final int RESULT_FIND_PW = 100;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.clear: {
                phone_edit.setText("");
            }
            break;

            case R.id.look: {
                if (!isCheck) {
                    //如果选中，显示密码
                    look.setImageResource(R.mipmap.look_psd);
                    psd_edit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isCheck = true;
                } else {
                    //否则隐藏密码
                    look.setImageResource(R.mipmap.unlook_psd);
                    psd_edit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isCheck = false;
                }
            }
            break;

            case R.id.cancle_rela: {
                finish();
            }
            break;

            case R.id.code_login: {
                Intent intent = new Intent(PsdLoginActivity.this, PhoneLoginActivity.class);
                startActivity(intent);
                finish();
            }
            break;

            case R.id.forget_psd: {
                Intent intent = new Intent(PsdLoginActivity.this, FindPsdActivity.class);
                startActivityForResult(intent, RESULT_FIND_PW);
            }
            break;

            case R.id.denglu_rela: {
                num = phone_edit.getText().toString();
                psd = psd_edit.getText().toString();
                if (TextUtils.isEmpty(num) || TextUtils.isEmpty(psd)) {
                    if (TextUtils.isEmpty(num)) {
                        Toast.makeText(PsdLoginActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PsdLoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    if (CommonUtil.isMobileNum(num)) {
                        login();
                    } else {
                        Toast.makeText(PsdLoginActivity.this, "请正确填写手机号码", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;

            case R.id.user_info: {
                Intent intent = new Intent(PsdLoginActivity.this, UserSignInfoActivity.class);
                startActivity(intent);
            }
            break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case RESULT_FIND_PW :
                goResetPwView(data);
                break;
        }
    }

    private void goResetPwView(Intent data) {
        if (data == null || data.getExtras() == null) return;
        Intent intent = new Intent(this, ModifyPsdActivity.class);
        intent.putExtras(data.getExtras());
        startActivity(intent);
        finish();
    }

    private void login() {
        Call<PsdLoginResult> call = RetrofitUtils.getRetrofit().create(UserServer.class).psdLogin(num, psd);
        addCall(call);
        call.enqueue(new Callback<PsdLoginResult>() {
            @Override
            public void onResponse(Call<PsdLoginResult> call, Response<PsdLoginResult> response) {
                PsdLoginResult psdLoginResult = response.body();
                if (psdLoginResult == null) return;
                if (psdLoginResult.getRspCode() == 0) {
                    PsdLoginResult.DataBean data = psdLoginResult.getData();
                    MyApplication.getInstance().setNeedRefresh(true);
                    UserBean userBean = data.getTkmUserInfo();
                    LoginUtils.saveToken(data.getToken(), PsdLoginActivity.this);
                    LoginUtils.saveUserBean(userBean, PsdLoginActivity.this);
                    finish();
                } else if (psdLoginResult.getRspMsg() != null) {
                    fail_text.setVisibility(View.VISIBLE);
                    Toast.makeText(PsdLoginActivity.this, psdLoginResult.getRspMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PsdLoginResult> call, Throwable t) {

            }
        });
    }
}

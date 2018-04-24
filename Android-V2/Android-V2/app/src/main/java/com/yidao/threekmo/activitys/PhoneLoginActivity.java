package com.yidao.threekmo.activitys;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsMessage;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ijustyce.fastkotlin.utils.ILog;
import com.ijustyce.fastkotlin.utils.RegularUtils;
import com.ijustyce.fastkotlin.utils.RunTimePermission;
import com.ijustyce.fastkotlin.utils.SmsUtils;
import com.yidao.myutils.toast.ToastUtil;
import com.yidao.threekmo.R;
import com.yidao.threekmo.application.MyApplication;
import com.yidao.threekmo.bean.BaseResult;
import com.yidao.threekmo.bean.CodeLoginResult;
import com.yidao.threekmo.bean.UserBean;
import com.yidao.threekmo.retrofit_server.RegServer;
import com.yidao.threekmo.retrofit_server.UserServer;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.LoginUtils;
import com.yidao.threekmo.utils.RetrofitUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneLoginActivity extends BaseActivity implements View.OnClickListener {

    private String num = "";
    private String code = "";

    private RelativeLayout all_rela;
    private TextView logo_name;
    private EditText phone_edit;
    private ImageView clear;
    private EditText code_edit;
    private RelativeLayout code_rela;
    private TextView code_text;
    private RelativeLayout deng_rela;
    private TextView deng_text;
    private TextView user_info;
    private TextView psd_login;
    private View view1;
    private View view2;
    private TextView fail_info;
    private RelativeLayout cancle_rela;
    private TextView cancle_text;
    private SmsUtils smsUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        viewInit();
        initPermission(this);
        smsUtils = new SmsUtils().initSmsReceiver(this, new SmsUtils.OnSms() {
            @Override
            public void onReceive(@NotNull SmsMessage smsMessage) {
                String body = smsMessage.getMessageBody();
                ILog.INSTANCE.e("===body===", body);
                String code = getCodeFromString(body);
                code_edit.setText(code);
                beginLogin();
            }
        });
    }

    public static String getCodeFromString(String value) {
        if (value == null || !value.startsWith("【三公里】您的验证码:") || value.length() < 17) {
            return null;
        }
        return value.substring(11, 17);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (smsUtils != null) smsUtils.destroy(this);
        smsUtils = null;
        code_rela = null;
        code_edit = null;
    }

    public static void initPermission(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
        ArrayList<String> arrayList = new ArrayList<>();
        RunTimePermission runTimePermission = new RunTimePermission(activity);
        if (!runTimePermission.checkPermissionByValue(Manifest.permission.RECEIVE_SMS)) {
            arrayList.add(Manifest.permission.RECEIVE_SMS);
        }
        if (!runTimePermission.checkPermissionByValue(Manifest.permission.READ_SMS)) {
            arrayList.add(Manifest.permission.READ_SMS);
        }
        runTimePermission.requestPermissions(arrayList);
    }

    private void viewInit() {
        all_rela = (RelativeLayout) findViewById(R.id.all_rela);
        RelativeLayout.LayoutParams llp_all_rela = (RelativeLayout.LayoutParams) all_rela.getLayoutParams();
        llp_all_rela.width = CommonUtil.screenWidth;
        llp_all_rela.height = CommonUtil.getRealWidth(862);
        llp_all_rela.topMargin = CommonUtil.getRealWidth(200);
        llp_all_rela.leftMargin = CommonUtil.getRealWidth(20);
        llp_all_rela.rightMargin = CommonUtil.getRealWidth(20);

        logo_name = (TextView) findViewById(R.id.logo_name);
        logo_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(44));
        RelativeLayout.LayoutParams llp_logo_name = (RelativeLayout.LayoutParams) logo_name.getLayoutParams();
        llp_logo_name.topMargin = CommonUtil.getRealWidth(80);

        phone_edit = (EditText) findViewById(R.id.phone_edit);
        phone_edit.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        phone_edit.setPadding(CommonUtil.getRealWidth(40), 0, 0, 0);
        RelativeLayout.LayoutParams llp_phone_edit = (RelativeLayout.LayoutParams) phone_edit.getLayoutParams();
        llp_phone_edit.height = CommonUtil.getRealWidth(100);
        llp_phone_edit.leftMargin = CommonUtil.getRealWidth(54);
        llp_phone_edit.rightMargin = CommonUtil.getRealWidth(54);
        llp_phone_edit.topMargin = CommonUtil.getRealWidth(204);

        clear = (ImageView) findViewById(R.id.clear);
        RelativeLayout.LayoutParams llp_clear = (RelativeLayout.LayoutParams) clear.getLayoutParams();
        llp_clear.width = CommonUtil.getRealWidth(40);
        llp_clear.height = llp_clear.width;
        llp_clear.topMargin = CommonUtil.getRealWidth(234);
        llp_clear.rightMargin = CommonUtil.getRealWidth(86);

        code_edit = (EditText) findViewById(R.id.code_edit);
        code_edit.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        code_edit.setPadding(CommonUtil.getRealWidth(40), 0, 0, 0);
        RelativeLayout.LayoutParams llp_code_edit = (RelativeLayout.LayoutParams) code_edit.getLayoutParams();
        llp_code_edit.height = CommonUtil.getRealWidth(100);
        llp_code_edit.width = CommonUtil.getRealWidth(368);
        llp_code_edit.leftMargin = CommonUtil.getRealWidth(52);
        llp_code_edit.topMargin = CommonUtil.getRealWidth(326);

        code_rela = (RelativeLayout) findViewById(R.id.code_rela);
        RelativeLayout.LayoutParams llp_code_rela = (RelativeLayout.LayoutParams) code_rela.getLayoutParams();
        llp_code_rela.width = CommonUtil.getRealWidth(194);
        llp_code_rela.height = CommonUtil.getRealWidth(80);
        llp_code_rela.topMargin = CommonUtil.getRealWidth(336);
        llp_code_rela.rightMargin = CommonUtil.getRealWidth(68);

        code_text = (TextView) findViewById(R.id.code_text);
        code_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));

        deng_rela = (RelativeLayout) findViewById(R.id.deng_rela);
        RelativeLayout.LayoutParams llp_deng_rela = (RelativeLayout.LayoutParams) deng_rela.getLayoutParams();
        llp_deng_rela.width = CommonUtil.getRealWidth(624);
        llp_deng_rela.height = CommonUtil.getRealWidth(80);
        llp_deng_rela.topMargin = CommonUtil.getRealWidth(498);

        deng_text = (TextView) findViewById(R.id.deng_text);
        deng_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(36));

        user_info = (TextView) findViewById(R.id.user_info);
        String str = "<font color='#c0c0d1'>点击登陆即表示您同意</font><font color='#1aba6e'>《用户协议》</font>";
        user_info.setText(Html.fromHtml(str));
        user_info.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(24));
        RelativeLayout.LayoutParams llp_user_info = (RelativeLayout.LayoutParams) user_info.getLayoutParams();
        llp_user_info.topMargin = CommonUtil.getRealWidth(598);
        llp_user_info.leftMargin = CommonUtil.getRealWidth(72);

        psd_login = (TextView) findViewById(R.id.psd_login);
        psd_login.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(24));
        RelativeLayout.LayoutParams llp_psd_login = (RelativeLayout.LayoutParams) psd_login.getLayoutParams();
        llp_psd_login.topMargin = CommonUtil.getRealWidth(686);

        view1 = findViewById(R.id.view1);
        RelativeLayout.LayoutParams llp_view1 = (RelativeLayout.LayoutParams) view1.getLayoutParams();
        llp_view1.width = CommonUtil.getRealWidth(156);
        llp_view1.topMargin = CommonUtil.getRealWidth(702);
        llp_view1.leftMargin = CommonUtil.getRealWidth(126);

        view2 = findViewById(R.id.view2);
        RelativeLayout.LayoutParams llp_view2 = (RelativeLayout.LayoutParams) view2.getLayoutParams();
        llp_view2.width = CommonUtil.getRealWidth(156);
        llp_view2.topMargin = CommonUtil.getRealWidth(702);
        llp_view2.rightMargin = CommonUtil.getRealWidth(126);

        fail_info = (TextView) findViewById(R.id.fail_info);
        fail_info.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(24));
        RelativeLayout.LayoutParams llp_fail_info = (RelativeLayout.LayoutParams) fail_info.getLayoutParams();
        llp_fail_info.topMargin = CommonUtil.getRealWidth(450);
        llp_fail_info.leftMargin = CommonUtil.getRealWidth(72);

        code_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    deng_rela.setBackgroundResource(R.drawable.shape_crcle_green);
                    fail_info.setVisibility(View.GONE);
                } else {
                    deng_rela.setBackgroundResource(R.drawable.spinner_gray_back);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cancle_rela = (RelativeLayout) findViewById(R.id.cancle_rela);
        RelativeLayout.LayoutParams llp_cancle_rela = (RelativeLayout.LayoutParams) cancle_rela.getLayoutParams();
        llp_cancle_rela.width = CommonUtil.screenWidth;
        llp_cancle_rela.height = CommonUtil.getRealWidth(140);
        llp_cancle_rela.topMargin = CommonUtil.getRealWidth(1040);
        llp_cancle_rela.leftMargin = CommonUtil.getRealWidth(20);
        llp_cancle_rela.rightMargin = CommonUtil.getRealWidth(20);

        cancle_text = (TextView) findViewById(R.id.cancle_text);
        cancle_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(36));

        code_rela.setOnClickListener(this);
        deng_rela.setOnClickListener(this);
        cancle_rela.setOnClickListener(this);
        psd_login.setOnClickListener(this);
        clear.setOnClickListener(this);
        user_info.setOnClickListener(this);
    }

    private void beginLogin() {
        num = phone_edit.getText().toString();
        code = code_edit.getText().toString();
        if (TextUtils.isEmpty(num) || TextUtils.isEmpty(code)) {
            if (TextUtils.isEmpty(num)) {
                Toast.makeText(PhoneLoginActivity.this, "电话号码不能为空", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PhoneLoginActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (RegularUtils.INSTANCE.isMobilePhone(num)) {
                login();
            } else {
                Toast.makeText(PhoneLoginActivity.this, "请正确填写手机号码", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.code_rela: {
                getVarCode();
            }
            break;

            case R.id.deng_rela:
                beginLogin();
                break;

            case R.id.cancle_rela: {
                finish();
            }
            break;

            case R.id.psd_login: {
                Intent intent = new Intent(PhoneLoginActivity.this, PsdLoginActivity.class);
                startActivity(intent);
                finish();
            }
            break;

            case R.id.clear: {
                phone_edit.setText("");
            }
            break;

            case R.id.user_info: {
                Intent intent = new Intent(PhoneLoginActivity.this, UserSignInfoActivity.class);
                startActivity(intent);
            }
            break;

            default:
                break;
        }
    }

    private void getVarCode() {
        code_rela.setEnabled(false);
        num = phone_edit.getText().toString();
        final CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (code_text == null || code_rela == null) {
                    cancel();
                    return;
                }
                code_text.setText("再次验证" + millisUntilFinished / 1000 + "S");
            }

            @Override
            public void onFinish() {
                if (code_text == null || code_rela == null) {
                    cancel();
                    return;
                }
                code_text.setText("发送验证码");
                code_rela.setEnabled(true);
            }
        };

        RegServer regServer = RetrofitUtils.getRetrofit().create(RegServer.class);
        Call<BaseResult> call = regServer.getLoginCode(num);
        addCall(call);
        call.enqueue(new Callback<BaseResult>() {
            @Override
            public void onResponse(Call<BaseResult> call, Response<BaseResult> response) {
                if (code_rela == null || code_edit == null) return;
                code_rela.setEnabled(true);
                BaseResult body = response.body();
                if (body == null) return;
                ToastUtil.showToast(body.getRspMsg(), false, PhoneLoginActivity.this);
                if (body.getRspCode() == 0) {
                    countDownTimer.start();
                    code_rela.setEnabled(false);
                    code_edit.requestFocus();
                }
            }

            @Override
            public void onFailure(Call<BaseResult> call, Throwable t) {
                ToastUtil.showToast(t.getMessage(), false, PhoneLoginActivity.this);
                if (code_rela == null || code_edit == null) return;
                code_rela.setEnabled(true);
            }
        });
    }


    private void login() {

        Call<CodeLoginResult> call = RetrofitUtils.getRetrofit().create(UserServer.class).codeLogin(num, code);
        addCall(call);
        call.enqueue(new Callback<CodeLoginResult>() {
            @Override
            public void onResponse(Call<CodeLoginResult> call, Response<CodeLoginResult> response) {
                CodeLoginResult loginResult = response.body();
                if (loginResult != null && loginResult.getRspCode() == 0) {
                    CodeLoginResult.DataBean data = loginResult.getData();
                    MyApplication.getInstance().setNeedRefresh(true);
                    UserBean userBean = data.getTkmUserInfo();
                    String token = data.getToken();
                    LoginUtils.saveToken(data.getToken(), PhoneLoginActivity.this);
                    LoginUtils.saveUserBean(userBean, PhoneLoginActivity.this);
                    if (data.isInit()) {
                        //没有密码，跳密码设置页面
                        Intent intent = new Intent(PhoneLoginActivity.this, SetPsdActivity.class);
                        intent.putExtra("num", num);
                        startActivity(intent);
                        finish();
                    } else {
                        finish();
                        //有密码，直接跳主页
//                        startActivity(new Intent(PhoneLoginActivity.this,MainActivity.class));
//                        startActivity(new Intent(PhoneLoginActivity.this,IndexActivity.class));
//                        finish();
                    }
                } else {
                    fail_info.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<CodeLoginResult> call, Throwable t) {
                ToastUtil.showToast(t.getMessage(), false, PhoneLoginActivity.this);

            }
        });


    }

}

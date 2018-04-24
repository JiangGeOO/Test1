package com.yidao.threekmo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsMessage;
import android.text.Editable;
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
import com.ijustyce.fastkotlin.utils.SmsUtils;
import com.yidao.myutils.toast.ToastUtil;
import com.yidao.threekmo.R;
import com.yidao.threekmo.bean.BaseResult;
import com.yidao.threekmo.retrofit_server.RegServer;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.RetrofitUtils;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindPsdActivity extends BaseActivity implements View.OnClickListener {
    private String num = "";
    private String code = "";
    //1:从登录界面过来 2：从设置界面过来
    private int type;

    private ImageView close;
    private RelativeLayout all_rela;
    private TextView tvTitle;
    private EditText edit_phone;
    private EditText code_edit;
    private RelativeLayout code_rela;
    private TextView code_text;
    private RelativeLayout deng_rela;
    private TextView deng_text;
    private ImageView clear;
    private RelativeLayout cancle_rela;
    private TextView cancle_text;
    private TextView fail_info;
    private SmsUtils smsUtils;

    public static final int TYPE_PAY_PW = 11;
    public static final int TYPE_FIND_PW = 12;
    public static final int TYPE_SET_PW = 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_psd);
        if (getIntent() != null) {
            type = getIntent().getIntExtra("type", -1);
        }
        viewInit();
        PhoneLoginActivity.initPermission(this);
        smsUtils = new SmsUtils().initSmsReceiver(this, new SmsUtils.OnSms() {
            @Override
            public void onReceive(@NotNull SmsMessage smsMessage) {
                String body = smsMessage.getMessageBody();
                ILog.INSTANCE.e("===body===", body);
                code = PhoneLoginActivity.getCodeFromString(body);
                code_edit.setText(code);
                login();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (smsUtils != null) smsUtils.destroy(this);
        smsUtils = null;
    }

    private void viewInit() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(44));
        RelativeLayout.LayoutParams llp_tvTitle = (RelativeLayout.LayoutParams) tvTitle.getLayoutParams();
        llp_tvTitle.topMargin = CommonUtil.getRealWidth(80);
        switch (type) {
            case TYPE_FIND_PW:
                tvTitle.setText("找回密码");
                break;
            case TYPE_SET_PW:
                tvTitle.setText("设置密码");
                break;
            case TYPE_PAY_PW:
                tvTitle.setText("验证信息");
                break;
            default:
                tvTitle.setText("找回密码");
        }
        close = (ImageView) findViewById(R.id.close);
        RelativeLayout.LayoutParams llp_close = (RelativeLayout.LayoutParams) close.getLayoutParams();
        llp_close.width = CommonUtil.getRealWidth(48);
        llp_close.height = llp_close.width;
        llp_close.leftMargin = CommonUtil.getRealWidth(30);
        llp_close.topMargin = CommonUtil.getRealWidth(30) + CommonUtil.getStatusBarHeight(FindPsdActivity.this);

        all_rela = (RelativeLayout) findViewById(R.id.all_rela);
        RelativeLayout.LayoutParams llp_all_rela = (RelativeLayout.LayoutParams) all_rela.getLayoutParams();
        llp_all_rela.height = CommonUtil.getRealWidth(682);
        llp_all_rela.width = CommonUtil.screenWidth;
        llp_all_rela.topMargin = CommonUtil.getRealWidth(200);
        llp_all_rela.leftMargin = CommonUtil.getRealWidth(20);
        llp_all_rela.rightMargin = CommonUtil.getRealWidth(20);

        edit_phone = (EditText) findViewById(R.id.edit_phone);
        edit_phone.setPadding(CommonUtil.getRealWidth(40), 0, 0, 0);
        edit_phone.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_edit_phone = (RelativeLayout.LayoutParams) edit_phone.getLayoutParams();
        llp_edit_phone.height = CommonUtil.getRealWidth(100);
        llp_edit_phone.leftMargin = CommonUtil.getRealWidth(54);
        llp_edit_phone.rightMargin = CommonUtil.getRealWidth(54);
        llp_edit_phone.topMargin = CommonUtil.getRealWidth(204);

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

        clear = (ImageView) findViewById(R.id.clear);
        RelativeLayout.LayoutParams llp_clear = (RelativeLayout.LayoutParams) clear.getLayoutParams();
        llp_clear.width = CommonUtil.getRealWidth(40);
        llp_clear.height = llp_clear.width;
        llp_clear.topMargin = CommonUtil.getRealWidth(234);
        llp_clear.rightMargin = CommonUtil.getRealWidth(86);

        cancle_rela = (RelativeLayout) findViewById(R.id.cancle_rela);
        RelativeLayout.LayoutParams llp_cancle_rela = (RelativeLayout.LayoutParams) cancle_rela.getLayoutParams();
        llp_cancle_rela.width = CommonUtil.screenWidth;
        llp_cancle_rela.height = CommonUtil.getRealWidth(140);
        llp_cancle_rela.topMargin = CommonUtil.getRealWidth(870);
        llp_cancle_rela.leftMargin = CommonUtil.getRealWidth(20);
        llp_cancle_rela.rightMargin = CommonUtil.getRealWidth(20);

        cancle_text = (TextView) findViewById(R.id.cancle_text);
        cancle_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(36));

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

        clear.setOnClickListener(this);
        code_rela.setOnClickListener(this);
        deng_rela.setOnClickListener(this);
        cancle_rela.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear: {
                edit_phone.setText("");
            }
            break;

            case R.id.code_rela: {
                num = edit_phone.getText().toString();
                getVarCode();
            }
            break;

            case R.id.deng_rela: {
                num = edit_phone.getText().toString();
                code = code_edit.getText().toString();
                if (TextUtils.isEmpty(num) || TextUtils.isEmpty(code)) {
                    if (TextUtils.isEmpty(num)) {
                        Toast.makeText(FindPsdActivity.this, "电话号码不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(FindPsdActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (CommonUtil.isMobileNum(num)) {
                        login();
                    } else {
                        Toast.makeText(FindPsdActivity.this, "请正确填写手机号码", Toast.LENGTH_SHORT).show();
                    }
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

    private void getVarCode() {
        code_rela.setEnabled(false);
        final CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (code_text == null) {
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
        Call<BaseResult> call = regServer.getModifyCode(num);
        addCall(call);
        call.enqueue(new Callback<BaseResult>() {
            @Override
            public void onResponse(Call<BaseResult> call, Response<BaseResult> response) {

                BaseResult body = response.body();
                if (body != null && body.getRspMsg() != null) {
                    ToastUtil.showToast(body.getRspMsg(), false, FindPsdActivity.this);
                }
                if (body != null && body.getRspCode() == 0 && code_edit != null) {
                    countDownTimer.start();
                    code_edit.requestFocus();
                }
            }

            @Override
            public void onFailure(Call<BaseResult> call, Throwable t) {
                ToastUtil.showToast(t.getMessage(), false, FindPsdActivity.this);

            }
        });

    }

    private void login() {
        Call<BaseResult> call = RetrofitUtils.getRetrofit().create(RegServer.class).checkCode(num, 2, code);
        addCall(call);
        call.enqueue(new Callback<BaseResult>() {
            @Override
            public void onResponse(Call<BaseResult> call, Response<BaseResult> response) {
                BaseResult baseResult = response.body();
                if (baseResult != null && baseResult.getRspCode() == 0) {
                    Intent intent = new Intent();
                    intent.putExtra("num", num);
                    intent.putExtra("code", code);
                    setResult(RESULT_OK, intent);
                    finish();
                } else if (fail_info != null) {
                    fail_info.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<BaseResult> call, Throwable t) {
                Toast.makeText(FindPsdActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

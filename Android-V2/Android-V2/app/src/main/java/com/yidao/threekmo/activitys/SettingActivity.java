package com.yidao.threekmo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yidao.myutils.file.FileUtils;
import com.yidao.myutils.file.SPUtils;
import com.yidao.threekmo.R;
import com.yidao.threekmo.application.MyApplication;
import com.yidao.threekmo.customview.CopyEaseDialog;
import com.yidao.threekmo.customview.SuccessToast;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TextView tvLoginOut;
    private ImageView setback;
    private LinearLayout psdSet;
    private TextView cacheSize;
    private LinearLayout cacheLinear;
    private LinearLayout aboutSkmoLinear;
    private TextView aboutSkmo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewInit();
        setData();
    }

    private void setData() {
        try {
            String allCacheSize = FileUtils.getTotalCacheSize(SettingActivity.this);
            cacheSize.setText(allCacheSize);
            aboutSkmo.setText("关于三公里(V"+MyApplication.getInstance().getDevice_verson_name()+")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void viewInit() {
        tvLoginOut = (TextView) findViewById(R.id.tvLoginOut);
        tvLoginOut.setOnClickListener(this);
        setback = (ImageView) findViewById(R.id.setback);
        setback.setOnClickListener(this);
        psdSet = (LinearLayout) findViewById(R.id.psdSet);
        psdSet.setOnClickListener(this);
        cacheSize = (TextView) findViewById(R.id.cacheSize);
        cacheLinear = (LinearLayout) findViewById(R.id.cacheLinear);
        cacheLinear.setOnClickListener(this);
        aboutSkmoLinear = (LinearLayout) findViewById(R.id.aboutSkmoLinear);
        aboutSkmoLinear.setOnClickListener(this);
        aboutSkmo = (TextView) findViewById(R.id.aboutSkmo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvLoginOut:
                loginOut();
                break;
            case R.id.setback:
                finish();
                break;

            case R.id.psdSet:
                Intent intent = new Intent(SettingActivity.this,FindPsdActivity.class);
                intent.putExtra("type",FindPsdActivity.TYPE_SET_PW);
                startActivity(intent);
                break;
            case R.id.cacheLinear:
                CopyEaseDialog.AlertDialogUser alert = new CopyEaseDialog.AlertDialogUser() {
                    @Override
                    public void onResult(boolean confirmed, Bundle bundle) {
                        if(confirmed){
                            try {
                                FileUtils.clearAllCache(SettingActivity.this);
                                String allCacheSize = FileUtils.getTotalCacheSize(SettingActivity.this);
                                cacheSize.setText(allCacheSize);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    }
                };
                new CopyEaseDialog(SettingActivity.this,"是否要清理缓存",null,null,alert,true).show();
                break;

            default:
                break;
        }
    }

    private void loginOut(){
        SPUtils.clear(SettingActivity.this);
        SuccessToast.showToast("退出登录",false,SettingActivity.this);
        MyApplication.getInstance().setNeedRefresh(true);
//        startActivity(new Intent(SettingActivity.this,MainActivity.class));
        startActivity(new Intent(SettingActivity.this,IndexActivity.class));
    }


}

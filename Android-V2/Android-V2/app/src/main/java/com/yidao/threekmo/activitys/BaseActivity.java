package com.yidao.threekmo.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.umeng.analytics.MobclickAgent;
import com.yidao.threekmo.customview.AndroidWorkaround;
import com.yidao.threekmo.utils.RetrofitManager;

import retrofit2.Call;


/**
 * Created by Administrator on 2017\8\21 0021.
 */

public class BaseActivity extends AppCompatActivity {


    private RetrofitManager retrofitManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!cancelAutoNavigationBar() && AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
        }
        retrofitManager = new RetrofitManager();
    }

    public boolean cancelAutoNavigationBar() {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        retrofitManager.cancleAllCall();
}

    public void addCall(Call call){
        retrofitManager.addCall(call);
    }

}

package com.yidao.threekmo.activitys;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ijustyce.fastkotlin.utils.AutoLayoutKt;
import com.ijustyce.fastkotlin.utils.ILog;
import com.ijustyce.fastkotlin.utils.RunTimePermission;
import com.yidao.threekmo.R;
import com.yidao.threekmo.bean.DialogDetailResult;
import com.yidao.threekmo.bean.DialogResult;
import com.yidao.threekmo.bean.DialogSecondResult;
import com.yidao.threekmo.customview.CopyEaseDialog;
import com.yidao.threekmo.customview.IndexEaseDialog;
import com.yidao.threekmo.events.EventCity;
import com.yidao.threekmo.fragment.MySelfFragment;
import com.yidao.threekmo.fragment.NearByFragment;
import com.yidao.threekmo.fragment.NewExperienceFragment;
import com.yidao.threekmo.map.*;
import com.yidao.threekmo.retrofit_server.SubjectServer;
import com.yidao.threekmo.utils.LoginUtils;
import com.yidao.threekmo.utils.RetrofitUtils;
import com.yidao.threekmo.v2.activity.WithdrawalDetailActivity;
import com.yidao.threekmo.v2.fragment.CloudShopFragment;
import com.yidao.threekmo.v2.fragment.IndexFragment;
import com.yidao.threekmo.v2.fragment.SeeShopFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IndexActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    private BottomNavigationBar bottom_navigation_bar;
    private IndexFragment newFirstFragment;
    private NearByFragment nearByFragment;
    private SeeShopFragment seeShopFragment;
    private MySelfFragment mySelfFragment;
    private CloudShopFragment newShopFragment;
    private NewExperienceFragment newExperienceFragment;
    private String url = "";
    private boolean showCloudShop, showExperience;
    private DialogDetailResult dialogDetailResult;
    private Fragment lastShowFragment;
    private boolean drogue;
    private boolean nearBy;

    private static int oldFlag;
    private static int oldColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        oldFlag = getWindow().getDecorView().getSystemUiVisibility();
        oldColor = Color.parseColor("#00000000");

        EventBus.getDefault().register(this);

        if (getIntent() != null) {
            showCloudShop = getIntent().getBooleanExtra("showCloudShop", false);
            showExperience = getIntent().getBooleanExtra("showExperience", false);
            url = getIntent().getStringExtra("url");
            drogue = getIntent().getBooleanExtra("drogue", false);
            nearBy = getIntent().getBooleanExtra("nearBy", false);
        }
        initViews();
        initFragments();
        achieveData();
        if (!TextUtils.isEmpty(url)) {
            Intent intent = new Intent(IndexActivity.this, WebViewScriptActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
        }

//        Intent intent = new Intent(this, WebViewScriptActivity.class);
//        intent.putExtra("url", "https://api.laozihaojia.com/album/share/album-share.html?flowId=2380638&isApp=1");
//        intent.putExtra("title", "网星大赛投票");
//        startActivity(intent);

        initPermission();
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
        ArrayList<String> arrayList = new ArrayList<>();
        RunTimePermission runTimePermission = new RunTimePermission(this);
        if (!runTimePermission.checkPermissionForLocation()) {
            arrayList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            arrayList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }if (!runTimePermission.checkPermissionByValue(Manifest.permission.READ_PHONE_STATE)) {
            arrayList.add(Manifest.permission.READ_PHONE_STATE);
        }if (!runTimePermission.checkPermissionForExternalStorage()) {
            arrayList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }if (!runTimePermission.checkPermissionForCamera()) {
            arrayList.add(Manifest.permission.CAMERA);
        }
        runTimePermission.requestPermissions(arrayList);
    }

    private void achieveData() {
        Call<DialogResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).dialogFlag(LoginUtils.getToken(IndexActivity.this));
        addCall(call);
        call.enqueue(new Callback<DialogResult>() {
            @Override
            public void onResponse(Call<DialogResult> call, Response<DialogResult> response) {
                DialogResult body = response.body();
                if (body != null) {
                    DialogSecondResult dialogSecondResult = body.getData().getData();
                    dialogDetailResult = body.getData().getData().getCandidate();
                    if (dialogDetailResult != null) {
                        if (dialogSecondResult.getShowcasing() == 1) {
                            new IndexEaseDialog(IndexActivity.this, dialogSecondResult, bottom_navigation_bar).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<DialogResult> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe          //订阅事件FirstEvent
    public void onEventMainThread(EventCity event) {
//        activeFragment.cityRefresh(event.getCity().toString());
    //    firstFragment.cityRefresh(event.getCity().toString());
    }

    private void initFragments() {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        newFirstFragment = new IndexFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("drogue", drogue);
        newFirstFragment.setArguments(bundle);
        ft.add(R.id.fragment_replace, newFirstFragment);
        lastShowFragment = newFirstFragment;
        ft.commit();
    }


    private void initViews() {

        bottom_navigation_bar = findViewById(R.id.bottom_navigation_bar);
        bottom_navigation_bar.setTabSelectedListener(this);
        bottom_navigation_bar.setMode(BottomNavigationBar.MODE_FIXED);
        bottom_navigation_bar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottom_navigation_bar
                .addItem(new BottomNavigationItem(R.mipmap.icon_tab_index_focus, "首页")
                        .setActiveColorResource(R.color.tu_biao)
                        .setInactiveIconResource(R.mipmap.active_act))

                .addItem(new BottomNavigationItem(R.mipmap.icon_tab_nearby_focus, nearBy ? "附近": "探店")
                        .setInactiveIconResource(R.mipmap.active_index_location)
                        .setActiveColorResource(R.color.tu_biao));
        if (showCloudShop) {
            bottom_navigation_bar.addItem(new BottomNavigationItem(R.mipmap.icon_tab_shop_focus, "云商城")
                    .setInactiveIconResource(R.mipmap.active_shop).setActiveColorResource(R.color.tu_biao));
        }if (showExperience) {
            bottom_navigation_bar.addItem(new BottomNavigationItem(R.mipmap.icon_tab_tiyan_focus, "体验")
                    .setInactiveIconResource(R.mipmap.active_exper).setActiveColorResource(R.color.tu_biao));
        }
        bottom_navigation_bar.addItem(new BottomNavigationItem(R.mipmap.icon_tab_me_focus, "我的")
                .setInactiveIconResource(R.mipmap.active_index_user).setActiveColorResource(R.color.tu_biao))
                .setInActiveColor(R.color.inactive_tab_text)
                .setFirstSelectedPosition(0)
                .initialise();

        View view = findViewById(R.id.line);
        AutoLayoutKt.setHeight(view, 2);
    }

    @Override
    public void onTabSelected(int position) {
        FragmentManager fg = getSupportFragmentManager();
        FragmentTransaction ft = fg.beginTransaction();
        switch (position) {
            case 1:
                if (nearBy) {
                    if (nearByFragment == null) {
                        nearByFragment = NearByFragment.newInstance("");
                        ft.add(R.id.fragment_replace, nearByFragment);
                    }
                    show(nearByFragment, ft);
                }else {
                    if (seeShopFragment == null) {
                        seeShopFragment = new SeeShopFragment();
                        ft.add(R.id.fragment_replace, seeShopFragment);
                    }
                    show(seeShopFragment, ft);
                }
                break;
            case 2:
//                if (showCloudShop) {
//                    if (newShopFragment == null) {
//                        newShopFragment = new CloudShopFragment();
//                        ft.add(R.id.fragment_replace, newShopFragment);
//                    }
//                    show(newShopFragment, ft);
//                }else if (showExperience) {
//                    if (newExperienceFragment == null) {
//                        newExperienceFragment = NewExperienceFragment.newInstance("");
//                        ft.add(R.id.fragment_replace, newExperienceFragment);
//                    }
//                    show(newExperienceFragment, ft);
//                }else {
//                    if (mySelfFragment == null) {
//                        mySelfFragment = MySelfFragment.newInstance("");
//                        ft.add(R.id.fragment_replace, mySelfFragment);
//                    }
//                    show(mySelfFragment, ft);
//                }
                Intent intent=new Intent(IndexActivity.this, com.yidao.threekmo.map.MapActivity.class);
                startActivity(intent);
                break;
            case 3:
                if (showExperience && showCloudShop) {
                    if (newExperienceFragment == null) {
                        newExperienceFragment = NewExperienceFragment.newInstance("");
                        ft.add(R.id.fragment_replace, newExperienceFragment);
                    }
                    show(newExperienceFragment, ft);
                }else {
                    if (mySelfFragment == null) {
                        mySelfFragment = MySelfFragment.newInstance("");
                        ft.add(R.id.fragment_replace, mySelfFragment);
                    }
                    show(mySelfFragment, ft);
                }
                break;
            case 4:
                if (mySelfFragment == null) {
                    mySelfFragment = MySelfFragment.newInstance("");
                    ft.add(R.id.fragment_replace, mySelfFragment);
                }
                show(mySelfFragment, ft);
                break;
            case 0:
                if (newFirstFragment == null || newFirstFragment.getContentView() == null) {
                    newFirstFragment = new IndexFragment();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("drogue", drogue);
                    newFirstFragment.setArguments(bundle);
                    ft.add(R.id.fragment_replace, newFirstFragment);
                }
                show(newFirstFragment, ft);
                break;
            default:
                break;
        }
        // 事务提交
        ft.commit();
    }

    public static void setLightStatusBar(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = activity.getWindow().getDecorView().getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            activity.getWindow().getDecorView().setSystemUiVisibility(flags);
        }if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(oldColor);
        }
    }
    public static void clearLightStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ILog.INSTANCE.e("===flag===", "flag is " + oldFlag);
            activity.getWindow().getDecorView().setSystemUiVisibility(oldFlag);
        }if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            oldColor = activity.getWindow().getStatusBarColor();
            activity.getWindow().setStatusBarColor(Color.parseColor("#0d000000"));
        }
    }

    private void show(Fragment fragment, FragmentTransaction ft) {
        if (fragment instanceof CloudShopFragment) setLightStatusBar(this);
        else if (lastShowFragment instanceof CloudShopFragment) clearLightStatusBar(this);
        if (lastShowFragment != null) ft.hide(lastShowFragment);
        ft.show(fragment);
        lastShowFragment = fragment;
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onBackPressed() {
        new CopyEaseDialog(IndexActivity.this, "确定退出么？", null, null, new CopyEaseDialog.AlertDialogUser() {
            @Override
            public void onResult(boolean confirmed, Bundle bundle) {
                if (confirmed) {
                    finish();
                }
            }
        }, true).show();
    }

}

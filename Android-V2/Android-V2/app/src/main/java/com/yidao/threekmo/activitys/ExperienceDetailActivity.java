package com.yidao.threekmo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yidao.threekmo.R;
import com.yidao.threekmo.adapter.BaseRvAdapter;
import com.yidao.threekmo.adapter.ExperienceConnectAdapter;
import com.yidao.threekmo.adapter.ExperienceViewpagerAdapter;
import com.yidao.threekmo.bean.ActivityBaomingResult;
import com.yidao.threekmo.bean.ExperienceConnectResult;
import com.yidao.threekmo.bean.ExperienceDetailResult;
import com.yidao.threekmo.bean.ExperiencePhotoListResult;
import com.yidao.threekmo.bean.ExperiencePhotoXyResult;
import com.yidao.threekmo.bean.ExperienceResult;
import com.yidao.threekmo.bean.ShopDetailResult;
import com.yidao.threekmo.customview.CopyEaseDialog;
import com.yidao.threekmo.retrofit_server.SubjectServer;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.LoginUtils;
import com.yidao.threekmo.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExperienceDetailActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout title_rela;
    private ImageView title_back;
    private TextView title_text;
    private ImageView title_ach;
    private RelativeLayout num_rela;
    private TextView photo_num;

    private ViewPager viewPager;
    private ExperienceViewpagerAdapter experienceViewpagerAdapter;
    private View selectView;
    private boolean selectFlag = false;

    private RecyclerView recyclerview;
    private ExperienceConnectAdapter experienceConnectAdapter;
    private List<ShopDetailResult> dataList;
    private long experienceId = 0;
    private ExperienceResult experienceResult;


    private List<View> relaList = new ArrayList<View>();
    private boolean collectFlag = false;

    private long experId = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_detail);

        if (getIntent() != null) {
            experId = getIntent().getLongExtra("experId", experId);
        }
        initViews();
        achieveData();
        setData();

    }

    private void setData() {

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                position++;
                photo_num.setText("0" + position + "/" + "0" + relaList.size());
                recyclerview.setVisibility(View.GONE);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void achieveData() {
        Call<ExperienceDetailResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).experienceDeatil(LoginUtils.getToken(ExperienceDetailActivity.this), experId);
        addCall(call);
        call.enqueue(new Callback<ExperienceDetailResult>() {
            @Override
            public void onResponse(Call<ExperienceDetailResult> call, Response<ExperienceDetailResult> response) {
                ExperienceDetailResult body = response == null ? null : response.body();
                if (body == null || body.getData() == null) return;
                if (body.getData().getData().getCoolect() == 0) {
                    collectFlag = false;
                    title_ach.setImageResource(R.mipmap.exper);
                } else {
                    collectFlag = true;
                    title_ach.setImageResource(R.mipmap.achieve_select);
                }
                experienceResult = body.getData().getData();
                List<ExperiencePhotoListResult> dataList = body.getData().getData().getPhotoList();
                paintPoint(dataList);
                photo_num.setText("1/" + dataList.size());
                if (experienceResult.getIsPanorama() == 1) {
                    View vr = findViewById(R.id.viewVr);
                    vr.setVisibility(View.VISIBLE);
                    vr.setOnClickListener(ExperienceDetailActivity.this);
                } else {
                    findViewById(R.id.viewVr).setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ExperienceDetailResult> call, Throwable t) {

            }
        });


    }

    private void initViews() {
        title_rela = (RelativeLayout) findViewById(R.id.title_rela);
        RelativeLayout.LayoutParams llp_title_rela = (RelativeLayout.LayoutParams) title_rela.getLayoutParams();
        llp_title_rela.height = CommonUtil.getRealWidth(130);

        title_back = (ImageView) findViewById(R.id.title_back);
        RelativeLayout.LayoutParams llp_title_back = (RelativeLayout.LayoutParams) title_back.getLayoutParams();
        llp_title_back.width = CommonUtil.getRealWidth(48);
        llp_title_back.height = llp_title_back.width;
        llp_title_back.leftMargin = CommonUtil.getRealWidth(30);
        llp_title_back.topMargin = CommonUtil.getRealWidth(60);

        title_text = (TextView) findViewById(R.id.title_text);
        title_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(36));
        RelativeLayout.LayoutParams llp_title_text = (RelativeLayout.LayoutParams) title_text.getLayoutParams();
        llp_title_text.topMargin = CommonUtil.getRealWidth(58);

        title_ach = (ImageView) findViewById(R.id.title_ach);
        RelativeLayout.LayoutParams llp_title_ach = (RelativeLayout.LayoutParams) title_ach.getLayoutParams();
        llp_title_ach.width = CommonUtil.getRealWidth(48);
        llp_title_ach.height = llp_title_ach.width;
        llp_title_ach.rightMargin = CommonUtil.getRealWidth(30);
        llp_title_ach.topMargin = CommonUtil.getRealWidth(60);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        experienceViewpagerAdapter = new ExperienceViewpagerAdapter(ExperienceDetailActivity.this, relaList);
        viewPager.setAdapter(experienceViewpagerAdapter);

        num_rela = (RelativeLayout) findViewById(R.id.num_rela);
        RelativeLayout.LayoutParams llp_num_rela = (RelativeLayout.LayoutParams) num_rela.getLayoutParams();
        llp_num_rela.width = CommonUtil.getRealWidth(144);
        llp_num_rela.height = CommonUtil.getRealWidth(66);
        llp_num_rela.topMargin = CommonUtil.getRealWidth(206);

        photo_num = (TextView) findViewById(R.id.photo_num);

        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        RelativeLayout.LayoutParams llp_recyclerview = (RelativeLayout.LayoutParams) recyclerview.getLayoutParams();
        llp_recyclerview.bottomMargin = CommonUtil.getRealWidth(36);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ExperienceDetailActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        experienceConnectAdapter = new ExperienceConnectAdapter(ExperienceDetailActivity.this);
        dataList = experienceConnectAdapter.getDatas();
        recyclerview.setAdapter(experienceConnectAdapter);
        experienceConnectAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void itemClickLIstener(View view, int position) {
                Intent intent = new Intent(ExperienceDetailActivity.this, ShopDetailActivity.class);
                intent.putExtra("id", dataList.get(position).getMerchandiseId());
                startActivity(intent);
            }
        });

        title_ach.setOnClickListener(this);
        title_back.setOnClickListener(this);

    }

    //描点
    private void paintPoint(final List<ExperiencePhotoListResult> dataList) {

        for (int i = 0; i < dataList.size(); i++) {
            final int numI = i;
            String piex = dataList.get(i).getPixels();
            String[] temp = piex.split(",");
            View layout = LayoutInflater.from(ExperienceDetailActivity.this).inflate(R.layout.item_experience_add_rela, null);
            final RelativeLayout add_view_rela = (RelativeLayout) layout.findViewById(R.id.add_view_rela);
            RelativeLayout.LayoutParams llp_add_view_rela = (RelativeLayout.LayoutParams) add_view_rela.getLayoutParams();
            llp_add_view_rela.width = CommonUtil.screenWidth;
            llp_add_view_rela.height = RelativeLayout.LayoutParams.WRAP_CONTENT;

            ImageView imageView = (ImageView) layout.findViewById(R.id.add_view_image);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(ExperienceDetailActivity.this).load(dataList.get(i).getPhotoUrl()).into(imageView);
            RelativeLayout.LayoutParams llp_imageView = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            llp_imageView.width = CommonUtil.screenWidth;

            double demo = Double.valueOf(temp[1]) * (Double.valueOf(CommonUtil.screenWidth) / Double.valueOf(temp[0]));
            llp_imageView.height = (int) Math.ceil(demo);

            if (dataList.get(i).getCoordinateList().size() != 0) {
                List<ExperiencePhotoXyResult> pointList = dataList.get(i).getCoordinateList();

                for (int k = 0; k < pointList.size(); k++) {
                    final int numK = k;
                    final ExperiencePhotoXyResult experiencePhotoXyResult = pointList.get(numK);
                    final double xShlf = experiencePhotoXyResult.getXshaft();
                    final double yShlf = experiencePhotoXyResult.getyShaft();

                    final View pointLayout = LayoutInflater.from(ExperienceDetailActivity.this).inflate(R.layout.item_experience_add_point, null);
                    final ImageView pointImage = (ImageView) pointLayout.findViewById(R.id.point_image);
                    pointImage.setImageResource(R.mipmap.exper_unselect);
                    final RelativeLayout.LayoutParams llp_pointImage = (RelativeLayout.LayoutParams) pointImage.getLayoutParams();
                    llp_pointImage.width = CommonUtil.getRealWidth(80);
                    llp_pointImage.height = llp_pointImage.width;

                    if (piex != null) {

                        double scale = Double.valueOf(CommonUtil.screenWidth) / Double.valueOf(temp[0]);
                        double lastWidth = xShlf * scale;
                        double lastHeight = yShlf * scale;
                        llp_pointImage.leftMargin = (int) Math.ceil(lastWidth * 0.9);
                        llp_pointImage.topMargin = (int) Math.ceil(lastHeight);

                    }


                    add_view_rela.addView(pointLayout);


                    pointImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Toast.makeText(ExperienceDetailActivity.this, numK + "", Toast.LENGTH_SHORT).show();
                            if (selectView != null) {
                                if (selectView == pointImage) {
                                    //点击是同一个，反复点击
                                    if (!selectFlag) {
                                        selectFlag = true;
                                        recyclerview.setVisibility(View.GONE);
                                        ((ImageView) selectView).setImageResource(R.mipmap.exper_unselect);
                                    } else {
                                        selectFlag = false;
                                        ((ImageView) selectView).setImageResource(R.mipmap.exper_select);
                                        achieveListData(experiencePhotoXyResult);
                                    }
                                } else {
                                    selectFlag = false;
                                    ((ImageView) selectView).setImageResource(R.mipmap.exper_unselect);
                                    ((ImageView) pointImage).setImageResource(R.mipmap.exper_select);
                                    achieveListData(experiencePhotoXyResult);
                                }
                            } else {
                                achieveListData(experiencePhotoXyResult);
                                ((ImageView) pointImage).setImageResource(R.mipmap.exper_select);
                            }
                            selectView = pointImage;

                        }
                    });


                }
            }

            relaList.add(layout);
        }

        experienceViewpagerAdapter.setData(relaList);


    }

    //获取关联商品的数据
    private void achieveListData(ExperiencePhotoXyResult experiencePhotoXyResult) {
        Call<ExperienceConnectResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).experienceConnect(experiencePhotoXyResult.getExperienceId() + "");
        addCall(call);
        call.enqueue(new Callback<ExperienceConnectResult>() {
            @Override
            public void onResponse(Call<ExperienceConnectResult> call, Response<ExperienceConnectResult> response) {
                ExperienceConnectResult body = response.body();
                if (body != null) {
                    dataList = body.getData();
                    if (dataList != null) {
                        recyclerview.setVisibility(View.VISIBLE);
                        experienceConnectAdapter.dataUpdate(dataList);
                    } else {
                        new CopyEaseDialog(ExperienceDetailActivity.this, "该物品已下架！", null, null, null, false).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ExperienceConnectResult> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_ach: {
                if (LoginUtils.isLogin(true, ExperienceDetailActivity.this)) {
                    if (!collectFlag) {
                        coolect();
                        collectFlag = true;
                    } else {
                        cancle();
                        collectFlag = false;
                    }
                }
            }
            break;

            case R.id.viewVr:
                Intent intent = new Intent(this, WebViewScriptActivity.class);
                intent.putExtra("url", experienceResult.getJumpUrl());
                startActivity(intent);
                break;

            case R.id.title_back: {
                finish();
            }
            break;

            default:
                break;

        }
    }

    private void coolect() {
        Call<ActivityBaomingResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).shopCoolect(LoginUtils.getToken(ExperienceDetailActivity.this), experienceResult.getId(), "experience");
        addCall(call);
        call.enqueue(new Callback<ActivityBaomingResult>() {
            @Override
            public void onResponse(Call<ActivityBaomingResult> call, Response<ActivityBaomingResult> response) {
                ActivityBaomingResult body = response.body();
                if (body.getRspCode() == 0) {
//                    go_button_ach_image
                    title_ach.setImageResource(R.mipmap.achieve_select);
                }
            }

            @Override
            public void onFailure(Call<ActivityBaomingResult> call, Throwable t) {
                Log.e("(((((((((((((((((", t.getMessage().toString());
            }
        });
    }

    private void cancle() {
        Call<ActivityBaomingResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).shopCancle(LoginUtils.getToken(ExperienceDetailActivity.this), experienceResult.getId(), "experience");
        addCall(call);
        call.enqueue(new Callback<ActivityBaomingResult>() {
            @Override
            public void onResponse(Call<ActivityBaomingResult> call, Response<ActivityBaomingResult> response) {
                String token = LoginUtils.getToken(ExperienceDetailActivity.this);
                ActivityBaomingResult body = response.body();
                if (body.getRspCode() == 0) {
//                    go_button_ach_image
                    title_ach.setImageResource(R.mipmap.exper);
                }
            }

            @Override
            public void onFailure(Call<ActivityBaomingResult> call, Throwable t) {
                Log.e("(((((((((((((((((", t.getMessage().toString());
            }
        });
    }
}

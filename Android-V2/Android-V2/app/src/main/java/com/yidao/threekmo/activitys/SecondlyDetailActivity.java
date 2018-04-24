package com.yidao.threekmo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ijustyce.fastkotlin.utils.CommonTools;
import com.yidao.myutils.log.LogUtils;
import com.yidao.myutils.toast.ToastUtil;
import com.yidao.threekmo.R;
import com.yidao.threekmo.bean.ActivityBaomingResult;
import com.yidao.threekmo.bean.MainHomeListItem;
import com.yidao.threekmo.bean.SubjectDetailResultBean;
import com.yidao.threekmo.customview.CopyEaseDialog;
import com.yidao.threekmo.customview.FlowLayout;
import com.yidao.threekmo.retrofit_server.SubjectServer;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.LoginUtils;
import com.yidao.threekmo.utils.RetrofitUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondlyDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final int UPTATE_VIEWPAGER = 0;
    //设置当前 第几个图片 被选中
    private int autoCurrIndex = 0;
    //Timer定时器
    private Timer timer = new Timer();

    private ImageView ivBack;
    private ImageView ivCollect;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TextView tvTitle;
    private ImageView ivPhone;
    private TextView tvAddress;
    private TextView tvMainSubjectName;
    private FlowLayout flTags;
    private TextView tvBusinessHours;
    private RatingBar rbScores;
    private TextView tvScore;
    private TextView tvWeChat;
    private TextView tvDesc;
    private RelativeLayout rlLoading;
    private long id;
    private MainHomeListItem data;
    private ArrayList<String> imageList;
    private MyPagerAdapter myPagerAdapter;
    private LinearLayout ascrption;
    private long subJectId;
    private LinearLayout pagerLinear;

    private boolean collectFlag = true;
    private int haveSecond = 1;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UPTATE_VIEWPAGER:
                    if(msg.arg1 != 0){
                        viewPager.setCurrentItem(msg.arg1);
                    }else{
                        viewPager.setCurrentItem(msg.arg1,false);
                    }
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondly_detail);

        viewInit();


        dataInit();
    }

    private void dataInit() {
        id = getIntent().getLongExtra("id", -1);
        subJectId = getIntent().getLongExtra("subJectId", -1);
        haveSecond = getIntent().getIntExtra("haveSecond",1);
        if (id != -1) {
            Call<SubjectDetailResultBean> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).getSubjectDetail(2, id, LoginUtils.getToken(SecondlyDetailActivity.this));
            addCall(call);
            call.enqueue(new Callback<SubjectDetailResultBean>() {
                @Override
                public void onResponse(Call<SubjectDetailResultBean> call, Response<SubjectDetailResultBean> response) {
                    SubjectDetailResultBean body = response.body();
                    if(body != null){
                        if (body.getRspCode() == 0) {
                            data = body.getData().getData();
                            subJectId = body.getData().getData().getSubjectId();
                            if (null != data) {
                                LogUtils.e("onResponse1");

                                if(data.getCoolect() == 0){
                                    collectFlag = false;
                                    ivCollect.setImageResource(R.mipmap.achieve);
                                }else{
                                    collectFlag = true;
                                    ivCollect.setImageResource(R.mipmap.achieve);
                                }

                                String secondaryImage = data.getSecondaryImage();
                                if (null != secondaryImage && !TextUtils.isEmpty(secondaryImage)) {
                                    String[] split = secondaryImage.split(",");
                                    imageList = new ArrayList<String>();
                                    for (int i = 0; i < split.length; i++) {
                                        imageList.add(split[i]);
                                    }
                                    List<ImageView> imageviewList = new ArrayList<ImageView>();
                                    for (int i = 0; i < imageList.size(); i++) {
                                        ImageView imageView = new ImageView(SecondlyDetailActivity.this);
                                        ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
                                        layoutParams.width = ViewPager.LayoutParams.MATCH_PARENT;
                                        layoutParams.height = ViewPager.LayoutParams.MATCH_PARENT;
                                        imageView.setLayoutParams(layoutParams);
                                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                        Glide.with(SecondlyDetailActivity.this).load(imageList.get(i)).into(imageView);
                                        imageviewList.add(imageView);
                                    }
                                    myPagerAdapter.addData(imageviewList);
                                    pagerChoose();

                                }

                                tvTitle.setText(data.getName());

                                String province = data.getProvince();
                                String city = data.getCity();
                                String area = data.getArea();
                                String address = data.getAddress();
                                if(TextUtils.isEmpty(province)){
                                    province = "";
                                }
                                if(TextUtils.isEmpty(city)){
                                    city = "";
                                }
                                if(TextUtils.isEmpty(area)){
                                    area = "";
                                }
                                if(TextUtils.isEmpty(address)){
                                    address = "";
                                }

                                tvAddress.setText(province + city + area + address);

                                tvMainSubjectName.setText(data.getSubjectName());

                                //标签初始化
                                tagInit(data.getLabelNames());
                                String startTime = "";
                                String endTime = "";
                                if (data.getBusinessStart() != null && data.getBusinessEnd() != null) {
                                    startTime = diyDate(data.getBusinessStart());
                                    endTime = diyDate(data.getBusinessEnd());
                                } else {
                                    startTime = "00:00";
                                    endTime = "00:00";
                                }
                                tvBusinessHours.setText(startTime + "至" + endTime);

                                double grade = data.getGrade();
                                tvScore.setText(grade + "分");
                                rbScores.setRating((float) grade);

                                tvWeChat.setText(data.getWebchatNum());

                                tvDesc.setText(data.getIndividualitySignature());

                            }
                            rlLoading.setVisibility(View.GONE);

                        } else {
                            Log.e("**********",body.getRspMsg().toString());
                            ToastUtil.showToast(body.getRspMsg(), false, SecondlyDetailActivity.this);
                        }
                    }else{
                        Toast.makeText(SecondlyDetailActivity.this,response.body().getRspMsg(),Toast.LENGTH_LONG).show();
                    }


                }

                @Override
                public void onFailure(Call<SubjectDetailResultBean> call, Throwable t) {
                    Log.e("**********",t.getMessage().toString());
                    Toast.makeText(SecondlyDetailActivity.this,t.getMessage().toString(),Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    private String diyDate(long ms) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String hms = formatter.format(ms * 1000);
        System.out.println(hms);
        return hms;
    }

    private String[] getTags(String str) {
        if (null != str && !TextUtils.isEmpty(str)) {
            String substring = str.substring(1, str.length() - 1);
            LogUtils.e("tage:" + substring);
            String[] tags = substring.split(",");
            return tags;
        } else {
            return new String[0];
        }
    }

    private void tagInit(String tags) {
        String[] strings = getTags(tags);

        for (int i = 0; i < strings.length; i++) {
            View tagView = LayoutInflater.from(SecondlyDetailActivity.this).inflate(R.layout.item_hot, flTags, false);
            TextView tvTag = (TextView) tagView.findViewById(R.id.tvTag);
            tvTag.setText(strings[i]);
            flTags.addView(tagView, flTags.getChildCount());
        }

        flTags.setmOnTagClickListener(new FlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {

                return false;
            }
        });
    }

    private void viewInit() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        ivCollect = (ImageView) findViewById(R.id.ivCollect);
        RelativeLayout.LayoutParams llp_ivCollect = (RelativeLayout.LayoutParams)ivCollect.getLayoutParams();
        llp_ivCollect.width = CommonUtil.getRealWidth(40);
        llp_ivCollect.height = llp_ivCollect.width;
        llp_ivCollect.rightMargin = CommonUtil.getRealWidth(30);

        ivCollect.setOnClickListener(this);

        viewPagerInit();

        tvTitle = (TextView) findViewById(R.id.tvTitle);

        ivPhone = (ImageView) findViewById(R.id.ivPhone);
        ivPhone.setOnClickListener(this);

        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvMainSubjectName = (TextView) findViewById(R.id.tvMainSubjectName);

        flTags = (FlowLayout) findViewById(R.id.flTags);

        tvBusinessHours = (TextView) findViewById(R.id.tvBusinessHours);

        rbScores = (RatingBar) findViewById(R.id.rbScores);
        tvScore = (TextView) findViewById(R.id.tvScore);

        tvWeChat = (TextView) findViewById(R.id.tvWeChat);

        tvDesc = (TextView) findViewById(R.id.tvDesc);

        rlLoading = (RelativeLayout) findViewById(R.id.rlLoading);

        ascrption = (LinearLayout) findViewById(R.id.ascrption);
        ascrption.setOnClickListener(this);

        pagerLinear = (LinearLayout) findViewById(R.id.pagerLinear);

    }

    private void viewPagerInit() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        myPagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(myPagerAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;

            case R.id.ivCollect:
                if(collectFlag){
                    cancle();
                    collectFlag = false;
                }else{
                    collect();
                    collectFlag = true;
                }
                break;

            case R.id.ivPhone: {
                if(data != null && !TextUtils.isEmpty(data.getPhone())){
                    CopyEaseDialog.AlertDialogUser alter = new CopyEaseDialog.AlertDialogUser() {
                        @Override
                        public void onResult(boolean confirmed, Bundle bundle) {
                            if (confirmed) {
                                CommonTools.INSTANCE.callPhone(SecondlyDetailActivity.this, data.getPhone());
                            }
                        }
                    };
                    new CopyEaseDialog(SecondlyDetailActivity.this, "是否需要拨打电话？", "电话："+data.getPhone(), null, alter, true).show();
                }else{
                    CopyEaseDialog.AlertDialogUser alter = new CopyEaseDialog.AlertDialogUser() {
                        @Override
                        public void onResult(boolean confirmed, Bundle bundle) {

                        }
                    };
                    new CopyEaseDialog(SecondlyDetailActivity.this, "未提供电话！", null, null, alter, false).show();
                }

            }
            break;

            case R.id.ascrption:

                Intent intent = new Intent(SecondlyDetailActivity.this, AscriptionSubActivity.class);
                intent.putExtra("subJect", subJectId);
                //有二级主体
                intent.putExtra("haveSecond",haveSecond);
                startActivityForResult(intent,101);

                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 101:{
                if(resultCode == 102){
                    finish();
                }
            }
                break;

            default:
                break;
        }
    }

    private void pagerChoose(){
        if (imageList != null && imageList.size() > 1) {
            for (int i = 0; i < imageList.size(); i++) {
                ImageView image = new ImageView(SecondlyDetailActivity.this);
                image.setBackgroundResource(R.drawable.point_up);
                LinearLayout.LayoutParams llp_image = new LinearLayout.LayoutParams(CommonUtil.getRealWidth(15), CommonUtil.getRealWidth(15));
                llp_image.leftMargin = CommonUtil.getRealWidth(15);
                llp_image.bottomMargin = CommonUtil.getRealWidth(10);
                image.setLayoutParams(llp_image);
                pagerLinear.addView(image);
            }
            pagerLinear.getChildAt(0).setBackgroundResource(R.drawable.point_down);
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(imageList.size() > 1){
                    for(int i =0; i < imageList.size(); i++){
                        if(i == position){
                            pagerLinear.getChildAt(i).setBackgroundResource(R.drawable.point_down);
                        }else{
                            pagerLinear.getChildAt(i).setBackgroundResource(R.drawable.point_up);
                        }
                    }
                }
                //设置全局变量，currentIndex为选中图标的 index
                autoCurrIndex = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 设置自动轮播图片，5s后执行，周期是5s
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = UPTATE_VIEWPAGER;
                if (autoCurrIndex == imageList.size() - 1) {
                    autoCurrIndex = -1;
                }
                message.arg1 = autoCurrIndex + 1;
                mHandler.sendMessage(message);
            }
        }, 3000, 3000);
    }

    private class MyPagerAdapter extends PagerAdapter {
        private List<ImageView> imageList;

        public MyPagerAdapter() {
            this.imageList = new ArrayList<ImageView>();
        }

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageList.get(position);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("imageList", (Serializable) SecondlyDetailActivity.this.imageList);
                    Intent intent = new Intent(SecondlyDetailActivity.this,ImageShowActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageList.get(position));
        }

        public void addData(List<ImageView> imgList) {
            imageList.addAll(imgList);
            notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        mHandler.removeMessages(UPTATE_VIEWPAGER);
    }

    private void cancle(){
        Call<ActivityBaomingResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).shopCancle(LoginUtils.getToken(SecondlyDetailActivity.this),id,"subject_train");
        addCall(call);
        call.enqueue(new Callback<ActivityBaomingResult>() {
            @Override
            public void onResponse(Call<ActivityBaomingResult> call, Response<ActivityBaomingResult> response) {
                ActivityBaomingResult body = response.body();
                if(body.getRspCode() == 0){
                    ivCollect.setImageResource(R.mipmap.achieve);
                    collectFlag = false;
                }
            }

            @Override
            public void onFailure(Call<ActivityBaomingResult> call, Throwable t) {

            }
        });
    }

    private void collect(){
        Call<ActivityBaomingResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).shopCoolect(LoginUtils.getToken(SecondlyDetailActivity.this),id,"subject_train");
        addCall(call);
        call.enqueue(new Callback<ActivityBaomingResult>() {
            @Override
            public void onResponse(Call<ActivityBaomingResult> call, Response<ActivityBaomingResult> response) {
                ActivityBaomingResult body = response.body();
                if(body.getRspCode() == 0){
                    ivCollect.setImageResource(R.mipmap.achieve_select);
                    collectFlag = true;
                }
            }

            @Override
            public void onFailure(Call<ActivityBaomingResult> call, Throwable t) {

            }
        });
    }
}

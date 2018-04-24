package com.yidao.threekmo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yidao.myutils.log.LogUtils;
import com.yidao.threekmo.R;
import com.yidao.threekmo.bean.MainHomeListItem;
import com.yidao.threekmo.bean.SubjectDetailResultBean;
import com.yidao.threekmo.customview.FlowLayout;
import com.yidao.threekmo.retrofit_server.SubjectServer;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.LoginUtils;
import com.yidao.threekmo.utils.RetrofitUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AscriptionSubActivity extends BaseActivity implements View.OnClickListener{

    private static final int UPTATE_VIEWPAGER = 0;
    //设置当前 第几个图片 被选中
    private int autoCurrIndex = 0;

    private ImageView ivBack;
    private ImageView ivCollect;
    private ViewPager viewPager;
    private TextView tvTitle;
    private TextView tvAddress;
    private FlowLayout flTags;
    private LinearLayout back_second_list;
    private Long subJectId;
    private MainHomeListItem mainHomeListItem;
    private MyPagerAdapter myPagerAdapter;
    private ArrayList<String> imageList;
    private LinearLayout pagerLinear;
    private Timer timer = new Timer();
    private int haveSecond = -1;

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
        setContentView(R.layout.activity_ascription_sub);

        if (getIntent() != null) {
            subJectId = getIntent().getLongExtra("subJect",0);
            haveSecond = getIntent().getIntExtra("haveSecond",-1);
        }

        initViews();
        getData();
        
    }

    private void getData() {

        Call<SubjectDetailResultBean> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).getSubjectDetail(1,subJectId, LoginUtils.getToken(AscriptionSubActivity.this));
        addCall(call);
        call.enqueue(new Callback<SubjectDetailResultBean>() {
            @Override
            public void onResponse(Call<SubjectDetailResultBean> call, Response<SubjectDetailResultBean> response) {
                SubjectDetailResultBean body = response.body();
                if(body.getRspCode() == 0){
                    mainHomeListItem = body.getData().getData();
                    if (null != mainHomeListItem) {
                        LogUtils.e("onResponse1");
                        String secondaryImage = mainHomeListItem.getSecondaryImage();
                        if (null != secondaryImage && !TextUtils.isEmpty(secondaryImage)) {
                            String[] split = secondaryImage.split(",");
                            imageList = new ArrayList<String>();
                            for (int i = 0; i < split.length; i++) {
                                imageList.add(split[i]);
                            }
                            List<ImageView> imageviewList = new ArrayList<ImageView>();
                            for (int i = 0; i < imageList.size(); i++) {
                                ImageView imageView = new ImageView(AscriptionSubActivity.this);
                                ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
                                layoutParams.width = ViewPager.LayoutParams.MATCH_PARENT;
                                layoutParams.height = ViewPager.LayoutParams.MATCH_PARENT;
                                imageView.setLayoutParams(layoutParams);
                                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                Glide.with(AscriptionSubActivity.this).load(imageList.get(i)).into(imageView);
                                imageviewList.add(imageView);
                            }
                            myPagerAdapter.addData(imageviewList);
                            pagerChoose();
                        }
                        tvTitle.setText(mainHomeListItem.getName());

                        String province = mainHomeListItem.getProvince();
                        String city = mainHomeListItem.getCity();
                        String area = mainHomeListItem.getArea();
                        String address = mainHomeListItem.getAddress();
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

                        tagInit(mainHomeListItem.getLabelNames());
                    }
                }
            }

            @Override
            public void onFailure(Call<SubjectDetailResultBean> call, Throwable t) {

            }
        });

    }

    //初始化控件
    private void initViews() {
        ivBack = (ImageView) findViewById(R.id.ivBack);

        ivCollect = (ImageView) findViewById(R.id.ivCollect);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        myPagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(myPagerAdapter);

        tvTitle = (TextView) findViewById(R.id.tvTitle);

        tvAddress = (TextView) findViewById(R.id.tvAddress);

        flTags = (FlowLayout) findViewById(R.id.flTags);

        pagerLinear = (LinearLayout) findViewById(R.id.pagerLinear);

        back_second_list = (LinearLayout) findViewById(R.id.back_second_list);

        back_second_list.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    private String[] getTags(String str){
        if(null!=str&& !TextUtils.isEmpty(str)){
            String substring = str.substring(1, str.length() - 1);
            LogUtils.e("tage:" + substring);
            String[] tags = substring.split(",");
            return tags;
        }else {
            return new String[0];
        }
    }

    private void tagInit(String tags) {
        String[] strings = getTags(tags);

        for (int i = 0; i < strings.length; i++) {
            View tagView = LayoutInflater.from(AscriptionSubActivity.this).inflate(R.layout.item_hot, flTags, false);
            TextView tvTag = (TextView) tagView.findViewById(R.id.tvTag);
            tvTag.setText(strings[i]);
            flTags.addView(tagView,flTags.getChildCount());
        }

        flTags.setmOnTagClickListener(new FlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {

                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivBack:
                finish();
                break;
            case R.id.back_second_list:
                if(haveSecond == 1){
                    setResult(102);
                    finish();
                }else{
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(AscriptionSubActivity.this,SecondaryListActivity.class);
                    bundle.putSerializable("mainHomeListItem",mainHomeListItem);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    private void pagerChoose(){
        if (imageList != null && imageList.size() > 1) {
            for (int i = 0; i < imageList.size(); i++) {
                ImageView image = new ImageView(AscriptionSubActivity.this);
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
                    //设置全局变量，currentIndex为选中图标的 index
                    autoCurrIndex = position;
                }

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
                    bundle.putSerializable("imageList", (Serializable) AscriptionSubActivity.this.imageList);
                    Intent intent = new Intent(AscriptionSubActivity.this,ImageShowActivity.class);
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
}

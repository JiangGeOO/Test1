package com.yidao.threekmo.activitys;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.yidao.threekmo.R;
import com.yidao.threekmo.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public class ImageShowActivity extends BaseActivity {

    private List<String> imageList;
    private ViewPager pager;
    private List<ImageView> imageviewList = new ArrayList<ImageView>();
    private MyAdpater adapter;
    private LinearLayout pagerLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_show);

        if(getIntent() != null){
            imageList = (List<String>) getIntent().getSerializableExtra("imageList");
            for (int i = 0; i < imageList.size(); i++) {
                ImageView imageView = new ImageView(ImageShowActivity.this);
                ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
                layoutParams.width = ViewPager.LayoutParams.WRAP_CONTENT;
                layoutParams.height = ViewPager.LayoutParams.WRAP_CONTENT;
                imageView.setLayoutParams(layoutParams);
                Glide.with(ImageShowActivity.this).load(imageList.get(i)).into(imageView);
                imageviewList.add(imageView);
            }
        }

        initViews();
    }

    private void initViews() {
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new MyAdpater();
        pager.setAdapter(adapter);
        pagerLinear = (LinearLayout) findViewById(R.id.pagerLinear);
        if (imageList != null && imageList.size() > 1) {
            for (int i = 0; i < imageList.size(); i++) {
                ImageView image = new ImageView(ImageShowActivity.this);
                image.setBackgroundResource(R.drawable.point_up);
                LinearLayout.LayoutParams llp_image = new LinearLayout.LayoutParams(CommonUtil.getRealWidth(15), CommonUtil.getRealWidth(15));
                llp_image.leftMargin = CommonUtil.getRealWidth(15);
                llp_image.bottomMargin = CommonUtil.getRealWidth(60);
                image.setLayoutParams(llp_image);
                pagerLinear.addView(image);
            }
            pagerLinear.getChildAt(0).setBackgroundResource(R.drawable.point_down);
        }

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private class MyAdpater extends PagerAdapter{

        private List<ImageView> list;

        public MyAdpater(){
            this.list = imageviewList;
        }

        @Override
        public int getCount() {
            return imageviewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageview = imageviewList.get(position);
            imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            container.addView(imageview);
            return imageview;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageviewList.get(position));
        }

        public void addData(List<ImageView> imgList) {
            imageviewList.addAll(imgList);
            notifyDataSetChanged();
        }
    }
}

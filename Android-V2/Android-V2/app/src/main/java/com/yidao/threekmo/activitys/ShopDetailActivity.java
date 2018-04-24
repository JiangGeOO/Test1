package com.yidao.threekmo.activitys;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ijustyce.fastkotlin.utils.RegularUtils;
import com.ijustyce.fastkotlin.utils.StringUtils;
import com.yidao.myutils.toast.ToastUtil;
import com.yidao.threekmo.R;
import com.yidao.threekmo.adapter.ShopHeaderViewPagerAdapter;
import com.yidao.threekmo.application.MyApplication;
import com.yidao.threekmo.bean.ActivityBaomingResult;
import com.yidao.threekmo.bean.ShopDetailResult;
import com.yidao.threekmo.bean.ShopInfoResult;
import com.yidao.threekmo.retrofit_server.SubjectServer;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.LoginUtils;
import com.yidao.threekmo.utils.RetrofitUtils;
import com.yidao.threekmo.v2.activity.ShopListActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopDetailActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout title_rela;
    private ImageView title_image;
    private TextView title_text;
    private ViewPager viewPager;
    private RelativeLayout shop_detail;
    private TextView shop_info;
    private TextView now_money;
    private TextView ago_money;
    private TextView month_scale;
    private TextView look_much;
    private RelativeLayout product_detail;
    private TextView product_detail_title;
    private View view_line;
    private TextView product_country;
    private TextView product_science;
    private TextView product_specification;
    private RelativeLayout go_button;
    private ImageView go_button_shop_image;
    private TextView go_button_shop_text;
    private ImageView go_button_ach_image;
    private TextView go_button_ach_text;
    private RelativeLayout go_button_pay;
    private TextView go_button_pay_text;
    private View view_pay;
    private LinearLayout viewpager_point;
    private WebView webView;
    private RelativeLayout html_rela;
    private TextView html_text;
    private View left_view;
    private View right_view;

    private long id;
    private boolean coolectFlag = true;

    private ArrayList<ImageView> listImage = new ArrayList<>();
    private ShopHeaderViewPagerAdapter shopHeaderViewPagerAdapter;
    private ShopDetailResult shopDetailResult;

    private static final int UPTATE_VIEWPAGER = 0;
    //设置当前 第几个图片 被选中
    private int autoCurrIndex = 0;
    //Timer定时器
    private Timer timer;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPTATE_VIEWPAGER:
                    if (msg.arg1 != 0) {
                        viewPager.setCurrentItem(msg.arg1);
                    } else {
                        viewPager.setCurrentItem(msg.arg1, false);
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
        setContentView(R.layout.activity_shop_detail);

        if (getIntent() != null) {
            id = getIntent().getLongExtra("id", 0L);
        }

        initViews();
        achieveData();
    }

    private void achieveData() {
        Call<ShopInfoResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).shopDetail(
                LoginUtils.getToken(ShopDetailActivity.this), id, MyApplication.getInstance().getLongitude() + "",
                MyApplication.getInstance().getLatitude() + "");
        addCall(call);
        call.enqueue(new Callback<ShopInfoResult>() {
            @Override
            public void onResponse(Call<ShopInfoResult> call, Response<ShopInfoResult> response) {
                ShopInfoResult body = response.body();
                if (body != null) {
                    if (body.getRspCode() == 0) {
                        shopDetailResult = body.getData().getData();
                        coolectFlag = shopDetailResult.getCoolect() == 0;
                        setData();
                    }
                }

            }

            @Override
            public void onFailure(Call<ShopInfoResult> call, Throwable t) {

            }
        });
    }

    private void setData() {
        if (!TextUtils.isEmpty(shopDetailResult.getParticulars())) {
            html_rela.setVisibility(View.VISIBLE);
            webView.loadDataWithBaseURL("about:blank", shopDetailResult.getParticulars(), "text/html", "utf-8", null);
        } else {
            html_rela.setVisibility(View.GONE);
        }

        shop_info.setText(shopDetailResult.getName());
        now_money.setText("￥" + shopDetailResult.getSellingPrice());
        ago_money.setText("原价：" + shopDetailResult.getOriginalPrice());
        month_scale.setText("月销量：" + shopDetailResult.getMonthlySales());
        look_much.setText("浏览量：" + shopDetailResult.getPageView());
        product_country.setText("产地：" + shopDetailResult.getProductionPlace());
        product_science.setText("材质：" + shopDetailResult.getTexture());
        product_specification.setText("规格：" + shopDetailResult.getSpecification());
        if (shopDetailResult.getCoolect() == 0) {
            go_button_ach_text.setText("收藏");
            go_button_ach_image.setImageResource(R.mipmap.shop_achieve);
        } else {
            go_button_ach_text.setText("已收藏");
            go_button_ach_image.setImageResource(R.mipmap.achieve_select);
        }

        String photo = shopDetailResult.getPhotoList();
        final String[] temp = photo.split(",");
        int len = temp.length;
        final ArrayList<String> pics = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            ImageView imageView = new ImageView(ShopDetailActivity.this);
            ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
            layoutParams.width = ViewPager.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewPager.LayoutParams.MATCH_PARENT;
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(ShopDetailActivity.this).load(temp[i]).into(imageView);
            listImage.add(imageView);

            pics.add(temp[i]);

            ImageView image = new ImageView(ShopDetailActivity.this);
            image.setBackgroundResource(R.drawable.point_up);
            LinearLayout.LayoutParams llp_image = new LinearLayout.LayoutParams(CommonUtil.getRealWidth(15), CommonUtil.getRealWidth(15));
            llp_image.leftMargin = CommonUtil.getRealWidth(15);
            llp_image.bottomMargin = CommonUtil.getRealWidth(10);
            image.setLayoutParams(llp_image);
            viewpager_point.addView(image);
        }

        if (len != 0) {
            viewpager_point.getChildAt(0).setBackgroundResource(R.drawable.point_down);
        }

        shopHeaderViewPagerAdapter = new ShopHeaderViewPagerAdapter(ShopDetailActivity.this, listImage);
        viewPager.setAdapter(shopHeaderViewPagerAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (temp.length > 1) {
                    for (int i = 0; i < temp.length; i++) {
                        if (i == position) {
                            viewpager_point.getChildAt(i).setBackgroundResource(R.drawable.point_down);
                        } else {
                            viewpager_point.getChildAt(i).setBackgroundResource(R.drawable.point_up);
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
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = UPTATE_VIEWPAGER;
                if (autoCurrIndex == listImage.size() - 1) {
                    autoCurrIndex = -1;
                }
                message.arg1 = autoCurrIndex + 1;
                mHandler.sendMessage(message);
            }
        }, 3000, 3000);

        final ArrayList<ShopDetailResult.PhotoItem> photoItems = shopDetailResult.photoLists;
        final int photoSize = photoItems.size();
        shopHeaderViewPagerAdapter.setOnPagerItemClickListener(new ShopHeaderViewPagerAdapter.OnPagerItemClickListener() {
            @Override
            public void onItem(int position) {
                if (position < 0 || position >= photoSize) return;
                ShopDetailResult.PhotoItem photoItem = photoItems.get(position);
                if (photoItem == null) return;
                if (photoItem.isPanorama == 1 && RegularUtils.INSTANCE.isUrl(photoItem.panoramaUrl)) {
                    Intent intent = new Intent(ShopDetailActivity.this, WebViewScriptActivity.class);
                    intent.putExtra("url", photoItem.panoramaUrl);
                    startActivity(intent);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("imageList", pics);
                    Intent intent = new Intent(ShopDetailActivity.this, ImageShowActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        if (shopDetailResult.experienceSum > 0) {
            RelativeLayout relativeLayout = findViewById(R.id.shopInfo);
            TextView shopNumInfo = findViewById(R.id.shopNumInfo);
            shopNumInfo.setText(String.format(getResources().getString(R.string.shop_info), shopDetailResult.experienceSum));
            relativeLayout.setVisibility(View.VISIBLE);
            relativeLayout.setOnClickListener(this);
        }
    }

    private void initViews() {
        title_rela = (RelativeLayout) findViewById(R.id.title_rela);
        RelativeLayout.LayoutParams llp_title_rela = (RelativeLayout.LayoutParams) title_rela.getLayoutParams();
        llp_title_rela.height = CommonUtil.getRealWidth(130);

        title_image = (ImageView) findViewById(R.id.title_image);
        RelativeLayout.LayoutParams llp_title_image = (RelativeLayout.LayoutParams) title_image.getLayoutParams();
        llp_title_image.width = CommonUtil.getRealWidth(48);
        llp_title_image.height = llp_title_image.width;
        llp_title_image.leftMargin = CommonUtil.getRealWidth(30);
        llp_title_image.topMargin = CommonUtil.getRealWidth(60);

        title_text = (TextView) findViewById(R.id.title_text);
        title_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(36));
        RelativeLayout.LayoutParams llp_title_text = (RelativeLayout.LayoutParams) title_text.getLayoutParams();
        llp_title_text.topMargin = CommonUtil.getRealWidth(58);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        RelativeLayout.LayoutParams llp_viewPager = (RelativeLayout.LayoutParams) viewPager.getLayoutParams();
        llp_viewPager.height = CommonUtil.getRealWidth(628);

        shop_detail = (RelativeLayout) findViewById(R.id.shop_detail);
        shop_detail.setMinimumHeight(CommonUtil.getRealWidth(212));

        shop_info = (TextView) findViewById(R.id.shop_info);
        shop_info.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_shop_info = (RelativeLayout.LayoutParams) shop_info.getLayoutParams();
        llp_shop_info.leftMargin = CommonUtil.getRealWidth(30);
        llp_shop_info.topMargin = CommonUtil.getRealWidth(12);

        now_money = (TextView) findViewById(R.id.now_money);
        now_money.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(40));
        RelativeLayout.LayoutParams llp_now_money = (RelativeLayout.LayoutParams) now_money.getLayoutParams();
        llp_now_money.topMargin = CommonUtil.getRealWidth(4);
        llp_now_money.leftMargin = CommonUtil.getRealWidth(30);

        ago_money = (TextView) findViewById(R.id.ago_money);
        ago_money.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        ago_money.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        RelativeLayout.LayoutParams llp_ago_money = (RelativeLayout.LayoutParams) ago_money.getLayoutParams();
        llp_ago_money.topMargin = CommonUtil.getRealWidth(4);
        llp_ago_money.leftMargin = CommonUtil.getRealWidth(30);

        month_scale = (TextView) findViewById(R.id.month_scale);
        month_scale.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(24));
        RelativeLayout.LayoutParams llp_month_scale = (RelativeLayout.LayoutParams) month_scale.getLayoutParams();
        llp_month_scale.topMargin = CommonUtil.getRealWidth(6);
        llp_month_scale.leftMargin = CommonUtil.getRealWidth(30);
        llp_month_scale.bottomMargin = CommonUtil.getRealWidth(10);

        look_much = (TextView) findViewById(R.id.look_much);
        look_much.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(24));
        RelativeLayout.LayoutParams llp_look_much = (RelativeLayout.LayoutParams) look_much.getLayoutParams();
        llp_look_much.topMargin = CommonUtil.getRealWidth(8);
        llp_look_much.leftMargin = CommonUtil.getRealWidth(228);

        product_detail = (RelativeLayout) findViewById(R.id.product_detail);
        RelativeLayout.LayoutParams llp_product_detail = (RelativeLayout.LayoutParams) product_detail.getLayoutParams();
        llp_product_detail.height = CommonUtil.getRealWidth(284);

        product_detail_title = (TextView) findViewById(R.id.product_detail_title);
        product_detail_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_product_detail_title = (RelativeLayout.LayoutParams) product_detail_title.getLayoutParams();
        llp_product_detail_title.leftMargin = CommonUtil.getRealWidth(30);
        llp_product_detail_title.topMargin = CommonUtil.getRealWidth(20);

        view_line = findViewById(R.id.view_line);
        RelativeLayout.LayoutParams llp_view_line = (RelativeLayout.LayoutParams) view_line.getLayoutParams();
        llp_view_line.topMargin = CommonUtil.getRealWidth(84);

        product_country = (TextView) findViewById(R.id.product_country);
        product_country.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_product_country = (RelativeLayout.LayoutParams) product_country.getLayoutParams();
        llp_product_country.topMargin = CommonUtil.getRealWidth(106);
        llp_product_country.leftMargin = CommonUtil.getRealWidth(30);

        product_science = (TextView) findViewById(R.id.product_science);
        product_science.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_product_science = (RelativeLayout.LayoutParams) product_science.getLayoutParams();
        llp_product_science.topMargin = CommonUtil.getRealWidth(166);
        llp_product_science.leftMargin = CommonUtil.getRealWidth(30);

        product_specification = (TextView) findViewById(R.id.product_specification);
        product_specification.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_product_specification = (RelativeLayout.LayoutParams) product_specification.getLayoutParams();
        llp_product_specification.topMargin = CommonUtil.getRealWidth(226);
        llp_product_specification.leftMargin = CommonUtil.getRealWidth(30);
        llp_product_specification.bottomMargin = CommonUtil.getRealWidth(18);

        go_button = (RelativeLayout) findViewById(R.id.go_button);
        RelativeLayout.LayoutParams llp_go_button = (RelativeLayout.LayoutParams) go_button.getLayoutParams();
        llp_go_button.height = CommonUtil.getRealWidth(98);

        go_button_shop_image = (ImageView) findViewById(R.id.go_button_shop_image);
        RelativeLayout.LayoutParams llp_go_button_shop_image = (RelativeLayout.LayoutParams) go_button_shop_image.getLayoutParams();
        llp_go_button_shop_image.width = CommonUtil.getRealWidth(44);
        llp_go_button_shop_image.height = llp_go_button_shop_image.width;
        llp_go_button_shop_image.leftMargin = CommonUtil.getRealWidth(30);

        go_button_shop_text = (TextView) findViewById(R.id.go_button_shop_text);
        go_button_shop_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_go_button_shop_text = (RelativeLayout.LayoutParams) go_button_shop_text.getLayoutParams();
        llp_go_button_shop_text.leftMargin = CommonUtil.getRealWidth(90);

        go_button_ach_image = (ImageView) findViewById(R.id.go_button_ach_image);
        RelativeLayout.LayoutParams llp_go_button_ach_image = (RelativeLayout.LayoutParams) go_button_ach_image.getLayoutParams();
        llp_go_button_ach_image.width = CommonUtil.getRealWidth(40);
        llp_go_button_ach_image.height = llp_go_button_ach_image.width;
        llp_go_button_ach_image.leftMargin = CommonUtil.getRealWidth(302);

        go_button_ach_text = (TextView) findViewById(R.id.go_button_ach_text);
        go_button_ach_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_go_button_ach_text = (RelativeLayout.LayoutParams) go_button_ach_text.getLayoutParams();
        llp_go_button_ach_text.leftMargin = CommonUtil.getRealWidth(358);

        go_button_pay = (RelativeLayout) findViewById(R.id.go_button_pay);
        RelativeLayout.LayoutParams llp_go_button_pay = (RelativeLayout.LayoutParams) go_button_pay.getLayoutParams();
        llp_go_button_pay.width = CommonUtil.getRealWidth(244);

        go_button_pay_text = (TextView) findViewById(R.id.go_button_pay_text);
        go_button_pay_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));

        view_pay = findViewById(R.id.view_pay);
        RelativeLayout.LayoutParams llp_view_pay = (RelativeLayout.LayoutParams) view_pay.getLayoutParams();
        llp_view_pay.height = CommonUtil.getRealWidth(44);
        llp_view_pay.leftMargin = CommonUtil.getRealWidth(228);

        viewpager_point = (LinearLayout) findViewById(R.id.viewpager_point);

        html_rela = (RelativeLayout) findViewById(R.id.html_rela);
        RelativeLayout.LayoutParams llp_html_rela = (RelativeLayout.LayoutParams) html_rela.getLayoutParams();
        llp_html_rela.height = CommonUtil.getRealWidth(86);
        llp_html_rela.topMargin = CommonUtil.getRealWidth(20);

        html_text = (TextView) findViewById(R.id.html_text);
        html_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));

        left_view = findViewById(R.id.left_view);
        RelativeLayout.LayoutParams llp_left_view = (RelativeLayout.LayoutParams) left_view.getLayoutParams();
        llp_left_view.height = CommonUtil.getRealWidth(2);
        llp_left_view.width = CommonUtil.getRealWidth(148);
        llp_left_view.leftMargin = CommonUtil.getRealWidth(156);

        right_view = findViewById(R.id.right_view);
        RelativeLayout.LayoutParams llp_right_view = (RelativeLayout.LayoutParams) right_view.getLayoutParams();
        llp_right_view.height = CommonUtil.getRealWidth(2);
        llp_right_view.width = CommonUtil.getRealWidth(148);
        llp_right_view.rightMargin = CommonUtil.getRealWidth(156);


        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        go_button_pay.setOnClickListener(this);
        go_button_ach_image.setOnClickListener(this);
        go_button_ach_text.setOnClickListener(this);
        go_button_shop_image.setOnClickListener(this);
        go_button_shop_text.setOnClickListener(this);
        title_image.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_button_pay: {
                if (LoginUtils.isLogin(true, ShopDetailActivity.this)) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(ShopDetailActivity.this, OrderRelaActivity.class);
                    bundle.putLong("id", id);
                    bundle.putSerializable("shopDetailResult", shopDetailResult);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 102);
                }
            }
            break;
            case R.id.go_button_ach_image: {
                if (LoginUtils.isLogin(true, ShopDetailActivity.this)) {
                    if (coolectFlag) {
                        coolect();
                        coolectFlag = false;
                    } else {
                        cancle();
                        coolectFlag = true;
                    }
                }
            }
            break;

            case R.id.shopInfo:
                Intent shopList = new Intent(ShopDetailActivity.this, ShopListActivity.class);
                shopList.putExtra("ids", shopDetailResult.experienceIds);
                startActivity(shopList);
                break;

            case R.id.go_button_ach_text: {
                if (LoginUtils.isLogin(true, ShopDetailActivity.this)) {
                    if (coolectFlag) {
                        coolect();
                        coolectFlag = false;
                    } else {
                        cancle();
                        coolectFlag = true;
                    }
                }
            }
            break;

            case R.id.go_button_shop_image: {
                Intent intent = new Intent(ShopDetailActivity.this, SecondlyDetailActivity.class);
                intent.putExtra("id", shopDetailResult.getSubjectTrainId());
                startActivity(intent);
            }
            break;

            case R.id.go_button_shop_text: {
                Intent intent = new Intent(ShopDetailActivity.this, SecondlyDetailActivity.class);
                intent.putExtra("id", shopDetailResult.getSubjectTrainId());
                startActivity(intent);
            }
            break;

            case R.id.title_image: {
                finish();
            }
            break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("______________", resultCode + "");
        if (requestCode == 102) {
            finish();
        }
    }

    private void coolect() {
        Call<ActivityBaomingResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).shopCoolect(LoginUtils.getToken(ShopDetailActivity.this), id, "merchandise");
        addCall(call);
        call.enqueue(new Callback<ActivityBaomingResult>() {
            @Override
            public void onResponse(Call<ActivityBaomingResult> call, Response<ActivityBaomingResult> response) {
                ActivityBaomingResult body = response.body();
                if (body != null) {
                    if (body.getRspCode() == 0) {
//                    go_button_ach_image
                        go_button_ach_text.setText("已收藏");
                        go_button_ach_image.setImageResource(R.mipmap.achieve_select);
                    }
                }

            }

            @Override
            public void onFailure(Call<ActivityBaomingResult> call, Throwable t) {

            }
        });
    }

    private void cancle() {
        Call<ActivityBaomingResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).shopCancle(LoginUtils.getToken(ShopDetailActivity.this), id, "merchandise");
        addCall(call);
        call.enqueue(new Callback<ActivityBaomingResult>() {
            @Override
            public void onResponse(Call<ActivityBaomingResult> call, Response<ActivityBaomingResult> response) {
                ActivityBaomingResult body = response.body();
                if (body != null) {
                    if (body.getRspCode() == 0) {
//                    go_button_ach_image
                        go_button_ach_text.setText("收藏");
                        go_button_ach_image.setImageResource(R.mipmap.shop_achieve);
                    }
                }

            }

            @Override
            public void onFailure(Call<ActivityBaomingResult> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

    }
}

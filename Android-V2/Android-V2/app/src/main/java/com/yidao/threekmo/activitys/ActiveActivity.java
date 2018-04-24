package com.yidao.threekmo.activitys;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.yidao.threekmo.R;
import com.yidao.threekmo.application.MyApplication;
import com.yidao.threekmo.bean.ActivityDetailsResult;
import com.yidao.threekmo.bean.MainHomeListItem;
import com.yidao.threekmo.parameter.WebParameters;
import com.yidao.threekmo.retrofit_server.SubjectServer;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.LoginUtils;
import com.yidao.threekmo.utils.RetrofitUtils;
import com.yidao.threekmo.utils.ShareUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActiveActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout active_rela;
    private ImageView active_back;
    private TextView active_text;
    private ImageView active_image;
    private RelativeLayout active_info_rela;
    private ImageView active_info_image;
    private RelativeLayout active_info_all_rela;
    private TextView active_info_all_text;
    private LinearLayout active_info_all_linear;
    private TextView active_info_all_human;
    private TextView active_info_all_look;
    private LinearLayout active_details_linear;
    private RelativeLayout active_details_linear_company;
    private ImageView active_details_linear_company_image;
    private TextView active_details_linear_company_text;
    private RelativeLayout active_details_linear_time;
    private ImageView active_details_linear_time_image;
    private TextView active_details_linear_time_text;
    private RelativeLayout active_details_linear_address;
    private ImageView active_details_linear_address_image;
    private TextView active_details_linear_address_text;
    private TextView active_details_linear_address_dis;
    private ImageView active_details_linear_address_right;
    private RelativeLayout baoming_rela;
    private ImageView baoming_image;
    private TextView baoming_text;
    private RelativeLayout webview_rela;
    private View left_view;
    private TextView webview_text;
    private View right_view;
    private WebView webview;
    private RelativeLayout view_rela;

    private long id;
    private List<String> lables;
    private MainHomeListItem mainHomeListItem;
    private PopupWindow popWindow;

    private boolean disFlag = true;
    private String mHtmlText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active);

        if (getIntent() != null) {
            id = getIntent().getLongExtra("id", 0);
        }

        initViews();
        getInfo();
    }

    private void setData(final MainHomeListItem mainHomeListItem) {

        if (!TextUtils.isEmpty(mainHomeListItem.getPhoto())) {
            Glide.with(ActiveActivity.this).load(mainHomeListItem.getPhoto()).into(active_info_image);
        } else {
            Glide.with(ActiveActivity.this).load(R.mipmap.wangxingback).into(active_info_image);
        }
        active_info_all_text.setText(mainHomeListItem.getName());
        active_info_all_human.setText("报名人数：" + mainHomeListItem.getParticipants());
        active_details_linear_company_text.setText(mainHomeListItem.getSponsor());

//        if ("2".equals(mainHomeListItem.getParticipants())) {
//            baoming_rela.setVisibility(View.GONE);
//        }

        String startDate = CommonUtil.getDateToString(mainHomeListItem.getActivityStart(), "yyyy-MM-dd");
        String endDate = CommonUtil.getDateToString(mainHomeListItem.getActivityEnd(), "yyyy-MM-dd");
        String startTime = CommonUtil.getDateToString(mainHomeListItem.getActivityStart(), "HH:mm");
        String endTime = CommonUtil.getDateToString(mainHomeListItem.getActivityEnd(), "HH:mm");
        active_details_linear_time_text.setText(startDate + " 至 " + endDate + "   " + startTime + "至" + endTime);
        active_details_linear_address_text.setText(mainHomeListItem.getAddress());
        Long distance = mainHomeListItem.getDistance();
        if (distance != null) {
            float dis = distance / 1000f;
            if (distance > 1000) {
                active_details_linear_address_dis.setText(String.format("%.1f", dis) + "km");
            } else {
                active_details_linear_address_dis.setText(distance + "m");
            }
        } else {
            active_details_linear_address_dis.setText("距离未知");
        }

        String lable = mainHomeListItem.getLabelNames();
        String[] temp = null;
        if (!TextUtils.isEmpty(lable)) {
            temp = lable.split(",");
            lables = new ArrayList<String>();
            for (int i = 0; i < temp.length; i++) {
                if (!TextUtils.isEmpty(temp[i])) {
                    RelativeLayout rela = new RelativeLayout(this);
                    rela.setPadding(CommonUtil.getRealWidth(10), CommonUtil.getRealWidth(5), CommonUtil.getRealWidth(10), CommonUtil.getRealWidth(5));
                    rela.setGravity(Gravity.CENTER);
                    rela.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_lable_green));
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, CommonUtil.getRealWidth(46));
                    layoutParams.rightMargin = CommonUtil.getRealWidth(20);
                    rela.setLayoutParams(layoutParams);
                    TextView textView = new TextView(this);
                    textView.setText(temp[i]);
                    textView.setTextColor(getResources().getColor(R.color.white));
                    rela.addView(textView);
                    active_info_all_linear.addView(rela);

                }
            }
        }

        webview.getSettings().setJavaScriptEnabled(true);
        webview.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj");
        webview.loadDataWithBaseURL("about:blank", mainHomeListItem.getParticulars(), "text/html", "utf-8", null);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.contains("tel:")) {
                    String[] tel = url.split(":");
                    if (CommonUtil.isMobileNum(tel[1])) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(url);
                    intent.setData(content_url);
                    startActivity(intent);
                }
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // 在结束加载网页时会回调

                // 获取页面内容
                view.loadUrl("javascript:window.java_obj.showSource("
                        + "document.getElementsByTagName('html')[0].innerHTML);");

                // 获取解析<meta name="share-description" content="获取到的值">
                view.loadUrl("javascript:window.java_obj.showDescription("
                        + "document.querySelector('meta[name=\"share-description\"]').getAttribute('content')"
                        + ");");
                super.onPageFinished(view, url);

            }
        });
    }

    public final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource(String html) {
            System.out.println("====>html=" + html);
            //取出HTML中P标签的文本内容,利用正则表达式匹配.
            Pattern pattern = Pattern.compile("[1][3578]\\d{9}");
            Matcher matcher = pattern.matcher(html);
            StringBuffer sb = new StringBuffer();
            while (matcher.find()) {
                sb.append(matcher.group());
                sb.append(",");
            }
            mHtmlText = sb.toString();
//            Toast.makeText(ActiveActivity.this,sb.toString(), Toast.LENGTH_SHORT).show();

        }

        @JavascriptInterface
        public void showDescription(String str) {
            System.out.println("====>html=" + str);
        }
    }


    private void getInfo() {
        SubjectServer subjectServer = RetrofitUtils.getRetrofit().create(SubjectServer.class);
        Call<ActivityDetailsResult> call = subjectServer.getActivityDetails(id, MyApplication.getInstance().getLongitude() + "", MyApplication.getInstance().getLatitude() + "");
        addCall(call);
        call.enqueue(new Callback<ActivityDetailsResult>() {
            @Override
            public void onResponse(Call<ActivityDetailsResult> call, Response<ActivityDetailsResult> response) {
                ActivityDetailsResult body = response == null ? null : response.body();
                if (body == null) return;
                if (body.getRspCode() == 0) {
                    mainHomeListItem = (MainHomeListItem) body.getData();
                    setData(mainHomeListItem);
                }
            }

            @Override
            public void onFailure(Call<ActivityDetailsResult> call, Throwable t) {

            }
        });
    }

    private void initViews() {
        active_rela = (RelativeLayout) findViewById(R.id.active_rela);
        RelativeLayout.LayoutParams llp_active_rela = (RelativeLayout.LayoutParams) active_rela.getLayoutParams();
        llp_active_rela.height = CommonUtil.getRealWidth(128);

        active_back = (ImageView) findViewById(R.id.active_back);
        RelativeLayout.LayoutParams llp_active_back = (RelativeLayout.LayoutParams) active_back.getLayoutParams();
        llp_active_back.width = CommonUtil.getRealWidth(52);
        llp_active_back.height = CommonUtil.getRealWidth(52);
        llp_active_back.leftMargin = CommonUtil.getRealWidth(30);
        llp_active_back.topMargin = CommonUtil.getRealWidth(62);

        active_text = (TextView) findViewById(R.id.active_text);
        active_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(36));
        RelativeLayout.LayoutParams llp_active_text = (RelativeLayout.LayoutParams) active_text.getLayoutParams();
        llp_active_text.topMargin = CommonUtil.getRealWidth(63);

        active_image = (ImageView) findViewById(R.id.active_image);
        RelativeLayout.LayoutParams llp_active_image = (RelativeLayout.LayoutParams) active_image.getLayoutParams();
        llp_active_image.width = CommonUtil.getRealWidth(48);
        llp_active_image.height = llp_active_image.width;
        llp_active_image.rightMargin = CommonUtil.getRealWidth(30);
        llp_active_image.topMargin = CommonUtil.getRealWidth(62);

        active_info_rela = (RelativeLayout) findViewById(R.id.active_info_rela);
        active_info_rela.setMinimumHeight(CommonUtil.getRealWidth(548));
        LinearLayout.LayoutParams llp_active_info_rela = (LinearLayout.LayoutParams) active_info_rela.getLayoutParams();
//        llp_active_info_rela.height = CommonUtil.getRealWidth(548);

        active_info_image = (ImageView) findViewById(R.id.active_info_image);
        RelativeLayout.LayoutParams llp_active_info_image = (RelativeLayout.LayoutParams) active_info_image.getLayoutParams();
        llp_active_info_image.height = CommonUtil.getRealWidth(328);

        active_info_all_rela = (RelativeLayout) findViewById(R.id.active_info_all_rela);
        active_info_all_rela.setMinimumHeight(CommonUtil.getRealWidth(220));
        RelativeLayout.LayoutParams llp_active_info_all_rela = (RelativeLayout.LayoutParams) active_info_all_rela.getLayoutParams();
//        llp_active_info_all_rela.height = CommonUtil.getRealWidth(220);

        active_info_all_text = (TextView) findViewById(R.id.active_info_all_text);
        active_info_all_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_active_info_all_text = (RelativeLayout.LayoutParams) active_info_all_text.getLayoutParams();
        llp_active_info_all_text.leftMargin = CommonUtil.getRealWidth(30);
        llp_active_info_all_text.topMargin = CommonUtil.getRealWidth(20);

        active_info_all_linear = (LinearLayout) findViewById(R.id.active_info_all_linear);
        RelativeLayout.LayoutParams llp_active_info_all_linear = (RelativeLayout.LayoutParams) active_info_all_linear.getLayoutParams();
        llp_active_info_all_linear.topMargin = CommonUtil.getRealWidth(16);
        llp_active_info_all_linear.leftMargin = CommonUtil.getRealWidth(30);

        active_info_all_human = (TextView) findViewById(R.id.active_info_all_human);
        active_info_all_human.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(24));
        RelativeLayout.LayoutParams llp_active_info_all_human = (RelativeLayout.LayoutParams) active_info_all_human.getLayoutParams();
        llp_active_info_all_human.topMargin = CommonUtil.getRealWidth(30);
        llp_active_info_all_human.leftMargin = CommonUtil.getRealWidth(30);
        llp_active_info_all_human.bottomMargin = CommonUtil.getRealWidth(10);

        active_info_all_look = (TextView) findViewById(R.id.active_info_all_look);
        active_info_all_look.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(24));
        RelativeLayout.LayoutParams llp_active_info_all_look = (RelativeLayout.LayoutParams) active_info_all_look.getLayoutParams();
        llp_active_info_all_look.topMargin = CommonUtil.getRealWidth(40);
        llp_active_info_all_look.rightMargin = CommonUtil.getRealWidth(32);

        active_details_linear = (LinearLayout) findViewById(R.id.active_details_linear);
        LinearLayout.LayoutParams llp_active_details_linear = (LinearLayout.LayoutParams) active_details_linear.getLayoutParams();
        llp_active_details_linear.height = CommonUtil.getRealWidth(252);
        llp_active_details_linear.topMargin = CommonUtil.getRealWidth(20);

        active_details_linear_company = (RelativeLayout) findViewById(R.id.active_details_linear_company);
        LinearLayout.LayoutParams llp_active_details_linear_company = (LinearLayout.LayoutParams) active_details_linear_company.getLayoutParams();
        llp_active_details_linear_company.height = CommonUtil.getRealWidth(84);

        active_details_linear_company_image = (ImageView) findViewById(R.id.active_details_linear_company_image);
        RelativeLayout.LayoutParams llp_active_details_linear_company_image = (RelativeLayout.LayoutParams) active_details_linear_company_image.getLayoutParams();
        llp_active_details_linear_company_image.width = CommonUtil.getRealWidth(36);
        llp_active_details_linear_company_image.height = llp_active_details_linear_company_image.width;
        llp_active_details_linear_company_image.leftMargin = CommonUtil.getRealWidth(34);

        active_details_linear_company_text = (TextView) findViewById(R.id.active_details_linear_company_text);
        active_details_linear_company_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(24));
        RelativeLayout.LayoutParams llp_active_details_linear_company_text = (RelativeLayout.LayoutParams) active_details_linear_company_text.getLayoutParams();
        llp_active_details_linear_company_text.leftMargin = CommonUtil.getRealWidth(86);

        active_details_linear_time = (RelativeLayout) findViewById(R.id.active_details_linear_time);
        LinearLayout.LayoutParams llp_active_details_linear_time = (LinearLayout.LayoutParams) active_details_linear_time.getLayoutParams();
        llp_active_details_linear_time.height = CommonUtil.getRealWidth(84);

        active_details_linear_time_image = (ImageView) findViewById(R.id.active_details_linear_time_image);
        RelativeLayout.LayoutParams llp_active_details_linear_time_image = (RelativeLayout.LayoutParams) active_details_linear_time_image.getLayoutParams();
        llp_active_details_linear_time_image.width = CommonUtil.getRealWidth(44);
        llp_active_details_linear_time_image.height = llp_active_details_linear_time_image.width;
        llp_active_details_linear_time_image.leftMargin = CommonUtil.getRealWidth(30);

        active_details_linear_time_text = (TextView) findViewById(R.id.active_details_linear_time_text);
        active_details_linear_time_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(24));
        RelativeLayout.LayoutParams llp_active_details_linear_time_text = (RelativeLayout.LayoutParams) active_details_linear_time_text.getLayoutParams();
        llp_active_details_linear_time_text.leftMargin = CommonUtil.getRealWidth(90);

        active_details_linear_address = (RelativeLayout) findViewById(R.id.active_details_linear_address);
        LinearLayout.LayoutParams llp_active_details_linear_address = (LinearLayout.LayoutParams) active_details_linear_address.getLayoutParams();
        llp_active_details_linear_address.height = CommonUtil.getRealWidth(84);

        active_details_linear_address_image = (ImageView) findViewById(R.id.active_details_linear_address_image);
        RelativeLayout.LayoutParams llp_active_details_linear_address_image = (RelativeLayout.LayoutParams) active_details_linear_address_image.getLayoutParams();
        llp_active_details_linear_address_image.width = CommonUtil.getRealWidth(44);
        llp_active_details_linear_address_image.height = llp_active_details_linear_address_image.width;
        llp_active_details_linear_address_image.leftMargin = CommonUtil.getRealWidth(30);

        active_details_linear_address_text = (TextView) findViewById(R.id.active_details_linear_address_text);
        active_details_linear_address_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(24));
        RelativeLayout.LayoutParams llp_active_details_linear_address_text = (RelativeLayout.LayoutParams) active_details_linear_address_text.getLayoutParams();
        llp_active_details_linear_address_text.leftMargin = CommonUtil.getRealWidth(90);
        llp_active_details_linear_address_text.rightMargin = CommonUtil.getRealWidth(150);

        active_details_linear_address_dis = (TextView) findViewById(R.id.active_details_linear_address_dis);
        active_details_linear_address_dis.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(24));
        RelativeLayout.LayoutParams llp_active_details_linear_address_dis = (RelativeLayout.LayoutParams) active_details_linear_address_dis.getLayoutParams();
        llp_active_details_linear_address_dis.rightMargin = CommonUtil.getRealWidth(78);

        active_details_linear_address_right = (ImageView) findViewById(R.id.active_details_linear_address_right);
        RelativeLayout.LayoutParams llp_active_details_linear_address_right = (RelativeLayout.LayoutParams) active_details_linear_address_right.getLayoutParams();
        llp_active_details_linear_address_right.width = CommonUtil.getRealWidth(36);
        llp_active_details_linear_address_right.height = llp_active_details_linear_address_right.width;
        llp_active_details_linear_address_right.rightMargin = CommonUtil.getRealWidth(30);

        baoming_rela = (RelativeLayout) findViewById(R.id.baoming_rela);
        RelativeLayout.LayoutParams llp_baoming_rela = (RelativeLayout.LayoutParams) baoming_rela.getLayoutParams();
        llp_baoming_rela.bottomMargin = CommonUtil.getRealWidth(100);
        llp_baoming_rela.rightMargin = CommonUtil.getRealWidth(30);

        baoming_image = (ImageView) findViewById(R.id.baoming_image);
        RelativeLayout.LayoutParams llp_baoming_image = (RelativeLayout.LayoutParams) baoming_image.getLayoutParams();
        llp_baoming_image.width = CommonUtil.getRealWidth(100);
        llp_baoming_image.height = llp_baoming_image.width;

        baoming_text = (TextView) findViewById(R.id.baoming_text);
        baoming_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));

        webview_rela = (RelativeLayout) findViewById(R.id.webview_rela);
        LinearLayout.LayoutParams llp_webview_rela = (LinearLayout.LayoutParams) webview_rela.getLayoutParams();
        llp_webview_rela.topMargin = CommonUtil.getRealWidth(20);

        view_rela = (RelativeLayout) findViewById(R.id.view_rela);
        RelativeLayout.LayoutParams llp_view_rela = (RelativeLayout.LayoutParams) view_rela.getLayoutParams();
        llp_view_rela.height = CommonUtil.getRealWidth(88);

        left_view = findViewById(R.id.left_view);
        RelativeLayout.LayoutParams llp_left_view = (RelativeLayout.LayoutParams) left_view.getLayoutParams();
        llp_left_view.width = CommonUtil.getRealWidth(148);
        llp_left_view.leftMargin = CommonUtil.getRealWidth(156);

        webview_text = (TextView) findViewById(R.id.webview_text);
        webview_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_webview_text = (RelativeLayout.LayoutParams) webview_text.getLayoutParams();

        right_view = findViewById(R.id.right_view);
        RelativeLayout.LayoutParams llp_right_view = (RelativeLayout.LayoutParams) right_view.getLayoutParams();
        llp_right_view.width = CommonUtil.getRealWidth(148);
        llp_right_view.rightMargin = CommonUtil.getRealWidth(8);

        webview = (WebView) findViewById(R.id.webview);

        baoming_rela.setOnClickListener(this);
        active_back.setOnClickListener(this);
        active_image.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.baoming_rela: {
                if (LoginUtils.isLogin(true, ActiveActivity.this)) {
                    Intent intent = new Intent(ActiveActivity.this, ActiveBaoMingActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("mHtmlText", mHtmlText);
                    startActivityForResult(intent, 101);
                }
            }
            break;

            case R.id.active_back: {
                finish();
            }
            break;

            case R.id.active_image: {
                String url = WebParameters.SERVERURL + "/app/apply/shareDetailPage?" + "activityId=" + id + "&longitude=120&latitude=30";
                showPopwindow(url, mainHomeListItem.getPhoto(), mainHomeListItem.getName(), "三公里,附近一手掌握");
            }
            break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101) {
            if (resultCode == 102) {
                finish();
            }
        } else {
            Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);
        }
    }

    //QQ回调监听
    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
            Toast.makeText(ActiveActivity.this, "分享取消", Toast.LENGTH_SHORT);
        }

        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            Toast.makeText(ActiveActivity.this, "分享成功", Toast.LENGTH_SHORT);
        }

        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
            Toast.makeText(ActiveActivity.this, "分享失败", Toast.LENGTH_SHORT);
        }
    };

    private void showPopwindow(final String url, final String imgUrl, final String title, final String main) {
        View parent = ((ViewGroup) this.findViewById(R.id.bottomRela));
        View popView = View.inflate(this, R.layout.share_popup_window, null);

        LinearLayout sharePopWindow = (LinearLayout) popView.findViewById(R.id.sharePopWindow);
        RelativeLayout.LayoutParams llpSharePopWindow = (RelativeLayout.LayoutParams) sharePopWindow.getLayoutParams();
        llpSharePopWindow.height = CommonUtil.getRealWidth(300);

        ImageView shareToQq = (ImageView) popView.findViewById(R.id.shareToQq);
        RelativeLayout.LayoutParams llpShareToQq = (RelativeLayout.LayoutParams) shareToQq.getLayoutParams();
        llpShareToQq.width = CommonUtil.getRealWidth(100);
        llpShareToQq.height = llpShareToQq.width;
        llpShareToQq.topMargin = CommonUtil.getRealWidth(45);

        ImageView shareToQZone = (ImageView) popView.findViewById(R.id.shareToQZone);
        RelativeLayout.LayoutParams llpShareToQZone = (RelativeLayout.LayoutParams) shareToQZone.getLayoutParams();
        llpShareToQZone.width = CommonUtil.getRealWidth(100);
        llpShareToQZone.height = llpShareToQZone.width;
        llpShareToQZone.topMargin = CommonUtil.getRealWidth(45);

        ImageView shareToWx = (ImageView) popView.findViewById(R.id.shareToWx);
        RelativeLayout.LayoutParams llpShareToWx = (RelativeLayout.LayoutParams) shareToWx.getLayoutParams();
        llpShareToWx.width = CommonUtil.getRealWidth(100);
        llpShareToWx.height = llpShareToWx.width;
        llpShareToWx.topMargin = CommonUtil.getRealWidth(45);

        ImageView shareToPyq = (ImageView) popView.findViewById(R.id.shareToPyq);
        RelativeLayout.LayoutParams llpShareToPyq = (RelativeLayout.LayoutParams) shareToPyq.getLayoutParams();
        llpShareToPyq.width = CommonUtil.getRealWidth(100);
        llpShareToPyq.height = llpShareToPyq.width;
        llpShareToPyq.topMargin = CommonUtil.getRealWidth(45);

        TextView qqText = (TextView) popView.findViewById(R.id.qqText);
        qqText.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llpQqText = (RelativeLayout.LayoutParams) qqText.getLayoutParams();
        llpQqText.topMargin = CommonUtil.getRealWidth(20);

        TextView qzoneText = (TextView) popView.findViewById(R.id.qzoneText);
        qzoneText.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llpQzoneText = (RelativeLayout.LayoutParams) qzoneText.getLayoutParams();
        llpQzoneText.topMargin = CommonUtil.getRealWidth(20);

        TextView wxText = (TextView) popView.findViewById(R.id.wxText);
        wxText.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llpWxText = (RelativeLayout.LayoutParams) wxText.getLayoutParams();
        llpWxText.topMargin = CommonUtil.getRealWidth(20);

        TextView pyqText = (TextView) popView.findViewById(R.id.pyqText);
        pyqText.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llpPyqText = (RelativeLayout.LayoutParams) pyqText.getLayoutParams();
        llpPyqText.topMargin = CommonUtil.getRealWidth(20);


        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        popWindow = new PopupWindow(popView, width, height);
        ColorDrawable dw = new ColorDrawable(0x30000000);
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//        popWindow.setAnimationStyle(R.style.AnimBottom);
//        popWindow.setFocusable(true);
//        popWindow.setOutsideTouchable(false);// 设置同意在外点击消失

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.shareToQq:
//                        shareToQQ(0, url, imgUrl, title, main);
                        ShareUtil.shareToQQ(0, ActiveActivity.this, url, imgUrl, title, main);
                        break;
                    case R.id.shareToQZone:
//                        shareToQQ(1, url, imgUrl, title, main);
                        ShareUtil.shareToQQ(1, ActiveActivity.this, url, imgUrl, title, main);
                        break;
                    case R.id.shareToWx:
//                        weiChat(0, url, imgUrl, title, main);
                        ShareUtil.weiChat(0, ActiveActivity.this, url, imgUrl, title, main);
                        break;
                    case R.id.shareToPyq:
//                        weiChat(1, url, imgUrl, title, main);
                        ShareUtil.weiChat(1, ActiveActivity.this, url, imgUrl, title, main);
                        break;
                    default:
                        break;
                }
                popWindow.dismiss();
            }
        };

        popView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int downY = (int) event.getY();
                    if (downY < CommonUtil.getRealWidth(900)) {
                        popWindow.dismiss();
                        disFlag = true;
                    }
                }
                return true;
            }
        });

        shareToQq.setOnClickListener(listener);
        shareToQZone.setOnClickListener(listener);
        shareToWx.setOnClickListener(listener);
        shareToPyq.setOnClickListener(listener);

    }

}

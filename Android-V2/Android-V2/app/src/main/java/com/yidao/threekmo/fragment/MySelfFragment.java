package com.yidao.threekmo.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ijustyce.fastkotlin.glide.GetBitmapCall;
import com.ijustyce.fastkotlin.glide.ImageLoader;
import com.yidao.threekmo.R;
import com.yidao.threekmo.activitys.ExperienceMyListActivity;
import com.yidao.threekmo.activitys.IndexActivity;
import com.yidao.threekmo.activitys.MyActiveListActivity;
import com.yidao.threekmo.activitys.MyCollectCommodityActivity;
import com.yidao.threekmo.activitys.MyFollowShopActivity;
import com.yidao.threekmo.activitys.OrderMySelfActivity;
import com.yidao.threekmo.v2.activity.RedPocketsActivity;
import com.yidao.threekmo.v2.activity.SettingActivity;
import com.yidao.threekmo.activitys.UserInfoEditActivity;
import com.yidao.threekmo.activitys.WebViewScriptActivity;
import com.yidao.threekmo.bean.CollectNumResult;
import com.yidao.threekmo.bean.UserBean;
import com.yidao.threekmo.retrofit_server.SubjectServer;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.FastBlurUtil;
import com.yidao.threekmo.utils.LoginUtils;
import com.yidao.threekmo.utils.RetrofitUtils;
import com.yidao.threekmo.v2.utils.AppImage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Smart~ on 2017/10/10.
 */

public class MySelfFragment extends BaseFragment implements View.OnClickListener {

    private ImageView back;
    private ImageView user_Icon;
    private ImageView go_on;
    private TextView setting;
    private TextView user_name;
    private TextView singnature;
    private LinearLayout info_linear;
    private RelativeLayout first_rela;
    private TextView first_text;
    private ImageView first_image;
    private RelativeLayout second_rela;
    private TextView second_text;
    private ImageView second_image;
    private RelativeLayout third_rela;
    private TextView third_text;
    private ImageView third_image;
    private View view1;
    private View view2;
    private ImageView first_bao;
    private ImageView first_exper;
    private ImageView first_money;

    private LinearLayout blank_linear;
    private RelativeLayout lookshop_rela;
    private TextView lookshop_num;
    private TextView lookshop_text;
    private RelativeLayout achievesome_rela;
    private TextView achievesome_num;
    private TextView achievesome_text;
    private RelativeLayout experence_rela;
    private TextView experence_num;
    private TextView experence_text;

    public static final MySelfFragment newInstance(String title) {
        MySelfFragment f = new MySelfFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(title, title);
        f.setArguments(bdl);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myself, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();

    }

    private void initViews() {
        back = (ImageView) getView().findViewById(R.id.back);
        RelativeLayout.LayoutParams llp_back = (RelativeLayout.LayoutParams) back.getLayoutParams();
        llp_back.height = CommonUtil.screenWidth;


        user_Icon = (ImageView) getView().findViewById(R.id.user_Icon);
        RelativeLayout.LayoutParams llp_user_Icon = (RelativeLayout.LayoutParams) user_Icon.getLayoutParams();
        llp_user_Icon.width = CommonUtil.getRealWidth(148);
        llp_user_Icon.height = llp_user_Icon.width;
        llp_user_Icon.topMargin = CommonUtil.getRealWidth(144);
        llp_user_Icon.leftMargin = CommonUtil.getRealWidth(30);

        go_on = (ImageView) getView().findViewById(R.id.go_on);
        RelativeLayout.LayoutParams llp_go_on = (RelativeLayout.LayoutParams) go_on.getLayoutParams();
        llp_go_on.width = CommonUtil.getRealWidth(32);
        llp_go_on.height = llp_go_on.width;
        llp_go_on.topMargin = CommonUtil.getRealWidth(180);
        llp_go_on.rightMargin = CommonUtil.getRealWidth(30);

        setting = (TextView) getView().findViewById(R.id.setting);
        setting.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_setting = (RelativeLayout.LayoutParams) setting.getLayoutParams();
        llp_setting.topMargin = CommonUtil.getRealWidth(64);
        llp_setting.rightMargin = CommonUtil.getRealWidth(30);

        user_name = (TextView) getView().findViewById(R.id.user_name);
        user_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(36));
        RelativeLayout.LayoutParams llp_user_name = (RelativeLayout.LayoutParams) user_name.getLayoutParams();
        llp_user_name.leftMargin = CommonUtil.getRealWidth(202);
        llp_user_name.topMargin = CommonUtil.getRealWidth(170);

        singnature = (TextView) getView().findViewById(R.id.singnature);
        singnature.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_singnature = (RelativeLayout.LayoutParams) singnature.getLayoutParams();
        llp_singnature.topMargin = CommonUtil.getRealWidth(228);
        llp_singnature.leftMargin = CommonUtil.getRealWidth(202);

        info_linear = (LinearLayout) getView().findViewById(R.id.info_linear);
        RelativeLayout.LayoutParams llp_info_linear = (RelativeLayout.LayoutParams) info_linear.getLayoutParams();
        llp_info_linear.topMargin = CommonUtil.getRealWidth(24);

        View view0 = getView().findViewById(R.id.view0);
        LinearLayout.LayoutParams llp_view0 = (LinearLayout.LayoutParams) view0.getLayoutParams();
        llp_view0.height = CommonUtil.getRealWidth(20);

        first_rela = (RelativeLayout) getView().findViewById(R.id.first_rela);
        LinearLayout.LayoutParams llp_first_rela = (LinearLayout.LayoutParams) first_rela.getLayoutParams();
        llp_first_rela.height = CommonUtil.getRealWidth(90);

        first_text = (TextView) getView().findViewById(R.id.first_text);
        first_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_first_text = (RelativeLayout.LayoutParams) first_text.getLayoutParams();
        llp_first_text.leftMargin = CommonUtil.getRealWidth(98);

        first_image = (ImageView) getView().findViewById(R.id.first_image);
        RelativeLayout.LayoutParams llp_first_image = (RelativeLayout.LayoutParams) first_image.getLayoutParams();
        llp_first_image.width = CommonUtil.getRealWidth(36);
        llp_first_image.height = llp_first_image.width;
        llp_first_image.rightMargin = CommonUtil.getRealWidth(30);

        view1 = getView().findViewById(R.id.view1);
        LinearLayout.LayoutParams llp_view1 = (LinearLayout.LayoutParams) view1.getLayoutParams();
        llp_view1.height = CommonUtil.getRealWidth(20);

        second_rela = (RelativeLayout) getView().findViewById(R.id.second_rela);
        LinearLayout.LayoutParams llp_second_rela = (LinearLayout.LayoutParams) second_rela.getLayoutParams();
        llp_second_rela.height = CommonUtil.getRealWidth(90);

        second_text = (TextView) getView().findViewById(R.id.second_text);
        second_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_second_text = (RelativeLayout.LayoutParams) second_text.getLayoutParams();
        llp_second_text.leftMargin = CommonUtil.getRealWidth(98);

        second_image = (ImageView) getView().findViewById(R.id.second_image);
        RelativeLayout.LayoutParams llp_second_image = (RelativeLayout.LayoutParams) second_image.getLayoutParams();
        llp_second_image.width = CommonUtil.getRealWidth(36);
        llp_second_image.height = llp_second_image.width;
        llp_second_image.rightMargin = CommonUtil.getRealWidth(30);

        view2 = getView().findViewById(R.id.view2);
        LinearLayout.LayoutParams llp_view2 = (LinearLayout.LayoutParams) view2.getLayoutParams();
        llp_view2.height = CommonUtil.getRealWidth(20);

        third_rela = (RelativeLayout) getView().findViewById(R.id.third_rela);
        LinearLayout.LayoutParams llp_third_rela = (LinearLayout.LayoutParams) third_rela.getLayoutParams();
        llp_third_rela.height = CommonUtil.getRealWidth(90);

        //  begin 我的 福卡
        View view3 = getView().findViewById(R.id.view3);
        LinearLayout.LayoutParams llp_view3 = (LinearLayout.LayoutParams) view3.getLayoutParams();
        llp_view3.height = CommonUtil.getRealWidth(20);

        RelativeLayout cardView = (RelativeLayout) getView().findViewById(R.id.my_card);
        LinearLayout.LayoutParams llp_third_card = (LinearLayout.LayoutParams) cardView.getLayoutParams();
        llp_third_card.height = CommonUtil.getRealWidth(90);
        cardView.setOnClickListener(this);

        ImageView icon_card = (ImageView) getView().findViewById(R.id.icon_card);
        RelativeLayout.LayoutParams llp_icon_card = (RelativeLayout.LayoutParams) icon_card.getLayoutParams();
        llp_icon_card.width = CommonUtil.getRealWidth(40);
        llp_icon_card.height = CommonUtil.getRealWidth(44);
        llp_icon_card.leftMargin = CommonUtil.getRealWidth(30);


        TextView text_card = (TextView) getView().findViewById(R.id.text_card);
        text_card.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_text_card = (RelativeLayout.LayoutParams) text_card.getLayoutParams();
        llp_text_card.leftMargin = CommonUtil.getRealWidth(98);

        ImageView card_right = (ImageView) getView().findViewById(R.id.card_right);
        RelativeLayout.LayoutParams llp_card_right = (RelativeLayout.LayoutParams) card_right.getLayoutParams();
        llp_card_right.width = CommonUtil.getRealWidth(36);
        llp_card_right.height = llp_card_right.width;
        llp_card_right.rightMargin = CommonUtil.getRealWidth(30);

        //  end 我的 福卡

        third_text = (TextView) getView().findViewById(R.id.third_text);
        third_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_third_text = (RelativeLayout.LayoutParams) third_text.getLayoutParams();
        llp_third_text.leftMargin = CommonUtil.getRealWidth(98);

        third_image = (ImageView) getView().findViewById(R.id.third_image);
        RelativeLayout.LayoutParams llp_third_image = (RelativeLayout.LayoutParams) third_image.getLayoutParams();
        llp_third_image.width = CommonUtil.getRealWidth(36);
        llp_third_image.height = llp_third_image.width;
        llp_third_image.rightMargin = CommonUtil.getRealWidth(30);

        first_bao = (ImageView) getView().findViewById(R.id.first_bao);
        RelativeLayout.LayoutParams llp_first_bao = (RelativeLayout.LayoutParams) first_bao.getLayoutParams();
        llp_first_bao.width = CommonUtil.getRealWidth(48);
        llp_first_bao.height = llp_first_bao.width;
        llp_first_bao.leftMargin = CommonUtil.getRealWidth(30);

        first_exper = (ImageView) getView().findViewById(R.id.first_exper);
        RelativeLayout.LayoutParams llp_first_exper = (RelativeLayout.LayoutParams) first_exper.getLayoutParams();
        llp_first_exper.width = CommonUtil.getRealWidth(48);
        llp_first_exper.height = llp_first_exper.width;
        llp_first_exper.leftMargin = CommonUtil.getRealWidth(30);

        first_money = (ImageView) getView().findViewById(R.id.first_money);
        RelativeLayout.LayoutParams llp_first_money = (RelativeLayout.LayoutParams) first_money.getLayoutParams();
        llp_first_money.width = CommonUtil.getRealWidth(48);
        llp_first_money.height = llp_first_money.width;
        llp_first_money.leftMargin = CommonUtil.getRealWidth(30);

        blank_linear = (LinearLayout) getView().findViewById(R.id.blank_linear);
        RelativeLayout.LayoutParams llp_blank_linear = (RelativeLayout.LayoutParams) blank_linear.getLayoutParams();
        llp_blank_linear.height = CommonUtil.getRealWidth(140);
        llp_blank_linear.topMargin = CommonUtil.getRealWidth(336);

        lookshop_rela = (RelativeLayout) getView().findViewById(R.id.lookshop_rela);

        lookshop_num = (TextView) getView().findViewById(R.id.lookshop_num);
        lookshop_num.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_lookshop_num = (RelativeLayout.LayoutParams) lookshop_num.getLayoutParams();
        llp_lookshop_num.topMargin = CommonUtil.getRealWidth(24);

        lookshop_text = (TextView) getView().findViewById(R.id.lookshop_text);
        lookshop_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_lookshop_text = (RelativeLayout.LayoutParams) lookshop_text.getLayoutParams();
        llp_lookshop_text.topMargin = CommonUtil.getRealWidth(12);

        achievesome_rela = (RelativeLayout) getView().findViewById(R.id.achievesome_rela);

        achievesome_num = (TextView) getView().findViewById(R.id.achievesome_num);
        achievesome_num.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_achievesome_num = (RelativeLayout.LayoutParams) achievesome_num.getLayoutParams();
        llp_achievesome_num.topMargin = CommonUtil.getRealWidth(24);

        achievesome_text = (TextView) getView().findViewById(R.id.achievesome_text);
        achievesome_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_achievesome_text = (RelativeLayout.LayoutParams) achievesome_text.getLayoutParams();
        llp_achievesome_text.topMargin = CommonUtil.getRealWidth(12);

        experence_rela = (RelativeLayout) getView().findViewById(R.id.experence_rela);

        experence_num = (TextView) getView().findViewById(R.id.experence_num);
        experence_num.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_experence_num = (RelativeLayout.LayoutParams) experence_num.getLayoutParams();
        llp_experence_num.topMargin = CommonUtil.getRealWidth(24);

        experence_text = (TextView) getView().findViewById(R.id.experence_text);
        experence_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_experence_text = (RelativeLayout.LayoutParams) experence_text.getLayoutParams();
        llp_experence_text.topMargin = CommonUtil.getRealWidth(12);

        user_Icon.setOnClickListener(this);
        user_name.setOnClickListener(this);
        singnature.setOnClickListener(this);
        first_rela.setOnClickListener(this);
        third_rela.setOnClickListener(this);
        setting.setOnClickListener(this);
        experence_rela.setOnClickListener(this);
        lookshop_rela.setOnClickListener(this);
        achievesome_rela.setOnClickListener(this);
        second_rela.setOnClickListener(this);
        go_on.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (LoginUtils.isLogin(false, getActivity())) {
            UserBean userBean = LoginUtils.getUserBean(getActivity());
            String name = userBean.getNickname();
            if (TextUtils.isEmpty(name)) {
                user_name.setText("无名");
            } else {
                user_name.setText(name);
            }

            if (TextUtils.isEmpty(userBean.getIndividualitySignature())) {
                singnature.setText("这个人很懒，什么都没有留下...");
            } else {
                singnature.setText(userBean.getIndividualitySignature());
            }

            String face = userBean.getFace();
            if (null != face && !TextUtils.isEmpty(face)) {
                AppImage.INSTANCE.circle(user_Icon, face);
                ImageLoader.getFromUrl(getActivity(), face, 200, 200, new GetBitmapCall() {
                    @Override
                    public void onGetBitmap(@org.jetbrains.annotations.Nullable Bitmap bitmap) {
                        back.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        back.setBackgroundDrawable(new BitmapDrawable(FastBlurUtil.fastblur(getActivity(), bitmap, 25)));
                    }
                });
            } else {
                Glide.with(getActivity()).load(R.mipmap.usericon_default).into(user_Icon);
                back.setBackgroundResource(R.color.user_back_green);
            }

        } else {
            back.setBackgroundResource(R.color.user_back_green);
            user_name.setText("未登录");
            singnature.setText("这个人很懒，什么都没有留下...");
            Glide.with(getActivity()).load(R.mipmap.usericon_default).into(user_Icon);
        }

        if (!TextUtils.isEmpty(LoginUtils.getToken(getActivity()))) {
            Call<CollectNumResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).collectNum(LoginUtils.getToken(getActivity()));
            ((IndexActivity) getActivity()).addCall(call);
            call.enqueue(new Callback<CollectNumResult>() {
                @Override
                public void onResponse(Call<CollectNumResult> call, Response<CollectNumResult> response) {
                    CollectNumResult body = response.body();
                    if (body != null) {
                        if (body.getRspCode() == 0) {
                            int orderNum = body.getData().getOrdersSum();
                            int merNum = body.getData().getMerchandiseSum();
                            int subNum = body.getData().getSubjectTrainSum();

                            lookshop_num.setText(subNum + "");
                            achievesome_num.setText(merNum + "");
                            experence_num.setText(orderNum + "");
                        }
                    }
                }

                @Override
                public void onFailure(Call<CollectNumResult> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_Icon: {
                if (LoginUtils.isLogin(true, getActivity())) {
                    Intent intent = new Intent(getActivity(), UserInfoEditActivity.class);
                    startActivity(intent);
                }
            }
            break;
            case R.id.user_name: {
                if (LoginUtils.isLogin(true, getActivity())) {
                    Intent intent = new Intent(getActivity(), UserInfoEditActivity.class);
                    startActivity(intent);
                }
            }
            break;
            case R.id.singnature: {
                if (LoginUtils.isLogin(true, getActivity())) {
                    Intent intent = new Intent(getActivity(), UserInfoEditActivity.class);
                    startActivity(intent);
                }
            }
            break;
            case R.id.first_rela: {
                if (LoginUtils.isLogin(true, getActivity())) {
                    Intent intent = new Intent(getActivity(), MyActiveListActivity.class);
                    startActivity(intent);
                }
            }
            break;
            case R.id.third_rela: {
                if (LoginUtils.isLogin(true, getActivity())) {
                    Intent intent = new Intent(getActivity(), RedPocketsActivity.class);
                    startActivity(intent);
                }
            }
            break;
            case R.id.setting: {
                if (LoginUtils.isLogin(true, getActivity())) {
                    Intent intent = new Intent(getActivity(), SettingActivity.class);
                    startActivity(intent);
                }
            }
            break;

            case R.id.experence_rela: {
                if (LoginUtils.isLogin(true, getActivity())) {
                    Intent intent = new Intent(getActivity(), OrderMySelfActivity.class);
                    startActivity(intent);
                }
            }
            break;
            //关注商家
            case R.id.lookshop_rela: {
                if (LoginUtils.isLogin(true, getActivity())) {
                    Intent intent = new Intent(getActivity(), MyFollowShopActivity.class);
                    startActivity(intent);
                }
            }
            break;
            //收藏商品
            case R.id.achievesome_rela: {
                if (LoginUtils.isLogin(true, getActivity())) {
                    Intent intent = new Intent(getActivity(), MyCollectCommodityActivity.class);
                    startActivity(intent);
                }
            }
            break;
            //我的体验
            case R.id.second_rela: {
                if (LoginUtils.isLogin(true, getActivity())) {
                    Intent intent = new Intent(getActivity(), ExperienceMyListActivity.class);
                    startActivity(intent);
                }
            }
            break;

            case R.id.my_card:
                if (LoginUtils.isLogin(true, getActivity())) {
                    Intent intent = new Intent(getActivity(), WebViewScriptActivity.class);
                    intent.putExtra("url", "http://h5.3kmo.com/app/card/mobileLuckbag?token=" + LoginUtils.getToken(getContext()));
                    intent.putExtra("title", "我的福卡");
                    startActivity(intent);
                }
                break;

            case R.id.go_on: {
                if (LoginUtils.isLogin(true, getActivity())) {
                    Intent intent = new Intent(getActivity(), UserInfoEditActivity.class);
                    startActivity(intent);
                }
            }
            break;

            default:
                break;
        }
    }
}

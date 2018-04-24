package com.yidao.threekmo.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidao.threekmo.R;
import com.yidao.threekmo.activitys.IndexActivity;
import com.yidao.threekmo.fragment.FirstFragment;
import com.yidao.threekmo.utils.CommonUtil;

/**
 * Created by Administrator on 2017\9\4 0004.
 */

public class NearBySpinner extends android.support.v7.widget.AppCompatTextView implements View.OnClickListener {
    private int currentSelectedItemPosition = 1;
    private PopupWindow mPopWindow;
    private OnClickNearByListener onclickNearBuListener;
    private ImageView near_image;
    private ImageView five_image;
    private ImageView ten_image;

    public NearBySpinner(Context context) {
        super(context);
        initView((Activity) context);
    }

    public NearBySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (context instanceof Activity) {
            initView((Activity) context);
        } else {

        }
    }

    public NearBySpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView((Activity) context);
    }

    /**
     * 初始化下拉框
     *
     * @param context
     */
    private void initView(Activity context) {
        Drawable drawable = getResources().getDrawable(R.mipmap.down2);
        /// 这一步必须要做,否则不会显示.
        setPadding(0, 0, 50, 0);
        setCompoundDrawablePadding(-40);
        drawable.setBounds(0, 0, CommonUtil.getRealWidth(20), CommonUtil.getRealWidth(20));
        setCompoundDrawables(null, null, drawable, null);
    }


    /**
     * 显示window
     */
    public void showWindow(Context mContext) {

        if(FirstFragment.fuJinType == 0){
            near_image.setVisibility(View.VISIBLE);
            five_image.setVisibility(View.GONE);
            ten_image.setVisibility(View.GONE);
        }else if(FirstFragment.fuJinType == 1){
            near_image.setVisibility(View.GONE);
            five_image.setVisibility(View.VISIBLE);
            ten_image.setVisibility(View.GONE);
        }else if(FirstFragment.fuJinType == 2){
            near_image.setVisibility(View.GONE);
            five_image.setVisibility(View.GONE);
            ten_image.setVisibility(View.VISIBLE);
        }

        Drawable drawable = getResources().getDrawable(R.mipmap.color_up);
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//        setBackground();
        setCompoundDrawables(null, null, drawable, null);
//        mPopWindow.showAsDropDown(this);
        if (Build.VERSION.SDK_INT != 24) {
            mPopWindow.showAsDropDown(this,0,0);
        } else {
            int[] a = new int[2];
            this.getLocationInWindow(a);
            mPopWindow.showAtLocation(((IndexActivity)mContext).getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, this.getHeight()+a[1]);
            mPopWindow.update();
        }
    }

    /**
     * 初始化列表window
     *
     * @param context 上下文 必须是activity
     *                //     * @param list    数据源
     */
    public void init(Activity context) {
//        setData(list);
        setGravity(Gravity.CENTER);
        View view = View.inflate(context, R.layout.spinner_nearby, null);
        final RelativeLayout text_rela = (RelativeLayout) view.findViewById(R.id.text_rela);

        RelativeLayout near_rela = (RelativeLayout) view.findViewById(R.id.near_rela);
        RelativeLayout.LayoutParams llp_near_rela = (RelativeLayout.LayoutParams) near_rela.getLayoutParams();
        llp_near_rela.height = CommonUtil.getRealWidth(92);

        TextView near_text = (TextView) view.findViewById(R.id.near_text);
        near_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_near_text = (RelativeLayout.LayoutParams) near_text.getLayoutParams();
        llp_near_text.leftMargin = CommonUtil.getRealWidth(40);

        near_image = (ImageView) view.findViewById(R.id.near_image);
        RelativeLayout.LayoutParams llp_near_image = (RelativeLayout.LayoutParams) near_image.getLayoutParams();
        llp_near_image.width = CommonUtil.getRealWidth(36);
        llp_near_image.height = llp_near_image.width;
        llp_near_image.rightMargin = CommonUtil.getRealWidth(40);

        RelativeLayout five_rela = (RelativeLayout) view.findViewById(R.id.five_rela);
        RelativeLayout.LayoutParams llp_five_rela = (RelativeLayout.LayoutParams) five_rela.getLayoutParams();
        llp_five_rela.height = CommonUtil.getRealWidth(92);

        TextView five_text = (TextView) view.findViewById(R.id.five_text);
        five_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_five_text = (RelativeLayout.LayoutParams) five_text.getLayoutParams();
        llp_five_text.leftMargin = CommonUtil.getRealWidth(40);

        five_image = (ImageView) view.findViewById(R.id.five_image);
        RelativeLayout.LayoutParams llp_five_image = (RelativeLayout.LayoutParams) five_image.getLayoutParams();
        llp_five_image.width = CommonUtil.getRealWidth(36);
        llp_five_image.height = llp_five_image.width;
        llp_five_image.rightMargin = CommonUtil.getRealWidth(40);

        RelativeLayout ten_rela = (RelativeLayout) view.findViewById(R.id.ten_rela);
        RelativeLayout.LayoutParams llp_ten_rela = (RelativeLayout.LayoutParams) ten_rela.getLayoutParams();
        llp_ten_rela.height = CommonUtil.getRealWidth(92);

        TextView ten_text = (TextView) view.findViewById(R.id.ten_text);
        ten_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_ten_text = (RelativeLayout.LayoutParams) ten_text.getLayoutParams();
        llp_ten_text.leftMargin = CommonUtil.getRealWidth(40);

        ten_image = (ImageView) view.findViewById(R.id.ten_image);
        RelativeLayout.LayoutParams llp_ten_image = (RelativeLayout.LayoutParams) ten_image.getLayoutParams();
        llp_ten_image.width = CommonUtil.getRealWidth(36);
        llp_ten_image.height = llp_ten_image.width;
        llp_ten_image.rightMargin = CommonUtil.getRealWidth(40);

        RelativeLayout none_rela = (RelativeLayout) view.findViewById(R.id.none_rela);
        RelativeLayout.LayoutParams llp_none_rela = (RelativeLayout.LayoutParams) none_rela.getLayoutParams();
        llp_none_rela.height = CommonUtil.getRealWidth(92);

        TextView none_text = (TextView) view.findViewById(R.id.none_text);
        none_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_none_text = (RelativeLayout.LayoutParams) none_text.getLayoutParams();
        llp_none_text.leftMargin = CommonUtil.getRealWidth(40);

        ImageView none_image = (ImageView) view.findViewById(R.id.none_image);
        RelativeLayout.LayoutParams llp_none_image = (RelativeLayout.LayoutParams) none_image.getLayoutParams();
        llp_none_image.width = CommonUtil.getRealWidth(36);
        llp_none_image.height = llp_none_image.width;
        llp_none_image.rightMargin = CommonUtil.getRealWidth(40);

        near_rela.setOnClickListener(this);
        five_rela.setOnClickListener(this);
        ten_rela.setOnClickListener(this);

        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

//                int downX = (int) event.getX();
                int downY = (int) event.getY();

                int listHeight = text_rela.getHeight();

                if (downY > listHeight) {
                    mPopWindow.dismiss();
                }

                return true;
            }
        });


        mPopWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        mPopWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_transparent));
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Drawable drawable = getResources().getDrawable(R.mipmap.down2);
                /// 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                setCompoundDrawables(null, null, drawable, null);

            }
        });

    }

    /**
     * 关闭当前的window
     */
    public void closeWindow() {
        if (mPopWindow != null && mPopWindow.isShowing()) {

            mPopWindow.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        mPopWindow.dismiss();
        switch (v.getId()) {
            case R.id.near_rela: {
                FirstFragment.fuJinType = 0;
                onclickNearBuListener.nearBy("3km",3);
            }
            break;
            case R.id.five_rela: {
                FirstFragment.fuJinType = 1;
                onclickNearBuListener.nearBy("5km",5);
            }
            break;
            case R.id.ten_rela: {
                FirstFragment.fuJinType = 2;
                onclickNearBuListener.nearBy("10km",10);
            }
            break;

            default:
                break;
        }
    }

    public interface OnClickNearByListener {
        void nearBy(String nearBy, int positon);
    }

    public void setOnclickNearBuListener(OnClickNearByListener onclickNearBuListener) {
        this.onclickNearBuListener = onclickNearBuListener;
    }

}
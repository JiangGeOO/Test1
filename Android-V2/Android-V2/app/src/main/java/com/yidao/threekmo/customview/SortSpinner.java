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

public class SortSpinner extends android.support.v7.widget.AppCompatTextView implements View.OnClickListener {
    private int currentSelectedItemPosition = 1;
    private PopupWindow mPopWindow;
    private OnclickSort onclickSort;

    private ImageView near_image;
    private ImageView five_image;
    private ImageView none_image;

    public SortSpinner(Context context) {
        super(context);
        initView((Activity) context);
    }

    public SortSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (context instanceof Activity) {
            initView((Activity) context);
        } else {

        }
    }

    public SortSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
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

        if(FirstFragment.sortType == 0){
            near_image.setVisibility(View.VISIBLE);
            five_image.setVisibility(View.GONE);
            none_image.setVisibility(View.GONE);
        }else if(FirstFragment.sortType == 1){
            near_image.setVisibility(View.GONE);
            five_image.setVisibility(View.VISIBLE);
            none_image.setVisibility(View.GONE);
        }else if(FirstFragment.sortType == 3){
            near_image.setVisibility(View.GONE);
            five_image.setVisibility(View.GONE);
            none_image.setVisibility(View.VISIBLE);
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
            mPopWindow.showAtLocation(((IndexActivity) mContext).getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, this.getHeight() + a[1]);
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
        View view = View.inflate(context, R.layout.spinner_sort, null);
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

        ImageView ten_image = (ImageView) view.findViewById(R.id.ten_image);
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

        none_image = (ImageView) view.findViewById(R.id.none_image);
        RelativeLayout.LayoutParams llp_none_image = (RelativeLayout.LayoutParams) none_image.getLayoutParams();
        llp_none_image.width = CommonUtil.getRealWidth(36);
        llp_none_image.height = llp_none_image.width;
        llp_none_image.rightMargin = CommonUtil.getRealWidth(40);

        text_rela.setOnClickListener(this);
        five_rela.setOnClickListener(this);
        none_rela.setOnClickListener(this);

        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

//                int downX = (int) event.getX();
                int downY = (int) event.getY();

                int listHeight = text_rela.getHeight();

//                if(downY > CommonUtil.getRealWidth(600)){
//                    mPopWindow.dismiss();
//                }

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
            case R.id.text_rela: {
                FirstFragment.sortType = 0;
                onclickSort.sort("默认",0);
            }
            break;
            case R.id.five_rela: {
                FirstFragment.sortType = 1;
                onclickSort.sort("最新",1);
            }
            break;
            case R.id.none_rela: {
                FirstFragment.sortType = 3;
                onclickSort.sort("最多",2);
            }
            break;

            default:
                break;
        }
    }

    public interface OnclickSort {
        void sort(String sort, int position);
    }

    public void setOnclickSortListener(OnclickSort onclickSort) {
        this.onclickSort = onclickSort;
    }

}
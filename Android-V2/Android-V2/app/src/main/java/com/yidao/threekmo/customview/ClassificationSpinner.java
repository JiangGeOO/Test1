package com.yidao.threekmo.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidao.threekmo.R;
import com.yidao.threekmo.activitys.IndexActivity;
import com.yidao.threekmo.utils.CommonUtil;

/**
 * Created by Administrator on 2017\9\4 0004.
 */

public class ClassificationSpinner extends android.support.v7.widget.AppCompatTextView implements View.OnClickListener {
    private int currentSelectedItemPosition = 1;
    private PopupWindow mPopWindow;
    private OnclickClassification onclickClassification;

    public ClassificationSpinner(Context context) {
        super(context);
        initView((Activity) context);
    }

    public ClassificationSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (context instanceof Activity) {
            initView((Activity) context);
        } else {

        }
    }

    public ClassificationSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView((Activity) context);
    }

    /**
     * 获取当前选中的item
     *
     * @return
     */
    public int getSelectedItemPosition() {
        return currentSelectedItemPosition;
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
            mPopWindow.showAtLocation(((IndexActivity) mContext).getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, this.getHeight() + a[1] + 60);
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
        View view = View.inflate(context, R.layout.spinner_classification, null);
//        final ListView listView = (ListView) view.findViewById(R.id.pop_list);
        final RelativeLayout text_rela = (RelativeLayout) view.findViewById(R.id.text_rela);

        TextView all_text = (TextView) view.findViewById(R.id.all_text);
        RelativeLayout.LayoutParams llp_all_text = (RelativeLayout.LayoutParams) all_text.getLayoutParams();
        llp_all_text.width = CommonUtil.getRealWidth(138);
        llp_all_text.height = CommonUtil.getRealWidth(56);
        llp_all_text.leftMargin = CommonUtil.getRealWidth(30);
        llp_all_text.topMargin = CommonUtil.getRealWidth(34);

        TextView food_text = (TextView) view.findViewById(R.id.food_text);
        RelativeLayout.LayoutParams llp_food_text = (RelativeLayout.LayoutParams) food_text.getLayoutParams();
        llp_food_text.width = CommonUtil.getRealWidth(138);
        llp_food_text.height = CommonUtil.getRealWidth(56);
        llp_food_text.leftMargin = CommonUtil.getRealWidth(46);
        llp_food_text.topMargin = CommonUtil.getRealWidth(34);

        TextView train_text = (TextView) view.findViewById(R.id.train_text);
        RelativeLayout.LayoutParams llp_train_text = (RelativeLayout.LayoutParams) train_text.getLayoutParams();
        llp_train_text.width = CommonUtil.getRealWidth(138);
        llp_train_text.height = CommonUtil.getRealWidth(56);
        llp_train_text.leftMargin = CommonUtil.getRealWidth(46);
        llp_train_text.topMargin = CommonUtil.getRealWidth(34);

        TextView show_text = (TextView) view.findViewById(R.id.show_text);
        RelativeLayout.LayoutParams llp_show_text = (RelativeLayout.LayoutParams) show_text.getLayoutParams();
        llp_show_text.width = CommonUtil.getRealWidth(138);
        llp_show_text.height = CommonUtil.getRealWidth(56);
        llp_show_text.leftMargin = CommonUtil.getRealWidth(46);
        llp_show_text.topMargin = CommonUtil.getRealWidth(34);

        TextView tourism_text = (TextView) view.findViewById(R.id.tourism_text);
        RelativeLayout.LayoutParams llp_tourism_text = (RelativeLayout.LayoutParams) tourism_text.getLayoutParams();
        llp_tourism_text.width = CommonUtil.getRealWidth(138);
        llp_tourism_text.height = CommonUtil.getRealWidth(56);
        llp_tourism_text.leftMargin = CommonUtil.getRealWidth(30);
        llp_tourism_text.topMargin = CommonUtil.getRealWidth(22);
        llp_tourism_text.bottomMargin = CommonUtil.getRealWidth(34);


        all_text.setOnClickListener(this);
        food_text.setOnClickListener(this);
        train_text.setOnClickListener(this);
        show_text.setOnClickListener(this);
        tourism_text.setOnClickListener(this);

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
            case R.id.all_text: {
                onclickClassification.classification("全部",0);
            }
            break;
            case R.id.food_text: {
                onclickClassification.classification("美食",0);
            }
            break;
            case R.id.train_text: {
                onclickClassification.classification("培训",0);
            }
            break;
            case R.id.show_text: {
                onclickClassification.classification("演出",0);
            }
            break;
            case R.id.tourism_text: {
                onclickClassification.classification("旅游",0);
            }
            break;
            default:
                break;
        }
    }

    public interface OnclickClassification {
        void classification(String classifiation, int positon);
    }

    public void setOnclickClassificationListener(OnclickClassification onclickClassification) {
        this.onclickClassification = onclickClassification;
    }

}
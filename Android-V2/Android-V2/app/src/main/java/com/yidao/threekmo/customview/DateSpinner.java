package com.yidao.threekmo.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidao.threekmo.R;
import com.yidao.threekmo.activitys.IndexActivity;
import com.yidao.threekmo.fragment.FirstFragment;
import com.yidao.threekmo.utils.CommonUtil;

import java.util.Calendar;

/**
 * Created by Administrator on 2017\9\4 0004.
 */

public class DateSpinner extends android.support.v7.widget.AppCompatTextView implements View.OnClickListener {
    private int currentSelectedItemPosition = 1;
    private PopupWindow mPopWindow;
    private OnclickDate onclickDate;
    private int type = 0;
    private DatePicker  pvTime;
    private RelativeLayout date_choose;

    private TextView all;
    private TextView today;
    private TextView tommrow;
    private TextView queding;

    public DateSpinner(Context context) {
        super(context);
        initView((Activity) context);
    }

    public DateSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (context instanceof Activity) {
            initView((Activity) context);
        } else {

        }
    }

    public DateSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
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

        if(FirstFragment.dataType == 0){
            all.setBackgroundResource(R.drawable.spinner_green_back);
            today.setBackgroundResource(R.drawable.spinner_gray_back);
            tommrow.setBackgroundResource(R.drawable.spinner_gray_back);
            queding.setBackgroundResource(R.drawable.spinner_gray_back);

            all.setTextColor(getResources().getColor(R.color.white));
            today.setTextColor(getResources().getColor(R.color.text_color_black));
            tommrow.setTextColor(getResources().getColor(R.color.text_color_black));
            queding.setTextColor(getResources().getColor(R.color.text_color_black));

        }else if(FirstFragment.dataType == 1){
            all.setBackgroundResource(R.drawable.spinner_gray_back);
            today.setBackgroundResource(R.drawable.spinner_green_back);
            tommrow.setBackgroundResource(R.drawable.spinner_gray_back);
            queding.setBackgroundResource(R.drawable.spinner_gray_back);

            all.setTextColor(getResources().getColor(R.color.text_color_black));
            today.setTextColor(getResources().getColor(R.color.white));
            tommrow.setTextColor(getResources().getColor(R.color.text_color_black));
            queding.setTextColor(getResources().getColor(R.color.text_color_black));

        }else if(FirstFragment.dataType == 2){
            all.setBackgroundResource(R.drawable.spinner_gray_back);
            today.setBackgroundResource(R.drawable.spinner_gray_back);
            tommrow.setBackgroundResource(R.drawable.spinner_green_back);
            queding.setBackgroundResource(R.drawable.spinner_gray_back);

            all.setTextColor(getResources().getColor(R.color.text_color_black));
            today.setTextColor(getResources().getColor(R.color.text_color_black));
            tommrow.setTextColor(getResources().getColor(R.color.white));
            queding.setTextColor(getResources().getColor(R.color.text_color_black));

        }else if(FirstFragment.dataType == 3){
            all.setBackgroundResource(R.drawable.spinner_gray_back);
            today.setBackgroundResource(R.drawable.spinner_gray_back);
            tommrow.setBackgroundResource(R.drawable.spinner_gray_back);
            queding.setBackgroundResource(R.drawable.spinner_green_back);

            all.setTextColor(getResources().getColor(R.color.text_color_black));
            today.setTextColor(getResources().getColor(R.color.text_color_black));
            tommrow.setTextColor(getResources().getColor(R.color.text_color_black));
            queding.setTextColor(getResources().getColor(R.color.white));

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
    public void init(final Activity context) {
//        initTimePicker(context);
        setGravity(Gravity.CENTER);
        View view = View.inflate(context, R.layout.spinner_date, null);
        final RelativeLayout text_rela = (RelativeLayout) view.findViewById(R.id.text_rela);

        all = (TextView) view.findViewById(R.id.all);
        all.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_all = (RelativeLayout.LayoutParams) all.getLayoutParams();
        llp_all.width = CommonUtil.getRealWidth(172);
        llp_all.height = CommonUtil.getRealWidth(60);
        llp_all.leftMargin = CommonUtil.getRealWidth(40);
        llp_all.topMargin = CommonUtil.getRealWidth(28);

        today = (TextView) view.findViewById(R.id.today);
        today.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_today = (RelativeLayout.LayoutParams) today.getLayoutParams();
        llp_today.width = CommonUtil.getRealWidth(172);
        llp_today.height = CommonUtil.getRealWidth(60);
        llp_today.leftMargin = CommonUtil.getRealWidth(78);
        llp_today.topMargin = CommonUtil.getRealWidth(28);

        tommrow = (TextView) view.findViewById(R.id.tommrow);
        tommrow.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_tommrow = (RelativeLayout.LayoutParams) tommrow.getLayoutParams();
        llp_tommrow.width = CommonUtil.getRealWidth(172);
        llp_tommrow.height = CommonUtil.getRealWidth(60);
        llp_tommrow.leftMargin = CommonUtil.getRealWidth(76);
        llp_tommrow.topMargin = CommonUtil.getRealWidth(28);

        TextView start = (TextView) view.findViewById(R.id.start);
        start.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_start = (RelativeLayout.LayoutParams) start.getLayoutParams();
        llp_start.width = CommonUtil.getRealWidth(214);
        llp_start.height = CommonUtil.getRealWidth(60);
        llp_start.leftMargin = CommonUtil.getRealWidth(40);
        llp_start.topMargin = CommonUtil.getRealWidth(52);

        View view1 = view.findViewById(R.id.view1);
        RelativeLayout.LayoutParams llp_view1 = (RelativeLayout.LayoutParams) view1.getLayoutParams();
        llp_view1.width = CommonUtil.getRealWidth(40);
        llp_view1.height = CommonUtil.getRealWidth(4);
        llp_view1.leftMargin = CommonUtil.getRealWidth(16);
        llp_view1.topMargin = CommonUtil.getRealWidth(78);

        TextView end = (TextView) view.findViewById(R.id.end);
        end.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_end = (RelativeLayout.LayoutParams) end.getLayoutParams();
        llp_end.width = CommonUtil.getRealWidth(214);
        llp_end.height = CommonUtil.getRealWidth(60);
        llp_end.leftMargin = CommonUtil.getRealWidth(16);
        llp_end.topMargin = CommonUtil.getRealWidth(52);

        queding = (TextView) view.findViewById(R.id.queding);
        queding.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_queding = (RelativeLayout.LayoutParams) queding.getLayoutParams();
        llp_queding.width = CommonUtil.getRealWidth(126);
        llp_queding.height = CommonUtil.getRealWidth(60);
        llp_queding.leftMargin = CommonUtil.getRealWidth(44);
        llp_queding.topMargin = CommonUtil.getRealWidth(52);
        llp_queding.bottomMargin = CommonUtil.getRealWidth(32);

        RelativeLayout choose_rela = (RelativeLayout) view.findViewById(R.id.choose_rela);
        TextView choose_false = (TextView) view.findViewById(R.id.choose_false);
        choose_false.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_choose_false = (RelativeLayout.LayoutParams)choose_false.getLayoutParams();
        llp_choose_false.leftMargin = CommonUtil.getRealWidth(40);
        llp_choose_false.topMargin = CommonUtil.getRealWidth(20);

        TextView choose_true = (TextView) view.findViewById(R.id.choose_true);
        choose_true.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_choose_true = (RelativeLayout.LayoutParams)choose_true.getLayoutParams();
        llp_choose_true.rightMargin = CommonUtil.getRealWidth(40);
        llp_choose_true.topMargin = CommonUtil.getRealWidth(20);

        date_choose = (RelativeLayout) view.findViewById(R.id.date_choose);

        pvTime = (DatePicker) view.findViewById(R.id.pvTime);
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        pvTime.init(year, month, day, new DatePicker.OnDateChangedListener()
        {

            @Override
            public void onDateChanged(DatePicker arg0, int year
                    , int month, int day)
            {
//                month需要加1
                if(type == 1){
                    Log.e("*********************","您选择的开始日期："+year+"年  " + (month + 1)+"月  "+day+"日");
                }else if(type == 2){
                    Log.e("*********************","您选择的结束日期："+year+"年  " + (month + 1)+"月  "+day+"日");
                }

            }
        });

        all.setOnClickListener(this);
        today.setOnClickListener(this);
        tommrow.setOnClickListener(this);
        start.setOnClickListener(this);
        end.setOnClickListener(this);
        queding.setOnClickListener(this);
        choose_false.setOnClickListener(this);
        choose_true.setOnClickListener(this);

        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int downY = (int) event.getY();
                int listHeight = text_rela.getHeight();
                if (downY > listHeight && downY < CommonUtil.getRealWidth(500)) {
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
                date_choose.setVisibility(View.GONE);
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

        switch (v.getId()) {
            case R.id.all: {
                FirstFragment.dataType = 0;
                onclickDate.date("","");
                mPopWindow.dismiss();
            }
            break;
            case R.id.today: {
                FirstFragment.dataType = 1;
                onclickDate.date("","");
                mPopWindow.dismiss();
            }
            break;
            case R.id.tommrow: {
                FirstFragment.dataType = 2;
                onclickDate.date("","");
                mPopWindow.dismiss();
            }
            break;
            case R.id.start: {
                type = 1;
                date_choose.setVisibility(View.VISIBLE);
            }
            break;
            case R.id.end: {
                type = 2;
                date_choose.setVisibility(View.VISIBLE);
            }
            break;
            case R.id.queding: {
                FirstFragment.dataType = 3;
                onclickDate.date("","");
                mPopWindow.dismiss();
            }
            break;
            case R.id.choose_false:{
                date_choose.setVisibility(View.GONE);
            }
            break;

            case R.id.choose_true:{
                date_choose.setVisibility(View.GONE);
            }
            break;
            default:
                break;
        }
    }

    public interface OnclickDate {
        void date(String start, String end);
    }

    public void setOnclickDateListener(OnclickDate onclickDate) {
        this.onclickDate = onclickDate;
    }

}
package com.yidao.threekmo.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yidao.threekmo.R;
import com.yidao.threekmo.bean.MainHomeListItem;
import com.yidao.threekmo.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017\9\4 0004.
 */

public class IosSpinner extends android.support.v7.widget.AppCompatTextView {
    private OnSpinnerItemClickListener mOnSpinnerItemClickListener;
    private int currentSelectedItemPosition = 1;
    public IosSpinnerAdapter iosAdapter;
    private List<MainHomeListItem> datas = new ArrayList<MainHomeListItem>();
    private PopupWindow mPopWindow;

    public IosSpinner(Context context) {
        super(context);
        initView((Activity) context);
    }

    public IosSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (context instanceof Activity) {

            initView((Activity) context);
        } else {

        }
    }

    public IosSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView((Activity) context);
    }

    /**
     * 设置item 点击事件
     *
     * @param listener
     */
    public void setOnSpinnerItemClickListener(OnSpinnerItemClickListener listener) {
        this.mOnSpinnerItemClickListener = listener;
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
        setPadding(0,0,200,0);
        setCompoundDrawablePadding(-CommonUtil.getRealWidth(100));
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * 设置数据源
     *
     * @param datas
     */
    public void setData(List<MainHomeListItem> datas) {
        this.datas = datas;
        iosAdapter.notifyDataSetChanged();
    }

    /**
     * 显示window
     */
    public void showWindow() {

        Drawable drawable = getResources().getDrawable(R.mipmap.color_up);
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//        setBackground();
        setCompoundDrawables(null, null, drawable, null);
        mPopWindow.showAsDropDown(this);
    }

    /**
     * 初始化列表window
     *
     * @param context 上下文 必须是activity
//     * @param list    数据源
     */
    public void init(Activity context) {
//        setData(list);
        setGravity(Gravity.CENTER);
        View view = View.inflate(context, R.layout.popwindow, null);
        final ListView listView = (ListView) view.findViewById(R.id.pop_list);
//        final RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.relaLayout);
//        if(list.size() == 0){
//            listView.setVisibility(View.GONE);
//            layout.setVisibility(View.VISIBLE);
//        }else{
//            listView.setVisibility(View.VISIBLE);
//            layout.setVisibility(View.GONE);
//        }
        iosAdapter = new IosSpinnerAdapter(context);
        listView.setAdapter(iosAdapter);

        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

//                int downX = (int) event.getX();
                int downY = (int) event.getY();

                int listHeight = listView.getHeight();

//                if(downY > CommonUtil.getRealWidth(600)){
//                    mPopWindow.dismiss();
//                }

                if(downY > listHeight){
                    mPopWindow.dismiss();
                }

                return true;
            }
        });


        mPopWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                IosSpinner.this.setText(datas.get(position));
                currentSelectedItemPosition = position + 1;
                iosAdapter.notifyDataSetChanged();
                mPopWindow.dismiss();
                if (mOnSpinnerItemClickListener != null) {
                    mOnSpinnerItemClickListener.OnSpinnerItemClick(parent, view, position, id);
                }
            }
        });

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


    public class IosSpinnerAdapter extends BaseAdapter {
        private Context context;

        public IosSpinnerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(context, R.layout.item_pop, null);
//                holder.view = convertView.findViewById(R.id.divider);
//                holder.up_divider = convertView.findViewById(R.id.up_divider);
                holder.textView = (TextView) convertView.findViewById(R.id.type);
                holder.view = convertView.findViewById(R.id.view);
//                holder.imageView = (ImageView) convertView.findViewById(R.id.isSelected);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
//            if (position == 0) {
//                holder.up_divider.setVisibility(View.VISIBLE);
//            }
//            if (position == datas.size() - 1) {
//                holder.view.setVisibility(View.GONE);
//            }
//            if (position == currentSelectedItemPosition - 1) {
//                holder.imageView.setVisibility(View.VISIBLE);
//                holder.textView.setTextColor(Color.parseColor("#99F47C30"));
//            } else {
//                holder.imageView.setVisibility(View.GONE);
//                holder.textView.setTextColor(Color.parseColor("#99000000"));
//            }
            if(position == 0){
               holder.view.setVisibility(View.INVISIBLE);
            }else{
                holder.view.setVisibility(View.VISIBLE);
            }
            if(datas.size() == 0){
                holder.textView.setText("暂无数据");
            }else{
                holder.textView.setText(datas.get(position).getName());
            }

            return convertView;
        }

        class ViewHolder{
//            View view;
//            View up_divider;
            TextView textView;
            View view;
//            ImageView imageView;
        }

    }

    public interface OnSpinnerItemClickListener {
        void OnSpinnerItemClick(AdapterView<?> parent, View view, int position, long id);
    }
}
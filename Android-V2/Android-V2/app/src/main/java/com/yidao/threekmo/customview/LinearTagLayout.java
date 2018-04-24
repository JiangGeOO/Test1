package com.yidao.threekmo.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yidao.myutils.log.LogUtils;
import com.yidao.threekmo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017\8\21 0021.
 */

public class LinearTagLayout extends ViewGroup {
    private List<String> tagList = new ArrayList<String>();
    private LayoutInflater layoutInflater;
    private int type = 0;

    public LinearTagLayout(Context context) {
        super(context);
        init(context);
    }

    public LinearTagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LinearTagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setNum(int type){
        this.type = type;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int totalWidth = 0;
        int measuredWidth = getMeasuredWidth();


        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
            int width = layoutParams.leftMargin + layoutParams.rightMargin +view.getMeasuredWidth();
            if(totalWidth + width <= measuredWidth){
                int left = totalWidth;
                int top = layoutParams.topMargin;
                int right = totalWidth +view.getMeasuredWidth();
                int bottom = top + view.getMeasuredHeight();
                LogUtils.e("left:" + left + "  top:" + top + "  right:" + right + "  bottom:" + bottom);

                view.layout(left,top,right,bottom);
                totalWidth = totalWidth + width;

            }else {
                removeView(view);
            }
        }
    }

    private void init(Context context){
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        int maxHeigh = 0;

        // 获得它的父容器为它设置的测量模式和大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        if(getChildCount()>0){
            View tag = getChildAt(0);
            MarginLayoutParams layoutParams = (MarginLayoutParams) tag.getLayoutParams();
            maxHeigh = layoutParams.bottomMargin +layoutParams.topMargin +tag.getMeasuredHeight();
        }

        setMeasuredDimension(sizeWidth,maxHeigh);


    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs)
    {
        return new MarginLayoutParams(getContext(), attrs);
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
        invalidate();
    }

    public void setTagList(String[] tagList) {
        List<String> tags = new ArrayList<String>();
        for (int i = 0; i < tagList.length; i++) {
            tags.add(tagList[i]);
        }
        this.tagList = tags;
        removeAllViews();

        for (int i = 0; i < this.tagList.size(); i++) {
            View view;
            if(type == 1){
                view = layoutInflater.inflate(R.layout.item_lable, this, false);
            }else{
                view = layoutInflater.inflate(R.layout.item_hot, this, false);
            }
            TextView tvTag = (TextView) view.findViewById(R.id.tvTag);
            tvTag.setText(this.tagList.get(i));
            addView(view,getChildCount());

        }

        invalidate();
    }
}

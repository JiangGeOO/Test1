package com.yidao.threekmo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Smart~ on 2017/11/23.
 */

public class ExperienceViewpagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<View> dataList;

    private ImageView imageView;

    public ExperienceViewpagerAdapter(Context mContext, List<View> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View relativeLayout = dataList.get(position);
        container.addView(relativeLayout);

        return relativeLayout;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(dataList.get(position));
    }

    public void setData(List<View> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }
}

package com.yidao.threekmo.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Smart~ on 2017/11/8.
 */

public class ShopHeaderViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<ImageView> imageList;
    private OnPagerItemClickListener onPagerItemClickListener;

    public void setOnPagerItemClickListener(OnPagerItemClickListener onPagerItemClickListener){
        this.onPagerItemClickListener = onPagerItemClickListener;
    };

    public ShopHeaderViewPagerAdapter(Context mContext, List<ImageView> imageList) {
        this.mContext = mContext;
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        ImageView imageView = imageList.get(position);
        container.addView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPagerItemClickListener.onItem(position);
            }
        });

        return imageView;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageList.get(position));
    }

    public void setData(List<ImageView> imageList){
        this.imageList = imageList;
        notifyDataSetChanged();
    }

    public interface OnPagerItemClickListener{
        void onItem(int position);
    }

}

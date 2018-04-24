package com.yidao.threekmo.customview;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Marker;
import com.bumptech.glide.Glide;
import com.yidao.threekmo.R;
import com.yidao.threekmo.bean.MainHomeListItem;

/**
 * Created by Administrator on 2017\9\5 0005.
 */

public class MyStyleGDMap implements AMap.InfoWindowAdapter {

    private Context mContext;

    public MyStyleGDMap(Context mContext) {
        this.mContext = mContext;
    }

    private View inforWindow = null;

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        if (inforWindow == null) {
            inforWindow = LayoutInflater.from(mContext).inflate(R.layout.m_style_gdmap,null);
        }
        render(marker,inforWindow);
        return inforWindow;
    }

    /**
     * 自定义infowinfow窗口
     */
    public void render(Marker marker, View view) {
    //如果想修改自定义Infow中内容，请通过view找到它并修改
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        MainHomeListItem mainHome = (MainHomeListItem) marker.getObject();
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView info = (TextView) view.findViewById(R.id.info);
        if(mainHome != null){
            if(mainHome != null && !TextUtils.isEmpty(mainHome.getPhotoUrl())){
                Glide.with(mContext).load(mainHome.getPhotoUrl()).into(imageView);
            }
            String province = mainHome.getProvince();
            String city = mainHome.getCity();
            String area = mainHome.getArea();
            String address = mainHome.getAddress();
            if(TextUtils.isEmpty(province)){
                province = "";
            }
            if(TextUtils.isEmpty(city)){
                city = "";
            }
            if(TextUtils.isEmpty(area)){
                area = "";
            }
            if(TextUtils.isEmpty(address)){
                address = "";
            }
            info.setText(province + city + area + address);
            imageView.setVisibility(View.VISIBLE);
            info.setVisibility(View.VISIBLE);
        }else{
            imageView.setVisibility(View.GONE);
            info.setVisibility(View.GONE);
        }
        title.setText(marker.getTitle());
    }
}

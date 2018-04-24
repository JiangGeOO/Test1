package com.yidao.threekmo.customview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Marker;
import com.bumptech.glide.Glide;
import com.yidao.threekmo.R;

/**
 * Created by Administrator on 2017\8\22 0022.
 */

public class MInfoWindowAdapter implements AMap.InfoWindowAdapter {
    private final LayoutInflater layoutInflater;
    private Context context;

    public MInfoWindowAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View view = layoutInflater.inflate(R.layout.item_map_infowindow, null);
        render(marker,view);
        return view;
    }
    //此方法和 getInfoWindow（Marker marker） 方法的实质是一样的，唯一的区别是：此方法不能修改整个 InfoWindow 的背景和边
    @Override
    public View getInfoContents(Marker marker) {

        return null;
    }

    public void render(Marker marker, View view) {
        ImageView ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(marker.getTitle());
        Glide.with(context).load(((String) marker.getObject())).into(ivIcon);
    }
}

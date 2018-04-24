package com.yidao.threekmo.activitys;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.yidao.threekmo.R;
import com.yidao.threekmo.application.MyApplication;
import com.yidao.threekmo.customview.MyStyleGDMap;

public class CityMapActivity extends BaseActivity {

    private MapView mMapView;
    private AMap aMap;
    private UiSettings mUiSettings;
    private ImageView back;
    private MyStyleGDMap gaodeMap;
    private CameraUpdate mCameraUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_city_map);

        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //参数依次是：视角调整区域的中心点坐标、希望调整到的缩放级别、俯仰角0°~45°（垂直与地图时为0）、偏航角 0~360° (正北方为0)
        mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(MyApplication.getInstance().getLatitude(), MyApplication.getInstance().getLongitude()), 18, 0, 0));

        maiInit();
        getData();

    }

    private void getData() {
        aMap.moveCamera(mCameraUpdate);
        aMap.animateCamera(mCameraUpdate, 2000, new AMap.CancelableCallback() {
            @Override
            public void onFinish() {
                LatLng latLng1 = new LatLng(MyApplication.getInstance().getLatitude(), MyApplication.getInstance().getLongitude());
                MarkerOptions markerOption = new MarkerOptions();
                markerOption.position(latLng1);
                markerOption.title("当前位置");
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.mipmap.zhoubian)));
                Marker marker = aMap.addMarker(markerOption);
            }

            @Override
            public void onCancel() {

            }
        });

    }

    private void maiInit() {

        if (aMap == null) {
            aMap = mMapView.getMap();
            //缩放级别≥17级时，地图上可以显示室内地图
            // 缩放级别≥18级时，不仅可以看到室内地图效果，还允许操作切换楼层，显示精细化室内地图。
            aMap.showIndoorMap(true);
//            aMap.setInfoWindowAdapter(new MInfoWindowAdapter(MapActivity.this));
            mUiSettings = aMap.getUiSettings();
            mUiSettings.setZoomControlsEnabled(false);
            mUiSettings.setCompassEnabled(true);

//            //参数依次是：视角调整区域的中心点坐标、希望调整到的缩放级别、俯仰角0°~45°（垂直与地图时为0）、偏航角 0~360° (正北方为0)
//            CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(MyApplication.getInstance().getLatitude(), MyApplication.getInstance().getLongitude()),18,0,0));
//            gaodeMap = new MyStyleGDMap(MapActivity.this);
//            aMap.animateCamera(mCameraUpdate, 2000, new AMap.CancelableCallback() {
//                @Override
//                public void onFinish() {
//
//                    LatLng latLng1 = new LatLng(MyApplication.getInstance().getLatitude(), MyApplication.getInstance().getLongitude());
//                    MarkerOptions markerOption = new MarkerOptions();
//                    markerOption.position(latLng1);
//                    markerOption.title("西安市").snippet("西安市：34.341568, 108.940174");
//                    markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
//                            .decodeResource(getResources(),R.mipmap.map_xiaoqu)));
//                    Marker marker = aMap.addMarker(markerOption);
////
//                }
//
//                @Override
//                public void onCancel() {
//
//                }
//            });
//            aMap.setInfoWindowAdapter(gaodeMap);
            //设置marker点击事件
            aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    if (marker.isInfoWindowShown()) {
                        marker.hideInfoWindow();
                    } else {
                        marker.showInfoWindow();

                    }
                    return true;
                }
            });
        }

        //添加圆
        LatLng latLng = new LatLng(MyApplication.getInstance().getLatitude(), MyApplication.getInstance().getLongitude());
        aMap.addCircle(new CircleOptions().
                center(latLng).
                radius(3000).
                fillColor(Color.argb(30, 106, 120, 140)).
                strokeColor(Color.rgb(106, 120, 140)).
                strokeWidth(2));

        //初始化设置缩放级别
//        aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

}

package com.yidao.threekmo.activitys;

import android.content.Intent;
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
import com.yidao.threekmo.bean.GaoDeMapBean;
import com.yidao.threekmo.bean.MainHomeListItem;
import com.yidao.threekmo.customview.MyStyleGDMap;
import com.yidao.threekmo.retrofit_server.SubjectServer;
import com.yidao.threekmo.utils.RetrofitUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapSecondActivity extends BaseActivity {

    private MapView mMapView;
    private AMap aMap;
    private UiSettings mUiSettings;
    private ImageView back;
    private MyStyleGDMap gaodeMap;
    private MainHomeListItem mainHomeListItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_map_second);

        if (getIntent() != null) {
            mainHomeListItem = (MainHomeListItem) getIntent().getSerializableExtra("mainHomeListItem");
        }

        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getData();
        initMap();
    }

    private void initMap() {
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
//            gaodeMap = new MyStyleGDMap(MapSecondActivity.this);
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
            //详情点击事件
            aMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    MainHomeListItem mainMap = (MainHomeListItem) marker.getObject();
                    long id = mainMap.getId();
                    Intent intent = new Intent(MapSecondActivity.this,SecondlyDetailActivity.class);
                    intent.putExtra("id",id);
                    intent.putExtra("subJectId",mainHomeListItem.getId());
                    startActivity(intent);
                }
            });
        }

        //添加圆
        LatLng latLng = new LatLng(Double.parseDouble(mainHomeListItem.getLatitude()), Double.parseDouble(mainHomeListItem.getLongitude()));
        aMap.addCircle(new CircleOptions().
                center(latLng).
                radius(3000).
                fillColor(Color.argb(30, 106, 120, 140)).
                strokeColor(Color.rgb(106, 120, 140)).
                strokeWidth(2));

        //初始化设置缩放级别
//        aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
    }

    private void getData() {
        Call<GaoDeMapBean> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).getGaoDeSecond(mainHomeListItem.getLongitude(), mainHomeListItem.getLatitude(), null, null, mainHomeListItem.getId());
        addCall(call);
        call.enqueue(new Callback<GaoDeMapBean>() {
            @Override
            public void onResponse(Call<GaoDeMapBean> call, Response<GaoDeMapBean> response) {
                GaoDeMapBean gaoDeMapBean = response.body();
                if(gaoDeMapBean != null) {

                    final List<MainHomeListItem> secondMap = gaoDeMapBean.getData().getData();

                    //参数依次是：视角调整区域的中心点坐标、希望调整到的缩放级别、俯仰角0°~45°（垂直与地图时为0）、偏航角 0~360° (正北方为0)
                    CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(Double.parseDouble(mainHomeListItem.getLatitude()), Double.parseDouble(mainHomeListItem.getLongitude())), 18, 0, 0));
                    LatLng latLng1 = new LatLng(MyApplication.getInstance().getLatitude(), MyApplication.getInstance().getLongitude());
                    MarkerOptions markerOption = new MarkerOptions();
                    markerOption.position(latLng1);
                    markerOption.title("当前位置");
                    Marker marker = aMap.addMarker(markerOption);
                    gaodeMap = new MyStyleGDMap(MapSecondActivity.this);
                    aMap.setInfoWindowAdapter(gaodeMap);
                    aMap.moveCamera(mCameraUpdate);
                    aMap.animateCamera(mCameraUpdate, 2000, new AMap.CancelableCallback() {
                        @Override
                        public void onFinish() {

                            for (int i = 0; i < secondMap.size(); i++) {
                                LatLng latLng1 = new LatLng(Double.parseDouble(secondMap.get(i).getLatitude()), Double.parseDouble(secondMap.get(i).getLongitude()));
                                MarkerOptions markerOption = new MarkerOptions();
                                markerOption.position(latLng1);
                                markerOption.title(secondMap.get(i).getName())
                                        .snippet(secondMap.get(i).getAddress());
                                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                        .decodeResource(getResources(), R.mipmap.zhoubian)));
                                Marker marker = aMap.addMarker(markerOption);
                                MainHomeListItem homeListItem = secondMap.get(i);
                                marker.setObject((MainHomeListItem)homeListItem);
                                MainHomeListItem path = (MainHomeListItem) marker.getObject();
                            }
//
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<GaoDeMapBean> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
}

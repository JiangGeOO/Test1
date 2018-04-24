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

public class MapActivity extends BaseActivity{

    private MapView mMapView ;
    private AMap aMap;
    private UiSettings mUiSettings;
    private ImageView back;
    private MyStyleGDMap gaodeMap;
    private CameraUpdate mCameraUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_map);

        mMapView  = (MapView) findViewById(R.id.mapView);
        if (mMapView != null) mMapView.onCreate(savedInstanceState);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //参数依次是：视角调整区域的中心点坐标、希望调整到的缩放级别、俯仰角0°~45°（垂直与地图时为0）、偏航角 0~360° (正北方为0)
        mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(MyApplication.getInstance().getLatitude(), MyApplication.getInstance().getLongitude()), 18, 0, 0));

        getData();
        maiInit();

    }

    private void getData() {
        Call<GaoDeMapBean> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).getGaoDeall(MyApplication.getInstance().getLongitude() + "", MyApplication.getInstance().getLatitude() + "", 3000);
        addCall(call);
        call.enqueue(new Callback<GaoDeMapBean>() {
            @Override
            public void onResponse(Call<GaoDeMapBean> call, Response<GaoDeMapBean> response) {
                GaoDeMapBean bean = response.body();
                if(bean != null) {

                    final List<MainHomeListItem> allMapInfo = bean.getData().getData();



                    LatLng latLng1 = new LatLng(MyApplication.getInstance().getLatitude(), MyApplication.getInstance().getLongitude());
                    MarkerOptions markerOption = new MarkerOptions();
                    markerOption.position(latLng1);
                    markerOption.title("当前位置");
//                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
//                                    .decodeResource(getResources(),R.mipmap.map_shangchang)));
                    if (aMap == null) return;
                    Marker marker = aMap.addMarker(markerOption);
                    gaodeMap = new MyStyleGDMap(MapActivity.this);
                    aMap.setInfoWindowAdapter(gaodeMap);
                    aMap.moveCamera(mCameraUpdate);
                    aMap.animateCamera(mCameraUpdate, 2000, new AMap.CancelableCallback() {
                        @Override
                        public void onFinish() {

                            for (int i = 0; i < allMapInfo.size(); i++) {
                                LatLng latLng1 = new LatLng(Double.parseDouble(allMapInfo.get(i).getLatitude()), Double.parseDouble(allMapInfo.get(i).getLongitude()));
                                MarkerOptions markerOption = new MarkerOptions();
                                markerOption.position(latLng1);
                                markerOption.title(allMapInfo.get(i).getName()).snippet(allMapInfo.get(i).getAddress());
                                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                        .decodeResource(getResources(), R.mipmap.zhoubian)));
                                Marker marker = aMap.addMarker(markerOption);
                                MainHomeListItem homeListItem = allMapInfo.get(i);
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

    private void maiInit() {

        if (aMap == null) {
            aMap = mMapView.getMap();
            if (aMap == null) return;
            aMap.showIndoorMap(true);
            mUiSettings = aMap.getUiSettings();
            mUiSettings.setZoomControlsEnabled(false);
            mUiSettings.setCompassEnabled(true);
            aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    if(marker.isInfoWindowShown()){
                        marker.hideInfoWindow();
                    }else {
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
                    Intent intent = new Intent(MapActivity.this,AscriptionSubActivity.class);
                    intent.putExtra("subJect",id);
                    startActivity(intent);
                }
            });
        }

        //添加圆
        LatLng latLng = new LatLng(MyApplication.getInstance().getLatitude(), MyApplication.getInstance().getLongitude());
        aMap.addCircle(new CircleOptions().
                center(latLng).
                radius(3000).
                fillColor(Color.argb(30,106, 120, 140)).
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

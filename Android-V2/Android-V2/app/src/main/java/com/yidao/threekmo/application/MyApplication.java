package com.yidao.threekmo.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.ijustyce.fastkotlin.IApplication;
import com.ijustyce.fastkotlin.glide.ImageLoader;
import com.ijustyce.fastkotlin.http.HttpManager;
import com.ijustyce.fastkotlin.utils.CommonTools;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.yidao.myutils.log.LogUtils;
import com.yidao.myutils.network.NetWorkUtils;
import com.yidao.myutils.phone.PhoneUtils;
import com.yidao.myutils.screen.ScreenUtils;
import com.yidao.threekmo.BuildConfig;
import com.yidao.threekmo.customview.GlideImageLoader;
import com.yidao.threekmo.parameter.WebParameters;
import com.yidao.threekmo.utils.LoginUtils;
import com.yidao.threekmo.utils.OkHttpUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Administrator on 2017\8\18 0018.
 */

public class MyApplication extends Application implements AMapLocationListener {
    private static MyApplication myApplication;
    private int screenHeight;
    private int screenWidth;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private double latitude;
    private double longitude;
    private boolean needRefresh = true;
    private OSSClient oss;

    public static boolean isWithdraw;

    //获取手机状态
    private int netWorkStatus;
    private String device_id = "";
    private String device_model= "";
    private String device_os= "";
    private String device_verson_name = "";
    private String device_verson_code = "";

    @Override
    public void onCreate() {
        super.onCreate();

        String processName = getProcessName(android.os.Process.myPid());
        String packageName = getPackageName();
        if (packageName != null && packageName.equals(processName)) {
            init();
        }
    }

    private void init() {
        myApplication = this;
        IApplication.application = this;
        HttpManager.INSTANCE.setBaseUrl(WebParameters.SERVERURL);
        String token = LoginUtils.getToken(this);
        HttpManager.INSTANCE.addCommonHeader("token", token);
        HttpManager.INSTANCE.addCommonParameter("token", token);
        OkHttpUtils.init(this);
        diviceInit();
        amapInit();
        imagePickerInit();
        ossInit();
        uMengInit();
        CrashReport.initCrashReport(getApplicationContext(), getBuglyId(), false);

        ImageLoader.type = new ImageLoader.ImageLoadType() {
            @Override
            public boolean isAliYunServer(String url) {
                return url != null && url.startsWith("http://tkm.oss-cn-hangzhou.aliyuncs.com");
            }

            @Override
            public boolean isSlowNetWork() {
                return !CommonTools.INSTANCE.isWifi(getApplicationContext());
            }
        };
    }

    private String getBuglyId() {
        return BuildConfig.DEBUG ? "7ded485eab" : "a9efde4e94";
    }

    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    private void uMengInit() {
        MobclickAgent.setScenarioType(MyApplication.this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    private void ossInit() {
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("frQfVOmQgQ9R11P8", "LW7k3FBY4JJ1u2qYKfxTPuQ0Udntl2");
        oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider);
    }


    private void imagePickerInit() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setMultiMode(false);
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    private void diviceInit() {

        //网络状态初始化
        netWorkStatus = NetWorkUtils.getNetWorkStatus(this);
        //获取设备imei号
        device_id = PhoneUtils.getAndroidId(this);
        //获取机型
        device_model = PhoneUtils.getDeviceModel();
        //获取系统版本号
        device_os = PhoneUtils.getSystemVersion();
        //获取app版本号
        device_verson_name = PhoneUtils.getVersionName(this);
        //获取versoncode
        device_verson_code = PhoneUtils.getVersionCode(this);

        screenHeight = ScreenUtils.getScreenHeight(this);
        screenWidth = ScreenUtils.getScreenWidth(this);


    }

    private void amapInit() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //定位参数配置
        mLocationOption = new AMapLocationClientOption();
        //低功耗模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //定位周期30s
        mLocationOption.setInterval(30000);
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

    public MyApplication getMyApplication() {
        return myApplication;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public static MyApplication getInstance(){
        return myApplication;
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        latitude = aMapLocation.getLatitude();
        longitude = aMapLocation.getLongitude();
        LogUtils.e("latitude:" +latitude + "  longitude:" + longitude);
        Log.e("latitude" ,latitude+"");
        Log.e("longitude" ,longitude+"");
        setLatitude(latitude);
        setLongitude(longitude);

    }

    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(base);
        super.attachBaseContext(base);
    }

    public OSSClient getOss() {
        return oss;
    }

    public void setOss(OSSClient oss) {
        this.oss = oss;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isNeedRefresh() {
        return needRefresh;
    }

    public void setNeedRefresh(boolean needRefresh) {
        this.needRefresh = needRefresh;
    }

    public int getNetWorkStatus() {
        return netWorkStatus;
    }

    public void setNetWorkStatus(int netWorkStatus) {
        this.netWorkStatus = netWorkStatus;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getDevice_model() {
        return device_model;
    }

    public void setDevice_model(String device_model) {
        this.device_model = device_model;
    }

    public String getDevice_os() {
        return device_os;
    }

    public void setDevice_os(String device_os) {
        this.device_os = device_os;
    }

    public String getDevice_verson_name() {
        return device_verson_name;
    }

    public void setDevice_verson_name(String device_verson_name) {
        this.device_verson_name = device_verson_name;
    }

    public String getDevice_verson_code() {
        return device_verson_code;
    }

    public void setDevice_verson_code(String device_verson_code) {
        this.device_verson_code = device_verson_code;
    }
}

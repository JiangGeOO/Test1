package com.yidao.threekmo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yidao.threekmo.R;
import com.yidao.threekmo.application.MyApplication;
import com.yidao.threekmo.bean.UpdateBean;
import com.yidao.threekmo.customview.CopyEaseDialog;
import com.yidao.threekmo.retrofit_server.RegServer;
import com.yidao.threekmo.service.DownloadService;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.RetrofitUtils;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends BaseActivity {

//    private GifImageView image;
    private ImageView company_logo;
    private ImageView live_logo;
    private ImageView skip;
    private Timer timer = new Timer();
    private TimerTask timerTask;

    private int MESSAGE_SUCCESS = 1;
    private long duration = 0;
    private String versionName = "";
    private int versionCode = 0;
    private String force = "";
    private String content = "";
    private String downloadUrl = "";
    private String audit = "true";
    private int advertising = 0;
    private String url = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        getData();
    }

    private void getData() {
        Call<UpdateBean> call = RetrofitUtils.getRetrofit().create(RegServer.class).getVersion("android");
        addCall(call);
        call.enqueue(new Callback<UpdateBean>() {
            @Override
            public void onResponse(Call<UpdateBean> call, Response<UpdateBean> response) {
                final UpdateBean body = response.body();
                if (body != null) {

                    if (body.getRspCode() == 0) {
                        versionName = body.getData().getVersionDisplay();
                        versionCode = body.getData().getVersion();
                        String versionServerName = MyApplication.getInstance().getDevice_verson_name();
                        int versionServerCode = Integer.parseInt(MyApplication.getInstance().getDevice_verson_code());
                        force = body.getData().getForce();
                        content = body.getData().getContent();
                        downloadUrl = body.getData().getUrl();
                        advertising = body.getData().getAdvertising();
//                        downloadUrl = "http://tkm.oss-cn-hangzhou.aliyuncs.com/app/app-release.apk";
                        audit = "" + body.getData().getAudit();
                        url = "http://www.3kmo.cn/app/candidate/appEnroll";
//                        audit = "false";
                        if (!versionName.equals(versionServerName) && versionCode != versionServerCode) {

                            CopyEaseDialog.AlertDialogUser alert = new CopyEaseDialog.AlertDialogUser() {
                                @Override
                                public void onResult(boolean confirmed, Bundle bundle) {
                                    if (confirmed) {
//                                    achievePix();
//                                    goToMain();
//                                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                                    startActivity(intent);
//                                    finish();

                                        String path = Environment.getExternalStorageDirectory() + File.separator + "YiDaoCompany/3kmo20171018";
                                        File file = new File(path);
                                        if(file.exists()){
                                            if (file.isFile()) { //该路径名表示的文件是否是一个标准文件
                                                file.delete(); //删除该文件
                                            } else if (file.isDirectory()) { //该路径名表示的文件是否是一个目录（文件夹）
                                                File[] files = file.listFiles(); //列出当前文件夹下的所有文件
                                                if(file != null){
                                                    for (File f : files) {
                                                        if(f.exists()){
                                                            f.delete();; //递归删除
                                                            //Log.d("fileName", f.getName()); //打印文件名
                                                        }

                                                    }
                                                }
                                            }
                                        }

                                        Intent intent = new Intent(SplashActivity.this, DownloadService.class);
                                        intent.putExtra("downloadUrl", downloadUrl);
                                        startService(intent);
                                        Toast.makeText(SplashActivity.this, "下载中，请稍后......", Toast.LENGTH_LONG).show();
                                    } else {
                                        achievePix();
                                        goToMain();
                                    }
                                }
                            };

                            if (Integer.parseInt(force) == 0) {
                                //不强制更新
                                new CopyEaseDialog(SplashActivity.this, "新版本更新提示", content, null, alert, true).show();
                            } else {
                                //强制更新
                                new CopyEaseDialog(SplashActivity.this, "新版本更新提示", content, null, alert, false).show();
                            }
                        } else {
                            achievePix();
                            goToMain();
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<UpdateBean> call, Throwable t) {
                Toast.makeText(SplashActivity.this,t.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void goToMain() {
//        image = (GifImageView) findViewById(R.id.image);
        company_logo = (ImageView) findViewById(R.id.company_logo);
        live_logo = (ImageView) findViewById(R.id.live_logo);
        skip = (ImageView) findViewById(R.id.skip);
        RelativeLayout.LayoutParams llp_skip = (RelativeLayout.LayoutParams)skip.getLayoutParams();
        llp_skip.width = CommonUtil.getRealWidth(82);
        llp_skip.height = llp_skip.width;
        llp_skip.topMargin = CommonUtil.getRealWidth(60);
        llp_skip.rightMargin = CommonUtil.getRealWidth(60);

        live_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(timer != null){
                    timer.cancel();
                    timer = null;
                }

                Intent intent = new Intent(SplashActivity.this,IndexActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("audit", audit);
                startActivity(intent);
                finish();
            }
        });

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this, IndexActivity.class);
                        intent.putExtra("audit", audit);
                        startActivity(intent);
                        finish();
                    }
                };
                if(advertising == 0){
                    skip.setVisibility(View.GONE);
                    company_logo.setVisibility(View.VISIBLE);
                    live_logo.setVisibility(View.GONE);
                    timer.schedule(timerTask, 1000, 1000);
                }else{
                    skip.setVisibility(View.VISIBLE);
                    company_logo.setVisibility(View.GONE);
                    live_logo.setVisibility(View.VISIBLE);
                    timer.schedule(timerTask, 2000, 6000);
                }


            }
        }, 1000);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(timer != null){
                    timer.cancel();
                    timer = null;
                }

                Intent intent = new Intent(SplashActivity.this, IndexActivity.class);
                intent.putExtra("audit", audit);
                startActivity(intent);
                finish();
            }
        });

    }


    Handler mHandler = new Handler() {
    };

    private void achievePix() {
        //获取手机的像素
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        CommonUtil.screenWidth = metrics.widthPixels;
        CommonUtil.screenHeight = metrics.heightPixels;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer != null){
            timer.cancel();
            timer = null;
            timerTask.cancel();
            timerTask = null;
        }
//        mHandler.removeMessages(MESSAGE_SUCCESS);
        SplashActivity.this.stopService(new
                Intent(SplashActivity.this,
                SplashActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}

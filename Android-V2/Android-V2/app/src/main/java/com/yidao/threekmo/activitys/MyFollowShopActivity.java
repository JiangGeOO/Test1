package com.yidao.threekmo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yidao.threekmo.R;
import com.yidao.threekmo.adapter.BaseRvAdapter;
import com.yidao.threekmo.adapter.MyFollowShopAdapter;
import com.yidao.threekmo.bean.ShopDetailResult;
import com.yidao.threekmo.bean.ShopListResult;
import com.yidao.threekmo.retrofit_server.SubjectServer;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.LoginUtils;
import com.yidao.threekmo.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFollowShopActivity extends BaseActivity implements View.OnClickListener{

    private List<ShopDetailResult> dataList;

    private RelativeLayout title_rela;
    private TextView title_text;
    private ImageView back;
    private XRecyclerView xrecyclerview;
    private MyFollowShopAdapter myFollowShopAdapter;

    private RelativeLayout isHave;
    private ImageView isHave_image;
    private TextView isHave_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);

        initViews();
        xrecyclerview.refresh();
    }

    private void initViews() {
        title_rela = (RelativeLayout) findViewById(R.id.title_rela);
        RelativeLayout.LayoutParams llp_title_rela = (RelativeLayout.LayoutParams)title_rela.getLayoutParams();
        llp_title_rela.height = CommonUtil.getRealWidth(130);

        title_text = (TextView) findViewById(R.id.title_text);
        title_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(36));
        RelativeLayout.LayoutParams llp_title_text = (RelativeLayout.LayoutParams)title_text.getLayoutParams();
        llp_title_text.topMargin = CommonUtil.getRealWidth(60);

        back = (ImageView) findViewById(R.id.back);
        RelativeLayout.LayoutParams llp_back = (RelativeLayout.LayoutParams)back.getLayoutParams();
        llp_back.width = CommonUtil.getRealWidth(52);
        llp_back.height = llp_back.width;
        llp_back.leftMargin = CommonUtil.getRealWidth(30);
        llp_back.topMargin = CommonUtil.getRealWidth(58);

        xrecyclerview = (XRecyclerView) findViewById(R.id.xrecyclerview);
        xrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        myFollowShopAdapter = new MyFollowShopAdapter(MyFollowShopActivity.this);
        dataList = myFollowShopAdapter.getDatas();
        xrecyclerview.setAdapter(myFollowShopAdapter);
        xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                achieveData();
            }

            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });
        myFollowShopAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void itemClickLIstener(View view, int position) {
                position --;
                Intent intent = new Intent(MyFollowShopActivity.this,SecondlyDetailActivity.class);
                intent.putExtra("id",dataList.get(position).getBeCollectId());
                startActivity(intent);
            }
        });


        isHave = (RelativeLayout) findViewById(R.id.isHave);
        isHave_image = (ImageView) findViewById(R.id.isHave_image);
        RelativeLayout.LayoutParams llp_isHave_image = (RelativeLayout.LayoutParams)isHave_image.getLayoutParams();
        llp_isHave_image.width = CommonUtil.getRealWidth(284);
        llp_isHave_image.height = llp_isHave_image.width;
        isHave_text = (TextView) findViewById(R.id.isHave_text);
        isHave_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_isHave_text = (RelativeLayout.LayoutParams)isHave_text.getLayoutParams();
        llp_isHave_text.topMargin = CommonUtil.getRealWidth(40);

        back.setOnClickListener(this);
    }

    private void achieveData(){
        Call<ShopListResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).myCollect(LoginUtils.getToken(MyFollowShopActivity.this),"subject_train",0,15);
        addCall(call);
        call.enqueue(new Callback<ShopListResult>() {
            @Override
            public void onResponse(Call<ShopListResult> call, Response<ShopListResult> response) {
                ShopListResult body = response.body();
                if(body != null){
                    if(body.getRspCode() == 0){
                        dataList = body.getData().getDatas();
                        if(dataList == null){
                            dataList = new ArrayList<ShopDetailResult>();
                            isHave.setVisibility(View.VISIBLE);
                        }else{
                            isHave.setVisibility(View.GONE);
                        }
                        myFollowShopAdapter.dataUpdate(dataList);
                    }
                }

                xrecyclerview.refreshComplete();
            }

            @Override
            public void onFailure(Call<ShopListResult> call, Throwable t) {

            }
        });
    }

    private void loadMoreData(){
        Call<ShopListResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).myCollect(LoginUtils.getToken(MyFollowShopActivity.this),"subject_train",dataList.size(),15);
        addCall(call);
        call.enqueue(new Callback<ShopListResult>() {
            @Override
            public void onResponse(Call<ShopListResult> call, Response<ShopListResult> response) {
                ShopListResult body = response.body();
                if(body.getRspCode() == 0){
                    List<ShopDetailResult> dataList = body.getData().getDatas();
                    myFollowShopAdapter.addData(dataList);
                }
                xrecyclerview.refreshComplete();
            }

            @Override
            public void onFailure(Call<ShopListResult> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:{
                finish();
            }
            break;
        }
    }
}

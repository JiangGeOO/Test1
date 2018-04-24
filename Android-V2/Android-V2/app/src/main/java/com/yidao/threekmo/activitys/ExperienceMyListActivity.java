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
import com.yidao.threekmo.adapter.NewExperienceFragmentAdapter;
import com.yidao.threekmo.bean.ExperienceListResult;
import com.yidao.threekmo.bean.ExperienceResult;
import com.yidao.threekmo.customview.CopyEaseDialog;
import com.yidao.threekmo.retrofit_server.SubjectServer;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.LoginUtils;
import com.yidao.threekmo.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExperienceMyListActivity extends BaseActivity implements View.OnClickListener {

    private List<ExperienceResult> dataList;

    private RelativeLayout title_rela;
    private TextView title_text;
    private ImageView title_back;

    private RelativeLayout isHave;
    private ImageView isHave_image;
    private TextView isHave_text;

    private XRecyclerView recyclerview;
    private NewExperienceFragmentAdapter newExperienceFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_my_list);

        initViews();
        recyclerview.refresh();

    }

    private void initViews() {
        title_rela = (RelativeLayout) findViewById(R.id.title_rela);
        RelativeLayout.LayoutParams llp_title_rela = (RelativeLayout.LayoutParams)title_rela.getLayoutParams();
        llp_title_rela.height = CommonUtil.getRealWidth(130);

        title_text = (TextView) findViewById(R.id.title_text);
        title_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(36));
        RelativeLayout.LayoutParams llp_title_text = (RelativeLayout.LayoutParams)title_text.getLayoutParams();
        llp_title_text.topMargin = CommonUtil.getRealWidth(60);

        title_back = (ImageView) findViewById(R.id.title_back);
        RelativeLayout.LayoutParams llp_title_back = (RelativeLayout.LayoutParams)title_back.getLayoutParams();
        llp_title_back.width = CommonUtil.getRealWidth(52);
        llp_title_back.height = llp_title_back.width;
        llp_title_back.topMargin = CommonUtil.getRealWidth(58);
        llp_title_back.leftMargin = CommonUtil.getRealWidth(30);

        recyclerview = (XRecyclerView)findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(ExperienceMyListActivity.this));
        newExperienceFragmentAdapter = new NewExperienceFragmentAdapter(ExperienceMyListActivity.this,2);
        dataList = newExperienceFragmentAdapter.getDatas();
        recyclerview.setAdapter(newExperienceFragmentAdapter);
        recyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                achieveData();
            }

            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });
        newExperienceFragmentAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void itemClickLIstener(View view, int position) {
                position --;
                if(dataList.get(position).getSoldOut() == 0){
                    new CopyEaseDialog(ExperienceMyListActivity.this,"该商品已下架！",null,null,null,false).show();
                }else{
                    Intent intent = new Intent(ExperienceMyListActivity.this, ExperienceDetailActivity.class);
                    intent.putExtra("experId",dataList.get(position).getBeCollectId());
                    startActivity(intent);
                }

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

        title_back.setOnClickListener(this);

    }


    private void achieveData(){
        Call<ExperienceListResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).myExperience(LoginUtils.getToken(ExperienceMyListActivity.this),"experience",0,15);
        addCall(call);
        call.enqueue(new Callback<ExperienceListResult>() {
            @Override
            public void onResponse(Call<ExperienceListResult> call, Response<ExperienceListResult> response) {
                ExperienceListResult body = response.body();
                if(body != null){
                    if(body.getRspCode() == 0){
                        dataList = body.getData().getDatas();
                        if(dataList == null){
                            dataList = new ArrayList<ExperienceResult>();
                            isHave.setVisibility(View.VISIBLE);
                        }else{
                            isHave.setVisibility(View.GONE);
                        }
                        newExperienceFragmentAdapter.dataUpdate(dataList);
                    }
                }

                recyclerview.refreshComplete();
            }

            @Override
            public void onFailure(Call<ExperienceListResult> call, Throwable t) {

            }
        });
    }

    private void loadMoreData(){
        Call<ExperienceListResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).myExperience(LoginUtils.getToken(ExperienceMyListActivity.this),"experience",dataList.size(),15);
        addCall(call);
        call.enqueue(new Callback<ExperienceListResult>() {
            @Override
            public void onResponse(Call<ExperienceListResult> call, Response<ExperienceListResult> response) {
                ExperienceListResult body = response.body();
                if(body.getRspCode() == 0){
                    List<ExperienceResult> dataList = body.getData().getDatas();
                    newExperienceFragmentAdapter.addData(dataList);
                }
                recyclerview.refreshComplete();
            }

            @Override
            public void onFailure(Call<ExperienceListResult> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.title_back:{
                finish();
            }
            break;

            default:
                break;
        }
    }
}

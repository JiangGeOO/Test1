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
import com.yidao.myutils.toast.ToastUtil;
import com.yidao.threekmo.R;
import com.yidao.threekmo.adapter.BaseRvAdapter;
import com.yidao.threekmo.adapter.MyActiveListAdapter;
import com.yidao.threekmo.bean.FirstListResult;
import com.yidao.threekmo.bean.MainHomeListItem;
import com.yidao.threekmo.parameter.WebParameters;
import com.yidao.threekmo.retrofit_server.SubjectServer;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.LoginUtils;
import com.yidao.threekmo.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyActiveListActivity extends BaseActivity {

    private RelativeLayout active_rela;
    private ImageView active_back;
    private TextView active_text;
    private XRecyclerView recyclerView;
    private MyActiveListAdapter myActiveListAdapter;
    private List<MainHomeListItem> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_active_list);

        initViews();
        recyclerView.refresh();

    }

    private void initViews() {
        active_rela = (RelativeLayout) findViewById(R.id.active_rela);
        RelativeLayout.LayoutParams llp_active_rela = (RelativeLayout.LayoutParams)active_rela.getLayoutParams();
        llp_active_rela.height = CommonUtil.getRealWidth(128);

        active_back = (ImageView) findViewById(R.id.active_back);
        RelativeLayout.LayoutParams llp_active_back = (RelativeLayout.LayoutParams)active_back.getLayoutParams();
        llp_active_back.width = CommonUtil.getRealWidth(52);
        llp_active_back.height = llp_active_back.width;
        llp_active_back.leftMargin = CommonUtil.getRealWidth(30);
        llp_active_back.topMargin = CommonUtil.getRealWidth(62);

        active_text = (TextView) findViewById(R.id.active_text);
        active_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(36));
        RelativeLayout.LayoutParams llp_active_text = (RelativeLayout.LayoutParams)active_text.getLayoutParams();
        llp_active_text.topMargin = CommonUtil.getRealWidth(63);

        active_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        recyclerView = (XRecyclerView) findViewById(R.id.recyclerview);
        RelativeLayout.LayoutParams llp_recyclerView = (RelativeLayout.LayoutParams)recyclerView.getLayoutParams();
        llp_recyclerView.topMargin = CommonUtil.getRealWidth(20);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyActiveListActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        myActiveListAdapter = new MyActiveListAdapter(MyActiveListActivity.this);
        recyclerView.setAdapter(myActiveListAdapter);
        dataList = myActiveListAdapter.getDatas();
        myActiveListAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void itemClickLIstener(View view, int position) {
                //由于使用了三方，需要-1
                if (position < 1 || position > dataList.size()) return;
                MainHomeListItem mainHomeListItem = dataList.get(position -1);
                if (mainHomeListItem.getType() == 0) {
                    long id = mainHomeListItem.getId();
                    //活动走的activity
                    Intent intent = new Intent(MyActiveListActivity.this, ActiveActivity.class);
                    intent.putExtra("id", id);
                    startActivityForResult(intent, 101);
                } else {
                    String url = mainHomeListItem.getActivityUrl();
                    String name = mainHomeListItem.getName();
                    //活动走的activity
                    Intent intent = new Intent(MyActiveListActivity.this, WebViewScriptActivity.class);
                    intent.putExtra("url", url);
                    intent.putExtra("title", name);
                    startActivityForResult(intent, 101);
                }

            }
        });

        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshDate();
            }

            @Override
            public void onLoadMore() {
                getDates();
            }
        });
    }

    private void refreshDate() {
        SubjectServer subjectServer = RetrofitUtils.getRetrofit().create(SubjectServer.class);
        Call<FirstListResult> firstlySubject = subjectServer.getMyActiveList(LoginUtils.getToken(MyActiveListActivity.this) + "",0, 0, WebParameters.PAGE_SIZE);
        addCall(firstlySubject);
        firstlySubject.enqueue(new Callback<FirstListResult>() {
            @Override
            public void onResponse(Call<FirstListResult> call, Response<FirstListResult> response) {
                FirstListResult body = response.body();
                String token = LoginUtils.getToken(MyActiveListActivity.this) + "";
                if(body != null){
                    if (body.getRspCode() == 0) {
                        List<MainHomeListItem> datas = body.getData().getDatas();
                        if(datas == null){
                            datas = new ArrayList<MainHomeListItem>();
                        }
                        myActiveListAdapter.dataUpdate(datas);
                    } else {
                        ToastUtil.showToast(body.getRspMsg(), false, MyActiveListActivity.this);
                    }
                }

                recyclerView.refreshComplete();
            }

            @Override
            public void onFailure(Call<FirstListResult> call, Throwable t) {
                ToastUtil.showToast(t.getMessage(), false, MyActiveListActivity.this);
                recyclerView.refreshComplete();
            }
        });
    }

    private void getDates() {
        SubjectServer subjectServer = RetrofitUtils.getRetrofit().create(SubjectServer.class);
        Call<FirstListResult> firstlySubject = subjectServer.getMyActiveList(LoginUtils.getToken(MyActiveListActivity.this) + "",0, dataList.size(), WebParameters.PAGE_SIZE);
        addCall(firstlySubject);
        firstlySubject.enqueue(new Callback<FirstListResult>() {
            @Override
            public void onResponse(Call<FirstListResult> call, Response<FirstListResult> response) {
                FirstListResult body = response.body();
                if (body.getRspCode() == 0) {
                    List<MainHomeListItem> datas = body.getData().getDatas();
                    myActiveListAdapter.addData(datas);
                } else {
                    ToastUtil.showToast(body.getRspMsg(), false, MyActiveListActivity.this);
                }
                recyclerView.loadMoreComplete();
            }

            @Override
            public void onFailure(Call<FirstListResult> call, Throwable t) {
                ToastUtil.showToast(t.getMessage(), false, MyActiveListActivity.this);
                recyclerView.loadMoreComplete();
            }
        });
    }
}

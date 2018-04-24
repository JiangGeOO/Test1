package com.yidao.threekmo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yidao.threekmo.R;
import com.yidao.threekmo.adapter.BaseRvAdapter;
import com.yidao.threekmo.adapter.OrderMySelfAdapter;
import com.yidao.threekmo.bean.OrderDetailResult;
import com.yidao.threekmo.bean.OrderMySelfResult;
import com.yidao.threekmo.retrofit_server.SubjectServer;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.LoginUtils;
import com.yidao.threekmo.utils.RetrofitUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderMySelfActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout title_rela;
    private ImageView back;
    private TextView my_order;
    private XRecyclerView xrecyclerview;
    private OrderMySelfAdapter orderMySelfAdapter;
    private List<OrderDetailResult> orderList;
    private List<OrderDetailResult> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_my_self);

        initViews();
        xrecyclerview.refresh();

    }

    private void initViews() {
        title_rela = (RelativeLayout) findViewById(R.id.title_rela);
        RelativeLayout.LayoutParams llp_title_rela = (RelativeLayout.LayoutParams)title_rela.getLayoutParams();
        llp_title_rela.height = CommonUtil.getRealWidth(130);

        back = (ImageView) findViewById(R.id.back);
        RelativeLayout.LayoutParams llp_back = (RelativeLayout.LayoutParams)back.getLayoutParams();
        llp_back.width = CommonUtil.getRealWidth(52);
        llp_back.height = llp_back.width;
        llp_back.topMargin = CommonUtil.getRealWidth(58);
        llp_back.leftMargin = CommonUtil.getRealWidth(30);

        my_order = (TextView) findViewById(R.id.my_order);
        my_order.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(36));
        RelativeLayout.LayoutParams llp_my_order = (RelativeLayout.LayoutParams)my_order.getLayoutParams();
        llp_my_order.topMargin = CommonUtil.getRealWidth(60);

        xrecyclerview = (XRecyclerView) findViewById(R.id.xrecyclerview);
        xrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        orderMySelfAdapter = new OrderMySelfAdapter(OrderMySelfActivity.this);
        xrecyclerview.setAdapter(orderMySelfAdapter);
        dataList = orderMySelfAdapter.getDatas();
        xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                achieveData();
            }

            @Override
            public void onLoadMore() {
                loadMordData();
            }
        });

        orderMySelfAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void itemClickLIstener(View view, int position) {
                position --;
                Intent intent = new Intent(OrderMySelfActivity.this,OrderDetailMySelfActivity.class);
                intent.putExtra("orderId",orderList.get(position).getId());
                startActivity(intent);

            }
        });

        back.setOnClickListener(this);

    }

    private void achieveData() {
        Call<OrderMySelfResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).myOderList(LoginUtils.getToken(this),0,15);
        addCall(call);
        call.enqueue(new Callback<OrderMySelfResult>() {
            @Override
            public void onResponse(Call<OrderMySelfResult> call, Response<OrderMySelfResult> response) {
                OrderMySelfResult body = response.body();
                if(body.getRspCode() == 0){
                    orderList = body.getData().getDatas();
                    orderMySelfAdapter.dataUpdate(orderList);

                }
                xrecyclerview.refreshComplete();

            }

            @Override
            public void onFailure(Call<OrderMySelfResult> call, Throwable t) {
                Toast.makeText(OrderMySelfActivity.this,t.getMessage().toString(),Toast.LENGTH_SHORT).show();
                Log.e("&&&&&&&&&&&&&&&&&",t.getMessage().toString());
            }
        });
    }

    private void loadMordData() {
        int num = dataList.size();
        Call<OrderMySelfResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).myOderList(LoginUtils.getToken(this),dataList.size(),15);
        addCall(call);
        call.enqueue(new Callback<OrderMySelfResult>() {
            @Override
            public void onResponse(Call<OrderMySelfResult> call, Response<OrderMySelfResult> response) {
                OrderMySelfResult body = response.body();
                if(body != null){
                    if(body.getRspCode() == 0){
                        List<OrderDetailResult> orderList = body.getData().getDatas();
                        orderMySelfAdapter.addData(orderList);

                    }
                }

                xrecyclerview.loadMoreComplete();
            }

            @Override
            public void onFailure(Call<OrderMySelfResult> call, Throwable t) {
                Toast.makeText(OrderMySelfActivity.this,t.getMessage().toString(),Toast.LENGTH_SHORT).show();
                Log.e("&&&&&&&&&&&&&&&&&",t.getMessage().toString());
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

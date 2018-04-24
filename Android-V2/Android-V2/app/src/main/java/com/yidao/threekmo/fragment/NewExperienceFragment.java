package com.yidao.threekmo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yidao.threekmo.R;
import com.yidao.threekmo.activitys.ExperienceDetailActivity;
import com.yidao.threekmo.activitys.IndexActivity;
import com.yidao.threekmo.activitys.WebViewScriptActivity;
import com.yidao.threekmo.adapter.BaseRvAdapter;
import com.yidao.threekmo.adapter.NewExperienceFragmentAdapter;
import com.yidao.threekmo.application.MyApplication;
import com.yidao.threekmo.bean.ExperienceListResult;
import com.yidao.threekmo.bean.ExperienceResult;
import com.yidao.threekmo.retrofit_server.SubjectServer;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.RetrofitUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Smart~ on 2017/11/14.
 */

public class NewExperienceFragment extends BaseFragment {

    private List<ExperienceResult> dataList;

    private RelativeLayout title_rela;
    private TextView title_text;
    private XRecyclerView recyclerview;
    private NewExperienceFragmentAdapter newExperienceFragmentAdapter;

    public static final NewExperienceFragment newInstance(String title) {
        NewExperienceFragment f = new NewExperienceFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(title, title);
        f.setArguments(bdl);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_experience, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();
        recyclerview.refresh();
    }

    private void initViews() {
        title_rela = (RelativeLayout) getView().findViewById(R.id.title_rela);
        RelativeLayout.LayoutParams llp_title_rela = (RelativeLayout.LayoutParams) title_rela.getLayoutParams();
        llp_title_rela.height = CommonUtil.getRealWidth(130);

        title_text = (TextView) getView().findViewById(R.id.title_text);
        title_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(36));
        RelativeLayout.LayoutParams llp_title_text = (RelativeLayout.LayoutParams) title_text.getLayoutParams();
        llp_title_text.topMargin = CommonUtil.getRealWidth(58);

        recyclerview = (XRecyclerView) getView().findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        newExperienceFragmentAdapter = new NewExperienceFragmentAdapter(getActivity(),1);
        dataList = newExperienceFragmentAdapter.getDatas();
        recyclerview.setAdapter(newExperienceFragmentAdapter);
        recyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshData();
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
                if(dataList.get(position).getIsPlane() == 1){
                    Intent intent = new Intent(getActivity(), ExperienceDetailActivity.class);
                    intent.putExtra("experId",dataList.get(position).getId());
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getActivity(), WebViewScriptActivity.class);
                    intent.putExtra("url",dataList.get(position).getJumpUrl());
                    startActivity(intent);
                }
            }
        });

    }

    private void refreshData(){
        Call<ExperienceListResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).experienceList(0,15, MyApplication.getInstance().getLongitude() + "",
                MyApplication.getInstance().getLatitude() + "");
        ((IndexActivity)getActivity()).addCall(call);
        call.enqueue(new Callback<ExperienceListResult>() {
            @Override
            public void onResponse(Call<ExperienceListResult> call, Response<ExperienceListResult> response) {
                ExperienceListResult body = response.body();
                if(body != null){
                    if(body.getRspCode() == 0){
                        dataList = body.getData().getDatas();
                        newExperienceFragmentAdapter.dataUpdate(dataList);
                        recyclerview.refreshComplete();
                    }
                }

            }

            @Override
            public void onFailure(Call<ExperienceListResult> call, Throwable t) {
                Log.e("####################",t.getMessage().toString());
            }
        });
    }

    private void loadMoreData(){
        Call<ExperienceListResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).experienceList(dataList.size(),15, MyApplication.getInstance().getLongitude() + "",
                MyApplication.getInstance().getLatitude() + "");
        ((IndexActivity)getActivity()).addCall(call);
        call.enqueue(new Callback<ExperienceListResult>() {
            @Override
            public void onResponse(Call<ExperienceListResult> call, Response<ExperienceListResult> response) {
                ExperienceListResult body = response == null ? null : response.body();
                if (body == null || body.getData() == null) return;
                if(body.getRspCode() == 0){
                    List<ExperienceResult> dataList = body.getData().getDatas();
                    if (dataList == null) return;
                    newExperienceFragmentAdapter.addData(dataList);
                    recyclerview.loadMoreComplete();
                }
            }

            @Override
            public void onFailure(Call<ExperienceListResult> call, Throwable t) {

            }
        });
    }
}

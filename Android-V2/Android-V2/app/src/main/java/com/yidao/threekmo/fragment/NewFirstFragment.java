package com.yidao.threekmo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yidao.myutils.toast.ToastUtil;
import com.yidao.threekmo.R;
import com.yidao.threekmo.activitys.ActiveActivity;
import com.yidao.threekmo.activitys.ActiveSearchActivity;
import com.yidao.threekmo.activitys.CityMapActivity;
import com.yidao.threekmo.activitys.IndexActivity;
import com.yidao.threekmo.activitys.WebViewScriptActivity;
import com.yidao.threekmo.adapter.BaseRecyclerAdapter;
import com.yidao.threekmo.adapter.BaseRvAdapter;
import com.yidao.threekmo.adapter.NewFirstFragmentAdapter;
import com.yidao.threekmo.adapter.NewFirstFragmentHeaderAdapter;
import com.yidao.threekmo.application.MyApplication;
import com.yidao.threekmo.bean.AchieveBinnerResult;
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

/**
 * Created by Smart~ on 2017/11/8.
 */

public class NewFirstFragment extends BaseFragment implements View.OnClickListener{

    private RelativeLayout title_rela;
    private ImageView title_img;
    private TextView title_loc;
    private TextView title_search;
    private ImageView title_searchimg;
    private XRecyclerView xrecyclerview;

    private NewFirstFragmentAdapter newFirstFragmentAdapter;
    private List<MainHomeListItem> dataList;

    //头布局
    private RelativeLayout recyclerview_rela;
    private RecyclerView recyclerview;
    private NewFirstFragmentHeaderAdapter newFirstFragmentHeaderAdapter;
    private List<AchieveBinnerResult.DataBean> bannerList;

    public static final NewFirstFragment newInstance(String title) {
        NewFirstFragment f = new NewFirstFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(title, title);
        f.setArguments(bdl);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_first, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();
        xrecyclerview.refresh();
        getUrl();
    }


    private void initViews() {
        title_rela = (RelativeLayout) getView().findViewById(R.id.title_rela);
        RelativeLayout.LayoutParams llp_title_rela = (RelativeLayout.LayoutParams)title_rela.getLayoutParams();
        llp_title_rela.height = CommonUtil.getRealWidth(130);

        title_img = (ImageView) getView().findViewById(R.id.title_img);
        RelativeLayout.LayoutParams llp_title_img = (RelativeLayout.LayoutParams)title_img.getLayoutParams();
        llp_title_img.width = CommonUtil.getRealWidth(35);
        llp_title_img.height = llp_title_img.width;
        llp_title_img.leftMargin = CommonUtil.getRealWidth(30);
        llp_title_img.topMargin = CommonUtil.getRealWidth(66);

        title_loc = (TextView) getView().findViewById(R.id.title_loc);
        title_loc.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(36));
        RelativeLayout.LayoutParams llp_title_loc = (RelativeLayout.LayoutParams)title_loc.getLayoutParams();
        llp_title_loc.leftMargin = CommonUtil.getRealWidth(78);
        llp_title_loc.topMargin = CommonUtil.getRealWidth(60);

        title_search = (TextView) getView().findViewById(R.id.title_search);
        title_search.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
        title_search.setPadding(CommonUtil.getRealWidth(76),0,0,0);
        title_search.setGravity(Gravity.CENTER_VERTICAL);
        RelativeLayout.LayoutParams llp_title_search = (RelativeLayout.LayoutParams)title_search.getLayoutParams();
        llp_title_search.width = CommonUtil.getRealWidth(538);
        llp_title_search.height = CommonUtil.getRealWidth(60);
        llp_title_search.leftMargin = CommonUtil.getRealWidth(182);
        llp_title_search.topMargin = CommonUtil.getRealWidth(54);

        title_searchimg = (ImageView) getView().findViewById(R.id.title_searchimg);
        RelativeLayout.LayoutParams llp_title_searchimg = (RelativeLayout.LayoutParams)title_searchimg.getLayoutParams();
        llp_title_searchimg.width = CommonUtil.getRealWidth(28);
        llp_title_searchimg.height = CommonUtil.getRealWidth(30);
        llp_title_searchimg.leftMargin = CommonUtil.getRealWidth(218);
        llp_title_searchimg.topMargin = CommonUtil.getRealWidth(70);

        xrecyclerview = (XRecyclerView) getView().findViewById(R.id.xrecyclerview);
        xrecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        newFirstFragmentAdapter = new NewFirstFragmentAdapter(getActivity());
        xrecyclerview.setAdapter(newFirstFragmentAdapter);
        dataList = newFirstFragmentAdapter.getDatas();
        setHeader(xrecyclerview);
        xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
//                xrecyclerview.refreshComplete();;
                refreshDate("","","","",0,0);
            }

            @Override
            public void onLoadMore() {
//                xrecyclerview.loadMoreComplete();
                getDates("","","","",0,0);
            }
        });
        newFirstFragmentAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                position --;
                MainHomeListItem mainHomeListItem = dataList.get(position);
                if (mainHomeListItem.getType() == 0) {
                    long id = mainHomeListItem.getId();
                    //活动走的activity
                    Intent intent = new Intent(getActivity(), ActiveActivity.class);
                    intent.putExtra("id", id);
                    startActivityForResult(intent, 101);
                } else {
                    String url = mainHomeListItem.getActivityUrl();
                    String name = mainHomeListItem.getName();
                    //活动走的activity
                    Intent intent = new Intent(getActivity(), WebViewScriptActivity.class);
                    intent.putExtra("url", url);
                    intent.putExtra("title", name);
                    startActivityForResult(intent, 101);
                }
            }
        });

        title_search.setOnClickListener(this);
        title_img.setOnClickListener(this);
        title_loc.setOnClickListener(this);

    }

    private void setHeader(RecyclerView view) {
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.header_new_first_fragment, view, false);

        recyclerview_rela = (RelativeLayout) header.findViewById(R.id.recyclerview_rela);
        LinearLayout.LayoutParams llp_recyclerview_rela = (LinearLayout.LayoutParams)recyclerview_rela.getLayoutParams();
        llp_recyclerview_rela.height = CommonUtil.getRealWidth(330);

        recyclerview = (RecyclerView) header.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        newFirstFragmentHeaderAdapter = new NewFirstFragmentHeaderAdapter(getActivity());
        recyclerview.setAdapter(newFirstFragmentHeaderAdapter);
        newFirstFragmentAdapter.setHeaderView(header);
        newFirstFragmentHeaderAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void itemClickLIstener(View view, int position) {
                if(bannerList.get(position).getUrl().contains("token=")){
                    if(LoginUtils.isLogin(true,getActivity())){
                        if(!TextUtils.isEmpty(bannerList.get(position).getUrl())){
                            String url = bannerList.get(position).getUrl() + LoginUtils.getToken(getActivity());
                            Intent intent = new Intent(getActivity(),WebViewScriptActivity.class);
                            intent.putExtra("url",url);
                            startActivity(intent);
                        }
                    }
                }else{
                    if(!TextUtils.isEmpty(bannerList.get(position).getUrl())){
                        String url = bannerList.get(position).getUrl();
                        Intent intent = new Intent(getActivity(),WebViewScriptActivity.class);
                        intent.putExtra("url",url);
                        startActivity(intent);
                    }
                }

            }
        });
    }

    private void refreshDate(String cityName, String acName, String startDate, String endDate, long distance, long sortRule) {
        SubjectServer subjectServer = RetrofitUtils.getRetrofit().create(SubjectServer.class);
        Call<FirstListResult> firstlySubject = subjectServer.getActivityList("0","", acName, startDate, endDate, distance, sortRule,
                MyApplication.getInstance().getLongitude() + "", MyApplication.getInstance().getLatitude() + "", 0, WebParameters.PAGE_SIZE);
        ((IndexActivity) getActivity()).addCall(firstlySubject);
        firstlySubject.enqueue(new Callback<FirstListResult>() {
            @Override
            public void onResponse(Call<FirstListResult> call, Response<FirstListResult> response) {
                FirstListResult body = response == null ? null : response.body();
                if (body == null || body.getData() == null || getActivity() == null) return;
                if (body.getRspCode() == 0) {
                    List<MainHomeListItem> datas = body.getData().getDatas();
                    if (datas == null) {
                        datas = new ArrayList<MainHomeListItem>();
                    }
                    newFirstFragmentAdapter.setDatas((ArrayList<MainHomeListItem>) datas);
                } else {
                    ToastUtil.showToast(body.getRspMsg(), false, getActivity());
                }
                xrecyclerview.refreshComplete();
            }

            @Override
            public void onFailure(Call<FirstListResult> call, Throwable t) {
                ToastUtil.showToast(t.getMessage(), false, getActivity());
                xrecyclerview.refreshComplete();
            }
        });
    }

    private void getDates(String cityName, String acName, String startDate, String endDate, long distance, long sortRule) {
        SubjectServer subjectServer = RetrofitUtils.getRetrofit().create(SubjectServer.class);
        Call<FirstListResult> firstlySubject = subjectServer.getActivityList("0","", acName, startDate, endDate, distance, sortRule,
                MyApplication.getInstance().getLongitude() + "", MyApplication.getInstance().getLatitude() + "", dataList.size(), WebParameters.PAGE_SIZE);
        ((IndexActivity) getActivity()).addCall(firstlySubject);
        firstlySubject.enqueue(new Callback<FirstListResult>() {
            @Override
            public void onResponse(Call<FirstListResult> call, Response<FirstListResult> response) {
                FirstListResult body = response.body();
                if (body.getRspCode() == 0) {
                    List<MainHomeListItem> datas = body.getData().getDatas();
                    newFirstFragmentAdapter.addDatas((ArrayList<MainHomeListItem>) datas);
                } else {
                    ToastUtil.showToast(body.getRspMsg(), false, getActivity());
                }
                xrecyclerview.loadMoreComplete();
            }

            @Override
            public void onFailure(Call<FirstListResult> call, Throwable t) {
                ToastUtil.showToast(t.getMessage(), false, getActivity());
                xrecyclerview.loadMoreComplete();
            }
        });
    }

    private void getUrl(){
        Call<AchieveBinnerResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).getBinnerImage(LoginUtils.getToken(getActivity()));
        ((IndexActivity)getActivity()).addCall(call);
        call.enqueue(new Callback<AchieveBinnerResult>() {
            @Override
            public void onResponse(Call<AchieveBinnerResult> call, Response<AchieveBinnerResult> response) {
                AchieveBinnerResult body = response.body();
                if(body != null){
                    if(body.getRspCode() == 0){
                        bannerList = body.getData();
                        if(bannerList == null){
                            bannerList = new ArrayList<AchieveBinnerResult.DataBean>();
                        }
                        newFirstFragmentHeaderAdapter.dataUpdate(bannerList);
                    }
                }

            }

            @Override
            public void onFailure(Call<AchieveBinnerResult> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_search:{
                Intent intent = new Intent(getActivity(), ActiveSearchActivity.class);
                startActivity(intent);
//                new IndexEaseDialog(getActivity(),null,null,null,0,0,null,false).show();
            }
            break;

            case R.id.title_img:{
                Intent intent = new Intent(getActivity(), CityMapActivity.class);
                startActivity(intent);
            }
            break;

            case R.id.title_loc:{
                Intent intent = new Intent(getActivity(), CityMapActivity.class);
                startActivity(intent);
            }
            break;

            default:
                break;
        }
    }
}

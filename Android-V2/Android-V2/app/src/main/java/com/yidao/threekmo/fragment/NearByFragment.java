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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yidao.myutils.toast.ToastUtil;
import com.yidao.threekmo.R;
import com.yidao.threekmo.activitys.AscriptionSubActivity;
import com.yidao.threekmo.activitys.IndexActivity;
import com.yidao.threekmo.activitys.MapActivity;
import com.yidao.threekmo.activitys.SearchActivity;
import com.yidao.threekmo.activitys.SecondaryListActivity;
import com.yidao.threekmo.adapter.BaseRvAdapter;
import com.yidao.threekmo.adapter.NearByAdapter;
import com.yidao.threekmo.application.MyApplication;
import com.yidao.threekmo.bean.FirstListResult;
import com.yidao.threekmo.bean.MainHomeListItem;
import com.yidao.threekmo.parameter.WebParameters;
import com.yidao.threekmo.retrofit_server.SubjectServer;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.RetrofitUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Smart~ on 2017/10/10.
 */

public class NearByFragment extends BaseFragment implements View.OnClickListener {

    private XRecyclerView recyclerView;
    private NearByAdapter nearByAdapter;
    private List<MainHomeListItem> dataList;
    private RelativeLayout toolbar;
    private RelativeLayout search_rela;
    private ImageView location;
    private TextView location_text;
    private TextView search;
    private ImageView map;

    public static final NearByFragment newInstance(String title) {
        NearByFragment f = new NearByFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(title, title);
        f.setArguments(bdl);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nearby, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initAddress();
        iniViews();
        recyclerView.refresh();

    }

    private void initAddress() {
        GeocodeSearch geocodeSearch = new GeocodeSearch(getActivity());
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int code) {
                if (regeocodeResult == null || regeocodeResult.getRegeocodeAddress() == null) return;
                String formatAddress = regeocodeResult.getRegeocodeAddress().getFormatAddress();
                int index = 0;
                String address = formatAddress;
                int len = formatAddress.length();
                for (int i = 0; i < len; i++) {
                    char item = formatAddress.charAt(i);
                    String a = String.valueOf(item);
                    if ("市".equals(a)) {
                        index = i + 1;
                        address = formatAddress.substring(index, len - 1);
                        break;
                    }
                }
                location_text.setText(address);
                Log.e("formatAddress", "formatAddress:" + formatAddress);
                Log.e("formatAddress", "rCode:" + code);
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });

        LatLonPoint lp = new LatLonPoint(MyApplication.getInstance().getLatitude(), MyApplication.getInstance().getLongitude());
        RegeocodeQuery query = new RegeocodeQuery(lp, 200, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(query);
    }

    @Override
    public void onResume() {
        super.onResume();
//        recyclerView.refresh();
    }

    private void iniViews() {
        toolbar = (RelativeLayout) getView().findViewById(R.id.toolbar);

        search_rela = (RelativeLayout) getView().findViewById(R.id.search_rela);
        RelativeLayout.LayoutParams tlpSearch_rela = (RelativeLayout.LayoutParams) search_rela.getLayoutParams();
        tlpSearch_rela.height = CommonUtil.getRealWidth(130);

        location = (ImageView) getView().findViewById(R.id.location);
        RelativeLayout.LayoutParams llpLocation = (RelativeLayout.LayoutParams) location.getLayoutParams();
        llpLocation.width = CommonUtil.getRealWidth(35);
        llpLocation.height = llpLocation.width;
        llpLocation.leftMargin = CommonUtil.getRealWidth(30);
        llpLocation.topMargin = CommonUtil.getRealWidth(66);

        location_text = (TextView) getView().findViewById(R.id.location_text);
        location_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(36));
        RelativeLayout.LayoutParams llpLocation_text = (RelativeLayout.LayoutParams) location_text.getLayoutParams();
        llpLocation_text.width = CommonUtil.getRealWidth(188);
        llpLocation_text.leftMargin = CommonUtil.getRealWidth(8);
        llpLocation_text.topMargin = CommonUtil.getRealWidth(60);

        search = (TextView) getView().findViewById(R.id.search);
        search.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        search.setPadding(CommonUtil.getRealWidth(20), 0, 0, 0);
        RelativeLayout.LayoutParams llpSearch = (RelativeLayout.LayoutParams) search.getLayoutParams();
//        llpSearch.width = CommonUtil.getRealWidth(380);
        llpSearch.height = CommonUtil.getRealWidth(60);
        llpSearch.leftMargin = CommonUtil.getRealWidth(28);
        llpSearch.rightMargin = CommonUtil.getRealWidth(28);
        llpSearch.topMargin = CommonUtil.getRealWidth(54);

        map = (ImageView) getView().findViewById(R.id.map);
        RelativeLayout.LayoutParams llpMap = (RelativeLayout.LayoutParams) map.getLayoutParams();
        llpMap.width = CommonUtil.getRealWidth(44);
        llpMap.height = llpMap.width;
        llpMap.rightMargin = CommonUtil.getRealWidth(30);
        llpMap.topMargin = CommonUtil.getRealWidth(60);

        location.setOnClickListener(this);
        location_text.setOnClickListener(this);
        map.setOnClickListener(this);
        search.setOnClickListener(this);

        recyclerView = (XRecyclerView) getView().findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        nearByAdapter = new NearByAdapter(getActivity());
        recyclerView.setAdapter(nearByAdapter);
        dataList = nearByAdapter.getDatas();
        nearByAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void itemClickLIstener(View view, int position) {
                //由于使用了三方，需要-1
                position--;
                MainHomeListItem mainHomeListItem = dataList.get(position);
                if (mainHomeListItem.getInferiorsCount() != null) {
                    //包含多个个体
                    Intent intent = new Intent(getActivity(), SecondaryListActivity.class);
                    intent.putExtra("mainHomeListItem", mainHomeListItem);
                    startActivity(intent);
                } else {
                    //包含0个个体
                    Intent intent = new Intent(getActivity(), AscriptionSubActivity.class);
                    intent.putExtra("subJect", mainHomeListItem.getId());
                    startActivity(intent);
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
        Call<FirstListResult> firstlySubject = subjectServer.getFirstlySubject(MyApplication.getInstance().getLongitude() + "", MyApplication.getInstance().getLatitude() + "", 0, WebParameters.PAGE_SIZE);
        ((IndexActivity) getActivity()).addCall(firstlySubject);
        firstlySubject.enqueue(new Callback<FirstListResult>() {
            @Override
            public void onResponse(Call<FirstListResult> call, Response<FirstListResult> response) {
                FirstListResult body = response == null ? null : response.body();
                if (body == null) return;
                if (body.getRspCode() == 0) {
                    List<MainHomeListItem> datas = body.getData().getDatas();
                    nearByAdapter.dataUpdate(datas);
                } else {
                    ToastUtil.showToast(body.getRspMsg(), false, getActivity());
                }
                recyclerView.refreshComplete();
            }

            @Override
            public void onFailure(Call<FirstListResult> call, Throwable t) {
                ToastUtil.showToast(t.getMessage(), false, getActivity());
                recyclerView.refreshComplete();
            }
        });
    }

    private void getDates() {
        SubjectServer subjectServer = RetrofitUtils.getRetrofit().create(SubjectServer.class);
        Call<FirstListResult> firstlySubject = subjectServer.getFirstlySubject(MyApplication.getInstance().getLongitude() + "", MyApplication.getInstance().getLatitude() + "", dataList.size(), WebParameters.PAGE_SIZE);
        ((IndexActivity) getActivity()).addCall(firstlySubject);
        firstlySubject.enqueue(new Callback<FirstListResult>() {
            @Override
            public void onResponse(Call<FirstListResult> call, Response<FirstListResult> response) {
                FirstListResult body = response.body();
                if (body.getRspCode() == 0) {
                    List<MainHomeListItem> datas = body.getData().getDatas();
                    nearByAdapter.addData(datas);
                } else {
                    ToastUtil.showToast(body.getRspMsg(), false, getActivity());
                }
                recyclerView.loadMoreComplete();
            }

            @Override
            public void onFailure(Call<FirstListResult> call, Throwable t) {
                ToastUtil.showToast(t.getMessage(), false, getActivity());
                recyclerView.loadMoreComplete();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.location: {
                initAddress();
                recyclerView.smoothScrollToPosition(0);
                recyclerView.refresh();
            }
            break;

            case R.id.location_text: {
                initAddress();
                recyclerView.smoothScrollToPosition(0);
                recyclerView.refresh();
            }
            break;

            case R.id.map: {
                Intent intent = new Intent(getActivity(), MapActivity.class);
                startActivity(intent);
            }
            break;

            case R.id.search: {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
            break;

            default:
                break;
        }
    }
}

package com.yidao.threekmo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yidao.myutils.file.SPUtils;
import com.yidao.myutils.toast.ToastUtil;
import com.yidao.threekmo.R;
import com.yidao.threekmo.activitys.ActiveActivity;
import com.yidao.threekmo.activitys.ActiveSearchActivity;
import com.yidao.threekmo.activitys.ChooseCityActivity;
import com.yidao.threekmo.activitys.IndexActivity;
import com.yidao.threekmo.activitys.WebViewScriptActivity;
import com.yidao.threekmo.adapter.BaseRvAdapter;
import com.yidao.threekmo.adapter.FirstFragmentAdapter;
import com.yidao.threekmo.application.MyApplication;
import com.yidao.threekmo.bean.BinnerResult;
import com.yidao.threekmo.bean.FirstListResult;
import com.yidao.threekmo.bean.MainHomeListItem;
import com.yidao.threekmo.customview.DateSpinner;
import com.yidao.threekmo.customview.DragTopLayout;
import com.yidao.threekmo.customview.NearBySpinner;
import com.yidao.threekmo.customview.SortSpinner;
import com.yidao.threekmo.parameter.SPParameters;
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
 * Created by Smart~ on 2017/10/15.
 */

public class FirstFragment extends BaseFragment implements View.OnClickListener {

    private XRecyclerView recyclerView;
    private FirstFragmentAdapter firstFragmentAdapter;
    private List<MainHomeListItem> dataList;
    private DragTopLayout dragLayout;

    private RelativeLayout search_rela;
    private ImageView location;
    private TextView location_text;
    private TextView search;

    private RelativeLayout banner_rela;
    private ImageView banner_image;
    private LinearLayout logo_rela;
    private ImageView clothes_rela_image;
    private TextView clothes_rela_text;
    private ImageView food_rela_image;
    private TextView food_rela_text;
    private ImageView house_rela_image;
    private TextView house_rela_text;
    private ImageView travel_rela_image;
    private TextView travel_rela_text;
    private ImageView creatwork_rela_image;
    private TextView creatwork_rela_text;
    private RelativeLayout clothes_rela;
    private RelativeLayout food_rela;
    private RelativeLayout house_rela;
    private RelativeLayout travel_rela;
    private RelativeLayout creatwork_rela;

    private View top_spinner_view;


    private LinearLayout spinner_linear;
    //    private ClassificationSpinner classificationSpinner;
    private NearBySpinner nearBySpinner;
    private DateSpinner dateSpinner;
    private SortSpinner sortSpinner;

    private String cityName = "";
    private String acName = "";
    private String startDate = "";
    private String endDate = "";
    private long distance = 0;
    private long sortRule = 0;

    public static int fuJinType = 0;
    public static int dataType = 0;
    public static int sortType = 0;
    private String binnerUrl = "";
    private String audit = "";

    public static final FirstFragment newInstance(String audit) {
        FirstFragment f = new FirstFragment();
        Bundle bdl = new Bundle();
        bdl.putString("audit", audit);
        f.setArguments(bdl);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getArguments() != null){
            audit = getArguments().getString("audit");
        }
        fuJinType = 0;
        dataType = 0;
        sortType = 0;

        initViews();
//        recyclerView.refresh();
        setCity();
        getBinner();
    }

    public void cityRefresh(String cityName) {
        //选择城市后刷新列表
        location_text.setText(cityName);
    }

    private void setCity() {
        //启动app根据城市刷新列表
        String cityName = (String) SPUtils.get(getActivity(), SPParameters.NEW_CITY, "杭州");
        location_text.setText(cityName);
        refreshDate("", acName, startDate, endDate, distance, sortRule);

        if("false".equals(audit)){
            logo_rela.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams llp_top_spinner_view = (LinearLayout.LayoutParams) top_spinner_view.getLayoutParams();
            llp_top_spinner_view.height = CommonUtil.getRealWidth(20);
        }else{
            logo_rela.setVisibility(View.GONE);
            LinearLayout.LayoutParams llp_top_spinner_view = (LinearLayout.LayoutParams) top_spinner_view.getLayoutParams();
            llp_top_spinner_view.height = CommonUtil.getRealWidth(0);
        }

    }


    private void initViews() {

        search_rela = (RelativeLayout) getView().findViewById(R.id.search_rela);
        RelativeLayout.LayoutParams tlpSearch_rela = (RelativeLayout.LayoutParams) search_rela.getLayoutParams();
        tlpSearch_rela.topMargin = CommonUtil.getStatusBarHeight(getActivity()) + CommonUtil.getRealWidth(24);
        tlpSearch_rela.bottomMargin = CommonUtil.getRealWidth(24);

        location = (ImageView) getView().findViewById(R.id.location);
        RelativeLayout.LayoutParams llpLocation = (RelativeLayout.LayoutParams) location.getLayoutParams();
        llpLocation.width = CommonUtil.getRealWidth(36);
        llpLocation.height = llpLocation.width;
        llpLocation.leftMargin = CommonUtil.getRealWidth(30);

        location_text = (TextView) getView().findViewById(R.id.location_text);
        location_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llpLocation_text = (RelativeLayout.LayoutParams) location_text.getLayoutParams();
        llpLocation_text.leftMargin = CommonUtil.getRealWidth(8);

        search = (TextView) getView().findViewById(R.id.search);
        search.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        search.setPadding(CommonUtil.getRealWidth(20), 0, 0, 0);
        RelativeLayout.LayoutParams llpSearch = (RelativeLayout.LayoutParams) search.getLayoutParams();
        llpSearch.height = CommonUtil.getRealWidth(54);
        llpSearch.leftMargin = CommonUtil.getRealWidth(28);
        llpSearch.rightMargin = CommonUtil.getRealWidth(30);

        banner_rela = (RelativeLayout) getView().findViewById(R.id.banner_rela);
        LinearLayout.LayoutParams llp_banner_rela = (LinearLayout.LayoutParams) banner_rela.getLayoutParams();
        llp_banner_rela.height = CommonUtil.getRealWidth(238);

        banner_image = (ImageView) getView().findViewById(R.id.banner_image);
        RelativeLayout.LayoutParams llp_banner_image = (RelativeLayout.LayoutParams) banner_image.getLayoutParams();
        llp_banner_image.height = CommonUtil.getRealWidth(238);

        logo_rela = (LinearLayout) getView().findViewById(R.id.logo_rela);
        LinearLayout.LayoutParams llp_logo_rela = (LinearLayout.LayoutParams) logo_rela.getLayoutParams();
        llp_logo_rela.height = CommonUtil.getRealWidth(146);

        clothes_rela_image = (ImageView) getView().findViewById(R.id.clothes_rela_image);
        RelativeLayout.LayoutParams llp_clothes_rela_image = (RelativeLayout.LayoutParams) clothes_rela_image.getLayoutParams();
        llp_clothes_rela_image.width = CommonUtil.getRealWidth(72);
        llp_clothes_rela_image.height = llp_clothes_rela_image.width;
        llp_clothes_rela_image.topMargin = CommonUtil.getRealWidth(12);

        clothes_rela_text = (TextView) getView().findViewById(R.id.clothes_rela_text);
        clothes_rela_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_clothes_rela_text = (RelativeLayout.LayoutParams) clothes_rela_text.getLayoutParams();
        llp_clothes_rela_text.topMargin = CommonUtil.getRealWidth(8);

        food_rela_image = (ImageView) getView().findViewById(R.id.food_rela_image);
        RelativeLayout.LayoutParams llp_food_rela_image = (RelativeLayout.LayoutParams) food_rela_image.getLayoutParams();
        llp_food_rela_image.width = CommonUtil.getRealWidth(72);
        llp_food_rela_image.height = llp_food_rela_image.width;
        llp_food_rela_image.topMargin = CommonUtil.getRealWidth(12);

        food_rela_text = (TextView) getView().findViewById(R.id.food_rela_text);
        food_rela_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_food_rela_text = (RelativeLayout.LayoutParams) food_rela_text.getLayoutParams();
        llp_food_rela_text.topMargin = CommonUtil.getRealWidth(8);

        house_rela_image = (ImageView) getView().findViewById(R.id.house_rela_image);
        RelativeLayout.LayoutParams llp_house_rela_image = (RelativeLayout.LayoutParams) house_rela_image.getLayoutParams();
        llp_house_rela_image.width = CommonUtil.getRealWidth(72);
        llp_house_rela_image.height = llp_house_rela_image.width;
        llp_house_rela_image.topMargin = CommonUtil.getRealWidth(12);

        house_rela_text = (TextView) getView().findViewById(R.id.house_rela_text);
        house_rela_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_house_rela_text = (RelativeLayout.LayoutParams) house_rela_text.getLayoutParams();
        llp_house_rela_text.topMargin = CommonUtil.getRealWidth(8);

        travel_rela_image = (ImageView) getView().findViewById(R.id.travel_rela_image);
        RelativeLayout.LayoutParams llp_travel_rela_image = (RelativeLayout.LayoutParams) travel_rela_image.getLayoutParams();
        llp_travel_rela_image.width = CommonUtil.getRealWidth(72);
        llp_travel_rela_image.height = llp_travel_rela_image.width;
        llp_travel_rela_image.topMargin = CommonUtil.getRealWidth(12);

        travel_rela_text = (TextView) getView().findViewById(R.id.travel_rela_text);
        travel_rela_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_travel_rela_text = (RelativeLayout.LayoutParams) travel_rela_text.getLayoutParams();
        llp_travel_rela_text.topMargin = CommonUtil.getRealWidth(8);

        creatwork_rela_image = (ImageView) getView().findViewById(R.id.creatwork_rela_image);
        RelativeLayout.LayoutParams llp_creatwork_rela_image = (RelativeLayout.LayoutParams) creatwork_rela_image.getLayoutParams();
        llp_creatwork_rela_image.width = CommonUtil.getRealWidth(72);
        llp_creatwork_rela_image.height = llp_creatwork_rela_image.width;
        llp_creatwork_rela_image.topMargin = CommonUtil.getRealWidth(12);

        creatwork_rela_text = (TextView) getView().findViewById(R.id.creatwork_rela_text);
        creatwork_rela_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_creatwork_rela_text = (RelativeLayout.LayoutParams) creatwork_rela_text.getLayoutParams();
        llp_creatwork_rela_text.topMargin = CommonUtil.getRealWidth(8);

        RelativeLayout clothes_rela = (RelativeLayout) getView().findViewById(R.id.clothes_rela);
        RelativeLayout food_rela = (RelativeLayout) getView().findViewById(R.id.food_rela);
        RelativeLayout house_rela = (RelativeLayout) getView().findViewById(R.id.house_rela);
        RelativeLayout travel_rela = (RelativeLayout) getView().findViewById(R.id.travel_rela);
        RelativeLayout creatwork_rela = (RelativeLayout) getView().findViewById(R.id.creatwork_rela);

        banner_rela.setOnClickListener(this);
        clothes_rela.setOnClickListener(this);
        food_rela.setOnClickListener(this);
        house_rela.setOnClickListener(this);
        travel_rela.setOnClickListener(this);
        creatwork_rela.setOnClickListener(this);
        location.setOnClickListener(this);
        location_text.setOnClickListener(this);
        search.setOnClickListener(this);

        top_spinner_view = getView().findViewById(R.id.top_spinner_view);

        spinner_linear = (LinearLayout) getView().findViewById(R.id.spinner_linear);
        LinearLayout.LayoutParams llp_spinner_linear = (LinearLayout.LayoutParams) spinner_linear.getLayoutParams();
        llp_spinner_linear.height = CommonUtil.getRealWidth(84);


//        //分类弹出下拉
//        classificationSpinner = (ClassificationSpinner) getView().findViewById(R.id.classificationSpinner);
//        classificationSpinner.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
//        classificationSpinner.init(getActivity());
//        classificationSpinner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                classificationSpinner.showWindow();
//            }
//        });
//        classificationSpinner.setOnclickClassificationListener(new ClassificationSpinner.OnclickClassification() {
//            @Override
//            public void classification(String classifiation, int positon) {
//                Toast.makeText(getActivity(), classifiation, Toast.LENGTH_SHORT).show();
//
//            }
//        });

        //附近弹出下拉
        nearBySpinner = (NearBySpinner) getView().findViewById(R.id.nearBySpinner);
        nearBySpinner.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        nearBySpinner.init(getActivity());
        nearBySpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nearBySpinner.showWindow(getActivity());
            }
        });
        nearBySpinner.setOnclickNearBuListener(new NearBySpinner.OnClickNearByListener() {
            @Override
            public void nearBy(String nearBy, int positon) {
//                Toast.makeText(getActivity(), nearBy, Toast.LENGTH_SHORT).show();


            }
        });

        //日期弹出下拉
        dateSpinner = (DateSpinner) getView().findViewById(R.id.dateSpinner);
        dateSpinner.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        dateSpinner.init(getActivity());
        dateSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSpinner.showWindow(getActivity());
            }
        });
        dateSpinner.setOnclickDateListener(new DateSpinner.OnclickDate() {
            @Override
            public void date(String start, String end) {
//                Toast.makeText(getActivity(), start + "-" + end, Toast.LENGTH_SHORT).show();

            }
        });

        //排序弹出下拉
        sortSpinner = (SortSpinner) getView().findViewById(R.id.sortSpinner);
        sortSpinner.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        sortSpinner.init(getActivity());
        sortSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortSpinner.showWindow(getActivity());
            }
        });
        sortSpinner.setOnclickSortListener(new SortSpinner.OnclickSort() {
            @Override
            public void sort(String sort, int position) {
//                Toast.makeText(getActivity(), sort, Toast.LENGTH_SHORT).show();

            }
        });


        dragLayout = (DragTopLayout) getView().findViewById(R.id.dragLayout);
        dragLayout.setOverDrag(false);

        recyclerView = (XRecyclerView) getView().findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        firstFragmentAdapter = new FirstFragmentAdapter(getActivity());
        recyclerView.setAdapter(firstFragmentAdapter);
        dataList = firstFragmentAdapter.getDatas();
        firstFragmentAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void itemClickLIstener(View view, int position) {
                //由于使用了三方，需要-1
                position--;

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

        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshDate(cityName, acName, startDate, endDate, distance, sortRule);
            }

            @Override
            public void onLoadMore() {
                getDates(cityName, acName, startDate, endDate, distance, sortRule);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                if (firstItemPosition == 0) {
                    dragLayout.setTouchMode(true);
                } else {
                    if (dy == 0) {
                        dragLayout.setTouchMode(true);
                    } else if (dy < 0) {
                        dragLayout.setTouchMode(false);
                    } else if (dy > 5) {
                        dragLayout.setTouchMode(false);
                    }
                }
//                if (dy == 0) {
//                    if(firstItemPosition == 0){
//                        dragLayout.setTouchMode(true);
//                    }
//                } else if (dy < 0) {
//                    dragLayout.setTouchMode(false);
//                }else if(dy > 5){
//                    dragLayout.setTouchMode(false);
//                }
                Log.e("****************************", dy + "");
            }
        });
    }

    private void refreshDate(String cityName, String acName, String startDate, String endDate, long distance, long sortRule) {
        SubjectServer subjectServer = RetrofitUtils.getRetrofit().create(SubjectServer.class);
        Call<FirstListResult> firstlySubject = subjectServer.getActivityList("0","", acName, startDate, endDate, distance, sortRule, MyApplication.getInstance().getLongitude() + "", MyApplication.getInstance().getLatitude() + "", 0, WebParameters.PAGE_SIZE);
        ((IndexActivity) getActivity()).addCall(firstlySubject);
        firstlySubject.enqueue(new Callback<FirstListResult>() {
            @Override
            public void onResponse(Call<FirstListResult> call, Response<FirstListResult> response) {
                FirstListResult body = response.body();
                if (body.getRspCode() == 0) {
                    List<MainHomeListItem> datas = body.getData().getDatas();
                    if (datas == null) {
                        datas = new ArrayList<MainHomeListItem>();
                    }
                    firstFragmentAdapter.dataUpdate(datas);
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

    private void getDates(String cityName, String acName, String startDate, String endDate, long distance, long sortRule) {
        SubjectServer subjectServer = RetrofitUtils.getRetrofit().create(SubjectServer.class);
        Call<FirstListResult> firstlySubject = subjectServer.getActivityList("0","", acName, startDate, endDate, distance, sortRule, MyApplication.getInstance().getLongitude() + "", MyApplication.getInstance().getLatitude() + "", dataList.size(), WebParameters.PAGE_SIZE);
        ((IndexActivity) getActivity()).addCall(firstlySubject);
        firstlySubject.enqueue(new Callback<FirstListResult>() {
            @Override
            public void onResponse(Call<FirstListResult> call, Response<FirstListResult> response) {
                FirstListResult body = response.body();
                if (body.getRspCode() == 0) {
                    List<MainHomeListItem> datas = body.getData().getDatas();
                    firstFragmentAdapter.addData(datas);
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
                Intent intent = new Intent(getActivity(), ChooseCityActivity.class);
                intent.putExtra("oldcity", location_text.getText().toString());
                startActivity(intent);
            }
            break;

            case R.id.location_text: {
                Intent intent = new Intent(getActivity(), ChooseCityActivity.class);
                intent.putExtra("oldcity", location_text.getText().toString());
                startActivity(intent);
            }
            break;
            case R.id.search: {
                Intent intent = new Intent(getActivity(), ActiveSearchActivity.class);
                startActivity(intent);
            }
            break;

            case R.id.banner_rela: {
//                binnerUrl = "app/home/intercept";
                if (!TextUtils.isEmpty(binnerUrl)) {
                    if (LoginUtils.isLogin(true, getActivity())) {
                        String token = LoginUtils.getToken(getActivity());
//                        String url = WebParameters.SERVERURL + "app?activityId=1&token=" + token;
                        String url = WebParameters.SERVERURL + binnerUrl + "?activityId=1&token=" + token;
                        Intent intent = new Intent(getActivity(), WebViewScriptActivity.class);
                        intent.putExtra("url", url);
                        intent.putExtra("title", "网星大赛投票");
                        startActivity(intent);
                    }
                }
            }
            break;
            case R.id.clothes_rela: {
                if (LoginUtils.isLogin(true, getActivity())) {
                    //http://192.168.0.83:8081/interior/manage/activity/lifeRelated
                    String url = WebParameters.SERVERURL + "interior/manage/activity/lifeRelated?id=" + 68;
                    Intent intent = new Intent(getActivity(), WebViewScriptActivity.class);
                    intent.putExtra("url", url);
                    intent.putExtra("title", "服饰");
                    startActivity(intent);
                }
            }
            break;
            case R.id.food_rela: {
                if (LoginUtils.isLogin(true, getActivity())) {
                    String url = WebParameters.SERVERURL + "interior/manage/activity/lifeRelated?id=" + 67;
                    Intent intent = new Intent(getActivity(), WebViewScriptActivity.class);
                    intent.putExtra("url", url);
                    intent.putExtra("title", "美食");
                    startActivity(intent);
                }
            }
            break;
            case R.id.house_rela: {
                if (LoginUtils.isLogin(true, getActivity())) {
                    String url = WebParameters.SERVERURL + "interior/manage/activity/lifeRelated?id=" + 55;
                    Intent intent = new Intent(getActivity(), WebViewScriptActivity.class);
                    intent.putExtra("url", url);
                    intent.putExtra("title", "住宿");
                    startActivity(intent);
                }
            }
            break;
            case R.id.travel_rela: {
                if (LoginUtils.isLogin(true, getActivity())) {
                    String url = WebParameters.SERVERURL + "interior/manage/activity/lifeRelated?id=" + 54;
                    Intent intent = new Intent(getActivity(), WebViewScriptActivity.class);
                    intent.putExtra("url", url);
                    intent.putExtra("title", "旅行");
                    startActivity(intent);
                }
            }
            break;
            case R.id.creatwork_rela: {
                if (LoginUtils.isLogin(true, getActivity())) {
                    String url = WebParameters.SERVERURL + "interior/manage/activity/lifeRelated?id=" + 76;
                    Intent intent = new Intent(getActivity(), WebViewScriptActivity.class);
                    intent.putExtra("url", url);
                    intent.putExtra("title", "创业");
                    startActivity(intent);
                }
            }
            break;
            default:
                break;
        }
    }

    private void getBinner() {
        SubjectServer subjectServer = RetrofitUtils.getRetrofit().create(SubjectServer.class);
        Call<BinnerResult> call = subjectServer.getBinnerSingleImage();
        ((IndexActivity) getActivity()).addCall(call);
        call.enqueue(new Callback<BinnerResult>() {
            @Override
            public void onResponse(Call<BinnerResult> call, Response<BinnerResult> response) {
                BinnerResult body = response.body();
                if (body != null) {
                    if (body.getRspCode() == 0) {

                        String photoUrl = body.getData().getPhotoUrl();
                        binnerUrl = body.getData().getUrl();
                        Glide.with(getActivity()).load(photoUrl).into(banner_image);
                    }
                }

            }

            @Override
            public void onFailure(Call<BinnerResult> call, Throwable t) {

            }
        });
    }
}

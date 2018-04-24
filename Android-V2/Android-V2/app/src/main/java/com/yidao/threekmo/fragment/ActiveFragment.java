package com.yidao.threekmo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.yidao.threekmo.adapter.IndexAdapter;
import com.yidao.threekmo.application.MyApplication;
import com.yidao.threekmo.bean.FirstListResult;
import com.yidao.threekmo.bean.MainHomeListItem;
import com.yidao.threekmo.customview.ClassificationSpinner;
import com.yidao.threekmo.customview.DateSpinner;
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
 * Created by Smart~ on 2017/10/10.
 */

public class ActiveFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout toolbar;
    private RelativeLayout search_rela;
    private ImageView location;
    private TextView location_text;
    private TextView search;

    private LinearLayout spinner_linear;
    private ClassificationSpinner classificationSpinner;
    private NearBySpinner nearBySpinner;
    private DateSpinner dateSpinner;
    private SortSpinner sortSpinner;

    private XRecyclerView recyclerView;
    private IndexAdapter indexAdapter;
    private List<MainHomeListItem> dataList;


    public static final ActiveFragment newInstance(String title) {
        ActiveFragment f = new ActiveFragment();
        Bundle bdl = new Bundle();
        bdl.putString(title, title);
        f.setArguments(bdl);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_active, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        iniViews();
        setCity();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setCity() {
        //启动app根据城市刷新列表
        String cityName = (String) SPUtils.get(getActivity(), SPParameters.NEW_CITY, "杭州");
        location_text.setText(cityName);
    }

    public void cityRefresh(String cityName) {
        //选择城市后刷新列表
        location_text.setText(cityName);
    }

    private void iniViews() {
        toolbar = (RelativeLayout) getView().findViewById(R.id.toolbar);

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

        spinner_linear = (LinearLayout) getView().findViewById(R.id.spinner_linear);
        RelativeLayout.LayoutParams llp_spinner_linear = (RelativeLayout.LayoutParams) spinner_linear.getLayoutParams();
        llp_spinner_linear.height = CommonUtil.getRealWidth(84);

        //分类弹出下拉
        classificationSpinner = (ClassificationSpinner) getView().findViewById(R.id.classificationSpinner);
        classificationSpinner.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        classificationSpinner.init(getActivity());
        classificationSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classificationSpinner.showWindow(getActivity());
            }
        });
        classificationSpinner.setOnclickClassificationListener(new ClassificationSpinner.OnclickClassification() {
            @Override
            public void classification(String classifiation, int positon) {
                Toast.makeText(getActivity(), classifiation, Toast.LENGTH_SHORT).show();

            }
        });

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
                Toast.makeText(getActivity(), nearBy, Toast.LENGTH_SHORT).show();

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
                Toast.makeText(getActivity(), start + "-" + end, Toast.LENGTH_SHORT).show();

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
                Toast.makeText(getActivity(), sort, Toast.LENGTH_SHORT).show();

            }
        });

        location.setOnClickListener(this);
        location_text.setOnClickListener(this);
        search.setOnClickListener(this);

        recyclerView = (XRecyclerView) getView().findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        indexAdapter = new IndexAdapter(getActivity());
        recyclerView.setAdapter(indexAdapter);
        dataList = indexAdapter.getDatas();
//        setHeader(recyclerView);
        indexAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void itemClickLIstener(View view, int position) {
                //由于使用了三方，需要-1
                position--;
                if (position == 0) {
                    //recylerview添加的头
                    Toast.makeText(getActivity(), "顶部binner", Toast.LENGTH_SHORT).show();
                } else {
                    MainHomeListItem mainHomeListItem = dataList.get(position - 1);
                    long id = mainHomeListItem.getId();
                    //活动走的activity
                    Intent intent = new Intent(getActivity(), ActiveActivity.class);
                    intent.putExtra("id", id);
                    startActivityForResult(intent, 101);
                }

            }
        });

        //adapter点击recylerview中的下拉选项，弹出相对应的spinner
        indexAdapter.setOnclickSpinnerListener(new IndexAdapter.OnclickSpinnerListener() {
            @Override
            public void OnclickSpinner(int num) {
                moveToPosition(3);
                switch (num) {
                    case 0: {
                        if (dataList.size() >= 3) {
                            classificationSpinner.showWindow(getActivity());
                        } else {
                            Toast.makeText(getActivity(), "当前活动较少，暂时无法分类", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                    case 1: {
                        if (dataList.size() >= 3) {
                            nearBySpinner.showWindow(getActivity());
                        } else {
                            Toast.makeText(getActivity(), "当前活动较少，暂时无法分类", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                    case 2: {
                        if (dataList.size() >= 3) {
                            dateSpinner.showWindow(getActivity());
                        } else {
                            Toast.makeText(getActivity(), "当前活动较少，暂时无法分类", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                    case 3: {
                        if (dataList.size() >= 3) {
                            sortSpinner.showWindow(getActivity());
                        } else {
                            Toast.makeText(getActivity(), "当前活动较少，暂时无法分类", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                    default:
                        break;
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

        //recylerview滑动状态
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                if (firstItemPosition == 0 || firstItemPosition == 1) {
                    spinner_linear.setVisibility(View.GONE);
                } else {
                    spinner_linear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                if (firstItemPosition == 0 || firstItemPosition == 1) {
                    spinner_linear.setVisibility(View.GONE);
                } else {
                    spinner_linear.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    //recylerview添加头
    private void setHeader(RecyclerView view) {
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.header_binner, view, false);
        RelativeLayout banner_rela = (RelativeLayout) header.findViewById(R.id.banner_rela);
        RelativeLayout.LayoutParams llp_banner_rela = (RelativeLayout.LayoutParams) banner_rela.getLayoutParams();
        llp_banner_rela.height = CommonUtil.getRealWidth(384);

        ImageView banner_image = (ImageView) header.findViewById(R.id.banner_image);
        RelativeLayout.LayoutParams llp_banner_image = (RelativeLayout.LayoutParams) banner_image.getLayoutParams();
        llp_banner_image.height = CommonUtil.getRealWidth(238);

        LinearLayout logo_rela = (LinearLayout) header.findViewById(R.id.logo_rela);
        RelativeLayout.LayoutParams llp_logo_rela = (RelativeLayout.LayoutParams) logo_rela.getLayoutParams();
        llp_logo_rela.height = CommonUtil.getRealWidth(146);

        ImageView clothes_rela_image = (ImageView) header.findViewById(R.id.clothes_rela_image);
        RelativeLayout.LayoutParams llp_clothes_rela_image = (RelativeLayout.LayoutParams) clothes_rela_image.getLayoutParams();
        llp_clothes_rela_image.width = CommonUtil.getRealWidth(72);
        llp_clothes_rela_image.height = llp_clothes_rela_image.width;
        llp_clothes_rela_image.topMargin = CommonUtil.getRealWidth(12);

        TextView clothes_rela_text = (TextView) header.findViewById(R.id.clothes_rela_text);
        clothes_rela_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_clothes_rela_text = (RelativeLayout.LayoutParams) clothes_rela_text.getLayoutParams();
        llp_clothes_rela_text.topMargin = CommonUtil.getRealWidth(8);

        ImageView food_rela_image = (ImageView) header.findViewById(R.id.food_rela_image);
        RelativeLayout.LayoutParams llp_food_rela_image = (RelativeLayout.LayoutParams) food_rela_image.getLayoutParams();
        llp_food_rela_image.width = CommonUtil.getRealWidth(72);
        llp_food_rela_image.height = llp_food_rela_image.width;
        llp_food_rela_image.topMargin = CommonUtil.getRealWidth(12);

        TextView food_rela_text = (TextView) header.findViewById(R.id.food_rela_text);
        food_rela_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_food_rela_text = (RelativeLayout.LayoutParams) food_rela_text.getLayoutParams();
        llp_food_rela_text.topMargin = CommonUtil.getRealWidth(8);

        ImageView house_rela_image = (ImageView) header.findViewById(R.id.house_rela_image);
        RelativeLayout.LayoutParams llp_house_rela_image = (RelativeLayout.LayoutParams) house_rela_image.getLayoutParams();
        llp_house_rela_image.width = CommonUtil.getRealWidth(72);
        llp_house_rela_image.height = llp_house_rela_image.width;
        llp_house_rela_image.topMargin = CommonUtil.getRealWidth(12);

        TextView house_rela_text = (TextView) header.findViewById(R.id.house_rela_text);
        house_rela_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_house_rela_text = (RelativeLayout.LayoutParams) house_rela_text.getLayoutParams();
        llp_house_rela_text.topMargin = CommonUtil.getRealWidth(8);

        ImageView travel_rela_image = (ImageView) header.findViewById(R.id.travel_rela_image);
        RelativeLayout.LayoutParams llp_travel_rela_image = (RelativeLayout.LayoutParams) travel_rela_image.getLayoutParams();
        llp_travel_rela_image.width = CommonUtil.getRealWidth(72);
        llp_travel_rela_image.height = llp_travel_rela_image.width;
        llp_travel_rela_image.topMargin = CommonUtil.getRealWidth(12);

        TextView travel_rela_text = (TextView) header.findViewById(R.id.travel_rela_text);
        travel_rela_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_travel_rela_text = (RelativeLayout.LayoutParams) travel_rela_text.getLayoutParams();
        llp_travel_rela_text.topMargin = CommonUtil.getRealWidth(8);

        ImageView creatwork_rela_image = (ImageView) header.findViewById(R.id.creatwork_rela_image);
        RelativeLayout.LayoutParams llp_creatwork_rela_image = (RelativeLayout.LayoutParams) creatwork_rela_image.getLayoutParams();
        llp_creatwork_rela_image.width = CommonUtil.getRealWidth(72);
        llp_creatwork_rela_image.height = llp_creatwork_rela_image.width;
        llp_creatwork_rela_image.topMargin = CommonUtil.getRealWidth(12);

        TextView creatwork_rela_text = (TextView) header.findViewById(R.id.creatwork_rela_text);
        creatwork_rela_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_creatwork_rela_text = (RelativeLayout.LayoutParams) creatwork_rela_text.getLayoutParams();
        llp_creatwork_rela_text.topMargin = CommonUtil.getRealWidth(8);

        RelativeLayout clothes_rela = (RelativeLayout) header.findViewById(R.id.clothes_rela);
        RelativeLayout food_rela = (RelativeLayout) header.findViewById(R.id.food_rela);
        RelativeLayout house_rela = (RelativeLayout) header.findViewById(R.id.house_rela);
        RelativeLayout travel_rela = (RelativeLayout) header.findViewById(R.id.travel_rela);
        RelativeLayout creatwork_rela = (RelativeLayout) header.findViewById(R.id.creatwork_rela);

        banner_image.setOnClickListener(this);
        clothes_rela.setOnClickListener(this);
        food_rela.setOnClickListener(this);
        house_rela.setOnClickListener(this);
        travel_rela.setOnClickListener(this);
        creatwork_rela.setOnClickListener(this);

        indexAdapter.setHeaderView(header);
    }

    private void refreshDate() {
        SubjectServer subjectServer = RetrofitUtils.getRetrofit().create(SubjectServer.class);
        Call<FirstListResult> firstlySubject = subjectServer.getActivityList("0", "", "",
                "", "", 0, 0, MyApplication.getInstance().getLongitude() + "",
                MyApplication.getInstance().getLatitude() + "", 0, WebParameters.PAGE_SIZE);
        ((IndexActivity) getActivity()).addCall(firstlySubject);
        firstlySubject.enqueue(new Callback<FirstListResult>() {
            @Override
            public void onResponse(Call<FirstListResult> call, Response<FirstListResult> response) {
                FirstListResult body = response == null ? null : response.body();
                if (body == null || body.getData() == null) return;
                if (body.getRspCode() == 0) {
                    List<MainHomeListItem> datas = body.getData().getDatas();
                    if (datas == null) {
                        datas = new ArrayList<MainHomeListItem>();
                    }
                    indexAdapter.dataUpdate(datas);
                    setHeader(recyclerView);
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
        Call<FirstListResult> firstlySubject = subjectServer.getActivityList("0","", "", "",
                "", 0, 0, MyApplication.getInstance().getLongitude() + "",
                MyApplication.getInstance().getLatitude() + "", dataList.size(), WebParameters.PAGE_SIZE);
        ((IndexActivity) getActivity()).addCall(firstlySubject);
        firstlySubject.enqueue(new Callback<FirstListResult>() {
            @Override
            public void onResponse(Call<FirstListResult> call, Response<FirstListResult> response) {
                FirstListResult body = response.body();
                if (body.getRspCode() == 0) {
                    List<MainHomeListItem> datas = body.getData().getDatas();
                    indexAdapter.addData(datas);
                    setHeader(recyclerView);
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
            case R.id.banner_image: {
                if (LoginUtils.isLogin(true, getActivity())) {
                    String token = LoginUtils.getToken(getActivity());
                    String url = WebParameters.SERVERURL + "app?activityId=1&token=" + token;
                    Intent intent = new Intent(getActivity(), WebViewScriptActivity.class);
                    intent.putExtra("url", url);
                    intent.putExtra("title", "网星大赛投票");
                    startActivity(intent);
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
                Toast.makeText(getActivity(), "服饰", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "美食", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "住宿", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "旅行", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "创业", Toast.LENGTH_SHORT).show();
            }
            break;
            default:
                break;

        }
    }

    private void moveToPosition(int n) {
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        LinearLayoutManager mLinearLayoutManager = (LinearLayoutManager) layoutManager;
        int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
        //然后区分情况
        if (n <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
            recyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            //当要置顶的项已经在屏幕上显示时
            int top = recyclerView.getChildAt(n - firstItem).getTop();
            recyclerView.scrollBy(0, top);
        } else {
            //当要置顶的项在当前显示的最后一项的后面时
            recyclerView.scrollToPosition(n);
        }

    }

}

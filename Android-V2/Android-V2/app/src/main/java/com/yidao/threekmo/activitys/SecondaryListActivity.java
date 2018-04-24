package com.yidao.threekmo.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yidao.myutils.keyboard.KeyBoardUtils;
import com.yidao.myutils.log.LogUtils;
import com.yidao.myutils.toast.ToastUtil;
import com.yidao.threekmo.R;
import com.yidao.threekmo.adapter.BaseRvAdapter;
import com.yidao.threekmo.adapter.SecondaryListAdapter;
import com.yidao.threekmo.application.MyApplication;
import com.yidao.threekmo.bean.ChooseSecondListBean;
import com.yidao.threekmo.bean.GaoDeMapBean;
import com.yidao.threekmo.bean.MainHomeListItem;
import com.yidao.threekmo.bean.SearchResultBean;
import com.yidao.threekmo.bean.SecondlyResultBean;
import com.yidao.threekmo.customview.CopyEaseDialog;
import com.yidao.threekmo.customview.IosSpinner;
import com.yidao.threekmo.parameter.WebParameters;
import com.yidao.threekmo.retrofit_server.SubjectServer;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.HistroyUtils;
import com.yidao.threekmo.utils.LoginUtils;
import com.yidao.threekmo.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondaryListActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivBack;
    private TextView tvTitle;
    private ImageView ivSearch;
    private ImageView ivMap;
    private XRecyclerView recyclerview;
    private SecondaryListAdapter secondaryListAdapter;
    private MainHomeListItem mainHomeListItem;
    //获取主页详情
    private List<MainHomeListItem> dataList;
    //获取楼栋下拉菜单详情
    private List<MainHomeListItem> items;
    //获取层高下拉菜单详情
    private List<MainHomeListItem> floors;
    //获取层高所有数据
    private List<MainHomeListItem> allFloors;

    private IosSpinner mIosSpinner1;
    private IosSpinner mIosSpinner2;
    private int currentSlectedItem1 = 0;
    private int currentSlectedItem2 = 0;
    private String banName = null;
    private String floorName = null;
    private String keyword = null;
    private String realKeys = null;

    private TextView tvCancle;
    private EditText etSearch;
    private RelativeLayout tool_rela;
    private RelativeLayout search_line;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary_list);

        mainHomeListItem = (MainHomeListItem) getIntent().getSerializableExtra("mainHomeListItem");

        viewInit();

        dataInit();

        achieveSelect();

        recyclerview.refresh();
    }

    private void dataInit() {

        tvTitle.setText(mainHomeListItem.getName());
    }

    private void viewInit() {

        barInit();

        selectBarInit();

        recyclerviewInit();
    }


    private void recyclerviewInit() {
        recyclerview = (XRecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SecondaryListActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        secondaryListAdapter = new SecondaryListAdapter(SecondaryListActivity.this);
        recyclerview.setAdapter(secondaryListAdapter);
        dataList = secondaryListAdapter.getDatas();
        secondaryListAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void itemClickLIstener(View view, int position) {
                position--;
                MainHomeListItem secondlyListItem = dataList.get(position);
                if(secondlyListItem.getLevel() == 1){
                    Intent intent = new Intent(SecondaryListActivity.this, AscriptionSubActivity.class);
                    intent.putExtra("subJect", secondlyListItem.getId());
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(SecondaryListActivity.this, SecondlyDetailActivity.class);
                    intent.putExtra("id", secondlyListItem.getId());
                    intent.putExtra("subJectId",mainHomeListItem.getId());
                    startActivity(intent);
                }

            }
        });
        secondaryListAdapter.setOnButtonClickListener(new BaseRvAdapter.OnButtonClickListener() {
            @Override
            public void itemButtonListener(int position) {
                final MainHomeListItem secondlyListItem = dataList.get(position);
                boolean isLogin = LoginUtils.isLogin(true,SecondaryListActivity.this);
                if(isLogin){
                    new CopyEaseDialog(SecondaryListActivity.this, "是否要隐藏，不再显示？", null, null, new CopyEaseDialog.AlertDialogUser() {
                        @Override
                        public void onResult(boolean confirmed, Bundle bundle) {
                            if(confirmed){
                                Call<GaoDeMapBean> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).yinCang(LoginUtils.getToken(SecondaryListActivity.this),mainHomeListItem.getId(),secondlyListItem.getId());
                                addCall(call);
                                call.enqueue(new Callback<GaoDeMapBean>() {
                                    @Override
                                    public void onResponse(Call<GaoDeMapBean> call, Response<GaoDeMapBean> response) {
                                        GaoDeMapBean body = response.body();
                                        if(body != null){
                                            if(body.getRspCode() == 0){
                                                recyclerview.refresh();
                                            }
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<GaoDeMapBean> call, Throwable t) {

                                    }
                                });
                            }
                        }
                    },true).show();
                }

            }
        });
        recyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                haveChoose(realKeys,banName,floorName);
            }

            @Override
            public void onLoadMore() {
                getChoose(realKeys,banName,floorName);
            }
        });
    }

    //获取楼栋列表
    private void selectBarInit() {
        Call<ChooseSecondListBean> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).getBanNum(mainHomeListItem.getId());
        addCall(call);
        call.enqueue(new Callback<ChooseSecondListBean>() {
            @Override
            public void onResponse(Call<ChooseSecondListBean> call, Response<ChooseSecondListBean> response) {
                ChooseSecondListBean body = response.body();
                if(body != null){
                    MainHomeListItem main = new MainHomeListItem();
                    main.setName("楼栋");
                    main.setId(mainHomeListItem.getId());
                    items = body.getData();
                    items.add(0,main);
                    mIosSpinner1.setData(items);
                }
            }

            @Override
            public void onFailure(Call<ChooseSecondListBean> call, Throwable t) {

            }
        });
    }

    //获取层高列表
    private void achieveBanNum(){
        long id = items.get(currentSlectedItem1 - 1).getId();
        Call<ChooseSecondListBean> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).getFloorNum(id);
        addCall(call);
        call.enqueue(new Callback<ChooseSecondListBean>() {
            @Override
            public void onResponse(Call<ChooseSecondListBean> call, Response<ChooseSecondListBean> response) {
                ChooseSecondListBean body = response.body();
                if(body != null){
                    floors = body.getData();
                    MainHomeListItem main = new MainHomeListItem();
                    main.setName("层高");
                    main.setId(mainHomeListItem.getId());
                    floors.add(0,main);
                    mIosSpinner2.setData(floors);
                }

            }

            @Override
            public void onFailure(Call<ChooseSecondListBean> call, Throwable t) {

            }
        });
    }


    //楼栋楼层列表初始化及点击事件
    private void achieveSelect(){

        mIosSpinner1= (IosSpinner)findViewById(R.id.iosspinner1);
        mIosSpinner1.init(SecondaryListActivity.this);
        currentSlectedItem1=mIosSpinner1.getSelectedItemPosition();
        mIosSpinner1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //楼栋选择
                mIosSpinner1.showWindow();
            }
        });
        mIosSpinner1.setOnSpinnerItemClickListener(new IosSpinner.OnSpinnerItemClickListener() {
            @Override
            public void OnSpinnerItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentSlectedItem1=mIosSpinner1.getSelectedItemPosition();

                //获取楼栋选择 第三方所以-1
                banName = items.get(currentSlectedItem1 - 1).getName();
                //楼栋选择
                achieveBanNum();
                //点击的是listview中的楼栋选项
                if(items.get(currentSlectedItem1 - 1).getId() == mainHomeListItem.getId()){
//                    refreshDate();
                    banName = null;
                    haveChoose(realKeys,null,null);
                }else{
                    //点击的不是楼栋选项
                    haveChoose(realKeys,banName,floorName);

                }
                //只要点击楼栋选项。层高就要初始化
                if(mIosSpinner2 != null){
                    mIosSpinner2.setText("层高");
                    floorName = null;
                }
                if(!TextUtils.isEmpty(banName)){
                    mIosSpinner1.setText(banName);
                }else{
                    mIosSpinner1.setText("楼栋");
                }

            }
        });


        mIosSpinner2= (IosSpinner)findViewById(R.id.iosspinner2);
        mIosSpinner2.init(SecondaryListActivity.this);
        currentSlectedItem2=mIosSpinner2.getSelectedItemPosition();
        mIosSpinner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //层高选择
                if(!TextUtils.isEmpty(banName) && !"楼栋".equals(banName)){
                    if(floors != null){
                        mIosSpinner2.showWindow();
                    }else{
                        String title = "暂无相关信息";

                        CopyEaseDialog.AlertDialogUser alertDialogUser = new CopyEaseDialog.AlertDialogUser() {
                            @Override
                            public void onResult(boolean confirmed, Bundle bundle) {

                            }
                        };
                        new CopyEaseDialog(SecondaryListActivity.this,title,null,null,alertDialogUser,false).show();
                    }

                }else if(TextUtils.isEmpty(banName)){
                    String title = "需要先选择楼栋！";

                    CopyEaseDialog.AlertDialogUser alertDialogUser = new CopyEaseDialog.AlertDialogUser() {
                        @Override
                        public void onResult(boolean confirmed, Bundle bundle) {

                        }
                    };
                    new CopyEaseDialog(SecondaryListActivity.this,title,null,null,alertDialogUser,false).show();
                }
            }
        });
        mIosSpinner2.setOnSpinnerItemClickListener(new IosSpinner.OnSpinnerItemClickListener() {
            @Override
            public void OnSpinnerItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentSlectedItem2=mIosSpinner2.getSelectedItemPosition();
                floorName = floors.get(currentSlectedItem2-1).getName();
                if(!"楼栋".equals(banName)){
                    if(currentSlectedItem2-1 == 0){
                        floorName = null;
                        haveChoose(realKeys,banName,null);
                    }else{
                        haveChoose(realKeys,banName,floorName);
                    }
                }
                if(!TextUtils.isEmpty(floorName)){
                    mIosSpinner2.setText(floorName);
                }else{
                    mIosSpinner2.setText("层高");
                }

                Log.e("当前选中item",""+currentSlectedItem2);
                // TODO do someThing
            }
        });

    }

    private void barInit() {

        RelativeLayout rela = (RelativeLayout) findViewById(R.id.rela);
        RelativeLayout.LayoutParams llp_rela = (RelativeLayout.LayoutParams)rela.getLayoutParams();
        llp_rela.height = CommonUtil.getRealWidth(130);

        ivBack = (ImageView) findViewById(R.id.ivBack);
        RelativeLayout.LayoutParams llp_ivBack = (RelativeLayout.LayoutParams)ivBack.getLayoutParams();
        llp_ivBack.topMargin = CommonUtil.getRealWidth(52);

        ivBack.setOnClickListener(this);

        tvTitle = (TextView) findViewById(R.id.tvTitle);

        RelativeLayout titleRela = (RelativeLayout) findViewById(R.id.titleRela);
        RelativeLayout.LayoutParams llp_titleRela = (RelativeLayout.LayoutParams)titleRela.getLayoutParams();
        llp_titleRela.width = CommonUtil.getRealWidth(400);
        llp_titleRela.topMargin = CommonUtil.getRealWidth(60);

        ivSearch = (ImageView) findViewById(R.id.ivSearch);
        RelativeLayout.LayoutParams llp_ivSearch = (RelativeLayout.LayoutParams)ivSearch.getLayoutParams();
        llp_ivSearch.topMargin = CommonUtil.getRealWidth(52);
        ivSearch.setOnClickListener(this);

        ivMap = (ImageView) findViewById(R.id.ivMap);
        RelativeLayout.LayoutParams llp_ivMap = (RelativeLayout.LayoutParams)ivMap.getLayoutParams();
        llp_ivMap.topMargin = CommonUtil.getRealWidth(52);
        ivMap.setOnClickListener(this);

        tvCancle = (TextView) findViewById(R.id.tvCancle);
        RelativeLayout.LayoutParams llp_tvCancle = (RelativeLayout.LayoutParams)tvCancle.getLayoutParams();
        llp_tvCancle.topMargin = CommonUtil.getRealWidth(60);
        llp_tvCancle.rightMargin = CommonUtil.getRealWidth(30);

        tvCancle.setOnClickListener(this);

        tool_rela = (RelativeLayout) findViewById(R.id.tool_rela);
        RelativeLayout.LayoutParams llp_tool_rela = (RelativeLayout.LayoutParams)tool_rela.getLayoutParams();
        llp_tool_rela.height = CommonUtil.getRealWidth(130);

        search_line = (RelativeLayout) findViewById(R.id.search_line);
        RelativeLayout.LayoutParams llp_search_line = (RelativeLayout.LayoutParams)search_line.getLayoutParams();
        llp_search_line.height = CommonUtil.getRealWidth(130);

        ImageView search_image = (ImageView) findViewById(R.id.search_image);
        RelativeLayout.LayoutParams llp_search_image = (RelativeLayout.LayoutParams)search_image.getLayoutParams();
        llp_search_image.topMargin = CommonUtil.getRealWidth(52);
        llp_search_image.leftMargin = CommonUtil.getRealWidth(60);

        etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.setPadding(CommonUtil.getRealWidth(60),0,0,0);
        RelativeLayout.LayoutParams llp_etSearch = (RelativeLayout.LayoutParams)etSearch.getLayoutParams();
        llp_etSearch.height = CommonUtil.getRealWidth(60);
        llp_etSearch.width = CommonUtil.getRealWidth(552);
        llp_etSearch.topMargin = CommonUtil.getRealWidth(54);
        llp_etSearch.leftMargin = CommonUtil.getRealWidth(60);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                keyword = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //搜索
                    if(!TextUtils.isEmpty(keyword)){
                        realKeys = keyword;
                        LogUtils.e("搜索keyword:" +realKeys);
                        HistroyUtils.getInstance(SecondaryListActivity.this).insertData(realKeys);
//                        achieveSearch();
                        haveChoose(realKeys,banName,floorName);
                        KeyBoardUtils.closeKeybord(etSearch,SecondaryListActivity.this);
                    }

                }

                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivSearch:
//                startActivity(new Intent(SecondaryListActivity.this,SearchActivity.class));
                search_line.setVisibility(View.VISIBLE);
                tool_rela.setVisibility(View.GONE);
                break;
            case R.id.tvCancle:
                search_line.setVisibility(View.GONE);
                tool_rela.setVisibility(View.VISIBLE);
                etSearch.setText("");
                realKeys = null;
                haveChoose(realKeys,banName,floorName);
                //强制收起软键盘
                InputMethodManager imm =  (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm != null) {
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),0);
                }
                break;
            case R.id.ivMap:
                Intent intent = new Intent(SecondaryListActivity.this,MapSecondActivity.class);
                intent.putExtra("mainHomeListItem",mainHomeListItem);
                startActivity(intent);
                break;
            default:
                break;

        }
    }

    private void achieveSearch(){
        Call<SearchResultBean> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).searchSubjects(MyApplication.getInstance().getLongitude() + "", MyApplication.getInstance().getLatitude() + "", realKeys, 0, WebParameters.PAGE_SIZE);
        addCall(call);
        call.enqueue(new Callback<SearchResultBean>() {
            @Override
            public void onResponse(Call<SearchResultBean> call, Response<SearchResultBean> response) {
                recyclerview.refreshComplete();
                SearchResultBean searchResultBean = response.body();
                if(searchResultBean.getRspCode() == 0){
                    List<MainHomeListItem> datas = searchResultBean.getData().getDatas();
                    if(datas == null){
                        datas = new ArrayList<MainHomeListItem>();
                    }
                    secondaryListAdapter.dataUpdate(datas);

                }else {
                    ToastUtil.showToast(searchResultBean.getRspMsg(),false,SecondaryListActivity.this);
                }
            }

            @Override
            public void onFailure(Call<SearchResultBean> call, Throwable t) {
                recyclerview.refreshComplete();
                ToastUtil.showToast(t.getMessage(),false,SecondaryListActivity.this);
            }
        });
    }


    //筛选结果下拉刷新
    private void haveChoose(String subjectName,String numberName,String floorName){
        Call<SecondlyResultBean> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).getSecondSubject(LoginUtils.getToken(SecondaryListActivity.this),2,mainHomeListItem.getLongitude(),mainHomeListItem.getLatitude(),subjectName,numberName,floorName,mainHomeListItem.getId(),1,0,WebParameters.PAGE_SIZE);
        addCall(call);
        call.enqueue(new Callback<SecondlyResultBean>() {
            @Override
            public void onResponse(Call<SecondlyResultBean> call, Response<SecondlyResultBean> response) {
                SecondlyResultBean body = response.body();
                if(body.getRspCode() == 0){
                    List<MainHomeListItem> datas = body.getData().getDatas();
                    if(datas == null){
                        datas = new ArrayList<MainHomeListItem>();
                    }
                    secondaryListAdapter.dataUpdate(datas);
                }else {
                    ToastUtil.showToast(body.getRspMsg(),false,SecondaryListActivity.this);
                }
                recyclerview.refreshComplete();

            }

            @Override
            public void onFailure(Call<SecondlyResultBean> call, Throwable t) {

            }
        });
    }

    //筛选结果上拉加载
    private void getChoose(String subjectName,String numberName,String floorName) {
        if(null!=mainHomeListItem){
            Call<SecondlyResultBean> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).getSecondSubject(LoginUtils.getToken(SecondaryListActivity.this),2, mainHomeListItem.getLongitude() + "", mainHomeListItem.getLatitude() + "",subjectName,numberName,floorName, mainHomeListItem.getId(), 1, dataList.size(), WebParameters.PAGE_SIZE);
            addCall(call);
            call.enqueue(new Callback<SecondlyResultBean>() {
                @Override
                public void onResponse(Call<SecondlyResultBean> call, Response<SecondlyResultBean> response) {
                    SecondlyResultBean body = response.body();
                    if(body.getRspCode() == 0){
                        List<MainHomeListItem> datas = body.getData().getDatas();
                        if(null!=datas&&datas.size()>0){
                            secondaryListAdapter.addData(datas);
                        }
                    }else {
                        ToastUtil.showToast(body.getRspMsg(),false,SecondaryListActivity.this);
                    }
                    recyclerview.loadMoreComplete();
                }

                @Override
                public void onFailure(Call<SecondlyResultBean> call, Throwable t) {
                    ToastUtil.showToast(t.getMessage(),false,SecondaryListActivity.this);
                    recyclerview.loadMoreComplete();

                }
            });
        }
    }
}

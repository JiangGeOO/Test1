package com.yidao.threekmo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.yidao.threekmo.bean.MainHomeListItem;
import com.yidao.threekmo.bean.SearchResultBean;
import com.yidao.threekmo.customview.FlowLayout;
import com.yidao.threekmo.db_bean.SearchHistroyBean;
import com.yidao.threekmo.parameter.WebParameters;
import com.yidao.threekmo.retrofit_server.SubjectServer;
import com.yidao.threekmo.utils.HistroyUtils;
import com.yidao.threekmo.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private EditText etSearch;
    private String keyword = "";
    private TextView tvCancle;
    private FlowLayout flHistroy;
    private LayoutInflater layoutInflater;
    private ImageView ivDelete;
    //历史记录模式 0:一般模式 1:删除模式
    private int histroyMode = 0;
    private TextView tvDeleteComplete;
    private ArrayList<String> histroyLIst;
    private FlowLayout flHot;
    private ArrayList<String> hotList;
    private LinearLayout blockSearchTags;
    private RelativeLayout blockNosearch;
    private Toolbar toolbar;
    private List<SearchHistroyBean> histroyBeanList;
    private XRecyclerView recyclerview;
    private SecondaryListAdapter secondaryListAdapter;
    private List<MainHomeListItem> dataList;
    private String realKeys = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();

        histroyTagInit();

        hotTagInit();

        recyclerviewInit();
    }

    private void recyclerviewInit() {
        recyclerview = (XRecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        secondaryListAdapter = new SecondaryListAdapter(SearchActivity.this);
        recyclerview.setAdapter(secondaryListAdapter);
        dataList = secondaryListAdapter.getDatas();

        secondaryListAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void itemClickLIstener(View view, int position) {
                position--;
//                Toast.makeText(SearchActivity.this,"天气不错" + position,Toast.LENGTH_SHORT).show();
                MainHomeListItem secondlyListItem = dataList.get(position);
                if(secondlyListItem.getLevel() == 1){
                    Intent intent = new Intent(SearchActivity.this, AscriptionSubActivity.class);
                    intent.putExtra("subJect", secondlyListItem.getId());
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(SearchActivity.this, SecondlyDetailActivity.class);
                    intent.putExtra("id", secondlyListItem.getId());
                    intent.putExtra("subJectId",secondlyListItem.getSubjectId());
                    intent.putExtra("haveSecond",-1);
                    startActivity(intent);
                }

            }
        });

        recyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshDatas();
            }

            @Override
            public void onLoadMore() {
                getMoreDatas();
            }
        });

    }

    private void histroyTagInit() {
        ivDelete = (ImageView) findViewById(R.id.ivDelete);
        ivDelete.setOnClickListener(this);
        tvDeleteComplete = (TextView) findViewById(R.id.tvDeleteComplete);
        tvDeleteComplete.setOnClickListener(this);

        flHistroy = (FlowLayout) findViewById(R.id.flHistroy);

        histroyLIst = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            histroyLIst.add("标签" + i);
        }

        histroyBeanList = HistroyUtils.getInstance(SearchActivity.this).getDatas();

        for (int i = 0; i < histroyBeanList.size(); i++)  //外循环是循环的次数
        {
            for (int j = histroyBeanList.size() - 1; j > i; j--)  //内循环是 外循环一次比较的次数
            {
                if (histroyBeanList.get(i).getHistroy().equals(histroyBeanList.get(j).getHistroy())) {
                    histroyBeanList.remove(j);
                }
            }
        }

        for (int i = 0; i < histroyBeanList.size(); i++) {
            View tagView = layoutInflater.inflate(R.layout.item_histroy, flHistroy, false);
            TextView tvTag = (TextView) tagView.findViewById(R.id.tvTag);
            tvTag.setText(histroyBeanList.get(i).getHistroy());
            flHistroy.addView(tagView,flHistroy.getChildCount());
        }

        flHistroy.setmOnTagClickListener(new FlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if(histroyMode == 1){
                    ToastUtil.showToast("删除标签" + histroyBeanList.get(position).getHistroy(),false,SearchActivity.this);
                    deleteTag(position);
                }else {
                    //搜索相关记录
                    realKeys = histroyBeanList.get(position).getHistroy();
                    refreshDatas();
                }

                return true;
            }
        });
    }


    private void hotTagInit() {
        flHot = (FlowLayout) findViewById(R.id.flHot);

        hotList = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            hotList.add("标签" + i);
        }

        for (int i = 0; i < hotList.size(); i++) {
            View tagView = layoutInflater.inflate(R.layout.item_hot, flHot, false);
            TextView tvTag = (TextView) tagView.findViewById(R.id.tvTag);
            tvTag.setText(hotList.get(i));
            flHot.addView(tagView,flHot.getChildCount());
        }

        flHot.setmOnTagClickListener(new FlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                realKeys = hotList.get(position);
                refreshDatas();
                return false;
            }
        });


    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //搜索标签
        blockSearchTags = (LinearLayout) findViewById(R.id.blockSearchTags);
        //没有搜索内容提示
        blockNosearch = (RelativeLayout) findViewById(R.id.blockNosearch);

        layoutInflater = LayoutInflater.from(SearchActivity.this);
        etSearch = (EditText) findViewById(R.id.etSearch);
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
                        HistroyUtils.getInstance(SearchActivity.this).insertData(realKeys);
                        refreshDatas();
                        KeyBoardUtils.closeKeybord(etSearch,SearchActivity.this);
                    }

                }

                return false;
            }
        });


        tvCancle = (TextView) findViewById(R.id.tvCancle);
        tvCancle.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancle:
                finish();
                break;
            case R.id.ivDelete:
                //显示删除x号
                ivDelete.setVisibility(View.GONE);
                tvDeleteComplete.setVisibility(View.VISIBLE);
                showDelete(true);
                histroyMode = 1;
                break;
            case R.id.tvDeleteComplete:
                //隐藏删除x号
                ivDelete.setVisibility(View.VISIBLE);
                tvDeleteComplete.setVisibility(View.GONE);
                showDelete(false);
                histroyMode = 0;
                break;

            default:
                break;
        }
    }

    private void deleteTag(int posi){
        SearchHistroyBean searchHistroyBean = histroyBeanList.get(posi);
        HistroyUtils.getInstance(SearchActivity.this).deleteData(searchHistroyBean);
        flHistroy.removeViewAt(posi);
        histroyBeanList.remove(posi);
    }

    private void showDelete(boolean b) {
        int childCount = flHistroy.getChildCount();
        if(b){
            //显示x号
            for (int i = 0; i < childCount; i++) {
                View childView = flHistroy.getChildAt(i);
                View view = childView.findViewById(R.id.ivDelete);
                view.setVisibility(View.VISIBLE);
            }
        }else {
            for (int i = 0; i < childCount; i++) {
                View childView = flHistroy.getChildAt(i);
                View view = childView.findViewById(R.id.ivDelete);
                view.setVisibility(View.GONE);
            }
        }

    }

    private void refreshDatas(){
        Call<SearchResultBean> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).searchSubjects(MyApplication.getInstance().getLongitude() + "", MyApplication.getInstance().getLatitude() + "", realKeys, 0, WebParameters.PAGE_SIZE);
        addCall(call);
        call.enqueue(new Callback<SearchResultBean>() {
            @Override
            public void onResponse(Call<SearchResultBean> call, Response<SearchResultBean> response) {
                recyclerview.refreshComplete();
                SearchResultBean searchResultBean = response.body();
                if(searchResultBean.getRspCode() == 0){
                    List<MainHomeListItem> datas = searchResultBean.getData().getDatas();
                    if(null!=datas && datas.size()>0){
                        blockSearchTags.setVisibility(View.GONE);
                        blockNosearch.setVisibility(View.GONE);
                        recyclerview.setVisibility(View.VISIBLE);
                        secondaryListAdapter.dataUpdate(datas);
                    }else {
                        recyclerview.setVisibility(View.GONE);
                        blockSearchTags.setVisibility(View.GONE);
                        blockNosearch.setVisibility(View.VISIBLE);
                    }

                }else {
                    ToastUtil.showToast(searchResultBean.getRspMsg(),false,SearchActivity.this);
                }
            }

            @Override
            public void onFailure(Call<SearchResultBean> call, Throwable t) {
                recyclerview.refreshComplete();
                ToastUtil.showToast(t.getMessage(),false,SearchActivity.this);
            }
        });
    }

    private void getMoreDatas(){
        Call<SearchResultBean> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).searchSubjects(MyApplication.getInstance().getLongitude() + "", MyApplication.getInstance().getLatitude() + "", realKeys, dataList.size(), WebParameters.PAGE_SIZE);
        addCall(call);
        call.enqueue(new Callback<SearchResultBean>() {
            @Override
            public void onResponse(Call<SearchResultBean> call, Response<SearchResultBean> response) {
                recyclerview.loadMoreComplete();
                SearchResultBean searchResultBean = response.body();
                if(searchResultBean.getRspCode() == 0){
                   if(searchResultBean.getData().getDatas().size()>0){
                       secondaryListAdapter.addData(searchResultBean.getData().getDatas());
                   }


                }else {
                    ToastUtil.showToast(searchResultBean.getRspMsg(),false,SearchActivity.this);
                }
            }

            @Override
            public void onFailure(Call<SearchResultBean> call, Throwable t) {
                ToastUtil.showToast(t.getMessage(),false,SearchActivity.this);
                recyclerview.loadMoreComplete();
            }
        });
    }
}

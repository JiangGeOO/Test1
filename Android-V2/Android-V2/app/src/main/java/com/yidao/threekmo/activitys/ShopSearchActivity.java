package com.yidao.threekmo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
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
import com.yidao.threekmo.adapter.ShopSearchAdapter;
import com.yidao.threekmo.bean.ShopDetailResult;
import com.yidao.threekmo.bean.ShopListResult;
import com.yidao.threekmo.customview.FlowLayout;
import com.yidao.threekmo.customview.GridSpacingItemDecoration;
import com.yidao.threekmo.db_bean.SearchHistroyBean;
import com.yidao.threekmo.retrofit_server.SubjectServer;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.HistroyUtils;
import com.yidao.threekmo.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShopSearchActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout search_rela;
    private EditText search_edit;
    private TextView search_cancle;
    private ImageView search_image;

    private String keyword = "";
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
    private List<SearchHistroyBean> histroyBeanList;
    private XRecyclerView recyclerview;
    //    private ActiveSearchAdapter activeSearchAdapter;
    private ShopSearchAdapter newShopAdapter;
    private List<ShopDetailResult> dataList;
    private String realKeys = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_search);

        initView();

        histroyTagInit();

        hotTagInit();

        recyclerviewInit();
    }

    private void recyclerviewInit() {
        recyclerview = (XRecyclerView) findViewById(R.id.recyclerview);
        newShopAdapter = new ShopSearchAdapter(ShopSearchActivity.this);
        recyclerview.addItemDecoration(new GridSpacingItemDecoration(2, CommonUtil.getRealWidth(20), false));
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerview.setAdapter(newShopAdapter);
        dataList = newShopAdapter.getDatas();
        newShopAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void itemClickLIstener(View view, int position) {
                position --;
                Intent intent = new Intent(ShopSearchActivity.this, ShopDetailActivity.class);
                intent.putExtra("id", dataList.get(position).getId());
                startActivity(intent);
            }
        });


        recyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }

            @Override
            public void onLoadMore() {
                loadMoreList();
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

        histroyBeanList = HistroyUtils.getInstance(ShopSearchActivity.this).getDatas();

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
            flHistroy.addView(tagView, flHistroy.getChildCount());
        }

        flHistroy.setmOnTagClickListener(new FlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (histroyMode == 1) {
                    ToastUtil.showToast("删除标签" + histroyBeanList.get(position).getHistroy(), false, ShopSearchActivity.this);
                    deleteTag(position);
                } else {
                    //搜索相关记录
                    realKeys = histroyBeanList.get(position).getHistroy();
                    search_edit.setText(realKeys + "");
                    refreshList();
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
            flHot.addView(tagView, flHot.getChildCount());
        }

        flHot.setmOnTagClickListener(new FlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                realKeys = hotList.get(position);
                refreshList();
                return false;
            }
        });


    }

    private void initView() {

        search_rela = (RelativeLayout) findViewById(R.id.search_rela);
        LinearLayout.LayoutParams llp_search_rela = (LinearLayout.LayoutParams) search_rela.getLayoutParams();
        llp_search_rela.height = CommonUtil.getRealWidth(130);

        //搜索标签
        blockSearchTags = (LinearLayout) findViewById(R.id.blockSearchTags);
        //没有搜索内容提示
        blockNosearch = (RelativeLayout) findViewById(R.id.blockNosearch);

        layoutInflater = LayoutInflater.from(ShopSearchActivity.this);
        search_edit = (EditText) findViewById(R.id.search_edit);
        search_edit.setPadding(CommonUtil.getRealWidth(60), 0, 0, 0);
        search_edit.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_search_edit = (RelativeLayout.LayoutParams) search_edit.getLayoutParams();
        llp_search_edit.width = CommonUtil.getRealWidth(552);
        llp_search_edit.height = CommonUtil.getRealWidth(60);
        llp_search_edit.leftMargin = CommonUtil.getRealWidth(60);
        llp_search_edit.topMargin = CommonUtil.getRealWidth(60);

        search_edit.addTextChangedListener(new TextWatcher() {
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

        search_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //搜索
                    if (!TextUtils.isEmpty(keyword)) {
                        realKeys = keyword;
                        LogUtils.e("搜索keyword:" + realKeys);
                        HistroyUtils.getInstance(ShopSearchActivity.this).insertData(realKeys);
                        refreshList();
                        KeyBoardUtils.closeKeybord(search_edit, ShopSearchActivity.this);
                    }

                }

                return false;
            }
        });


        search_cancle = (TextView) findViewById(R.id.search_cancle);
        search_cancle.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(36));
        RelativeLayout.LayoutParams llp_search_cancle = (RelativeLayout.LayoutParams) search_cancle.getLayoutParams();
        llp_search_cancle.rightMargin = CommonUtil.getRealWidth(30);
        llp_search_cancle.topMargin = CommonUtil.getRealWidth(66);
        search_cancle.setOnClickListener(this);

        search_image = (ImageView) findViewById(R.id.search_image);
        RelativeLayout.LayoutParams llp_search_image = (RelativeLayout.LayoutParams) search_image.getLayoutParams();
        llp_search_image.topMargin = CommonUtil.getRealWidth(60);
        llp_search_image.leftMargin = CommonUtil.getRealWidth(60);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_cancle:
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

    private void deleteTag(int posi) {
        SearchHistroyBean searchHistroyBean = histroyBeanList.get(posi);
        HistroyUtils.getInstance(ShopSearchActivity.this).deleteData(searchHistroyBean);
        flHistroy.removeViewAt(posi);
        histroyBeanList.remove(posi);
    }

    private void showDelete(boolean b) {
        int childCount = flHistroy.getChildCount();
        if (b) {
            //显示x号
            for (int i = 0; i < childCount; i++) {
                View childView = flHistroy.getChildAt(i);
                View view = childView.findViewById(R.id.ivDelete);
                view.setVisibility(View.VISIBLE);
            }
        } else {
            for (int i = 0; i < childCount; i++) {
                View childView = flHistroy.getChildAt(i);
                View view = childView.findViewById(R.id.ivDelete);
                view.setVisibility(View.GONE);
            }
        }

    }
    private void refreshList() {
        Call<ShopListResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).shopList(realKeys, 0, 15);
        addCall(call);
        call.enqueue(new Callback<ShopListResult>() {
            @Override
            public void onResponse(Call<ShopListResult> call, Response<ShopListResult> response) {
                ShopListResult body = response.body();
                if(body != null){
                    if (body.getRspCode() == 0) {
                        dataList = body.getData().getDatas();
//                    for(int i =0; i < 13;i++){
//                        dataList.add(new ShopDetailResult());
//                    }
                        if (null != dataList && dataList.size() > 0) {
                            blockSearchTags.setVisibility(View.GONE);
                            blockNosearch.setVisibility(View.GONE);
                            recyclerview.setVisibility(View.VISIBLE);
                            newShopAdapter.dataUpdate((ArrayList<ShopDetailResult>) dataList);
                        } else {
                            recyclerview.setVisibility(View.GONE);
                            blockSearchTags.setVisibility(View.GONE);
                            blockNosearch.setVisibility(View.VISIBLE);
                        }
                        recyclerview.refreshComplete();
                    }
                }

            }

            @Override
            public void onFailure(Call<ShopListResult> call, Throwable t) {

            }
        });
    }

    private void loadMoreList() {
        Call<ShopListResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).shopList(realKeys, dataList.size(), 15);
        addCall(call);
        call.enqueue(new Callback<ShopListResult>() {
            @Override
            public void onResponse(Call<ShopListResult> call, Response<ShopListResult> response) {
                ShopListResult body = response.body();
                if (body.getRspCode() == 0) {
                    List<ShopDetailResult> dataList = body.getData().getDatas();
                    newShopAdapter.addData((ArrayList<ShopDetailResult>) dataList);
                    recyclerview.loadMoreComplete();
                }
            }

            @Override
            public void onFailure(Call<ShopListResult> call, Throwable t) {

            }
        });
    }
}

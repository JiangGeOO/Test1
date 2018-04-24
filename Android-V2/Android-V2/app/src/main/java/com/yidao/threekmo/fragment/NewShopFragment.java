package com.yidao.threekmo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yidao.threekmo.R;
import com.yidao.threekmo.activitys.IndexActivity;
import com.yidao.threekmo.activitys.ShopDetailActivity;
import com.yidao.threekmo.activitys.ShopSearchActivity;
import com.yidao.threekmo.adapter.BaseRecyclerAdapter;
import com.yidao.threekmo.adapter.BaseRvAdapter;
import com.yidao.threekmo.adapter.NewShopAdapter;
import com.yidao.threekmo.adapter.ShopHeaderBaoAdapter;
import com.yidao.threekmo.adapter.ShopHeaderHotAdapter;
import com.yidao.threekmo.adapter.ShopHeaderJingAdapter;
import com.yidao.threekmo.adapter.ShopHeaderViewPagerAdapter;
import com.yidao.threekmo.bean.ShopDetailResult;
import com.yidao.threekmo.bean.ShopIndexResult;
import com.yidao.threekmo.bean.ShopListResult;
import com.yidao.threekmo.customview.GridSpacingItemDecoration;
import com.yidao.threekmo.retrofit_server.SubjectServer;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Smart~ on 2017/11/8.
 */

public class NewShopFragment extends BaseFragment {

    private List<ShopDetailResult> dataList;

    private RelativeLayout title_rela;
    private ImageView search_image;
    private TextView title_search;
    private XRecyclerView recyclerView;
    private RelativeLayout all_title;
    private TextView all_text;
    private ImageView all_left;
    private ImageView all_right;

    //头布局
    private RelativeLayout viewPager_rela;
    private RelativeLayout hot_rela;
    private RelativeLayout bao_rela;
    private RelativeLayout jing_rela;
    private RelativeLayout header_title_rela;
    private ViewPager viewPager_rela_viewPager;
    private ImageView header_title_rela_left;
    private ImageView header_title_rela_right;
    private TextView header_title_rela_text;
    private RelativeLayout hot_rela_rela;
    private ImageView hot_rela_image;
    private TextView hot_rela_text;
    private TextView bao_rela_text;
    private RelativeLayout jing_rela_rela;
    private ImageView jing_rela_image;
    private TextView jing_rela_text;
    private RecyclerView hot_recyclerView;
    private RecyclerView bao_recyclerView;
    private RecyclerView jing_recyclerView;
    private LinearLayout viewPager_rela_point;


    //头布局变量
    private List<ImageView> imageList = new ArrayList<ImageView>();

    //头布局适配器
    private ShopHeaderViewPagerAdapter shopHeaderViewPagerAdapter;
    private ShopHeaderHotAdapter shopHeaderHotAdapter;
    private ShopHeaderJingAdapter shopHeaderJingAdapter;
    private ShopHeaderBaoAdapter shopHeaderBaoAdapter;

    private NewShopAdapter newShopAdapter;


    private List<ShopDetailResult> listBurst;
    private List<ShopDetailResult> listHandpick;
    private List<ShopDetailResult> listHot;
    private List<ShopDetailResult> listBanner;

    private static final int UPTATE_VIEWPAGER = 0;
    //设置当前 第几个图片 被选中
    private int autoCurrIndex = 0;
    //Timer定时器
//    public Timer timer = new Timer();
    public Timer timer;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPTATE_VIEWPAGER:
                    if (msg.arg1 != 0) {
                        viewPager_rela_viewPager.setCurrentItem(msg.arg1);
                    } else {
                        viewPager_rela_viewPager.setCurrentItem(msg.arg1, false);
                    }
                    break;

                default:
                    break;
            }
        }
    };

    public static final NewShopFragment newInstance(String title) {
        NewShopFragment f = new NewShopFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString(title, title);
        f.setArguments(bdl);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_shop, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();
        // 设置自动轮播图片，5s后执行，周期是5s
        recyclerView.refresh();

    }

    @Override
    public void onResume() {
        super.onResume();

        if(imageList != null){
            shopHeaderViewPagerAdapter.setData(imageList);
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = UPTATE_VIEWPAGER;
                if (autoCurrIndex == imageList.size() - 1) {
                    autoCurrIndex = -1;
                }
                message.arg1 = autoCurrIndex + 1;
                mHandler.sendMessage(message);
            }
        }, 3000, 3000);
    }

    @Override
    public void onPause() {
        super.onPause();
//        imageList.clear();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void setData() {

        int size = listBanner.size();
        if(size != 0){
            viewPager_rela_point.removeAllViews();
            viewPager_rela_point.invalidate();
            imageList.clear();
        }

        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(getActivity());
            ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
            layoutParams.width = ViewPager.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewPager.LayoutParams.MATCH_PARENT;
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(getActivity()).load(listBanner.get(i).getPhoto()).into(imageView);
            imageList.add(imageView);
        }
        shopHeaderViewPagerAdapter.setData(imageList);

        shopHeaderViewPagerAdapter.setOnPagerItemClickListener(new ShopHeaderViewPagerAdapter.OnPagerItemClickListener() {
            @Override
            public void onItem(int position) {
                Intent intent = new Intent(getActivity(), ShopDetailActivity.class);
                intent.putExtra("id", listBanner.get(position).getMerchandiseId());
                startActivity(intent);
            }
        });


        shopHeaderHotAdapter.dataUpdate(listHot);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hot_recyclerView.setLayoutManager(linearLayoutManager);
        shopHeaderHotAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void itemClickLIstener(View view, int position) {
//                Toast.makeText(getActivity(),position + "",Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getActivity(), OrderRelaActivity.class));
                Intent intent = new Intent(getActivity(), ShopDetailActivity.class);
                intent.putExtra("id", listHot.get(position).getMerchandiseId());
                startActivity(intent);
            }
        });

        shopHeaderJingAdapter.dataUpdate(listHandpick);
        LinearLayoutManager linearLayoutManagerj = new LinearLayoutManager(getActivity());
        linearLayoutManagerj.setOrientation(LinearLayoutManager.HORIZONTAL);
        jing_recyclerView.setLayoutManager(linearLayoutManagerj);
        shopHeaderJingAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void itemClickLIstener(View view, int position) {
//                Toast.makeText(getActivity(),position + "",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ShopDetailActivity.class);
                intent.putExtra("id", listHandpick.get(position).getMerchandiseId());
                startActivity(intent);
            }
        });

        shopHeaderBaoAdapter.dataUpdate(listBurst);
        LinearLayoutManager linearLayoutManagerb = new LinearLayoutManager(getActivity());
        linearLayoutManagerb.setOrientation(LinearLayoutManager.HORIZONTAL);
        bao_recyclerView.setLayoutManager(linearLayoutManagerb);
        shopHeaderBaoAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void itemClickLIstener(View view, int position) {
//                Toast.makeText(getActivity(),position + "",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ShopDetailActivity.class);
                intent.putExtra("id", listBurst.get(position).getMerchandiseId());
                startActivity(intent);
            }
        });

        if (imageList != null && imageList.size() > 0) {
            for (int i = 0; i < imageList.size(); i++) {
                ImageView image = new ImageView(getActivity());
                image.setBackgroundResource(R.drawable.point_up);
                LinearLayout.LayoutParams llp_image = new LinearLayout.LayoutParams(CommonUtil.getRealWidth(15), CommonUtil.getRealWidth(15));
                llp_image.leftMargin = CommonUtil.getRealWidth(15);
                llp_image.bottomMargin = CommonUtil.getRealWidth(10);
                image.setLayoutParams(llp_image);
                viewPager_rela_point.addView(image);
            }
            viewPager_rela_point.getChildAt(0).setBackgroundResource(R.drawable.point_down);
            viewPager_rela_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (imageList.size() > 1) {
                        for (int i = 0; i < imageList.size(); i++) {
                            if (i == position) {
                                viewPager_rela_point.getChildAt(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.point_down));
                            } else {
                                viewPager_rela_point.getChildAt(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.point_up));
                            }
                        }
                    }
                    //设置全局变量，currentIndex为选中图标的 index
                    autoCurrIndex = position;
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }


    }

    private void initViews() {
        title_rela = (RelativeLayout) getView().findViewById(R.id.title_rela);
        RelativeLayout.LayoutParams llp_title_rela = (RelativeLayout.LayoutParams) title_rela.getLayoutParams();
        llp_title_rela.height = CommonUtil.getRealWidth(130);

        search_image = (ImageView) getView().findViewById(R.id.search_image);
        RelativeLayout.LayoutParams llp_search_image = (RelativeLayout.LayoutParams) search_image.getLayoutParams();
        llp_search_image.width = CommonUtil.getRealWidth(28);
        llp_search_image.height = CommonUtil.getRealWidth(30);
        llp_search_image.leftMargin = CommonUtil.getRealWidth(46);
        llp_search_image.topMargin = CommonUtil.getRealWidth(70);

        title_search = (TextView) getView().findViewById(R.id.title_search);
        title_search.setPadding(CommonUtil.getRealWidth(60), 0, 0, 0);
        title_search.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_title_search = (RelativeLayout.LayoutParams) title_search.getLayoutParams();
        llp_title_search.height = CommonUtil.getRealWidth(60);
        llp_title_search.leftMargin = CommonUtil.getRealWidth(30);
        llp_title_search.rightMargin = CommonUtil.getRealWidth(30);
        llp_title_search.topMargin = CommonUtil.getRealWidth(54);

        all_title = (RelativeLayout) getView().findViewById(R.id.all_title);
        RelativeLayout.LayoutParams llp_all_title = (RelativeLayout.LayoutParams) all_title.getLayoutParams();
        llp_all_title.height = CommonUtil.getRealWidth(74);

        all_text = (TextView) getView().findViewById(R.id.all_text);
        all_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));

        all_left = (ImageView) getView().findViewById(R.id.all_left);
        RelativeLayout.LayoutParams llp_all_left = (RelativeLayout.LayoutParams) all_left.getLayoutParams();
        llp_all_left.width = CommonUtil.getRealWidth(164);
        llp_all_left.height = CommonUtil.getRealWidth(12);
        llp_all_left.leftMargin = CommonUtil.getRealWidth(130);

        all_right = (ImageView) getView().findViewById(R.id.all_right);
        RelativeLayout.LayoutParams llp_all_right = (RelativeLayout.LayoutParams) all_right.getLayoutParams();
        llp_all_right.width = CommonUtil.getRealWidth(164);
        llp_all_right.height = CommonUtil.getRealWidth(12);
        llp_all_right.rightMargin = CommonUtil.getRealWidth(130);

        recyclerView = (XRecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, CommonUtil.getRealWidth(20), false));
        newShopAdapter = new NewShopAdapter(getActivity());
//        newShopAdapter.addDatas((ArrayList<ShopDetailResult>) dataList);
        dataList = newShopAdapter.getDatas();
        recyclerView.setAdapter(newShopAdapter);
        setHeader(recyclerView);
        newShopAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                position--;
//                Toast.makeText(getActivity(),position + "",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ShopDetailActivity.class);
                intent.putExtra("id", dataList.get(position).getId());
                startActivity(intent);
            }
        });

        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
//                recyclerView.refreshComplete();
                refreshList();
                achieveData();
            }

            @Override
            public void onLoadMore() {
//                recyclerView.loadMoreComplete();
                loadMoreList();
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
                int[] firstVisibleItems = null;
                firstVisibleItems = ((StaggeredGridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPositions(firstVisibleItems);
                if (firstVisibleItems[1] == 1) {
                    all_title.setVisibility(View.INVISIBLE);
                } else {
                    if (firstVisibleItems[0] != 0) {
                        all_title.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        //viewpager


        title_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShopSearchActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setHeader(RecyclerView view) {
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.header_new_shop, view, false);

        viewPager_rela = (RelativeLayout) header.findViewById(R.id.viewPager_rela);
        LinearLayout.LayoutParams llp_viewPager_rela = (LinearLayout.LayoutParams) viewPager_rela.getLayoutParams();
        llp_viewPager_rela.height = CommonUtil.getRealWidth(344);

        hot_rela = (RelativeLayout) header.findViewById(R.id.hot_rela);
        LinearLayout.LayoutParams llp_hot_rela = (LinearLayout.LayoutParams) hot_rela.getLayoutParams();
        llp_hot_rela.height = CommonUtil.getRealWidth(532);

        bao_rela = (RelativeLayout) header.findViewById(R.id.bao_rela);
        LinearLayout.LayoutParams llp_bao_rela = (LinearLayout.LayoutParams) bao_rela.getLayoutParams();
        llp_bao_rela.height = CommonUtil.getRealWidth(524);
        llp_bao_rela.topMargin = CommonUtil.getRealWidth(20);

        jing_rela = (RelativeLayout) header.findViewById(R.id.jing_rela);
        LinearLayout.LayoutParams llp_jing_rela = (LinearLayout.LayoutParams) jing_rela.getLayoutParams();
        llp_jing_rela.height = CommonUtil.getRealWidth(452);
        llp_jing_rela.topMargin = CommonUtil.getRealWidth(20);

        header_title_rela = (RelativeLayout) header.findViewById(R.id.header_title_rela);
        LinearLayout.LayoutParams llp_header_title_rela = (LinearLayout.LayoutParams) header_title_rela.getLayoutParams();
        llp_header_title_rela.height = CommonUtil.getRealWidth(92);
        llp_header_title_rela.topMargin = CommonUtil.getRealWidth(20);

        header_title_rela_left = (ImageView) header.findViewById(R.id.header_title_rela_left);
        RelativeLayout.LayoutParams llp_header_title_rela_left = (RelativeLayout.LayoutParams) header_title_rela_left.getLayoutParams();
        llp_header_title_rela_left.width = CommonUtil.getRealWidth(164);
        llp_header_title_rela_left.height = CommonUtil.getRealWidth(12);
        llp_header_title_rela_left.leftMargin = CommonUtil.getRealWidth(130);

        header_title_rela_right = (ImageView) header.findViewById(R.id.header_title_rela_right);
        RelativeLayout.LayoutParams llp_header_title_rela_right = (RelativeLayout.LayoutParams) header_title_rela_right.getLayoutParams();
        llp_header_title_rela_right.width = CommonUtil.getRealWidth(164);
        llp_header_title_rela_right.height = CommonUtil.getRealWidth(12);
        llp_header_title_rela_right.rightMargin = CommonUtil.getRealWidth(130);

        header_title_rela_text = (TextView) header.findViewById(R.id.header_title_rela_text);
        header_title_rela_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(36));

        hot_rela_rela = (RelativeLayout) header.findViewById(R.id.hot_rela_rela);
        RelativeLayout.LayoutParams llp_hot_rela_rela = (RelativeLayout.LayoutParams) hot_rela_rela.getLayoutParams();
        llp_hot_rela_rela.height = CommonUtil.getRealWidth(78);

        hot_rela_image = (ImageView) header.findViewById(R.id.hot_rela_image);
        RelativeLayout.LayoutParams llp_hot_rela_image = (RelativeLayout.LayoutParams) hot_rela_image.getLayoutParams();
        llp_hot_rela_image.width = CommonUtil.getRealWidth(6);
        llp_hot_rela_image.height = CommonUtil.getRealWidth(42);
        llp_hot_rela_image.leftMargin = CommonUtil.getRealWidth(30);

        hot_rela_text = (TextView) header.findViewById(R.id.hot_rela_text);
        hot_rela_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(36));
        RelativeLayout.LayoutParams llp_hot_rela_text = (RelativeLayout.LayoutParams) hot_rela_text.getLayoutParams();
        llp_hot_rela_text.leftMargin = CommonUtil.getRealWidth(12);

        bao_rela_text = (TextView) header.findViewById(R.id.bao_rela_text);
        bao_rela_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(36));
        RelativeLayout.LayoutParams llp_bao_rela_text = (RelativeLayout.LayoutParams) bao_rela_text.getLayoutParams();
        llp_bao_rela_text.leftMargin = CommonUtil.getRealWidth(30);
        llp_bao_rela_text.topMargin = CommonUtil.getRealWidth(22);

        jing_rela_rela = (RelativeLayout) header.findViewById(R.id.jing_rela_rela);
        RelativeLayout.LayoutParams llp_jing_rela_rela = (RelativeLayout.LayoutParams) jing_rela_rela.getLayoutParams();
        llp_jing_rela_rela.height = CommonUtil.getRealWidth(78);

        jing_rela_image = (ImageView) header.findViewById(R.id.jing_rela_image);
        RelativeLayout.LayoutParams llp_jing_rela_image = (RelativeLayout.LayoutParams) jing_rela_image.getLayoutParams();
        llp_jing_rela_image.width = CommonUtil.getRealWidth(6);
        llp_jing_rela_image.height = CommonUtil.getRealWidth(40);
        llp_jing_rela_image.leftMargin = CommonUtil.getRealWidth(30);

        jing_rela_text = (TextView) header.findViewById(R.id.jing_rela_text);
        jing_rela_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(36));
        RelativeLayout.LayoutParams llp_jing_rela_text = (RelativeLayout.LayoutParams) jing_rela_text.getLayoutParams();
        llp_jing_rela_text.leftMargin = CommonUtil.getRealWidth(14);

        viewPager_rela_viewPager = (ViewPager) header.findViewById(R.id.viewPager_rela_viewPager);
        hot_recyclerView = (RecyclerView) header.findViewById(R.id.hot_recyclerView);
        bao_recyclerView = (RecyclerView) header.findViewById(R.id.bao_recyclerView);
        jing_recyclerView = (RecyclerView) header.findViewById(R.id.jing_recyclerView);

        shopHeaderViewPagerAdapter = new ShopHeaderViewPagerAdapter(getActivity(), imageList);
        shopHeaderHotAdapter = new ShopHeaderHotAdapter(getActivity());
        shopHeaderJingAdapter = new ShopHeaderJingAdapter(getActivity());
        shopHeaderBaoAdapter = new ShopHeaderBaoAdapter(getActivity());

        viewPager_rela_viewPager.setAdapter(shopHeaderViewPagerAdapter);
        hot_recyclerView.setAdapter(shopHeaderHotAdapter);
        jing_recyclerView.setAdapter(shopHeaderJingAdapter);
        bao_recyclerView.setAdapter(shopHeaderBaoAdapter);


        viewPager_rela_point = (LinearLayout) header.findViewById(R.id.viewPager_rela_point);
        RelativeLayout.LayoutParams llp_viewPager_rela_point = (RelativeLayout.LayoutParams) viewPager_rela_point.getLayoutParams();
        llp_viewPager_rela_point.bottomMargin = CommonUtil.getRealWidth(20);

        newShopAdapter.setHeaderView(header);
    }

    private void achieveData() {
        Call<ShopIndexResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).getShopShow();
        ((IndexActivity) getActivity()).addCall(call);
        call.enqueue(new Callback<ShopIndexResult>() {
            @Override
            public void onResponse(Call<ShopIndexResult> call, Response<ShopIndexResult> response) {
                if (getActivity() == null) return;
                ShopIndexResult body = response.body();
                if (body != null) {
                    if (body.getRspCode() == 0) {
                        listBurst = body.getData().getListBurst();
                        listBanner = body.getData().getListBanner();
                        //精选
                        listHandpick = body.getData().getListHandpick();
                        listHot = body.getData().getListHot();
                        setData();
                    }
                }


            }

            @Override
            public void onFailure(Call<ShopIndexResult> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void refreshList() {
        Call<ShopListResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).shopList("", 0, 15);
        ((IndexActivity) getActivity()).addCall(call);
        call.enqueue(new Callback<ShopListResult>() {
            @Override
            public void onResponse(Call<ShopListResult> call, Response<ShopListResult> response) {
                ShopListResult body = response.body();
                if (body != null) {
                    if (body.getRspCode() == 0) {
                        dataList = body.getData().getDatas();
                        if(dataList == null){
                            dataList = new ArrayList<ShopDetailResult>();
                        }
                        newShopAdapter.setDatas((ArrayList<ShopDetailResult>) dataList);
                        recyclerView.refreshComplete();
                    }
                }

            }

            @Override
            public void onFailure(Call<ShopListResult> call, Throwable t) {

            }
        });
    }

    private void loadMoreList() {
        Call<ShopListResult> call = RetrofitUtils.getRetrofit().create(SubjectServer.class).shopList("", dataList.size(), 15);
        ((IndexActivity) getActivity()).addCall(call);
        call.enqueue(new Callback<ShopListResult>() {
            @Override
            public void onResponse(Call<ShopListResult> call, Response<ShopListResult> response) {
                ShopListResult body = response.body();
                if (body != null) {
                    if (body.getRspCode() == 0) {
                        List<ShopDetailResult> dataList = body.getData().getDatas();
                        newShopAdapter.addDatas((ArrayList<ShopDetailResult>) dataList);
                        recyclerView.loadMoreComplete();
                    }
                }

            }

            @Override
            public void onFailure(Call<ShopListResult> call, Throwable t) {

            }
        });
    }



}

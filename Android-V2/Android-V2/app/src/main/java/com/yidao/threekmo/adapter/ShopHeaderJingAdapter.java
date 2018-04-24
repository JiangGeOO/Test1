package com.yidao.threekmo.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yidao.threekmo.R;
import com.yidao.threekmo.bean.ShopDetailResult;
import com.yidao.threekmo.utils.CommonUtil;

/**
 * Created by Smart~ on 2017/11/9.
 */

public class ShopHeaderJingAdapter extends BaseRvAdapter<ShopDetailResult> {

    public ShopHeaderJingAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = layoutInflater.inflate(R.layout.item_shop_jing,parent,false);
        return new MyHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        ShopDetailResult shopDetailResult = dataList.get(position);
        Glide.with(context).load(shopDetailResult.getPhoto()).into(myHolder.item_jing_image);
        myHolder.item_jing_info.setText(shopDetailResult.getTitle());
        myHolder.item_jing_ago_money.setText("￥" + shopDetailResult.getOriginalPrice());
        myHolder.item_jing_now_money.setText("￥" + shopDetailResult.getSellingPrice());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyHolder extends BaseRvAdapter.BaseViewHolder{

        RelativeLayout item_jing;
        ImageView item_jing_image;
        TextView item_jing_now_money;
        TextView item_jing_ago_money;
        TextView item_jing_info;

        public MyHolder(View itemView) {
            super(itemView);

            item_jing = (RelativeLayout) itemView.findViewById(R.id.item_jing);
            RelativeLayout.LayoutParams llp_item_jing = (RelativeLayout.LayoutParams)item_jing.getLayoutParams();
            llp_item_jing.width = CommonUtil.getRealWidth(250);
            llp_item_jing.leftMargin = CommonUtil.getRealWidth(20);

            item_jing_image = (ImageView) itemView.findViewById(R.id.item_jing_image);
            RelativeLayout.LayoutParams llp_item_jing_image = (RelativeLayout.LayoutParams)item_jing_image.getLayoutParams();
            llp_item_jing_image.width = CommonUtil.getRealWidth(250);
            llp_item_jing_image.height = llp_item_jing_image.width;
            llp_item_jing_image.topMargin = CommonUtil.getRealWidth(16);

            item_jing_now_money = (TextView) itemView.findViewById(R.id.item_jing_now_money);
            item_jing_now_money.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
            RelativeLayout.LayoutParams llp_item_jing_now_money = (RelativeLayout.LayoutParams)item_jing_now_money.getLayoutParams();
            llp_item_jing_now_money.leftMargin = CommonUtil.getRealWidth(40);
            llp_item_jing_now_money.topMargin = CommonUtil.getRealWidth(6);

            item_jing_ago_money = (TextView) itemView.findViewById(R.id.item_jing_ago_money);
            item_jing_ago_money.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            item_jing_ago_money.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
            RelativeLayout.LayoutParams llp_item_jing_ago_money = (RelativeLayout.LayoutParams)item_jing_ago_money.getLayoutParams();
            llp_item_jing_ago_money.leftMargin = CommonUtil.getRealWidth(8);
            llp_item_jing_ago_money.topMargin = CommonUtil.getRealWidth(10);

            item_jing_info = (TextView) itemView.findViewById(R.id.item_jing_info);
            item_jing_info.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
            RelativeLayout.LayoutParams llp_item_jing_info = (RelativeLayout.LayoutParams)item_jing_info.getLayoutParams();
            llp_item_jing_info.topMargin = CommonUtil.getRealWidth(2);
            llp_item_jing_info.bottomMargin = CommonUtil.getRealWidth(14);


        }
    }


}

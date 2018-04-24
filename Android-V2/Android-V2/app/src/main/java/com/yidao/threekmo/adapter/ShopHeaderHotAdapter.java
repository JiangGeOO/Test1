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

public class ShopHeaderHotAdapter extends BaseRvAdapter<ShopDetailResult> {

    public ShopHeaderHotAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = layoutInflater.inflate(R.layout.item_shop_hot, parent, false);
        return new MyHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder viewHolder = (MyHolder) holder;
        ShopDetailResult shopDetailResult = dataList.get(position);

        Glide.with(context).load(shopDetailResult.getPhoto()).into(viewHolder.item_image);
        viewHolder.item_info.setText(shopDetailResult.getTitle());
        viewHolder.item_ago_money.setText("￥" + shopDetailResult.getOriginalPrice());
        viewHolder.item_now_money.setText("￥限时价：" + shopDetailResult.getSellingPrice());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyHolder extends BaseRvAdapter.BaseViewHolder {

        RelativeLayout item_hot;
        ImageView item_image;
        TextView item_info;
        TextView item_ago_money;
        TextView item_now_money;

        public MyHolder(View itemView) {
            super(itemView);
            item_hot = (RelativeLayout) itemView.findViewById(R.id.item_hot);
            RelativeLayout.LayoutParams llp_item_hot = (RelativeLayout.LayoutParams) item_hot.getLayoutParams();
            llp_item_hot.leftMargin = CommonUtil.getRealWidth(20);
            llp_item_hot.width = CommonUtil.getRealWidth(250);

            item_image = (ImageView) itemView.findViewById(R.id.item_image);
            RelativeLayout.LayoutParams llp_item_image = (RelativeLayout.LayoutParams) item_image.getLayoutParams();
            llp_item_image.width = CommonUtil.getRealWidth(250);
            llp_item_image.height = llp_item_hot.width;
            llp_item_image.topMargin = CommonUtil.getRealWidth(16);

            item_info = (TextView) itemView.findViewById(R.id.item_info);
            item_info.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
            RelativeLayout.LayoutParams llp_item_info = (RelativeLayout.LayoutParams) item_info.getLayoutParams();
            llp_item_info.topMargin = CommonUtil.getRealWidth(6);

            item_ago_money = (TextView) itemView.findViewById(R.id.item_ago_money);
            item_ago_money.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            item_ago_money.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
            RelativeLayout.LayoutParams llp_item_ago_money = (RelativeLayout.LayoutParams) item_ago_money.getLayoutParams();
            llp_item_ago_money.topMargin = CommonUtil.getRealWidth(4);

            item_now_money = (TextView) itemView.findViewById(R.id.item_now_money);
            item_now_money.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
            RelativeLayout.LayoutParams llp_item_now_money = (RelativeLayout.LayoutParams) item_now_money.getLayoutParams();
            llp_item_now_money.topMargin = CommonUtil.getRealWidth(4);
            llp_item_now_money.bottomMargin = CommonUtil.getRealWidth(22);

        }
    }
}

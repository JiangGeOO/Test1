package com.yidao.threekmo.adapter;

import android.content.Context;
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
 * Created by Smart~ on 2017/11/27.
 */

public class ShopSearchAdapter extends BaseRvAdapter<ShopDetailResult> {

    public ShopSearchAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = layoutInflater.inflate(R.layout.item_new_shop,parent,false);
        return new MyHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        ShopDetailResult shopDetailResult = dataList.get(position);
        if(shopDetailResult != null){
            Glide.with(context).load(shopDetailResult.getPhotoUrl()).into(myHolder.item_image);
            myHolder.item_info.setText(shopDetailResult.getName());
            myHolder.item_money.setText("￥"+shopDetailResult.getSellingPrice());
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyHolder extends BaseRvAdapter.BaseViewHolder{

        RelativeLayout item_rela;
        ImageView item_image;
        TextView item_info;
        TextView item_money;

        public MyHolder(View itemView) {
            super(itemView);
            item_rela = (RelativeLayout) itemView.findViewById(R.id.item_rela);
            RelativeLayout.LayoutParams llp_item_rela = (RelativeLayout.LayoutParams)item_rela.getLayoutParams();
            llp_item_rela.width = CommonUtil.getRealWidth(732);

            item_image = (ImageView) itemView.findViewById(R.id.item_image);
            RelativeLayout.LayoutParams llp_item_image = (RelativeLayout.LayoutParams)item_image.getLayoutParams();
            llp_item_image.height = CommonUtil.getRealWidth(366);

            item_info = (TextView) itemView.findViewById(R.id.item_info);
            item_info.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
            RelativeLayout.LayoutParams llp_item_info = (RelativeLayout.LayoutParams)item_info.getLayoutParams();
            llp_item_info.leftMargin = CommonUtil.getRealWidth(12);
            llp_item_info.topMargin = CommonUtil.getRealWidth(12);
            llp_item_info.rightMargin = CommonUtil.getRealWidth(12);

            item_money = (TextView) itemView.findViewById(R.id.item_money);
            item_money.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
            RelativeLayout.LayoutParams llp_item_money = (RelativeLayout.LayoutParams)item_money.getLayoutParams();
            llp_item_money.leftMargin = CommonUtil.getRealWidth(12);
            llp_item_money.topMargin = CommonUtil.getRealWidth(12);
            llp_item_money.bottomMargin = CommonUtil.getRealWidth(14);

        }
    }
}

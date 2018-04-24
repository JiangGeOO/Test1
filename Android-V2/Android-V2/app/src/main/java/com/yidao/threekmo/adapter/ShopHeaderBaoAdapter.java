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
 * Created by Smart~ on 2017/11/9.
 */

public class ShopHeaderBaoAdapter extends BaseRvAdapter<ShopDetailResult> {

    public ShopHeaderBaoAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = layoutInflater.inflate(R.layout.item_shop_bao,parent,false);
        return new MyHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        //glide4.0圆角
//        RequestOptions picOptions = new RequestOptions()
//                .centerCrop()
//                .bitmapTransform(new RoundedCornersTransformation(CommonUtil.getRealWidth(12),0,RoundedCornersTransformation.CornerType.TOP))
//                .priority(Priority.HIGH);
        ShopDetailResult shopDetailResult = dataList.get(position);
        Glide.with(context).load(shopDetailResult.getPhoto()).into(((MyHolder) holder).item_jing_image);
        myHolder.item_jing_info.setText(shopDetailResult.getTitle());
        myHolder.item_jing_money.setText("￥" + shopDetailResult.getSellingPrice());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class MyHolder extends BaseRvAdapter.BaseViewHolder{

        RelativeLayout item_jing;
        ImageView item_jing_image;
        TextView item_jing_info;
        TextView item_jing_money;

        public MyHolder(View itemView) {
            super(itemView);

            item_jing = (RelativeLayout) itemView.findViewById(R.id.item_jing);
            RelativeLayout.LayoutParams llp_item_jing = (RelativeLayout.LayoutParams)item_jing.getLayoutParams();
            llp_item_jing.width = CommonUtil.getRealWidth(264);
            llp_item_jing.height = CommonUtil.getRealWidth(390);
            llp_item_jing.leftMargin = CommonUtil.getRealWidth(20);
            llp_item_jing.topMargin = CommonUtil.getRealWidth(24);

            item_jing_image = (ImageView) itemView.findViewById(R.id.item_jing_image);
            RelativeLayout.LayoutParams llp_item_jing_image = (RelativeLayout.LayoutParams)item_jing_image.getLayoutParams();
            llp_item_jing_image.width = CommonUtil.getRealWidth(264);
            llp_item_jing_image.height = CommonUtil.getRealWidth(298);

            item_jing_info = (TextView) itemView.findViewById(R.id.item_jing_info);
            item_jing_info.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
            RelativeLayout.LayoutParams llp_item_jing_info = (RelativeLayout.LayoutParams)item_jing_info.getLayoutParams();
            llp_item_jing_info.topMargin = CommonUtil.getRealWidth(10);

            item_jing_money = (TextView) itemView.findViewById(R.id.item_jing_money);
            item_jing_money.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
            RelativeLayout.LayoutParams llp_item_jing_money = (RelativeLayout.LayoutParams)item_jing_money.getLayoutParams();
            llp_item_jing_money.topMargin = CommonUtil.getRealWidth(4);

        }
    }
}

package com.yidao.threekmo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ijustyce.fastkotlin.glide.ImageLoader;
import com.yidao.threekmo.R;
import com.yidao.threekmo.bean.ShopDetailResult;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.v2.utils.AppImage;

/**
 * Created by Smart~ on 2017/11/21.
 */

public class MyCollectCommodityAdapter extends BaseRvAdapter<ShopDetailResult> {

    public MyCollectCommodityAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = layoutInflater.inflate(R.layout.item_my_collect_commodity,parent,false);
        return new MyHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        ShopDetailResult shopDetailResult = dataList.get(position);

        AppImage.INSTANCE.load(myHolder.item_image, shopDetailResult.getPhotoUrl(), 4, ImageLoader.TYPE_ALL);
        if(shopDetailResult.getSoldOut() == 0){
            myHolder.item_image.setColorFilter(R.color.black_bantou);
            myHolder.soOut.setVisibility(View.VISIBLE);
        }else{
            myHolder.item_image.setColorFilter(null);
            myHolder.soOut.setVisibility(View.GONE);
        }

        myHolder.item_text.setText(shopDetailResult.getName());
        myHolder.item_money.setText("￥" + shopDetailResult.getSellingPrice()+"");

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyHolder extends BaseRvAdapter.BaseViewHolder{

        RelativeLayout item_rela;
        ImageView item_image;
        TextView item_text;
        TextView item_money;
        TextView soOut;

        public MyHolder(View itemView) {
            super(itemView);

            item_rela = (RelativeLayout) itemView.findViewById(R.id.item_rela);
            RelativeLayout.LayoutParams llp_item_rela = (RelativeLayout.LayoutParams)item_rela.getLayoutParams();
            llp_item_rela.height = CommonUtil.getRealWidth(220);

            item_image = (ImageView) itemView.findViewById(R.id.item_image);
            RelativeLayout.LayoutParams llp_item_image = (RelativeLayout.LayoutParams)item_image.getLayoutParams();
            llp_item_image.width = CommonUtil.getRealWidth(172);
            llp_item_image.height = llp_item_image.width;
            llp_item_image.leftMargin = CommonUtil.getRealWidth(30);

            item_text = (TextView) itemView.findViewById(R.id.item_text);
            item_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
            RelativeLayout.LayoutParams llp_item_text = (RelativeLayout.LayoutParams)item_text.getLayoutParams();
            llp_item_text.leftMargin = CommonUtil.getRealWidth(222);
            llp_item_text.topMargin = CommonUtil.getRealWidth(28);

            item_money = (TextView) itemView.findViewById(R.id.item_money);
            item_money.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(36));
            RelativeLayout.LayoutParams llp_item_money = (RelativeLayout.LayoutParams)item_money.getLayoutParams();
            llp_item_money.leftMargin = CommonUtil.getRealWidth(222);
            llp_item_money.topMargin = CommonUtil.getRealWidth(142);

            soOut = (TextView) itemView.findViewById(R.id.soOut);
            soOut.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(36));
            RelativeLayout.LayoutParams llp_soOut = (RelativeLayout.LayoutParams)soOut.getLayoutParams();
            llp_soOut.leftMargin = CommonUtil.getRealWidth(70);

        }
    }
}

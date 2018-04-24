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
import com.yidao.threekmo.customview.RoundLayout;
import com.yidao.threekmo.utils.CommonUtil;

/**
 * Created by Smart~ on 2017/11/25.
 */

public class ExperienceConnectAdapter extends BaseRvAdapter<ShopDetailResult> {

    public ExperienceConnectAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = layoutInflater.inflate(R.layout.item_experience_connect,parent,false);
        return new MyHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        ShopDetailResult shopDetailResult = dataList.get(position);

        if(shopDetailResult != null){
            Glide.with(context).load(shopDetailResult.getPhotoUrl()).into(myHolder.image);
            myHolder.name.setText(shopDetailResult.getName());
            myHolder.money.setText("￥"+shopDetailResult.getSellingPrice());
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyHolder extends BaseRvAdapter.BaseViewHolder{

        RoundLayout roundLayout;
        ImageView image;
        TextView name;
        TextView money;
        RelativeLayout rela;
        TextView pay;

        public MyHolder(View itemView) {
            super(itemView);
            roundLayout = (RoundLayout) itemView.findViewById(R.id.roundLayout);
            RelativeLayout.LayoutParams llp_roundLayout = (RelativeLayout.LayoutParams)roundLayout.getLayoutParams();
            llp_roundLayout.width = CommonUtil.getRealWidth(558);
            llp_roundLayout.height = CommonUtil.getRealWidth(272);
            llp_roundLayout.leftMargin = CommonUtil.getRealWidth(30);

            image = (ImageView) itemView.findViewById(R.id.image);
            RelativeLayout.LayoutParams llp_image = (RelativeLayout.LayoutParams)image.getLayoutParams();
            llp_image.width = CommonUtil.getRealWidth(226);
            llp_image.height = CommonUtil.getRealWidth(244);

            name = (TextView) itemView.findViewById(R.id.name);
            name.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
            RelativeLayout.LayoutParams llp_name = (RelativeLayout.LayoutParams)name.getLayoutParams();
            llp_name.leftMargin = CommonUtil.getRealWidth(254);
            llp_name.topMargin = CommonUtil.getRealWidth(20);

            money = (TextView) itemView.findViewById(R.id.money);
            money.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
            RelativeLayout.LayoutParams llp_money = (RelativeLayout.LayoutParams)money.getLayoutParams();
            llp_money.leftMargin = CommonUtil.getRealWidth(254);
            llp_money.topMargin = CommonUtil.getRealWidth(118);

            rela = (RelativeLayout) itemView.findViewById(R.id.rela);
            RelativeLayout.LayoutParams llp_rela = (RelativeLayout.LayoutParams)rela.getLayoutParams();
            llp_rela.width = CommonUtil.getRealWidth(268);
            llp_rela.height = CommonUtil.getRealWidth(74);
            llp_rela.leftMargin = CommonUtil.getRealWidth(254);
            llp_rela.topMargin = CommonUtil.getRealWidth(172);

            pay = (TextView) itemView.findViewById(R.id.pay);
            pay.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));

        }
    }
}

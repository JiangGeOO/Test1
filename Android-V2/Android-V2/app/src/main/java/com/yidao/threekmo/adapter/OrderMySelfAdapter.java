package com.yidao.threekmo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ijustyce.fastkotlin.glide.ImageLoader;
import com.yidao.threekmo.R;
import com.yidao.threekmo.bean.OrderDetailResult;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.v2.utils.AppImage;

/**
 * Created by Smart~ on 2017/11/20.
 */

public class OrderMySelfAdapter extends BaseRvAdapter<OrderDetailResult>{

    public OrderMySelfAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = layoutInflater.inflate(R.layout.item_order_myself,parent,false);
        return new MyHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyHolder myHolder = (MyHolder) holder;
        OrderDetailResult orderDetailResult = dataList.get(position);
        myHolder.type_name.setText(orderDetailResult.getShopName());
        if(orderDetailResult.getStatus().equals("waitFor")){
            myHolder.pay_type.setText("待发货");
        }else{
            myHolder.pay_type.setText("交易成功");
        }
        AppImage.INSTANCE.load(myHolder.order_image, orderDetailResult.getPhoto(), 16, ImageLoader.TYPE_ALL);
        myHolder.order_name.setText(orderDetailResult.getName());
        myHolder.order_money.setText("￥"+orderDetailResult.getSellingPrice());
        myHolder.pay_money_result.setText("合计金额：￥" + orderDetailResult.getActualPayment());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyHolder extends BaseRvAdapter.BaseViewHolder{

        LinearLayout all_linear;
        RelativeLayout pay_type_rela;
        ImageView type_image;
        TextView type_name;
        TextView pay_type;
        RelativeLayout order_detail_rela;
        ImageView order_image;
        TextView order_name;
        TextView order_money;
        TextView order_num;
        RelativeLayout pay_money_rela;
        TextView pay_money_result;


        public MyHolder(View itemView) {
            super(itemView);

            all_linear = (LinearLayout) itemView.findViewById(R.id.all_linear);
            RelativeLayout.LayoutParams llp_all_linear = (RelativeLayout.LayoutParams)all_linear.getLayoutParams();
            llp_all_linear.height = CommonUtil.getRealWidth(390);
            llp_all_linear.bottomMargin = CommonUtil.getRealWidth(20);

            pay_type_rela = (RelativeLayout) itemView.findViewById(R.id.pay_type_rela);
            LinearLayout.LayoutParams llp_pay_type_rela = (LinearLayout.LayoutParams)pay_type_rela.getLayoutParams();
            llp_pay_type_rela.height = CommonUtil.getRealWidth(88);

            type_image = (ImageView) itemView.findViewById(R.id.type_image);
            RelativeLayout.LayoutParams llp_type_image = (RelativeLayout.LayoutParams)type_image.getLayoutParams();
            llp_type_image.width = CommonUtil.getRealWidth(40);
            llp_type_image.height = llp_type_image.width;
            llp_type_image.leftMargin = CommonUtil.getRealWidth(30);

            type_name = (TextView) itemView.findViewById(R.id.type_name);
            type_name.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
            RelativeLayout.LayoutParams llp_type_name = (RelativeLayout.LayoutParams)type_name.getLayoutParams();
            llp_type_name.leftMargin = CommonUtil.getRealWidth(86);

            pay_type = (TextView) itemView.findViewById(R.id.pay_type);
            pay_type.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
            RelativeLayout.LayoutParams llp_pay_type = (RelativeLayout.LayoutParams)pay_type.getLayoutParams();
            llp_pay_type.rightMargin = CommonUtil.getRealWidth(30);

            order_detail_rela = (RelativeLayout) itemView.findViewById(R.id.order_detail_rela);
            LinearLayout.LayoutParams llp_order_detail_rela = (LinearLayout.LayoutParams)order_detail_rela.getLayoutParams();
            llp_order_detail_rela.height = CommonUtil.getRealWidth(216);

            order_image = (ImageView) itemView.findViewById(R.id.order_image);
            RelativeLayout.LayoutParams llp_order_image = (RelativeLayout.LayoutParams)order_image.getLayoutParams();
            llp_order_image.width = CommonUtil.getRealWidth(160);
            llp_order_image.height = llp_order_image.width;
            llp_order_image.leftMargin = CommonUtil.getRealWidth(30);

            order_name = (TextView) itemView.findViewById(R.id.order_name);
            order_name.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
            RelativeLayout.LayoutParams llp_order_name = (RelativeLayout.LayoutParams)order_name.getLayoutParams();
            llp_order_name.leftMargin = CommonUtil.getRealWidth(214);
            llp_order_name.topMargin = CommonUtil.getRealWidth(28);
            llp_order_name.rightMargin = CommonUtil.getRealWidth(90);

            order_money = (TextView) itemView.findViewById(R.id.order_money);
            order_money.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
            RelativeLayout.LayoutParams llp_order_money = (RelativeLayout.LayoutParams)order_money.getLayoutParams();
            llp_order_money.leftMargin = CommonUtil.getRealWidth(214);
            llp_order_money.topMargin = CommonUtil.getRealWidth(144);


            order_num = (TextView) itemView.findViewById(R.id.order_num);
            order_num.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
            RelativeLayout.LayoutParams llp_order_num = (RelativeLayout.LayoutParams)order_num.getLayoutParams();
            llp_order_num.topMargin = CommonUtil.getRealWidth(144);
            llp_order_num.rightMargin = CommonUtil.getRealWidth(30);

            pay_money_rela = (RelativeLayout) itemView.findViewById(R.id.pay_money_rela);
            LinearLayout.LayoutParams llp_pay_money_rela = (LinearLayout.LayoutParams)pay_money_rela.getLayoutParams();
            llp_pay_money_rela.height = CommonUtil.getRealWidth(84);

            pay_money_result = (TextView) itemView.findViewById(R.id.pay_money_result);
            pay_money_result.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
            RelativeLayout.LayoutParams llp_pay_money_result = (RelativeLayout.LayoutParams)pay_money_result.getLayoutParams();
            llp_pay_money_result.rightMargin = CommonUtil.getRealWidth(30);

        }
    }

}

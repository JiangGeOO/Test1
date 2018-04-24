package com.yidao.threekmo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yidao.threekmo.R;
import com.yidao.threekmo.bean.MainHomeListItem;
import com.yidao.threekmo.utils.CommonUtil;

/**
 * Created by Smart~ on 2017/10/14.
 */

public class MyActiveListAdapter extends BaseRvAdapter<MainHomeListItem> implements OnClickListener {

    public MyActiveListAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_myactive_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        MainHomeListItem mainHomeListItem = dataList.get(position);
        if (null != mainHomeListItem) {
            RelativeLayout.LayoutParams llp_active_image = (RelativeLayout.LayoutParams)viewHolder.active_image.getLayoutParams();
            llp_active_image.width = CommonUtil.getRealWidth(158);
            llp_active_image.height = llp_active_image.width;
            llp_active_image.topMargin = CommonUtil.getRealWidth(24);
            llp_active_image.leftMargin = CommonUtil.getRealWidth(40);
            llp_active_image.bottomMargin = CommonUtil.getRealWidth(28);

            viewHolder.name.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
            RelativeLayout.LayoutParams llp_name = (RelativeLayout.LayoutParams)viewHolder.name.getLayoutParams();
            llp_name.leftMargin = CommonUtil.getRealWidth(32);
            llp_name.topMargin = CommonUtil.getRealWidth(40);

            viewHolder.address.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(24));
            RelativeLayout.LayoutParams llp_address = (RelativeLayout.LayoutParams)viewHolder.address.getLayoutParams();
            llp_address.leftMargin = CommonUtil.getRealWidth(32);
            llp_address.topMargin = CommonUtil.getRealWidth(8);

            viewHolder.date.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
            RelativeLayout.LayoutParams llp_date = (RelativeLayout.LayoutParams)viewHolder.date.getLayoutParams();
            llp_date.leftMargin = CommonUtil.getRealWidth(32);
            llp_date.topMargin = CommonUtil.getRealWidth(8);

            viewHolder.isGo.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(24));
            RelativeLayout.LayoutParams llp_isGo = (RelativeLayout.LayoutParams)viewHolder.isGo.getLayoutParams();
            llp_isGo.rightMargin = CommonUtil.getRealWidth(30);
            llp_isGo.topMargin = CommonUtil.getRealWidth(88);

            if(mainHomeListItem.getPhoto() != null){
                Glide.with(context).load(mainHomeListItem.getPhoto()).into(viewHolder.active_image);
            }else{
                Glide.with(context).load(R.mipmap.wangxingsuo).into(viewHolder.active_image);
            }

            viewHolder.name.setText(mainHomeListItem.getName());
            viewHolder.address.setText(mainHomeListItem.getAddress());

            String activeStart = CommonUtil.getDateToString(mainHomeListItem.getActivityStart(),"MM/yy");
            String activeEnd = CommonUtil.getDateToString(mainHomeListItem.getActivityEnd(),"MM/yy");
            viewHolder.date.setText(activeStart + " - " + activeEnd);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private class ViewHolder extends BaseViewHolder {
        ImageView active_image;
        TextView name;
        TextView address;
        TextView date;
        TextView isGo;

        public ViewHolder(View itemView) {
            super(itemView);
            active_image = (ImageView) itemView.findViewById(R.id.active_image);
            name = (TextView) itemView.findViewById(R.id.name);
            address = (TextView) itemView.findViewById(R.id.address);
            date = (TextView) itemView.findViewById(R.id.date);
            isGo = (TextView) itemView.findViewById(R.id.isGo);
        }
    }

    @Override
    public void onClick(View v) {



    }
}

package com.yidao.threekmo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ijustyce.fastkotlin.glide.ImageLoader;
import com.yidao.myutils.log.LogUtils;
import com.yidao.threekmo.R;
import com.yidao.threekmo.bean.MainHomeListItem;
import com.yidao.threekmo.customview.LinearTagLayout;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.v2.utils.AppImage;

/**
 * Created by Administrator on 2017\8\21 0021.
 */

public class SecondaryListAdapter extends BaseRvAdapter<MainHomeListItem> {


    public SecondaryListAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_secondarylist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MainHomeListItem mainHomeListItem = dataList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        if(null!=mainHomeListItem){

            AppImage.INSTANCE.load(viewHolder.ivImage, mainHomeListItem.getPhotoUrl(), 22, ImageLoader.TYPE_ALL);

            viewHolder.tvName.setText(mainHomeListItem.getName());

            String province = mainHomeListItem.getProvince();
            String city = mainHomeListItem.getCity();
            String area = mainHomeListItem.getArea();
            String address = mainHomeListItem.getAddress();
            if(TextUtils.isEmpty(province)){
                province = "";
            }
            if(TextUtils.isEmpty(city)){
                city = "";
            }
            if(TextUtils.isEmpty(area)){
                area = "";
            }
            if(TextUtils.isEmpty(address)){
                address = "";
            }
            viewHolder.tvAddress.setText(province + city + area + address);

            Long distance = mainHomeListItem.getDistance();
            if(distance != null){
                float dis = distance / 1000f;
                if(distance < 1000){
                    viewHolder.tvDistance.setText(distance+ "m");
                }else{
                    viewHolder.tvDistance.setText(String .format("%.1f",dis)+ "km");
                }
            }else{
                viewHolder.tvDistance.setText(0+ "m");
            }



            String[] tags = getTags(mainHomeListItem.getLabelNames());
            LinearTagLayout ltgTags = viewHolder.ltgTags;
            if(tags.length>0){
                ltgTags.setTagList(tags);
            }else {
                ltgTags.removeAllViews();
            }

            viewHolder.yin_cang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onButtonClickListener != null) {
                        onButtonClickListener.itemButtonListener(position);
                    }
                }
            });

        }
    }

    private String[] getTags(String str){
        if(null!=str&& !TextUtils.isEmpty(str)){
            String substring = str.substring(1, str.length() - 1);
            LogUtils.e("tage:" + substring);
            String[] tags = substring.split(",");
            return tags;
        }else {
            return new String[0];
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private class ViewHolder extends BaseViewHolder{
        ImageView ivImage;
        TextView tvName;
        TextView tvAddress;
        TextView tvDistance;
        LinearTagLayout ltgTags;
        ImageView yin_cang;
        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            RelativeLayout.LayoutParams llp_tvName = (RelativeLayout.LayoutParams)tvName.getLayoutParams();
            llp_tvName.rightMargin = CommonUtil.getRealWidth(70);

            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);

            tvDistance = (TextView) itemView.findViewById(R.id.tvDistance);

            ltgTags = (LinearTagLayout) itemView.findViewById(R.id.ltgTags);

            yin_cang = (ImageView) itemView.findViewById(R.id.yin_cang);
            RelativeLayout.LayoutParams llp_yin_cang = (RelativeLayout.LayoutParams)yin_cang.getLayoutParams();
            llp_yin_cang.width = CommonUtil.getRealWidth(50);
            llp_yin_cang.height = llp_yin_cang.width;
            llp_yin_cang.rightMargin = CommonUtil.getRealWidth(15);
        }
    }
}

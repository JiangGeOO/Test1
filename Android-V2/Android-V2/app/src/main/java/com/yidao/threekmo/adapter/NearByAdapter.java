package com.yidao.threekmo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidao.threekmo.R;
import com.yidao.threekmo.bean.MainHomeListItem;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.v2.utils.AppImage;


/**
 * Created by Administrator on 2017\8\18 0018.
 */

/**
 * 修改后暂时无用
 */

public class NearByAdapter extends BaseRvAdapter<MainHomeListItem> {
    public NearByAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_nearby_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

        MainHomeListItem mainHomeListItem = dataList.get(position);
        if (null != mainHomeListItem) {
            RelativeLayout.LayoutParams llpInfoRela = (RelativeLayout.LayoutParams)viewHolder.infoRela.getLayoutParams();
            llpInfoRela.width = CommonUtil.getRealWidth(670);
            llpInfoRela.height = CommonUtil.getRealWidth(292);
            llpInfoRela.topMargin = CommonUtil.getRealWidth(24);

            viewHolder.ivImage.setColorFilter(R.color.black_bantou);
            RelativeLayout.LayoutParams llpIvImage = (RelativeLayout.LayoutParams)viewHolder.ivImage.getLayoutParams();
            llpIvImage.width = CommonUtil.getRealWidth(670);
            llpIvImage.height = CommonUtil.getRealWidth(348);

            AppImage.INSTANCE.load(viewHolder.ivImage, mainHomeListItem.getPhotoUrl(), 12, 670, 348);

            viewHolder.tvDistance.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
            RelativeLayout.LayoutParams llpTvDistance = (RelativeLayout.LayoutParams)viewHolder.tvDistance.getLayoutParams();
            llpTvDistance.leftMargin = CommonUtil.getRealWidth(30);
            llpTvDistance.topMargin = CommonUtil.getRealWidth(20);

            viewHolder.tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(48));
            RelativeLayout.LayoutParams llpTvName = (RelativeLayout.LayoutParams)viewHolder.tvName.getLayoutParams();
            llpTvName.leftMargin = CommonUtil.getRealWidth(30);
            llpTvName.topMargin = CommonUtil.getRealWidth(8);

            RelativeLayout.LayoutParams llpLinear = (RelativeLayout.LayoutParams)viewHolder.linear.getLayoutParams();
            llpLinear.topMargin = CommonUtil.getRealWidth(8);

            viewHolder.tvType.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(24));
            LinearLayout.LayoutParams llpTvType = (LinearLayout.LayoutParams)viewHolder.tvType.getLayoutParams();
            llpTvType.leftMargin = CommonUtil.getRealWidth(30);

            viewHolder.tvCount.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(24));

            viewHolder.tvAddress.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(24));
            RelativeLayout.LayoutParams llpTvAddress = (RelativeLayout.LayoutParams)viewHolder.tvAddress.getLayoutParams();
            llpTvAddress.topMargin = CommonUtil.getRealWidth(74);
            llpTvAddress.leftMargin = CommonUtil.getRealWidth(20);

            RelativeLayout.LayoutParams llp_location_nearby = (RelativeLayout.LayoutParams)viewHolder.location_nearby.getLayoutParams();
            llp_location_nearby.width = CommonUtil.getRealWidth(30);
            llp_location_nearby.height = llp_location_nearby.width;
            llp_location_nearby.topMargin = CommonUtil.getRealWidth(74);
            llp_location_nearby.leftMargin = CommonUtil.getRealWidth(30);

            viewHolder.tvName.setText(mainHomeListItem.getName());
            String province = mainHomeListItem.getProvince();
            String city = mainHomeListItem.getCity();
            String area = mainHomeListItem.getArea();
            String address = mainHomeListItem.getAddress();
            if (TextUtils.isEmpty(province)) {
                province = "";
            }
            if (TextUtils.isEmpty(city)) {
                city = "";
            }
            if (TextUtils.isEmpty(area)) {
                area = "";
            }
            if (TextUtils.isEmpty(address)) {
                address = "";
            }
            viewHolder.tvAddress.setText(province + city + area + address);

            Long distance = mainHomeListItem.getDistance();
            float dis = distance / 1000f;
            if (distance > 1000) {
                viewHolder.tvDistance.setText(String.format("%.1f", dis) + "km");
            } else {
                viewHolder.tvDistance.setText(distance + "m");
            }
            if (null == mainHomeListItem.getInferiorsCount()) {
                viewHolder.tvCount.setText("包含主体" + "0" + "个");
            } else {
                viewHolder.tvCount.setText("包含主体" + mainHomeListItem.getInferiorsCount() + "个");
            }

            if (null != mainHomeListItem.getTypeName()) {
                viewHolder.tvType.setText(mainHomeListItem.getTypeName() + "");
            } else {
                viewHolder.tvType.setText("小区");
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private class ViewHolder extends BaseViewHolder {
        RelativeLayout infoRela;
        ImageView ivImage;
        TextView tvName;
        ImageView location_nearby;
//        ImageView ivType;
        TextView tvType;
        TextView tvCount;
        TextView tvAddress;
        TextView tvDistance;
        LinearLayout linear;

        public ViewHolder(View itemView) {
            super(itemView);
            infoRela = (RelativeLayout) itemView.findViewById(R.id.infoRela);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            location_nearby = (ImageView) itemView.findViewById(R.id.location_nearby);

//            ivType = (ImageView) itemView.findViewById(R.id.ivType);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvType = (TextView) itemView.findViewById(R.id.tvType);
            tvCount = (TextView) itemView.findViewById(R.id.tvCount);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            tvDistance = (TextView) itemView.findViewById(R.id.tvDistance);
            linear = (LinearLayout) itemView.findViewById(R.id.linear);

        }
    }
}

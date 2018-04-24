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

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.ijustyce.fastkotlin.glide.ImageLoader;
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

public class ActiveSearchAdapter extends BaseRvAdapter<MainHomeListItem> implements View.OnClickListener {

    public ActiveSearchAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_index_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = getRealPosition(holder);

        final ViewHolder viewHolder = (ViewHolder) holder;
        MainHomeListItem mainHomeListItem = dataList.get(position);
        if (null != mainHomeListItem) {
            LinearLayout.LayoutParams llpInfoRela = (LinearLayout.LayoutParams) viewHolder.infoRela.getLayoutParams();
            llpInfoRela.width = CommonUtil.getRealWidth(670);
            llpInfoRela.height = CommonUtil.getRealWidth(372);
            llpInfoRela.topMargin = CommonUtil.getRealWidth(24);

            viewHolder.ivImage.setColorFilter(R.color.black_bantou);
            RelativeLayout.LayoutParams llpIvImage = (RelativeLayout.LayoutParams) viewHolder.ivImage.getLayoutParams();
            llpIvImage.width = CommonUtil.getRealWidth(670);
            llpIvImage.height = CommonUtil.getRealWidth(372);

            viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
            RelativeLayout.LayoutParams llp_text = (RelativeLayout.LayoutParams) viewHolder.text.getLayoutParams();
            llp_text.leftMargin = CommonUtil.getRealWidth(38);
            llp_text.topMargin = CommonUtil.getRealWidth(20);

            viewHolder.tvDistance.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
            RelativeLayout.LayoutParams llpTvDistance = (RelativeLayout.LayoutParams) viewHolder.tvDistance.getLayoutParams();
            llpTvDistance.width = CommonUtil.getRealWidth(126);
            llpTvDistance.height = CommonUtil.getRealWidth(48);
            llpTvDistance.leftMargin = CommonUtil.getRealWidth(38);
            llpTvDistance.topMargin = CommonUtil.getRealWidth(292);

            viewHolder.tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(48));
            RelativeLayout.LayoutParams llpTvName = (RelativeLayout.LayoutParams) viewHolder.tvName.getLayoutParams();
            llpTvName.leftMargin = CommonUtil.getRealWidth(38);
            llpTvName.topMargin = CommonUtil.getRealWidth(8);

            viewHolder.date.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
            RelativeLayout.LayoutParams llp_date = (RelativeLayout.LayoutParams) viewHolder.date.getLayoutParams();
            llp_date.topMargin = CommonUtil.getRealWidth(158);
            llp_date.leftMargin = CommonUtil.getRealWidth(38);

            viewHolder.tvName.setText(mainHomeListItem.getName());
            String province = mainHomeListItem.getProvince();
            String city = mainHomeListItem.getCity();
            String area = mainHomeListItem.getArea();
            String address = mainHomeListItem.getAddress();

            if (!TextUtils.isEmpty(mainHomeListItem.getPhoto())) {
                AppImage.INSTANCE.load(viewHolder.ivImage, mainHomeListItem.getPhoto(), 22, ImageLoader.TYPE_ALL);
            } else {
                viewHolder.ivImage.setImageResource(R.mipmap.wangxingback);
            }

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
            Long distance = mainHomeListItem.getDistance();
            if (distance != null) {
                float dis = distance / 1000f;
                if (distance > 1000) {
                    viewHolder.tvDistance.setText(String.format("%.1f", dis) + "km");
                } else {
                    viewHolder.tvDistance.setText(distance + "m");
                }
            } else {
                viewHolder.tvDistance.setText("距离未知");
            }
            viewHolder.date.setText(CommonUtil.getDateToString(mainHomeListItem.getActivityStart(),
                    "MM月dd号") + "-" + CommonUtil.getDateToString(mainHomeListItem.getActivityEnd(), "MM月dd号"));
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return position;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }
    }

    private class ViewHolder extends BaseViewHolder {
        RelativeLayout infoRela;
        ImageView ivImage;
        TextView tvName;
        TextView tvDistance;
        TextView text;
        TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            infoRela = (RelativeLayout) itemView.findViewById(R.id.infoRela);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            date = (TextView) itemView.findViewById(R.id.date);
//            ivType = (ImageView) itemView.findViewById(R.id.ivType);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDistance = (TextView) itemView.findViewById(R.id.tvDistance);
            text = (TextView) itemView.findViewById(R.id.text);

        }
    }

}

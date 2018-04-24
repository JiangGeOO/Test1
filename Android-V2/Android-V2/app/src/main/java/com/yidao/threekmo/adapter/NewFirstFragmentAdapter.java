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
 * Created by Smart~ on 2017/11/10.
 */

public class NewFirstFragmentAdapter extends BaseRecyclerAdapter<MainHomeListItem> {

    public NewFirstFragmentAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View layout = layoutInflater.inflate(R.layout.item_new_first_fragment,parent,false);
        return new MyHolder(layout);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, MainHomeListItem mainHomeListItem) {
        MyHolder myHolder = (MyHolder) viewHolder;

        int visible = mainHomeListItem.showContent() ? View.VISIBLE : View.GONE;
        if (visible == View.VISIBLE) myHolder.ivImage.setColorFilter(R.color.black_bantou);
        myHolder.date.setVisibility(visible);
        myHolder.text.setVisibility(visible);
        myHolder.tvDistance.setVisibility(visible);
        myHolder.tvName.setVisibility(visible);

        LinearLayout.LayoutParams llpInfoRela = (LinearLayout.LayoutParams) myHolder.infoRela.getLayoutParams();
        llpInfoRela.width = CommonUtil.getRealWidth(670);
        llpInfoRela.height = CommonUtil.getRealWidth(372);
        llpInfoRela.topMargin = CommonUtil.getRealWidth(24);

        RelativeLayout.LayoutParams llpIvImage = (RelativeLayout.LayoutParams) myHolder.ivImage.getLayoutParams();
        llpIvImage.width = CommonUtil.getRealWidth(690);
        llpIvImage.height = CommonUtil.getRealWidth(370);

        myHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llp_text = (RelativeLayout.LayoutParams) myHolder.text.getLayoutParams();
        llp_text.leftMargin = CommonUtil.getRealWidth(38);
        llp_text.topMargin = CommonUtil.getRealWidth(20);

        myHolder.tvDistance.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(32));
        RelativeLayout.LayoutParams llpTvDistance = (RelativeLayout.LayoutParams) myHolder.tvDistance.getLayoutParams();
        llpTvDistance.width = CommonUtil.getRealWidth(126);
        llpTvDistance.height = CommonUtil.getRealWidth(48);
        llpTvDistance.leftMargin = CommonUtil.getRealWidth(38);
        llpTvDistance.topMargin = CommonUtil.getRealWidth(292);

        myHolder.tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(48));
        RelativeLayout.LayoutParams llpTvName = (RelativeLayout.LayoutParams) myHolder.tvName.getLayoutParams();
        llpTvName.leftMargin = CommonUtil.getRealWidth(38);
        llpTvName.topMargin = CommonUtil.getRealWidth(8);

        myHolder.date.setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtil.getRealWidth(28));
        RelativeLayout.LayoutParams llp_date = (RelativeLayout.LayoutParams) myHolder.date.getLayoutParams();
        llp_date.topMargin = CommonUtil.getRealWidth(158);
        llp_date.leftMargin = CommonUtil.getRealWidth(38);

        myHolder.tvName.setText(mainHomeListItem.getName());
        String province = mainHomeListItem.getProvince();
        String city = mainHomeListItem.getCity();
        String area = mainHomeListItem.getArea();
        String address = mainHomeListItem.getAddress();
        if (!TextUtils.isEmpty(mainHomeListItem.getPhoto())) {
            AppImage.INSTANCE.load(myHolder.ivImage, mainHomeListItem.getPhoto(), 12, 690, 370);
        } else {
            myHolder.ivImage.setImageResource(R.mipmap.wangxingback);
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
                myHolder.tvDistance.setText(String.format("%.1f", dis) + "km");
            } else {
                myHolder.tvDistance.setText(distance + "m");
            }
        }
        myHolder.tvDistance.setVisibility(mainHomeListItem.isSuggest()
                || !mainHomeListItem.showContent() || distance == null ? View.GONE : View.VISIBLE);
        myHolder.date.setText(CommonUtil.getDateToString(mainHomeListItem.getActivityStart(), "MM月dd号")
                + "-" + CommonUtil.getDateToString(mainHomeListItem.getActivityEnd(), "MM月dd号"));
    }

    class MyHolder extends BaseRecyclerAdapter.Holder{

        RelativeLayout infoRela;
        ImageView ivImage;
        TextView tvName;
        TextView tvDistance;
        TextView text;
        TextView date;

        public MyHolder(View itemView) {
            super(itemView);

            infoRela = (RelativeLayout) itemView.findViewById(R.id.infoRela);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            date = (TextView) itemView.findViewById(R.id.date);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDistance = (TextView) itemView.findViewById(R.id.tvDistance);
            text = (TextView) itemView.findViewById(R.id.text);

        }
    }
}

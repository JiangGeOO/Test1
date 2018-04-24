package com.yidao.threekmo.adapter;

import android.app.Activity;
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
import android.widget.Toast;

import com.ijustyce.fastkotlin.glide.ImageLoader;
import com.yidao.threekmo.R;
import com.yidao.threekmo.bean.MainHomeListItem;
import com.yidao.threekmo.customview.ClassificationSpinner;
import com.yidao.threekmo.customview.DateSpinner;
import com.yidao.threekmo.customview.NearBySpinner;
import com.yidao.threekmo.customview.SortSpinner;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.v2.utils.AppImage;

/**
 * Created by Administrator on 2017\8\18 0018.
 */

/**
 * 修改后暂时无用
 */

public class IndexAdapter extends BaseRvAdapter<MainHomeListItem> implements View.OnClickListener{

    private View mHeaderView;
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private OnclickSpinnerListener onclickSpinnerListener;

    //点击spinner的回掉接口
    public void setOnclickSpinnerListener(OnclickSpinnerListener onclickSpinnerListener){
        this.onclickSpinnerListener = onclickSpinnerListener;
    }

    public IndexAdapter(Context context) {
        super(context);
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new ViewHolder(mHeaderView);
        }
        View view = layoutInflater.inflate(R.layout.item_index_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = getRealPosition(holder);

        final ViewHolder viewHolder = (ViewHolder) holder;
        if (position != 0) {


            MainHomeListItem mainHomeListItem = dataList.get(position - 1);
            if (null != mainHomeListItem) {
                LinearLayout.LayoutParams llpInfoRela = (LinearLayout.LayoutParams) viewHolder.infoRela.getLayoutParams();
                llpInfoRela.width = CommonUtil.getRealWidth(670);
                llpInfoRela.height = CommonUtil.getRealWidth(372);
                llpInfoRela.topMargin = CommonUtil.getRealWidth(24);

                viewHolder.ivImage.setColorFilter(R.color.black_bantou);
                RelativeLayout.LayoutParams llpIvImage = (RelativeLayout.LayoutParams) viewHolder.ivImage.getLayoutParams();
                llpIvImage.width = CommonUtil.getRealWidth(670);
                llpIvImage.height = CommonUtil.getRealWidth(372);

                viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
                RelativeLayout.LayoutParams llp_text = (RelativeLayout.LayoutParams)viewHolder.text.getLayoutParams();
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

                viewHolder.date.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
                RelativeLayout.LayoutParams llp_date = (RelativeLayout.LayoutParams)viewHolder.date.getLayoutParams();
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
                if(distance != null){
                    float dis = distance / 1000f;
                    if (distance > 1000) {
                        viewHolder.tvDistance.setText(String.format("%.1f", dis) + "km");
                    } else {
                        viewHolder.tvDistance.setText(distance + "m");
                    }
                }else{
                    viewHolder.tvDistance.setText("距离未知");
                }

            }

            viewHolder.date.setText(CommonUtil.getDateToString(mainHomeListItem.getActivityStart(),"MM月dd号") + "-" +CommonUtil.getDateToString(mainHomeListItem.getActivityEnd(),"MM月dd号"));

            if(position == 1){
                //如果是第一个显示spinner
                viewHolder.spinner_linear.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams llp_spinner_linear = (LinearLayout.LayoutParams)viewHolder.spinner_linear.getLayoutParams();
                llp_spinner_linear.height = CommonUtil.getRealWidth(84);

                int size = dataList.size();

                viewHolder.classificationSpinner.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
                viewHolder.classificationSpinner.init((Activity) context);
//                viewHolder.classificationSpinner.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(dataList.size() < 3){
////                            viewHolder.classificationSpinner.showWindow();
//                            Toast.makeText(context,"当前活动较少，暂时无法分类.",Toast.LENGTH_SHORT).show();
//                        }else{
//                            onclickSpinnerListener.OnclickSpinner(0);
//                        }
//                    }
//                });
                viewHolder.classificationSpinner.setOnclickClassificationListener(new ClassificationSpinner.OnclickClassification() {
                    @Override
                    public void classification(String classifiation, int positon) {
                        Toast.makeText(context,classifiation,Toast.LENGTH_SHORT).show();
                    }
                });

                viewHolder.dateSpinner.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
                viewHolder.dateSpinner.init((Activity) context);
//                viewHolder.dateSpinner.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(dataList.size() < 3){
////                            viewHolder.dateSpinner.showWindow();
//                            Toast.makeText(context,"当前活动较少，暂时无法分类.",Toast.LENGTH_SHORT).show();
//                        }else{
//                            onclickSpinnerListener.OnclickSpinner(1);
//                        }
//                    }
//                });
                viewHolder.dateSpinner.setOnclickDateListener(new DateSpinner.OnclickDate() {
                    @Override
                    public void date(String start, String end) {
                        Toast.makeText(context,start + "-" + end,Toast.LENGTH_SHORT).show();
                    }
                });

                viewHolder.nearBySpinner.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
                viewHolder.nearBySpinner.init((Activity) context);
//                viewHolder.nearBySpinner.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(dataList.size() < 3){
////                            viewHolder.nearBySpinner.showWindow();
//                            Toast.makeText(context,"当前活动较少，暂时无法分类.",Toast.LENGTH_SHORT).show();
//                        }else{
//                            onclickSpinnerListener.OnclickSpinner(2);
//                        }
//                    }
//                });
                viewHolder.nearBySpinner.setOnclickNearBuListener(new NearBySpinner.OnClickNearByListener() {
                    @Override
                    public void nearBy(String nearBy, int positon) {
                        Toast.makeText(context,nearBy,Toast.LENGTH_SHORT).show();
                    }
                });

                viewHolder.sortSpinner.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
                viewHolder.sortSpinner.init((Activity) context);
//                viewHolder.sortSpinner.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(dataList.size() < 3){
////                            viewHolder.sortSpinner.showWindow();
//                            Toast.makeText(context,"当前活动较少，暂时无法分类.",Toast.LENGTH_SHORT).show();
//                        }else{
//                            onclickSpinnerListener.OnclickSpinner(3);
//
//                        }
//                    }
//                });
                viewHolder.sortSpinner.setOnclickSortListener(new SortSpinner.OnclickSort() {
                    @Override
                    public void sort(String sort, int position) {
                        Toast.makeText(context,sort,Toast.LENGTH_SHORT).show();
                    }
                });


                viewHolder.classificationSpinner.setOnClickListener(this);
                viewHolder.dateSpinner.setOnClickListener(this);
                viewHolder.nearBySpinner.setOnClickListener(this);
                viewHolder.sortSpinner.setOnClickListener(this);

            }else{
                viewHolder.spinner_linear.setVisibility(View.GONE);
            }
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? dataList.size() : dataList.size() + 1;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.classificationSpinner:{
                onclickSpinnerListener.OnclickSpinner(0);
            }
                break;
            case R.id.nearBySpinner:{
                onclickSpinnerListener.OnclickSpinner(1);
            }
            break;
            case R.id.dateSpinner:{
                onclickSpinnerListener.OnclickSpinner(2);
            }
            break;
            case R.id.sortSpinner:{
                onclickSpinnerListener.OnclickSpinner(3);
            }
            break;
            default:
                break;
        }
    }

    private class ViewHolder extends BaseViewHolder {
        RelativeLayout infoRela;
        ImageView ivImage;
        TextView tvName;
        //        ImageView ivType;
        TextView tvDistance;
        TextView text;
        TextView date;

        LinearLayout spinner_linear;
        ClassificationSpinner classificationSpinner;
        NearBySpinner nearBySpinner;
        DateSpinner dateSpinner;
        SortSpinner sortSpinner;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) {
                return;
            }
            infoRela = (RelativeLayout) itemView.findViewById(R.id.infoRela);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            date = (TextView) itemView.findViewById(R.id.date);
//            ivType = (ImageView) itemView.findViewById(R.id.ivType);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDistance = (TextView) itemView.findViewById(R.id.tvDistance);
            text = (TextView) itemView.findViewById(R.id.text);

            spinner_linear = (LinearLayout) itemView.findViewById(R.id.spinner_linear);
            classificationSpinner = (ClassificationSpinner) itemView.findViewById(R.id.classificationSpinner);
            nearBySpinner = (NearBySpinner) itemView.findViewById(R.id.nearBySpinner);
            dateSpinner = (DateSpinner) itemView.findViewById(R.id.dateSpinner);
            sortSpinner = (SortSpinner) itemView.findViewById(R.id.sortSpinner);

        }
    }

    public interface OnclickSpinnerListener{
        void OnclickSpinner(int num);
    }

}

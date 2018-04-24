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
import com.ijustyce.fastkotlin.utils.StringUtils;
import com.yidao.threekmo.R;
import com.yidao.threekmo.bean.ExperienceResult;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.v2.utils.AppImage;

/**
 * Created by Smart~ on 2017/11/14.
 */

public class NewExperienceFragmentAdapter extends BaseRvAdapter<ExperienceResult> {

    private int type;

    public NewExperienceFragmentAdapter(Context context,int type) {
        super(context);
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = layoutInflater.inflate(R.layout.item_new_experience_fragment,parent,false);
        return new MyHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        ExperienceResult experienceResult = dataList.get(position);
        myHolder.biaoti.setText(experienceResult.getName());
        myHolder.look.setText(0+"浏览量");

        AppImage.INSTANCE.load(myHolder.datu, experienceResult.getFrontCover(), 16, 690, 386);
        myHolder.info.setText(experienceResult.getProfile());

        if(type == 2){
            if(experienceResult.getSoldOut() == 0){
                myHolder.datu.setColorFilter(R.color.black_bantou);
                myHolder.sortOut.setVisibility(View.VISIBLE);
            }else{
                myHolder.datu.setColorFilter(null);
                myHolder.sortOut.setVisibility(View.GONE);
            }
        }

        if ("null".equals(experienceResult.distance) || StringUtils.INSTANCE.isEmpty(experienceResult.distance)) {
            myHolder.distance.setVisibility(View.GONE);
        }else {
            myHolder.distance.setVisibility(View.VISIBLE);
            myHolder.distance.setText(experienceResult.distance);
        }

        if(experienceResult.getIsPanorama() != null){
            if(experienceResult.getIsPanorama() == 0){
                myHolder.threesix.setVisibility(View.INVISIBLE);
            }else{
                myHolder.threesix.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyHolder extends BaseRvAdapter.BaseViewHolder{

        RelativeLayout detail_rela;
        TextView biaoti;
        TextView look;
        ImageView datu;
        TextView info;
        ImageView threesix;
        TextView sortOut;
        TextView distance;

        public MyHolder(View itemView) {
            super(itemView);

            detail_rela = (RelativeLayout) itemView.findViewById(R.id.detail_rela);
            RelativeLayout.LayoutParams llp_detail_rela = (RelativeLayout.LayoutParams)detail_rela.getLayoutParams();
            llp_detail_rela.height = CommonUtil.getRealWidth(612);
            llp_detail_rela.bottomMargin = CommonUtil.getRealWidth(20);

            biaoti = (TextView) itemView.findViewById(R.id.biaoti);
            biaoti.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(36));
            RelativeLayout.LayoutParams llp_biaoti = (RelativeLayout.LayoutParams)biaoti.getLayoutParams();
            llp_biaoti.leftMargin = CommonUtil.getRealWidth(30);
            llp_biaoti.topMargin = CommonUtil.getRealWidth(30);

            look = (TextView) itemView.findViewById(R.id.look);
            look.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
            RelativeLayout.LayoutParams llp_look = (RelativeLayout.LayoutParams)look.getLayoutParams();
            llp_look.topMargin = CommonUtil.getRealWidth(34);
            llp_look.rightMargin = CommonUtil.getRealWidth(30);

            datu = (ImageView) itemView.findViewById(R.id.datu);
            RelativeLayout.LayoutParams llp_datu = (RelativeLayout.LayoutParams)datu.getLayoutParams();
            llp_datu.width = CommonUtil.getRealWidth(690);
            llp_datu.height = CommonUtil.getRealWidth(386);
            llp_datu.topMargin = CommonUtil.getRealWidth(114);

            info = (TextView) itemView.findViewById(R.id.info);
            info.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(30));
            RelativeLayout.LayoutParams llp_info = (RelativeLayout.LayoutParams)info.getLayoutParams();
            llp_info.leftMargin = CommonUtil.getRealWidth(30);
            llp_info.rightMargin = CommonUtil.getRealWidth(30);
            llp_info.topMargin = CommonUtil.getRealWidth(516);
            llp_info.bottomMargin = CommonUtil.getRealWidth(16);

            threesix = (ImageView) itemView.findViewById(R.id.threesix);
            RelativeLayout.LayoutParams llp_threesix = (RelativeLayout.LayoutParams)threesix.getLayoutParams();
            llp_threesix.width = CommonUtil.getRealWidth(104);
            llp_threesix.height = llp_threesix.width;

            sortOut = (TextView) itemView.findViewById(R.id.sortOut);
            sortOut.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(36));
            RelativeLayout.LayoutParams llp_sortOut = (RelativeLayout.LayoutParams)sortOut.getLayoutParams();
            llp_sortOut.topMargin = CommonUtil.getRealWidth(280);

            distance = itemView.findViewById(R.id.distance);

        }
    }
}

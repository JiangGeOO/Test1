package com.yidao.threekmo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.ijustyce.fastkotlin.glide.ImageLoader;
import com.yidao.threekmo.R;
import com.yidao.threekmo.bean.AchieveBinnerResult;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.v2.utils.AppImage;

/**
 * Created by Smart~ on 2017/11/10.
 */

public class NewFirstFragmentHeaderAdapter extends BaseRvAdapter<AchieveBinnerResult.DataBean> {

    public NewFirstFragmentHeaderAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = layoutInflater.inflate(R.layout.item_new_first_fragment_header,parent,false);
        return new MyHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        AchieveBinnerResult.DataBean mainHomeListItem = dataList.get(position);
        String url = mainHomeListItem.getPhoto();

        AppImage.INSTANCE.load(myHolder.imageview, url, 16, 540, 310);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyHolder extends BaseRvAdapter.BaseViewHolder{

        RelativeLayout imageview_rela;
        ImageView imageview;

        public MyHolder(View itemView) {
            super(itemView);

            imageview_rela = (RelativeLayout) itemView.findViewById(R.id.imageview_rela);
            RelativeLayout.LayoutParams llp_imageview_rela = (RelativeLayout.LayoutParams)imageview_rela.getLayoutParams();
            llp_imageview_rela.width = CommonUtil.getRealWidth(540);
            llp_imageview_rela.height = CommonUtil.getRealWidth(310);
            llp_imageview_rela.topMargin = CommonUtil.getRealWidth(20);
            llp_imageview_rela.leftMargin = CommonUtil.getRealWidth(40);

            imageview = (ImageView) itemView.findViewById(R.id.imageview);
            RelativeLayout.LayoutParams llp_imageview = (RelativeLayout.LayoutParams)imageview.getLayoutParams();
            llp_imageview.rightMargin = CommonUtil.getRealWidth(16);
            llp_imageview.bottomMargin = CommonUtil.getRealWidth(16);


        }
    }
}

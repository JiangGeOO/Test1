package com.yidao.threekmo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ijustyce.fastkotlin.glide.ImageLoader;
import com.yidao.myutils.log.LogUtils;
import com.yidao.threekmo.R;
import com.yidao.threekmo.bean.ShopDetailResult;
import com.yidao.threekmo.customview.LinearTagLayout;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.v2.utils.AppImage;

/**
 * Created by Smart~ on 2017/11/21.
 */

public class MyFollowShopAdapter extends BaseRvAdapter<ShopDetailResult> {

    public MyFollowShopAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = layoutInflater.inflate(R.layout.item_my_follow_shop,parent,false);
        return new MyHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        if(dataList != null){
            ShopDetailResult shopDetailResult = dataList.get(position);
            AppImage.INSTANCE.load(myHolder.item_image, shopDetailResult.getPhotoUrl(), 12, ImageLoader.TYPE_ALL);
            myHolder.shopName.setText(shopDetailResult.getName());
            myHolder.shopLoc.setText(shopDetailResult.getAddress());
            String[] tags = getTags(shopDetailResult.getLabelNames());
            LinearTagLayout ltgTags = myHolder.ltgTags;
            if(tags.length>0){
                ltgTags.setTagList(tags);
            }else {
                ltgTags.removeAllViews();
            }

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

    class MyHolder extends BaseRvAdapter.BaseViewHolder{

        RelativeLayout item_rela;
        ImageView item_image;
        TextView shopName;
        LinearTagLayout ltgTags;
        TextView shopLoc;

        public MyHolder(View itemView) {
            super(itemView);

            item_rela = (RelativeLayout) itemView.findViewById(R.id.item_rela);
            RelativeLayout.LayoutParams llp_item_rela = (RelativeLayout.LayoutParams)item_rela.getLayoutParams();
            llp_item_rela.height = CommonUtil.getRealWidth(224);

            item_image = (ImageView) itemView.findViewById(R.id.item_image);
            RelativeLayout.LayoutParams llp_item_image = (RelativeLayout.LayoutParams)item_image.getLayoutParams();
            llp_item_image.width = CommonUtil.getRealWidth(186);
            llp_item_image.height = llp_item_image.width;
            llp_item_image.leftMargin = CommonUtil.getRealWidth(30);

            shopName = (TextView) itemView.findViewById(R.id.shopName);
            shopName.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
            RelativeLayout.LayoutParams llp_shopName = (RelativeLayout.LayoutParams)shopName.getLayoutParams();
            llp_shopName.leftMargin = CommonUtil.getRealWidth(230);
            llp_shopName.topMargin = CommonUtil.getRealWidth(28);

            ltgTags = (LinearTagLayout) itemView.findViewById(R.id.ltgTags);
            ltgTags.setNum(1);
            RelativeLayout.LayoutParams llp_ltgTags = (RelativeLayout.LayoutParams)ltgTags.getLayoutParams();
            llp_ltgTags.height = CommonUtil.getRealWidth(52);
            llp_ltgTags.leftMargin = CommonUtil.getRealWidth(230);
            llp_ltgTags.topMargin = CommonUtil.getRealWidth(84);

            shopLoc = (TextView) itemView.findViewById(R.id.shopLoc);
            shopLoc.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(28));
            RelativeLayout.LayoutParams llp_shopLoc = (RelativeLayout.LayoutParams)shopLoc.getLayoutParams();
            llp_shopLoc.leftMargin = CommonUtil.getRealWidth(230);
            llp_shopLoc.topMargin = CommonUtil.getRealWidth(156);

        }
    }
}

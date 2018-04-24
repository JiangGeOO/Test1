package com.yidao.threekmo.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidao.threekmo.R;
import com.yidao.threekmo.utils.CommonUtil;


public class ChoosePopupWindow extends PopupWindow{

	private RelativeLayout lover_rela;
	private RelativeLayout money_rela;
	private ImageView money_close;
	private TextView zhifu_money;
	private TextView zhifu_num;
	private RelativeLayout zhifubao_rela;
	private ImageView zhifubao_rela_logo;
	private TextView zhifubao_rela_name;
	private TextView zhifubao_rela_scale;
	private ImageView zhifubao_rela_choose;
	private RelativeLayout weixin_rela;
	private ImageView weixin_rela_logo;
	private TextView weixin_rela_name;
	private TextView weixin_rela_scale;
	private ImageView weixin_rela_choose;
	private RelativeLayout scale_rela;
	private TextView scale_rela_text;

	private int scaleType = 1;


	private View mView;

	public OnButtonClickListener onButtonClickListener;

	public interface OnButtonClickListener{
		void itemButtonListener(int position);
	}

	public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener){
		this.onButtonClickListener = onButtonClickListener;
	}
	
	public ChoosePopupWindow(final Activity context, double money) {
		super(context);
		
		LayoutInflater layout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = layout.inflate(R.layout.popupwindow_choose, null);
		
		lover_rela = (RelativeLayout) mView.findViewById(R.id.lover_rela);
		RelativeLayout.LayoutParams llp_rela = (RelativeLayout.LayoutParams)lover_rela.getLayoutParams();
		llp_rela.width = CommonUtil.screenWidth;
		llp_rela.height = CommonUtil.getRealWidth(528);

		money_rela = (RelativeLayout) mView.findViewById(R.id.money_rela);
		RelativeLayout.LayoutParams llp_money_rela = (RelativeLayout.LayoutParams)money_rela.getLayoutParams();
		llp_money_rela.height = CommonUtil.getRealWidth(92);

		money_close = (ImageView) mView.findViewById(R.id.money_close);
		RelativeLayout.LayoutParams llp_money_close = (RelativeLayout.LayoutParams)money_close.getLayoutParams();
		llp_money_close.width = CommonUtil.getRealWidth(32);
		llp_money_close.height = llp_money_close.width;
		llp_money_close.leftMargin = CommonUtil.getRealWidth(30);

		zhifu_money = (TextView) mView.findViewById(R.id.zhifu_money);
		zhifu_money.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
		RelativeLayout.LayoutParams llp_zhifu_money = (RelativeLayout.LayoutParams)zhifu_money.getLayoutParams();
		llp_zhifu_money.leftMargin = CommonUtil.getRealWidth(256);

		zhifu_num = (TextView) mView.findViewById(R.id.zhifu_num);
		zhifu_num.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
		RelativeLayout.LayoutParams llp_zhifu_num= (RelativeLayout.LayoutParams)zhifu_num.getLayoutParams();
		llp_zhifu_num.leftMargin = CommonUtil.getRealWidth(5);

		zhifubao_rela = (RelativeLayout) mView.findViewById(R.id.zhifubao_rela);
		RelativeLayout.LayoutParams llp_zhifubao_rela = (RelativeLayout.LayoutParams)zhifubao_rela.getLayoutParams();
		llp_zhifubao_rela.height = CommonUtil.getRealWidth(130);

		zhifubao_rela_logo = (ImageView) mView.findViewById(R.id.zhifubao_rela_logo);
		RelativeLayout.LayoutParams llp_zhifubao_rela_logo = (RelativeLayout.LayoutParams)zhifubao_rela_logo.getLayoutParams();
		llp_zhifubao_rela_logo.width = CommonUtil.getRealWidth(68);
		llp_zhifubao_rela_logo.height = llp_zhifubao_rela_logo.width;
		llp_zhifubao_rela_logo.leftMargin = CommonUtil.getRealWidth(30);

		zhifubao_rela_name = (TextView) mView.findViewById(R.id.zhifubao_rela_name);
		zhifubao_rela_name.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
		RelativeLayout.LayoutParams llp_zhifubao_rela_name = (RelativeLayout.LayoutParams)zhifubao_rela_name.getLayoutParams();
		llp_zhifubao_rela_name.leftMargin = CommonUtil.getRealWidth(118);
		llp_zhifubao_rela_name.topMargin = CommonUtil.getRealWidth(22);

		zhifubao_rela_scale = (TextView) mView.findViewById(R.id.zhifubao_rela_scale);
		zhifubao_rela_scale.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(24));
		RelativeLayout.LayoutParams llp_zhifubao_rela_scale = (RelativeLayout.LayoutParams)zhifubao_rela_scale.getLayoutParams();
		llp_zhifubao_rela_scale.leftMargin = CommonUtil.getRealWidth(118);
		llp_zhifubao_rela_scale.topMargin = CommonUtil.getRealWidth(74);

		zhifubao_rela_choose = (ImageView) mView.findViewById(R.id.zhifubao_rela_choose);
		RelativeLayout.LayoutParams llp_zhifubao_rela_choose = (RelativeLayout.LayoutParams)zhifubao_rela_choose.getLayoutParams();
		llp_zhifubao_rela_choose.width = CommonUtil.getRealWidth(40);
		llp_zhifubao_rela_choose.height = llp_zhifubao_rela_choose.width;
		llp_zhifubao_rela_choose.rightMargin = CommonUtil.getRealWidth(32);

		weixin_rela = (RelativeLayout) mView.findViewById(R.id.weixin_rela);
		RelativeLayout.LayoutParams llp_weixin_rela = (RelativeLayout.LayoutParams)weixin_rela.getLayoutParams();
		llp_weixin_rela.height = CommonUtil.getRealWidth(130);

		weixin_rela_logo = (ImageView) mView.findViewById(R.id.weixin_rela_logo);
		RelativeLayout.LayoutParams llp_weixin_rela_logo = (RelativeLayout.LayoutParams)weixin_rela_logo.getLayoutParams();
		llp_weixin_rela_logo.width = CommonUtil.getRealWidth(68);
		llp_weixin_rela_logo.height = llp_weixin_rela_logo.width;
		llp_weixin_rela_logo.leftMargin = CommonUtil.getRealWidth(30);

		weixin_rela_name = (TextView) mView.findViewById(R.id.weixin_rela_name);
		weixin_rela_name.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
		RelativeLayout.LayoutParams llp_weixin_rela_name = (RelativeLayout.LayoutParams)weixin_rela_name.getLayoutParams();
		llp_weixin_rela_name.leftMargin = CommonUtil.getRealWidth(118);
		llp_weixin_rela_name.topMargin = CommonUtil.getRealWidth(22);

		weixin_rela_scale = (TextView) mView.findViewById(R.id.weixin_rela_scale);
		weixin_rela_scale.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(24));
		RelativeLayout.LayoutParams llp_weixin_rela_scale = (RelativeLayout.LayoutParams)weixin_rela_scale.getLayoutParams();
		llp_weixin_rela_scale.leftMargin = CommonUtil.getRealWidth(118);
		llp_weixin_rela_scale.topMargin = CommonUtil.getRealWidth(74);

		weixin_rela_choose = (ImageView) mView.findViewById(R.id.weixin_rela_choose);
		RelativeLayout.LayoutParams llp_weixin_rela_choose = (RelativeLayout.LayoutParams)weixin_rela_choose.getLayoutParams();
		llp_weixin_rela_choose.width = CommonUtil.getRealWidth(40);
		llp_weixin_rela_choose.height = llp_weixin_rela_choose.width;
		llp_weixin_rela_choose.rightMargin = CommonUtil.getRealWidth(32);

		scale_rela = (RelativeLayout) mView.findViewById(R.id.scale_rela);
		RelativeLayout.LayoutParams llp_scale_rela = (RelativeLayout.LayoutParams)scale_rela.getLayoutParams();
		llp_scale_rela.height = CommonUtil.getRealWidth(98);
		llp_scale_rela.width = CommonUtil.getRealWidth(690);
		llp_scale_rela.topMargin = CommonUtil.getRealWidth(48);

		scale_rela_text = (TextView) mView.findViewById(R.id.scale_rela_text);
		scale_rela_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(32));
		zhifu_num.setText("￥"+money);

		money_close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		zhifubao_rela.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				zhifubao_rela_choose.setImageDrawable(context.getResources().getDrawable(R.mipmap.money_choose));
				weixin_rela_choose.setImageDrawable(context.getResources().getDrawable(R.mipmap.money_unchoose));
				scaleType = 1;
			}
		});

		weixin_rela.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				zhifubao_rela_choose.setImageDrawable(context.getResources().getDrawable(R.mipmap.money_unchoose));
				weixin_rela_choose.setImageDrawable(context.getResources().getDrawable(R.mipmap.money_choose));
				scaleType = 2;
			}
		});

		scale_rela.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onButtonClickListener.itemButtonListener(scaleType);
				dismiss();
			}
		});

		this.setContentView(mView);
		this.setWidth(LayoutParams.FILL_PARENT);
//		this.setHeight(CommonUtil.getHeightByDesignHeight(1334));
		this.setHeight(LayoutParams.MATCH_PARENT);
		this.setFocusable(true);
//		this.setAnimationStyle(R.style.PopupAnimation);
		ColorDrawable dw = new ColorDrawable(0xb0000000);
//		ColorDrawable dw = new ColorDrawable(Color.argb(0, 0, 0, 0));
		AnimationSet animationSet = new AnimationSet(true); 
		TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,  
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,  
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF,  
                0.0f); 
		translateAnimation.setDuration(300); 
		animationSet.addAnimation(translateAnimation); 
		lover_rela.startAnimation(animationSet);
		this.setBackgroundDrawable(dw);
		mView.setOnTouchListener(new OnTouchListener() {
			
			@Override
            public boolean onTouch(View v, MotionEvent event) {
				
				int height = mView.findViewById(R.id.lover_rela).getTop();
				int y=(int) event.getY();
				if(event.getAction()==MotionEvent.ACTION_UP){
					if(y<height){
						dismiss();
					}
				}				
				return true;
			}
		});
		
	}

}

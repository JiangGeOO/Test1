package com.yidao.threekmo.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.OSSRequest;
import com.alibaba.sdk.android.oss.model.OSSResult;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.ijustyce.fastkotlin.utils.CommonTools;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.yidao.myutils.date.TimeUtils;
import com.yidao.myutils.log.LogUtils;
import com.yidao.myutils.toast.ToastUtil;
import com.yidao.threekmo.R;
import com.yidao.threekmo.actions.CompressAction;
import com.yidao.threekmo.application.MyApplication;
import com.yidao.threekmo.bean.BaseResult;
import com.yidao.threekmo.bean.UserBean;
import com.yidao.threekmo.customview.SuccessToast;
import com.yidao.threekmo.retrofit_server.UserServer;
import com.yidao.threekmo.utils.CommonUtil;
import com.yidao.threekmo.utils.LoginUtils;
import com.yidao.threekmo.utils.OSSUtils;
import com.yidao.threekmo.utils.RetrofitUtils;
import com.yidao.threekmo.v2.utils.AppImage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoEditActivity extends BaseActivity implements View.OnClickListener {
    private static final int IMAGE_PICKER = 1;

    private TimePickerView pvTime;
    private OptionsPickerView pvOptions;
    private ImageView ivUserIcon;
    private RelativeLayout rlChangeUserIcon;
    private EditText etName;
    private LinearLayout llSex;
    private TextView tvSex;
    private LinearLayout llBirthDay;
    private TextView tvBrithDay;
    private EditText etDesc;
    private UserBean userBean;
    private String faceOriginal;
    private ProgressDialog waitingDialog;

    private RelativeLayout title_rela;
    private ImageView ivBack;
    private TextView title_text;
    private TextView tvSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_edit);

        viewInit();

        initTimePicker();
        initOptionPicker();

        dataInit();
    }

    private void dataInit() {
        userBean = LoginUtils.getUserBean(UserInfoEditActivity.this);

        String name = userBean.getNickname();
        if(null!=name&&!TextUtils.isEmpty(name)){
            etName.setText(name);
        }else {
            etName.setText("未名");
        }

        Long sex = userBean.getSex();
        if(sex==0){
            tvSex.setText("男");
        }else {
            tvSex.setText("女");
        }


        String face = userBean.getFace();
        if (null != face && !TextUtils.isEmpty(face)) {
            AppImage.INSTANCE.circle(ivUserIcon, face);
        }

        long birthday = userBean.getBirthday();
        if(birthday!=0){
            String bd = TimeUtils.getTime3(birthday);
            tvBrithDay.setText(bd);
        }else {
            tvBrithDay.setText("暂无生日信息");
        }

        String individualitySignature = userBean.getIndividualitySignature();
        if(null!=individualitySignature&&!TextUtils.isEmpty(individualitySignature)){
            etDesc.setText(individualitySignature);
        }
    }

    private void viewInit() {

        title_rela = (RelativeLayout) findViewById(R.id.title_rela);
        LinearLayout.LayoutParams llp_title_rela = (LinearLayout.LayoutParams)title_rela.getLayoutParams();
        llp_title_rela.height = CommonUtil.getRealWidth(130);

        title_text = (TextView) findViewById(R.id.title_text);
        title_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(36));
        RelativeLayout.LayoutParams llp_title_text = (RelativeLayout.LayoutParams)title_text.getLayoutParams();
        llp_title_text.topMargin = CommonUtil.getRealWidth(60);

        ivBack = (ImageView) findViewById(R.id.ivBack);
        RelativeLayout.LayoutParams llp_ivBack = (RelativeLayout.LayoutParams)ivBack.getLayoutParams();
        llp_ivBack.width = CommonUtil.getRealWidth(52);
        llp_ivBack.height = llp_ivBack.width;
        llp_ivBack.leftMargin = CommonUtil.getRealWidth(30);
        llp_ivBack.topMargin = CommonUtil.getRealWidth(58);
        ivBack.setOnClickListener(this);

        tvSave = (TextView) findViewById(R.id.tvSave);
        tvSave.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(36));
        RelativeLayout.LayoutParams llp_tvSave = (RelativeLayout.LayoutParams)tvSave.getLayoutParams();
        llp_tvSave.rightMargin = CommonUtil.getRealWidth(30);
        llp_tvSave.topMargin = CommonUtil.getRealWidth(60);
        tvSave.setOnClickListener(this);

        ivUserIcon = (ImageView) findViewById(R.id.ivUserIcon);
        rlChangeUserIcon = (RelativeLayout) findViewById(R.id.rlChangeUserIcon);
        rlChangeUserIcon.setOnClickListener(this);

        etName = (EditText) findViewById(R.id.etName);
        etName.addTextChangedListener(new TextWatcher() {
            private String name;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvSave.setEnabled(true);
                userBean.setNickname(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        llSex = (LinearLayout) findViewById(R.id.llSex);
        llSex.setOnClickListener(this);
        tvSex = (TextView) findViewById(R.id.tvSex);

        llBirthDay = (LinearLayout) findViewById(R.id.llBirthDay);
        llBirthDay.setOnClickListener(this);
        tvBrithDay = (TextView) findViewById(R.id.tvBrithDay);

        etDesc = (EditText) findViewById(R.id.etDesc);
        etDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvSave.setEnabled(true);
                userBean.setIndividualitySignature(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initOptionPicker() {//条件选择器初始化

        /**
         * 注意 ：如果是三级联动的数据(省市区等)，请参照 JsonDataActivity 类里面的写法。
         */
        final List<String> sexList = new ArrayList<String>();
        sexList.add("男");
        sexList.add("女");

        pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = sexList.get(options1);
                // 性别  0:男 1:女
                tvSave.setEnabled(true);
                if("男".equals(tx)){
                    userBean.setSex(0L);
                    tvSex.setText("男");
                }else {
                    userBean.setSex(1L);
                    tvSex.setText("女");
                }

            }
        })
                .setTitleText("性别选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .setSelectOptions(0, 1)//默认选中项
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .build();

        //pvOptions.setSelectOptions(1,1);
        /*pvOptions.setPicker(options1Items);//一级选择器*/
        pvOptions.setPicker(sexList);//二级选择器
        /*pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器*/
    }


    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(1990,0,1);
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 0, 1);
        Calendar endDate = Calendar.getInstance();
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
                tvSave.setEnabled(true);
                String time = getTime(date);
                LogUtils.e("生日:" + TimeUtils.dataToMs(time));
                userBean.setBirthday(TimeUtils.dataToMs(time));
                tvBrithDay.setText(time);
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;

            case R.id.tvSave:
                uoloadInfo();
                break;

            case R.id.rlChangeUserIcon:
                ImagePicker.getInstance().setCrop(true);
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, IMAGE_PICKER);
                break;

            case R.id.llSex:
                pvOptions.show();
                break;
            case R.id.llBirthDay:
                pvTime.show();
                break;

            default:
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                faceOriginal = images.get(0).path;
                compressPic();
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void upLoadIcon(String photoPath){
        final String headPicName =  TimeUtils.getTime1() + "_" +userBean.getId() +".png" ;
        OSSUtils.upLoadFile(getString(R.string.oss_bucketname), headPicName, photoPath, null, new OSSCompletedCallback() {
            @Override
            public void onSuccess(OSSRequest ossRequest, OSSResult ossResult) {
                LogUtils.e("onSuccess1");
                String userHeadPhoto = MyApplication.getInstance().getOss().presignPublicObjectURL(getString(R.string.oss_bucketname), headPicName);
                LogUtils.e("onSuccess2");
//                Glide.with(UserInfoEditActivity.this).load(userHeadPhoto).into(ivUserIcon);


                LogUtils.e("photo:"+userHeadPhoto);
                LogUtils.e("onSuccess3");
                userBean.setFace(userHeadPhoto);
                if(waitingDialog.isShowing()){
                    waitingDialog.cancel();
                    LogUtils.e("onSuccess4");
                }


            }

            @Override
            public void onFailure(OSSRequest ossRequest, ClientException e, ServiceException e1) {
                LogUtils.e("ClientException:"+e.getMessage() + "  ServiceException:" +e1.getMessage());
                if(waitingDialog.isShowing()){
                    waitingDialog.cancel();
                }
            }

        });


    }

    private void compressPic(){
        CompressAction compressAction = new CompressAction(UserInfoEditActivity.this);
        ArrayList<String> strings = new ArrayList<>();
        strings.add(faceOriginal);
        compressAction.setCompressListener(new CompressAction.CompressListener() {
            @Override
            public void onPreExecute() {
                waitingDialog= new ProgressDialog(UserInfoEditActivity.this);
                waitingDialog.setMessage("图片压缩中...");
                waitingDialog.setCanceledOnTouchOutside(false);
                waitingDialog.show();
            }

            @Override
            public void onUpdate(int index) {

            }

            @Override
            public void onPostExecute(List<String> imageList) {
                if (imageList == null || imageList.isEmpty()) return;
                String path = imageList.get(0);
                ivUserIcon.setImageBitmap(CommonTools.INSTANCE.compressImageFromFile(path));

                waitingDialog.setMessage("正在上传头像...");
                upLoadIcon(path);
            }

            @Override
            public void onCancle() {
                if(waitingDialog.isShowing()){
                    waitingDialog.cancel();
                }
            }
        });
        compressAction.execute(strings);
    }

    private void uoloadInfo(){
        if(checkParameters()){
            Call<BaseResult> call = RetrofitUtils.getRetrofit().create(UserServer.class).changeUserInfo(LoginUtils.getToken(UserInfoEditActivity.this), userBean.getFace(), userBean.getNickname(), TimeUtils.getTime3(userBean.getBirthday()), userBean.getIndividualitySignature(),userBean.getSex()+"");
            addCall(call);
            call.enqueue(new Callback<BaseResult>() {
                @Override
                public void onResponse(Call<BaseResult> call, Response<BaseResult> response) {
                    BaseResult baseResult = response.body();
                    if(baseResult.getRspCode() == 0){
                        SuccessToast.showToast("修改数据成功",false,UserInfoEditActivity.this);
                        LoginUtils.saveUserBean(userBean,UserInfoEditActivity.this);
                        MyApplication.getInstance().setNeedRefresh(true);
//                        startActivity(new Intent(UserInfoEditActivity.this,MainActivity.class));
                        startActivity(new Intent(UserInfoEditActivity.this,IndexActivity.class));
                    }else {
                        ToastUtil.showToast(baseResult.getRspMsg(),false,UserInfoEditActivity.this);
                    }
                }

                @Override
                public void onFailure(Call<BaseResult> call, Throwable t) {
                    ToastUtil.showToast(t.getMessage(),false,UserInfoEditActivity.this);
                }
            });
        }
    }

    private boolean checkParameters(){
        if(TextUtils.isEmpty(userBean.getNickname())){
            ToastUtil.showToast("名字不可为空",false,UserInfoEditActivity.this);
            return false;
        }
        if(TextUtils.isEmpty(userBean.getIndividualitySignature())){
            ToastUtil.showToast("签名不可为空",false,UserInfoEditActivity.this);
            return false;
        }
        return true;
    }

}

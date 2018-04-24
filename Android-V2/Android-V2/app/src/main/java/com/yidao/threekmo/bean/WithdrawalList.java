package com.yidao.threekmo.bean;

import android.graphics.Color;
import android.view.View;

import com.ijustyce.fastkotlin.base.BaseViewModel;
import com.ijustyce.fastkotlin.irecyclerview.IResponseData;
import com.yidao.threekmo.R;
import org.jetbrains.annotations.NotNull;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by deepin on 18-2-6.
 */

public class WithdrawalList implements IResponseData<WithdrawalList.DataBean.DatasBean>{

    public DataBean data;
    public int rspCode;
    public String rspMsg;

    @NotNull
    @Override
    public ArrayList<DataBean.DatasBean> getList() {
        if (data == null || data.datas == null) return new ArrayList<>();
        return data.datas;
    }

    public static class DataBean {
        public int start;
        public int pageIndex;
        public int pageSize;
        public int pageCount;
        public int totalCount;
        public int errorCode;
        public Object errorMsg;
        public boolean success;
        public ArrayList<DatasBean> datas;

        public static class DatasBean extends BaseViewModel {
            public int id;
            public long gmtCreate;
            public long gmtModified;
            public Object creator;
            public Object modifier;
            public String orderNumber;
            public Object orderId;
            public int userId;
            public String openId;
            public String wxNickname;
            public double amount;
            public double poundage;
            public String errorMsg;
            public Object payDate;
            public int status;
            public String spbillCreateIp;
            public String type;
            public int enable;
            public Object payeeAccount;
            public String payeeRealName;

            public String money() {
                return "-" + amount;
            }

            public String id() {
                return "" + id;
            }

            public int textColor() {
                switch (status) {
                    case 1:
                        return Color.parseColor("#ff8422");
                    case 2:
                        return Color.parseColor("#e5270f");
                    default:
                        return Color.parseColor("#333333");
                }
            }

            public int icon() {
                switch (status) {
                    case 1:
                        return R.mipmap.icon_withdrawal_processing;
                    case 2:
                        return R.mipmap.icon_withdrawal_success;
                    default:
                        return R.mipmap.icon_withdrawal_failed;
                }
            }

            public int lineVisible() {
                return getPosition() == 0 ? View.INVISIBLE : View.VISIBLE;
            }

            public String status() {
                switch (status) {
                    case 1:
                        return "处理中";
                    case 2:
                        return "提现成功";
                    default:
                        return "提现失败";
                }
            }

            public String create() {
                SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                return format.format(new Date(gmtCreate));
            }
        }
    }
}

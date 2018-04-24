package com.yidao.threekmo.customview;

import android.app.Activity;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.yidao.threekmo.R;
import com.yidao.threekmo.bean.DialogSecondResult;
import com.yidao.threekmo.databinding.IndexDialogView;
import com.yidao.threekmo.v2.viewmodel.IndexDialogVm;

/**
 * Created by Administrator on 2017\9\6 0006.
 */

public class IndexEaseDialog extends Dialog {

    private Activity activity;
    private DialogSecondResult data;
    private View bottomView;

    public IndexEaseDialog(Activity activity, DialogSecondResult data, View bottomView) {
        super(activity, R.style.dialog);
        this.activity = activity;
        this.data = data;
        this.bottomView = bottomView;
        this.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IndexDialogView dialogView = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.custom_index_dialog_mvvm, null, false);
        dialogView.setData(data);
        IndexDialogVm viewModel = new IndexDialogVm(activity, this,bottomView, null);
        dialogView.setEvent(viewModel);
        setContentView(dialogView.getRoot());
    }
}

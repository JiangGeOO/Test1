package com.yidao.threekmo.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yidao.threekmo.R;

/**
 * Created by Administrator on 2017\9\6 0006.
 */

public class CopyEaseDialog extends Dialog {

    public interface AlertDialogUser {
        public void onResult(boolean confirmed, Bundle bundle);
    }
    private String title;
    private String msg;
    private AlertDialogUser user;
    private Bundle bundle;
    private boolean showCancel = false;

//    public CopyEaseDialog(Context context, int msgId) {
//        super(context, R.style.MyAlertDialog);
//        this.title = context.getResources().getString(R.string.prompt);
//        this.msg = context.getResources().getString(msgId);
//        this.setCanceledOnTouchOutside(false);
//    }
//
//    public CopyEaseDialog(Context context, String msg) {
//        super(context, R.style.MyAlertDialog);
//        this.title = context.getResources().getString(R.string.prompt);
//        this.msg = msg;
//        this.setCanceledOnTouchOutside(false);
//    }

    public CopyEaseDialog(Context context, int titleId, int msgId) {
        super(context, R.style.dialog);
//        super(context);
        this.title = context.getResources().getString(titleId);
        this.msg = context.getResources().getString(msgId);
        this.setCanceledOnTouchOutside(false);
    }

    public CopyEaseDialog(Context context, String title, String msg) {
        super(context, R.style.dialog);
//        super(context);
        this.title = title;
        this.msg = msg;
        this.setCanceledOnTouchOutside(false);
    }

    public CopyEaseDialog(Context context, int titleId, int msgId, Bundle bundle, AlertDialogUser user,
                          boolean showCancel) {
        super(context, R.style.dialog);
//        super(context);
        this.title = context.getResources().getString(titleId);
        this.msg = context.getResources().getString(msgId);
        this.user = user;
        this.bundle = bundle;
        this.showCancel = showCancel;
        this.setCanceledOnTouchOutside(false);
    }

    public CopyEaseDialog(Context context, String title, String msg, Bundle bundle, AlertDialogUser user,
                          boolean showCancel) {
        super(context, R.style.dialog);
//        super(context);
        this.title = title;
        this.msg = msg;
        this.user = user;
        this.bundle = bundle;
        this.showCancel = showCancel;
        this.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.copy_ease_dialog);
        LinearLayout text_linear = (LinearLayout) findViewById(R.id.text_linear);
        TextView titleview = (TextView) findViewById(R.id.title);
        TextView alert_message = (TextView) findViewById(R.id.alert_message);
        LinearLayout btn_linear = (LinearLayout) findViewById(R.id.btn_linear);
        Button btn_cancel = (Button) findViewById(R.id.btn_cancel);
        Button btn_ok = (Button) findViewById(R.id.btn_ok);

        setTitle(title);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btn_ok) {
                    onOk(view);
                } else if (view.getId() == R.id.btn_cancel) {
                    onCancel(view);
                }
            }
        };
        btn_cancel.setOnClickListener(listener);
        btn_ok.setOnClickListener(listener);

        if (title != null) {
            titleview.setText(title);
        } else {
            titleview.setVisibility(View.GONE);
        }

        if(msg != null) {
            alert_message.setText(msg);
        }else{
            alert_message.setVisibility(View.GONE);
        }

        if (showCancel) {
            btn_cancel.setVisibility(View.VISIBLE);
        }

//        if (msg != null)
//            ((TextView) findViewById(R.id.alert_message)).setText(msg);
    }

    public void onOk(View view) {
        this.dismiss();
        if (this.user != null) {
            this.user.onResult(true, this.bundle);
        }
    }

    public void onCancel(View view) {
        this.dismiss();
        if (this.user != null) {
            this.user.onResult(false, this.bundle);
        }
    }
}

package com.yidao.threekmo.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidao.threekmo.R;
import com.yidao.threekmo.utils.CommonUtil;

import java.io.IOException;
import java.io.InputStream;

public class UserSignInfoActivity extends BaseActivity {

    private RelativeLayout title_rela;
    private TextView title_text;
    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_info);

        initViews();

    }

    private void initViews() {

        title_rela = (RelativeLayout) findViewById(R.id.title_rela);
        RelativeLayout.LayoutParams llp_title_rela = (RelativeLayout.LayoutParams)title_rela.getLayoutParams();
        llp_title_rela.height = CommonUtil.getRealWidth(130);

        title_text = (TextView) findViewById(R.id.title_text);
        title_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,CommonUtil.getRealWidth(36));
        RelativeLayout.LayoutParams llp_title_text = (RelativeLayout.LayoutParams)title_text.getLayoutParams();
        llp_title_text.topMargin = CommonUtil.getRealWidth(56);

        content = (TextView) findViewById(R.id.content);

        InputStream inputStream = null;
        try {
            inputStream = getAssets().open("驿道用户注册与服务协议.txt");
            int size = inputStream.available();
            int len = -1;
            byte[] bytes = new byte[size];
            inputStream.read(bytes);
            inputStream.close();
            String string = new String(bytes);
            content.setText(string);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

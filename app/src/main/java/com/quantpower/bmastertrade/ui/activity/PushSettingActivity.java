package com.quantpower.bmastertrade.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.quantpower.bmastertrade.R;
import com.quantpower.bmastertrade.utils.UIHelper;
import com.quantpower.bmastertrade.widget.extend.ToggleButton;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by ShuLin on 2017/5/17.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class PushSettingActivity extends Activity {
    ImageView imageBack;
    private ToggleButton tbPush;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pushsettingactivity);
        imageBack = (ImageView) findViewById(R.id.image_back);
        tbPush = (ToggleButton) findViewById(R.id.tb_information_push);
        imageBack.setOnClickListener(v -> finish());
        tbPush.setOnToggleChanged((toggleButton, on) -> {
            if (on) {
                UIHelper.toastMessage(PushSettingActivity.this, "资讯推送已打开");
            } else {
                UIHelper.toastMessage(PushSettingActivity.this, "资讯推送已关闭");
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);//友盟统计
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);//友盟统计
    }
}

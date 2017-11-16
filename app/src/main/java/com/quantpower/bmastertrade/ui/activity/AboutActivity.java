package com.quantpower.bmastertrade.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.quantpower.bmastertrade.R;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.util.EncodingUtils;

/**
 * Created by ShuLin on 2017/5/18.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class AboutActivity extends Activity {
    ImageView image_back;
    String loadUrl = "   外汇行情软件，短线模拟交易大师，致力于为投资者及对金融感兴趣的人士提供实时行情，新鲜行业快讯，以及热门行业交易产品，让您了解外汇,读懂金融，从外汇开始，助力您每一份财富的实现。\n" +
            "\n   外汇行情软件，免费为广大投资者提供最快最全的财经服务内容，包括：外汇，贵金属资讯行情解读，财经日历，时事要闻，走势行情，专家解读，数据分析，做多做空一键搞定。\n" +
            "\n   外汇行情软件,是一家专业提供外汇软件类型外汇金属、黄金、原油等走势的财经新媒体平台，网站主要是有新闻资讯、学习视频，投资者交流等服务，致力于为投资者提供最及时的一手外汇软件类型外汇资讯。";
    TextView tvAbout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        tvAbout = (TextView) findViewById(R.id.tv_about);
        tvAbout.setText(loadUrl);
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(v -> finish());
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

package com.quantpower.bmastertrade.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quantpower.bmastertrade.R;
import com.quantpower.bmastertrade.constant.URLS;
import com.quantpower.bmastertrade.model.MessageResult;
import com.quantpower.bmastertrade.model.NewsModel;
import com.quantpower.bmastertrade.ui.activity.MainActivity;
import com.quantpower.bmastertrade.utils.Constants;
import com.quantpower.bmastertrade.utils.UIHelper;
import com.quantpower.bmastertrade.widget.extend.MarketWebView;
import com.quantpower.bmastertrade.widget.loding.CustomDialog;
import com.quantpower.bmastertrade.widget.loding.LodingDialog;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


/**
 * Created by ShuLin on 2017/5/18.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */
public class HomeFragment extends Fragment {
    private View rootView;
    private MarketWebView market_WebView;
    private CustomDialog lodingDialog;
    private WebSettings wvSettings;
    private ImageView iv_back_icon;
    private LinearLayout llBottom;
    private TextView tvMoreThanSingle;
    private TextView tvAnEmptyList;
    private String menu_url = "http://m.dyhjw.com/quote/,http://m.dyhjw.com/shanghaihuangjin/,\r\nhttp://m.dyhjw.com/guojijin.html,http://m.dyhjw.com/zhipan.html,\r\nhttp://m.dyhjw.com/guzhi.html,http://m.dyhjw.com/guijinshu.html,\r\nhttp://m.dyhjw.com/meiguoyuanyou/";
    private String webview = "http://m.dyhjw.com/quote/";
    private String jquery = "$('.side_menu_box').remove();$('.top_xl').remove();$('.huadong_box').remove();$('.down_app').remove();$('.container').css('padding-bottom','0');$('.footer').remove();$('.totop').remove();$('.menu_two_list').css('top','0');$('#listcontent li h2 a').css('color','#333333');$('body').append('<style>svg>text:nth-last-child(1){display:none !important}.down_app{display:none !important}</style>');";

    public static HomeFragment newInstance(String s) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_home, container, false);
        this.market_WebView = (MarketWebView) rootView.findViewById(R.id.market_WebView);
        this.iv_back_icon = (ImageView) rootView.findViewById(R.id.image_back);
        this.tvMoreThanSingle = (TextView) rootView.findViewById(R.id.tv_more_than_a_single);
        this.tvAnEmptyList = (TextView) rootView.findViewById(R.id.tv_an_empty_list);
        this.llBottom = (LinearLayout) rootView.findViewById(R.id.ll_bottom);
        this.iv_back_icon.setVisibility(View.GONE);
        this.llBottom.setVisibility(View.GONE);
        tvMoreThanSingle.setOnClickListener(v -> UIHelper.toastMessage(getActivity(), "系统已收到您的模拟申请，会在一个工作日内与您取得联系，为您开通。"));
        tvAnEmptyList.setOnClickListener(v -> UIHelper.toastMessage(getActivity(), "系统已收到您的模拟申请，会在一个工作日内与您取得联系，为您开通。"));
        loadJsCod();
        showWebViewData();
        return rootView;
    }

    private void loadJsCod() {
        market_WebView.loadUrl(webview);
        jquery = "javascript: " + jquery;
        new Handler().postDelayed(() -> lodingDialog.dismiss(), 1500);

    }

    /**
     * 显示行情与隐藏行情上面title标题
     */
    private void showWebViewData() {
        this.market_WebView.setVisibility(View.INVISIBLE);
        lodingDialog = new CustomDialog(getActivity(), "加载中...");
        lodingDialog.show();
        wvSettings = market_WebView.getSettings();
        wvSettings.setJavaScriptEnabled(true);
        wvSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        wvSettings.setAppCacheEnabled(true);
        wvSettings.setDomStorageEnabled(true);
        wvSettings.setDatabaseEnabled(true);
        wvSettings.setAllowFileAccess(true);
        wvSettings.setBlockNetworkImage(true);
        wvSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        wvSettings.setDomStorageEnabled(true);
        market_WebView.setOnLongClickListener(view -> true);
        iv_back_icon.setOnClickListener(v -> {
            market_WebView.goBack();
            market_WebView.setVisibility(View.GONE);
            new Handler().postDelayed(() -> {
                market_WebView.setVisibility(View.VISIBLE);
            }, 1000);
        });

        market_WebView.setOnTouchScreenListener(new MarketWebView.OnTouchScreenListener() {
            @Override
            public void onTouchScreen() {
            }

            @Override
            public void onReleaseScreen() {
            }
        });

        /**
         * 定时执行
         */
        new Thread(() -> {
            int count = 0;
            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        market_WebView.setOnScrollChangeListener(new MarketWebView.OnScrollChangeListener() {

            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
            }

            @Override
            public void onPageTop(int l, int t, int oldl, int oldt) {
            }

            @Override
            public void onPageEnd(int l, int t, int oldl, int oldt) {
            }
        });
        /**
         * WebView 控件webViewClient 所有回调方法
         */
        market_WebView.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (menu_url.indexOf(url) != -1) {
                    iv_back_icon.setVisibility(View.GONE);
                    llBottom.setVisibility(View.GONE);
                } else {
                    iv_back_icon.setVisibility(View.VISIBLE);
                    llBottom.setVisibility(View.VISIBLE);
                }
                market_WebView.setVisibility(View.GONE);

                lodingDialog.show();

            }


            /**
             * 加载给定URL资源内容   该方法待使用    判断webview是否可以返回,不能返回就隐藏返回按钮
             */
            @Override
            public void onLoadResource(final WebView view, String url) {
                view.loadUrl(jquery);
                super.onLoadResource(view, url);
            }

            /**
             * 页面加载完成回调方法，获取对应url地址
             * */
            @Override
            public void onPageFinished(final WebView view, String url) {

                new Handler().postDelayed(() -> {
                    market_WebView.setVisibility(View.VISIBLE);
                    lodingDialog.dismiss();
                }, 100);
                wvSettings.setBlockNetworkImage(false);
                super.onPageFinished(view, url);
            }

            /**
             * 自己定义加载错误处理效果，比如：TeachCourse定义在没有网络时候，显示一张无网络的图片
             * API 23 之后调用
             */
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                view.setVisibility(View.GONE);
                super.onReceivedError(view, request, error);

            }

            /**
             * 自己定义加载错误处理效果，比如：TeachCourse定义在没有网络时候，显示一张无网络的图片
             *API 23之前调用
             */
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                view.setVisibility(View.GONE);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
    }

}

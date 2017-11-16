package com.quantpower.bmastertrade.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.quantpower.bmastertrade.R;
import com.quantpower.bmastertrade.common.ImageLoaderEx;
import com.quantpower.bmastertrade.constant.URLS;
import com.quantpower.bmastertrade.model.GetUserInfoResponse;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ShuLin on 2017/5/18.
 * Email linlin.1016@qq.com
 * Company Shanghai Quantpower Information Technology Co.,Ltd.
 */

public class BaseApplication extends Application {
    public static Context context = null;
    public static Handler handler = null;
    public static Thread mainThread = null;
    public static int mainThreadId = 0;
    private static BaseApplication mInstance;
    public static int WINDOW_WIDTH = 0;
    public static int WINDOW_HEIGHT = 0;
    private List<Activity> mList = new LinkedList<>();
    public static String NEWS_ID_TAG = "0";
    private static final String VERSION_FIRST = "version_first";//设置全局变量,用来判断app版本
    private String version;


    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(false); //是否输出debug日子,开启会影响性能

        context = getApplicationContext();
        handler = new Handler();
        mainThread = Thread.currentThread();
        mainThreadId = android.os.Process.myTid();
        getPhoneInfo();
        this.initImageLoader();
        setVersion(VERSION_FIRST);
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    /**
     * 初始化图片缓存
     */
    private void initImageLoader() {
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .memoryCache(new LruMemoryCache(4 * 1024 * 1024))
                .memoryCacheSize(4 * 1024 * 1024)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCache(new UnlimitedDiskCache(ImageLoaderEx.getCacheDir(this)))
                .diskCacheFileCount(500).build();
        ImageLoader.getInstance().init(configuration);
    }


    /**
     * 初始化App参数
     */
    private void initSysData() {
        this.mInstance = this;
        DisplayMetrics dm = getResources().getDisplayMetrics();
        WINDOW_WIDTH = dm.widthPixels;// 获取屏幕分辨率宽度
        WINDOW_HEIGHT = dm.heightPixels;

    }




    /**
     * 获取手机信息
     */
    public void getPhoneInfo() {

        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        String brand = android.os.Build.BRAND;// 手机品牌
        String model = android.os.Build.MODEL; // 手机型号
        String systemVersion = android.os.Build.VERSION.RELEASE; // Android版本
        String imei = "";
        String imsi = "";
        String phoneNumber = "";
        try {
            imei = tm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            imsi = tm.getSubscriberId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            phoneNumber = tm.getLine1Number(); // 手机号码
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!TextUtils.isEmpty(phoneNumber)) {
            PostPhone(phoneNumber);//传给服务器
        } else {
//            Log.e("手机号码------------","为空");

        }

    }

    private void PostPhone(String phoneNumber) {
        RequestParams params = new RequestParams(URLS.USER_GET_PHONE);
        params.addBodyParameter("phone", phoneNumber);
        params.addBodyParameter("source", getString(R.string.user_from));
        params.addBodyParameter("app_num", getString(R.string.appNumber));
        org.xutils.x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                GetUserInfoResponse response = gson.fromJson(result, GetUserInfoResponse.class);
                if (response.getCode().equals("0") || response.getCode().equals("202")) {
//                    Log.e("----------",""+"msg---"+response.getMsg());

//                                        Log.e("----------",""+"huoqushouji---"+phoneNumber);
//                    Log.e("----------",""+"huoqushouji---"+result);


                } else {
//                    PostPhone(phoneNumber);
//                    Log.e("----------",""+"huoqushouji");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
            }

        });
    }






    /**
     * 当前实例
     *
     * @return
     */
    public synchronized static BaseApplication getInstance() {
        if (null == mInstance) {
            mInstance = new BaseApplication();
        }
        return mInstance;
    }

    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            System.exit(0);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }


}

package com.wangyuelin.fruit.app;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.BuildConfig;
import com.orhanobut.logger.Logger;

import okhttp3.OkHttpClient;

/**
 * Created by wangyuelin on 2018/1/27.
 */

public class MainApplication extends Application {

    // 单例
    private static MainApplication mApplication = null;


    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        init();
    }

    public static MainApplication getInstance() {
        return mApplication;
    }

    /**
     * 做初始化相关的工作
     */
    private void init(){
        initLogger();
        initNetFrame();

    }

    /**
     * 初始化logger库
     */
    private void initLogger(){
        Logger.addLogAdapter(new AndroidLogAdapter(){
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

    }


    /**
     * 初始化网络请求的框架
     */
    private void initNetFrame(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkGo.getInstance().init(this)                       //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.IF_NONE_CACHE_REQUEST)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3);
    }


}

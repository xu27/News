package com.example.xcxlibrary;

import android.app.Application;


import com.example.xcxlibrary.Util.HttpUtil.OkHttpHelper;
import com.example.xcxlibrary.sql.SqlManager;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化http
        OkHttpClient okHttpClient =null;
        File cache = getExternalFilesDir("cache");
        int cacheSize = 10 * 1024 * 1024;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .callTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .cache(new Cache(cache.getAbsoluteFile(),cacheSize));
        okHttpClient = builder.build();
        OkHttpHelper.setInstance(okHttpClient);

        //初始化数据库
        //SqlManager.getManager(this,"xcx.db",null,1);
    }
}

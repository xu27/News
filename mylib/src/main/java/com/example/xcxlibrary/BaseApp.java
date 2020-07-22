package com.example.xcxlibrary;

import android.app.Application;
import android.util.Log;


import com.example.xcxlibrary.Util.HttpUtil.OkHttpHelper;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BaseApp extends Application {

    private static final String TAG = "BaseApp";

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化http
        if (isInitializedOkHttp()){
            setokhttp();
            Log.d(TAG, "onCreate: okhttp已初始化");
        }
        //初始化数据库
        if (isInitializedGreenDao()){
            setgreenDao();
            Log.d(TAG, "onCreate: GreenDao已初始化");
        }
    }

    protected boolean isInitializedOkHttp(){
        return false;
    }
    protected boolean isInitializedGreenDao(){
        return false;
    }

    protected void setokhttp(){
        OkHttpClient okHttpClient =null;
        File cache = getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .callTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .cache(new Cache(cache.getAbsoluteFile(),cacheSize))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder()
                                .removeHeader("User-Agent")
                                .addHeader(
                                        "User-Agent",
                                        "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
                                .build() ;
                        return chain.proceed(newRequest);
                    }
                });
        okHttpClient = builder.build();
        OkHttpHelper.setInstance(okHttpClient);
    }

    protected void setgreenDao(){
        //根据各自数据库写
    }
}

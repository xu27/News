package com.example.xcxlibrary.Util.HttpUtil;



import com.example.xcxlibrary.baseRequestBuilder.GetRequest;

import okhttp3.OkHttpClient;

public class OkHttpHelper<T> {
    private static volatile OkHttpHelper okHttpHelper;

    private static OkHttpClient mHttpClient;

    private static OkHttpClient init() {
        synchronized (OkHttpHelper.class) {
            if (mHttpClient == null) {
                mHttpClient = new OkHttpClient();
            }
        }
        return mHttpClient;
    }

    public static OkHttpClient getInstance() {
        return mHttpClient == null ? init() : mHttpClient;
    }
    public static void setInstance(OkHttpClient okHttpClient) {
        OkHttpHelper.mHttpClient = okHttpClient;
    }

    public static GetRequest get(){
        return new GetRequest();
    }

    public static void cancel(Object tag){

    }
}

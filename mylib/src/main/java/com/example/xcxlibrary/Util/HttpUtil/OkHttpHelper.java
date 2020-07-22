package com.example.xcxlibrary.Util.HttpUtil;




import okhttp3.OkHttpClient;

public class OkHttpHelper {
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
//////////////////////////////////未完成
}

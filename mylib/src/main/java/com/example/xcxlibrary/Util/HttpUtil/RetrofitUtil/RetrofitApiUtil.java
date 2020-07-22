package com.example.xcxlibrary.Util.HttpUtil.RetrofitUtil;


import com.example.xcxlibrary.Util.HttpUtil.OkHttpHelper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApiUtil {

    //声明Retrofit对象
    private Retrofit mRetrofit;
    //声明RetrofitApiService对象
    private RetrofitService retrofitService;
    //private String BASEURL = "http://127.0.0.1:8080/JavaWeb_war_exploded/";
    private String BASEURL = "http://10.0.2.2:8080/JavaWeb_war_exploded/";
    private volatile static RetrofitApiUtil instance;

    /**
     * 单例模式
     * @return
     */
    public static RetrofitApiUtil getInstance(){
        if (instance == null) {
            synchronized (RetrofitApiUtil.class) {
                if (instance == null) {
                    instance = new RetrofitApiUtil();
                }
            }
        }
        return instance;
    }
    private RetrofitApiUtil(){
        init();
    }

    private void init() {
        mRetrofit=new Retrofit.Builder()
                .baseUrl(BASEURL)
                .client(OkHttpHelper.getInstance())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
    public RetrofitService getRetrofitApiService(){
        retrofitService = mRetrofit.create(RetrofitService.class);
        return retrofitService;
    }
    public Call<ResponseBody> get(){
        return retrofitService.getbody();
    }
}

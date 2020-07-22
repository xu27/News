package com.example.xcxlibrary.baseRequestBuilder;

import android.text.TextUtils;

import com.example.xcxlibrary.Util.HttpUtil.OkHttpHelper;
import com.example.xcxlibrary.callback.HelperCallBack;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;

public class GetRequest extends RequestBuilder {

    public GetRequest url(String url){
        this.url = url;
        return this;
    }

    public GetRequest setParams(Map<String,String> params){
        this.params = params;
        return this;
    }

    public GetRequest tag(Object tag){
        this.tag = tag;
        return this;
    }

    @Override
    public Call enqueue(Callback callback) {
        if (TextUtils.isEmpty(url)){
            throw new IllegalArgumentException("url is null !");
        }
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (tag!=null){
            requestBuilder.tag(tag);
        }
        if (params!=null){
            url = appendParams(url,params);
        }
        if (callback instanceof HelperCallBack) {
            ((HelperCallBack) callback).onStart();
        }
        Request request =requestBuilder.build();
        Call call = OkHttpHelper.getInstance().newCall(request);
        call.enqueue(callback);
        return call;
    }
}

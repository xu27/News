package com.example.xcxlibrary.callback;

import android.os.Handler;
import android.os.Looper;


import com.example.xcxlibrary.parser.BaseParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class HelperCallBack<T> implements Callback {

    private BaseParser<T> parser;
    private static Handler handler= new Handler(Looper.getMainLooper());

    public HelperCallBack(BaseParser<T> parser) {
        this.parser=parser;
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onError(e);
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) {
        final int code =parser.getCode();
        try {
            final T t = parser.parseResponse(response);
            if (response.isSuccessful() & t!=null){
                final T finalT = t;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onSuccessful(code, t);
                    }
                });
            }
        } catch (final IOException e) {
            e.printStackTrace();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onError(e);
                }
            });
        }
    }

    public void onStart(){

    }

    public abstract void onSuccessful(int code,T t);
    public abstract void onError(Exception e);
}

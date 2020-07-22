package com.example.a2020_5_24_byxcx.View.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.a2020_5_24_byxcx.Modle.MyMainModle;
import com.example.a2020_5_24_byxcx.Modle.News_JAVAbean.Base_Bean;
import com.example.a2020_5_24_byxcx.R;
import com.example.a2020_5_24_byxcx.View.ArticleActivity;
import com.example.xcxlibrary.BaseService;
import com.example.xcxlibrary.Util.HttpUtil.RetrofitUtil.RetrofitManager;
import com.example.xcxlibrary.Util.NoticeHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


public class TestService extends BaseService {
    private static final String TAG = "TestService";

    @Override
    public void serviceLogic() {
        super.serviceLogic();
        int x=1+(int)(Math.random()*20);
        RetrofitManager
                .getManager()
                .getNews("BBM54PGAwangning", x, x+1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ResponseBody, Base_Bean>() {
                    @Override
                    public Base_Bean apply(ResponseBody responseBody) throws Exception {
                        Log.d(TAG, "apply: Map");
                        Base_Bean base_bean = null;
                        if (responseBody != null) {
                            String data = responseBody.string();
                            if (data.length() > 9) {
                                data = data.substring(9, data.length() - 1);
                                base_bean = MyMainModle.toJson("BBM54PGAwangning", data);
                            } else {
                                throw new Exception("获取数据失败");
                            }
                            return base_bean;
                        }
                        return null;
                    }
                })
                .subscribe(new Consumer<Base_Bean>() {
                    @Override
                    public void accept(Base_Bean base_bean) throws Exception {
                        Intent intent = new Intent();
                        intent.putExtra("key",base_bean.getItemList().get(0));
                        intent.setAction("location.reportsucc");
                        sendBroadcast(intent);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }
}

package com.example.a2020_5_24_byxcx.Modle;

import android.content.Context;
import android.util.Log;

import com.danikula.videocache.Cache;
import com.example.a2020_5_24_byxcx.Modle.Dao.CacheDBUtils;
import com.example.a2020_5_24_byxcx.Modle.Dao.CacheModle;
import com.example.xcxlibrary.Util.NetUtil;
import com.example.xcxlibrary.bean.Videobaen;
import com.example.a2020_5_24_byxcx.Modle.News_JAVAbean.Base_Bean;
import com.example.a2020_5_24_byxcx.Modle.News_JAVAbean.Bean_item;
import com.example.a2020_5_24_byxcx.My_2020_5_24Byxcx;
import com.example.xcxlibrary.Util.HttpUtil.RetrofitUtil.RetrofitManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MyMainModle implements My_2020_5_24Byxcx.MainModle {
    private static final String TAG = "MyMainModle";

    private CompositeDisposable compositeDisposable;

    public MyMainModle(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    public void addDisposable(Disposable disposable) {
        if (compositeDisposable != null) {
            compositeDisposable.add(disposable);
        }
    }


    @Override
    public <T> void cacheDate(Context context, String type, T bean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                CacheDBUtils utils = new CacheDBUtils(context);
                Base_Bean base_bean = (Base_Bean) bean;
                if (base_bean.getItemList().size() > 1) {
                    List<CacheModle> cacheModles = new ArrayList<>();
                    for (int i = 0; base_bean.getItemList().size() > i; i++) {
                        CacheModle cacheModle = new CacheModle(
                                base_bean.getItemList().get(i).getDocid(),
                                base_bean.getItemList().get(i).getSource(),
                                base_bean.getItemList().get(i).getTitle(),
                                base_bean.getItemList().get(i).getUrl(),
                                base_bean.getItemList().get(i).getDigest(),
                                base_bean.getItemList().get(i).getImgsrc(),
                                base_bean.getItemList().get(i).getPtime(),
                                base_bean.getItemList().get(i).getDocid(),
                                null,
                                type);
                       utils.insertdb(cacheModle);
                    }
                } else {
                    Log.d(TAG, "cacheDate: 数据出错");
                }
                utils.close();
            }
        }).start();
    }

    @Override
    public void getcacheDate(Context context, String type, HttpCallBack callBack) {
        Log.d(TAG, "asdasdasad: 2");
        CacheDBUtils utils = new CacheDBUtils(context);
        List<CacheModle> cacheModles = utils.queryCacheModleByQueryBuilder_toType(type);
        Log.d(TAG, "queryCacheModleByQueryBuilder_toType:" + cacheModles.size());
        if (cacheModles.size() >= 10) {
            Base_Bean base_bean = new Base_Bean();
            base_bean.setType(type);
            List<Bean_item> bean_itemList = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                Bean_item item = new Bean_item(
                        cacheModles.get(cacheModles.size()-i).getSource()
                        , cacheModles.get(cacheModles.size()-i).getTitle()
                        , cacheModles.get(cacheModles.size()-i).getUrl()
                        , cacheModles.get(cacheModles.size()-i).getDigest()
                        , cacheModles.get(cacheModles.size()-i).getImgsrc()
                        , cacheModles.get(cacheModles.size()-i).getPtime()
                        , cacheModles.get(cacheModles.size()-i).getDocid()
                );
                bean_itemList.add(item);
            }
            base_bean.setItemList(bean_itemList);
            callBack.onSuccess(base_bean);
        }else {
            callBack.onFilure("获取失败");
        }
    }

    @Override
    public void getHttpData(String type, int start, HttpCallBack callBack, Context context) {
        Log.d(TAG, "getHttpData: start");
        addDisposable(RetrofitManager
                .getManager()
                .getNews(type, start, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ResponseBody, Base_Bean>() {
                    @Override
                    public Base_Bean apply(ResponseBody responseBody) throws Exception {
                        //如果网络正常
                        Log.d(TAG, "asdasdasad: 1");
                        Base_Bean base_bean = null;
                        if (responseBody != null) {
                            String data = responseBody.string();
                            if (data.length() > 9) {
                                data = data.substring(9, data.length() - 1);
                                base_bean = toJson(type, data);
                            } else {
                                throw new Exception("获取数据失败");
                            }
                            cacheDate(context, type, base_bean);
                            return base_bean;
                        }
                        return null;
                    }
                })
                .subscribe(new Consumer<Base_Bean>() {
                    @Override
                    public void accept(Base_Bean base_bean) throws Exception {
                        callBack.onSuccess(base_bean);
                        compositeDisposable.clear();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBack.onFilure(throwable.getMessage());
                    }
                }));
    }

    @Override
    public void getHttpVideo(int start, HttpCallBack callBack, Context context) {
        Log.d(TAG, "getHttpVideo: ");
        addDisposable(RetrofitManager.getManager()
                .getvd(start)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Videobaen>() {
                    @Override
                    public void accept(Videobaen videobaen) throws Exception {
                        callBack.onSuccess(videobaen);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBack.onFilure(throwable.getMessage());
                    }
                }));

    }


    public static Base_Bean toJson(String type, String data) throws JSONException {
        Base_Bean base_bean = new Base_Bean();
        base_bean.setType(type);
        JSONObject jsonObject = new JSONObject(data);
        JSONArray jsonArray = jsonObject.getJSONArray(type);
        List<Bean_item> bean_itemList = new ArrayList<>();
        Log.d(TAG, "toJson: 一共" + jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            Bean_item item = new Bean_item();
            JSONObject object = jsonArray.getJSONObject(i);
            item.setDigest(object.getString("digest"));
            item.setImgsrc(object.getString("imgsrc"));
            item.setSource(object.getString("source"));
            item.setTitle(object.getString("title"));
            item.setUrl(object.getString("url"));
            item.setPtime(object.getString("ptime"));
            item.setDocid(object.getString("docid"));
            bean_itemList.add(item);
        }
        base_bean.setItemList(bean_itemList);
        return base_bean;
    }
}

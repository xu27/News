package com.example.a2020_5_24_byxcx.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.a2020_5_24_byxcx.R;
import com.example.a2020_5_24_byxcx.View.custom.MyPbView;
import com.example.xcxlibrary.BaseActivity;
import com.example.xcxlibrary.Util.HttpUtil.RetrofitUtil.RetrofitManager;
import com.example.xcxlibrary.Util.SharePrenceUtil;
import com.example.xcxlibrary.bean.ADurl;

public class AdActivity extends BaseActivity {

    private static final String TAG = "AdActivity";

    private MyPbView pbView;
    private ImageView imageView;
    
    private static String IMAGE = "image";
    private static String LAST_TIME = "last_time";
    private static String TIME_OUT = "last_time";

    private Animator a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //开启全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ad);
        init();
        isRequestData();
    }

    private void init() {
        pbView = findViewById(R.id.ad_time);
        imageView = findViewById(R.id.ad_img);
        pbView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a.pause();
                Log.d(TAG, "onClick:  点击启动");
                toMain();
            }
        });
    }

    private void isRequestData() {
        String url = SharePrenceUtil.getString(this,IMAGE);
        if (TextUtils.isEmpty(url)){
            Log.d(TAG, "isRequestData: ok");
            //请求数据
            requestData();
        }else {
            Log.d(TAG, "isRequestData: no");
            //取出数据
            long time_out = SharePrenceUtil.getLong(this, TIME_OUT);
            long now = System.currentTimeMillis();
            long last = SharePrenceUtil.getLong(this, LAST_TIME);
            if ((now-last)>time_out*60*10){
                requestData();
            }else {
                setimage(url);
            }
        }
    }

    private void requestData() {
        addDisposable(RetrofitManager.getManager()
                .getad()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ADurl>() {
                    @Override
                    public void accept(ADurl aDurl) throws Exception {
                        if (TextUtils.isEmpty(aDurl.getError())){
                            //检查数据源是否有错误
                            for (int i = 0 ; i <aDurl.getAds().get(0).getRes_url().size();i++) {
                                if (TextUtils.isEmpty(aDurl.getAds().get(0).getRes_url().get(i))) {
                                } else {
                                    String url = aDurl.getAds().get(0).getRes_url().get(i);
                                    SharePrenceUtil.saveString(AdActivity.this,IMAGE,url);
                                    SharePrenceUtil.saveLong(AdActivity.this, TIME_OUT, aDurl.getNext_req());
                                    SharePrenceUtil.saveLong(AdActivity.this, LAST_TIME, System.currentTimeMillis());
                                    setimage(url);
                                    break;
                                }
                            }
                        }else {
                            //如果有 开始动画
                            Log.d(TAG, "accept: api错误开始动画");
                            startAnimator();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //如果在请求错误
                        Log.d(TAG, "accept: 请求api数据错误开始动画");
                        startAnimator();
                    }
                }));
    }

    private void setimage(String url) {
        Glide.with(this)
                .load(url)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.d(TAG, "onLoadFailed: 加载失败启动动画");
                        startAnimator();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d(TAG, "onResourceReady: 加载就绪启动动画");
                        startAnimator();
                        return false;
                    }
                })
                .into(imageView);
    }

    public void toMain(){
        Log.d(TAG, "toMain: ");
        Intent intent = new Intent();
        intent.setClass(AdActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void startAnimator(){
        pbView.setVisibility(View.VISIBLE);
        a = ObjectAnimator.ofInt(pbView,"progress",0,100).setDuration(3000);
        a.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                toMain();
            }
        });
        a.start();
       /* pbView.startAnimator(pbView, 0, 100, 3000,
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation, boolean isReverse) {
                        a= animation;
                    }

                    @Override
                    public void onAnimationEnd(Animator animation, boolean isReverse) {
                        Log.d(TAG, "onAnimationEnd: 计时启动");
                        toMain();
                    }
                });*/
    }
}

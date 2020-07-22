package com.example.a2020_5_24_byxcx;

import android.content.Context;
import android.util.Log;

import com.example.a2020_5_24_byxcx.Modle.Events.DataType;
import com.example.a2020_5_24_byxcx.Modle.MyMainModle;
import com.example.a2020_5_24_byxcx.Modle.News_JAVAbean.Base_Bean;
import com.example.xcxlibrary.Util.NetUtil;
import com.example.xcxlibrary.bean.Videobaen;

import io.reactivex.disposables.CompositeDisposable;

public class MvpPresenter implements My_2020_5_24Byxcx.PresenterMain {
    private static final String TAG = "MvpPresenter";

    private My_2020_5_24Byxcx.MainView main;
    private Context context;
    private MyMainModle modle;

    public MvpPresenter() {
    }

    public MvpPresenter(Context context, CompositeDisposable compositeDisposable) {
        this.context = context;
        modle = new MyMainModle(compositeDisposable);
    }

    @Override
    public void attachView(My_2020_5_24Byxcx.MainView mainView) {
        Log.d(TAG, "attachView: 获取View");
        this.main = mainView;
    }

    @Override
    public void deachView() {
        main = null;
    }

    @Override
    public void updata(String type, int start) {
        if(NetUtil.isNetworkAvalible(context)) {
            modle.getHttpData(type, start, new My_2020_5_24Byxcx.MainModle.HttpCallBack() {
                @Override
                public void onSuccess(Object baseBean) {
                    if (isAttachView()) {
                        Base_Bean bean = (Base_Bean) baseBean;
                        bean.setStart(start);
                        main.sendData(bean);
                        modle.cacheDate(context, type, bean);
                        Log.d(TAG, "onSuccess: 一共：" + bean.getItemList().size());
                    }
                }

                @Override
                public void onFilure(String msg) {
                    if (isAttachView()) {
                        main.showMsg(msg);
                        main.senderr(DataType.EER);
                    }
                }
            }, context);
        }else {
            modle.getcacheDate(context, type, new My_2020_5_24Byxcx.MainModle.HttpCallBack() {
                @Override
                public void onSuccess(Object baseBean) {
                    if (isAttachView()) {
                        Base_Bean bean = (Base_Bean) baseBean;
                        bean.setStart(start);
                        main.sendData(bean);
                        modle.cacheDate(context, type, bean);
                        Log.d(TAG, "onSuccess: 一共：" + bean.getItemList().size());
                    }
                }

                @Override
                public void onFilure(String msg) {
                    if (isAttachView()) {
                        main.showMsg(msg);
                        main.senderr(DataType.EER);
                    }
                }
            });
        }
    }

    @Override
    public void upVideo(int start) {
        modle.getHttpVideo(start, new My_2020_5_24Byxcx.MainModle.HttpCallBack() {
            @Override
            public void onSuccess(Object bean) {
                main.sendVideo((Videobaen) bean,start+10);
            }

            @Override
            public void onFilure(String msg) {
                Log.d(TAG, "onFilure: "+msg);
                main.senderr(DataType.EER_VIDEO);
            }
        },context);
    }

    /**
     * 判断 view是否为空
     *
     * @return
     */
    public boolean isAttachView() {
        return main != null;
    }

    /**
     * 返回目标view
     *
     * @return
     */
    public My_2020_5_24Byxcx.MainView getMvpView() {
        return main;
    }

    /**
     * 检查view和presenter是否连接
     */
    public void checkViewAttach() {
        if (!isAttachView()) {
            throw new MvpViewNotAttachedException();
        }
    }

    /**
     * 自定义异常
     */
    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("请求数据前请先调用 attachView(MvpView) 方法与View建立连接");
        }
    }
}

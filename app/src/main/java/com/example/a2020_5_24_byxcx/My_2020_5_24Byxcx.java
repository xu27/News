package com.example.a2020_5_24_byxcx;

import android.content.Context;

import com.example.xcxlibrary.bean.Videobaen;
import com.example.a2020_5_24_byxcx.Modle.News_JAVAbean.Base_Bean;

public interface My_2020_5_24Byxcx {
    interface MainView{
        //显示信息
        public void showMsg(String msg);

        //发送数据
        void sendData(Base_Bean base_bean);

        //发送视频数据
        void sendVideo(Videobaen bean,int start);

        //发送错误
        void senderr(int type);
    }

    interface MainModle{

        public interface HttpCallBack<T> {
            //成功返回
            void onSuccess( T Bean);
            //失败返回
            void onFilure(String msg);
        }

        public <T> void cacheDate(Context context,String type,T bean);

        public void getcacheDate(Context context,String type,HttpCallBack callBack);

        //重网络获取数据
        public void getHttpData(String type,int start,HttpCallBack callBack,Context context);

        //重网络获取数据
        public void getHttpVideo(int start,HttpCallBack callBack,Context context);

    }

    interface PresenterMain{

        public void attachView(MainView main);

        public void deachView();

        //更新数据
        public void updata(String type,int start);

        //更新视频
        public void upVideo(int start);

    }


}

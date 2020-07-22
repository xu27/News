package com.example.xcxlibrary.Util.HttpUtil.RetrofitUtil;



import com.example.xcxlibrary.bean.ADurl;
import com.example.xcxlibrary.bean.Javabean;
import com.example.xcxlibrary.bean.MyComment;
import com.example.xcxlibrary.bean.Videobaen;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class RetrofitManager {
    private RetrofitService mRetrofit;
    public static volatile RetrofitManager manager;

    public RetrofitManager(){
        mRetrofit= RetrofitApiUtil.getInstance().getRetrofitApiService();
    }

    public static RetrofitManager getManager(){
        if (manager==null){
            synchronized (RetrofitManager.class){
                if ((manager==null)){
                    manager=new RetrofitManager();
                }
            }
        }
        return manager;
    }

    public Call<ResponseBody> getbody(){
        return mRetrofit.getbody();
    }

    public Call<ResponseBody> login(String name,String password){
        return mRetrofit.loguser(name,password);
    }
    public Call<ResponseBody> postFile(MultipartBody.Part part){
        return mRetrofit.postFile(part);
    }

    /*public Call<ExpandableBean> getExpandableBean(){
        return mRetrofit.getExpandableBean();
    }*/

    public Call<ResponseBody> getimg(){
        return  mRetrofit.getimg();
    }

    public Observable<ResponseBody> gethtml(){return mRetrofit.getHtml();}

    public Observable<Javabean> getjh(String type,String key){return mRetrofit.getCall(type, key);}

    public Observable<ResponseBody> getNews(String type,int start,int end){return mRetrofit.getWYnews(type,start, end);}

    public Observable<MyComment> getComment(String id ,int start){ return  mRetrofit.getWYcomments(id,start);}

    public Observable<ADurl> getad(){return mRetrofit.getadurl();}

    public Observable<Videobaen> getvd(int start){return mRetrofit.getVideolist(start);}
}

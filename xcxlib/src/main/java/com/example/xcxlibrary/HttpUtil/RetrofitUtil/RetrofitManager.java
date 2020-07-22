package com.example.xcxlibrary.Util.HttpUtil.RetrofitUtil;



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
}

package com.example.xcxlibrary.Util.HttpUtil.RetrofitUtil;


import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitService {

    /*
    HelloWorld
    JsonServlet
     */
    @GET("JsonServlet")
    Call<ResponseBody> getbody();

    @GET("test.png")
    Call<ResponseBody> getimg();

    /*@GET("http://www.imooc.com/api/expandablelistview")
    Call<ExpandableBean> getExpandableBean();*/

    @FormUrlEncoded
    @POST("LoginServlet")
    Call<ResponseBody> loguser(@Field("username") String username, @Field("password") String password);

    @Multipart
    @POST("FileServlet")
    Call<ResponseBody> postFile(@Part MultipartBody.Part part);
}
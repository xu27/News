package com.example.xcxlibrary.Util.HttpUtil.RetrofitUtil;


import com.example.xcxlibrary.bean.ADurl;
import com.example.xcxlibrary.bean.Javabean;
import com.example.xcxlibrary.bean.MyComment;
import com.example.xcxlibrary.bean.Videobaen;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @GET("https://www.jianshu.com/p/9e7c6a7037b0")
    Observable<ResponseBody> getHtml();

    @FormUrlEncoded
    @POST("LoginServlet")
    Call<ResponseBody> loguser(@Field("username") String username, @Field("password") String password);

    @Multipart
    @POST("FileServlet")
    Call<ResponseBody> postFile(@Part MultipartBody.Part part);

    @GET("http://v.juhe.cn/toutiao/index")
    Observable<Javabean> getCall(@Query("type")String type,@Query("key")String key);

    @GET("https://3g.163.com/touch/reconstruct/article/list/{type}/{start}-{end}.html")
    Observable<ResponseBody> getWYnews(@Path("type")String type, @Path("start")int start,@Path("end") int end);

    @GET("http://comment.api.163.com/api/json/post/list/new/hot/news3_bbs/{id}/{start}/10/10/2/2")
    Observable<MyComment> getWYcomments(@Path("id")String id, @Path("start")int start);

    @GET("http://g1.163.com/madr?app=7A16FBB6&platform=android&category=STARTUP&location=1&timestamp=1462779408364&uid=OaBKRDb%2B9FBz%2FXnwAuMBWF38KIbARZdnRLDJ6Kkt9ZMAI3VEJ0RIR9SBSPvaUWjrFtfw1N%2BgxquT0B2pjMN5zsxz13RwOIZQqXxgjCY8cfS8XlZuu2bJj%2FoHqOuDmccGyNEtV%2FX%2FnBofofdcXyudJDmBnAUeMBtnIzHPha2wl%2FQnUPI4%2FNuAdXkYqX028puyLDhnigFtrX1oiC2F7UUuWhDLo0%2BE0gUyeyslVNqLqJCLQ0VeayQa%2BgbsGetk8JHQ")
    Observable<ADurl> getadurl();

    @GET("https://c.3g.163.com/nc/video/home/{start}-10.html")
    Observable<Videobaen> getVideolist(@Path("start") int start);
}
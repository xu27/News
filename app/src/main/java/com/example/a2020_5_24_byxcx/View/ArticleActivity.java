package com.example.a2020_5_24_byxcx.View;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.a2020_5_24_byxcx.Base.LoginDialog;
import com.example.a2020_5_24_byxcx.Modle.Adapter.CommentAdapter;
import com.example.a2020_5_24_byxcx.Modle.Dao.CacheDBUtils;
import com.example.a2020_5_24_byxcx.Modle.Dao.CacheModle;
import com.example.a2020_5_24_byxcx.Modle.Dao.DaoManager;
import com.example.a2020_5_24_byxcx.Modle.Dao.NewsDBUtils;
import com.example.a2020_5_24_byxcx.Modle.Dao.NewsModle;
import com.example.a2020_5_24_byxcx.R;
import com.example.xcxlibrary.BaseActivity;
import com.example.xcxlibrary.Util.HttpUtil.RetrofitUtil.RetrofitManager;
import com.example.xcxlibrary.Util.NetUtil;
import com.example.xcxlibrary.Util.SharePrenceUtil;
import com.example.xcxlibrary.bean.MyComment;
import com.google.android.material.snackbar.Snackbar;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class ArticleActivity extends BaseActivity {

    private static final String TAG = "ArticleActivity";
    //private static final String APP_ID = "222222"; //腾讯qq分享获取的APPID
    @BindView(R.id.article_toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.article_image)
    protected ImageView titleImg;
    @BindView(R.id.article_title)
    protected TextView viewTitle;
    @BindView(R.id.article_source_time)
    protected TextView viewSource_Time;
    @BindView(R.id.article_nestedScrollView)
    protected NestedScrollView scrollView;
    @BindView(R.id.article_Leaving_message)
    protected LinearLayout leaving_message;
    @BindView(R.id.article_content)
    protected LinearLayout article_content;
    @BindView(R.id.article_comment_show)
    protected RecyclerView comment_show;
    @BindView(R.id.i1)
    protected ImageView i1;
    @BindView(R.id.i2)
    protected ImageView i2;
    @BindView(R.id.i3)
    protected ImageView i3;
    private String url, title, source, imgUrl, source_time, id;
    //private Tencent mTencent = null;
    private ClipboardManager cm;
    private ClipData mClipData;
    private Unbinder mUnbinder = null;
    private NewsDBUtils newsDBUtils;
    private CommentAdapter commentAdapter;
    private String html;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        mUnbinder = ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        source = getIntent().getStringExtra("source");
        imgUrl = getIntent().getStringExtra("img");
        title = getIntent().getStringExtra("title");
        id = getIntent().getStringExtra("id");
        Log.d(TAG, "onCreate: " + url);
        setWindow();
        initView();
        setListener();
        getData();
    }

    private void setListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //分享
                /*cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                mClipData = ClipData.newPlainText("Label", url);
                cm.setPrimaryClip(mClipData);
                showMsg("以复制到剪切版");*/
                //应用未在qq注册就不写了
                /*final Bundle params = new Bundle();
                        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
                        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
                        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "点击查看");
                        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgUrl);
                        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, getPackageName());
                        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                        //隐藏分享到qq空间
                        //params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
                        doShareToQQ(params);*/
                //action分享
                //ACTION_SEND会自动显示能响应的应用
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                //放入数据
                intent.putExtra(Intent.EXTRA_TEXT,
                        "标题：\n"+title+ "\n内容："+url);
                //发送
                startActivity(intent);
            }
        });
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //收藏
                if (SharePrenceUtil.getInt(ArticleActivity.this,"login") == 1) {
                    if (newsDBUtils.queryNewsModleById(id) != null) {
                        if (newsDBUtils.deleteNewsModle(new NewsModle(id, url, imgUrl, title, source))) {
                            i2.setImageResource(R.drawable.ic_favor_1);
                        } else {
                            showMsg("取消收藏失败");
                        }
                    } else {
                        if (newsDBUtils.insertNews(new NewsModle(id, url, imgUrl, title, source))) {
                            i2.setImageResource(R.drawable.ic_favor_fill);
                        } else {
                            showMsg("收藏失败");
                        }
                    }
                }else {
                    //请求登陆
                    new LoginDialog(ArticleActivity.this).show();
                }
            }
        });
        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //查看评论
                Log.d(TAG, "onClick: ggggg");
                scrollView.scrollTo(0,leaving_message.getTop());
            }
        });
    }

    private void initView() {
        RequestBuilder<Drawable> builder = Glide.with(this)
                //图片url
                .load(imgUrl)
                //渐变动画
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(500));
        RequestOptions options = new RequestOptions(); // 创建glide的请求选项
        // 在图像视图上展示网络图片。apply方法表示启用指定的请求选项
        builder.apply(options).into(titleImg);
        toolbar.setTitle(source);
        viewTitle.setText(title);
        //腾讯初始化
        //mTencent = Tencent.createInstance(APP_ID, ArticleActivity.this.getApplicationContext());
        //
        newsDBUtils = new NewsDBUtils(this);
        if (newsDBUtils.queryNewsModleById(id) != null)
            i2.setImageResource(R.drawable.ic_favor_fill);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        newsDBUtils.mManager.closeConnection();
    }

    private void getData() {
        Observable.create(new ObservableOnSubscribe<HtmlData>() {
            @Override
            public void subscribe(ObservableEmitter<HtmlData> emitter) throws Exception {
                Document doc = null;
                CacheDBUtils utils = new CacheDBUtils(ArticleActivity.this);
                List<CacheModle> cacheModleList = utils.queryCacheModleByQueryBuilder_toTitle(title);
                if (cacheModleList.size()>0){
                    Log.d(TAG, "subscribe: 12213123213");
                    html = cacheModleList.get(0).getHtml();
                    if (html!=null){
                        Log.d(TAG, "subscribe: 有存储数据");
                        doc = Jsoup.parse(html);
                    }else {
                        Log.d(TAG, "subscribe: 存储");
                        doc = Jsoup.connect(geturl()).get();
                        CacheModle cacheModle = cacheModleList.get(0);
                        cacheModle.setHtml(doc.html());
                        utils.insertdb(cacheModle);

                    }
                    utils.close();
                }else {
                    Log.d(TAG, "subscribe: 456447654");
                    doc = Jsoup.connect(geturl()).get();
                }
                Elements head = doc.getElementsByClass("head");
                //考虑到稳定问题，由intent传入
                //title = head.select("h1[class=title]").text();
                source_time = head.select("div[class=info]").text();
                Elements content = doc.getElementsByClass("content").select("div[class=page js-page on]").select("div,p");
                for (Element element : content) {
                    HtmlData htmlData = new HtmlData();
                    htmlData.text = element.text();
                    htmlData.imgurl = element.select("div[class=photo]").select("a").attr("href");
                    emitter.onNext(htmlData);
                }
                emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HtmlData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(HtmlData data) {
                        if (data.text.length() > 2) {
                            Log.d(TAG, "onNext: addtext" + data.imgurl);
                            addTextView(data.text);
                            if (data.imgurl.length() > 5) {
                                addImageView(data.imgurl);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                        Toast.makeText(ArticleActivity.this, "啊哦！加载失败了，请检查网络", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        viewSource_Time.setText(source_time);
                        if(NetUtil.isNetworkAvalible(ArticleActivity.this)) {
                            getComments();
                        }else{

                        }
                    }
                });
    }

    private String geturl() {
        //某些未知原因下数据源可能是null&""
        if(url.length()<4){
            return "https://3g.163.com/news/article/"+id+".html";
        }else {
            return url;
        }
    }

    /**
     * 设置状态栏
     */
    private void setWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(R.color.no_color));
        }
    }

    private void addTextView(String text) {
        TextView textView = (TextView) LayoutInflater.from(ArticleActivity.this).inflate(R.layout.article_content_item, article_content, false);
        textView.setText("\u3000\u3000" + text);
        textView.setAlpha(.1f);
        article_content.addView(textView);
        textView.animate().alpha(1).start();
    }

    private void addImageView(String url) {
        ImageView imageView = (ImageView) LayoutInflater.from(ArticleActivity.this).inflate(R.layout.article_content_item_img, article_content, false);
        Glide.with(this)
                .load(url)
                .into(imageView);
        article_content.addView(imageView);
    }

    private void showMsg(String msg) {
        Snackbar snackbar = Snackbar.make(article_content, msg, 3000);
        snackbar.setTextColor(getResources().getColor(R.color.white_color));
        snackbar.setBackgroundTint(getResources().getColor(R.color.red_color));
        snackbar.setActionTextColor(getResources().getColor(R.color.white_color));
        snackbar.setAction("关闭", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (snackbar.isShown()) {
                    snackbar.dismiss();
                }
            }
        });
        snackbar.show();
    }

    private void getComments(){
        Log.d(TAG, "getComments: ");
        addDisposable(RetrofitManager.getManager()
                .getComment(id,0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MyComment>() {
                    @Override
                    public void accept(MyComment myComment) throws Exception {
                        Log.d(TAG, "accept: "+myComment.getHotPosts().size());
                        commentAdapter = new CommentAdapter(myComment);
                        comment_show.setLayoutManager(new LinearLayoutManager(ArticleActivity.this, LinearLayoutManager.VERTICAL, false));
                        comment_show.setAdapter(commentAdapter);
                        commentAdapter.setFooteronClickListener(new CommentAdapter.FooteronClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.d(TAG, "onClick: "+(int)view.getTag());
                                switch ((int)view.getTag()){
                                    case 200:
                                        //footer
                                        Intent intent =new Intent(ArticleActivity.this,CommentActivity.class);
                                        intent.putExtra("id",id);
                                        startActivity(intent);
                                        break;
                                    case 201:
                                        //点赞
                                        ConstraintLayout layout = view.findViewById(R.id.comment_fabulousLayout);
                                        ImageView img = layout.findViewById(R.id.comment_fabulous_img);
                                       /* TextView textView = layout.findViewById(R.id.comment_fabulous);
                                        textView.setText(Integer.parseInt((String) textView.getText())+1);*/
                                        img.setImageResource(R.drawable.ic_appreciate_fill);
                                        view.setTag(222);
                                        break;
                                    case 222:
                                        ConstraintLayout layout2 = view.findViewById(R.id.comment_fabulousLayout);
                                        ImageView img2 = layout2.findViewById(R.id.comment_fabulous_img);
                                        /*TextView textView2 = layout2.findViewById(R.id.comment_fabulous);
                                        textView2.setText(Integer.parseInt((String) textView2.getText())-1);*/
                                        img2.setImageResource(R.drawable.ic_appreciate);
                                        view.setTag(201);
                                        break;
                                }
                            }
                        });
                        if (commentAdapter.getItemCount() != 1) {
                            leaving_message.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: ",throwable );
                        Toast.makeText(ArticleActivity.this,"拉取评论失败:"+throwable.getMessage(),Toast.LENGTH_SHORT);
                    }
                }));
    }

    //qq分享方法，暂时不用
    /*private void doShareToQQ(final Bundle params) {
        // QQ分享要在主线程做
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTencent.shareToQQ(ArticleActivity.this, params, new IUiListener() {
                    @Override
                    public void onComplete(Object o) {

                    }

                    @Override
                    public void onError(UiError uiError) {

                    }

                    @Override
                    public void onCancel() {
                        showMsg("");
                    }
                });
            }
        });
    }*/

    private class HtmlData {
        public String text = null;
        public String imgurl = null;
    }
}

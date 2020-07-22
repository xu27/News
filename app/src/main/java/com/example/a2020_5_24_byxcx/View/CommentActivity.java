package com.example.a2020_5_24_byxcx.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2020_5_24_byxcx.Modle.Adapter.CommentAdapter;
import com.example.a2020_5_24_byxcx.Modle.Adapter.CommentAdapter2;
import com.example.a2020_5_24_byxcx.Modle.listenter.EndlessRecyclerOnScrollListener;
import com.example.a2020_5_24_byxcx.R;
import com.example.xcxlibrary.BaseActivity;
import com.example.xcxlibrary.Util.HttpUtil.RetrofitUtil.RetrofitManager;
import com.example.xcxlibrary.bean.MyComment;

public class CommentActivity extends BaseActivity {
    private static final String TAG = "CommentActivity";

    @BindView(R.id.comment_toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.comment_lists)
    protected RecyclerView recyclerView;

    private Unbinder mUnbinder = null;
    private CommentAdapter2 commentAdapter;
    private String id;
    private int start = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        setWindow();
        mUnbinder = ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        initview();
        setListener();
        Log.d(TAG, "onCreate: " + id);
        if (id != null) {
            getComments();
        }
        ;
    }

    private void initview() {
        commentAdapter = new CommentAdapter2();
        recyclerView.setLayoutManager(new LinearLayoutManager(CommentActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(commentAdapter);

    }

    private void setListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        commentAdapter.setFooteronClickListener(new CommentAdapter2.FooteronClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: " + (int) view.getTag());
                switch ((int) view.getTag()) {
                    case 200:
                        //footer
                        break;
                    case 201:
                        //点赞
                        ImageView img = view.findViewById(R.id.comment_fabulous_img);
                        img.setImageResource(R.drawable.ic_appreciate_fill);
                        view.setTag(222);
                        break;
                    case 222:
                        ImageView img2 = view.findViewById(R.id.comment_fabulous_img);
                        img2.setImageResource(R.drawable.ic_appreciate);
                        view.setTag(201);
                        break;
                }
            }
        });
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                Log.d(TAG, "onLoadMore: 设置footer");
                getComments();
            }
        });
    }

    private void getComments() {
        Log.d(TAG, "getComments: ");
        addDisposable(RetrofitManager.getManager()
                .getComment(id, start)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MyComment>() {
                    @Override
                    public void accept(MyComment myComment) throws Exception {
                        Log.d(TAG, "accept: " + myComment.getHotPosts().size());
                        if (myComment.getHotPosts().size() == 0){
                            Log.d(TAG, "accept: 没有数据");
                            throw new Exception("没有更多数据了");
                        }else {
                            commentAdapter.setComment(myComment);
                            start += 10;
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: ", throwable);
                        Toast.makeText(CommentActivity.this, "拉取评论失败:" + throwable.getMessage(), Toast.LENGTH_SHORT);
                        commentAdapter.setLoadState(commentAdapter.LOADING_END);
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    private void setWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(R.color.no_color));
        }
    }
}

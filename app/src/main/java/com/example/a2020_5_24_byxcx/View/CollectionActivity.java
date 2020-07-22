package com.example.a2020_5_24_byxcx.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.a2020_5_24_byxcx.Modle.Adapter.CollectionAdapter;
import com.example.a2020_5_24_byxcx.Modle.Adapter.MyRecyclerViewDivider;
import com.example.a2020_5_24_byxcx.Modle.Dao.NewsDBUtils;
import com.example.a2020_5_24_byxcx.Modle.Dao.NewsModle;
import com.example.a2020_5_24_byxcx.R;
import com.example.xcxlibrary.BaseActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CollectionActivity extends BaseActivity {
    private static final String TAG = "CollectionActivity";

    private NewsDBUtils dbUtils;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private CollectionAdapter adapter;

    private List<NewsModle> newsModles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        setWindow();
        init();
        setListener();
    }

    private void init() {
        dbUtils = new NewsDBUtils(this);
        recyclerView = findViewById(R.id.collection_list);
        toolbar = findViewById(R.id.collection_toolbar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new MyRecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(this, R.color.no_color)));
        addDisposable(Observable.create(new ObservableOnSubscribe<List<NewsModle>>() {
            @Override
            public void subscribe(ObservableEmitter<List<NewsModle>> emitter) throws Exception {
                emitter.onNext(dbUtils.queryAllNewsModle());
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<NewsModle>>() {
                    @Override
                    public void accept(List<NewsModle> newsModleList) throws Exception {
                        newsModles = newsModleList;
                        adapter = new CollectionAdapter(newsModles);
                        recyclerView.setAdapter(adapter);
                        adapter.setListener(new CollectionAdapter.MyOnClickListener() {
                            @Override
                            public void click(View view, int i) {
                                Log.d(TAG, "click: ");
                                switch (view.getId()) {
                                    case R.id.collection_title:
                                        Log.d(TAG, "click: " + "点击跳转");
                                        Intent intent = new Intent(CollectionActivity.this, ArticleActivity.class);
                                        intent.putExtra("url", newsModles.get(i).getUrl());
                                        intent.putExtra("source", newsModles.get(i).getSource());
                                        intent.putExtra("img", newsModles.get(i).getImg());
                                        intent.putExtra("title", newsModles.get(i).getTitle());
                                        intent.putExtra("id", newsModles.get(i).getId());
                                        startActivity(intent);
                                        break;
                                    case R.id.collection_img:
                                        Log.d(TAG, "click: " + "点击显示更多");
                                        break;
                                }
                            }
                        });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: ", throwable);
                    }
                }));
    }

    private void setListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        dbUtils.mManager.closeConnection();
        super.onDestroy();
    }

    private void setWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(R.color.red_color));
        }
    }
}

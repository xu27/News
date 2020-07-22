package com.example.a2020_5_24_byxcx.View;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2020_5_24_byxcx.Base.LoginDialog;
import com.example.a2020_5_24_byxcx.View.custom.NoScrollViewPager;
import com.example.a2020_5_24_byxcx.View.service.TestService;
import com.example.xcxlibrary.Util.SharePrenceUtil;
import com.example.xcxlibrary.bean.Videobaen;
import com.example.a2020_5_24_byxcx.Modle.Events.DataMsg;
import com.example.a2020_5_24_byxcx.Modle.Events.DataType;
import com.example.a2020_5_24_byxcx.Modle.News_JAVAbean.Base_Bean;
import com.example.a2020_5_24_byxcx.MvpPresenter;
import com.example.a2020_5_24_byxcx.My_2020_5_24Byxcx;
import com.example.a2020_5_24_byxcx.R;
import com.example.a2020_5_24_byxcx.View.Fragments.MainFragmentAdapter;
import com.example.xcxlibrary.BaseActivity;
import com.example.xcxlibrary.Util.EventBusUtils;
import com.google.android.material.tabs.TabLayout;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends BaseActivity implements My_2020_5_24Byxcx.MainView {

    private static final String TAG = "MainActivity";
    private Unbinder mUnbinder = null;
    public MvpPresenter presenter;
    private MainFragmentAdapter fragmentAdapter;

    private MsgReceiver msgReceiver = new MsgReceiver();

    @BindView(R.id.main_tab)
    protected TabLayout tabLayout;
    @BindView(R.id.main_ViewPager)
    protected NoScrollViewPager viewPager;
    @BindView(R.id.main_toolbar)
    protected Toolbar main_toobar;

    private String nowType;

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWindow();
        mUnbinder = ButterKnife.bind(this);
        //初始化控制者
        presenter = new MvpPresenter(this, compositeDisposable);
        presenter.attachView(MainActivity.this);
        //设置tablayout
        addTabitem();
        //设置fragment
        setFragment();
        settoolbar();
        islogin();
        register();
        setService();
    }

    private void setService() {
        TestService.GetInstance().start(this, TestService.class);
    }

    private void register() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BOOT_COMPLETED);
        intentFilter.addAction(Intent.ACTION_SHUTDOWN);
        //亮屏
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        //息屏
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction("location.reportsucc");
        this.registerReceiver(msgReceiver, intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册EventBus
        EventBusUtils.register(this);
        Log.d(TAG, "onResume: ");
        GSYVideoManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        presenter.deachView();
        GSYVideoManager.releaseAllVideos();
        EventBusUtils.unregister(this);
        if (msgReceiver != null) {
            unregisterReceiver(msgReceiver);
        }
    }

    @Subscribe
    public void onData_Msg(DataMsg event) {
        switch (event.getCode()) {
            case DataType.GET_DROP_DOWN:
                getData(event.getType(), 0);
                break;
            case DataType.GET_PULL_UP:
                //上拉刷新 刷新10条 从start开始
                getData(event.getType(), event.getStart() + 10);
                break;
            case DataType.GET_DROP_DOWN_VIDEO:
                Log.d(TAG, "onData_Msg: GET_DROP_DOWN_VIDEO");
                presenter.upVideo(0);
                break;
            case DataType.GET_PULL_UP_VIDEO:
                Log.d(TAG, "onData_Msg: GET_PULL_UP_VIDEO");
                presenter.upVideo(event.getStart());
                break;

        }
    }

    public void getData(String type, int start) {
        presenter.updata(type, start);
    }

    @Override
    public void showMsg(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, msg, LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void sendData(Base_Bean base_bean) {
        DataMsg dataMsg = new DataMsg(DataType.SENDDATA);
        dataMsg.setData(base_bean);
        EventBus.getDefault().post(dataMsg);
        Log.d(TAG, "setdata: 已发送" + base_bean.getStart());
    }

    @Override
    public void sendVideo(Videobaen bean, int code) {
        Log.d(TAG, "sendVideo: " + bean.getVideoList().size());
        DataMsg dataMsg = new DataMsg(DataType.SENDVIDEO);
        dataMsg.setData(bean);
        dataMsg.setStart(code);
        EventBus.getDefault().post(dataMsg);
    }

    @Override
    public void senderr(int type) {
        DataMsg dataMsg = new DataMsg(type);
        EventBus.getDefault().post(dataMsg);
        Toast.makeText(this,"啊，哦加载出错了，请检查网络",LENGTH_SHORT).show();
    }

    private void addTabitem() {
        String[] titles = getResources().getStringArray(R.array.Tabitems);
        int[] icon = new int[]{R.drawable.statelistdrawable_home, R.drawable.statelistdrawable_video, R.drawable.statelistdrawable_me};
        for (int i = 0; i < titles.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();//
            View view = getLayoutInflater().inflate(R.layout.tab_item, null);
            TextView textView = view.findViewById(R.id.tabtxt);
            ImageView imageView = view.findViewById(R.id.tabimg);
            imageView.setImageResource(icon[i]);
            textView.setText(titles[i]);
            tab.setCustomView(view);
            tabLayout.addTab(tab);
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
            window.setStatusBarColor(this.getResources().getColor(R.color.red_color));
        }
    }

    private void setFragment() {
        fragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), 3);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), false);//取消平滑
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.setOffscreenPageLimit(0);
    }

    private void settoolbar() {
        main_toobar.inflateMenu(R.menu.main_menu);
        main_toobar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //热门新闻
                return true;
            }
        });
    }

    @Override
    protected boolean isDouble_click_to_return() {
        return true;
    }

    @Override
    public void onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    private void islogin() {
        if (SharePrenceUtil.getInt(this, "login") == 1) {
            //登陆成功
            //判断超时
            Long lastLoginTime = SharePrenceUtil.getLong(this, "lastLogonTime");
            Log.d(TAG, "islogin: " + lastLoginTime);
            long now = System.currentTimeMillis();
            Log.d(TAG, "islogin: " + now);
            if ((now - lastLoginTime) > 700 * 60 * 10) {
                SharePrenceUtil.saveInt(this, "login", 0);
                new LoginDialog(this).show();
            }
        } else {
            //登陆失败
            new LoginDialog(this).show();
        }
    }
}

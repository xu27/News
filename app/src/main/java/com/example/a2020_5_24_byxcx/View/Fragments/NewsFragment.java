package com.example.a2020_5_24_byxcx.View.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a2020_5_24_byxcx.Modle.Adapter.MyRecyclerViewDivider;
import com.example.a2020_5_24_byxcx.Modle.Adapter.NewsRecyclerAdapter;
import com.example.a2020_5_24_byxcx.Modle.Adapter.SpaceItemDecoration;
import com.example.a2020_5_24_byxcx.Modle.Events.DataMsg;
import com.example.a2020_5_24_byxcx.Modle.Events.DataType;
import com.example.a2020_5_24_byxcx.Modle.News_JAVAbean.Base_Bean;
import com.example.a2020_5_24_byxcx.Modle.News_JAVAbean.News_Types;
import com.example.a2020_5_24_byxcx.Modle.listenter.EndlessRecyclerOnScrollListener;
import com.example.a2020_5_24_byxcx.R;
import com.example.a2020_5_24_byxcx.View.ArticleActivity;
import com.example.xcxlibrary.Util.EventBusUtils;
import com.example.xcxlibrary.Util.NetUtil;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NewsFragment extends Fragment {

    private static final String TAG = "NewsFragment";
    private String nowType = null;
    private View view;
    private Unbinder mUnbinder = null;

    @BindView(R.id.news_tab)
    protected TabLayout tabLayout;
    @BindView(R.id.news_Refresh)
    protected SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.news_list)
    protected RecyclerView recyclerView;
    private Base_Bean base_bean = null;
    private NewsRecyclerAdapter recyclerAdapter;
    private LinearLayoutManager layoutManager;
    private boolean NET_FLAG = true;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_layout, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        EventBusUtils.register(this);
        initView();
        setTab();
        setlistener();
        nowType = News_Types.TYPE_NORMALNEWS;
        onDrop_down_refresh();
        swipeRefreshLayout.setRefreshing(true);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //activity的onResume在fragment的onStart之前
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        if (recyclerAdapter.getItemCount() == 0) {
            onDrop_down_refresh();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onData_Msg(DataMsg event) {
        switch (event.getCode()) {
            case DataType.SENDDATA:
                Base_Bean data = (Base_Bean) event.getData();
                if (data.getStart() == 0) {
                    //如果等于0表示当前是只获取前10条数据
                    base_bean = (Base_Bean) event.getData();
                    //重置适配器
                    recyclerAdapter.reset_adapter(base_bean.getItemList());
                    recyclerView.scrollToPosition(0);
                } else {
                    base_bean.setStart(data.getStart());
                    base_bean.getItemList().addAll(data.getItemList());
                    recyclerAdapter.addData(data.getItemList());
                    recyclerAdapter.setLoadState(recyclerAdapter.LOADING_COMPLETE);
                    Log.d(TAG, "onData_Msg: 加载完成更新");
                }
                isLoadState();
                break;
            case DataType.EER:
                recyclerAdapter.setLoadState(recyclerAdapter.LOADING_END);
                break;
        }
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        EventBusUtils.unregister(this);
        super.onDestroy();
    }

    public void showToast(String msg) {
        Snackbar snackbar = Snackbar.make(getView(), "这是一个Snackbar", 10000);
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

    private void setlistener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (nowType != null) {
                    swipeRefreshLayout.setRefreshing(true);
                    onDrop_down_refresh();
                }
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                swipeRefreshLayout.setRefreshing(true);
                nowType = (String) tab.getTag();
                onDrop_down_refresh();
                Log.d(TAG, "onTabSelected: " + nowType);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        recyclerAdapter.setListener(new NewsRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClickitem(View view) {
                int i = (int) view.getTag();
                Log.d(TAG, "onClickitem: 111111111111111111111111111111111:" + recyclerAdapter.getList().get(i).toString());
                Log.d(TAG, "onClickitem: 111111111111111111111111111111111:" + recyclerAdapter.getList().get(i).getUrl());
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtra("url", recyclerAdapter.getList().get(i).getUrl());
                intent.putExtra("source", recyclerAdapter.getList().get(i).getSource());
                intent.putExtra("img", recyclerAdapter.getList().get(i).getImgsrc());
                intent.putExtra("title", recyclerAdapter.getList().get(i).getTitle());
                intent.putExtra("id", recyclerAdapter.getList().get(i).getDocid());
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        view, getActivity().getString(R.string.news_title));
                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
            }
        });
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                Log.d(TAG, "onLoadMore: 设置footer");
                onPull_up_refresh();
            }
        });
    }

    private void setTab() {
        if (tabLayout.getTabCount() == 0) {
            tabLayout.addTab(tabLayout.newTab().setText("新闻").setTag(News_Types.TYPE_NORMALNEWS));
            tabLayout.addTab(tabLayout.newTab().setText("游戏").setTag(News_Types.TYPE_YOUXI));
            tabLayout.addTab(tabLayout.newTab().setText("体育").setTag(News_Types.TYPE_TIYU));
            tabLayout.addTab(tabLayout.newTab().setText("科技").setTag(News_Types.TYPE_KEJI));
        }
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        //recyclerView.addItemDecoration(new MyRecyclerViewDivider(this.getActivity(), LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(this.getActivity(), R.color.no_color)));
        recyclerView.addItemDecoration(new SpaceItemDecoration(20));
        recyclerAdapter = new NewsRecyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void onPull_up_refresh() {
        if (NetUtil.isNetworkAvalible(getContext())) {
            DataMsg dataMsg = new DataMsg(DataType.GET_PULL_UP);
            dataMsg.setType(nowType);
            dataMsg.setStart(base_bean.getStart());
            EventBus.getDefault().post(dataMsg);
        }else {
            recyclerAdapter.setLoadState(NewsRecyclerAdapter.LOADING_END);
        }
    }

    private void onDrop_down_refresh() {
        DataMsg dataMsg = new DataMsg(DataType.GET_DROP_DOWN);
        dataMsg.setType(nowType);
        dataMsg.setStart(0);
        EventBus.getDefault().post(dataMsg);
    }

    private void isLoadState() {
        if (recyclerAdapter.getLoadState() == recyclerAdapter.LOADING) {
            recyclerAdapter.setLoadState(recyclerAdapter.LOADING_COMPLETE);
        }
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}

package com.example.a2020_5_24_byxcx.View.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a2020_5_24_byxcx.Modle.Adapter.VideoAdapter;
import com.example.a2020_5_24_byxcx.Modle.Events.DataMsg;
import com.example.a2020_5_24_byxcx.Modle.Events.DataType;
import com.example.a2020_5_24_byxcx.Modle.listenter.EndlessRecyclerOnScrollListener;
import com.example.a2020_5_24_byxcx.Modle.listenter.Endless_VideoRecyclerOnScrollListener;
import com.example.a2020_5_24_byxcx.R;
import com.example.a2020_5_24_byxcx.View.CommentActivity;
import com.example.xcxlibrary.BaseFragment;
import com.example.xcxlibrary.Util.EventBusUtils;
import com.example.xcxlibrary.bean.Videobaen;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.cache.CacheFactory;
import com.shuyu.gsyvideoplayer.model.VideoOptionModel;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VideoFragment extends Fragment {
    private static final String TAG = "VideoFragment";

    private View view;
    private Unbinder mUnbinder;
    private int start = 0;
    private VideoAdapter adapter;
    private GSYVideoHelper smallVideoHelper;

    @BindView(R.id.video_Refresh)
    protected SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.video_list)
    protected RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.video_layout, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initView();
        setlistener();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        onDrop_down_refresh();
    }

    private void initView() {
        EventBusUtils.register(this);
        /**此中内容：优化加载速度，降低延迟*/
        VideoOptionModel videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "rtsp_transport", "tcp");
        List<VideoOptionModel> list = new ArrayList<>();
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "rtsp_flags", "prefer_tcp");
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "allowed_media_types", "video"); //根据媒体类型来配置
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "timeout", 10000);
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "buffer_size", 1316);
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "infbuf", 1);  // 无限读
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "analyzemaxduration", 100);
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "probesize", 10240);
        list.add(videoOptionModel);
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "flush_packets", 1);
        list.add(videoOptionModel);
        //  关闭播放器缓冲，这个必须关闭，否则会出现播放一段时间后，一直卡主，控制台打印 FFP_MSG_BUFFERING_START
        videoOptionModel = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "packet-buffering", 0);
        list.add(videoOptionModel);
        GSYVideoManager.instance().setOptionModelList(list);
        smallVideoHelper = new GSYVideoHelper(this.getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new VideoAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void setlistener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onDrop_down_refresh();
            }
        });
        adapter.setStartVideo(new VideoAdapter.StartVideo() {
            @Override
            public void onClick(View view) {
            }
        });
        recyclerView.addOnScrollListener(new Endless_VideoRecyclerOnScrollListener(){
            @Override
            public void onLoadMore() {
                Log.d(TAG, "onLoadMore: 设置footer");
                onPull_up_refresh();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onData_Msg(DataMsg event) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        switch (event.getCode()) {
            case DataType.SENDVIDEO:
                Log.d(TAG, "onData_Msg: 成功");
                if (event.getStart() == 10) {
                    //下拉刷新
                    Log.d(TAG, "onData_Msg: 下拉");
                    Videobaen videobaen = (Videobaen) event.getData();
                    adapter.claerdata();
                    adapter.setVideobaen(videobaen);
                } else {
                    //上拉刷新
                    Log.d(TAG, "onData_Msg: 上拉"+start);
                    adapter.setVideobaen((Videobaen) event.getData());
                    Log.d(TAG, "onData_Msg: "+adapter.getItemCount());
                }
                start = event.getStart();
                break;
            case DataType.EER_VIDEO:
                Log.d(TAG, "onData_Msg: 失败");
                adapter.setLoadState(adapter.LOADING_END);
                break;
        }
    }

    //上拉刷新
    private void onPull_up_refresh() {
        DataMsg dataMsg = new DataMsg(DataType.GET_PULL_UP_VIDEO);
        dataMsg.setStart(start);
        EventBus.getDefault().post(dataMsg);
    }

    //下拉刷新
    private void onDrop_down_refresh() {
        GSYVideoManager.onPause();
        DataMsg dataMsg = new DataMsg(DataType.GET_DROP_DOWN_VIDEO);
        dataMsg.setStart(0);
        EventBus.getDefault().post(dataMsg);
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
        if (adapter.getItemCount()==0){
            onDrop_down_refresh();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        EventBusUtils.unregister(this);
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }
}

package com.example.a2020_5_24_byxcx.Modle.listenter;

import com.example.a2020_5_24_byxcx.Modle.Adapter.VideoAdapter;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class Endless_VideoRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    //用来标记是否正在向上滑动
    private boolean isSlidingUpward = false;
    //
    public int firstVisibleItem, lastVisibleItem, visibleCount;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        // 当不滑动时
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            //获取最后一个完全显示的itemPosition
            int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
            int itemCount = manager.getItemCount();
            // 判断是否滑动到了最后一个item，并且是向上滑动
            if (lastItemPosition == (itemCount - 1) && isSlidingUpward) {
                //加载更多
                onLoadMore();
            }
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        // 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
        isSlidingUpward = dy > 0;
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
        lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        visibleCount = lastVisibleItem - firstVisibleItem;

        //大于0说明有播放
        if (GSYVideoManager.instance().getPlayPosition() >= 0) {
            //当前播放的位置
            int position = GSYVideoManager.instance().getPlayPosition();
            //对应的播放列表TAG
            if (GSYVideoManager.instance().getPlayTag().equals(VideoAdapter.TAG) && (position < firstVisibleItem || position > lastVisibleItem)) {
                GSYVideoManager.releaseAllVideos();
            }
        }
    }

    /**
     * 加载更多回调
     */
    public abstract void onLoadMore();
}

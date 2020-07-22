package com.example.a2020_5_24_byxcx.Modle.Adapter;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.a2020_5_24_byxcx.R;
import com.example.xcxlibrary.bean.MyComment;
import com.example.xcxlibrary.bean.Videobaen;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class VideoAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    public static final String TAG = "VideoAdapter";

    private Videobaen videobaen = null;
    private StartVideo startVideo = null;
    public int FOOTER = 200;
    public int FABULOUS = 201;

    private int loadState = 1;
    public final int LOADING_COMPLETE = 2;
    public final int LOADING_END = 3;

    public VideoAdapter() {
    }

    public interface StartVideo{
        void onClick(View view);
    }

    @Override
    public void onClick(View view) {
        if (startVideo!=null){
            startVideo.onClick(view);
        }
    }

    public void setStartVideo(StartVideo startVideo) {
        this.startVideo = startVideo;
    }

    public void setVideobaen(Videobaen videobaen) {
        if (this.videobaen==null) {
            this.videobaen = videobaen;
        }else {
            this.videobaen.getVideoList().addAll(videobaen.getVideoList());
        }
        Log.d(TAG, "setVideobaen: "+getItemCount());
        notifyDataSetChanged();
    }

    public void claerdata(){
        videobaen = null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == videobaen.getVideoList().size() + 1) {
            return FOOTER;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: "+viewType);
        if (viewType == FOOTER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_footer, parent, false);
            view.setTag(FOOTER);
            return new FooterViewHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        if (holder instanceof VideoViewHolder){
            //
            VideoViewHolder viewHolder = (VideoViewHolder) holder;
            viewHolder.player.setOnClickListener(this);
            viewHolder.player.setUpLazy(videobaen.getVideoList().get(position).getMp4_url(), true, null, null, videobaen.getVideoList().get(position).getTitle());
            //增加title
            viewHolder.player.getTitleTextView().setVisibility(View.GONE);
            //设置返回键
            viewHolder.player.getBackButton().setVisibility(View.GONE);
            //设置全屏按键功能
            viewHolder.player.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolder.player.startWindowFullscreen(viewHolder.itemView.getContext(), false, true);
                }
            });
            //防止错位设置
            viewHolder.player.setPlayTag(TAG);
            viewHolder.player.setPlayPosition(position);
            //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
            //viewHolder.player.setAutoFullWithSize(true);
            //音频焦点冲突时是否释放
            viewHolder.player.setReleaseWhenLossAudio(false);
            //全屏动画
            viewHolder.player.setShowFullAnimation(true);
            //小屏时不触摸滑动
            viewHolder.player.setIsTouchWiget(false);
            viewHolder.time.setText(videobaen.getVideoList().get(position).getPtime());
            viewHolder.topicDesc.setText(videobaen.getVideoList().get(position).getTitle());
            RequestBuilder<Drawable> builder = Glide.with(holder.itemView)
                    //图片url
                    .load(videobaen.getVideoList().get(position).getTopicImg())
                    //渐变动画
                    .transition(DrawableTransitionOptions.withCrossFade(1000));
            RequestOptions options = new RequestOptions()
                    .bitmapTransform(new CircleCrop())
                    .error(R.drawable.ic_friend); // 创建glide的请求选项
            // 在图像视图上展示网络图片。apply方法表示启用指定的请求选项
            builder.apply(options).into(viewHolder.img);
        }else if (holder instanceof NewsRecyclerAdapter.FooterViewHolder) {
            /*这里返回的是FooterView*/
            Log.d(TAG, "onBindViewHolder: 设置footer");
            FooterViewHolder footViewHolder = (FooterViewHolder) holder;
            switch (loadState) {
                case LOADING_COMPLETE: // 加载完成
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    Log.d(TAG, "onBindViewHolder: 设置隐藏完成");
                    footViewHolder.pbLoading.setVisibility(View.VISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.VISIBLE);
                    break;

                case LOADING_END: // 加载到底
                    footViewHolder.complete.setVisibility(View.GONE);
                    footViewHolder.pbLoading.setVisibility(View.GONE);
                    footViewHolder.tvLoading.setVisibility(View.GONE);
                    footViewHolder.llEnd.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }
        }
    }

    public void setLoadState(int loadState) {
        Log.d(TAG, "setLoadState: 设置状态"+loadState);
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return videobaen==null? 0:videobaen.getVideoList().size() + 1;
    }

    protected class VideoViewHolder extends RecyclerView.ViewHolder{

        public ImageView img;
        public StandardGSYVideoPlayer player;
        public TextView topicDesc,time;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.video_userimg);
            player = itemView.findViewById(R.id.videoView);
            topicDesc = itemView.findViewById(R.id.video_username);
            time = itemView.findViewById(R.id.video_time);
        }
    }

    protected class FooterViewHolder extends RecyclerView.ViewHolder {
        ProgressBar pbLoading;
        TextView tvLoading;
        LinearLayout llEnd,complete;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_loading);
            tvLoading = (TextView) itemView.findViewById(R.id.tv_loading);
            llEnd = (LinearLayout) itemView.findViewById(R.id.ll_end);
            complete = (LinearLayout) itemView.findViewById(R.id.complete);
        }
    }

}

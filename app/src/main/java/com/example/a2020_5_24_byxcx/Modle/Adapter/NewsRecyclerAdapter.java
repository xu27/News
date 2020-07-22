package com.example.a2020_5_24_byxcx.Modle.Adapter;

import android.content.Context;
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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.a2020_5_24_byxcx.Modle.News_JAVAbean.Bean_item;
import com.example.a2020_5_24_byxcx.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsRecyclerAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private static final String TAG = "NewsRecyclerAdapter";

    public static final int FOOTER = 2000;
    private List<Bean_item> list;
    private Context context;
    private OnItemClickListener listener;

    private int loadState = 1;
    public static final int LOADING = 1;
    public static final int LOADING_COMPLETE = 2;
    public static final int LOADING_END = 3;

    public NewsRecyclerAdapter() {
        list = new ArrayList<>();
    }

    public NewsRecyclerAdapter(Context context) {
        this.context = context;
    }

    public interface OnItemClickListener {
        public void onClickitem(View view);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void addData(List<Bean_item> data) {
        this.list.addAll(data);
        notifyItemRangeInserted(list.size() - data.size(), data.size());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        if (holder instanceof MyViewHolder) {
            /*这里返回的是普通的View*/
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.itemView.setTag(position);
            myViewHolder.source.setText(list.get(position).getSource());
            myViewHolder.title.setText(list.get(position).getTitle());
            myViewHolder.ptime.setText(list.get(position).getPtime());
            RequestBuilder<Drawable> builder = Glide.with(holder.itemView)
                    //图片url
                    .load(list.get(position).getImgsrc())
                    //渐变动画
                    .transition(DrawableTransitionOptions.withCrossFade(1000));
            RequestOptions options = new RequestOptions()
                    .bitmapTransform(new RoundedCorners(20)); // 创建glide的请求选项
            // 在图像视图上展示网络图片。apply方法表示启用指定的请求选项
            builder.apply(options).into(myViewHolder.image);
        } else if (holder instanceof FooterViewHolder) {
            /*这里返回的是FooterView*/
            Log.d(TAG, "onBindViewHolder: 设置footer");
            FooterViewHolder footViewHolder = (FooterViewHolder) holder;
            switch (loadState) {
                /*case LOADING: // 正在加载
                    footViewHolder.pbLoading.setVisibility(View.GONE);
                    footViewHolder.tvLoading.setVisibility(View.GONE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    footViewHolder.complete.setVisibility(View.VISIBLE);
                    break;*/

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

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_footer, parent, false);
            return new FooterViewHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == list.size() + 1) {
            return FOOTER;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size() + 1;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClickitem(view);
        }
    }

    /**
     *
     * @param loadState 1.正在加载 2.加载完成 3.加载到底
     */
    public void setLoadState(int loadState) {
        Log.d(TAG, "setLoadState: 设置状态"+loadState);
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    public int getLoadState() {
        return loadState;
    }

    public void reset_adapter(List<Bean_item> data){
        this.list = data;
        notifyDataSetChanged();
    }

    public List<Bean_item> getList() {
        return list;
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, source, ptime;
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title);
            source = itemView.findViewById(R.id.news_source);
            ptime = itemView.findViewById(R.id.new_ptime);
            image = itemView.findViewById(R.id.news_img);
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

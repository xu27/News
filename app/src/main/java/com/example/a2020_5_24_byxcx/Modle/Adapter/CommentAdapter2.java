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

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class CommentAdapter2 extends RecyclerView.Adapter implements View.OnClickListener {
    private static final String TAG = "CommentAdapter2";

    private MyComment comment = null;
    private FooteronClickListener footeronClickListener = null;
    public int FOOTER = 200;
    public int FABULOUS = 201;

    private int loadState = 1;
    public final int LOADING_COMPLETE = 2;
    public final int LOADING_END = 3;

    public CommentAdapter2() {
    }

    public CommentAdapter2(MyComment comment) {
        this.comment = comment;
    }

    public void setFooteronClickListener(CommentAdapter.FooteronClickListener footeronClickListener) {
    }

    public interface FooteronClickListener{
        public void onClick(View view);
    }

    public void setFooteronClickListener(FooteronClickListener footeronClickListener) {
        this.footeronClickListener = footeronClickListener;
    }

    @Override
    public void onClick(View view) {
        if (footeronClickListener != null){
            footeronClickListener.onClick(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == comment.getHotPosts().size() + 1) {
            return FOOTER;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: "+viewType);
        if (viewType == FOOTER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_footer2, parent, false);
            view.setTag(FOOTER);
            return new FooterViewHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        if (holder instanceof CommentViewHolder){
            CommentViewHolder viewHolder= (CommentViewHolder) holder;
            viewHolder.user_name.setText(comment.getHotPosts().get(position).get_$1().getN());
            viewHolder.user_time.setText(comment.getHotPosts().get(position).get_$1().getT());
            viewHolder.fabulous.setText(comment.getHotPosts().get(position).get_$1().getV());
            viewHolder.comment_text.setText(comment.getHotPosts().get(position).get_$1().getB());
            String str = filterString(comment.getHotPosts().get(position).get_$1().getF());
            viewHolder.user_from.setText(str);
            RequestBuilder<Drawable> builder = Glide.with(holder.itemView)
                    //图片url
                    .load(comment.getHotPosts().get(position).get_$1().getTimg())
                    //渐变动画
                    .transition(DrawableTransitionOptions.withCrossFade(1000));
            RequestOptions options = new RequestOptions()
                    .bitmapTransform(new CircleCrop())
                    .error(R.drawable.ic_friend); // 创建glide的请求选项
            // 在图像视图上展示网络图片。apply方法表示启用指定的请求选项
            builder.apply(options).into(viewHolder.user_img);
            viewHolder.layout.setTag(FABULOUS);
            viewHolder.layout.setOnClickListener(this);
        }else if (holder instanceof FooterViewHolder) {
            /*这里返回的是FooterView*/
            Log.d(TAG, "onBindViewHolder: 设置footer");
           FooterViewHolder footViewHolder = (FooterViewHolder) holder;
            switch (loadState) {
                case LOADING_COMPLETE: // 加载完成
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    footViewHolder.pbLoading.setVisibility(View.VISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.VISIBLE);
                    break;

                case LOADING_END: // 加载到底
                    Log.d(TAG, "onBindViewHolder: end");
                    footViewHolder.complete.setVisibility(View.GONE);
                    footViewHolder.pbLoading.setVisibility(View.GONE);
                    footViewHolder.tvLoading.setVisibility(View.GONE);
                    footViewHolder.llEnd.setVisibility(View.VISIBLE);
                    break;
            }
        }else {
            Log.d(TAG, "onBindViewHolder: 失败");
        }
    }

    public void setComment(MyComment getcomment) {
        if (getcomment.getCode() == 1){
            if (comment == null) {
                this.comment = getcomment;
                notifyDataSetChanged();
            }else {
                int i = this.comment.getHotPosts().size();
                this.comment.getHotPosts().addAll(getcomment.getHotPosts());
                notifyItemRangeChanged(i, this.getItemCount());
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
        return comment==null? 0:comment.getHotPosts().size() + 1;
    }

    protected class CommentViewHolder extends RecyclerView.ViewHolder{

        public ConstraintLayout layout;
        public ImageView user_img,fabulous_img;
        private TextView user_name,user_from,user_time,comment_text,fabulous;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            user_img = itemView.findViewById(R.id.comment_img);
            fabulous_img = itemView.findViewById(R.id.comment_fabulous_img);
            user_name = itemView.findViewById(R.id.comment_user);
            user_from = itemView.findViewById(R.id.comment_from);
            user_time = itemView.findViewById(R.id.comment_time);
            comment_text = itemView.findViewById(R.id.comment_text);
            fabulous = itemView.findViewById(R.id.comment_fabulous);
            layout = itemView.findViewById(R.id.comment_fabulousLayout);
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

    private String filterString(String str){
        String s = null;
        if (str.contains("&nbsp")){
            int i = str.indexOf("&nbsp");
            s = str.substring(0,i);
        }
        return s;
    }
}

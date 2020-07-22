package com.example.a2020_5_24_byxcx.Modle.Adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class CommentAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private MyComment comment;
    private FooteronClickListener footeronClickListener = null;
    public int FOOTER = 200;
    public int FABULOUS = 201;

    public CommentAdapter(MyComment comment) {
        this.comment = comment;
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
        if (viewType == FOOTER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_footer, parent, false);
            view.setOnClickListener(this);
            view.setTag(FOOTER);
            return new FooterViewHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
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
        }
    }

    @Override
    public int getItemCount() {
        return comment.getHotPosts().size() + 1;
    }

    protected class CommentViewHolder extends RecyclerView.ViewHolder{

        public ConstraintLayout layout;
        public ImageView user_img,fabulous_img;
        public TextView user_name,user_from,user_time,comment_text,fabulous;

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

    protected class FooterViewHolder extends RecyclerView.ViewHolder{

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
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

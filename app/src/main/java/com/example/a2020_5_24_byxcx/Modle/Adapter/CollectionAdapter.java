package com.example.a2020_5_24_byxcx.Modle.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a2020_5_24_byxcx.Modle.Dao.NewsModle;
import com.example.a2020_5_24_byxcx.Modle.News_JAVAbean.Bean_item;
import com.example.a2020_5_24_byxcx.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CollectionAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private static final String TAG = "CollectionAdapter";

    private List<NewsModle> data;
    private MyOnClickListener listener = null;

    public CollectionAdapter(List<NewsModle> list) {
        data = list;
    }

    public interface MyOnClickListener {
        void click(View view, int i);
    }

    public void setListener(MyOnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_list_item, parent, false);
        return new CollectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        CollectionViewHolder viewHolder = (CollectionViewHolder) holder;
        viewHolder.title.setTag(position);
        viewHolder.title.setText(data.get(position).getTitle());
        viewHolder.source.setText(data.get(position).getSource());
        viewHolder.right.setOnClickListener(this);
        viewHolder.title.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.click(view, (Integer) view.getTag());
        }
    }


    public class CollectionViewHolder extends RecyclerView.ViewHolder {

        TextView title, source;

        ImageView right;

        public CollectionViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.collection_title);
            source = itemView.findViewById(R.id.collection_source);
            right = itemView.findViewById(R.id.collection_img);
        }
    }
}

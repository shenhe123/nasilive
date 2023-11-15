package com.feicui365.live.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.feicui365.live.R;
import com.feicui365.live.model.entity.ShortVideo;

import java.util.List;

public class AnchorWorksAdapter extends RecyclerView.Adapter<AnchorWorksAdapter.ViewHolder> {

    private List<ShortVideo> anchorHistories;
    private Context context;
    private OnItemClickListener onItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView like_tv;
        ImageView cover_iv;
        View root_view;

        public ViewHolder (View view)
        {
            super(view);
            like_tv = view.findViewById(R.id.like_tv);
            cover_iv = view.findViewById(R.id.cover_iv);
            root_view = view.findViewById(R.id.root_view);
        }

    }

    public AnchorWorksAdapter(List <ShortVideo> anchorHistories, Context context){
        this.anchorHistories = anchorHistories;
        this.context = context;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.anchor_anchor_works_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        if (null != anchorHistories.get(position).getLike_count() &&
                Integer.valueOf(anchorHistories.get(position).getLike_count())>10000){
            holder.like_tv.setText(String.valueOf(Float.valueOf(anchorHistories.get(position).getLike_count()).floatValue()/1000));
        } else {
            holder.like_tv.setText(anchorHistories.get(position).getLike_count());
        }
        Glide.with(context).applyDefaultRequestOptions(new RequestOptions().placeholder(R.mipmap.zhanwei)).load(anchorHistories.get(position).getThumb_url()).into(holder.cover_iv);
        holder.root_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position);
            }
        });

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount(){
        return anchorHistories.size();
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
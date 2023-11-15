package com.feicui365.live.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.feicui365.live.R;
import com.feicui365.live.model.entity.ContributeRank;

import java.util.ArrayList;

public class ChatVerticalUserAdapter extends BaseAdapter {

    private final Context context;
    private ArrayList<ContributeRank> filters;
    public OnItemClikeListener  onItemClikeListener;


    public  interface OnItemClikeListener{
        void onItemClick(String id);
    }

    public void setOnItemClikeListener(OnItemClikeListener onItemClikeListener) {
        this.onItemClikeListener = onItemClikeListener;
    }

    public ChatVerticalUserAdapter(Context applicationContext, ArrayList<ContributeRank> intimacys) {
        this.context = applicationContext;
        this.filters = intimacys;
    }

    @Override
    public int getCount() {
        return filters.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.top_rank_item, null);
            holder = new ViewHolder();
            holder.avatar_iv = convertView.findViewById(R.id.avatar_iv);
            holder.avatar_rl = convertView.findViewById(R.id.avatar_rl);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        switch (position){
            case 0:
                holder.avatar_rl.setBackgroundResource(R.mipmap.no1);
                break;
            case 1:
                holder.avatar_rl.setBackgroundResource(R.mipmap.no2);
                break;
            case 2:
                holder.avatar_rl.setBackgroundResource(R.mipmap.no);
                break;
            default:
                holder.avatar_rl.setBackgroundColor(Color.TRANSPARENT);
                break;
        }
        Glide.with(context).load(filters.get(position).getUser().getAvatar()).into(holder.avatar_iv);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClikeListener!=null){
                    onItemClikeListener.onItemClick(filters.get(position).getUser().getId()+"");
                }
            }
        });
        return convertView;
    }


    static class ViewHolder {
        View avatar_rl;
        ImageView avatar_iv;
    }
}




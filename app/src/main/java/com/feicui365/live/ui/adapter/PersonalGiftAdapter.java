package com.feicui365.live.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.feicui365.live.R;
import com.feicui365.live.model.entity.GiftShow;

import java.util.ArrayList;

public class PersonalGiftAdapter extends BaseAdapter {

    private final Context context;
    private ArrayList<GiftShow> filters;

    public PersonalGiftAdapter(Context applicationContext, ArrayList<GiftShow> intimacys) {
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
            convertView = View.inflate(context, R.layout.personal_gift_item, null);
            holder = new ViewHolder();
            holder.avatar_iv = convertView.findViewById(R.id.avatar_iv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(filters.get(position).getIcon()).into(holder.avatar_iv);
        return convertView;
    }


    static class ViewHolder {
        ImageView avatar_iv;
    }
}




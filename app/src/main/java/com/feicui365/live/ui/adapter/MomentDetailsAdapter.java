package com.feicui365.live.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.feicui365.live.R;
import com.feicui365.live.util.FormatCurrentData;
import com.feicui365.live.model.entity.MomentDetail;

import java.util.ArrayList;

public class MomentDetailsAdapter extends BaseAdapter {

    private final Context context;
    private ArrayList<MomentDetail> trend;

    public MomentDetailsAdapter(Context applicationContext,ArrayList<MomentDetail> trend) {
        this.context = applicationContext;
        this.trend = trend;
    }

    @Override
    public int getCount() {
        return trend.size();
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
            convertView = View.inflate(context, R.layout.moment_details_item, null);
            holder = new ViewHolder();
            holder.civ_avatar = convertView.findViewById(R.id.civ_avatar);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_age = convertView.findViewById(R.id.tv_age);
            holder.tv_level = convertView.findViewById(R.id.tv_level);
            holder.tv_content = convertView.findViewById(R.id.tv_content);
            holder.tv_time = convertView.findViewById(R.id.tv_time);
            holder.tv_zan = convertView.findViewById(R.id.tv_zan);
            holder.tv_liuyan = convertView.findViewById(R.id.tv_liuyan);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(convertView).load(trend.get(position).getUser().getAvatar()).into(holder.civ_avatar);
        holder.tv_name.setText(trend.get(position).getUser().getNick_name());
        holder.tv_age.setText(trend.get(position).getUser().getAge());
        holder.tv_level.setText(trend.get(position).getUser().getUser_level());
        holder.tv_content.setText(trend.get(position).getContent());
        holder.tv_time.setText(  FormatCurrentData.getTimeRange2(trend.get(position).getCreate_time()));

        holder.tv_zan.setText(trend.get(position).getLike_count());
//        holder.tv_liuyan.setText(trend.get(position).getLike_count());


        return convertView;
    }

    public void setData(ArrayList<MomentDetail> trend) {
        this.trend.addAll(trend);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        ImageView civ_avatar;
        TextView tv_name;
        TextView tv_age;
        TextView tv_level;
        TextView tv_content;
        TextView tv_time;
        TextView tv_zan;
        TextView tv_liuyan;
    }
}




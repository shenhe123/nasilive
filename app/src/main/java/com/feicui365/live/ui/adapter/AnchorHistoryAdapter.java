package com.feicui365.live.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.feicui365.live.R;
import com.feicui365.live.live.adapter.SwiftMessageAdapter;
import com.feicui365.live.model.entity.AnchorHistory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AnchorHistoryAdapter extends RecyclerView.Adapter<AnchorHistoryAdapter.ViewHolder> {

    private List<AnchorHistory> anchorHistories;
    private Context mContext;

    OnAnchorHistoryClickListener OnAnchorHistoryClickListener ;

    public void setOnAnchorHistoryClickListener(OnAnchorHistoryClickListener OnAnchorHistoryClickListener) {
        this.OnAnchorHistoryClickListener = OnAnchorHistoryClickListener;
    }
    public interface OnAnchorHistoryClickListener {
        void onItemClick(String id);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_anchor_title,  tv_anchor_long, tv_time ,tv_hide_name;
        ImageView thumb_iv;
        CircleImageView tv_anchor_iv;

        public ViewHolder(View view) {
            super(view);
            tv_anchor_title = (TextView) view.findViewById(R.id.tv_anchor_title);
          //  tv_anchor_income = (TextView) view.findViewById(R.id.tv_anchor_income);
            tv_anchor_long = (TextView) view.findViewById(R.id.tv_anchor_long);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_hide_name = (TextView) view.findViewById(R.id.tv_hide_name);
            thumb_iv = (ImageView) view.findViewById(R.id.thumb_iv);
            tv_anchor_iv = (CircleImageView) view.findViewById(R.id.tv_anchor_iv);
        }

    }


    public AnchorHistoryAdapter(List<AnchorHistory> anchorHistories) {
        this.anchorHistories = anchorHistories;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.anchor_history_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        AnchorHistory anchorHistory = anchorHistories.get(position);
        holder.tv_time.setText(anchorHistory.getStart_time());
        holder.tv_anchor_title.setText("直播标题：" + anchorHistory.getTitle());
    //    holder.tv_anchor_income.setText("直播收益：" + anchorHistory.getGift_profit() +"钻石");
        //【1隐藏 2显示】
        int is_hide_name = anchorHistory.getIs_hide_name();
        if (is_hide_name == 1 ) {
            holder.tv_hide_name.setText("观众昵称隐藏：隐藏");
        }else if (is_hide_name == 2) {
            holder.tv_hide_name.setText("观众昵称隐藏：无隐藏");
        }

        Glide.with(mContext).load(anchorHistory.getThumb()).into(holder.thumb_iv);
       // Glide.with(mContext).load(anchorHistory.get()).into(holder.thumb_iv);

        try {
            holder.tv_anchor_long.setText("直播时长：" +anchorHistory.getDuration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OnAnchorHistoryClickListener != null) {
                    OnAnchorHistoryClickListener.onItemClick(anchorHistory.getLiveid());
                }
            }
        });
    }

    public String  getTime(String date11, String date22) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date now = df.parse(date11);
            Date date = df.parse(date22);
            long l = now.getTime() - date.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

            String time = "";
            if (hour == 0) {
                time = time + "00"+"：";
            } else if (hour > 0) {
                if (hour < 10) {
                    time = time + "0" + hour+"：";
                } else {
                    time = time + hour+"：";
                }
            }

            if (min == 0) {
                time = time + "00"+"：";
            } else if (min > 0) {
                if (min < 10) {
                    time = time + "0" + min+"：";
                } else {
                    time = time + min+"：";
                }
            }

            if (s == 0) {
                time = time + "00";
            } else if (s > 0) {
                if (s < 10) {
                    time = time + "0" + s;
                } else {
                    time = time + s;
                }
            }

            return time;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public int getItemCount() {
        return anchorHistories.size();
    }
}
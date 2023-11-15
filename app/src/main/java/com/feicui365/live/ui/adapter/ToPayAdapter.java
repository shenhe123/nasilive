package com.feicui365.live.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.model.entity.Gold;

import java.util.List;

public class ToPayAdapter extends RecyclerView.Adapter<ToPayAdapter.MyViewHolder> {
    private Context context;
    private List<Gold> list;
    private int defItem = -1;//默认值
    private OnItemListener onItemListener;

    public ToPayAdapter(Context context, List<Gold> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    public interface OnItemListener {
        void onClick(View v, int pos, Gold projectc);
    }

    public void setDefSelect(int position) {
        this.defItem = position;
        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.to_pay_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (defItem != -1) {
            if (defItem == position) {
                int gold=Integer.parseInt(list.get(position).getGold())+Integer.parseInt(list.get(position).getGold_added());
                holder.tv_coin.setText(gold+"金币");
                holder.tv_money.setText("￥"+list.get(position).getPrice());
                holder.tv_coin.setTextColor(Color.parseColor("#ffffff"));
                holder.tv_money.setTextColor(Color.parseColor("#ffffff"));
                holder.ll_to_pay.setBackgroundResource(R.mipmap.jinbi_pre);
                holder.v_1.setBackground(context.getResources().getDrawable(R.drawable.trans_bg));
            } else {
                int gold=Integer.parseInt(list.get(position).getGold())+Integer.parseInt(list.get(position).getGold_added());
                holder.tv_coin.setText(gold+"金币");
                holder.tv_money.setText("￥"+list.get(position).getPrice());
                holder.tv_coin.setTextColor(Color.parseColor("#000000"));
                holder.tv_money.setTextColor(Color.parseColor("#9F9F9F"));
                holder.ll_to_pay.setBackgroundResource(R.drawable.line_stroke);
                holder.v_1.setVisibility(View.VISIBLE);
            }
        }else{
            int gold=Integer.parseInt(list.get(position).getGold())+Integer.parseInt(list.get(position).getGold_added());
            holder.tv_coin.setText(gold+"金币");
            holder.tv_money.setText("￥"+list.get(position).getPrice());
            holder.tv_coin.setTextColor(Color.parseColor("#000000"));
            holder.tv_money.setTextColor(Color.parseColor("#9F9F9F"));
            holder.ll_to_pay.setBackgroundResource(R.drawable.line_stroke);
            holder.v_1.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * ViewHolder的类，用于缓存控件
     */
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_money,tv_coin;
        LinearLayout ll_to_pay;
        View v_1;
        public MyViewHolder(View view) {
            super(view);
            tv_coin = (TextView) view.findViewById(R.id.tv_coin);
            tv_money = (TextView) view.findViewById(R.id.tv_money);
            ll_to_pay=view.findViewById(R.id.ll_to_pay_item);

            v_1=view.findViewById(R.id.v_1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemListener != null) {
                        onItemListener.onClick(v, getLayoutPosition(), list.get(getLayoutPosition()));
                    }
                }
            });
        }


    }

}
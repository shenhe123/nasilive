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
import com.feicui365.live.model.entity.CashOutHistory;

import java.util.List;

public class CashOutHistorydapter extends RecyclerView.Adapter<CashOutHistorydapter.ViewHolder> {

    private List<CashOutHistory> cashOutHistories;
    Context context;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_time,tv_apply_num,tv_apply_money,tv_ali_account,tv_order_num;
        ImageView iv_state;

        public ViewHolder (View view)
        {
            super(view);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_apply_num = (TextView) view.findViewById(R.id.tv_apply_num);
            tv_apply_money = (TextView) view.findViewById(R.id.tv_apply_money);
            tv_ali_account = (TextView) view.findViewById(R.id.tv_ali_account);
            tv_order_num = (TextView) view.findViewById(R.id.tv_order_num);
            iv_state =view.findViewById(R.id.iv_state);

        }

    }

    public CashOutHistorydapter(List <CashOutHistory> fruitList, Context context){
        cashOutHistories = fruitList;
        this.context=context;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cash_out_history_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        CashOutHistory cashOutHistory = cashOutHistories.get(position);
        holder.tv_time.setText(cashOutHistory.getCreate_time());
        holder.tv_apply_num.setText("申请提现："+cashOutHistory.getDiamond());
        holder.tv_ali_account.setText("收款支付宝账号："+cashOutHistory.getAlipay_account());
        holder.tv_order_num.setText("转账单号："+cashOutHistory.getTrade_no());
        holder.tv_apply_money.setText("¥ "+cashOutHistory.getCash());

        switch (cashOutHistory.getStatus()){
            case "0":
                Glide.with(context).load(R.mipmap.dengdaichuli).into(holder.iv_state);
                break;
            case "1":
                Glide.with(context).load(R.mipmap.yiwancheng).into(holder.iv_state);
                break;
            case "2":
                Glide.with(context).load(R.mipmap.yibohui).into(holder.iv_state);
                break;
            case "3":
                Glide.with(context).load(R.mipmap.yichang).into(holder.iv_state);
                break;
        }

    }

    @Override
    public int getItemCount(){
        return cashOutHistories.size();
    }
}
package com.feicui365.live.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.model.entity.GiftIncome;

import java.util.List;

public class MyIncomeAdapter extends RecyclerView.Adapter<MyIncomeAdapter.MyViewHolder> {

    private List<GiftIncome> giftIncomeList;
    private Context context;
     class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_gift;
        TextView tv_count;
        TextView tv_time;

        public MyViewHolder (View view)
        {
            super(view);
            tv_gift = (TextView) view.findViewById(R.id.tv_gift);
            tv_count = (TextView) view.findViewById(R.id.tv_count);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
        }

    }

    public MyIncomeAdapter(List <GiftIncome> giftIncomeList, Context context){
        this.giftIncomeList = giftIncomeList;
        this.context = context;
    }

    @Override

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        MyViewHolder holder  = new MyIncomeAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.my_income_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){

        GiftIncome giftIncome = giftIncomeList.get(position);
        if(giftIncome.getGift()!=null){
            holder.tv_gift.setText(giftIncome.getGift().getTitle());
        }else{
            holder.tv_gift.setText(giftIncome.getContent());
        }
        holder.tv_count.setText(giftIncome.getCoin_count());

        holder.tv_time.setText(giftIncome.getCreate_time());
    }

    @Override
    public int getItemCount(){
        return giftIncomeList.size();
    }
}
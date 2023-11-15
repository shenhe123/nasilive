package com.feicui365.live.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.model.entity.ProfitLog;

import java.util.List;

public class MyIncomeAgentAdapter extends RecyclerView.Adapter<MyIncomeAgentAdapter.MyViewHolder> {

    private List<ProfitLog> giftIncomeList;
    private Context context;
     class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_act;
        TextView tv_time;
        TextView tv_result;

        public MyViewHolder (View view)
        {
            super(view);
            tv_act = (TextView) view.findViewById(R.id.tv_act);
            tv_result = (TextView) view.findViewById(R.id.tv_result);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
        }

    }

    public MyIncomeAgentAdapter(List <ProfitLog> giftIncomeList, Context context){
        this.giftIncomeList = giftIncomeList;
        this.context = context;
    }

    @Override

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        MyViewHolder holder  = new MyIncomeAgentAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.my_income_agent_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){

        ProfitLog giftIncome = giftIncomeList.get(position);
        holder.tv_act.setText(giftIncome.getContent());
        holder.tv_result.setText(giftIncome.getProfit());
        holder.tv_time.setText(giftIncome.getCreate_time());
    }

    @Override
    public int getItemCount(){
        return giftIncomeList.size();
    }
}
package com.feicui365.live.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.ui.act.ReportActivity;

import java.util.List;

public class ReportItemAdapter extends RecyclerView.Adapter<ReportItemAdapter.MyViewHolder> {

    private List<String> giftIncomeList;
    private Context context;
    private String report_id;
    private String report_type;
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_report;

        public MyViewHolder(View view) {
            super(view);
            tv_report = (TextView) view.findViewById(R.id.tv_report);
        }

    }

    public ReportItemAdapter(List<String> giftIncomeList, Context context,String report_id,String report_type) {
        this.giftIncomeList = giftIncomeList;
        this.context = context;
        this.report_id=report_id;
        this.report_type=report_type;
    }

    @Override

    public ReportItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ReportItemAdapter.MyViewHolder holder = new ReportItemAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.report_item, parent,
                false));

        return holder;
    }

    @Override
    public void onBindViewHolder(ReportItemAdapter.MyViewHolder holder, int position) {



        holder.tv_report.setText(giftIncomeList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ReportActivity.class);
                intent.putExtra("REPORT_ITEM",holder.tv_report.getText());
                intent.putExtra("REPORT_ID",report_id);
                intent.putExtra("REPORT_TYPE",report_type);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return giftIncomeList.size();
    }
}

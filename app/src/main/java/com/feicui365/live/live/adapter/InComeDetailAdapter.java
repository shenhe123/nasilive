package com.feicui365.live.live.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.model.entity.AccountDetailBean;
import com.feicui365.live.model.entity.SwiftMessageBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class InComeDetailAdapter extends RecyclerView.Adapter<InComeDetailAdapter.IncomeDetailViewHolder> {

    private final Context mContext;
   // OnSwiftMessageClickListener OnSwiftMessageClickListener;
    private List<AccountDetailBean> mList = new ArrayList<>();

//    public void setOnSwiftMessageClickListener(OnSwiftMessageClickListener OnSwiftMessageClickListener) {
//        this.OnSwiftMessageClickListener = OnSwiftMessageClickListener;
//    }
//
//    public interface OnSwiftMessageClickListener {
//        void onItemClick(String id);
//    }

    public InComeDetailAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public IncomeDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_income_detail, parent, false);

        return new IncomeDetailViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeDetailViewHolder holder, int i) {
        holder.mDetailTitle.setText(mList.get(i).getTitle());
        //余额记录类型（1账户充值 2后台扣款 3直播费用支出）
        int type = mList.get(i).getType();
        if (type == 3) {
             holder.mDetailTimeTv.setText("开播时间");
            holder.mDetailPriceTv.setTextColor(mContext.getResources().getColor(R.color.color_main_theme_color));
        } else if (type == 1) {
            holder.mDetailTimeTv.setText("充值时间");
            holder.mDetailPriceTv.setTextColor(mContext.getResources().getColor(R.color.color_FE3F10));
        }
        holder.mDetailTime.setText(mList.get(i).getPay_time());
        holder.mDetailPriceTv.setText(mList.get(i).getPrice());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public void addData(List<AccountDetailBean> data) {
        if (mList.size() != 0) mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }


    public class IncomeDetailViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.detail_title_tv)
        TextView mDetailTitle;
        @BindView(R.id.detail_time_tv)
        TextView mDetailTimeTv;
        @BindView(R.id.detail_time)
        TextView mDetailTime;
        @BindView(R.id.detail_price_tv)
        TextView mDetailPriceTv;


        public IncomeDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

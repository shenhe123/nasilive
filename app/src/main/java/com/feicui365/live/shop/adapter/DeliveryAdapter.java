package com.feicui365.live.shop.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.shop.entity.Logistics;
import com.feicui365.nasinet.utils.DipPxUtils;

import java.util.List;

public class DeliveryAdapter extends BaseQuickAdapter<Logistics, BaseViewHolder> {

    public DeliveryAdapter( @Nullable List<Logistics> data) {
        super(R.layout.delicery_item, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Logistics item) {
        if (getData().size()>1){
            helper.setGone(R.id.line_gray_1,true);
            helper.setGone(R.id.line_gray_2,true);
        }else{

            helper.setGone(R.id.line_gray_1,false);
            helper.setGone(R.id.line_gray_2,false);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DipPxUtils.dip2px(helper.itemView.getContext(),7), DipPxUtils.dip2px(helper.itemView.getContext(),7));
            lp.gravity= Gravity.CENTER_HORIZONTAL;
            lp.setMargins(0, DipPxUtils.dip2px(helper.itemView.getContext(),5), 0, 0);
            helper.getView(R.id.iv_point).setLayoutParams(lp);
        }

        if(helper.getLayoutPosition()==1){
            helper.setImageResource(R.id.iv_point,R.drawable.shape_circle_red);
        }else{
            helper.setImageResource(R.id.iv_point,R.drawable.shape_circle_gray);
        }


        helper.setText(R.id.tv_delivety,item.getStatus()+" "+item.getContext());
        helper.setText(R.id.tv_time,item.getTime());
    }
}

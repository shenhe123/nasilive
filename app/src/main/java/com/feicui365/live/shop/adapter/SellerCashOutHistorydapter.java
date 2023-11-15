package com.feicui365.live.shop.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.shop.entity.SellerCashOut;

import java.util.List;

public class SellerCashOutHistorydapter extends BaseQuickAdapter<SellerCashOut, BaseViewHolder> {



    public SellerCashOutHistorydapter( @Nullable List<SellerCashOut> data) {
        super(R.layout.seller_cash_out_history_item, data);
    }



    @Override
    protected void convert(@NonNull BaseViewHolder helper, SellerCashOut item) {
        helper.setText(R.id.tv_time,item.getCreate_time());
        helper.setText(R.id.tv_apply_price,"申请提现："+item.getApply_cash());
        helper.setText(R.id.tv_service_money,"服务费："+item.getCommission_cash());
        helper.setText(R.id.tv_ali_account,"收款支付宝账号："+item.getAlipay_account());
        if(item.getTrade_no()!=null){
            helper.setGone(R.id.tv_order_num,true);
            helper.setText(R.id.tv_order_num,"转账单号："+item.getTrade_no());
        }else{
            helper.setGone(R.id.tv_order_num,false);
        }

        switch (item.getStatus()){
            case 0:

                helper.setImageResource(R.id.iv_state,R.mipmap.dengdaichuli);
                break;
            case 1:

                helper.setImageResource(R.id.iv_state,R.mipmap.yiwancheng);
                break;
            case 2:

                helper.setImageResource(R.id.iv_state,R.mipmap.yibohui);
                break;
            case 3:

                helper.setImageResource(R.id.iv_state,R.mipmap.yichang);
                break;
        }
    }

}
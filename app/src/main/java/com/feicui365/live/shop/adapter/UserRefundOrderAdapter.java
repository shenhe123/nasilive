package com.feicui365.live.shop.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.shop.entity.OrderGoods;
import com.feicui365.live.shop.entity.RefundOrderGoods;

import java.util.ArrayList;
import java.util.List;

public class UserRefundOrderAdapter extends BaseQuickAdapter<RefundOrderGoods, BaseViewHolder> {



    public UserRefundOrderAdapter(@Nullable List<RefundOrderGoods> data) {
        super(R.layout.order_item, data);

    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RefundOrderGoods item) {
        Glide.with(helper.itemView)
                .applyDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(R.mipmap.zhanwei))
                .load(item.getShop().getAvatar())
                .into((ImageView) helper.getView(R.id.civ_avatar));
        helper.setText(R.id.tv_nickname, item.getShop().getNick_name());
        List<OrderGoods> temp=new ArrayList<>();
        temp.add(item.getGoods());
        UserRefundOrderChildAdapter orderChildAdapter = new UserRefundOrderChildAdapter(temp);
        ((RecyclerView) helper.getView(R.id.rv_shop_goods)).setAdapter(orderChildAdapter);
        ((RecyclerView) helper.getView(R.id.rv_shop_goods)).setLayoutManager(new LinearLayoutManager(helper.itemView.getContext()));
        ((RecyclerView) helper.getView(R.id.rv_shop_goods)).setLayoutFrozen(true);


            initStatus(helper,item);

        
    }

    private void initStatus(BaseViewHolder helper, RefundOrderGoods item) {
        switch (item.getStatus()) {

            case "1":
                //发起退货

        helper.setText(R.id.tv_status,"审核中");
                break;
            case "2":
                //卖家同意退货
                helper.setText(R.id.tv_status,"退货中");
                break;
            case "3":
                //-卖家拒绝退货
                helper.setText(R.id.tv_status,"拒绝退货");
                break;
            case "4":
                //退货完成
                helper.setText(R.id.tv_status,"已完成");
                break;
            case "5":
                //买家取消退货
                helper.setText(R.id.tv_status,"已取消");
                break;



        }




    }

}

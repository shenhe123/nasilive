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
import com.feicui365.live.shop.entity.CartGoodInfo;

import java.util.List;

public class ConfirmGoodAdapter extends BaseQuickAdapter<CartGoodInfo, BaseViewHolder> {
    ConfirmGoodChildAdapter.ChildNumChangeListener childCheckChangeListener;

    public interface ChildNumChangeListener {
        void checkChange();
    }

    public ConfirmGoodChildAdapter.ChildNumChangeListener getCheckChangeListener() {
        return childCheckChangeListener;
    }

    public void setCheckChangeListener(ConfirmGoodChildAdapter.ChildNumChangeListener childCheckChangeListener) {
        this.childCheckChangeListener = childCheckChangeListener;
    }
    public ConfirmGoodAdapter(@Nullable List<CartGoodInfo> data) {
        super(R.layout.confirm_good_item, data);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, CartGoodInfo item) {
        Glide.with(helper.itemView)
                .applyDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(R.mipmap.zhanwei))
                .load(item.getShop().getUser().getAvatar())
                .into((ImageView) helper.getView(R.id.civ_avatar));

        helper.setText(R.id.tv_nickname, item.getShop().getUser().getNick_name());
        ConfirmGoodChildAdapter confirmGoodChildAdapter = new ConfirmGoodChildAdapter(item.getCartgoods());
        ((RecyclerView) helper.getView(R.id.rv_shop_goods)).setAdapter(confirmGoodChildAdapter);
        ((RecyclerView) helper.getView(R.id.rv_shop_goods)).setLayoutManager(new LinearLayoutManager(helper.itemView.getContext()));
        confirmGoodChildAdapter.setCheckChangeListener(new ConfirmGoodChildAdapter.ChildNumChangeListener() {
            @Override
            public void checkChange() {
                if(childCheckChangeListener!=null){
                    childCheckChangeListener.checkChange();
                }
            }
        });

    }




}

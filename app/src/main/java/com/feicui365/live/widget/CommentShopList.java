package com.feicui365.live.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lxj.xpopup.core.BottomPopupView;
import com.feicui365.live.R;

import com.feicui365.live.shop.entity.Good;
import com.feicui365.live.ui.adapter.ShopItemAdapter;

import java.util.List;


public class CommentShopList extends BottomPopupView implements View.OnClickListener {
    TextView tv_shop_num;
    RecyclerView rv_shop_list;
    List<Good> shopItems;


    //表情结束
    public CommentShopList(@NonNull Context context, List<Good> shopItems) {
        super(context);
        this.shopItems = shopItems;
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_shop_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        if(shopItems==null){

            dismiss();
            return;
        }
        if(shopItems.size()==0){
            dismiss();
            return;
        }
        tv_shop_num = findViewById(R.id.tv_shop_num);
        rv_shop_list = findViewById(R.id.rv_shop_list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        rv_shop_list.setLayoutManager(linearLayoutManager);
        rv_shop_list.setAdapter(new ShopItemAdapter(shopItems,getContext()));
        tv_shop_num.setText("共 "+shopItems.size()+" 件商品");
    }


    @Override
    public void onClick(View v) {

    }
}
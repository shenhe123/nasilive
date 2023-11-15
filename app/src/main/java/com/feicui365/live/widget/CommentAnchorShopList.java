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

import com.feicui365.live.ui.adapter.ShopAnchorItemAdapter;

import java.util.List;


public class CommentAnchorShopList extends BottomPopupView implements View.OnClickListener {
    TextView tv_shop_num;
    RecyclerView rv_shop_list;
    List<Good> shopItems;
    ShopAnchorItemAdapter shopItemAdapter;
    Context context;

    //表情结束
    public CommentAnchorShopList(@NonNull Context context, List<Good> shopItems) {
        super(context);
        this.shopItems = shopItems;
        this.context=context;
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_shop_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        if (shopItems == null) {

            dismiss();
            return;
        }
        if (shopItems.size() == 0) {
            dismiss();
            return;
        }
        tv_shop_num = findViewById(R.id.tv_shop_num);
        rv_shop_list = findViewById(R.id.rv_shop_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_shop_list.setLayoutManager(linearLayoutManager);
     //   shopItemAdapter = new ShopAnchorItemAdapter(shopItems, getContext(),);
        rv_shop_list.setAdapter(shopItemAdapter);
        shopItemAdapter.setUpdate(new ShopAnchorItemAdapter.UpDate() {
            @Override
            public void upDate(Good shopItem, int postion) {
                for (int i=0;i<shopItems.size();i++){
                    if(shopItems.get(i).getId().equals(shopItem.getId())){
                        shopItems.get(i).setLive_explaining(shopItem.getLive_explaining());
                     //   ((LivePushActivity)getContext()).sendShopItem(shopItem);
                    }else {
                        shopItems.get(i).setLive_explaining("0");
                    }
                }
                shopItemAdapter.notifyDataSetChanged();



            }
        });
        tv_shop_num.setText("共 " + shopItems.size() + " 件商品");
    }


    @Override
    public void onClick(View v) {

    }


}
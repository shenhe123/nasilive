package com.feicui365.live.shop.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.shop.entity.Subcates;

import java.util.List;

public class ClassifyChildAdapter extends BaseQuickAdapter<Subcates, BaseViewHolder> {
    public ClassifyChildAdapter(@Nullable List<Subcates> data) {
        super(R.layout.classify_child_item, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Subcates item) {
        helper.setText(R.id.tv_title,item.getTitle());

        if (item.isChose()) {

            helper.setBackgroundRes(R.id.tv_title, R.drawable.good_color_line_chose_circle);
            helper.setTextColor(R.id.tv_title, helper.itemView.getResources().getColor(R.color.color_FF4386));
        } else {
            helper.setBackgroundRes(R.id.tv_title, R.drawable.good_color_line_unchose_circle);
            helper.setTextColor(R.id.tv_title, helper.itemView.getResources().getColor(R.color.color_333333));
        }

    }


    public void change( Subcates item){
        for(int i=0;i<getData().size();i++){
            if(getData().get(i).getId()==item.getId()){
                getData().get(i).setChose(true);
            }else{
                getData().get(i).setChose(false);
            }
        }
        notifyDataSetChanged();
    }

}

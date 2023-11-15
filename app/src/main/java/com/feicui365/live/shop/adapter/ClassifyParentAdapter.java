package com.feicui365.live.shop.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.shop.entity.ClassIfy;


import java.util.List;

public class ClassifyParentAdapter extends BaseQuickAdapter<ClassIfy, BaseViewHolder> {
    public ClassifyParentAdapter( @Nullable List<ClassIfy> data) {
        super(R.layout.classify_item, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ClassIfy item) {
        helper.setText(R.id.tv_title,item.getTitle());
        if(item.isChose()){
            helper.setGone(R.id.view_chose,true);
            helper.getView(R.id.rl_root).setBackgroundColor(Color.WHITE);
        }else{
            helper.setGone(R.id.view_chose,false);
            helper.getView(R.id.rl_root).setBackgroundColor(Color.TRANSPARENT);
        }


    }

    public void change( ClassIfy item){
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

package com.feicui365.live.live.adapter;



import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.feicui365.live.R;
import com.feicui365.live.base.MyApp;
import com.feicui365.live.live.bean.BaseGuardianBuyInfo;

import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class LiveGuardianBuyListAdapter extends BaseQuickAdapter<BaseGuardianBuyInfo, BaseViewHolder> {

    public LiveGuardianBuyListAdapter() {
        super(R.layout.item_live_buy_guardian);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, BaseGuardianBuyInfo bean) {
        helper.setText(R.id.tv_type,bean.getTitle());
        helper.setText(R.id.tv_tips,bean.getTips());
        helper.setText(R.id.tv_gold,String.valueOf(bean.getGold()));

        if(bean.isCheck()){
            helper.setImageResource(R.id.iv_check,R.drawable.bg_guardian_chose_pre);
            helper.setTextColor(R.id.tv_gold_text, MyApp.getInstance().getResources().getColor(R.color.white));
            helper.setTextColor(R.id.tv_gold,MyApp.getInstance().getResources().getColor(R.color.white));
            helper.setTextColor(R.id.tv_type,MyApp.getInstance().getResources().getColor(R.color.white));
            helper.setTextColor(R.id.tv_tips,MyApp.getInstance().getResources().getColor(R.color.white));
        }else{
            helper.setImageResource(R.id.iv_check,R.drawable.bg_guardian_chose);
            helper.setTextColor(R.id.tv_gold_text,MyApp.getInstance().getResources().getColor(R.color.color_FF8B02));
            helper.setTextColor(R.id.tv_gold,MyApp.getInstance().getResources().getColor(R.color.color_FF8B02));
            helper.setTextColor(R.id.tv_type,MyApp.getInstance().getResources().getColor(R.color.black));
            helper.setTextColor(R.id.tv_tips,MyApp.getInstance().getResources().getColor(R.color.color_2A2A2A));
        }


    }

    public void setCheck(int postion) {
        for (int i = 0; i < getData().size(); i++) {
            if (postion == i) {
                getData().get(i).setCheck(true);
            } else {
                getData().get(i).setCheck(false);
            }
        }
        notifyDataSetChanged();
    }
}

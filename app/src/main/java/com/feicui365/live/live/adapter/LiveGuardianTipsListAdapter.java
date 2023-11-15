package com.feicui365.live.live.adapter;

import android.widget.ImageView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.feicui365.live.R;
import com.feicui365.live.base.MyApp;
import com.feicui365.live.live.bean.BaseGuardianTipsInfo;

import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class LiveGuardianTipsListAdapter extends BaseQuickAdapter<BaseGuardianTipsInfo, BaseViewHolder> {

    public LiveGuardianTipsListAdapter() {
        super(R.layout.item_live_tips_guardian);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, BaseGuardianTipsInfo bean) {
        helper.setText(R.id.tv_title, bean.getType());
        helper.setText(R.id.tv_tips, bean.getTips());

        helper.setImageResource(R.id.iv_icon, bean.getIcon());


        if (bean.isCheck()) {
            ((ImageView) helper.getView(R.id.iv_icon)).setImageAlpha(125);
            helper.setTextColor(R.id.tv_title, MyApp.getInstance().getResources().getColor(R.color.black50));
            helper.setTextColor(R.id.tv_tips, MyApp.getInstance().getResources().getColor(R.color.color_gray_939393_50));
        } else {
            ((ImageView) helper.getView(R.id.iv_icon)).setImageAlpha(255);
            helper.setTextColor(R.id.tv_title, MyApp.getInstance().getResources().getColor(R.color.black));
            helper.setTextColor(R.id.tv_tips, MyApp.getInstance().getResources().getColor(R.color.color_939393));

        }


    }

    public void setType(int type) {
        //0年,1月,2周
        switch (type) {
            case 0:
                getData().get(2).setCheck(false);
                getData().get(3).setCheck(false);
                break;
            case 1:
                getData().get(2).setCheck(false);
                getData().get(3).setCheck(true);
                break;
            case 2:
                getData().get(2).setCheck(true);
                getData().get(3).setCheck(true);
                break;
        }
        notifyDataSetChanged();

    }


}

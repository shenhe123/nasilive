package com.feicui365.live.live.adapter;

import android.view.View;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.feicui365.live.R;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.live.utils.GlideUtils;
import com.feicui365.live.model.entity.Guardian;
import com.feicui365.live.util.LevelUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *
 */
public class GuardianRankListAdapter extends BaseQuickAdapter<Guardian, BaseViewHolder> {

    int type;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public GuardianRankListAdapter(List<Guardian> data, int type) {
        super(R.layout.guardian_item, data);

        this.type = type;


    }


    @Override
    protected void convert(@NotNull BaseViewHolder helper, Guardian bean) {


        if (type == 1) {
            switch (helper.getLayoutPosition()) {
                case 1:
                    helper.setImageResource(R.id.iv_top, R.drawable.ic_guardian_no1);
                    break;
                case 2:
                    helper.setImageResource(R.id.iv_top, R.drawable.ic_guardian_no2);
                    break;
                case 3:
                    helper.setImageResource(R.id.iv_top, R.drawable.ic_guardian_no3);
                    break;
                default:
                    helper.setImageResource(R.id.iv_top, 0);
                    break;
            }
        } else {
            helper.setImageResource(R.id.iv_top, 0);

        }


        GlideUtils.setImage(mLayoutInflater.getContext(), bean.getUser().getAvatar(), R.mipmap.moren, helper.getView(R.id.civ_avatar));
        int vipLevel = ArmsUtils.getVipLevel2(bean.getUser().getVip_level(), bean.getUser().getVip_date());
        if (vipLevel == 0) {
            helper.getView(R.id.iv_vip).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.iv_vip).setVisibility(View.VISIBLE);
            helper.setImageResource(R.id.iv_vip, ArmsUtils.getVipLevelIcon(bean.getUser().getVip_level(), bean.getUser().getVip_date()));
        }
        helper.setImageResource(R.id.iv_user_level, LevelUtils.getUserLevel(bean.getUser().getUser_level()));
        helper.setText(R.id.tv_nickname, bean.getUser().getNick_name());
        helper.setText(R.id.tv_coin, mLayoutInflater.getContext().getString(R.string.st_live_guardian_intimacy, String.valueOf(bean.getIntimacy_week())));


    }
}

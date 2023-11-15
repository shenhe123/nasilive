package com.feicui365.live.live.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.feicui365.live.R;
import com.feicui365.live.live.utils.GlideUtils;
import com.feicui365.live.model.entity.ContributeRank;

import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class LivePageRankAdaper extends BaseQuickAdapter<ContributeRank, BaseViewHolder> {
    public LivePageRankAdaper() {
        super(R.layout.live_user_page_rank_item);

    }


    @Override
    protected void convert(@NotNull BaseViewHolder helper, ContributeRank bean) {
        switch (helper.getLayoutPosition()) {
            case 0:
                helper.setBackgroundRes(R.id.rl_avatar, R.mipmap.no1);

                break;
            case 1:
                helper.setBackgroundRes(R.id.rl_avatar, R.mipmap.no2);

                break;
            case 2:
                helper.setBackgroundRes(R.id.rl_avatar, R.mipmap.no);

                break;
            default:
                helper.setBackgroundRes(R.id.rl_avatar, 0);

                break;
        }


        GlideUtils.setImage(mLayoutInflater.getContext(), bean.getUser().getAvatar(), R.mipmap.moren, helper.getView(R.id.civ_avatar));


    }
}

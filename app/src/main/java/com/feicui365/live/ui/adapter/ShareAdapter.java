package com.feicui365.live.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.model.entity.ShareBean;

import java.util.List;

public class ShareAdapter extends BaseQuickAdapter<ShareBean, BaseViewHolder> {
    public ShareAdapter(@Nullable List<ShareBean> data) {
        super(R.layout.dialog_share_item, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ShareBean item) {
        helper.setImageResource(R.id.iv_icon,item.getRes());
        helper.setText(R.id.tv_title,item.getTitle());
    }


}

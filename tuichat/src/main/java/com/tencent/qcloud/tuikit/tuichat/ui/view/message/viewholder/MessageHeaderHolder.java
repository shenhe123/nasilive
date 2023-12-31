package com.tencent.qcloud.tuikit.tuichat.ui.view.message.viewholder;

import android.view.View;
import android.widget.LinearLayout;

import android.support.v7.widget.RecyclerView;

import com.tencent.qcloud.tuikit.tuichat.bean.message.TUIMessageBean;

public class MessageHeaderHolder extends MessageBaseHolder {

    private boolean mLoading;

    public MessageHeaderHolder(View itemView) {
        super(itemView);
    }

    @Override
    public int getVariableLayout() {
        return 0;
    }

    public void setLoadingStatus(boolean loading) {
        mLoading = loading;
    }

    @Override
    public void layoutViews(TUIMessageBean msg, int position) {
        RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        if (mLoading) {
            param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            param.width = LinearLayout.LayoutParams.MATCH_PARENT;
            itemView.setVisibility(View.VISIBLE);
        } else {
            param.height = 0;
            param.width = 0;
            itemView.setVisibility(View.GONE);
        }
        itemView.setLayoutParams(param);
    }
}

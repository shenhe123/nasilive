package com.tencent.qcloud.tuikit.tuiconversation.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import android.support.v7.widget.SimpleItemAnimator;
import android.util.AttributeSet;
import android.view.View;

import com.tencent.qcloud.tuicore.component.CustomLinearLayoutManager;
import com.tencent.qcloud.tuikit.tuiconversation.bean.ConversationInfo;
import com.tencent.qcloud.tuikit.tuiconversation.ui.interfaces.IConversationListAdapter;
import com.tencent.qcloud.tuikit.tuiconversation.ui.interfaces.IConversationListLayout;
import com.tencent.qcloud.tuikit.tuiconversation.presenter.ConversationPresenter;

public class ConversationListLayout extends RecyclerView implements IConversationListLayout {

    private ConversationListAdapter mAdapter;
    private ConversationPresenter presenter;

    public ConversationListLayout(Context context) {
        super(context);
        init();
    }

    public ConversationListLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ConversationListLayout(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setPresenter(ConversationPresenter presenter) {
        this.presenter = presenter;
    }

    public void init() {
        setLayoutFrozen(false);
        setItemViewCacheSize(0);
        setHasFixedSize(true);
        setFocusableInTouchMode(false);
        CustomLinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        setLayoutManager(linearLayoutManager);
        SimpleItemAnimator animator = (SimpleItemAnimator) getItemAnimator();
        if (animator != null) {
            animator.setSupportsChangeAnimations(false);
        }
    }

    @Override
    public void setBackground(int resId) {
        setBackgroundColor(resId);
    }

    @Override
    public void disableItemUnreadDot(boolean flag) {
        mAdapter.disableItemUnreadDot(flag);
    }

    @Override
    public void setItemAvatarRadius(int radius) {
        mAdapter.setItemAvatarRadius(radius);
    }

    @Override
    public void setItemTopTextSize(int size) {
        mAdapter.setItemTopTextSize(size);
    }

    @Override
    public void setItemBottomTextSize(int size) {
        mAdapter.setItemBottomTextSize(size);
    }

    @Override
    public void setItemDateTextSize(int size) {
        mAdapter.setItemDateTextSize(size);
    }

    @Override
    public ConversationListLayout getListLayout() {
        return this;
    }

    @Override
    public ConversationListAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setAdapter(IConversationListAdapter adapter) {
        if (adapter instanceof ConversationListAdapter) {
            super.setAdapter((ConversationListAdapter) adapter);
            mAdapter = (ConversationListAdapter) adapter;
        }
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        mAdapter.setOnItemClickListener(listener);
    }

    @Override
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mAdapter.setOnItemLongClickListener(listener);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int viewType, ConversationInfo messageInfo);
    }

    public interface OnItemLongClickListener {
        void OnItemLongClick(View view, ConversationInfo messageInfo);
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
            if (layoutManager == null) {
                return;
            }
            int lastPosition = layoutManager.findLastCompletelyVisibleItemPosition();

            if (mAdapter != null) {
                if (lastPosition == mAdapter.getItemCount() - 1 && !isLoadCompleted()) {
                    mAdapter.onLoadingStateChanged(true);
                    if (presenter != null) {
                        presenter.loadMoreConversation();
                    }
                }
            }
        }
    }

    public void loadConversation(long nextSeq) {
        if (presenter != null) {
            presenter.loadConversation(nextSeq);
        }
    }

    boolean isLoadCompleted(){
        if (presenter != null) {
            return presenter.isLoadFinished();
        }
        return false;
    }
}
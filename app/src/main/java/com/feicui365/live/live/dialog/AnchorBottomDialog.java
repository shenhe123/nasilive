package com.feicui365.live.live.dialog;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseDialogFragment;
import com.feicui365.live.contract.LivePushContrat;
import com.feicui365.live.im.IMDialog;
import com.feicui365.live.interfaces.OnAnchorBottomClickListener;
import com.feicui365.live.live.adapter.LiveAnchorBottomAdapter;
import com.feicui365.live.live.bean.BottomItem;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.live.utils.BaseInitUtils;
import com.feicui365.live.presenter.LivePushPresenter;
import com.feicui365.live.util.MyUserInstance;

import java.util.ArrayList;

import butterknife.BindView;

/**
 *
 */
@SuppressLint("ValidFragment")
public class AnchorBottomDialog extends BaseDialogFragment implements LivePushContrat.View {

    @BindView(R.id.rv_anchor_live_bottom)
    RecyclerView mRvList;
    LiveAnchorBottomAdapter mBottomAdapter;
    boolean isLink = false;
    ArrayList<BottomItem> bottomItems;
    OnAnchorBottomClickListener onAnchorBottomClickListener;
    LivePushPresenter mPresenter;
    String mAnchorId;

    public void setmAnchorId(String mAnchorId) {
        this.mAnchorId = mAnchorId;
    }

    public AnchorBottomDialog(boolean isLink) {
        this.isLink = isLink;
    }

    public void setOnAnchorBottomClickListener(OnAnchorBottomClickListener onAnchorBottomClickListener) {
        this.onAnchorBottomClickListener = onAnchorBottomClickListener;
    }

    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY));
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = new LivePushPresenter();
        mPresenter.attachView(this);
        initList();

    }

    private void initList() {
        bottomItems = BaseInitUtils.getAnchorBottomList();
        if (isLink) {
            bottomItems.get(2).setName(getString(R.string.st_link_off));
            bottomItems.get(2).setChose(true);
        } else {
            bottomItems.get(2).setName(getString(R.string.st_link_on));
            bottomItems.get(2).setChose(false);
        }

        mBottomAdapter = new LiveAnchorBottomAdapter(bottomItems);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 5);
        mRvList.setLayoutManager(layoutManager);
        mRvList.setAdapter(mBottomAdapter);
        mBottomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!ArmsUtils.canClick()) {
                    return;
                }
                //IM 商品 开关连麦 PK 房间管理
                switch (position) {
                    case 0:
                        IMDialog imDialog = new IMDialog();
                        imDialog.show(getChildFragmentManager(), "");

                        break;
                    case 1:
                        LiveShopDialog liveShopDialog=new LiveShopDialog(0, MyUserInstance.getInstance().getUserinfo().getId());
                        liveShopDialog.show(getChildFragmentManager(), "");
                        break;
                    case 2:
                        if (isLink) {
                            mPresenter.setLinkOnOff("0");

                            bottomItems.get(2).setName(getString(R.string.st_link_on));
                            bottomItems.get(2).setChose(false);
                            isLink = false;
                        } else {
                            mPresenter.setLinkOnOff("1");
                            bottomItems.get(2).setName(getString(R.string.st_link_off));
                            bottomItems.get(2).setChose(true);
                            isLink = true;
                        }

                        mBottomAdapter.notifyItemChanged(2);
                        if (onAnchorBottomClickListener != null) {
                            onAnchorBottomClickListener.onLinkClick();
                        }

                        break;
                    case 3:
                        if (onAnchorBottomClickListener != null) {
                            onAnchorBottomClickListener.onPkClick();
                        }
                        dismiss();
                        break;
                    case 4:
                        if (mAnchorId == null) {
                            return;
                        }
                        LiveRoomManagerDialog dialog = new LiveRoomManagerDialog(mAnchorId, 1);
                        dialog.show(getChildFragmentManager(), "");
                        break;

                }

            }
        });

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {

    }


    @Override
    protected int getLayoutId() {
        return R.layout.dialog_anchor_live_bottom;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.transparentBackgroundDiaolg;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;

        params.height = ArmsUtils.dip2px(getContext(), 100);


        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }
}

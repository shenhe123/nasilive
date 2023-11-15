package com.feicui365.live.live.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gcssloop.widget.PagerGridLayoutManager;
import com.gcssloop.widget.PagerGridSnapHelper;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.contract.LivePushContrat;
import com.feicui365.live.eventbus.OnGiftChoseBus;
import com.feicui365.live.interfaces.OnGiftClickListener;
import com.feicui365.live.live.adapter.GiftAdapter;
import com.feicui365.live.model.entity.GiftInfo;
import com.feicui365.live.presenter.LivePushPresenter;
import com.feicui365.live.util.ComputerUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator;

/**
 *
 */
@SuppressLint("ValidFragment")
public class NormalGiftFragment extends BaseMvpFragment<LivePushPresenter> implements LivePushContrat.View {

    @BindView(R.id.rv_gift)
    RecyclerView mRvGift;
    @BindView(R.id.ci_gift)
    CircleIndicator mCiGift;
    boolean isVertical = false;
    GiftAdapter mGiftAdapter;
    GiftInfo mChoseBean;
    int mCount = 1;

    int type=0;

    public NormalGiftFragment(boolean isVertical, int type) {
        this.isVertical = isVertical;
        this.type = type;
    }

    public NormalGiftFragment(boolean isVertical) {
        this.isVertical = isVertical;

    }



    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initView(View view) {
        mPresenter = new LivePushPresenter();
        mPresenter.attachView(this);
        mPresenter.getGiftList();
    }

    @Override
    public void setGiftList(ArrayList<GiftInfo> bean) {

        initList(bean);
    }

    private void initList(ArrayList<GiftInfo> bean) {
        mGiftAdapter = new GiftAdapter();
        mGiftAdapter.addData(bean);

        PagerGridLayoutManager layoutManager;
        int result;
        if (isVertical) {
            layoutManager = new PagerGridLayoutManager(
                    2, 5, PagerGridLayoutManager.HORIZONTAL);
            result = ComputerUtils.divUp(bean.size(), 10, 0);
        } else {
            layoutManager = new PagerGridLayoutManager(
                    1, 8, PagerGridLayoutManager.HORIZONTAL);
            result = ComputerUtils.divUp(bean.size(), 8, 0);
        }


        mRvGift.setLayoutManager(layoutManager);
        mRvGift.setAdapter(mGiftAdapter);

        mGiftAdapter.bindToRecyclerView(mRvGift);
        PagerGridSnapHelper pageSnapHelper = new PagerGridSnapHelper();
        pageSnapHelper.attachToRecyclerView(mRvGift);


        layoutManager.setPageListener(new PagerGridLayoutManager.PageListener() {
            @Override
            public void onPageSizeChanged(int pageSize) {

            }

            @Override
            public void onPageSelect(int pageIndex) {
                mCiGift.animatePageSelected(pageIndex);
            }
        });
        mCiGift.createIndicators(result, 0);

        mGiftAdapter.setOnGiftClickListener(new OnGiftClickListener() {
            @Override
            public void onGiftClick(GiftInfo bean) {
                mChoseBean=bean;
                EventBus.getDefault().post(new OnGiftChoseBus(bean));
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_normal_gift;
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

    public GiftInfo getChoseGift(){
        return mChoseBean;
    }
}

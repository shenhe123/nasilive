package com.feicui365.live.live.fragment;

import android.view.View;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.contract.LivePushContrat;
import com.feicui365.live.presenter.LivePushPresenter;


/**
 *
 */
public class LivePushNothingFragment extends BaseMvpFragment<LivePushPresenter> implements LivePushContrat.View {

    /**
     * 需要分出来的点
     * 1,头像
     * 2,名字
     * 3,人气
     * 4,在线人数
     * 5,贡献榜
     * 6,守护人
     * 7,收入钻石
     * 8,聊天信息
     * 9,PK相关
     */





    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.live_push_fragment_2;
    }

    /**
     * 注释
     * 初始化直播时间
     */
    private void initLiveTime() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //关闭计时

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
}

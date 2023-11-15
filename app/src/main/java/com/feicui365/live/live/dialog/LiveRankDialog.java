package com.feicui365.live.live.dialog;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;




import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseDialogFragment2;
import com.feicui365.live.contract.LivePushContrat;
import com.feicui365.live.live.fragment.LiveRankFragment;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.live.weight.CommonNavigatorUtils;
import com.feicui365.live.live.weight.MySimplePagerTitleView;
import com.feicui365.live.live.weight.PagerFragmentAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;

import static android.view.Gravity.CENTER_HORIZONTAL;

/**
 *
 */
@SuppressLint("ValidFragment")
public class LiveRankDialog extends BaseDialogFragment2 implements LivePushContrat.View {

    @BindView(R.id.ll_viewpager_root)
    LinearLayout mViewpagerRoot;
    @BindView(R.id.magic_tab)
    MagicIndicator magicHome;

    @BindView(R.id.vp_tab_2)
    ViewPager vpHome;
    PagerFragmentAdapter fragmentAdapter;
    ArrayList<Fragment> mFragments;

    ArrayList<String> mTitles;
    String mLiveId;

    public LiveRankDialog(String mLiveId) {
        this.mLiveId = mLiveId;
    }

    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY));
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
        return R.layout.tab_viewpager_layout_2;
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

        params.height = ArmsUtils.dip2px(getContext(), 370);
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

/*    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }*/

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewpagerRoot.setBackgroundResource(R.drawable.shape_corner_white_half_15);
        initFragment();
        initTab();
    }

    private void initTab() {
        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        p.gravity = CENTER_HORIZONTAL;
        magicHome.setLayoutParams(p);


        mTitles = new ArrayList<>();


        mTitles.add(getString(R.string.st_live_rank_list));

        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdjustMode(true);

        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitles == null ? 0 : mTitles.size();
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                MySimplePagerTitleView simplePagerTitleView = new MySimplePagerTitleView(context);
                CommonNavigatorUtils.initTitleView(simplePagerTitleView, context, mTitles.get(index),
                        R.color.black, R.color.black, 20);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vpHome.setCurrentItem(index);
                    }
                });

                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {

                return null;
            }


        });
        magicHome.setNavigator(commonNavigator);

        ViewPagerHelper.bind(magicHome, vpHome);
        vpHome.setOffscreenPageLimit(mTitles.size());


    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(new LiveRankFragment(mLiveId));
        fragmentAdapter = new PagerFragmentAdapter(getChildFragmentManager(), mFragments);
        vpHome.setAdapter(fragmentAdapter);
    }
}

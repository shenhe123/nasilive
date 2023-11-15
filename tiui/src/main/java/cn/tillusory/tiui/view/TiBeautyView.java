package cn.tillusory.tiui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.thread.EventThread;
import com.shizhefei.view.indicator.FragmentListPageAdapter;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;
import java.util.List;

import cn.tillusory.sdk.TiSDKManager;
import cn.tillusory.sdk.bean.TiTypeEnum;
import cn.tillusory.tiui.R;
import cn.tillusory.tiui.custom.TiSharePreferences;
import cn.tillusory.tiui.fragment.TiBeautyFragment;
import cn.tillusory.tiui.fragment.TiDistortionFragment;
import cn.tillusory.tiui.fragment.TiFaceTrimFragment;
import cn.tillusory.tiui.fragment.TiFilterFragment;
import cn.tillusory.tiui.fragment.TiGiftFragment;
import cn.tillusory.tiui.fragment.TiGreenScreenFragment;
import cn.tillusory.tiui.fragment.TiInteractionFragment;
import cn.tillusory.tiui.fragment.TiMakeupFragment;
import cn.tillusory.tiui.fragment.TiMaskFragment;
import cn.tillusory.tiui.fragment.TiQuickBeautyFragment;
import cn.tillusory.tiui.fragment.TiRockFragment;
import cn.tillusory.tiui.fragment.TiStickerFragment;
import cn.tillusory.tiui.fragment.TiWatermarkFragment;
import cn.tillusory.tiui.model.RxBusAction;
import cn.tillusory.tiui.model.TiMode;

/**
 * Created by Anko on 2018/5/12.
 * Copyright (c) 2018-2019 拓幻科技 - tillusory.cn. All rights reserved.
 */
public class TiBeautyView extends LinearLayout {

    private TiSDKManager tiSDKManager;
    private ScrollIndicatorView tiIndicatorView;

    private TiBarView tiBarView;
    private TiMakeupView tiMakeupView;

    private LinearLayout tiBeautyLL;
    private LinearLayout tiEnableLL;
    private TextView tiEnableTV;
    private ImageView tiEnableIV;
    private View tiDividerV;
    private ViewPager tiViewPager;

    //    private List<TiTypeEnum> tiTabs = new ArrayList<>();
    private List<Integer> tiTabs = new ArrayList<>();

    private boolean isBeautyEnable = false;
    private boolean isFaceTrimEnable = false;
    private boolean isMakeupEnable = false;

    private int tiBeautyMode = TiMode.MODE_BEAUTY;
    private IndicatorViewPager.IndicatorFragmentPagerAdapter fragmentPagerAdapter;

    public TiBeautyView(Context context) {
        super(context);
    }

    public TiBeautyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TiBeautyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TiBeautyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public TiBeautyView init(@NonNull TiSDKManager tiSDKManager) {
        this.tiSDKManager = tiSDKManager;

        RxBus.get().register(this);

        initView();

        initData();

        return this;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        RxBus.get().unregister(this);
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_ti_beauty, this);

        tiBarView = findViewById(R.id.tiBarView);
        tiBarView.init(tiSDKManager);

        tiMakeupView = findViewById(R.id.tiMakeupView);
        tiMakeupView.init();

        tiIndicatorView = findViewById(R.id.tiIndicatorView);

        tiBeautyLL = findViewById(R.id.tiBeautyLL);
        tiEnableLL = findViewById(R.id.tiEnableLL);
        tiEnableTV = findViewById(R.id.tiEnableTV);
        tiEnableIV = findViewById(R.id.tiEnableIV);
        tiDividerV = findViewById(R.id.tiDividerV);
        tiViewPager = findViewById(R.id.viewPager);
    }

    private void initData() {

        //屏蔽点击事件
        setOnClickListener(null);

        isBeautyEnable = tiSDKManager.isBeautyEnable();
        isFaceTrimEnable = tiSDKManager.isFaceTrimEnable();

        tiEnableIV.setSelected(isBeautyEnable);
        tiEnableTV.setSelected(isBeautyEnable);
        tiEnableTV.setText(isBeautyEnable ? R.string.ti_beauty_on : R.string.ti_beauty_off);

        tiEnableLL.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tiBeautyMode == TiMode.MODE_BEAUTY) {
                    isBeautyEnable = !isBeautyEnable;
                    tiSDKManager.setBeautyEnable(isBeautyEnable);
                    tiEnableIV.setSelected(isBeautyEnable);
                    tiEnableTV.setSelected(isBeautyEnable);
                    tiEnableTV.setText(isBeautyEnable ? R.string.ti_beauty_on : R.string.ti_beauty_off);
                    tiBarView.selectBeauty();
                } else if (tiBeautyMode == TiMode.MODE_FACE_TRIM) {
                    isFaceTrimEnable = !isFaceTrimEnable;
                    tiSDKManager.setFaceTrimEnable(isFaceTrimEnable);
                    tiEnableIV.setSelected(isFaceTrimEnable);
                    tiEnableTV.setSelected(isFaceTrimEnable);
                    tiEnableTV.setText(isFaceTrimEnable ? R.string.ti_face_trim_on : R.string.ti_face_trim_off);
                    tiBarView.selectFaceTrim();
                } else if (tiBeautyMode == TiMode.MODE_MAKEUP) {
                    isMakeupEnable = !isMakeupEnable;
                    tiSDKManager.enableMakeup(isMakeupEnable);
                    TiSharePreferences.getInstance().putMakeupEnable(isMakeupEnable);
                    tiEnableIV.setSelected(isMakeupEnable);
                    tiEnableTV.setSelected(isMakeupEnable);
                    tiEnableTV.setText(isMakeupEnable ? R.string.makeup_on : R.string.makeup_off);
                }
            }
        });

        tiTabs.clear();
        tiTabs.add(TiTypeEnum.Beauty.getStringId());
        tiEnableIV.setSelected(isBeautyEnable);
        tiEnableTV.setSelected(isBeautyEnable);
        tiEnableTV.setText(isBeautyEnable ? R.string.ti_beauty_on : R.string.ti_beauty_off);
        RxBus.get().post(RxBusAction.ACTION_SKIN_WHITENING);

        tiViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tiIndicatorView.getIndicatorAdapter().notifyDataSetChanged();
                if (tiBeautyMode == TiMode.MODE_FILTER) {
                    if (position == 0) {
                        tiBarView.selectFilter();
                    } else {
                        tiBarView.hideSeekBar();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        tiIndicatorView.setOnTransitionListener(new OnTransitionTextListener()
                .setColor(getResources().getColor(R.color.ti_selected), getResources().getColor(R.color.ti_unselected)));
        tiIndicatorView.setSplitAuto(false);
        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(tiIndicatorView, tiViewPager);
        indicatorViewPager.setPageOffscreenLimit(6);
        fragmentPagerAdapter = new IndicatorViewPager.IndicatorFragmentPagerAdapter(((FragmentActivity) getContext()).getSupportFragmentManager()) {
                    @Override
                    public int getCount() {
                        return tiTabs.size();
                    }

                    @Override
                    public View getViewForTab(int position, View convertView, ViewGroup container) {
                        if (convertView == null) {
                            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_ti_tab, container, false);
                        }
                        if (position == 0) {
                            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) convertView.getLayoutParams();
                            p.setMargins((int) (convertView.getContext().getResources().getDisplayMetrics().density * 17 + 0.5f), 0, 0, 0);
                            convertView.requestLayout();
                        } else {
                            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) convertView.getLayoutParams();
                            p.setMargins(0, 0, 0, 0);
                            convertView.requestLayout();
                        }

//                        ((TextView) convertView).setText(tiTabs.get(position).getString(convertView.getContext()));
                        ((TextView) convertView).setText(convertView.getContext().getResources().getString(tiTabs.get(position)));
                        return convertView;
                    }

                    @Override
                    public int getItemPosition(Object object) {
                        return FragmentListPageAdapter.POSITION_NONE;
                    }

                    @Override
                    public Fragment getFragmentForPage(int position) {
                        if (tiBeautyMode == TiMode.MODE_BEAUTY) {
                            return new TiBeautyFragment();
                        } else if (tiBeautyMode == TiMode.MODE_FACE_TRIM) {
                            return new TiFaceTrimFragment();
                        } else if (tiBeautyMode == TiMode.MODE_CUTE) {
                            switch (position) {
                                case 0:
                                    return new TiStickerFragment().setTiSDKManager(tiSDKManager);
                                case 1:
                                    return new TiInteractionFragment().setTiSDKManager(tiSDKManager);
                                case 2:
                                    return new TiGiftFragment().setTiSDKManager(tiSDKManager);
                                case 3:
                                    return new TiDistortionFragment().setTiSDKManager(tiSDKManager);
                                case 4:
                                    return new TiMaskFragment().setTiSDKManager(tiSDKManager);
                                case 5:
                                    return new TiGreenScreenFragment().setTiSDKManager(tiSDKManager);
                            }
                        } else if (tiBeautyMode == TiMode.MODE_FILTER) {
                            switch (position) {
                                case 0:
                                    return new TiFilterFragment();
                                case 1:
                                    return new TiRockFragment().setTiSDKManager(tiSDKManager);
                                case 2:
                                    return new TiWatermarkFragment().setTiSDKManager(tiSDKManager);
                            }
                        } else if (tiBeautyMode == TiMode.MODE_QUICK_BEAUTY) {
                            return new TiQuickBeautyFragment();
                        } else if (tiBeautyMode == TiMode.MODE_MAKEUP) {
                            return new TiMakeupFragment();
                        }
                        return null;
                    }
                };

        indicatorViewPager.setAdapter(fragmentPagerAdapter);
    }

    public void refreshData(int mode) {
        tiBeautyMode = mode;
        tiTabs.clear();
        if (tiBeautyMode == TiMode.MODE_BEAUTY) {
            tiTabs.add(TiTypeEnum.Beauty.getStringId());

            tiEnableLL.setVisibility(VISIBLE);
            tiDividerV.setVisibility(VISIBLE);

            tiBarView.selectBeauty();

            isBeautyEnable = tiSDKManager.isBeautyEnable();
            tiEnableIV.setSelected(isBeautyEnable);
            tiEnableTV.setSelected(isBeautyEnable);
            tiEnableTV.setText(isBeautyEnable ? R.string.ti_beauty_on : R.string.ti_beauty_off);
            RxBus.get().post(RxBusAction.ACTION_SKIN_WHITENING);
        } else if (tiBeautyMode == TiMode.MODE_FACE_TRIM) {
            tiTabs.add(TiTypeEnum.FaceTrim.getStringId());

            tiEnableLL.setVisibility(VISIBLE);
            tiDividerV.setVisibility(VISIBLE);

            tiBarView.selectFaceTrim();

            isFaceTrimEnable = tiSDKManager.isFaceTrimEnable();
            tiEnableIV.setSelected(isFaceTrimEnable);
            tiEnableTV.setSelected(isFaceTrimEnable);
            tiEnableTV.setText(isFaceTrimEnable ? R.string.ti_face_trim_on : R.string.ti_face_trim_off);
            RxBus.get().post(RxBusAction.ACTION_EYE_MAGNIFYING);
        } else if (tiBeautyMode == TiMode.MODE_CUTE) {
            tiTabs.add(TiTypeEnum.Sticker.getStringId());
            tiTabs.add(TiTypeEnum.Interaction.getStringId());
            tiTabs.add(TiTypeEnum.Gift.getStringId());
            tiTabs.add(TiTypeEnum.Distortion.getStringId());
            tiTabs.add(TiTypeEnum.Mask.getStringId());
            tiTabs.add(TiTypeEnum.GreenScreen.getStringId());

            tiEnableLL.setVisibility(GONE);
            tiDividerV.setVisibility(GONE);

            tiBarView.hideSeekBar();
        } else if (tiBeautyMode == TiMode.MODE_FILTER) {
            tiTabs.add(TiTypeEnum.Filter.getStringId());
            tiTabs.add(TiTypeEnum.Rock.getStringId());
            tiTabs.add(TiTypeEnum.Watermark.getStringId());

            tiEnableLL.setVisibility(GONE);
            tiDividerV.setVisibility(GONE);

            tiBarView.selectFilter();
        } else if (tiBeautyMode == TiMode.MODE_QUICK_BEAUTY) {
            tiTabs.add(R.string.quick_beauty);

            tiEnableLL.setVisibility(GONE);
            tiDividerV.setVisibility(GONE);

            tiBarView.selectQuickBeauty();
        } else if (tiBeautyMode == TiMode.MODE_MAKEUP) {
            tiTabs.add(R.string.makeup);

            tiEnableLL.setVisibility(VISIBLE);
            tiDividerV.setVisibility(VISIBLE);

            tiBarView.hideSeekBar();

            isMakeupEnable = TiSharePreferences.getInstance().isMakeupEnable();
            tiSDKManager.enableMakeup(isMakeupEnable);
            tiEnableIV.setSelected(isMakeupEnable);
            tiEnableTV.setSelected(isMakeupEnable);
            tiEnableTV.setText(isMakeupEnable ? R.string.makeup_on : R.string.makeup_off);
        }

        fragmentPagerAdapter.notifyDataSetChanged();
    }

    public boolean hideMakeupView() {
        if (tiMakeupView != null && tiMakeupView.getVisibility() == VISIBLE) {
//            tiBeautyLL.setVisibility(VISIBLE);
//            tiMakeupView.setVisibility(GONE);
            showMakeupView(RxBusAction.ACTION_MAKEUP_BACK);
            return true;
        }
        return false;
    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void showMakeupView(String action) {
        switch (action) {
            case RxBusAction.ACTION_MAKEUP_BACK:
                tiBeautyLL.setVisibility(VISIBLE);
                tiMakeupView.setVisibility(GONE);
                tiBarView.hideSeekBar();
                break;
            case RxBusAction.ACTION_BLUSHER:
                tiBeautyLL.setVisibility(GONE);
                tiMakeupView.setVisibility(VISIBLE);
                tiBarView.selectMakeupBlusher();
                break;
            case RxBusAction.ACTION_EYELASH:
                tiBeautyLL.setVisibility(GONE);
                tiMakeupView.setVisibility(VISIBLE);
                tiBarView.selectMakeupEyelash();
                break;
            case RxBusAction.ACTION_EYEBROW:
                tiBeautyLL.setVisibility(GONE);
                tiMakeupView.setVisibility(VISIBLE);
                tiBarView.selectMakeupEyebrow();
                break;
        }
    }
}

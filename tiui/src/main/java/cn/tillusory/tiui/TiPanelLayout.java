package cn.tillusory.tiui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;

import java.util.ArrayList;
import java.util.List;

import cn.tillusory.sdk.TiSDKManager;
import cn.tillusory.tiui.adapter.TiViewPagerAdapter;
import cn.tillusory.tiui.custom.TiSharePreferences;
import cn.tillusory.tiui.model.RxBusAction;
import cn.tillusory.tiui.model.TiMode;
import cn.tillusory.tiui.view.TiBeautyView;

/**
 * Created by Anko on 2018/5/12.
 * Copyright (c) 2018-2020 拓幻科技 - tillusory.cn. All rights reserved.
 */
public class TiPanelLayout extends ConstraintLayout {

    private TiSDKManager tiSDKManager;
    private ImageView tiBeautyIV;
    private TiBeautyView tiBeautyView;

    private FrameLayout tiBeautyModeContainer;
    private ViewPager tiViewPager;
    private TiViewPagerAdapter tiViewPagerAdapter;
    private LayoutInflater tiLayoutInflater;
    private List<View> tiModeViewList;
    private View tiModeIndicator1;
    private View tiModeIndicator2;
    private View tiBeauty;
    private View tiFaceTrim;
    private View tiCute;
    private View tiFilter;
    private View tiQuickBeauty;
    private View tiMakeup;
    private TextView tiInteractionHint;

  private   onDissMiss onDissMiss;

    public interface onDissMiss{
        void onDissmiss();
    }

    public TiPanelLayout.onDissMiss getOnDissMiss() {
        return onDissMiss;
    }

    public void setOnDissMiss(TiPanelLayout.onDissMiss onDissMiss) {
        this.onDissMiss = onDissMiss;
    }

    public TiPanelLayout(Context context) {
        super(context);
    }

    public TiPanelLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TiPanelLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TiPanelLayout init(@NonNull TiSDKManager tiSDKManager) {
        this.tiSDKManager = tiSDKManager;

        RxBus.get().register(this);

        TiSharePreferences.getInstance().init(getContext());

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
        LayoutInflater.from(getContext()).inflate(R.layout.layout_ti_panel, this);

        tiBeautyIV = findViewById(R.id.tiBeautyIV);

        tiBeautyView = findViewById(R.id.tiBeautyView);

        tiViewPager = findViewById(R.id.tiViewPager);
        tiLayoutInflater = LayoutInflater.from(getContext());
        View tiModePage1 = tiLayoutInflater.inflate(R.layout.view_ti_mode_page_1, null);
        View tiModePage2 = tiLayoutInflater.inflate(R.layout.view_ti_mode_page_2, null);
        tiModeIndicator1 = findViewById(R.id.ti_mode_indicator_1);
        tiModeIndicator2 = findViewById(R.id.ti_mode_indicator_2);
        tiBeauty = tiModePage1.findViewById(R.id.beauty);
        tiFaceTrim = tiModePage1.findViewById(R.id.face_trim);
        tiCute = tiModePage1.findViewById(R.id.cute);
        tiFilter = tiModePage1.findViewById(R.id.filter);
        tiQuickBeauty = tiModePage2.findViewById(R.id.quick_beauty);
        tiMakeup = tiModePage2.findViewById(R.id.makeup);

        tiModeViewList = new ArrayList<>();
        tiModeViewList.add(tiModePage1);
        tiModeViewList.add(tiModePage2);
        tiViewPagerAdapter = new TiViewPagerAdapter(tiModeViewList);

        tiBeautyModeContainer = findViewById(R.id.tiBeautyModeContainer);
        tiInteractionHint = findViewById(R.id.interaction_hint);
    }

    private void initData() {
        tiModeIndicator1.setSelected(true);
        tiViewPager.setAdapter(tiViewPagerAdapter);
        tiViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    tiModeIndicator1.setSelected(true);
                    tiModeIndicator2.setSelected(false);
                } else {
                    tiModeIndicator1.setSelected(false);
                    tiModeIndicator2.setSelected(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        tiBeautyView.init(tiSDKManager);
        tiBeautyIV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  if (tiBeautyModeContainer != null && tiBeautyModeContainer.getVisibility() == GONE) {
                    tiBeautyModeContainer.setVisibility(VISIBLE);
                }
                if (tiBeautyIV != null) {
                    tiBeautyIV.setVisibility(GONE);
                }*/

                showView();
            }
        });

        tiBeauty.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tiBeautyView.refreshData(TiMode.MODE_BEAUTY);
                if (tiBeautyView != null && tiBeautyView.getVisibility() == GONE) {
                    tiBeautyView.setVisibility(VISIBLE);
                }
            }
        });

        tiFaceTrim.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tiBeautyView.refreshData(TiMode.MODE_FACE_TRIM);
                if (tiBeautyView != null && tiBeautyView.getVisibility() == GONE) {
                    tiBeautyView.setVisibility(VISIBLE);
                }
            }
        });

        tiCute.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tiBeautyView.refreshData(TiMode.MODE_CUTE);
                if (tiBeautyView != null && tiBeautyView.getVisibility() == GONE) {
                    tiBeautyView.setVisibility(VISIBLE);
                }
            }
        });

        tiFilter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tiBeautyView.refreshData(TiMode.MODE_FILTER);
                if (tiBeautyView != null && tiBeautyView.getVisibility() == GONE) {
                    tiBeautyView.setVisibility(VISIBLE);
                }
            }
        });

        tiQuickBeauty.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tiBeautyView.refreshData(TiMode.MODE_QUICK_BEAUTY);
                if (tiBeautyView != null && tiBeautyView.getVisibility() == GONE) {
                    tiBeautyView.setVisibility(VISIBLE);
                }
            }
        });

        tiMakeup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tiBeautyView.refreshData(TiMode.MODE_MAKEUP);
                if (tiBeautyView != null && tiBeautyView.getVisibility() == GONE) {
                    tiBeautyView.setVisibility(VISIBLE);
                }
            }
        });

        //空白处隐藏面板
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.performClick();
                if(tiBeautyView.hideMakeupView()) {
                    return false;
                } else if (tiBeautyView != null && tiBeautyView.getVisibility() == VISIBLE) {
                    tiBeautyView.setVisibility(GONE);
                } else if (tiBeautyModeContainer != null && tiBeautyModeContainer.getVisibility() == VISIBLE) {
                    tiBeautyModeContainer.setVisibility(GONE);
                    /*if (tiBeautyIV != null) {
                        tiBeautyIV.setVisibility(VISIBLE);
                    }*/

                    if(onDissMiss!=null){
                        onDissMiss.onDissmiss();
                    }
                }

                return false;
            }
        });
    }

    public void showView(){
        if (tiBeautyModeContainer != null && tiBeautyModeContainer.getVisibility() == GONE) {
            tiBeautyModeContainer.setVisibility(VISIBLE);
        }
        if (tiBeautyIV != null) {
            tiBeautyIV.setVisibility(GONE);
        }
    }
    @Subscribe(thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(RxBusAction.ACTION_SHOW_INTERACTION)
            })
    public void showHint(String hint) {
        tiInteractionHint.setVisibility(TextUtils.isEmpty(hint) ? View.GONE : View.VISIBLE);
        tiInteractionHint.setText(hint);
    }
}

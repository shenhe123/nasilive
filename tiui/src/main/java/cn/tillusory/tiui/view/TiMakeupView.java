package cn.tillusory.tiui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.thread.EventThread;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.tillusory.sdk.TiSDK;
import cn.tillusory.sdk.bean.TiMakeup;
import cn.tillusory.tiui.R;
import cn.tillusory.tiui.adapter.TiBlusherAdapter;
import cn.tillusory.tiui.adapter.TiEyeBrowAdapter;
import cn.tillusory.tiui.adapter.TiEyelashAdapter;
import cn.tillusory.tiui.model.RxBusAction;
import cn.tillusory.tiui.model.TiMakeupText;

/**
 * Created by Anko on 2019-09-06.
 * Copyright (c) 2018-2020 拓幻科技 - tillusory.cn. All rights reserved.
 */
public class TiMakeupView extends LinearLayout {

    private LinearLayout tiMakeupBackLL;
    private TextView tiMakeupTV;
    private RecyclerView tiMakeupRV;
    private TiBlusherAdapter blusherAdapter;
    private TiEyelashAdapter eyelashAdapter;
    private TiEyeBrowAdapter eyeBrowAdapter;
    private List<TiMakeup> blusherList = new ArrayList<>();
    private List<TiMakeup> eyelashList = new ArrayList<>();
    private List<TiMakeup> eyebrowList = new ArrayList<>();
    private List<TiMakeupText> blusherTextList = new ArrayList<>();
    private List<TiMakeupText> eyelashTextList = new ArrayList<>();
    private List<TiMakeupText> eyebrowTextList = new ArrayList<>();

    public TiMakeupView(Context context) {
        super(context);
    }

    public TiMakeupView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TiMakeupView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TiMakeupView init() {
        RxBus.get().register(this);

        initView();

        initData();

        return this;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        RxBus.get().unregister(this);
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_ti_makeup, this);

        tiMakeupBackLL = findViewById(R.id.tiMakeupBackLL);
        tiMakeupTV = findViewById(R.id.tiMakeupTV);
        tiMakeupRV = findViewById(R.id.tiMakeupRV);
    }


    private void initData() {
        tiMakeupBackLL.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.get().post(RxBusAction.ACTION_MAKEUP_BACK);
            }
        });

        blusherList.clear();
        blusherList.add(TiMakeup.NO_MAKEUP);
        blusherList.addAll(TiMakeup.getAllMakeups(getContext()).subList(0, 6));
        blusherTextList.clear();
        blusherTextList.add(TiMakeupText.NO_MAKEUP);
        blusherTextList.addAll(Arrays.asList(TiMakeupText.values()).subList(1, 7));
        blusherAdapter = new TiBlusherAdapter(blusherList, blusherTextList);

        eyelashList.clear();
        eyelashList.add(TiMakeup.NO_MAKEUP);
        eyelashList.addAll(TiMakeup.getAllMakeups(getContext()).subList(12, 18));
        eyelashTextList.clear();
        eyelashTextList.add(TiMakeupText.NO_MAKEUP);
        eyelashTextList.addAll(Arrays.asList(TiMakeupText.values()).subList(13, 19));
        eyelashAdapter = new TiEyelashAdapter(eyelashList, eyelashTextList);

        eyebrowList.clear();
        eyebrowList.add(TiMakeup.NO_MAKEUP);
        eyebrowList.addAll(TiMakeup.getAllMakeups(getContext()).subList(6, 12));
        eyebrowTextList.clear();
        eyebrowTextList.add(TiMakeupText.NO_MAKEUP);
        eyebrowTextList.addAll(Arrays.asList(TiMakeupText.values()).subList(7, 13));
        eyeBrowAdapter = new TiEyeBrowAdapter(eyebrowList, eyebrowTextList);

        tiMakeupRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void selectMakeup(String action) {
        switch (action) {
            case RxBusAction.ACTION_BLUSHER:
                tiMakeupTV.setText(R.string.blusher);
                tiMakeupRV.setAdapter(blusherAdapter);
                break;
            case RxBusAction.ACTION_EYELASH:
                tiMakeupTV.setText(R.string.eyelash);
                tiMakeupRV.setAdapter(eyelashAdapter);
                break;
            case RxBusAction.ACTION_EYEBROW:
                tiMakeupTV.setText(R.string.eyebrow);
                tiMakeupRV.setAdapter(eyeBrowAdapter);
                break;
        }
    }
}

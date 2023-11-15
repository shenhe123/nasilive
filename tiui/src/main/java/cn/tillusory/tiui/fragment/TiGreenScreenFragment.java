package cn.tillusory.tiui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.List;

import cn.tillusory.sdk.TiSDKManager;
import cn.tillusory.sdk.bean.TiGreenScreen;
import cn.tillusory.sdk.bean.TiWatermark;
import cn.tillusory.tiui.R;
import cn.tillusory.tiui.adapter.TiGreenScreenAdapter;
import cn.tillusory.tiui.adapter.TiWatermarkAdapter;

/**
 * Created by Anko on 2020/3/25.
 * Copyright (c) 2020 拓幻科技 - tillusory.cn. All rights reserved.
 */
public class TiGreenScreenFragment extends LazyFragment {

    private List<TiGreenScreen> greenScreenList = new ArrayList<>();
    private TiSDKManager tiSDKManager;

    public TiGreenScreenFragment setTiSDKManager(TiSDKManager tiSDKManager) {
        this.tiSDKManager = tiSDKManager;
        return this;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        setContentView(R.layout.fragment_ti_sticker);

        if (getContext() == null) return;

        greenScreenList.clear();
        greenScreenList.add(TiGreenScreen.NO_GreenScreen);
        greenScreenList.addAll(TiGreenScreen.getAllTiGreenScreens(getContext()));

        RecyclerView tiGreenScreenRV = (RecyclerView) findViewById(R.id.tiRecyclerView);
        TiGreenScreenAdapter greenScreenAdapter = new TiGreenScreenAdapter(greenScreenList, tiSDKManager);
        tiGreenScreenRV.setLayoutManager(new GridLayoutManager(getContext(), 5));
        tiGreenScreenRV.setAdapter(greenScreenAdapter);
    }
}

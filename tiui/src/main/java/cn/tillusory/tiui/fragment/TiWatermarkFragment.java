package cn.tillusory.tiui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.List;

import cn.tillusory.sdk.TiSDKManager;
import cn.tillusory.sdk.bean.TiWatermark;
import cn.tillusory.tiui.R;
import cn.tillusory.tiui.adapter.TiWatermarkAdapter;

/**
 * Created by Anko on 2018/12/1.
 * Copyright (c) 2018-2020 拓幻科技 - tillusory.cn. All rights reserved.
 */
public class TiWatermarkFragment extends LazyFragment {

    private List<TiWatermark> watermarkList = new ArrayList<>();
    private TiSDKManager tiSDKManager;

    public TiWatermarkFragment setTiSDKManager(TiSDKManager tiSDKManager) {
        this.tiSDKManager = tiSDKManager;
        return this;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        setContentView(R.layout.fragment_ti_sticker);

        if (getContext() == null) return;

        watermarkList.clear();
        watermarkList.add(TiWatermark.NO_WATERMARK);
        watermarkList.addAll(TiWatermark.getAllWatermarks(getContext()));

        RecyclerView tiWatermarkRV = (RecyclerView) findViewById(R.id.tiRecyclerView);
        TiWatermarkAdapter watermarkAdapter = new TiWatermarkAdapter(watermarkList, tiSDKManager);
        tiWatermarkRV.setLayoutManager(new GridLayoutManager(getContext(), 5));
        tiWatermarkRV.setAdapter(watermarkAdapter);
    }
}

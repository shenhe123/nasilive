package cn.tillusory.tiui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.tillusory.tiui.R;
import cn.tillusory.tiui.adapter.TiQuickBeautyAdapter;
import cn.tillusory.tiui.model.TiQuickBeauty;

/**
 * Copyright (c) 2020 拓幻科技 - tillusory.cn. All rights reserved.
 */
public class TiQuickBeautyFragment extends LazyFragment {

    private List<TiQuickBeauty> quickBeautyList = new ArrayList<>();

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        setContentView(R.layout.fragment_ti_recyclerview);

        quickBeautyList.clear();
        quickBeautyList.addAll(Arrays.asList(TiQuickBeauty.values()));

        RecyclerView tiQuickBeautyRV = (RecyclerView) findViewById(R.id.tiRecyclerView);
        TiQuickBeautyAdapter adapter = new TiQuickBeautyAdapter(quickBeautyList);
        tiQuickBeautyRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        tiQuickBeautyRV.setAdapter(adapter);
    }
}

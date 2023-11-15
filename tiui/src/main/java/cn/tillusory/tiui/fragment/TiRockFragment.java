package cn.tillusory.tiui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.tillusory.sdk.TiSDKManager;
import cn.tillusory.tiui.R;
import cn.tillusory.tiui.adapter.TiRockAdapter;
import cn.tillusory.tiui.model.TiRock;

/**
 * Created by Anko on 2018/12/1.
 * Copyright (c) 2018-2020 拓幻科技 - tillusory.cn. All rights reserved.
 */
public class TiRockFragment extends LazyFragment {

    private List<TiRock> rockList = new ArrayList<>();
    private TiSDKManager tiSDKManager;

    public TiRockFragment setTiSDKManager(TiSDKManager tiSDKManager) {
        this.tiSDKManager = tiSDKManager;
        return this;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        setContentView(R.layout.fragment_ti_recyclerview);

        rockList.clear();
        rockList.addAll(Arrays.asList(TiRock.values()));

        RecyclerView tiRockRV = (RecyclerView) findViewById(R.id.tiRecyclerView);
        TiRockAdapter rockAdapter = new TiRockAdapter(rockList, tiSDKManager);
        tiRockRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        tiRockRV.setAdapter(rockAdapter);
    }
}

package cn.tillusory.tiui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.tillusory.sdk.TiSDKManager;
import cn.tillusory.tiui.R;
import cn.tillusory.tiui.adapter.TiDistortionAdapter;
import cn.tillusory.tiui.model.TiDistortion;

/**
 * Created by Anko on 2018/12/1.
 * Copyright (c) 2018-2020 拓幻科技 - tillusory.cn. All rights reserved.
 */
public class TiDistortionFragment extends LazyFragment {

    private List<TiDistortion> distortionList = new ArrayList<>();
    private TiSDKManager tiSDKManager;

    public TiDistortionFragment setTiSDKManager(TiSDKManager tiSDKManager) {
        this.tiSDKManager = tiSDKManager;
        return this;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        setContentView(R.layout.fragment_ti_recyclerview);

        distortionList.clear();
        distortionList.addAll(Arrays.asList(TiDistortion.values()));

        RecyclerView tiDistortionRV = (RecyclerView) findViewById(R.id.tiRecyclerView);
        TiDistortionAdapter distortionAdapter = new TiDistortionAdapter(distortionList, tiSDKManager);
        tiDistortionRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));
        tiDistortionRV.setAdapter(distortionAdapter);
    }
}

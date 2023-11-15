package cn.tillusory.tiui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cn.tillusory.sdk.TiSDK;
import cn.tillusory.sdk.TiSDKManager;
import cn.tillusory.sdk.bean.TiMask;
import cn.tillusory.tiui.R;
import cn.tillusory.tiui.adapter.TiMaskAdapter;

/**
 * Created by Anko on 2019-07-12.
 * Copyright (c) 2018-2020 拓幻科技 - tillusory.cn. All rights reserved.
 */
public class TiMaskFragment extends LazyFragment implements Observer {

    private List<TiMask> maskList = new ArrayList<>();
    private TiSDKManager tiSDKManager;
    private TiMaskAdapter tiMaskAdapter;

    public TiMaskFragment setTiSDKManager(TiSDKManager tiSDKManager) {
        this.tiSDKManager = tiSDKManager;
        return this;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        setContentView(R.layout.fragment_ti_sticker);

        TiSDK.addObserver(this);

        if (getContext() == null) return;

        maskList.clear();
        maskList.add(TiMask.NO_MASK);
        maskList.addAll(TiMask.getAllMasks(getContext()));

        RecyclerView tiMaskRV = (RecyclerView) findViewById(R.id.tiRecyclerView);
        tiMaskAdapter = new TiMaskAdapter(maskList, tiSDKManager);
        tiMaskRV.setLayoutManager(new GridLayoutManager(getContext(), 5));
        tiMaskRV.setAdapter(tiMaskAdapter);
    }

    @Override
    public void update(Observable o, Object arg) {

        if (getContext() == null) return;

        maskList.clear();
        maskList.add(TiMask.NO_MASK);
        maskList.addAll(TiMask.getAllMasks(getContext()));

        if (tiMaskAdapter != null)
            tiMaskAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();

        TiSDK.deleteObserver(this);
    }
}

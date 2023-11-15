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
import cn.tillusory.sdk.bean.TiInteraction;
import cn.tillusory.tiui.R;
import cn.tillusory.tiui.adapter.TiInteractionAdapter;

/**
 * Created by Anko on 2020/4/27.
 * Copyright (c) 2020 拓幻科技 - tillusory.cn. All rights reserved.
 */
public class TiInteractionFragment extends LazyFragment implements Observer {

    private List<TiInteraction> interactionList = new ArrayList<>();
    private TiSDKManager tiSDKManager;
    private TiInteractionAdapter tiInteractionAdapter;

    public TiInteractionFragment setTiSDKManager(TiSDKManager tiSDKManager) {
        this.tiSDKManager = tiSDKManager;
        return this;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_ti_sticker);

        TiSDK.addObserver(this);

        if (getContext() == null) return;

        interactionList.clear();
        interactionList.add(TiInteraction.NO_INTERACTION);
        interactionList.addAll(TiInteraction.getAllInteractions(getContext()));

        RecyclerView tiInteractionRV = (RecyclerView) findViewById(R.id.tiRecyclerView);
        tiInteractionAdapter = new TiInteractionAdapter(interactionList, tiSDKManager);
        tiInteractionRV.setLayoutManager(new GridLayoutManager(getContext(), 5));
        tiInteractionRV.setAdapter(tiInteractionAdapter);
    }

    @Override
    public void update(Observable o, Object arg) {

        if (getContext() == null) return;

        interactionList.clear();
        interactionList.add(TiInteraction.NO_INTERACTION);
        interactionList.addAll(TiInteraction.getAllInteractions(getContext()));

        if (tiInteractionAdapter != null)
            tiInteractionAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        TiSDK.deleteObserver(this);
    }

}

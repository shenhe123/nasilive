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
import cn.tillusory.sdk.bean.TiSticker;
import cn.tillusory.tiui.R;
import cn.tillusory.tiui.adapter.TiStickerAdapter;

/**
 * Created by Anko on 2018/12/1.
 * Copyright (c) 2018-2020 拓幻科技 - tillusory.cn. All rights reserved.
 */
public class TiStickerFragment extends LazyFragment implements Observer {

    private List<TiSticker> stickerList = new ArrayList<>();
    private TiSDKManager tiSDKManager;
    private TiStickerAdapter tiStickerAdapter;

    public TiStickerFragment setTiSDKManager(TiSDKManager tiSDKManager) {
        this.tiSDKManager = tiSDKManager;
        return this;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        setContentView(R.layout.fragment_ti_sticker);

        TiSDK.addObserver(this);

        if (getContext() == null) return;

        stickerList.clear();
        stickerList.add(TiSticker.NO_STICKER);
        stickerList.addAll(TiSticker.getAllStickers(getContext()));

        RecyclerView tiStickerRV = (RecyclerView) findViewById(R.id.tiRecyclerView);
        tiStickerAdapter = new TiStickerAdapter(stickerList, tiSDKManager);
        tiStickerRV.setLayoutManager(new GridLayoutManager(getContext(), 5));
        tiStickerRV.setAdapter(tiStickerAdapter);
    }

    @Override
    public void update(Observable o, Object arg) {

        if (getContext() == null) return;

        stickerList.clear();
        stickerList.add(TiSticker.NO_STICKER);
        stickerList.addAll(TiSticker.getAllStickers(getContext()));

        if (tiStickerAdapter != null)
            tiStickerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        TiSDK.deleteObserver(this);
    }
}

package cn.tillusory.tiui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.tillusory.tiui.R;
import cn.tillusory.tiui.adapter.TiMakeupAdapter;
import cn.tillusory.tiui.model.TiMakeupType;

/**
 * Created by Anko on 2019-09-05.
 * Copyright (c) 2018-2020 拓幻科技 - tillusory.cn. All rights reserved.
 */
public class TiMakeupFragment extends LazyFragment {

    private List<TiMakeupType> makeupList = new ArrayList<>();

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        setContentView(R.layout.fragment_ti_recyclerview);

        makeupList.clear();
        makeupList.addAll(Arrays.asList(TiMakeupType.values()));

        RecyclerView tiMakeupRV = (RecyclerView) findViewById(R.id.tiRecyclerView);
        TiMakeupAdapter makeupAdapter = new TiMakeupAdapter(makeupList);
        tiMakeupRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));
        tiMakeupRV.setAdapter(makeupAdapter);
    }
}

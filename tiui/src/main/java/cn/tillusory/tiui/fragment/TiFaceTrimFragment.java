package cn.tillusory.tiui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.tillusory.tiui.R;
import cn.tillusory.tiui.adapter.TiFaceTrimAdapter;
import cn.tillusory.tiui.model.TiFaceTrim;

/**
 * Created by Anko on 2018/12/1.
 * Copyright (c) 2018-2020 拓幻科技 - tillusory.cn. All rights reserved.
 */
public class TiFaceTrimFragment extends LazyFragment {

    private List<TiFaceTrim> faceTrimList = new ArrayList<>();

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        setContentView(R.layout.fragment_ti_recyclerview);

        faceTrimList.clear();
        faceTrimList.addAll(Arrays.asList(TiFaceTrim.values()));

        RecyclerView tiFaceTrimRV = (RecyclerView) findViewById(R.id.tiRecyclerView);
        TiFaceTrimAdapter faceTrimAdapter = new TiFaceTrimAdapter(faceTrimList);
        tiFaceTrimRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        tiFaceTrimRV.setAdapter(faceTrimAdapter);
    }

}

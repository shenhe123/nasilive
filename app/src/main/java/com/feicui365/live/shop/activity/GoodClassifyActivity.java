package com.feicui365.live.shop.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.adapter.ClassifyChildAdapter;
import com.feicui365.live.shop.adapter.ClassifyParentAdapter;
import com.feicui365.live.shop.entity.ClassIfy;
import com.feicui365.live.shop.entity.Subcates;
import com.feicui365.nasinet.userconfig.AppStatusManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import butterknife.BindView;

public class GoodClassifyActivity extends BaseMvpActivity<ShopPresenter> implements ShopContract.View {


    @BindView(R.id.rv_goods_classify_parent)
    RecyclerView rv_goods_classify_parent;

    @BindView(R.id.rv_goods_classify_child)
    RecyclerView rv_goods_classify_child;

    ClassifyParentAdapter class_parent_adapter;
    ArrayList<ClassIfy> classify_list = new ArrayList<>();

    ClassifyChildAdapter class_child_adapter;
    ArrayList<Subcates> child_list = new ArrayList<>();
    HashMap chile_map = new HashMap();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_classify;
    }

    @Override
    protected void initData() {
        mPresenter.getShopCategoryList();
    }

    @Override
    protected void initView() {
        AppStatusManager.getInstance().setAppStatus(1);
        mPresenter = new ShopPresenter();
        mPresenter.attachView(this);
        setTitle("商品分类");
        initParentList();
        initChildList();
    }

    @Override
    public void getShopCategoryList(ArrayList<ClassIfy> baseResponse) {
        if (baseResponse == null) {
            return;
        }
        if (baseResponse.size() == 0) {
            return;
        }
        baseResponse.get(0).setChose(true);
        classify_list.addAll(baseResponse);
        class_parent_adapter.notifyDataSetChanged();
        initChildData(baseResponse);
    }

    private void initChildData(ArrayList<ClassIfy> result) {
        for (ClassIfy classIfy : result) {
            chile_map.put(classIfy.getId(), classIfy.getSubcates());
        }
        child_list.clear();

        child_list.addAll(result.get(0).getSubcates());
        class_child_adapter.notifyDataSetChanged();
    }

    private void initChildList() {
        class_child_adapter = new ClassifyChildAdapter(child_list);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        rv_goods_classify_child.setLayoutManager(layoutManager);
        rv_goods_classify_child.setAdapter(class_child_adapter);
        class_child_adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                class_child_adapter.change(child_list.get(position));
                Intent intent = new Intent();
                intent.putExtra("data", child_list.get(position));
                setResult(RESULT_OK, intent);
                finish();


            }
        });

    }

    private void initParentList() {
        class_parent_adapter = new ClassifyParentAdapter(classify_list);
        rv_goods_classify_parent.setLayoutManager(new LinearLayoutManager(this));
        rv_goods_classify_parent.setAdapter(class_parent_adapter);
        class_parent_adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                class_parent_adapter.change(classify_list.get(position));
                if (chile_map.get(classify_list.get(position).getId()) != null) {
                    child_list.clear();
                    child_list.addAll((Collection<? extends Subcates>) chile_map.get(classify_list.get(position).getId()));
                    class_child_adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onError(Throwable throwable) {

    }


}

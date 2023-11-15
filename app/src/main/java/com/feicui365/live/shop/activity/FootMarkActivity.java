package com.feicui365.live.shop.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.adapter.FootMarkAdapter;
import com.feicui365.live.shop.entity.OrderGoods;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FootMarkActivity extends BaseMvpActivity<ShopPresenter> implements ShopContract.View {

    FootMarkAdapter footMarkAdapter;
    @BindView(R.id.ll_chose_all)
    LinearLayout ll_chose_all;

    @BindView(R.id.tv_del)
    TextView tv_del;

    @BindView(R.id.rl_edit)
    RelativeLayout rl_edit;

    @BindView(R.id.rv_footmark)
    RecyclerView rv_footmark;

    @BindView(R.id.iv_chose)
    ImageView iv_chose;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.rl_nothing)
    RelativeLayout rl_nothing;


    int page = 1;
    //
    private List<OrderGoods> list_goods = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_footmark;
    }

    @Override
    protected void initData() {
        mPresenter.getUserVisits(String.valueOf(page));
    }

    @Override
    protected void initView() {
        mPresenter = new ShopPresenter();
        mPresenter.attachView(this);
        initList();
        setTitle("我的足迹");
        setOther("管理");
        hideOther(false);
    }

    private void initList() {
        footMarkAdapter = new FootMarkAdapter(list_goods);
        rv_footmark.setLayoutManager(new LinearLayoutManager(this));
        rv_footmark.setAdapter(footMarkAdapter);
        footMarkAdapter.setOnChoseListener(new FootMarkAdapter.OnChoseListener() {
            @Override
            public void onChose() {
                openEdit();
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getUserVisits(String.valueOf(page));
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mPresenter.getUserVisits(String.valueOf(page));
            }
        });
    }

    @Override
    public void getUserVisits(ArrayList<OrderGoods> bean) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        Log.e("getUserVisits",bean.size()+"");
        if (page == 1) {
            list_goods.clear();
            if (bean == null) {
                rl_nothing.setVisibility(View.VISIBLE);
                return;
            }

            if (bean.size() == 0) {
                rl_nothing.setVisibility(View.VISIBLE);
                return;
            }

        } else {
            if (bean == null) {
                page--;
                return;
            }
            if (bean.size() == 0) {
                page--;
                return;
            }

        }
        rl_nothing.setVisibility(View.GONE);
        list_goods.addAll(bean);
        footMarkAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @OnClick({R.id.ll_chose_all, R.id.tv_del, R.id.tv_other})
    public void onClick(View view) {
        switch (view.getId()) {
            //全选
            case R.id.ll_chose_all:
                choseAll();
                break;
            case R.id.tv_del:
                delChose();
                break;
            case R.id.tv_other:
                if (rl_edit.getVisibility() == View.VISIBLE) {
                    closeEdit();
                } else {
                    openEdit();
                }
                break;
        }

    }


    public void choseAll() {
        //是否是全选
        boolean is_all = true;
        for (int i = 0; i < list_goods.size(); i++) {
            if (!list_goods.get(i).isIs_chose()) {
                is_all = false;
                break;
            }
        }
        //如果是全选
        if (is_all) {
            for (int i = 0; i < list_goods.size(); i++) {
                list_goods.get(i).setIs_chose(false);
            }
            footMarkAdapter.notifyDataSetChanged();

            iv_chose.setImageResource(R.drawable.radio_unchoose);
            tv_del.setText("删除");

        } else {
            for (int i = 0; i < list_goods.size(); i++) {
                list_goods.get(i).setIs_chose(true);
            }
            footMarkAdapter.notifyDataSetChanged();
            iv_chose.setImageResource(R.drawable.radio_choose);
            tv_del.setText("删除(" + list_goods.size() + ")");
        }
    }


    public void openEdit() {


        //选择个数
        int chose = 0;
        //是否全选
        boolean is_all = true;

        rl_edit.setVisibility(View.VISIBLE);
        for (int i = 0; i < list_goods.size(); i++) {
            if (!list_goods.get(i).isIs_chose()) {
                is_all = false;
            } else {
                chose++;
            }
        }
        if (chose > 0) {
            tv_del.setText("删除(" + chose + ")");
        } else {
            tv_del.setText("删除");
        }
        //如果是全选
        if (is_all) {
            iv_chose.setImageResource(R.drawable.radio_choose);
        } else {
            iv_chose.setImageResource(R.drawable.radio_unchoose);
        }


        //如果是全选
        footMarkAdapter.setIs_edit(true);
        footMarkAdapter.notifyDataSetChanged();
    }

    public void closeEdit() {
        rl_edit.setVisibility(View.GONE);
        for (int i = 0; i < list_goods.size(); i++) {
            list_goods.get(i).setIs_chose(false);
        }
        footMarkAdapter.setIs_edit(false);
        footMarkAdapter.notifyDataSetChanged();
    }


    private void delChose() {

        String result = "";
        ArrayList<OrderGoods> list_orderGoods = new ArrayList<>();
        for (int i = 0; i < list_goods.size(); i++) {
            if (list_goods.get(i).isIs_chose()) {
                result = result + list_goods.get(i).getId() + ",";
                list_orderGoods.add(list_goods.get(i));
            }
        }
        if (result.equals("")) {
            return;
        }

        Iterator<OrderGoods> iterator = list_goods.iterator();
        while (iterator.hasNext()) {
            OrderGoods orderGoods = iterator.next();
            for (int y = 0; y < list_orderGoods.size(); y++) {
                if (orderGoods.getId() == list_orderGoods.get(y).getId()) {
                    iterator.remove();
                }
            }
        }

        StringBuilder stringBuilder = new StringBuilder(result);
        stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
        mPresenter.delUserVisits(result);
        footMarkAdapter.notifyDataSetChanged();
        tv_del.setText("删除");

        if (list_goods.size() == 0) {
            iv_chose.setImageResource(R.drawable.radio_unchoose);
            rl_nothing.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void delUserVisits(BaseResponse baseResponse) {
        // mPresenter.getUserVisits();

    }
}

package com.feicui365.live.shop.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.im.TxImUtils;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.adapter.GoodsAdapter;
import com.feicui365.live.shop.entity.Good;
import com.feicui365.live.shop.entity.Shop;
import com.feicui365.live.shop.entity.ShopGoodList;
import com.feicui365.live.util.SpacingItemDecoration;
import com.feicui365.nasinet.userconfig.AppStatusManager;
import com.feicui365.nasinet.utils.AppManager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ShopActivity extends BaseMvpActivity<ShopPresenter> implements ShopContract.View {

    @BindView(R.id.rv_shop)
    RecyclerView rv_shop;
    @BindView(R.id.tv_all_goods)
    TextView tv_all_goods;
    @BindView(R.id.iv_avatar)
    CircleImageView iv_avatar;
    @BindView(R.id.tv_shoper)
    TextView tv_shoper;
    @BindView(R.id.rl_null)
    RelativeLayout rl_null;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.iv_go_chat)
    ImageView iv_go_chat;


    private Shop shop;
    private GoodsAdapter goodsAdapter;
    private int page = 1;
    private ArrayList<Good> goods_list = new ArrayList<>();
    String shop_id="";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop;
    }

    @Override
    protected void initData() {
        mPresenter.getShopGoodsList(shop_id, String.valueOf(page));
    }

    @Override
    protected void initView() {
        AppStatusManager.getInstance().setAppStatus(1);
        mPresenter = new ShopPresenter();
        mPresenter.attachView(this);
        shop_id=getIntent().getStringExtra(Constants.SHOP_ID);
        if(shop_id==null){
           finish();
        }
        initList();

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                initData();

            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                initData();
                refreshLayout.setEnableLoadMore(true);
            }
        });


        initOther();

    }

    private void initOther() {
        showImageOther(70, 12, R.mipmap.ic_openstore);

    }

    private void initList() {
        goodsAdapter = new GoodsAdapter(goods_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rv_shop.addItemDecoration(new SpacingItemDecoration(0, 4));
        rv_shop.setLayoutManager(gridLayoutManager);
        rv_shop.setAdapter(goodsAdapter);
        goodsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent i = new Intent(context, GoodsInfoActivity.class);
                i.putExtra(Constants.GOODS_INFO, goods_list.get(position));
                context.startActivity(i);
            }
        });

    }

    @Override
    public void getShopGoodsList(ShopGoodList bean) {
        refreshLayout.finishLoadMore(true);
        refreshLayout.finishRefresh(true);
        if (bean.getShop()!=null) {
            shop=bean.getShop();
            Glide.with(this)
                    .applyDefaultRequestOptions(new RequestOptions().placeholder(R.mipmap.moren))
                    .load(bean.getShop().getUser().getAvatar())
                    .into(iv_avatar);

            tv_shoper.setText(bean.getShop().getUser().getNick_name());
            tv_all_goods.setText("全部商品（" + bean.getCount() + "）");
        }


        if (page == 1) {
            goods_list.clear();



            if (bean.getList() == null) {
                rl_null.setVisibility(View.VISIBLE);
                return;
            }
            if (bean.getList().size() == 0) {
                rl_null.setVisibility(View.VISIBLE);
                return;
            }


        } else {
            if (bean.getList().size() == 0) {
                page--;
                refreshLayout.setEnableLoadMore(false);
                return;
            }
        }
        rl_null.setVisibility(View.GONE);
        goods_list.addAll(bean.getList());
        goodsAdapter.notifyDataSetChanged();


    }

    @Override
    public void onError(Throwable throwable) {

    }
    @OnClick({R.id.iv_go_chat,R.id.iv_other})
    public void onClick(View view) {
        if (isFastClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.iv_go_chat:
                TxImUtils.getSocketManager().startChat(shop.getUser().getId(), shop.getUser().getNick_name(),shop.getUser().getAvatar());
                break;
            case R.id.iv_other:
                AppManager.getAppManager().startActivity(UserShopActivity.class);

                break;
        }
    }
}

package com.feicui365.live.ui.act;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lxj.xpopup.XPopup;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.base.OthrBase2Activity;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.widget.CommentZhifu;
import com.feicui365.live.model.entity.Gold;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.ui.adapter.ToPayAdapter;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.wxapi.PayCallback;
import com.feicui365.live.util.MyUserInstance;

import java.util.ArrayList;
import java.util.List;

public class ToPayActivity extends OthrBase2Activity implements PayCallback {

    RecyclerView rv_coins;
    TextView tv_coin, tv_submit;
    ToPayAdapter adapter;
    private List<Gold> topayList = new ArrayList<>();
    String itemid = "";

    RelativeLayout rl_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_pay_activity);
        hideTitle(true);
        MyUserInstance.getInstance().getWxPayBuilder(ToPayActivity.this).setPayCallback(this);
        MyUserInstance.getInstance().getAliPayBuilder(ToPayActivity.this).setPayCallback(this);

        rv_coins = findViewById(R.id.rv_coins);
        tv_coin = findViewById(R.id.tv_coin);
        tv_submit = findViewById(R.id.tv_submit);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        int space = 8;
        if (rv_coins.getItemDecorationCount() == 0) {
            rv_coins.addItemDecoration(new GridSpacingItemDecoration(3, 40, true));
        }
        rv_coins.setHasFixedSize(true);
        // rv_coins.addItemDecoration(new SpaceItemDecoration(space));
        rv_coins.setLayoutManager(layoutManager);
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemid.equals("")) {
                    ToastUtils.showT("请选择充值金额");
                    return;
                }
                toPay();
            }
        });

        rl_back = findViewById(R.id.rl_back);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    private void toPay() {
        CommentZhifu commentZhifu = new CommentZhifu(this);


        commentZhifu.setToPayListener(new CommentZhifu.ToPayListener() {
            @Override
            public void ali() {
                MyUserInstance.getInstance().getAliPayBuilder(ToPayActivity.this).pay(itemid);
            }

            @Override
            public void creditcard() {
                MyUserInstance.getInstance().getWxPayBuilder(ToPayActivity.this).pay(itemid);
            }
        });
        new XPopup.Builder(ToPayActivity.this)
                .hasShadowBg(true)
                .asCustom(commentZhifu)
                .show();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.to_pay_activity;
    }

    @Override
    protected void initData() {
        //初始化金币
        showLoading();

        HttpUtils.getInstance().getUserInfo(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                hideLoading();
                try {
                    JSONObject jsonObject = (JSONObject) HttpUtils.getInstance().check(response);
                    if (HttpUtils.getInstance().swtichStatus(jsonObject)) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        UserRegist userRegist = (UserRegist) JSONObject.parseObject(data.toString(), UserRegist.class);
                        tv_coin.setText(userRegist.getGold());
                        MyUserInstance.getInstance().setUserInfo(userRegist);

                    }
                } catch (Exception e) {
                    Log.e("Exception", e.toString());
                }

            }
            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                hideLoading();
            }
        });

        HttpUtils.getInstance().getPriceList(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                hideLoading();
                try {
                    // JSONArray data = (JSONArray) HttpUtils.getInstance().check(response).getJSONArray("data");
                    JSONObject jsonObject = HttpUtils.getInstance().check(response);
                    if (HttpUtils.getInstance().swtichStatus(jsonObject)) {
                        JSONArray data = jsonObject.getJSONArray("data");

                        if (null != data) {
                            List<Gold> golds = JSON.parseArray(data.toJSONString(), Gold.class);
                            for (int i = 0; i < golds.size(); i++) {
                                topayList.add(golds.get(i));
                            }
                            adapter = new ToPayAdapter(ToPayActivity.this, topayList);
                            rv_coins.setAdapter(adapter);

                            adapter.setOnItemListener(new ToPayAdapter.OnItemListener() {
                                @Override
                                public void onClick(View v, int pos, Gold projectc) {
                                    adapter.setDefSelect(pos);
                                    itemid = projectc.getId();

                                }
                            });
                            adapter.setDefSelect(0);
                            itemid = golds.get(0).getId();
                            hideLoading();
                        }
                    }
                } catch (Exception e) {
                    Log.e("Exception", e.toString());
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                hideLoading();
            }
        });

    }

    @Override
    public void onSuccess() {
        ToastUtils.showT("充值成功");
        HttpUtils.getInstance().getUserInfo(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    JSONObject data = (JSONObject) HttpUtils.getInstance().check(response).getJSONObject("data");
                    if (null != data) {
                        UserRegist userRegist = (UserRegist) JSONObject.parseObject(data.toString(), UserRegist.class);
                        MyUserInstance.getInstance().setUserInfo(userRegist);

                        tv_coin.setText(MyUserInstance.getInstance().getUserinfo().getGold());
                    }
                } catch (Exception e) {
                    Log.e("Exception", e.toString());
                }

            }
            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                hideLoading();
            }
        });

    }

    @Override
    public void onFailed() {
        ToastUtils.showT("充值失败");
    }


    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        private final int column;
        private final int space;

        public SpaceItemDecoration(int space, int column) {
            this.space = space;
            this.column = column;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            // 第一列左边贴边、后边列项依次移动一个space和前一项移动的距离之和
            int mod = parent.getChildAdapterPosition(view) % column;
            outRect.left = space * mod;
        }

    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }

    }


}

package com.feicui365.live.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.adapter.ConfirmGoodAdapter;
import com.feicui365.live.shop.adapter.ConfirmGoodChildAdapter;
import com.feicui365.live.shop.entity.Address;
import com.feicui365.live.shop.entity.CartGoodInfo;
import com.feicui365.live.shop.entity.CartGoods;
import com.feicui365.live.shop.entity.Confirm;
import com.feicui365.live.shop.entity.ConfirmGood;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.wxapi.PayCallback;
import com.feicui365.nasinet.utils.AppManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class ConfirmOrderActivity extends BaseMvpActivity<ShopPresenter> implements ShopContract.View {


    @BindView(R.id.rv_goods_list)
    RecyclerView rv_goods_list;
    @BindView(R.id.tv_freight)
    TextView tv_freight;
    @BindView(R.id.tv_all_price)
    TextView tv_all_price;

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_address)
    TextView tv_address;

    @BindView(R.id.tv_chose_address)
    TextView tv_chose_address;

    @BindView(R.id.rl_address)
    RelativeLayout rl_address;

    @BindView(R.id.rl_top)
    RelativeLayout rl_top;

    @BindView(R.id.rl_ali)
    RelativeLayout rl_ali;

    @BindView(R.id.rl_wx)
    RelativeLayout rl_wx;

    @BindView(R.id.et_words)
    EditText et_words;


    @BindView(R.id.iv_chose_ali)
    ImageView iv_chose_ali;

    @BindView(R.id.iv_chose_wx)
    ImageView iv_chose_wx;
    int buy_type = 1;

    ArrayList<CartGoodInfo> goods_list;

    ConfirmGoodAdapter confirmGoodAdapter;
    Address chose_address;
    double result;
    String order_no;
    String total_fee;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_order;
    }

    @Override
    protected void initData() {
        int all_freight = 0;
        result = 0;
        for (int i = 0; i < goods_list.size(); i++) {

            for (int y = 0; y < goods_list.get(i).getCartgoods().size(); y++) {
                CartGoods cartGoods = goods_list.get(i).getCartgoods().get(y);
                all_freight = all_freight + cartGoods.getGoods().getFreight();
                result = result + new Double(cartGoods.getInventory().getPrice()) * new Double(cartGoods.getCount());
            }
        }

        if (all_freight == 0) {
            tv_freight.setText("免运费");
        } else {
            tv_freight.setText("￥" + all_freight);
        }


        tv_all_price.setText("￥ " + result);
    }

    @Override
    protected void initView() {
        mPresenter = new ShopPresenter();
        mPresenter.attachView(this);
        setTitle("确认订单");
        Bundle bundleObject = getIntent().getExtras();
        if (bundleObject == null) {
            return;
        }
        if (bundleObject.getSerializable(Constants.SHOP_CART_GOODS) == null) {
            return;
        }
        goods_list = (ArrayList<CartGoodInfo>) bundleObject.getSerializable(Constants.SHOP_CART_GOODS);
        initList();

    }


    private void initList() {
        confirmGoodAdapter = new ConfirmGoodAdapter(goods_list);
        rv_goods_list.setLayoutManager(new LinearLayoutManager(this));
        rv_goods_list.setAdapter(confirmGoodAdapter);
        confirmGoodAdapter.setCheckChangeListener(new ConfirmGoodChildAdapter.ChildNumChangeListener() {
            @Override
            public void checkChange() {
                result = 0;
                for (int i = 0; i < goods_list.size(); i++) {
                    for (int y = 0; y < goods_list.get(i).getCartgoods().size(); y++) {
                        CartGoods cartGoods = goods_list.get(i).getCartgoods().get(y);
                        result = result + new Double(cartGoods.getInventory().getPrice()) * new Double(cartGoods.getCount());
                        tv_all_price.setText("￥ " + result);
                    }
                }
            }
        });
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @OnClick({R.id.rl_top, R.id.rl_wx, R.id.rl_ali, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_top:
                Intent intent = new Intent(this, MyAddressActivity.class);
                startActivityForResult(intent, Constants.GET_ADDRESS);
                break;
            case R.id.rl_ali:
                buy_type = 1;
                iv_chose_ali.setImageResource(R.drawable.radio_choose);
                iv_chose_wx.setImageResource(R.drawable.radio_unchoose);
                break;
            case R.id.rl_wx:
                buy_type = 2;
                iv_chose_ali.setImageResource(R.drawable.radio_unchoose);
                iv_chose_wx.setImageResource(R.drawable.radio_choose);
                break;
            case R.id.tv_submit:

                if (chose_address == null) {
                    ToastUtils.showT("请选择地址后,再下单");
                    return;
                }

                ArrayList<Confirm> confirms = initConfirmDate();
                if (confirms.size() == 0) {
                    return;
                }
                if (order_no == null & total_fee == null) {
                    mPresenter.submitOrder(chose_address.getId(), confirms, String.valueOf(result));
                } else {
                    buy();
                }

                break;
        }
    }

    //重构数据结构,因为这里传的数据比较特殊要转成特定的格式提交

    private ArrayList<Confirm> initConfirmDate() {

        //订单信息
        ArrayList<Confirm> result_confirm = new ArrayList<>();
        //轮询参数
        for (int i = 0; i < goods_list.size(); i++) {
            //购物车信息集合
            CartGoodInfo cartGoodInfo = goods_list.get(i);
            //新建提交订单信息
            Confirm confirm = new Confirm();
            //订单留言
            confirm.setRemark(et_words.getText().toString());
            //商店ID
            confirm.setShopid(cartGoodInfo.getShopid());
            //总价
            double result_temp = 0;
            //物品详情
            ArrayList<ConfirmGood> confirmGoods = new ArrayList<>();
            for (int y = 0; y < cartGoodInfo.getCartgoods().size(); y++) {
                CartGoods cartGoods = cartGoodInfo.getCartgoods().get(y);

                result_temp = result_temp + new Double(cartGoods.getInventory().getPrice()) * new Double(cartGoods.getCount());
                //组合订单物品信息
                confirmGoods.add(new ConfirmGood(cartGoods.getCount(),//数量
                        Integer.parseInt(cartGoods.getGoods().getId()),//购物车内物品ID
                        cartGoods.getInventoryid(),//库存ID
                        new Double(cartGoods.getInventory().getPrice()).toString()));//库存单价
            }
            confirm.setTotal_price(new Double(result_temp).toString());//总价
            confirm.setGoods(confirmGoods);//设置商品
            result_confirm.add(confirm);
        }


        return result_confirm;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.GET_ADDRESS) {
                Address address = (Address) data.getSerializableExtra("data");
                if (address != null) {
                    chose_address = address;
                    tv_chose_address.setVisibility(View.GONE);
                    rl_address.setVisibility(View.VISIBLE);

                    tv_name.setText(address.getName());
                    tv_phone.setText(address.getMobile());
                    tv_address.setText(address.getProvince() + address.getCity() + address.getDistrict() + address.getAddress());
                }
            }
        }

    }

    @Override
    public void submitOrder(JSONObject baseResponse) {
        ToastUtils.showT("提交订单成功,准备支付");


        JSONObject data = baseResponse.getJSONObject("data");
        if (baseResponse == null) {
            return;
        }
        if (data.getString("order_no") == null) {
            return;
        }
        if (data.getString("total_price") == null) {
            return;
        }
        order_no = data.getString("order_no");
        total_fee = data.getString("total_price");
        buy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyUserInstance.getInstance().getWxPayBuilder(this).setPayCallback(null);
        MyUserInstance.getInstance().getAliPayBuilder(this).setPayCallback(null);
    }

    private void buy() {
        if (order_no == null) {
            return;
        }
        if (total_fee == null) {
            return;
        }
        switch (buy_type) {
            case 1:

                MyUserInstance.getInstance().getAliPayBuilder(this).getAliShopPayOrder(order_no, total_fee);
                MyUserInstance.getInstance().getAliPayBuilder(this).setPayCallback(new PayCallback() {
                    @Override
                    public void onSuccess() {

                        AppManager.getAppManager().startActivity(PaySuccessActivity.class);
                        finish();
                    }

                    @Override
                    public void onFailed() {
                        ToastUtils.showT("支付失败");

                    }
                });
                break;
            case 2:
                MyUserInstance.getInstance().getWxPayBuilder(this).getWxShopPayOrder(order_no, total_fee);
                MyUserInstance.getInstance().getWxPayBuilder(this).setPayCallback(new PayCallback() {
                    @Override
                    public void onSuccess() {

                        AppManager.getAppManager().startActivity(PaySuccessActivity.class);
                        finish();
                    }

                    @Override
                    public void onFailed() {
                        ToastUtils.showT("支付失败");
                    }
                });

                break;
        }
    }
}

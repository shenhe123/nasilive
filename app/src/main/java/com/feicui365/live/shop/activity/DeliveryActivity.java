package com.feicui365.live.shop.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.adapter.DeliveryAdapter;
import com.feicui365.live.shop.entity.Logistics;
import com.feicui365.live.shop.entity.LogisticsInfo;

import java.util.ArrayList;

import butterknife.BindView;

public class DeliveryActivity extends BaseMvpActivity<ShopPresenter> implements ShopContract.View {
    @BindView(R.id.rv_delivery)
    RecyclerView rv_delivery;

    @BindView(R.id.iv_good_pic)
    ImageView iv_good_pic;
    @BindView(R.id.tv_company)
    TextView tv_company;
    @BindView(R.id.tv_num)
    TextView tv_num;

    @BindView(R.id.rl_null)
    RelativeLayout rl_null;




    LogisticsInfo logisticsInfo;
    String pic_url = "";
    String express_no = "";
    String express_company = "";
    String orderid= "";
    DeliveryAdapter deliveryAdapter;
    ArrayList<Logistics> list_data = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_delivery;
    }

    @Override
    protected void initData() {


        Bundle bundleObject = getIntent().getExtras();
        if (bundleObject == null) {
            finish();
            return;
        }
        if (bundleObject.getSerializable(Constants.EXPRESS_NO) == null) {
            finish();
            return;
        }

        pic_url = bundleObject.getString(Constants.PIC_URL);
        express_no= bundleObject.getString(Constants.EXPRESS_NO);
        express_company= bundleObject.getString(Constants.EXPRESS_COMPANY);
        orderid= bundleObject.getString(Constants.ORDER_ID);
        initTop();


        mPresenter.getExpressInfo(String.valueOf(orderid));

    }

    @Override
    protected void initView() {

        mPresenter = new ShopPresenter();
        mPresenter.attachView(this);
        setTitle("物流信息");
        deliveryAdapter = new DeliveryAdapter(list_data);
        rv_delivery.setLayoutManager(new LinearLayoutManager(this));
        rv_delivery.setAdapter(deliveryAdapter);

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void getExpressInfo(LogisticsInfo baseResponse) {
        if (baseResponse == null) {
            rl_null.setVisibility(View.VISIBLE);
        } else {
            if (baseResponse.getData() == null) {
                rl_null.setVisibility(View.VISIBLE);
                return;
            }
            if (baseResponse.getData().size() == 0) {
                rl_null.setVisibility(View.VISIBLE);
                return;
            }
            rl_null.setVisibility(View.GONE);
            logisticsInfo = baseResponse;
            if (logisticsInfo.getData() != null) {
                if (logisticsInfo.getData().size() != 0) {
                    list_data.clear();
                    list_data.addAll(logisticsInfo.getData());
                    deliveryAdapter.notifyDataSetChanged();
                }
            }
        }

    }


    private void initTop() {

        Glide.with(this).applyDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(R.mipmap.zhanwei))
                .load(pic_url).into(iv_good_pic);
        tv_company.setText(express_company);
        tv_num.setText(express_no);


    }
}

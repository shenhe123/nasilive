package com.feicui365.live.shop.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.adapter.UserAddressAdapter;
import com.feicui365.live.shop.entity.Address;
import com.feicui365.nasinet.utils.AppManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MyAddressActivity extends BaseMvpActivity<ShopPresenter> implements ShopContract.View {

    @BindView(R.id.rv_address)
    RecyclerView rv_address;
    @BindView(R.id.tv_submit)
    TextView tv_submit;


    UserAddressAdapter userAddressAdapter;
    ArrayList<Address> list_address = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_address;
    }

    @Override
    protected void initData() {
        showLoading();
        mPresenter.getUserAddressList();
    }

    @Override
    protected void initView() {
        mPresenter = new ShopPresenter();
        mPresenter.attachView(this);
        setTitle("收货地址");
        initList();


    }

    private void initList() {
        userAddressAdapter = new UserAddressAdapter(list_address);
        rv_address.setLayoutManager(new LinearLayoutManager(this));
        rv_address.setAdapter(userAddressAdapter);
        userAddressAdapter.setCheckListener(new UserAddressAdapter.AddressCheckListener() {
            @Override
            public void addressCheck(Address address) {
                Intent intent = new Intent();
                intent.putExtra("data", address);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void editAddress(Address chose) {
                Intent intent = new Intent(MyAddressActivity.this,EditMyAddressActivity.class);
                intent.putExtra(Constants.EDIT_ADDRESS, chose);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void getUserAddressList(ArrayList<Address> baseResponse) {
        hideLoading();
        list_address.clear();
        list_address.addAll(baseResponse);
        userAddressAdapter.notifyDataSetChanged();
    }


    @OnClick({R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:

                AppManager.getAppManager().startActivity(EditMyAddressActivity.class);

                break;
        }
    }
}

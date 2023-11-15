package com.feicui365.live.shop.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.shop.entity.Address;

import java.util.List;

public class UserAddressAdapter extends BaseQuickAdapter<Address, BaseViewHolder> {

    AddressCheckListener checkListener;

    public interface AddressCheckListener {
        void addressCheck(Address chose);
        void editAddress(Address chose);
    }

    public AddressCheckListener getCheckListener() {
        return checkListener;
    }

    public void setCheckListener(AddressCheckListener checkListener) {
        this.checkListener = checkListener;
    }

    public UserAddressAdapter(@Nullable List<Address> data) {
        super(R.layout.my_address_item, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Address item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_phone, item.getMobile());
        helper.setText(R.id.tv_address, item.getProvince()+item.getCity()+item.getDistrict()+item.getAddress());


        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.setGone(R.id.iv_check, true);
                if (checkListener != null) {
                    checkListener.addressCheck(item);
                }


            }
        });
        helper.getView(R.id.rl_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkListener != null) {
                    checkListener.editAddress(item);
                }
            }
        });
    }


}

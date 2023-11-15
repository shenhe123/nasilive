package com.feicui365.live.shop.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.shop.custom.EditNumView;
import com.feicui365.live.shop.entity.CartGoods;
import com.feicui365.live.util.HttpUtils;

import java.util.List;

public class ConfirmGoodChildAdapter extends BaseQuickAdapter<CartGoods, BaseViewHolder> {
    ChildNumChangeListener childCheckChangeListener;

    public interface ChildNumChangeListener {
        void checkChange();
    }

    public ChildNumChangeListener getCheckChangeListener() {
        return childCheckChangeListener;
    }

    public void setCheckChangeListener(ChildNumChangeListener childCheckChangeListener) {
        this.childCheckChangeListener = childCheckChangeListener;
    }


    public ConfirmGoodChildAdapter(@Nullable List<CartGoods> data) {
        super(R.layout.confirm_good_child_item, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CartGoods item) {
        Glide.with(helper.itemView)
                .applyDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(R.mipmap.zhanwei))
                .load(item.getGoods().getThumb_urls_list()[0])
                .into((ImageView) helper.getView(R.id.iv_pic));

        helper.setText(R.id.tv_title, item.getGoods().getTitle());
        helper.setText(R.id.tv_color, item.getInventory().getColor().getColor());
        helper.setText(R.id.tv_price, "￥ " + item.getInventory().getPrice());



        ((EditNumView)helper.getView(R.id.edit_num)).setResult(item.getCount());
        ((EditNumView)helper.getView(R.id.edit_num)).setDefault_result(1);
        ((EditNumView)helper.getView(R.id.edit_num)).setGetNumListener(new EditNumView.GetNumListener() {
            @Override
            public void getNum(int num) {
                item.setCount(num);
                editCartGoodsCount(String.valueOf(item.getId()),String.valueOf(num));
                if(childCheckChangeListener!=null){
                    childCheckChangeListener.checkChange();
                }
            }
        });

    }


    private void editCartGoodsCount(String cartgoodsid,String count){
        HttpUtils.getInstance().editCartGoodsCount(cartgoodsid, count, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                Log.e("onSuccess",response.body().toString());
            }
        });
    }
}

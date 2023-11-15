package com.feicui365.live.shop.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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

public class ShopCarChildAdapter extends BaseQuickAdapter<CartGoods, BaseViewHolder> {

    ChildCheckChangeListener childCheckChangeListener;

    public interface ChildCheckChangeListener {
        void checkChange(boolean all_check);
    }

    public ChildCheckChangeListener getCheckChangeListener() {
        return childCheckChangeListener;
    }

    public void setCheckChangeListener(ChildCheckChangeListener childCheckChangeListener) {
        this.childCheckChangeListener = childCheckChangeListener;
    }

    public ShopCarChildAdapter(@Nullable List<CartGoods> data) {
        super(R.layout.shop_car_child_item, data);
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
        Log.e("convert2",item.isCheck()+"     "+helper.getLayoutPosition()+"    "+item.getGoods().getId());
        if(item.isCheck()){
            ((CheckBox)helper.getView(R.id.cb_all)).setChecked(true);
        }else{
            ((CheckBox)helper.getView(R.id.cb_all)).setChecked(false);
        }



        ((RelativeLayout)helper.getView(R.id.rl_check)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //复选框点击事件
                //每次勾选需要做一次检测
                if(((CheckBox)helper.getView(R.id.cb_all)).isChecked()){
                    ((CheckBox)helper.getView(R.id.cb_all)).setChecked(false);
                    item.setCheck(false);
                }else{
                    ((CheckBox)helper.getView(R.id.cb_all)).setChecked(true);
                    item.setCheck(true);
                }


                if(childCheckChangeListener!=null){
                    childCheckChangeListener.checkChange(checkChangeResult());
                }

            }
        });

        ((EditNumView)helper.getView(R.id.edit_num)).setResult(item.getCount());
        ((EditNumView)helper.getView(R.id.edit_num)).setDefault_result(1);
        ((EditNumView)helper.getView(R.id.edit_num)).setGetNumListener(new EditNumView.GetNumListener() {
            @Override
            public void getNum(int num) {
                item.setCount(num);
                editCartGoodsCount(String.valueOf(item.getId()),String.valueOf(num));
                if(childCheckChangeListener!=null){
                    childCheckChangeListener.checkChange(checkChangeResult());
                }
            }
        });

    }


    public void myNotifyData(boolean check) {
        if (getData() == null) {
            return;
        }
        if (getData().size() == 0) {
            return;
        }
        for (int i = 0; i < getData().size(); i++) {
            getData().get(i).setCheck(check);
        }
        notifyDataSetChanged();
    }

    public boolean checkChangeResult(){
        //只关心是否全部选中

        boolean check=true;
        for (int i = 0; i < getData().size(); i++) {
           if(getData().get(i).isCheck()==false){
               check=false;
               break;
           }
        }

        return check;
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

package com.feicui365.live.shop.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.shop.entity.AddInventory;

import java.util.List;

public class AddSpeceAdapter extends BaseQuickAdapter<AddInventory, BaseViewHolder> {

    public AddSpeceAdapter( @Nullable List<AddInventory> data) {
        super(R.layout.add_spece_item, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AddInventory item) {

        if(item.getColor().equals("")){
            helper.setGone(R.id.tv_color,false);
        }else{
            helper.setGone(R.id.tv_color,true);
            helper.setText(R.id.tv_color,"颜色分类 "+item.getColor());
        }


        if(item.getSize().equals("")){

            helper.setGone(R.id.tv_size,false);
        }else{
            helper.setGone(R.id.tv_size,true);
            helper.setText(R.id.tv_size,"尺码分类 "+item.getSize());
        }


        if(item.getLeft_count()!=0){
            helper.setText(R.id.et_left_count,String.valueOf(item.getLeft_count()));
        }else{
            helper.setText(R.id.et_left_count,"");
        }

        if(item.getPrice()!=null){
            helper.setText(R.id.et_price,String.valueOf(item.getPrice()));
        }else{
            helper.setText(R.id.et_price,"");
        }

        //价格监听
        EditText et_price=helper.getView(R.id.et_price);
        if(et_price.getTag()!=null&& et_price.getTag() instanceof  TextWatcher){
            et_price.removeTextChangedListener((TextWatcher) et_price.getTag());
        }

        TextWatcher textWatcher_price = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if("".equals(editable.toString())){
                    return;
                }
                //对列表中的数据集合进行刷新,并刷新列表
                item.setPrice(editable.toString());
            }
        };
        et_price.addTextChangedListener(textWatcher_price);
        et_price.setTag(textWatcher_price);


        //数量监听


        EditText et_left_count=helper.getView(R.id.et_left_count);
        if(et_left_count.getTag()!=null&& et_left_count.getTag() instanceof  TextWatcher){
            et_left_count.removeTextChangedListener((TextWatcher) et_left_count.getTag());
        }

        TextWatcher textWatcher_count = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //对列表中的数据集合进行刷新,并刷新列表
                if("".equals(editable.toString())){
                    return;
                }
                item.setLeft_count(Integer.parseInt(editable.toString()));
            }
        };
        et_left_count.addTextChangedListener(textWatcher_count);
        et_left_count.setTag(textWatcher_count);
    }


}

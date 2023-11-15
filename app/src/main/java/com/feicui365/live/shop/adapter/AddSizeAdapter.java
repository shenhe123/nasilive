package com.feicui365.live.shop.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.shop.entity.AddSize;
import com.feicui365.live.util.ToastUtils;

import java.util.List;

public class AddSizeAdapter extends BaseQuickAdapter<AddSize, BaseViewHolder> {
    private OnAddSizeListener onAddSizeListener;


    public interface OnAddSizeListener{


        void sumbitSize();
    }

    public OnAddSizeListener getOnAddSizeListener() {
        return onAddSizeListener;
    }

    public void setOnAddSizeListener(OnAddSizeListener onAddSizeListener) {
        this.onAddSizeListener = onAddSizeListener;
    }

    public AddSizeAdapter(@Nullable List<AddSize> data) {
        super(R.layout.add_good_size_item, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AddSize item) {


        //区分是否已经添加
        if(item.getTag()==null){
            helper.setGone(R.id.iv_submit,true);
            helper.setGone(R.id.iv_cancel,false);
        }else{
            helper.setGone(R.id.iv_submit,false);
            helper.setGone(R.id.iv_cancel,true);
        }


        if(item.getSize()==null){
            helper.setText(R.id.et_size,"");
        }else {
            helper.setText(R.id.et_size,item.getSize());
        }

        helper.getView(R.id.iv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((((EditText)helper.getView(R.id.et_size)).getText().toString()).equals("")){
                    ToastUtils.showT("请输入尺码后在确认");
                    return;
                }

                helper.getView(R.id.et_size).setEnabled(false);
                helper.setGone(R.id.iv_submit,false);
                helper.setGone(R.id.iv_cancel,true);
                item.setSize( (((EditText)helper.getView(R.id.et_size)).getText().toString()));
                item.setTag("1");
                if(onAddSizeListener!=null){
                    onAddSizeListener.sumbitSize();
                }
            }
        });

        helper.getView(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              remove(helper.getLayoutPosition());
                if(onAddSizeListener!=null){
                    onAddSizeListener.sumbitSize();
                }
            }
        });


    }
}

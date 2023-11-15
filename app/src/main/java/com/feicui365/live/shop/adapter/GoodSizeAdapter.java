package com.feicui365.live.shop.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.shop.entity.Size;
import com.feicui365.live.shop.interfaces.OnChoseListener;

import java.util.List;

public class GoodSizeAdapter extends BaseQuickAdapter<Size, BaseViewHolder> {

    private int chose_id = -1;

    private OnChoseListener onChoseSizeListener;

    public void setOnChoseSizeListener(OnChoseListener onChoseSizeListener) {
        this.onChoseSizeListener = onChoseSizeListener;
    }

    public GoodSizeAdapter(@Nullable List<Size> data) {
        super(R.layout.goods_size, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Size item) {

        //判断颜色
        if (item.isChose()) {
            helper.setBackgroundRes(R.id.tv_size, R.drawable.good_color_line_chose);
            helper.setTextColor(R.id.tv_size, helper.itemView.getResources().getColor(R.color.color_FF4386));
        } else {
            helper.setBackgroundRes(R.id.tv_size, R.drawable.good_color_line_unchose);
            helper.setTextColor(R.id.tv_size, helper.itemView.getResources().getColor(R.color.color_333333));
        }
        //填充文字
        helper.setText(R.id.tv_size, item.getSize());

        //点击事件
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断情况,1自己点击自己,2自己选中时点击别人
                //表示当前没有任何一个被选中

                    chose_id = item.getId();
                    item.setChose(true);


                if (onChoseSizeListener != null) {
                    onChoseSizeListener.onChose(chose_id);
                }
                reData(chose_id);
                notifyDataSetChanged();


            }
        });
    }

    public void reData(int id) {
        for (int i = 0; i < getData().size(); i++) {
            if (getData().get(i).getId() != id) {
                getData().get(i).setChose(false);
            }
        }
    }

}

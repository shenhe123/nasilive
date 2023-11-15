package com.feicui365.live.shop.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.shop.entity.Color;
import com.feicui365.live.shop.interfaces.OnChoseListener;

import java.util.List;

public class GoodColorAdapter extends BaseQuickAdapter<Color, BaseViewHolder> {
    private int chose_id = -1;

    public GoodColorAdapter(@Nullable List<Color> data) {
        super(R.layout.goods_color, data);
    }

    private OnChoseListener onChoseColorListener;

    public void setOnChoseColorListener(OnChoseListener onChoseColorListener) {
        this.onChoseColorListener = onChoseColorListener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Color item) {
        //判断颜色
        if (item.isChose()) {
            chose_id = item.getId();
            helper.setBackgroundRes(R.id.tv_color, R.drawable.good_color_line_chose);
            helper.setTextColor(R.id.tv_color, helper.itemView.getResources().getColor(R.color.color_FF4386));
        } else {
            helper.setBackgroundRes(R.id.tv_color, R.drawable.good_color_line_unchose);
            helper.setTextColor(R.id.tv_color, helper.itemView.getResources().getColor(R.color.color_333333));
        }
        //填充文字
        helper.setText(R.id.tv_color, item.getColor());

        //点击事件
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断情况,1自己点击自己,2自己选中时点击别人
                //表示当前没有任何一个被选中

                //如果已经有被选择了


                chose_id = item.getId();
                item.setChose(true);


                if (onChoseColorListener != null) {
                    onChoseColorListener.onChose(chose_id);
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

package com.feicui365.live.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.model.entity.Guardian;

import java.util.List;

/*import android.support.v7.widget.RecyclerView;*/

public class GuardianListAdapter extends BaseMultiItemQuickAdapter<Guardian, BaseViewHolder> {

    Context context;


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public GuardianListAdapter(Context context, List<Guardian> data,String type) {
        super(data);
        this.context = context;

        addItemType(1, R.layout.guardian_item_head);
        addItemType(2, R.layout.guardian_item_head);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Guardian item) {
            switch (helper.getLayoutPosition()){
                default:
                    break;
            }

    }

}
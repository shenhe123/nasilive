package cn.tillusory.tiui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwangjr.rxbus.RxBus;

import java.util.List;

import cn.tillusory.tiui.R;
import cn.tillusory.tiui.model.RxBusAction;
import cn.tillusory.tiui.model.TiFaceTrim;

/**
 * Created by Anko on 2018/11/25.
 * Copyright (c) 2018-2020 拓幻科技 - tillusory.cn. All rights reserved.
 */
public class TiFaceTrimAdapter extends RecyclerView.Adapter<TiDesViewHolder> {

    private List<TiFaceTrim> list;

    private int selectedPosition = 0;

    public TiFaceTrimAdapter(List<TiFaceTrim> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public TiDesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ti_des, parent, false);
        return new TiDesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TiDesViewHolder holder, int position) {
        if (position == 0) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            p.setMargins((int) (holder.itemView.getContext().getResources().getDisplayMetrics().density * 20 + 0.5f), 0, 0, 0);
            holder.itemView.requestLayout();
        } else {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            p.setMargins(0, 0, 0, 0);
            holder.itemView.requestLayout();
        }
        holder.tiTextTV.setText(list.get(position).getString(holder.itemView.getContext()));
        holder.tiImageIV.setImageDrawable(list.get(position).getImageDrawable(holder.itemView.getContext()));

        if (selectedPosition == position) {
            holder.tiTextTV.setSelected(true);
            holder.tiImageIV.setSelected(true);
        } else {
            holder.tiTextTV.setSelected(false);
            holder.tiImageIV.setSelected(false);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = holder.getAdapterPosition();
                switch (list.get(holder.getAdapterPosition())) {
                    case EYE_MAGNIFYING:
                        RxBus.get().post(RxBusAction.ACTION_EYE_MAGNIFYING);
                        break;
                    case CHIN_SLIMMING:
                        RxBus.get().post(RxBusAction.ACTION_CHIN_SLIMMING);
                        break;
                    case JAW_TRANSFORMING:
                        RxBus.get().post(RxBusAction.ACTION_JAW_TRANSFORMING);
                        break;
                    case FOREHEAD_TRANSFORMING:
                        RxBus.get().post(RxBusAction.ACTION_FOREHEAD_TRANSFORMING);
                        break;
                    case MOUTH_TRANSFORMING:
                        RxBus.get().post(RxBusAction.ACTION_MOUTH_TRANSFORMING);
                        break;
                    case NOSE_MINIFYING:
                        RxBus.get().post(RxBusAction.ACTION_NOSE_MINIFYING);
                        break;
                    case TEETH_WHITENING:
                        RxBus.get().post(RxBusAction.ACTION_TEETH_WHITENING);
                        break;
                    case FACE_NARROWING:
                        RxBus.get().post(RxBusAction.ACTION_FACE_NARROWING);
                        break;
                    case EYE_SPACING:
                        RxBus.get().post(RxBusAction.ACTION_EYE_SPACING);
                        break;
                    case NOSE_ELONGATING:
                        RxBus.get().post(RxBusAction.ACTION_NOSE_ELONGATING);
                        break;
                    case EYE_CORNERS:
                        RxBus.get().post(RxBusAction.ACTION_EYE_CORNERS);
                        break;
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
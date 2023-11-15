package com.feicui365.live.ui.adapter;

import android.content.Context;
import android.icu.text.DecimalFormat;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/*import android.support.v7.widget.RecyclerView;*/

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.model.entity.HotLive;
import com.feicui365.live.util.LevelUtils;

import java.util.List;

public class HomeLiveAdapter extends BaseQuickAdapter<HotLive, BaseViewHolder> {
    private com.feicui365.live.ui.os.OnItemClickListener onItemClickListener;
    Context context;

    public HomeLiveAdapter(Context context, int layoutResId, @Nullable List<HotLive> data) {
        super(layoutResId, data);
        this.context = context;
    }

    public void setOnItemClickListener(com.feicui365.live.ui.os.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HotLive item) {

        Glide.with(context).applyDefaultRequestOptions(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.zhanwei)).load(item.getThumb()).into((ImageView) helper.getView(R.id.iv_cover));
        ((TextView) helper.getView(R.id.tv_name)).setText(item.getAnchor().getNick_name());
        ((TextView) helper.getView(R.id.tv_title)).setText(item.getTitle());


        if (Integer.valueOf(item.getHot()) > 10000) {

            ((TextView) helper.getView(R.id.tv_hot)).setText(getintimacy(Integer.valueOf(item.getHot()) * 1f / 10000) + "万");
        } else {

            ((TextView) helper.getView(R.id.tv_hot)).setText(item.getHot() + "");
        }

        helper.getView(R.id.root_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(item);
                }

            }
        });

        switch (item.getRoom_type()) {

            case "1":

                Glide.with(context).applyDefaultRequestOptions(new RequestOptions().centerCrop()).load(R.mipmap.privateroom).into((ImageView) helper.getView(R.id.iv_room_type));

                break;
            case "2":
                Glide.with(context).applyDefaultRequestOptions(new RequestOptions().centerCrop()).load(R.mipmap.pay).into((ImageView) helper.getView(R.id.iv_room_type));

                break;

            default:
                Glide.with(context).applyDefaultRequestOptions(new RequestOptions().centerCrop()).load(R.mipmap.ordinary).into((ImageView) helper.getView(R.id.iv_room_type));
                break;

        }

        if (item.getAnchor().getAnchor_level() != 0) {
            helper.setImageResource(R.id.iv_anchor_level, LevelUtils.getAnchorLevel(item.getAnchor().getAnchor_level()));
        } else {

            helper.getView(R.id.iv_anchor_level).setVisibility(View.GONE);
        }

        if (item.getPk_status() == 1) {
            helper.getView(R.id.iv_pk_type).setVisibility(View.VISIBLE);
            helper.getView(R.id.iv_room_type).setVisibility(View.GONE);

        } else {
            helper.getView(R.id.iv_pk_type).setVisibility(View.GONE);
            helper.getView(R.id.iv_room_type).setVisibility(View.VISIBLE);
        }
    }

    private String getintimacy(Float f) {
        String data = "";
        DecimalFormat format = new DecimalFormat(".0");
        data = format.format(f);
        return data;
    }

}
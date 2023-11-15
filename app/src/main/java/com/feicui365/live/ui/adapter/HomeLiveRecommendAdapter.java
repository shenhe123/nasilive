package com.feicui365.live.ui.adapter;

import android.content.Context;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.model.entity.HotLive;
import com.feicui365.live.util.LevelUtils;

import java.util.List;

/*import android.support.v7.widget.RecyclerView;*/

public class HomeLiveRecommendAdapter extends BaseMultiItemQuickAdapter<HotLive, BaseViewHolder> {
    private OnItemClickListener onItemClickListener;
    Context context;
    boolean is_have_banner = false;
    int rank_postion = 2;

    public HomeLiveRecommendAdapter(Context context, boolean is_have_banner, @Nullable List<HotLive> data) {
        super(data);
        this.context = context;
        addItemType(1, R.layout.home_item);
        addItemType(2, R.layout.home_item_rank);
        this.is_have_banner = is_have_banner;
        if (is_have_banner) {
            rank_postion = 3;
        } else {
            rank_postion = 2;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(HotLive live, int postion);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void convert(@NonNull BaseViewHolder helper, HotLive item) {

        if (helper.getLayoutPosition() == rank_postion) {
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(item, helper.getLayoutPosition());
                    }
                }
            });
        } else {

            if (item.getAnchorid() == null) {
                RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) helper.itemView.getLayoutParams();
                param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                param.width = LinearLayout.LayoutParams.MATCH_PARENT;
                helper.itemView.setVisibility(View.GONE);
                param.height = 0;
                param.width = 0;
                helper.itemView.setLayoutParams(param);
                return;
            }


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
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(item, helper.getLayoutPosition());
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

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getintimacy(Float f) {
        String data = "";
        DecimalFormat format = new DecimalFormat(".0");
        data = format.format(f);
        return data;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == rank_postion) {
            return 2;
        } else {
            return super.getItemViewType(position);
        }

    }
}
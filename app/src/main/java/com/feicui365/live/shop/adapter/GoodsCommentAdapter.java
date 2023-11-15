package com.feicui365.live.shop.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.shop.entity.Evaluate;

import java.util.List;

import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import de.hdodenhof.circleimageview.CircleImageView;

public class GoodsCommentAdapter extends BaseQuickAdapter<Evaluate, BaseViewHolder> {


    public GoodsCommentAdapter(@Nullable List<Evaluate> data) {
        super(R.layout.goods_comment_item, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Evaluate item) {
        Glide.with(helper.itemView)
                .applyDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(R.mipmap.moren))
                .load(item.getUser().getAvatar())
                .into((CircleImageView) helper.getView(R.id.iv_pic));

        helper.setText(R.id.tv_nickname, item.getUser().getNick_name());
        helper.setText(R.id.tv_time, item.getCreate_time());
        helper.setText(R.id.tv_content, item.getContent());
        helper.setText(R.id.tv_color, item.getOrdergoods().getColor());

        if (item.getImg_list()==null) {
            helper.setGone(R.id.snpl_moment_add_photos,false);
        }else{
            helper.setGone(R.id.snpl_moment_add_photos,true);
            ((BGASortableNinePhotoLayout) helper.getView(R.id.snpl_moment_add_photos)).setData(item.getImg_list());
        }

    }
}

package com.feicui365.live.live.adapter;

import android.view.View;
import android.widget.LinearLayout;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.feicui365.live.R;
import com.feicui365.live.base.MyApp;
import com.feicui365.live.interfaces.OnCancelClickLinstener;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.live.utils.GlideUtils;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.util.LevelUtils;

import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class LiveBanAdapter extends BaseQuickAdapter<UserRegist, BaseViewHolder> {

    LinearLayout.LayoutParams p;
    OnCancelClickLinstener onCancelClickLinstener;

    public void setOnCancelClickLinstener(OnCancelClickLinstener onCancelClickLinstener) {
        this.onCancelClickLinstener = onCancelClickLinstener;
    }
    public LiveBanAdapter() {
        super(R.layout.user_item);

    }

//    private  LinearLayout.LayoutParams initLayoutPrama() {
//        if(p==null){
//            p = new LinearLayout.LayoutParams(ArmsUtils.dip2px(MyApp.getInstance(), 58), ArmsUtils.dip2px(MyApp.getInstance(), 24));
//
//        }
//
//        return p;
//    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, UserRegist bean) {
        GlideUtils.setImage(mContext,bean.getAvatar(),R.mipmap.moren, helper.getView(R.id.civ_avatar));

        if (ArmsUtils.getVipLevelIcon(bean.getVip_level(), bean.getVip_date()) == 0) {
            helper.getView(R.id.iv_vip).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.iv_vip).setVisibility(View.GONE);
            helper.setImageResource(R.id.iv_vip, ArmsUtils.getVipLevelIcon(bean.getVip_level(), bean.getVip_date()));
        }

        helper.setImageResource(R.id.iv_user_level, LevelUtils.getUserLevel(bean.getUser_level()));
        helper.getView(R.id.iv_item_status).setVisibility(View.GONE);
        helper.setText(R.id.tv_name, bean.getNick_name());

       //   helper.getView(R.id.iv_item_status_2).setLayoutParams( initLayoutPrama());
       // helper.setImageResource(R.id.iv_item_status_2, R.drawable.ic_cancel_room_ban);
        helper.getView(R.id.iv_item_status_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onCancelClickLinstener!=null){
                    onCancelClickLinstener.onCancelClick(bean.getId(),helper.getLayoutPosition());
                }
            }
        });
    }
}

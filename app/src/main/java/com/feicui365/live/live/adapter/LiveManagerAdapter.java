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
import com.feicui365.live.model.entity.RoomManager;
import com.feicui365.live.util.LevelUtils;

import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class LiveManagerAdapter  extends BaseQuickAdapter<RoomManager, BaseViewHolder> {

    LinearLayout.LayoutParams p;
    OnCancelClickLinstener onCancelClickLinstener;

    public void setOnCancelClickLinstener(OnCancelClickLinstener onCancelClickLinstener) {
        this.onCancelClickLinstener = onCancelClickLinstener;
    }

    public LiveManagerAdapter() {
        super(R.layout.user_item_2);

    }

    private  LinearLayout.LayoutParams initLayoutPrama() {
        if(p==null){
            p = new LinearLayout.LayoutParams(ArmsUtils.dip2px(MyApp.getInstance(), 58), ArmsUtils.dip2px(MyApp.getInstance(), 24));

        }

        return p;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, RoomManager bean) {
        if(bean.getUser()==null){
            GlideUtils.setImage(mContext,R.mipmap.moren, helper.getView(R.id.civ_avatar));
            helper.getView(R.id.iv_vip).setVisibility(View.GONE);
            helper.getView(R.id.iv_user_level).setVisibility(View.GONE);
            helper.setText(R.id.tv_name,"该用户已失效");
        }else{
            GlideUtils.setImage(mContext,bean.getUser().getAvatar(),R.mipmap.moren, helper.getView(R.id.civ_avatar));

            if (ArmsUtils.getVipLevelIcon(bean.getUser().getVip_level(), bean.getUser().getVip_date()) == 0) {
                helper.getView(R.id.iv_vip).setVisibility(View.GONE);
            } else {
                helper.getView(R.id.iv_vip).setVisibility(View.VISIBLE);
                helper.setImageResource(R.id.iv_vip, ArmsUtils.getVipLevelIcon(bean.getUser().getVip_level(), bean.getUser().getVip_date()));
            }
            helper.getView(R.id.iv_user_level).setVisibility(View.GONE);
            helper.setImageResource(R.id.iv_user_level, LevelUtils.getUserLevel(bean.getUser().getUser_level()));
            helper.getView(R.id.iv_item_status).setVisibility(View.GONE);
            helper.setText(R.id.tv_name, bean.getUser().getNick_name());
        }




//        helper.getView(R.id.iv_item_status_2).setLayoutParams(initLayoutPrama());
//        helper.setImageResource(R.id.iv_item_status_2, R.drawable.ic_cancel_room_manager);
        helper.getView(R.id.iv_item_status_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onCancelClickLinstener!=null){
                    onCancelClickLinstener.onCancelClick(bean.getMgrid(),helper.getLayoutPosition());
                }
            }
        });
    }
}

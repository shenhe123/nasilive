package com.feicui365.live.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.feicui365.live.R;
import com.feicui365.live.base.OtherBaseFragment;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.model.entity.VipPrice;
import com.feicui365.live.wxapi.PayCallback;
import com.feicui365.live.util.MyUserInstance;

@SuppressLint("ValidFragment")
public class ItemMemberFragment extends OtherBaseFragment implements PayCallback {

    public ImageView iv_icon_3, iv_icon_4, iv_icon_6, iv_icon_7, iv_icon_9, iv_icon, iv_pay;
    RelativeLayout rl_bottom;
    public int type;
    VipPrice vipPrice;
    TextView tv_song_coin, tv_time,tv_time_go;


    public ItemMemberFragment(int i, VipPrice vipPrice) {
        type = i;
        this.vipPrice = vipPrice;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_member_fragment, container, false);
        iv_icon_3 = view.findViewById(R.id.iv_icon_3);
        iv_icon_4 = view.findViewById(R.id.iv_icon_4);
        iv_icon_6 = view.findViewById(R.id.iv_icon_6);
        iv_icon_7 = view.findViewById(R.id.iv_icon_7);
        iv_icon_9 = view.findViewById(R.id.iv_icon_9);
        rl_bottom=view.findViewById(R.id.rl_bottom);
        iv_icon = view.findViewById(R.id.iv_icon);
        tv_time_go=view.findViewById(R.id.tv_time_go);
        tv_song_coin = view.findViewById(R.id.tv_song_coin);
        tv_time = view.findViewById(R.id.tv_time);
        if (vipPrice != null) {
            tv_song_coin.setText(vipPrice.getGold());
            tv_time.setText(vipPrice.getPrice());
        }
        initView(type);
        MyUserInstance.getInstance().getWxPayBuilder(getContext()).setPayCallback(this);


        MyUserInstance.getInstance().getAliPayBuilder(getActivity()).setPayCallback(this);
        iv_pay = view.findViewById(R.id.iv_pay);
        iv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toPay(type + "");
            }
        });

        if(MyUserInstance.getInstance().getUserinfo().getVip_date()!=null){
            if(MyUserInstance.getInstance().OverTime(MyUserInstance.getInstance().getUserinfo().getVip_date())){


                        if (MyUserInstance.getInstance().getUserinfo().getVip_level() > type) {
                            rl_bottom.setVisibility(View.GONE);
                        }
                        if (MyUserInstance.getInstance().getUserinfo().getVip_level() == type) {
                            iv_pay.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.button_xufei));
                            tv_time_go.setText("VIP时间"+MyUserInstance.getInstance().getUserinfo().getVip_date());
                        }


            }
        }



        return view;
    }

    private void initView(int type) {
        switch (type) {
            case 1:
                Glide.with(getContext()).load(R.mipmap.lianmai_off).into(iv_icon_3);
                Glide.with(getContext()).load(R.mipmap.gift_off).into(iv_icon_4);
                Glide.with(getContext()).load(R.mipmap.huanying_off).into(iv_icon_6);
                Glide.with(getContext()).load(R.mipmap.dongtai_2_off).into(iv_icon_7);
                Glide.with(getContext()).load(R.mipmap.jinyan_off).into(iv_icon_9);
                break;
            case 2:
                Glide.with(getContext()).load(R.mipmap.dongtai_2_off).into(iv_icon_7);
                Glide.with(getContext()).load(R.mipmap.jinyan_off).into(iv_icon_9);
                Glide.with(getContext()).load(R.mipmap.buy_qishi).into(iv_icon);
                break;
            case 3:
                Glide.with(getContext()).load(R.mipmap.jinyan_off).into(iv_icon_9);
                Glide.with(getContext()).load(R.mipmap.buy_gongjue).into(iv_icon);
                break;
            case 4:

                Glide.with(getContext()).load(R.mipmap.buy_king).into(iv_icon);
                break;
        }

    }


    @Override
    public void onSuccess() {

        ToastUtils.showT("充值成功");
    }

    @Override
    public void onFailed() {
        ToastUtils.showT("充值失败");
    }

    private void toPay(String level) {
        new XPopup.Builder(getActivity())
                .hasShadowBg(true)
                .asBottomList("支付", new String[]{"微信","支付宝"},
                        new int[]{R.mipmap.wechat, R.mipmap.alipay},
                        new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                switch (position) {
                                    case 0:
                                        MyUserInstance.getInstance().getWxPayBuilder(getContext()).payVip(level);
                                        break;
                                    case 1:
                                        MyUserInstance.getInstance().getAliPayBuilder(getActivity()).payVip(level);
                                        break;
                                }
                            }
                        })
                .show();
    }
}

package com.feicui365.live.live.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


import com.feicui365.live.R;
import com.feicui365.live.base.MyApp;
import com.feicui365.live.base.SocketConstants;
import com.feicui365.live.bean.ImMessage;
import com.feicui365.live.interfaces.ChatClickLinstener;
import com.feicui365.live.live.bean.Chat;

import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.live.utils.GlideUtils;
import com.feicui365.live.live.utils.TextColorSizeHelper;
import com.feicui365.live.live.weight.LiveImageSpan;
import com.feicui365.live.model.entity.GiftInfo;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.util.LevelUtils;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.TextRender;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;


public class LiveChatVerticalAdapter extends BaseQuickAdapter<ImMessage, BaseViewHolder> {

    LinearLayout.LayoutParams lp;

    ForegroundColorSpan nameSpan;
    ForegroundColorSpan addressSpan;
    ForegroundColorSpan messageSpan;
    ForegroundColorSpan systemSpan;
    BackgroundColorSpan addressBgSpan;
    ImageSpan addressBgIdSpan;

    SpannableStringBuilder builder;
    ChatClickLinstener chatClickLinstener;


    public void setChatClickLinstener(ChatClickLinstener chatClickLinstener) {
        this.chatClickLinstener = chatClickLinstener;
    }

    public LiveChatVerticalAdapter() {
        super(R.layout.item_chat_vertical);

        nameSpan = new ForegroundColorSpan(MyApp.getInstance().getResources().getColor(R.color.blue_color));
        systemSpan = new ForegroundColorSpan(MyApp.getInstance().getResources().getColor(R.color.color_FFEE30));
        messageSpan = new ForegroundColorSpan(MyApp.getInstance().getResources().getColor(R.color.white));
        addressSpan = new ForegroundColorSpan(MyApp.getInstance().getResources().getColor(R.color.chat_address_font));
//        addressBgSpan = new BackgroundColorSpan(MyApp.getInstance().getResources().get(R.drawable.account_bg));
        addressBgIdSpan= new ImageSpan(MyApp.getInstance().getResources().getDrawable(R.drawable.account_bg));
    }

    private LinearLayout.LayoutParams getLayoutParams() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, ImMessage item) {
        //自定义点击事件
        initChatClick(helper, item);

        //事件分类
        switch (item.getAction()) {
            //直播间通知消息
            case SocketConstants.LIVE_ACTION_RoomNotification:

                switch (item.getData().getNotify().getType()) {


                    //设置管理员
                    case SocketConstants.SOCKET_NOTIFY_RoomNotifyTypeSetManager:
                        //取消管理员
                    case SocketConstants.SOCKET_NOTIFY_RoomNotifyTypeCancelManager:
                        initManagerMessage(helper, item);
                        break;
                    //禁言
          /*         case SocketConstants.SOCKET_NOTIFY_RoomNotifyTypeUserBeBanned:
                        initBanMessage(helper, item);
                        break;*/
                    //主播连麦开启
                    case SocketConstants.SOCKET_NOTIFY_RoomNotifyTypeLinkOn:

                        //主播连麦关闭
                    case SocketConstants.SOCKET_NOTIFY_RoomNotifyTypeLinkOff:
                        initLinkMessage(helper, item);
                        break;
                    //守护主播
                    case SocketConstants.SOCKET_NOTIFY_RoomNotifyTypeGuardAnchor:
                        initGuardianMessage(helper, item);
                        break;

                    case SocketConstants.SOCKET_NOTIFY_RoomNotifyNotice:
                        //   initLiveNoticeSystem(helper);
                        break;

                }


                break;
            //直播间对话消息
            case SocketConstants.LIVE_ACTION_RoomMessage:
                initChatMessage(helper, item);
                break;
            case SocketConstants.LIVE_ACTION_GiftAnimation:
                initChatGift(helper, item);
                break;

        }


    }

    private void initChatClick(BaseViewHolder helper, ImMessage item) {
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (chatClickLinstener != null) {
                    chatClickLinstener.onChatClick(item);
                }

            }
        });
    }


    private void initChatMessage(BaseViewHolder helper, ImMessage item) {
        CircleImageView circle_iv = helper.getView(R.id.circle_iv);
        if (item.getData().getChat() == null) {
            return;
        }
        UserRegist user = item.getData().getChat().getSender();
        Chat mChat = item.getData().getChat();
        if (user == null) {
            return;
        }
        String nickName = user.getNick_name()+"  ";
        String address = "";
        if (!TextUtils.isEmpty(user.getProvince()) && user.getProvince().equals(user.getCity())) {
            address = user.getCity() + user.getArea();
        } else {
            address = user.getProvince() + user.getCity() + user.getArea();
        }
        builder = new SpannableStringBuilder();
        builder.append(nickName);
        //顺序为,守护>等级>管理>VIP
//        builder.append("：");
        builder.append(address);
        builder.append("  ");
        builder.append(TextRender.renderChatMessage2(item.getData().getChat().getMessage()));

        //调整名字文字颜色
        builder.setSpan(nameSpan, 0, nickName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        //添加图标
        builder.setSpan(new TextColorSizeHelper.RadiusBackgroundSpan(MyApp.getInstance().getResources().getColor(R.color.chat_address_font),
                        MyApp.getInstance().getResources().getColor(R.color.chat_address_bg),25), nickName.length(),
                nickName.length() + address.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        View markerView = LayoutInflater.from(MyApp.getInstance()).inflate(R.layout.item_chat_address_vertical, null);
        builder.setSpan(addressSpan, nickName.length(),
                nickName.length() + address.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


//        builder.setSpan(new MarkerViewSpan(markerView), nickName.length(),
//                nickName.length() + address.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

//        Drawable drawableManager = MyApp.getInstance().getResources().getDrawable(R.drawable.ic_chat_live_manager);
//        drawableManager.setBounds(0, 0, ArmsUtils.dip2px(mLayoutInflater.getContext(), 34), ArmsUtils.dip2px(mLayoutInflater.getContext(), 15));
//        LiveImageSpan i_admin = new LiveImageSpan(drawableManager);
//        builder.setSpan(i_admin, nickName.length(),
//                nickName.length() + address.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //vip
      /*  if (user.getVip_date() != null) {
            int vip = ArmsUtils.getVipLevelIcon(user.getVip_level(), user.getVip_date());
            if (vip != 0) {
                builder.insert(0, "\uFFFC");
                Drawable drawableVip = mLayoutInflater.getContext().getResources().getDrawable(vip);
                drawableVip.setBounds(0, 0, ArmsUtils.dip2px(mLayoutInflater.getContext(), 17), ArmsUtils.dip2px(mLayoutInflater.getContext(), 21));
                LiveImageSpan imageSpanVip = new LiveImageSpan(drawableVip);
                builder.setSpan(imageSpanVip, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            }
        }
        //管理
        if (mChat.getIs_manager() == 1) {
            builder.insert(0, "\uFFFC");
            Drawable drawableManager = mLayoutInflater.getContext().getResources().getDrawable(R.drawable.ic_chat_live_manager);
            drawableManager.setBounds(0, 0, ArmsUtils.dip2px(mLayoutInflater.getContext(), 34), ArmsUtils.dip2px(mLayoutInflater.getContext(), 15));
            LiveImageSpan i_admin = new LiveImageSpan(drawableManager);
            builder.setSpan(i_admin, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        //等级
        builder.insert(0, "\uFFFC");
        Drawable drawableLevel = mLayoutInflater.getContext().getResources().getDrawable(LevelUtils.getUserLevel(user.getUser_level()));
        drawableLevel.setBounds(0, 0, ArmsUtils.dip2px(mLayoutInflater.getContext(), 34), ArmsUtils.dip2px(mLayoutInflater.getContext(), 15));
        LiveImageSpan imageSpanLevel = new LiveImageSpan(drawableLevel);
        builder.setSpan(imageSpanLevel, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //守护
        if (mChat.getIs_guardian() == 1) {

            builder.insert(0, "\uFFFC");
            Drawable drawableGuardian = mLayoutInflater.getContext().getResources().getDrawable(R.drawable.ic_chat_guardian);
            drawableGuardian.setBounds(0, 0, ArmsUtils.dip2px(mLayoutInflater.getContext(), 34), ArmsUtils.dip2px(mLayoutInflater.getContext(), 15));
            LiveImageSpan imageSpanGuardian = new LiveImageSpan(drawableGuardian);
            builder.setSpan(imageSpanGuardian, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);


        }
*/
//        Drawable drawableLevel = getDrawableGlide(user.getAvatar());
//        drawableLevel.setBounds(0, 0, ArmsUtils.dip2px(mLayoutInflater.getContext(), 34), ArmsUtils.dip2px(mLayoutInflater.getContext(), 15));
//        LiveImageSpan imageSpanLevel = new LiveImageSpan(drawableLevel);
//        builder.setSpan(imageSpanLevel, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        Glide.with(mContext).load(user.getAvatar()).into(circle_iv);


        if (mChat.getIs_manager() == 1) {
            int result = TextRender.renderChatMessage2(item.getData().getChat().getMessage()).length();
            int result2 = builder.length();
            builder.setSpan(systemSpan, result2 - result, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }


        lp = getLayoutParams();
        lp.setMargins(ArmsUtils.dip2px(mLayoutInflater.getContext(), 8), ArmsUtils.dip2px(mLayoutInflater.getContext(), 0)
                , ArmsUtils.dip2px(mLayoutInflater.getContext(), 8), ArmsUtils.dip2px(mLayoutInflater.getContext(), 4));
        //  helper.getView(R.id.tv_message).setLayoutParams(lp);
        helper.setText(R.id.tv_message, builder);


//        if (!TextUtils.isEmpty(address)) {
//            helper.setText(R.id.tv_address, address);
//            helper.getView(R.id.tv_address).setVisibility(View.VISIBLE);
//        } else {
//            helper.getView(R.id.tv_address).setVisibility(View.GONE);
//        }
//        if (!TextUtils.isEmpty(item.getData().getChat().getMessage())) {
//            helper.setText(R.id.tv_msg, TextRender.renderChatMessage2(item.getData().getChat().getMessage()));
//            helper.getView(R.id.tv_msg).setVisibility(View.VISIBLE);
//        } else {
//            helper.getView(R.id.tv_msg).setVisibility(View.GONE);
//        }
    }


    public void initLiveNoticeSystem(BaseViewHolder helper) {


        String content = MyUserInstance.getInstance().getUserConfig().getConfig().getRoom_notice();

        builder = new SpannableStringBuilder();
        builder.append(TextRender.renderChatMessage2(content));
        //调整名字文字颜色
        builder.setSpan(systemSpan, 0, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        lp = getLayoutParams();
        lp.setMargins(ArmsUtils.dip2px(mLayoutInflater.getContext(), 8), ArmsUtils.dip2px(mLayoutInflater.getContext(), 2)
                , ArmsUtils.dip2px(mLayoutInflater.getContext(), 8), ArmsUtils.dip2px(mLayoutInflater.getContext(), 2));
        helper.getView(R.id.tv_message).setLayoutParams(lp);
        helper.setText(R.id.tv_message, builder);

    }

    private void initChatGift(BaseViewHolder helper, ImMessage item) {
        if (item.getData().getGift() == null) {
            return;
        }

        GiftInfo bean = item.getData().getGift();
        GlideUtils.getImageDrawable(mLayoutInflater.getContext(), bean.getIcon(), new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull @NotNull Drawable resource, @Nullable @org.jetbrains.annotations.Nullable Transition<? super Drawable> transition) {

                lp = getLayoutParams();
                lp.setMargins(ArmsUtils.dip2px(mLayoutInflater.getContext(), 5), ArmsUtils.dip2px(mLayoutInflater.getContext(), -5)
                        , ArmsUtils.dip2px(mLayoutInflater.getContext(), 5), ArmsUtils.dip2px(mLayoutInflater.getContext(), 5));

                helper.getView(R.id.tv_message).setLayoutParams(lp);
                helper.setText(R.id.tv_message, initGiftMessage(messageSpan, bean, resource));
            }

            @Override
            public void onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                lp = getLayoutParams();
                lp.setMargins(ArmsUtils.dip2px(mLayoutInflater.getContext(), 5), ArmsUtils.dip2px(mLayoutInflater.getContext(), -5)
                        , ArmsUtils.dip2px(mLayoutInflater.getContext(), 5), ArmsUtils.dip2px(mLayoutInflater.getContext(), 5));

                helper.getView(R.id.tv_message).setLayoutParams(lp);
                helper.setText(R.id.tv_message, initGiftMessage(messageSpan, bean, null));
            }
        });

    }

    private SpannableStringBuilder initGiftMessage(ForegroundColorSpan span, GiftInfo bean, Drawable drawable) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(bean.getSender().getNick_name());
        stringBuffer.append(" ");
        stringBuffer.append(ArmsUtils.getString(mLayoutInflater.getContext(), R.string.st_send_gift_2));
        stringBuffer.append(" ");
        stringBuffer.append(bean.getTitle());
        stringBuffer.append(" ");
        stringBuffer.append("x " + bean.getCount());
        stringBuffer.append(" ");
        stringBuffer.append("\uFFFC");
        //调整前端文字颜色
        builder = new SpannableStringBuilder(stringBuffer.toString());
        builder.setSpan(span, 0, stringBuffer.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //插入图片

        if (drawable != null) {
            drawable.setBounds(0, 0, ArmsUtils.dip2px(mLayoutInflater.getContext(), 20), ArmsUtils.dip2px(mLayoutInflater.getContext(), 20));
            LiveImageSpan imageSpanLevel = new LiveImageSpan(drawable);
            builder.setSpan(imageSpanLevel, stringBuffer.indexOf("\uFFFC"), stringBuffer.indexOf("\uFFFC") + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }


    private void initManagerMessage(BaseViewHolder helper, ImMessage bean) {

        if (bean.getData().getNotify() == null) {
            return;
        }


        builder = new SpannableStringBuilder();
        switch (bean.getData().getNotify().getType()) {
            case SocketConstants.SOCKET_NOTIFY_RoomNotifyTypeSetManager:
                if (bean.getData().getNotify().getUser() != null) {
                    builder.append(bean.getData().getNotify().getUser().getNick_name() + "  被主播设置为房管");
                }
                break;
            case SocketConstants.SOCKET_NOTIFY_RoomNotifyTypeCancelManager:
                if (bean.getData().getNotify().getUser() != null) {
                    builder.append(bean.getData().getNotify().getUser().getNick_name() + "  房管权限已被取消");
                }
                break;
        }
        //调整名字文字颜色
        builder.setSpan(systemSpan, 0, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        lp = getLayoutParams();
        lp.setMargins(ArmsUtils.dip2px(mLayoutInflater.getContext(), 4), ArmsUtils.dip2px(mLayoutInflater.getContext(), 2)
                , ArmsUtils.dip2px(mLayoutInflater.getContext(), 8), ArmsUtils.dip2px(mLayoutInflater.getContext(), 2));
        helper.getView(R.id.tv_message).setLayoutParams(lp);
        helper.setText(R.id.tv_message, builder);
        helper.getView(R.id.circle_iv).setVisibility(View.GONE);


    }

    private void initBanMessage(BaseViewHolder helper, ImMessage bean) {

        if (bean.getData().getNotify() == null) {
            return;
        }
        if (bean.getData().getNotify().getUser() == null) {
            return;
        }


        builder = new SpannableStringBuilder();
        builder.append(bean.getData().getNotify().getUser().getNick_name());
        builder.append(" ");
        builder.append("被禁言了");
        //调整名字文字颜色
        builder.setSpan(systemSpan, 0, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        lp = getLayoutParams();
        lp.setMargins(ArmsUtils.dip2px(mLayoutInflater.getContext(), 8), ArmsUtils.dip2px(mLayoutInflater.getContext(), 2)
                , ArmsUtils.dip2px(mLayoutInflater.getContext(), 8), ArmsUtils.dip2px(mLayoutInflater.getContext(), 2));
        helper.getView(R.id.tv_message).setLayoutParams(lp);
        helper.setText(R.id.tv_message, builder);


    }

    private void initGuardianMessage(BaseViewHolder helper, ImMessage bean) {

        if (bean.getData().getNotify() == null) {
            return;
        }
        if (bean.getData().getNotify().getUser() == null) {
            return;
        }


        builder = new SpannableStringBuilder();
        builder.append(bean.getData().getNotify().getUser().getNick_name());
        builder.append(" ");
        builder.append("守护了主播");
        //调整名字文字颜色
        builder.setSpan(systemSpan, 0, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        lp = getLayoutParams();
        lp.setMargins(ArmsUtils.dip2px(mLayoutInflater.getContext(), 8), ArmsUtils.dip2px(mLayoutInflater.getContext(), 2)
                , ArmsUtils.dip2px(mLayoutInflater.getContext(), 8), ArmsUtils.dip2px(mLayoutInflater.getContext(), 2));
        helper.getView(R.id.tv_message).setLayoutParams(lp);
        helper.setText(R.id.tv_message, builder);


    }


    private void initLinkMessage(BaseViewHolder helper, ImMessage bean) {

        if (bean.getData().getNotify() == null) {
            return;
        }

        builder = new SpannableStringBuilder();

        switch (bean.getData().getNotify().getType()) {
            case SocketConstants.SOCKET_NOTIFY_RoomNotifyTypeLinkOn:
                builder.append("主播打开了连麦");
                break;
            case SocketConstants.SOCKET_NOTIFY_RoomNotifyTypeLinkOff:
                builder.append("主播关闭了连麦");
                break;
        }
        //调整名字文字颜色
        builder.setSpan(systemSpan, 0, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        lp = getLayoutParams();
        lp.setMargins(ArmsUtils.dip2px(mLayoutInflater.getContext(), 8), ArmsUtils.dip2px(mLayoutInflater.getContext(), 2)
                , ArmsUtils.dip2px(mLayoutInflater.getContext(), 8), ArmsUtils.dip2px(mLayoutInflater.getContext(), 2));
        helper.getView(R.id.tv_message).setLayoutParams(lp);
        helper.setText(R.id.tv_message, builder);


    }

}
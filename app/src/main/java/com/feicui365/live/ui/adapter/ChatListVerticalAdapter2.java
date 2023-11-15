/*
package com.feicui365.live.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.model.entity.HotLive;
import com.feicui365.live.util.DpUtil;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.widget.MyImageSpan;

import java.util.ArrayList;
import java.util.List;

public class ChatListVerticalAdapter2 extends RecyclerView.Adapter<ChatListVerticalAdapter2.MyViewHolder> {

    private List<Message> infos ;
    private Context context;
    LinearLayout.LayoutParams lp;
    private HotLive startLive;
    private ChatClickListener chatClickListener;

    public void setChatClickListener(ChatClickListener chatClickListener) {
        this.chatClickListener = chatClickListener;
    }

    public void setStartLive(HotLive startLive) {
        this.startLive = startLive;
    }

    public interface ChatClickListener{
        void chatclick(String id);
    }

    public ChatListVerticalAdapter2(ArrayList<Message> chatList, Context context) {
        this.infos = chatList;
        this.context = context;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView prompt_tv;

        TextView message_tv;
        LinearLayout message_ll;

        public MyViewHolder (View view)
        {
            super(view);
            prompt_tv = (TextView) view.findViewById(R.id.prompt_tv);
            message_tv = (TextView) view.findViewById(R.id.message_tv);
            message_ll = view.findViewById(R.id.message_ll);
        }

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        MyViewHolder holder  = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_list_vertical_item, viewGroup,
                false));
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(DpUtil.dp2px(5), 0, DpUtil.dp2px(5), 0);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        boolean is_vip=false;
        if (position == 0) {
            holder.prompt_tv.setVisibility(View.VISIBLE);
            holder.message_ll.setVisibility(View.GONE);
            if(MyUserInstance.getInstance().getUserConfig().getConfig().getRoom_notice()==null){
                return;
            }
            if(MyUserInstance.getInstance().getUserConfig().getConfig().getRoom_notice().equals("")){
                return;
            }
            holder.prompt_tv.setText(MyUserInstance.getInstance().getUserConfig().getConfig().getRoom_notice());
        } else {

            holder.prompt_tv.setVisibility(View.GONE);
            holder.message_ll.setVisibility(View.VISIBLE);

            if (infos.get(position).getAction().equals("RoomMessage") || infos.get(position).getAction().equals("RoomAttentAnchor")) {


                if (infos.get(position).getData().getChat().getSender() != null) {

                    String nickName = "";
                    if (infos.get(position).getData().getChat().getNick_name() != null) {
                        nickName = infos.get(position).getData().getChat().getNick_name();
                    }

                    if (infos.get(position).getData().getChat().getSender() != null) {
                        if (infos.get(position).getData().getChat().getSender().getNick_name() != null) {
                            nickName = infos.get(position).getData().getChat().getSender().getNick_name();
                        }
                    }


                    String message = infos.get(position).getData().getChat().getMessage();

                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(nickName);
                    stringBuffer.append("：");
                    stringBuffer.append(message);

                    SpannableStringBuilder builder = new SpannableStringBuilder(stringBuffer.toString());

                    ForegroundColorSpan graySpan = new ForegroundColorSpan(holder.message_tv.getContext().getResources().getColor(R.color.color_message));
                    ForegroundColorSpan blankSpan = new ForegroundColorSpan(Color.WHITE);

                    builder.setSpan(graySpan, 0, nickName.length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    builder.setSpan(blankSpan, nickName.length() + 1, stringBuffer.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


                    if (infos.get(position).getData().getChat().getSender().getUser_level() != 0) {
                        builder.insert(0, " ");
                        Drawable drawable = context.getResources().getDrawable(LevelUtils.getUserLevel(infos.get(position).getData().getChat().getSender().getUser_level() + ""));
                        drawable.setBounds(0, 0, DpUtil.dp2px(18), DpUtil.dp2px(10));
                        MyImageSpan imageSpan = new MyImageSpan(drawable);
                        builder.setSpan(imageSpan, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    }


                    //先判斷有没有vip
                    if (infos.get(position).getData().getChat().getSender().getVip_level() != 0) {
                        if (infos.get(position).getData().getChat().getSender().getVip_date() != null) {
                            if (MyUserInstance.getInstance().OverTime(infos.get(position).getData().getChat().getSender().getVip_date())) {
                                if (MyUserInstance.getInstance().OverTime(infos.get(position).getData().getChat().getSender().getVip_date())) {
                                    builder.insert(0, " ");
                                    Drawable drawable = context.getResources().getDrawable(MyUserInstance.getInstance().getVipLevel(infos.get(position).getData().getChat().getSender().getVip_level() + ""));
                                    drawable.setBounds(0, 0, DpUtil.dp2px(22), DpUtil.dp2px(22));
                                    MyImageSpan imageSpan = new MyImageSpan(drawable);
                                    builder.setSpan(imageSpan, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                                    is_vip = true;
                                }
                            }
                        }
                    }

                    if (!is_vip) {
                        holder.message_tv.setLayoutParams(lp);
                        holder.message_tv.setPadding(DpUtil.dp2px(5), DpUtil.dp2px(5), DpUtil.dp2px(5), DpUtil.dp2px(5));
                    }
                    holder.message_tv.setText(builder);


                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (infos.get(position).getData().getChat() != null) {
                                if (infos.get(position).getData().getChat().getSender() != null) {
                                    if (infos.get(position).getData().getChat().getSender().getId() != null || infos.get(position).getData().getChat().getSender().getId().equals("0")) {
                                        if(chatClickListener!=null){
                                            chatClickListener.chatclick(infos.get(position).getData().getChat().getSender().getId());
                                        }
                                    }
                                }
                            }
                        }
                    });

                }
            }
            //普通消息结束
            //礼物信息
            if(infos.get(position).getAction().equals("GiftAnimation") ){


                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(infos.get(position).getData().getGift().getSender().getNick_name());
                stringBuffer.append(" 送给主播 ");
                stringBuffer.append(infos.get(position).getData().getGift().getTitle());
                stringBuffer.append("x"+infos.get(position).getData().getGift().getCount());
                SpannableStringBuilder builder = new SpannableStringBuilder(stringBuffer);
                ForegroundColorSpan blueSpan = new ForegroundColorSpan(holder.message_tv.getContext().getResources().getColor(R.color.color_message));
                builder.setSpan(blueSpan, 0, stringBuffer.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.message_tv.setLayoutParams(lp);
                holder.message_tv.setPadding(DpUtil.dp2px(5), DpUtil.dp2px(5), DpUtil.dp2px(5), DpUtil.dp2px(5));
                holder.message_tv.setText(builder);
            }
            //房管设置
            if(infos.get(position).getAction().equals("RoomNotification") ){

                if(infos.get(position).getData().getNotify().getType().equals("RoomNotifyTypeSetManager")){

                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(infos.get(position).getData().getNotify().getUser().getNick_name());
                    stringBuffer.append(" 被主播设置为房管");
                    SpannableStringBuilder builder = new SpannableStringBuilder(stringBuffer);
                    ForegroundColorSpan blueSpan = new ForegroundColorSpan(holder.message_tv.getContext().getResources().getColor(R.color.color_message));
                    builder.setSpan(blueSpan, 0, stringBuffer.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    holder.message_tv.setLayoutParams(lp);
                    holder.message_tv.setPadding(DpUtil.dp2px(5), DpUtil.dp2px(5), DpUtil.dp2px(5), DpUtil.dp2px(5));
                    holder.message_tv.setText(builder);


                }else if(infos.get(position).getData().getNotify().getType().equals("RoomNotifyTypeCancelManager")){
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(infos.get(position).getData().getNotify().getUser().getNick_name());
                    stringBuffer.append(" 房管权限已被取消");
                    SpannableStringBuilder builder = new SpannableStringBuilder(stringBuffer);
                    ForegroundColorSpan blueSpan = new ForegroundColorSpan(holder.message_tv.getContext().getResources().getColor(R.color.color_message));
                    builder.setSpan(blueSpan, 0, stringBuffer.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    holder.message_tv.setLayoutParams(lp);
                    holder.message_tv.setPadding(DpUtil.dp2px(5), DpUtil.dp2px(5), DpUtil.dp2px(5), DpUtil.dp2px(5));
                    holder.message_tv.setText(builder);

                }

            }
        }


    }

    @Override
    public int getItemCount() {
        return infos.size();
    }


}
*/

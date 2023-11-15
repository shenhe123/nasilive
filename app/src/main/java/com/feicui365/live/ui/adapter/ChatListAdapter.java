/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *//*

package com.feicui365.live.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.feicui365.live.util.DpUtil;
import com.feicui365.live.widget.MyImageSpan;
import com.feicui365.live.util.MyUserInstance;

import java.util.List;


public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyViewHolder> {

    private List<Message> infos;
    private Context context;
    LinearLayout.LayoutParams lp;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView prompt_tv;
        TextView message_tv;
        LinearLayout message_ll;

        public MyViewHolder(View view) {
            super(view);
            prompt_tv = (TextView) view.findViewById(R.id.prompt_tv);
            message_tv = (TextView) view.findViewById(R.id.message_tv);
            message_ll = (LinearLayout) view.findViewById(R.id.message_ll);
        }

    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void Onclick(String id);
    }

    public ChatListAdapter(List<Message> infos, Context context) {
        this.infos = infos;
        this.context = context;
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(DpUtil.dp2px(5), 0, DpUtil.dp2px(5), 0);
    }

    @Override

    public ChatListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ChatListAdapter.MyViewHolder holder = new ChatListAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_list_item, parent,
                false));

        return holder;
    }

    @Override
    public void onBindViewHolder(ChatListAdapter.MyViewHolder holder, int position) {
        boolean is_vip = false;
        if (position == 0) {
            holder.prompt_tv.setVisibility(View.VISIBLE);
            holder.message_ll.setVisibility(View.GONE);
            if (MyUserInstance.getInstance().getUserConfig().getConfig().getRoom_notice() == null) {
                return;
            }
            if (MyUserInstance.getInstance().getUserConfig().getConfig().getRoom_notice().equals("")) {
                return;
            }
            holder.prompt_tv.setText(MyUserInstance.getInstance().getUserConfig().getConfig().getRoom_notice());
        } else {
            holder.prompt_tv.setVisibility(View.GONE);
            holder.message_ll.setVisibility(View.VISIBLE);
            if (infos.get(position).getAction().equals("RoomMessage") || infos.get(position).getAction().equals("RoomAttentAnchor")) {

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
                ForegroundColorSpan blankSpan = new ForegroundColorSpan(Color.BLACK);

                builder.setSpan(graySpan, 0, nickName.length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.setSpan(blankSpan, nickName.length() + 1, stringBuffer.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                //守护
                if (infos.get(position).getData().getChat().getIs_guardian() == 1) {
                    builder.insert(0, " ");
                    Drawable d_guardian = context.getResources().getDrawable(R.mipmap.ic_protect);
                    d_guardian.setBounds(0, 0, DpUtil.dp2px(34), DpUtil.dp2px(15));
                    MyImageSpan i_guardian = new MyImageSpan(d_guardian);
                    builder.setSpan(i_guardian, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                //用户等级
                builder.insert(0, " ");
                Drawable d_lv = context.getResources().getDrawable(LevelUtils.getUserLevel(infos.get(position).getData().getChat().getSender().getUser_level() + ""));
                d_lv.setBounds(0, 0, DpUtil.dp2px(34), DpUtil.dp2px(15));
                MyImageSpan i_lv = new MyImageSpan(d_lv);
                builder.setSpan(i_lv, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                //是否是管理
                if(infos.get(position).getData().getChat().getIs_manager() == 1){
                    builder.insert(0, " ");
                    Drawable d_admin = context.getResources().getDrawable(R.mipmap.ic_admin);
                    d_admin.setBounds(0, 0, DpUtil.dp2px(34), DpUtil.dp2px(15));
                    MyImageSpan i_admin = new MyImageSpan(d_admin);
                    builder.setSpan(i_admin, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                //先判斷有没有vip
                if (MyUserInstance.getInstance().isVip(infos.get(position).getData().getChat().getSender())) {
                    builder.insert(0, " ");

                    Drawable d_vip = context.getResources().getDrawable(MyUserInstance.getInstance().getVipLevel(infos.get(position).getData().getChat().getSender().getVip_level() + ""));
                    d_vip.setBounds(0, 0, DpUtil.dp2px(17), DpUtil.dp2px(21));
                    MyImageSpan imageSpan = new MyImageSpan(d_vip);
                    builder.setSpan(imageSpan, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    is_vip = true;
                }



                if (!is_vip) {
                    lp.setMargins(0, DpUtil.dp2px(5), DpUtil.dp2px(8), DpUtil.dp2px(5));
                    holder.message_tv.setLayoutParams(lp);

                } else {
                    lp.setMargins(0, 0, DpUtil.dp2px(8), DpUtil.dp2px(5));
                    holder.message_tv.setLayoutParams(lp);
                }
                holder.message_tv.setText(builder);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (infos.get(position).getData().getChat() != null) {
                            if (infos.get(position).getData().getChat().getSender() != null) {
                                if (infos.get(position).getData().getChat().getSender().getId() != null) {

                                    if (onItemClickListener != null) {
                                        onItemClickListener.Onclick(infos.get(position).getData().getChat().getSender().getId());
                                    }

                                }
                            }
                        }
                    }
                });
            }
            //普通消息结束
            //礼物信息
            if(infos.get(position).getAction().equals("GiftAnimation") ){


                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(infos.get(position).getData().getGift().getSender().getNick_name());
                stringBuffer.append(" 送给主播 ");
                stringBuffer.append(infos.get(position).getData().getGift().getTitle());
                stringBuffer.append("x" + infos.get(position).getData().getGift().getCount());
                SpannableStringBuilder builder = new SpannableStringBuilder(stringBuffer);
                ForegroundColorSpan blueSpan = new ForegroundColorSpan(holder.message_tv.getContext().getResources().getColor(R.color.color_message));
                builder.setSpan(blueSpan, 0, stringBuffer.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.message_tv.setLayoutParams(lp);
                holder.message_tv.setPadding(DpUtil.dp2px(2), DpUtil.dp2px(5), DpUtil.dp2px(2), DpUtil.dp2px(3));
                holder.message_tv.setText(builder);
            }
            //房管设置
            if(infos.get(position).getAction().equals("RoomNotification") ){
                StringBuffer stringBuffer = new StringBuffer();
                switch (infos.get(position).getData().getNotify().getType()) {
                    case "RoomNotifyTypeSetManager":

                        stringBuffer.append(infos.get(position).getData().getNotify().getUser().getNick_name());
                        stringBuffer.append(" 被主播设置为房管");

                        break;
                    case "RoomNotifyTypeCancelManager":

                        stringBuffer.append(infos.get(position).getData().getNotify().getUser().getNick_name());
                        stringBuffer.append(" 房管权限已被取消");
                        break;

                    case "RoomNotifyTypeLinkOn":

                        stringBuffer.append("主播开启连麦");
                        break;

                    case "RoomNotifyTypeLinkOff":

                        stringBuffer.append("主播关闭连麦");
                        break;
                    case "RoomNotifyTypeGuardAnchor":
                        stringBuffer.append(infos.get(position).getData().getNotify().getUser().getNick_name());
                        stringBuffer.append(" 守护了主播");
                        break;
                }

                SpannableStringBuilder builder = new SpannableStringBuilder(stringBuffer);
                ForegroundColorSpan blueSpan = new ForegroundColorSpan(holder.message_tv.getContext().getResources().getColor(R.color.color_message));
                builder.setSpan(blueSpan, 0, stringBuffer.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.message_tv.setLayoutParams(lp);
                holder.message_tv.setPadding(DpUtil.dp2px(2), DpUtil.dp2px(5), DpUtil.dp2px(2), DpUtil.dp2px(3));
                holder.message_tv.setText(builder);


            }

        }

    }


    @Override
    public int getItemCount() {
        return infos.size();
    }
}
*/

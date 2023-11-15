package com.feicui365.live.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.im.TxImUtils;
import com.feicui365.live.ui.act.BuyMemberActivity;
import com.feicui365.live.ui.act.AnchorCenterActivity;
import com.feicui365.live.ui.act.ReportItemActivity;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.LevelUtils;
import com.feicui365.live.util.MyUserInstance;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoDialog extends Dialog {

    public UserInfoDialog(Context context) {
        super(context);
    }

    public UserInfoDialog(Context context, int theme) {
        super(context, theme);
    }

    public interface OnFinishListener {
        void onFinish(String price);
    }

    public static class Builder {
        private Context context;

        private View contentView;
        private OnFinishListener onFinishListener;
        private ImageView iv_level, iv_sex, iv_vip_backround;
        CircleImageView civ_avatar;
        TextView tv_name, tv_age, tv_fans, tv_member, tv_guanzhu, tv_sixin, tv_zhuye;
        RelativeLayout rl_age, rL_close, rl_report,rl_manager;
        UserRegist userRegist;
        String anchorid;




        String type = "1";
        String status = "1";
        String is_follow = "0";


        public String getAnchorid() {
            return anchorid;
        }

        public void setAnchorid(String anchorid) {
            this.anchorid = anchorid;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setUserRegist(UserRegist userRegist) {
            this.userRegist = userRegist;
        }

        public Builder(Context context) {
            this.context = context;
        }


        public void setOnFinishListener(OnFinishListener onFinishListener) {
            this.onFinishListener = onFinishListener;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }


        public UserInfoDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final UserInfoDialog dialog = new UserInfoDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_user_info_layout, null);
            dialog.addContentView(layout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            initView(layout);
            dialog.setContentView(layout);

            if (null != userRegist) {
                //头像
                Glide.with(context).load(userRegist.getAvatar()).into(civ_avatar);
                //ID
                tv_name.setText(userRegist.getNick_name());
                //等级
                if (type.equals("1")) {

                    Glide.with(context).load(LevelUtils.getUserLevel(userRegist.getUser_level())).into(iv_level);
                } else {

                    Glide.with(context).load(LevelUtils.getAnchorLevel(userRegist.getAnchor_level())).into(iv_level);
                }
                //年级
                tv_age.setText(userRegist.getProfile().getAge());

                //性别
                switch (userRegist.getProfile().getGender()) {
                    case 0:
                        Glide.with(context).load(R.mipmap.girl).into(iv_sex);

                        break;
                    case 1:
                        Glide.with(context).load(R.mipmap.boy).into(iv_sex);
                        rl_age.setBackgroundResource(R.drawable.shape_corner_age_boy);
                        break;
                }
                //粉丝
                tv_fans.setText("粉丝：" + userRegist.getFans_count());


                if (userRegist.getVip_level() != 0&userRegist.getVip_date()!=null) {
                    if(MyUserInstance.getInstance().OverTime(userRegist.getVip_date())){


                    switch (userRegist.getVip_level()) {
                        case 0:
                            break;

                        case 1:
                            iv_vip_backround.setVisibility(View.VISIBLE);
                            iv_vip_backround.setImageDrawable(context.getResources().getDrawable(R.mipmap.top_youxia));
                            break;
                        case 2:
                            iv_vip_backround.setVisibility(View.VISIBLE);
                            iv_vip_backround.setImageDrawable(context.getResources().getDrawable(R.mipmap.top_qishi));
                            break;
                        case 3:
                            iv_vip_backround.setVisibility(View.VISIBLE);
                            iv_vip_backround.setImageDrawable(context.getResources().getDrawable(R.mipmap.top_gongjue));
                            break;
                        case 4:
                            iv_vip_backround.setVisibility(View.VISIBLE);
                            iv_vip_backround.setImageDrawable(context.getResources().getDrawable(R.mipmap.top_guowang));
                            break;

                    }
                    }
                }


            }


//购买会员
            tv_member.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(MyUserInstance.getInstance().visitorIsLogin((Activity) context)) {
                        context.startActivity(new Intent(context, BuyMemberActivity.class));
                    }
                }
            });
            //关注

            if(MyUserInstance.getInstance().hasToken()){
                HttpUtils.getInstance().checkAttent(userRegist.getId(), new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSON.parseObject(response.body());
                        if (jsonObject.getString("status").equals("0")) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            if (jsonObject1.getString("attented").equals("0")) {
                                tv_guanzhu.setText("关注");
                                is_follow = "0";
                            } else {
                                tv_guanzhu.setText("已关注");
                                is_follow = "1";
                            }
                        }
                    }
                });
            }


            tv_guanzhu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MyUserInstance.getInstance().visitorIsLogin((Activity) context)) {
                        String temp = "";
                        if (is_follow.equals("0")) {
                            temp = "1";
                        } else {
                            temp = "0";
                        }


                        HttpUtils.getInstance().attentAnchor(userRegist.getId(), temp, new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                JSONObject data = HttpUtils.getInstance().check(response);
                                if (data.get("status").toString().equals("0")) {
                                    if (is_follow.equals("0")) {
                                        is_follow = "1";
                                        tv_guanzhu.setText("已关注");
                                    } else {
                                        is_follow = "0";
                                        tv_guanzhu.setText("关注");
                                    }

                                }

                            }
                        });
                    }
                }
            });
            //私信
            tv_sixin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(MyUserInstance.getInstance().visitorIsLogin((Activity) context)) {
                        TxImUtils.getSocketManager().startChat(userRegist.getId(), userRegist.getNick_name(),userRegist.getAvatar());

                    }
                }
            });
            //主页
            tv_zhuye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, AnchorCenterActivity.class)
                            .putExtra("data", userRegist.getId()));
                }
            });

            rL_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            if(status.equals("1")){

                rl_report.setVisibility(View.VISIBLE);
                rl_manager.setVisibility(View.GONE);
            }else{
                rl_report.setVisibility(View.GONE);
                rl_manager.setVisibility(View.VISIBLE);
            }

            rl_report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(MyUserInstance.getInstance().visitorIsLogin((Activity) context)) {
                        Intent i = new Intent(context, ReportItemActivity.class);
                        i.putExtra("REPORT_ID", userRegist.getId());
                        i.putExtra("REPORT_TYPE", "1");
                        context.startActivity(i);
                    }
                }
            });

            rl_manager.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(anchorid==null){
                        return;
                    }

                    ManageDialog.Builder builder = new ManageDialog.Builder(context);
                    builder.setUserid(userRegist.getId());
                    builder.setUsername(userRegist.getNick_name());
                    builder.setAnchorid(anchorid);
                    builder.setStatus(status);
                    builder.create().show();
                }
            });

            return dialog;
        }

        private void initView(View layout) {
            iv_level = layout.findViewById(R.id.iv_level);
            iv_sex = layout.findViewById(R.id.iv_sex);
            civ_avatar = layout.findViewById(R.id.civ_avatar);
            tv_name = layout.findViewById(R.id.tv_name);
            tv_age = layout.findViewById(R.id.tv_age);
            tv_fans = layout.findViewById(R.id.tv_fans);
            tv_member = layout.findViewById(R.id.tv_member);
            tv_guanzhu = layout.findViewById(R.id.tv_guanzhu);
            tv_sixin = layout.findViewById(R.id.tv_sixin);
            tv_zhuye = layout.findViewById(R.id.tv_zhuye);
            rl_age = layout.findViewById(R.id.rl_age);
            rL_close = layout.findViewById(R.id.rL_close);
            rl_report = layout.findViewById(R.id.rl_report);
            rl_manager=layout.findViewById(R.id.rl_manager);
            iv_vip_backround = layout.findViewById(R.id.iv_vip_backround);
        }
    }


    public Dialog getDialog() {
        return this;
    }

    @Override
    public void show() {
        super.show();



        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();

        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);
    }
}

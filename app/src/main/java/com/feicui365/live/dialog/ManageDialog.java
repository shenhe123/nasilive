package com.feicui365.live.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feicui365.live.R;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.ToastUtils;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class ManageDialog extends Dialog {

    public ManageDialog(Context context) {
        super(context);
    }

    public ManageDialog(Context context, int theme) {
        super(context, theme);
    }

    public interface OnFinishListener {
        void onFinish(String price);
    }

    public static class Builder {
        private Context context;
        private String status = "1";
        private View contentView;
        TextView tv_name;
        RelativeLayout rl_close, rl_no_talk, rl_manager;
        String anchorid;
        String userid;
        String username;
        View v_2;

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setAnchorid(String anchorid) {
            this.anchorid = anchorid;
        }



        public void setStatus(String status) {
            this.status = status;
        }

        public Builder(Context context) {
            this.context = context;
        }


        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }


        public ManageDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ManageDialog dialog = new ManageDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.anchor_room_manage, null);
            dialog.addContentView(layout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            initView(layout);
            dialog.setContentView(layout);

            if (status.equals("1") || status.equals("3")) {
                rl_manager.setVisibility(View.GONE);
                v_2.setVisibility(View.GONE);
            } else {
                rl_manager.setVisibility(View.VISIBLE);
                v_2.setVisibility(View.VISIBLE);
            }

            tv_name.setText(username);

            rl_no_talk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HttpUtils.getInstance().banUser(anchorid,userid, "1", new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            JSONObject jsonObject = JSON.parseObject(response.body());
                            if (HttpUtils.getInstance().swtichStatus(jsonObject)) {
                                ToastUtils.showT("禁言成功");

                            }
                            dialog.dismiss();
                        }
                    });
                }
            });


            rl_manager.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HttpUtils.getInstance().setRoomMgr(userid, "1", new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            JSONObject jsonObject = JSON.parseObject(response.body());
                            if (HttpUtils.getInstance().swtichStatus(jsonObject)) {
                                ToastUtils.showT("设置房管");

                            } else {
                                ToastUtils.showT("设置房管失败");
                            }
                            dialog.dismiss();
                        }
                    });
                }
            });

            rl_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            return dialog;
        }

        private void initView(View layout) {
            tv_name = layout.findViewById(R.id.tv_name);
            rl_close = layout.findViewById(R.id.rl_close);
            rl_no_talk = layout.findViewById(R.id.rl_no_talk);
            rl_manager = layout.findViewById(R.id.rl_manager);
            v_2=layout.findViewById(R.id.v_2);

        }
    }


    public Dialog getDialog() {
        return this;
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();

        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);
    }
}
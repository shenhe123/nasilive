package com.feicui365.live.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.feicui365.live.R;
import com.feicui365.live.model.entity.UserRegist;

import de.hdodenhof.circleimageview.CircleImageView;

public class JoinInfoDialog extends Dialog {

    public JoinInfoDialog(Context context) {
        super(context);
    }

    public JoinInfoDialog(Context context, int theme) {
        super(context, theme);
    }

    public interface OnFinishListener {
        void onSubmit();
        void onCancel();
    }

    public static class Builder {
        private Context context;

        private View contentView;
        private OnFinishListener onFinishListener;
        private TextView tv_nickname, tv_id, tv_cancel, tv_submit;
        CircleImageView civ_avatar;
        UserRegist user;


        public void setUser(UserRegist user) {
            this.user = user;
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


        public JoinInfoDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final JoinInfoDialog dialog = new JoinInfoDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_join_info_layout, null);
            dialog.addContentView(layout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            initView(layout);
            dialog.setContentView(layout);
            tv_nickname.setText(user.getNick_name());
            tv_id.setText("ID: " + user.getId());
            Glide.with(context).applyDefaultRequestOptions(new RequestOptions().placeholder(R.mipmap.moren)).load(user.getAvatar()).into(civ_avatar);
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onFinishListener!=null){
                        onFinishListener.onCancel();
                    }
                    dialog.dismiss();
                }
            });

            tv_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onFinishListener!=null){
                        onFinishListener.onSubmit();
                    }
                    dialog.dismiss();
                }
            });

            return dialog;
        }

        private void initView(View layout) {
            tv_nickname = layout.findViewById(R.id.tv_nickname);
            tv_id = layout.findViewById(R.id.tv_id);
            civ_avatar = layout.findViewById(R.id.civ_avatar);
            tv_cancel = layout.findViewById(R.id.tv_cancel);
            tv_submit = layout.findViewById(R.id.tv_submit);

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
package com.feicui365.live.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feicui365.live.R;

public class LivePriceDialog extends Dialog {

    public LivePriceDialog(Context context) {
        super(context);
    }

    public LivePriceDialog(Context context, int theme) {
        super(context, theme);
    }

    public interface OnFinishListener {
        void onFinish(String price);
    }

    public static class Builder {
        private Context context;
        public LivePriceDialog livePriceDialog;
        private View contentView;
        private View.OnClickListener onSubmit,onCancel;
        private TextView tv_title,tv_content,tv_submit;

        private RelativeLayout rl_submit,rl_cancel;
        public Builder(Context context) {
            this.context = context;
        }


        public void setOnSubmit(View.OnClickListener onSubmit) {
            this.onSubmit = onSubmit;
        }

        public void setOnCancel(View.OnClickListener onCancel) {
            this.onCancel = onCancel;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }


        public LivePriceDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final LivePriceDialog dialog = new LivePriceDialog(context, R.style.Base_Theme_AppCompat_Dialog);
            View layout = inflater.inflate(R.layout.dialog_live_price_layout, null);
            dialog.addContentView(layout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            tv_title = layout.findViewById(R.id.tv_title);
            tv_content = layout.findViewById(R.id.tv_content);
            tv_submit = layout.findViewById(R.id.tv_submit);
            rl_submit = layout.findViewById(R.id.rl_submit);
            rl_cancel = layout.findViewById(R.id.rl_cancel);
            rl_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null!=onSubmit){
                        onSubmit.onClick(layout);
                        dialog.dismiss();
                    }
                }
            });
            rl_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                        dialog.dismiss();

                }
            });
            dialog.setContentView(layout);
            livePriceDialog=dialog;
            return dialog;
        }


        public void setTitle(String title) {
            tv_title.setText(title);
        }

        public void setContent(String title) {
            tv_content.setText(title);
        }

        public void setSubmitText(String title) {
            tv_submit.setText(title);
        }

        public void setCancelHide(boolean is_hide) {
           if(is_hide){
               rl_cancel.setVisibility(View.GONE);
           }else{
               rl_cancel.setVisibility(View.VISIBLE);
           }
        }

        public void setCanCancel(boolean is_hide) {
                livePriceDialog.setCancelable(is_hide);
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
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.argb(0, 0, 0, 0)));
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.CENTER_HORIZONTAL;
            //设置dialog宽度充满屏幕
            window.getDecorView().setPadding(0, 0, 0, 0);
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }
    }
}
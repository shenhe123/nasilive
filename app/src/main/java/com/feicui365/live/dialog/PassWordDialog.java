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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.util.ToastUtils;

public class PassWordDialog extends Dialog {

    public PassWordDialog(Context context) {
        super(context);
    }

    public PassWordDialog(Context context, int theme) {
        super(context, theme);
    }

    public interface OnFinishListener {
        void onFinish(String price,PassWordDialog passWordDialog);
    }

    public static class Builder {
        private Context context;

        private View contentView;
        private OnFinishListener onFinishListener;
        private TextView tv_title;
        private EditText et_price;

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


        public PassWordDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final PassWordDialog dialog = new PassWordDialog(context, R.style.Base_Theme_AppCompat_Dialog);
            View layout = inflater.inflate(R.layout.dialog_price_layout, null);
            dialog.addContentView(layout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            tv_title = layout.findViewById(R.id.tv_title);
            et_price = layout.findViewById(R.id.et_price);
            layout.findViewById(R.id.tv_finish).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onFinishListener != null) {
                        String price = ((EditText) layout.findViewById(R.id.et_price)).getText().toString();

                        if (price.equals("")) {
                            ToastUtils.showT("当前房间密码输入错误");
                            return;
                        }

                        onFinishListener.onFinish(price,dialog);

                    }
                }
            });

            layout.findViewById(R.id.tv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


            dialog.setContentView(layout);
            return dialog;
        }

        public void setTitle(String title) {
            tv_title.setText(title);
        }

        public void setHint(String title) {
            et_price.setHint(title);
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
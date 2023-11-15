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

public class LanguageDialog extends Dialog {

    public LanguageDialog(Context context) {
        super(context);
    }

    public LanguageDialog(Context context, int theme) {
        super(context, theme);
    }

    public interface OnFinishListener {
        void onFinish(String language);
    }

    public static class Builder {
        private Context context;

        private View contentView;
        private OnFinishListener onFinishListener;
        private TextView tv_title, tv_close,tv_chinese,tv_english;
        private EditText et_price;

        LanguageDialog dialog;

        public Builder(Context context) {
            this.context = context;
        }


        public void setOnFinishListener(LanguageDialog.OnFinishListener onFinishListener) {
            this.onFinishListener = onFinishListener;
        }

        public LanguageDialog.Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }


        public LanguageDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            dialog = new LanguageDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_language_layout, null);
            dialog.addContentView(layout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            tv_title = layout.findViewById(R.id.tv_title);
            et_price = layout.findViewById(R.id.et_price);
            tv_close = layout.findViewById(R.id.tv_close);
            tv_chinese=layout.findViewById(R.id.tv_chinese);
            tv_english=layout.findViewById(R.id.tv_english);


            tv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            tv_chinese.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onFinishListener!=null){
                        onFinishListener.onFinish("1");
                    }
                    dialog.dismiss();
                }
            });
            tv_english.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onFinishListener!=null){
                        onFinishListener.onFinish("2");
                    }
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

        public void setCanCancel(boolean is_can) {
            dialog.setCancelable(is_can);
        }
    }


    public Dialog getDialog() {
        return this;
    }


    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.argb(0, 0, 0, 0)));
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.CENTER_HORIZONTAL;
            window.getDecorView().setPadding(0, 0, 0, 0);
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }
    }
}
package com.feicui365.live.shop.custom;

import android.app.Activity;
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
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.wxapi.PayCallback;

public class PayDialog extends Dialog {


    public PayDialog(Context context) {
        super(context);
    }

    public PayDialog(Context context, int theme) {
        super(context, theme);
    }

    public interface OnFinishListener {
        void Success();

        void Fail();
    }

    public static class Builder {
        private Context context;

        private View contentView;
        private OnFinishListener onFinishListener;
        private TextView tv_title, tv_close, tv_ali, tv_wechat;
        private EditText et_price;
        private String order_num;
        String price;

        PayDialog dialog;

        public Builder(Context context) {
            this.context = context;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Builder(Context context, String order_num, String price) {
            this.order_num = order_num;
            this.price = price;
            this.context = context;
        }

        public void setOnFinishListener(PayDialog.OnFinishListener onFinishListener) {
            this.onFinishListener = onFinishListener;
        }

        public PayDialog.Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }


        public PayDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            dialog = new PayDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_pay_layout, null);
            dialog.addContentView(layout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            tv_title = layout.findViewById(R.id.tv_title);
            et_price = layout.findViewById(R.id.et_price);
            tv_close = layout.findViewById(R.id.tv_close);
            tv_ali = layout.findViewById(R.id.tv_ali);
            tv_wechat = layout.findViewById(R.id.tv_wechat);


            tv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


            tv_ali.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    MyUserInstance.getInstance().getAliPayBuilder((Activity) context).getAliShopPayOrder(order_num, price);
                    MyUserInstance.getInstance().getAliPayBuilder((Activity) context).setPayCallback(new PayCallback() {
                        @Override
                        public void onSuccess() {
                            if (onFinishListener != null) {
                                onFinishListener.Success();
                            }
                            dialog.dismiss();
                        }

                        @Override
                        public void onFailed() {
                            if (onFinishListener != null) {
                                onFinishListener.Fail();
                            }
                            dialog.dismiss();
                        }
                    });



                }
            });
            tv_wechat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyUserInstance.getInstance().getWxPayBuilder(context).getWxShopPayOrder(order_num, price);
                    MyUserInstance.getInstance().getWxPayBuilder(context).setPayCallback(new PayCallback() {
                        @Override
                        public void onSuccess() {
                            if (onFinishListener != null) {
                                onFinishListener.Success();
                            }
                            dialog.dismiss();
                        }

                        @Override
                        public void onFailed() {
                            if (onFinishListener != null) {
                                onFinishListener.Fail();
                            }
                            dialog.dismiss();
                        }
                    });
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
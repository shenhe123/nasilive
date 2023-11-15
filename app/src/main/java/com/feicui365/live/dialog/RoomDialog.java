package com.feicui365.live.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.util.StringUtil;
import com.feicui365.live.util.ToastUtils;

public class RoomDialog extends Dialog {

    public RoomDialog(Context context) {
        super(context);
    }

    public RoomDialog(Context context, int theme) {
        super(context, theme);
    }

    public interface OnFinishListener {
        void onFinish(String price);
    }

    public static class Builder {
        private Context context;

        private View contentView;
        private OnFinishListener onFinishListener;
        private TextView tv_title;
        private EditText et_price;

        String room_type;
        RoomDialog dialog;
        public Builder(Context context,String room_type) {
            this.context = context;
            this.room_type=room_type;
        }


        public void setOnFinishListener(OnFinishListener onFinishListener) {
            this.onFinishListener = onFinishListener;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }


        public RoomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

             dialog = new RoomDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_price2_layout, null);
            dialog.addContentView(layout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            tv_title = layout.findViewById(R.id.tv_title);
            et_price = layout.findViewById(R.id.et_price);


            layout.findViewById(R.id.tv_finish).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onFinishListener != null) {
                        String price = ((EditText) layout.findViewById(R.id.et_price)).getText().toString();

                        switch (room_type){
                            case "1":
                                if (price.equals("")) {
                                    ToastUtils.showT("密码输入错误,请输入正确的密码");
                                    return;
                                }
                                break;
                            case "2":
                                if (price.equals("")) {
                                    ToastUtils.showT("请输入正确的收费金额");
                                    return;
                                }
                                if(!StringUtil.isInt(price)){
                                    ToastUtils.showT("请输入正确的收费金额");
                                    return;
                                }

                                if (Integer.parseInt(price) < 0) {
                                    ToastUtils.showT("请输入正确的收费金额");
                                    return;
                                }
                                break;
                        }



                        onFinishListener.onFinish(price);
                        dialog.dismiss();
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

       public void setInputType(String i){
            switch (i){
                case "1":
                    et_price.setInputType(InputType.TYPE_CLASS_TEXT);
                    break;
                case "2":
                    et_price.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;
            }
       }
        public void setCanCancel(boolean is_can){
          dialog.setCancelable(is_can);
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
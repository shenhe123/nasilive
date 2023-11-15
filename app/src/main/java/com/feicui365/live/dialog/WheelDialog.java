package com.feicui365.live.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.widget.WheelListView;

import java.util.List;

public class WheelDialog extends Dialog {

    private WheelListView wl_wheelView;
    private TextView tv_cancel;
    private TextView tv_success;

    @SuppressLint("PrivateResource")
    public WheelDialog(@NonNull Context context) {
        super(context, R.style.Base_Theme_AppCompat_Dialog);
        initView();
        initListener();
    }

    private void initView() {
        View rootView = View.inflate(getContext(), R.layout.widget_wheel_dialog, null);
        wl_wheelView = (WheelListView) rootView.findViewById(R.id.wl_wheelView);
        tv_cancel = (TextView) rootView.findViewById(R.id.tv_cancel);
        tv_success = (TextView) rootView.findViewById(R.id.tv_success);
        setContentView(rootView);
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.argb(0, 0, 0, 0)));
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            //设置dialog宽度充满屏幕
            window.getDecorView().setPadding(0, 0, 0, 0);
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }
    }

    private void initListener() {
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tv_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectLabel = wl_wheelView.getSelectLabel();
                if (listener != null) {
                    listener.onClickOk(wl_wheelView.getSelectIndex(), selectLabel);
                }
            }
        });
    }

    public void setLabels(List<String> labels) {
        wl_wheelView.setLabels(labels);
    }

    public void setOnWheelSelectListener(OnWheelSelectListener listener) {
        this.listener = listener;
    }

    private OnWheelSelectListener listener;

    public interface OnWheelSelectListener {
        void onClickOk(int index, String selectLabel);
    }

}
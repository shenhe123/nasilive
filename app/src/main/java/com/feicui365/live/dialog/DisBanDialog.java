package com.feicui365.live.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.feicui365.live.R;

public class DisBanDialog extends BaseDialog implements View.OnClickListener{

    private  Context mContext;

    private Button ban_cancel_bt;
    private Button ban_confirm_bt;
    private TextView quite_user_name;


    public DisBanDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public static DisBanDialog create(Context context) {
        DisBanDialog dialog = new DisBanDialog(context);
        dialog.show();
        return dialog;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ban_cancel_bt:
                 dismiss();
                break;
            case R.id.ban_confirm_bt:
                 dismiss();
                 if (onContentClickCallback != null) {
                      onContentClickCallback.onContentClick("0");
                 }
                break;
        }
    }

    @Override
    protected boolean isBottomPop() {
        return true;
    }


    @Override
    protected int getContentView() {
        return R.layout.dialog_disban;
    }


    public DisBanDialog setDesc(String firstText) {
        setText(quite_user_name, firstText);
        return this;
    }


    private void setText(TextView textView, String text) {
        if (textView == null) {
            return;
        }
        String name = "您确定要解除对"+text+"的禁言吗？";
        textView.setText(!TextUtils.isEmpty(text) ? name : "");
    }

    @Override
    protected void initView() {
        super.initView();

        ban_cancel_bt = findViewById(R.id.ban_cancel_bt);
        ban_confirm_bt = findViewById(R.id.ban_confirm_bt);
        quite_user_name = findViewById(R.id.quite_user_name);
    }

    @Override
    protected void initEvents() {
        super.initEvents();

        ban_cancel_bt.setOnClickListener(this);
        ban_confirm_bt.setOnClickListener(this);
    }
}

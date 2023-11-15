package com.feicui365.live.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.feicui365.live.R;

public class KickDialog extends BaseDialog implements View.OnClickListener{

    private  Context mContext;
    private Button ban_cancel_bt;
    private Button ban_kick_bt;
    private TextView kick_nickname_tv;
    private int type = 1 ;

    public KickDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public static KickDialog create(Context context) {
        KickDialog dialog = new KickDialog(context);
        dialog.show();
        return dialog;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ban_cancel_bt:
                 dismiss();
                break;
            case R.id.ban_kick_bt:
                 dismiss();
                 if (onClickCallback != null) {
                     onClickCallback.onClickType(BaseDialog.SELECT_FIRST);
                 }
                break;

        }
    }

    @Override
    protected boolean isBottomPop() {
        return true;
    }


    public KickDialog setDesc(String firstText) {
        setText(kick_nickname_tv, firstText);
        return this;
    }


    private void setText(TextView textView, String text) {
        if (textView == null) {
            return;
        }
        String name = "您确认将"+text+"请出直播间吗？";
        textView.setText(!TextUtils.isEmpty(text) ? name : "");
    }


    @Override
    protected int getContentView() {
        return R.layout.dialog_kick;
    }

    @Override
    protected void initView() {
        super.initView();
        ban_cancel_bt = findViewById(R.id.ban_cancel_bt);
        ban_kick_bt = findViewById(R.id.ban_kick_bt);
        kick_nickname_tv = findViewById(R.id.kick_nickname_tv);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        ban_cancel_bt.setOnClickListener(this);
        ban_kick_bt.setOnClickListener(this);
    }
}

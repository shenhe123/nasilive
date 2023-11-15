package com.feicui365.live.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.feicui365.live.R;

public class BanDialog extends BaseDialog implements View.OnClickListener{

    private  Context mContext;
    private RadioButton ban_text_hide;
    private RadioButton ban_text_visible;
    private Button ban_cancel_bt;
    private Button ban_confirm_bt;
    private int type = 1 ;
    //禁言类型 【1 评论主播可见，观众不可见，2 评论主播不可见，观众不可见】

    public BanDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public static BanDialog create(Context context) {
        BanDialog dialog = new BanDialog(context);
        dialog.show();
        return dialog;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ban_cancel_bt:
                 dismiss();
                if (onContentTypeClickCallback != null) {
                    onContentTypeClickCallback.onContentTypeClick(0,type+"");
                }
                break;
            case R.id.ban_confirm_bt:
                 dismiss();
                 if (onContentTypeClickCallback != null) {
                     onContentTypeClickCallback.onContentTypeClick(1,type+"");
                 }
                break;
            case R.id.ban_text_hide:
                type = 1 ;
                ban_text_hide.setChecked(true);
                ban_text_visible.setChecked(false);
                break;
            case R.id.ban_text_visible:
                ban_text_hide.setChecked(false);
                ban_text_visible.setChecked(true);
                type = 2 ;
                break;
        }
    }

    @Override
    protected boolean isBottomPop() {
        return true;
    }


    @Override
    protected int getContentView() {
        return R.layout.dialog_ban;
    }

    @Override
    protected void initView() {
        super.initView();
        ban_text_hide = findViewById(R.id.ban_text_hide);
        ban_text_visible = findViewById(R.id.ban_text_visible);
        ban_cancel_bt = findViewById(R.id.ban_cancel_bt);
        ban_confirm_bt = findViewById(R.id.ban_confirm_bt);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        ban_text_hide.setOnClickListener(this);
        ban_text_visible.setOnClickListener(this);
        ban_cancel_bt.setOnClickListener(this);
        ban_confirm_bt.setOnClickListener(this);
    }
}

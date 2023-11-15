package com.feicui365.live.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.feicui365.nasinet.R;
import com.feicui365.nasinet.utils.DialogListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

/**
 * Desc: 提示信息对话框
 * <p>
 *
 * @author Create by sinochem on 2018/8/20
 * <p>
 * Version: 1.0.0
 */
class PromptDialog {

    private DialogPlus dialogPlus;
    private TextView dialogTitle;
    private Button confirm, cancel;
    private String confirmStr, cancelStr;
    private String title;
    private DialogListener listener;

    public PromptDialog(DialogListener listener) {
        this.listener = listener;
    }

    PromptDialog setPromptTitle(String title) {
        this.title = title;
        return this;
    }

    PromptDialog setConfirmStr(String confirmStr) {
        this.confirmStr = confirmStr;
        return this;
    }

    PromptDialog setCancelStr(String cancelStr) {
        this.cancelStr = cancelStr;
        return this;
    }

    DialogPlus createPromptDialog(Context context) {
        dialogPlus = DialogPlus.newDialog(context)
                .setGravity(Gravity.BOTTOM)
                .setCancelable(true)
                .setContentHolder(new ViewHolder(R.layout.dialog_prompt))
                .setContentBackgroundResource(Color.TRANSPARENT)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        int id = view.getId();
                        if (id == R.id.dialog_confirm) {
                            if (listener != null) {
                                listener.onDialogConfirm(null);
                            }
                            dialogPlus.dismiss();
                        } else if (id == R.id.dialog_cancel) {
                            if (listener != null) {
                                listener.onDialogCancel();
                            }
                            dialogPlus.dismiss();
                        }
                    }
                }).create();
        dialogTitle = (TextView) dialogPlus.findViewById(R.id.dialog_title);
        if (!TextUtils.isEmpty(title)) dialogTitle.setText(title);
        confirm = (Button) dialogPlus.findViewById(R.id.dialog_confirm);
        if (!TextUtils.isEmpty(confirmStr)) confirm.setText(confirmStr);
        cancel = (Button) dialogPlus.findViewById(R.id.dialog_cancel);
        if (!TextUtils.isEmpty(cancelStr)) cancel.setText(cancelStr);
        return dialogPlus;
    }

}

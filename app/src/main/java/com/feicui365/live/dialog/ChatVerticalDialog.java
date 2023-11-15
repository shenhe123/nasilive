/*
package com.feicui365.live.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.feicui365.live.R;
import com.feicui365.live.ui.fragment.ChatVerticalFragment;
import com.feicui365.live.util.DpUtil;
import com.feicui365.live.widget.Dialogs;
import com.nasinet.nasinet.utils.DipPxUtils;

import static android.content.Context.INPUT_METHOD_SERVICE;



@SuppressLint("ValidFragment")
public class ChatVerticalDialog extends AbsDialogFragment implements View.OnClickListener, EditText.OnEditorActionListener {
    private InputMethodManager imm;
    public EditText chat_et;

    private int mOriginHeight;
    private Handler mHandler;
    public LinearLayout ll_send;
    ChatVerticalFragment chatVerticalFragment;

    public void setOnComentSendListener(ChatVerticalDialog.OnComentSendListener onComentSendListener) {
        this.onComentSendListener = onComentSendListener;
    }

    public ChatVerticalDialog.OnComentSendListener onComentSendListener;

    public interface OnComentSendListener {

        void onSendSucess(String comment);
    }


    @SuppressLint("ValidFragment")
    public ChatVerticalDialog(ChatVerticalFragment chatVerticalFragment) {

        this.chatVerticalFragment = chatVerticalFragment;

    }


    @Override
    public int getLayoutId() {
        return R.layout.dialog_live_input;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.transparentBackgroundDiaolg;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        mOriginHeight = DpUtil.dp2px(60);
        params.height = mOriginHeight;
        params.gravity = Gravity.BOTTOM;
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imm = (InputMethodManager) mContext.getSystemService(INPUT_METHOD_SERVICE);
        mHandler = new Handler();

        chat_et = mRootView.findViewById(R.id.chat_et);
        ll_send = mRootView.findViewById(R.id.ll_send);
        chat_et.setOnClickListener(this);
        chat_et.setOnEditorActionListener(this);
        chat_et.getLayoutParams().width = 1;
        chat_et.getLayoutParams().height = 1;
        // chatVerticalFragment.ll_bottom.setVisibility(View.GONE);
        if (mHandler != null) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showSoftInput();
                }
            }, 100);
        }


    }


    private void showSoftInput() {
        //软键盘弹出
        if (chat_et != null) {
            chat_et.setVisibility(View.VISIBLE);
            chat_et.requestFocus();
        }
        if (imm != null) {
            if (mHandler != null) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imm.showSoftInput(chat_et, 0);
                    }
                }, 100);
            }
        }

    }

    private void hideSoftInput() {
        if (imm != null) {
            imm.hideSoftInputFromWindow(chat_et.getWindowToken(), 0);
            imm.hideSoftInputFromInputMethod(chat_et.getWindowToken(), 0);
        }
    }


    @Override
    public void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        mHandler = null;
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (!canClick()) {
            return;
        }
    }


    */
/**
     * 改变高度
     *//*

    private void changeHeight(int deltaHeight) {
        Dialog dialog = getDialog();
        if (dialog == null) {
            return;
        }
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams params = window.getAttributes();
        params.height = mOriginHeight + deltaHeight;
        window.setAttributes(params);
    }


    @Override
    public void onStart() {
        super.onStart();
        getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                hideSoftInput();
                dismissAllowingStateLoss();
            }
        });
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        dismiss();
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            if (chat_et.getText().toString().equals("")) {
                Toast.makeText(getContext(), "请输入内容.", Toast.LENGTH_SHORT).show();
                return true;
            }


            if (null != onComentSendListener) {
                onComentSendListener.onSendSucess(chat_et.getText().toString());
            }


            return true;
        }
        return false;
    }


    private Dialog dialog;

    public void showLoading() {
        hideLoading();
        dialog = Dialogs.createLoadingDialog(getContext());
        dialog.show();
    }


    public void hideLoading() {
        if (null != dialog) {
            dialog.dismiss();
        }
    }

    public void openInput() {
        if (ll_send==null) {
            return;
        }

        ll_send.setBackgroundColor(getResources().getColor(R.color.white));
        chat_et.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
        chat_et.getLayoutParams().height = DipPxUtils.dip2px(getContext(), 30);
        chat_et.setBackground(getContext().getResources().getDrawable(R.drawable.send_text_live_bg));
        alpha(mRootView);
    }

    public void alpha(View v) {

        AlphaAnimation alphaAni = new AlphaAnimation(0.2f, 1);

        alphaAni.setDuration(300);

        alphaAni.setRepeatCount(0);

        alphaAni.setRepeatMode(Animation.REVERSE);

        v.startAnimation(alphaAni);
    }

}
*/

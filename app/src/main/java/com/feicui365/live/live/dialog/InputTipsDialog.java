package com.feicui365.live.live.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.feicui365.live.R;

import com.feicui365.live.base.BaseDialogFragment;
import com.feicui365.live.interfaces.OnSendChatListener;
import com.feicui365.live.live.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class InputTipsDialog extends BaseDialogFragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.rl_cancel)
    RelativeLayout rlCancel;
    @BindView(R.id.rl_submit)
    RelativeLayout rlSubmit;
    @BindView(R.id.et_input)
    EditText mInput;


    String mTitle;
    String mContent;
    String hint;
    boolean isHideCancel;
    String submitText;
    String cancelText;
    View.OnClickListener mCancelClick;
    OnSendChatListener mSubmitClick;

    public void setCancelClick(View.OnClickListener mCancelClick) {
        this.mCancelClick = mCancelClick;
    }

    public void setSubmitClick(OnSendChatListener mSubmitClick) {
        this.mSubmitClick = mSubmitClick;
    }

    public InputTipsDialog() {
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public void setHideCancel(boolean hideCancel) {
        isHideCancel = hideCancel;
    }

    public void setSubmitText(String submitText) {
        this.submitText = submitText;
    }

    public void setCancelText(String cancelText) {
        this.cancelText = cancelText;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_input_tips;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.transparentBackgroundDiaolg;
    }

    @Override
    protected boolean canCancel() {
        return false;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(ArmsUtils.isStringEmpty(mTitle)){
            tvTitle.setText(mTitle);
        }
        if(ArmsUtils.isStringEmpty(mContent)){
            tvContent.setText(mContent);
        }
        if(ArmsUtils.isStringEmpty(submitText)){
            tvSubmit.setText(submitText);
        }
        if(ArmsUtils.isStringEmpty(cancelText)){
            tvCancel.setText(cancelText);
        }
        if(ArmsUtils.isStringEmpty(hint)){
            mInput.setText(cancelText);
        }
        if(isHideCancel){
            rlCancel.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.rl_cancel, R.id.rl_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_cancel:
                dismiss();
                if(mCancelClick!=null){
                    mCancelClick.onClick(view);
                }
                break;
            case R.id.rl_submit:
                dismiss();
                if(mSubmitClick!=null){
                    mSubmitClick.sendText(mInput.getText().toString());
                }
                break;


        }
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height =  WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }
}

package com.feicui365.live.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.base.Constants;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.widget.Dialogs;
import com.feicui365.live.model.entity.Comment;
import com.feicui365.live.ui.act.UserShortVideoActivity;
import com.feicui365.live.util.DpUtil;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.TextRender;
import com.feicui365.nasinet.utils.DipPxUtils;

import static android.content.Context.INPUT_METHOD_SERVICE;



@SuppressLint("ValidFragment")
public class UserShortCommentDialog extends AbsDialogFragment implements View.OnClickListener, ChatFaceDialog.ActionListener, EditText.OnEditorActionListener {

    private InputMethodManager imm;
    public EditText mInput;
    private boolean mOpenFace = true;
    private int mOriginHeight;
    private ImageView btn_face;
    private ChatFaceDialog mChatFaceDialog;
    private Handler mHandler;
    String video_id;
    private LinearLayout ll_send;
    private RelativeLayout rl_face;

    public void setOnComentSendListener(OnComentSendListener onComentSendListener) {
        this.onComentSendListener = onComentSendListener;
    }

    public UserShortCommentDialog.OnComentSendListener onComentSendListener;

    public interface OnComentSendListener {

        void onSendSucess(Comment comment);
    }



    public UserShortCommentDialog(String video_id) {

        this.video_id=video_id;
    }


    @Override
    public int getLayoutId() {
        return R.layout.dialog_video_input;
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
    //    window.setWindowAnimations(R.style.bottomToTopAnim2);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        mOriginHeight = DpUtil.dp2px(48);
        params.height = mOriginHeight;
        params.gravity = Gravity.BOTTOM;
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN );
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imm = (InputMethodManager) mContext.getSystemService(INPUT_METHOD_SERVICE);
        mHandler = new Handler();
        mInput = mRootView.findViewById(R.id.input);
        mInput.setOnClickListener(this);
        mInput.setOnEditorActionListener(this);
        btn_face = mRootView.findViewById(R.id.btn_face);
        btn_face.setOnClickListener(this);
        Bundle bundle = getArguments();
        if(null!=bundle){
            mOpenFace = bundle.getBoolean(Constants.VIDEO_FACE_OPEN, false);

        }
        ll_send=mRootView.findViewById(R.id.ll_send);
        rl_face=mRootView.findViewById(R.id.rl_face);
        mInput.getLayoutParams().width=1;
        mInput.getLayoutParams().height=1;
        btn_face.getLayoutParams().width=1;
        btn_face.getLayoutParams().height=1;


        if (mOpenFace) {
            if (mHandler != null) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showFace();
                    }
                }, 200);
            }
            mOpenFace=false;
        } else {
            if (mHandler != null) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showSoftInput();
                    }
                }, 200);
            }
            mOpenFace=true;
        }

    }


    private void showSoftInput() {
        //软键盘弹出
        if (imm != null) {
            if (mHandler != null) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imm.showSoftInput(mInput, 0);


                    }
                }, 200);
            }

        }
        if (mInput != null) {
            mInput.requestFocus();
        }
    }

    private void hideSoftInput() {
        if (imm != null) {
            String a = mInput.getWindowToken() + "";
            imm.hideSoftInputFromWindow(mInput.getWindowToken(), 0);
            imm.hideSoftInputFromInputMethod(mInput.getWindowToken(), 0);
        }
    }


    @Override
    public void onDestroy() {

        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        mHandler = null;
        if (mChatFaceDialog != null) {
            hideSoftInput();
            mChatFaceDialog.dismiss();
        }
        mChatFaceDialog = null;
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (!canClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.btn_face:
                clickFace();
                break;
            case R.id.input:
                clickInput();
                break;
        }
    }

    private void clickInput() {
        hideFace();
    }

    private void clickFace() {
        if (mOpenFace) {
            hideSoftInput();
           // showSoftInput();
            if (mHandler != null) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showFace();

                    }
                }, 200);
            }
            mOpenFace=false;
        } else {
            hideFace();
            showSoftInput();
            mOpenFace=true;
        }

    }

    private void showFace() {

        View faceView =((UserShortVideoActivity) getContext()).initFaceView();
        changeHeight(((UserShortVideoActivity) getContext()).mFaceViewHeight);
        if (faceView != null) {
            mChatFaceDialog = new ChatFaceDialog(mRootView, faceView, false, UserShortCommentDialog.this);
            mChatFaceDialog.show();
            openInput();
            // showSoftInput();
        }
    }

    private void hideFace() {

        if (mChatFaceDialog != null) {
            mChatFaceDialog.dismiss();
        }
      //  dismiss();
    }

    /**
     * 改变高度
     */
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
    public void onFaceDialogDismiss() {
        changeHeight(0);
        mChatFaceDialog = null;
    }


    /**
     * 点击表情上面的删除按钮
     */
    public void onFaceDeleteClick() {
        if (mInput != null) {
            int selection = mInput.getSelectionStart();
            String text = mInput.getText().toString();
            if (selection > 0) {
                String text2 = text.substring(selection - 1, selection);
                if ("]".equals(text2)) {
                    int start = text.lastIndexOf("[", selection);
                    if (start >= 0) {
                        mInput.getText().delete(start, selection);
                    } else {
                        mInput.getText().delete(selection - 1, selection);
                    }
                } else {
                    mInput.getText().delete(selection - 1, selection);
                }
            }
        }
    }

    /**
     * 点击表情
     */
    public void onFaceClick(String str, int faceImageRes) {
        if (mInput != null) {
            Editable editable = mInput.getText();
            editable.insert(mInput.getSelectionStart(), TextRender.getFaceImageSpan(str, faceImageRes));
        }
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
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
      //  dismissAllowingStateLoss();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        dismiss();
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            if(mInput.getText().toString().equals("")){


                ToastUtils.showT("请输入内容");
                return true;
            }

            String rootid="0";
            String tocommentid="0";
            String touid="0";
            String content=mInput.getText().toString();
            showLoading();
            HttpUtils.getInstance().setComment(video_id, content, rootid, tocommentid, touid, new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    hideLoading();
                    JSONObject data = HttpUtils.getInstance().check(response);
                    if (data.get("status").toString().equals("0")) {
                        if (null != data.getJSONObject("data")) {

                            Comment comment = JSON.toJavaObject(data.getJSONObject("data"), Comment.class);
                            if(null!=onComentSendListener){
                                onComentSendListener.onSendSucess(comment);
                            }
                        }

                    }
                }
            });

            return true;
        }
        return false;
    }

    public void sendMessage(){
        if(mInput.getText().toString().equals("")){
           return;
        }
        HttpUtils.getInstance().setComment(video_id, mInput.getText().toString(), "0", "0", "0", new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                hideLoading();
                JSONObject data = HttpUtils.getInstance().check(response);
                if (data.get("status").toString().equals("0")) {
                    if (null != data.getJSONObject("data")) {
                        Comment comment = JSON.toJavaObject(data.getJSONObject("data"), Comment.class);
                        if(null!=onComentSendListener){
                            onComentSendListener.onSendSucess(comment);
                        }
                    }

                }
            }
        });
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

    public void openInput(){
        ll_send.setBackgroundColor(getContext().getResources().getColor(R.color.color_F6F5F5));
        rl_face.setBackgroundColor(getContext().getResources().getColor(R.color.color_F6F5F5));
        mInput.getLayoutParams().width=0;
        mInput.getLayoutParams().height= DipPxUtils.dip2px(getContext(),35);
        mInput.setBackground(getContext().getResources().getDrawable(R.drawable.shape_corner_white));
        btn_face.getLayoutParams().width= DipPxUtils.dip2px(getContext(),21);
        btn_face.getLayoutParams().height= DipPxUtils.dip2px(getContext(),21);
    }
}

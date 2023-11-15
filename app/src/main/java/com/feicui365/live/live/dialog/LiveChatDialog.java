package com.feicui365.live.live.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;




import com.feicui365.live.R;
import com.feicui365.live.base.BaseDialogFragment;
import com.feicui365.live.dialog.ChatFaceDialog;
import com.feicui365.live.interfaces.OnFaceClickListener;
import com.feicui365.live.live.adapter.ImChatFacePagerHorizontalAdapter;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.ui.adapter.ImChatFacePagerAdapter;
import com.feicui365.live.util.TextRender;

import static android.content.Context.INPUT_METHOD_SERVICE;


@SuppressLint("ValidFragment")
public class LiveChatDialog extends BaseDialogFragment implements View.OnClickListener, EditText.OnEditorActionListener {
    public EditText mInput;
    private InputMethodManager imm;
    private ImageView mFace;
    private int mOriginHeight;
    private ChatFaceDialog mChatFaceDialog;
    private Handler mHandler;
    private boolean mFaceShow;
    private int mFaceHeight;
    private View faceView;
    private TextView mSend;
    private int type = 1;

    private String hintText;
    private String mText = "";

    int maxInput;


    public LiveChatDialog() {
    }

    public LiveChatDialog(int maxInput) {
        this.maxInput = maxInput;
    }

    //设置横竖屏直播对话框类型
    public void setType(int type) {
        this.type = type;
    }

    public void setOnComentSendListener(OnComentSendListener onComentSendListener) {
        this.onComentSendListener = onComentSendListener;
    }

    public OnComentSendListener onComentSendListener;

    public interface OnComentSendListener {
        void onSend(String comment);

        void onDismiss();
    }
    //设置横竖屏直播对话框类型

    @Override
    public int getLayoutId() {
        return R.layout.dialog_live_input_new;
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
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        mOriginHeight = ArmsUtils.dip2px(getContext(), 60);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imm = (InputMethodManager) mContext.getSystemService(INPUT_METHOD_SERVICE);
        mHandler = new Handler();
        mInput = mRootView.findViewById(R.id.chat_et);

        if (maxInput != 0) {
            mInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxInput)});
        }


        mInput.setOnClickListener(this);
        mInput.setOnEditorActionListener(this);

        if (hintText != null) {
            mInput.setHint(hintText);
        }
        mInput.setText(TextRender.renderChatMessage2(mText));
        mSend = mRootView.findViewById(R.id.tv_send_button);
        mSend.setOnClickListener(this);
        mFace = mRootView.findViewById(R.id.iv_face);
        mFace.setOnClickListener(this);
        if (mHandler != null) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showSoftInput();
                }
            }, 100);
        }

        mInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mText = editable.toString();
            }
        });
    }


    //延迟展示输入框
    private void showSoftInput() {
        //软键盘弹出
        if (imm != null) {
            if (mHandler != null) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imm.showSoftInput(mInput, 0);
                    }
                }, 100);
            }

        }
        if (mInput != null) {
            mInput.requestFocus();
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
        switch (v.getId()) {
            case R.id.iv_face:

                if (mHandler != null) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //表情开了
                            if (mFaceShow) {
                                hideFace();
                                imm.showSoftInput(mInput, 0);
                                mSend.setVisibility(View.VISIBLE);
                            } else {
                                mFace.setVisibility(View.VISIBLE);
                                showFace(getContext());
                            }
                        }
                    }, 150);

                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (!mFaceShow) {
                                hideSoftInput();

                            }
                        }
                    }, 100);
                }


                break;

            case R.id.tv_send_button:
                if (ArmsUtils.isStringEmpty(mInput.getText().toString())) {
                    dismiss();
                    if (null != onComentSendListener) {
                        mText = "";
                        onComentSendListener.onSend(mInput.getText().toString());
                    }
                }

                break;

            case R.id.chat_et:
                hideFace();
                mSend.setVisibility(View.VISIBLE);
                break;


        }

    }


    private void showFace(Context context) {

        if (faceView == null) {
            faceView = initFaceView(context);
            mFaceHeight = faceView.getMeasuredHeight();
        }
        changeHeight(mFaceHeight);
        if (faceView != null) {
            if (mChatFaceDialog == null) {
                mChatFaceDialog = new ChatFaceDialog(mRootView, faceView, false, new ChatFaceDialog.ActionListener() {
                    @Override
                    public void onFaceDialogDismiss() {

                    }
                });
            }
            mFaceShow = true;
            mChatFaceDialog.show();

        }
    }

    private void hideFace() {
        if (mChatFaceDialog != null) {
            changeHeight(0);
            mFaceShow = false;
            mChatFaceDialog.dismiss();
            mChatFaceDialog = null;

        }
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

    public View initFaceView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.view_chat_face, null);
        v.measure(0, 0);
        final RadioGroup radioGroup = v.findViewById(R.id.radio_group);
        ViewPager viewPager = v.findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(10);
        PagerAdapter adapter;
        if (type == 1) {
            adapter = new ImChatFacePagerAdapter(context, new OnFaceClickListener() {
                @Override
                public void onFaceClick(String str, int faceImageRes) {
                    if (mInput != null) {
                        Editable editable = mInput.getText();
                        editable.insert(mInput.getSelectionStart(), TextRender.getFaceImageSpan(str, faceImageRes));
                    }
                }

                @Override
                public void onFaceDeleteClick() {
                    onDeleteFaceClick();

                }
            });

        } else {
            adapter = new ImChatFacePagerHorizontalAdapter(context, new OnFaceClickListener() {
                @Override
                public void onFaceClick(String str, int faceImageRes) {
                    if (mInput != null) {
                        Editable editable = mInput.getText();
                        editable.insert(mInput.getSelectionStart(), TextRender.getFaceImageSpan(str, faceImageRes));
                    }
                }

                @Override
                public void onFaceDeleteClick() {
                    onDeleteFaceClick();

                }
            });

        }
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) radioGroup.getChildAt(position)).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0, pageCount = adapter.getCount(); i < pageCount; i++) {
            RadioButton radioButton = (RadioButton) inflater.inflate(R.layout.view_chat_indicator, radioGroup, false);
            radioButton.setId(i + 10000);
            if (i == 0) {
                radioButton.setChecked(true);
            }
            radioGroup.addView(radioButton);
        }
        return v;
    }

    public void onDeleteFaceClick() {
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

    //输入监听
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (actionId == EditorInfo.IME_ACTION_SEND) {
            if (mInput.getText().toString().equals("")) {
                return true;
            }

            if (null != onComentSendListener) {
                dismiss();
                mText = "";
                onComentSendListener.onSend(mInput.getText().toString());
            }

            return true;
        }
        return false;
    }


    private void hideSoftInput() {
        if (imm != null) {
            imm.hideSoftInputFromWindow(mInput.getWindowToken(), 0);
            imm.hideSoftInputFromInputMethod(mInput.getWindowToken(), 0);
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        if (onComentSendListener != null) {
            onComentSendListener.onDismiss();
        }
        if (mChatFaceDialog != null) {
            mFaceShow = false;
            mChatFaceDialog.dismiss();
            mChatFaceDialog = null;

        }
        super.onDismiss(dialog);
    }

    public void setHint(String hint) {
        hintText = hint;

    }

}

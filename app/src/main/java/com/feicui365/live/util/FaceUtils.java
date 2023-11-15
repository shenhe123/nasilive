/*
package com.fengzq.live.util;

import android.content.Context;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fengzq.live.R;
import com.fengzq.live.dialog.ChatFaceDialog;

public class FaceUtils {
    //上下文
    static Context context;

    private static FaceUtils faceUtils;

    public static FaceUtils getInstance() {
        return faceUtils;
    }

    public static void init(Context contexts) {
        context = contexts;
    }


    private ChatFaceDialog mChatFaceDialog;//表情弹窗
    private View mFaceView;//表情控件
    private int mFaceViewHeight;//表情控件高度
    private int mMoreViewHeight;//更多控件的高度


    */
/**
     * 显示表情弹窗
     *//*

    private void showFace() {
        if (mChatFaceDialog != null && mChatFaceDialog.isShowing()) {
            return;
        }

        if (mFaceView == null) {
            mFaceView = initFaceView();
        }

        mChatFaceDialog = new ChatFaceDialog(context, mFaceView, true, new Actio);
        mChatFaceDialog.show();
        mNeedToBottom = true;
    }

    */
/**
     * 隐藏表情弹窗
     *//*

    private boolean hideFace() {
        if (mChatFaceDialog != null) {
            mChatFaceDialog.dismiss();
            mChatFaceDialog = null;
            return true;
        }
        return false;
    }

    */
/**
     * 表情弹窗消失的回调
     *//*

    @Override
    public void onFaceDialogDismiss() {
        if (mActionListener != null && (mType == TYPE_DIALOG || mNeedToBottom)) {
            mActionListener.onPopupWindowChanged(0);
        }
        mChatFaceDialog = null;
        if (mBtnFace != null) {
            mBtnFace.setChecked(false);
        }
    }


    */
/**
     * 点击表情图标按钮
     *//*

    @Override
    public void onFaceClick(String str, int faceImageRes) {
        if (mEditText != null) {
            Editable editable = mEditText.getText();
            editable.insert(mEditText.getSelectionStart(), TextRender.getFaceImageSpan(str, faceImageRes));
        }
    }


    */
/**
     * 初始化表情控件
     *//*

    private View initFaceView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.view_chat_face, null);
        v.measure(0, 0);
        mFaceViewHeight = v.getMeasuredHeight();
        v.findViewById(R.id.btn_send).setOnClickListener(this);
        final RadioGroup radioGroup = v.findViewById(R.id.radio_group);
        ViewPager viewPager = v.findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(10);
        ImChatFacePagerAdapter adapter = new ImChatFacePagerAdapter(mContext, this);
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


}
*/

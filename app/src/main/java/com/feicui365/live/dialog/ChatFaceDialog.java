package com.feicui365.live.dialog;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.PopupWindow;

import com.feicui365.live.R;




public class ChatFaceDialog extends PopupWindow {

    private View mParent;
    private View mContentView;
    private ActionListener mActionListener;

    public ChatFaceDialog(View parent, View contentView, boolean needAnim, ActionListener actionListener) {
        mParent = parent;
        mActionListener = actionListener;
        ViewParent viewParent = contentView.getParent();
        if (viewParent != null) {
            ((ViewGroup) viewParent).removeView(contentView);
        }
        mContentView = contentView;
        setContentView(contentView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(false);
        if (false) {
            setAnimationStyle(R.style.bottomToTopAnim2);
        }
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                ViewParent viewParent = mContentView.getParent();
                if (viewParent != null) {
                    ((ViewGroup) viewParent).removeView(mContentView);
                }
                mContentView = null;
                if (mActionListener != null) {
                    mActionListener.onFaceDialogDismiss();
                }
                mActionListener = null;
            }
        });
    }


    public void show() {
        showAtLocation(mParent, Gravity.BOTTOM, 0, 0);
    }


    public interface ActionListener {
        void onFaceDialogDismiss();
    }

}

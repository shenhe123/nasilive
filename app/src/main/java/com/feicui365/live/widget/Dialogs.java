package com.feicui365.live.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feicui365.nasinet.R;
import com.feicui365.nasinet.utils.DialogListener;
import com.orhanobut.dialogplus.DialogPlus;

/**
 * Desc: 对话框管理类
 * <p>
 *
 * @author Create by sinochem on 2019-08-26
 * <p>
 * Version: 1.0.0
 */
public class Dialogs {

    /**
     * 创建提示对话框
     *
     * @param context  上下文
     * @param title    标题
     * @param listener 监听
     * @return 对话框实例
     */
    public static DialogPlus createPromptDialog(Context context, String title, DialogListener listener) {
        return createPromptDialog(context, title, null, null, listener);
    }

    /**
     * 创建提示对话框
     *
     * @param context     上下文
     * @param title       标题
     * @param confirmName 确认名称
     * @param cancelName  取消名称
     * @param listener    监听
     * @return 对话框实例
     */
    public static DialogPlus createPromptDialog(Context context, String title, String confirmName, String cancelName, DialogListener listener) {
        return new PromptDialog(listener).setPromptTitle(title).setConfirmStr(confirmName).setCancelStr(cancelName).createPromptDialog(context);
    }

    /**
     * 得到自定义的progressDialog
     *
     * @param context 上下文
     * @return dialog
     */
    public static Dialog createLoadingDialog(Context context) {
        return createLoadingDialog(context, null);
    }


    /**
     * 得到自定义的progressDialog
     *
     * @param context 上下文
     * @param msg     提示信息
     * @return dialog
     */
    public static Dialog createLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean needMsg = !TextUtils.isEmpty(msg);
        // 得到加载view
        View v = inflater.inflate(needMsg ? R.layout.loading_dialog_msg : R.layout.loading_dialog, null);
        // 加载布局
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.loading_img);
        if (needMsg) {
            // 提示文字
            TextView tipTextView = (TextView) v.findViewById(R.id.loading_msg);
            // 设置加载信息
            tipTextView.setText(msg);
        }
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.loading_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        // 创建自定义样式dialog
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
        // 不可以用“返回键”取消
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        // 设置布局
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        return loadingDialog;

    }
}

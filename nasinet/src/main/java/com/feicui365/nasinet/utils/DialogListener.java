package com.feicui365.nasinet.utils;

/**
 * Desc: 弹框监听
 * <p>
 *
 * @author Create by sinochem on 2018/8/20
 * <p>
 * Version: 1.0.0
 */
public interface DialogListener extends IDialogListener<String> {

    /**
     * 确认事件回传字符串
     *
     * @param content 回传内容
     */
    @Override
    void onDialogConfirm(String content);

    /**
     * 取消事件
     */
    void onDialogCancel();
}

package com.feicui365.nasinet.utils;

/**
 * Desc: 对话框监听器
 * <p>
 * @author Create by sinochem on 2018/8/20
 * <p>
 * Version: 1.0.0
 */
public interface IDialogListener<T> {

    /**
     * 确认事件
     * @param content 回传内容
     */
    void onDialogConfirm(T content);
}

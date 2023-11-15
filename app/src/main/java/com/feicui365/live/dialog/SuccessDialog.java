package com.feicui365.live.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.feicui365.live.R;

public class SuccessDialog extends Dialog {
    /**
     * 显示的图片
     */


    private ImageView iv_close;

    SuccessDialog walletDialog;

    Activity context;
    public SuccessDialog(Activity context) {
        super(context, R.style.CustomDialog);
        this.context=context;
    }

    /**
     * 底部是否只有一个按钮
     */
    private boolean isSingle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_dialog_layout);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        //初始化界面数据

        //初始化界面控件的事件
        initEvent();
        walletDialog=this;
    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {

        //设置取消按钮被点击后，向外界提供监听
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walletDialog.cancel();
                context.finish();
            }
        });
    }


    @Override
    public void show() {
        super.show();

    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        iv_close = findViewById(R.id.iv_close);

    }


}

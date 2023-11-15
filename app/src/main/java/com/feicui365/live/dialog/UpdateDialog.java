package com.feicui365.live.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.feicui365.live.R;
import com.feicui365.live.util.MyUserInstance;

public class UpdateDialog extends Dialog {


    private RelativeLayout rl_close;
    private TextView tv_content, iv_go;
    String url;
    RoundedCorners roundedCorners = new RoundedCorners(10);
    //通过RequestOptions扩展功能
    RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);

    @SuppressLint("PrivateResource")
    public UpdateDialog(@NonNull Context context, String url) {
        super(context, R.style.Base_Theme_AppCompat_Dialog);
        this.url = url;
        initView();

    }
    Close close;

    public Close getClose() {
        return close;
    }

    public void setClose(Close close) {
        this.close = close;
    }

    public interface  Close{
        void close();
    }

    private void initView() {
        View rootView = View.inflate(getContext(), R.layout.update_dialog, null);
        setContentView(rootView);

        rl_close = rootView.findViewById(R.id.rl_close);
        rl_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if(close!=null){
                    close.close();
                }
            }
        });
        tv_content = rootView.findViewById(R.id.tv_content);
        tv_content.setText(MyUserInstance.getInstance().getUserConfig().getConfig().getUpdate_info_android());
        tv_content.setMovementMethod(ScrollingMovementMethod.getInstance());
        iv_go = rootView.findViewById(R.id.iv_go);
        iv_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                getContext().startActivity(intent);
                dismiss();
                if(close!=null){
                    close.close();
                }
            }
        });

        setCancelable(false);
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.argb(0, 0, 0, 0)));
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.CENTER_HORIZONTAL;
            //设置dialog宽度充满屏幕
            window.getDecorView().setPadding(0, 0, 0, 0);
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }
    }


}
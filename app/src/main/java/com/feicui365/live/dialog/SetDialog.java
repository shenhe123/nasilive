package com.feicui365.live.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.feicui365.live.R;

public class SetDialog extends BaseDialog {

    private  Context mContext;
    private CheckBox lo_default_cb;
    private int type = 1 ;
    private static int default_status = 2 ;   //1隐藏 2显示

    public SetDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public static SetDialog create(Context context,int mirrorType) {
        default_status=mirrorType;
        SetDialog dialog = new SetDialog(context);
        dialog.show();
        return dialog;
    }

    @Override
    protected boolean isBottomPop() {
        return true;
    }


    @Override
    protected int getContentView() {
        return R.layout.dialog_set;
    }

    @Override
    protected void initView() {
        super.initView();
        lo_default_cb = findViewById(R.id.lo_default_cb);
        if (default_status==1){
            lo_default_cb.setChecked(true);
        }else{
            lo_default_cb.setChecked(false);
        }
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        lo_default_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    default_status = 1;
                }else{
                    default_status = 2;
                }
                if (onClickCallback!=null){
                    onClickCallback.onClickType(default_status);
                }
            }
        });

    }
}

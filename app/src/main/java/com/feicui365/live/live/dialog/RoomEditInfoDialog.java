package com.feicui365.live.live.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseDialogFragment;
import com.feicui365.live.interfaces.OnRoomInfoInputClickListenr;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class RoomEditInfoDialog extends BaseDialogFragment {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.tv_close)
    TextView tvClose;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    int mRoomType;


    OnRoomInfoInputClickListenr onRoomInfoInputClickListenr;

    public void setOnRoomInfoInputClickListenr(OnRoomInfoInputClickListenr onRoomInfoInputClickListenr) {
        this.onRoomInfoInputClickListenr = onRoomInfoInputClickListenr;
    }

    public RoomEditInfoDialog() {
    }

    @SuppressLint("ValidFragment")
    public RoomEditInfoDialog(int mRoomType) {
        this.mRoomType = mRoomType;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.room_info_input_dialog;
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

        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPage();
    }

    private void initPage() {
        switch (mRoomType) {
            case 1:
                tvTitle.setText(getString(R.string.st_password_room));
                etPassword.setVisibility(View.VISIBLE);
                etPrice.setVisibility(View.GONE);
                break;
            case 2:
                tvTitle.setText(getString(R.string.st_price_room));
                etPassword.setVisibility(View.GONE);
                etPrice.setVisibility(View.VISIBLE);
                etPrice.setHint("请输入收费金额(金币/分钟)");
                break;

        }


    }


    @OnClick({R.id.tv_close, R.id.tv_confirm})
    public void onClick(View view) {
        if (!ArmsUtils.canClick()) {
            return;
        }
        switch (view.getId()) {

            case R.id.tv_close:
                dismiss();
                break;
            case R.id.tv_confirm:
                String mPrice = etPrice.getText().toString();
                String mPassword = etPassword.getText().toString();
                if(mRoomType==1){

                    if (!ArmsUtils.isStringEmpty(mPassword)) {
                        ToastUtils.showT("请输入密码");
                        return;
                    }


                    if (onRoomInfoInputClickListenr != null) {
                        onRoomInfoInputClickListenr.onResult(mRoomType, mPassword,null);
                    }
                }else   if(mRoomType==2){
                    if (!ArmsUtils.isStringEmpty(mPrice)) {
                        ToastUtils.showT("请输入价格");
                        return;
                    }

                    if(Integer.valueOf(mPrice)<=0){
                        ToastUtils.showT("请输入正确价格");
                        return;
                    }

                    if (onRoomInfoInputClickListenr != null) {
                        onRoomInfoInputClickListenr.onResult(mRoomType, null,mPrice);
                    }
                }


                dismiss();

                break;

        }
    }


}

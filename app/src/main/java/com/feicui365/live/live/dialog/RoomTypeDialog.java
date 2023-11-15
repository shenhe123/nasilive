package com.feicui365.live.live.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.feicui365.live.R;
import com.feicui365.live.base.BaseDialogFragment;
import com.feicui365.live.interfaces.OnRoomInfoInputClickListenr;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.widget.DrawableTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class RoomTypeDialog extends BaseDialogFragment {


    @BindView(R.id.dtv_normal)
    DrawableTextView dtvNormal;
    @BindView(R.id.dtv_price)
    DrawableTextView dtvPrice;
    @BindView(R.id.dtv_password)
    DrawableTextView dtvPassword;
    int mRoomType;

    OnRoomInfoInputClickListenr onRoomInfoInputClickListenr;

    public void setOnRoomInfoInputClickListenr(OnRoomInfoInputClickListenr onRoomInfoInputClickListenr) {
        this.onRoomInfoInputClickListenr = onRoomInfoInputClickListenr;
    }
    public RoomTypeDialog() {
    }

    @SuppressLint("ValidFragment")
    public RoomTypeDialog(int mRoomType) {
        this.mRoomType = mRoomType;

    }

    @Override
    protected int getLayoutId() {
        return R.layout.room_type_dialog;
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

        params.height = ArmsUtils.dip2px(getContext(),100);
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        swticType(mRoomType);
    }


    @OnClick({R.id.rl_normal, R.id.rl_price, R.id.rl_password})
    public void onClick(View view) {
        if (!ArmsUtils.canClick()) {
            return;
        }
        switch (view.getId()) {

            case R.id.rl_normal:
                swticType(0);
                if(onRoomInfoInputClickListenr!=null){
                    onRoomInfoInputClickListenr.onResult(mRoomType,null,null);
                }
                dismiss();
                break;
            case R.id.rl_price:

                showInputDiaolg(2);
                break;
            case R.id.rl_password:

                showInputDiaolg(1);
                break;
        }
    }


    //0普通,1密码,2收费
    private void swticType(int type) {
        mRoomType=type;
        switch (type) {
            case 0:
                dtvNormal.setTopDrawable(getResources().getDrawable(R.drawable.ic_room_normal_pre));
                dtvNormal.setTextColor(getResources().getColor(R.color.color_room_chose));
                dtvPrice.setTopDrawable(getResources().getDrawable(R.drawable.ic_room_paid));
                dtvPrice.setTextColor(getResources().getColor(R.color.white));
                dtvPassword.setTopDrawable(getResources().getDrawable(R.drawable.ic_room_password));
                dtvPassword.setTextColor(getResources().getColor(R.color.white));

                break;
            case 1:
                dtvNormal.setTopDrawable(getResources().getDrawable(R.drawable.ic_room_normal));
                dtvNormal.setTextColor(getResources().getColor(R.color.white));
                dtvPrice.setTopDrawable(getResources().getDrawable(R.drawable.ic_room_paid));
                dtvPrice.setTextColor(getResources().getColor(R.color.white));
                dtvPassword.setTopDrawable(getResources().getDrawable(R.drawable.ic_room_password_pre));
                dtvPassword.setTextColor(getResources().getColor(R.color.color_room_chose));

                break;
            case 2:
                dtvNormal.setTopDrawable(getResources().getDrawable(R.drawable.ic_room_normal));
                dtvNormal.setTextColor(getResources().getColor(R.color.white));
                dtvPrice.setTopDrawable(getResources().getDrawable(R.drawable.ic_room_paid_pre));
                dtvPrice.setTextColor(getResources().getColor(R.color.color_room_chose));
                dtvPassword.setTopDrawable(getResources().getDrawable(R.drawable.ic_room_password));
                dtvPassword.setTextColor(getResources().getColor(R.color.white));

                break;

        }


    }


    private void showInputDiaolg(int type){
        RoomEditInfoDialog roomEditInfoDialog=new RoomEditInfoDialog(type);
        roomEditInfoDialog.show(getFragmentManager(),"");
        roomEditInfoDialog.setOnRoomInfoInputClickListenr(new OnRoomInfoInputClickListenr() {
            @Override
            public void onResult(int mRoomType, String mPassword, String mPrice) {
                swticType(mRoomType);
                if(onRoomInfoInputClickListenr!=null){
                    onRoomInfoInputClickListenr.onResult(mRoomType,mPassword,mPrice);
                }
                dismiss();
            }


        });


    }
}

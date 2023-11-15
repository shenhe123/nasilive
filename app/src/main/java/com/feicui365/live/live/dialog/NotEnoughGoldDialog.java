package com.feicui365.live.live.dialog;

import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;




import com.feicui365.nasinet.utils.AppManager;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseDialogFragment;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.ui.act.MyWalletActivity;
import com.feicui365.live.util.MyUserInstance;

import butterknife.OnClick;

/**
 *
 */
public class NotEnoughGoldDialog extends BaseDialogFragment {






    @Override
    protected int getLayoutId() {
        return R.layout.dialog_not_enough_gold;
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

        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }


    @OnClick({R.id.tv_cancel, R.id.tv_submit})
    public void onClick(View view) {
        if (!ArmsUtils.canClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_submit:
                if (MyUserInstance.getInstance().visitorIsLogin(getActivity())) {
                    AppManager.getAppManager().startActivity(MyWalletActivity.class);
                }
                dismiss();
                break;
        }
    }

}

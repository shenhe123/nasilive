package com.feicui365.live.ui.act;

import android.Manifest;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.live.activity.LiveEditActivity;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.nasinet.utils.AppManager;
import com.gyf.immersionbar.ImmersionBar;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class RejectActivity extends BaseMvpActivity<HomePresenter> implements HomeContract.View {

    private TextView tv_title;
    private ImageView reject_iv;
    private TextView reject_msg_tv;
    private TextView reject_topic_tv;
    private Button reject_button;
    private ImageView iv_back;
    private HomePresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reject;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).init();
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        mPresenter.getUserInfo();

        tv_title = findViewById(R.id.tv_title);
        reject_iv = findViewById(R.id.reject_iv);
        reject_msg_tv = findViewById(R.id.reject_msg_tv);
        reject_topic_tv = findViewById(R.id.reject_topic_tv);
        reject_button = findViewById(R.id.reject_button);

        tv_title.setText("审核进度");

        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onError(Throwable throwable) {

    }


    @Override
    public void setUserInfo(UserRegist bean) {
        reject_button.setVisibility(View.VISIBLE);
        int reject_status = bean.getReject_status();
        reject_msg_tv.setText(bean.getReject_reason());
       // 审核状态0-待审核 1-已通过 2-已拒绝
        if (reject_status == 0) {
            reject_topic_tv.setText("审核中......");
            reject_iv.setImageDrawable(getResources().getDrawable(R.mipmap.reject_0));
            reject_msg_tv.setText(R.string.idif_under_review);
            reject_button.setText("刷新");
        } else if (reject_status == 1) {
            reject_topic_tv.setText("审核成功");
            reject_iv.setImageDrawable(getResources().getDrawable(R.mipmap.reject_1));
            reject_button.setText("去直播");
        } else if (reject_status == 2) {
            reject_topic_tv.setText("审核失败");
            reject_iv.setImageDrawable(getResources().getDrawable(R.mipmap.reject_2));
            reject_button.setText("去修改");
        }

        reject_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reject_status == 0) {
                    mPresenter.getUserInfo();
                }else if (reject_status == 1) {
                    if (!MyUserInstance.getInstance().isFastClick()) {
                        RxPermissions rxPermissions = new RxPermissions(RejectActivity.this);
                        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                                .subscribe(granted -> {
                                    if (granted) {

                                        AppManager.getAppManager().startActivity(LiveEditActivity.class);
                                        //    AppManager.getAppManager().startActivity(LivePushActivity3.class);
                                    } else {
                                        ToastUtils.showT("用户没有授权相机权限,请授权在打开直播");
                                    }
                                });
                    }
                }else if (reject_status == 2) {
                    startActivity(new Intent(RejectActivity.this,ApplyAnchorActivity.class));
                }
            }
        });
    }


}

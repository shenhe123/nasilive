package com.feicui365.live.ui.act;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.base.OthrBase2Activity;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.dialog.ShareDialog;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;

//邀请好友
public class InviteFriendActivity extends OthrBase2Activity implements View.OnClickListener {
    TextView tv_invite, tv_history, tv_invite_code;
    ImageView iv_copy;
    RelativeLayout rl_back_2;
    String url = "";

    @Override
    protected int getLayoutId() {
        return R.layout.invite_activity;
    }

    @Override
    protected void initData() {
        initView();
        HttpUtils.getInstance().getAgentInfo(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                JSONObject result = HttpUtils.getInstance().check(response);
                if (HttpUtils.getInstance().swtichStatus(result)) {
                    url = result.getJSONObject("data").getString("invite_url");

                }
            }
        });
    }

    private void initView() {
        setTitle("邀请好友");
        hideTitle(true);
        tv_invite = findViewById(R.id.tv_invite);
        tv_invite_code = findViewById(R.id.tv_invite_code);
        tv_history = findViewById(R.id.tv_history);
        iv_copy = findViewById(R.id.iv_copy);
        rl_back_2 = findViewById(R.id.rl_back_2);
        tv_history.setOnClickListener(this);
        rl_back_2.setOnClickListener(this);
        iv_copy.setOnClickListener(this);
        tv_invite.setOnClickListener(this);
        if (ArmsUtils.isStringEmpty(MyUserInstance.getInstance().getUserinfo().getInvite_code())) {
            tv_invite_code.setText(MyUserInstance.getInstance().getUserinfo().getInvite_code());
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        HttpUtils.getInstance().getAgentInfo(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                JSONObject result = HttpUtils.getInstance().check(response);
                if (HttpUtils.getInstance().swtichStatus(result)) {
                    url = result.getJSONObject("data").getString("invite_url");

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.tv_history:
                startActivity(new Intent(InviteFriendActivity.this, InviteHistoryAgentActivity.class));
                break;
            case R.id.tv_invite:
                ShareDialog.Builder builder = new ShareDialog.Builder(InviteFriendActivity.this);
                builder.setShare_url(url);
                builder.create().show();
                break;
            case R.id.iv_copy:
                if (!ArmsUtils.isStringEmpty(tv_invite_code.getText().toString())) {
                    ToastUtils.showT("您的邀请码出了点小问题");
                    return;
                }

                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型
                ClipData mClipData = ClipData.newPlainText("Label", tv_invite_code.getText().toString());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                ToastUtils.showT("复制成功");
                break;
            case R.id.rl_back_2:
                finish();
                break;
        }
    }
}

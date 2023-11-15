package com.feicui365.live.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.ui.act.ApplyAnchorActivity;
import com.feicui365.live.util.CountDownUtil;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.widget.NoSrcollViewPager;
import com.feicui365.live.util.MyUserInstance;

public class ApplyAnchor1Fragment extends Fragment {

    TextView tv_submit, tv_content, tv_getcode;
    LinearLayout rl_bind_phone;
    ImageView iv_binded_phone;
    EditText et_phone, et_password, et_code;
    private CountDownUtil countDownUtil;


    public ApplyAnchor1Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.appply_anchor_1_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        rl_bind_phone = view.findViewById(R.id.rl_bind_phone);
        et_phone = view.findViewById(R.id.et_phone);

        et_password = view.findViewById(R.id.et_password);
        et_code = view.findViewById(R.id.et_code);
        iv_binded_phone = view.findViewById(R.id.iv_binded_phone);
        tv_submit = view.findViewById(R.id.tv_submit);
        tv_content = view.findViewById(R.id.tv_content);
        et_phone.setFocusable(true);
        et_phone .setFocusableInTouchMode(true);
        tv_getcode=view.findViewById(R.id.tv_getcode);
        countDownUtil = new CountDownUtil(60000, 1000, tv_getcode);
        tv_getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_phone.getText().toString().equals("")) {
                  ToastUtils.showT("请输入11位手机号");
                    return;
                }


                HttpUtils.getInstance().sendCode(et_phone.getText().toString(), new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSON.parseObject(response.body());
                        if( HttpUtils.getInstance().swtichStatus(jsonObject)){
                            countDownUtil.start();
                        }
                    }
                });
            }
        });






        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(MyUserInstance.getInstance().getUserinfo().getAccount().equals("")){

                    if (et_phone.getText().toString().equals("")) {
                        ToastUtils.showT("请输入11位手机号");
                        return;
                    }

                    if (et_password.getText().toString().equals("")) {
                        ToastUtils.showT("请设置登录密码");
                        return;
                    }

                    if (et_code.getText().toString().equals("")) {
                        ToastUtils.showT("请输入验证码");
                        return;
                    }

                    HttpUtils.getInstance().bindPhone(et_phone.getText().toString(), et_password.getText().toString(), et_code.getText().toString(), new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            JSONObject jsonObject = JSON.parseObject(response.body());
                            if( HttpUtils.getInstance().swtichStatus(jsonObject)){
                                MyUserInstance.getInstance().getUserinfo().setAccount(et_phone.getText().toString());
                                ToastUtils.showT("成功");
                                rl_bind_phone.setVisibility(View.GONE);
                                iv_binded_phone.setVisibility(View.VISIBLE);
                                tv_content.setVisibility(View.VISIBLE);
                            }else{
                                ToastUtils.showT(jsonObject.getString("msg"));
                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                        }
                    });


                }else{
                    skip();
                }


            }
        });


        if (MyUserInstance.getInstance().getUserinfo().getAccount().equals("")) {
            rl_bind_phone.setVisibility(View.VISIBLE);
            iv_binded_phone.setVisibility(View.GONE);
            tv_content.setVisibility(View.GONE);

        } else {
            rl_bind_phone.setVisibility(View.GONE);
            iv_binded_phone.setVisibility(View.VISIBLE);
            tv_content.setVisibility(View.VISIBLE);

            Activity activity = (Activity) getActivity();
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }

    }

    private void skip(){
        final ApplyAnchorActivity mainActivity = (ApplyAnchorActivity) getActivity();
        mainActivity.setFragmentSkipInterface(new ApplyAnchorActivity.FragmentSkipInterface() {
            @Override
            public void gotoFragment(NoSrcollViewPager viewPager) {
                /** 跳转到第二个页面的逻辑 */
                viewPager.setCurrentItem(1);
            }
        });
        /** 进行跳转 */
        mainActivity.skipToFragment();
    }
}

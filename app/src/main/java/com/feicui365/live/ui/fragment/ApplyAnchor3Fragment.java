package com.feicui365.live.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.base.OtherBaseFragment;
import com.feicui365.live.dialog.SuccessDialog;
import com.feicui365.live.dialog.WheelDialog;
import com.feicui365.live.ui.act.ApplyAnchorActivity;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class ApplyAnchor3Fragment extends OtherBaseFragment implements View.OnClickListener {


    TextView tv_submit, tv_choose_start, tv_choose_sex, tv_4;
    EditText et_age, et_high, et_weight, et_work, et_city, et_my_text;
    WheelDialog wheelDialog_start, wheelDialog_sex;
    List<String> startlist = new ArrayList<>();
    List<String> sexlist = new ArrayList<>();
    ApplyAnchorActivity act;
    int type = 0;
    LinearLayout ll_anchor;

    public ApplyAnchor3Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.appply_anchor_3_fragment, container, false);
        act = (ApplyAnchorActivity) getActivity();
        this.type = act.type;
        initData();
        initView(view);
        return view;
    }

    private void initView(View view) {

        tv_submit = view.findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
        tv_choose_start = view.findViewById(R.id.tv_choose_start);
        tv_choose_start.setOnClickListener(this);
        tv_choose_sex = view.findViewById(R.id.tv_choose_sex);
        tv_choose_sex.setOnClickListener(this);
        ll_anchor = view.findViewById(R.id.ll_anchor);
        tv_4 = view.findViewById(R.id.tv_4);
        et_age = view.findViewById(R.id.et_age);
        et_high = view.findViewById(R.id.et_high);
        et_weight = view.findViewById(R.id.et_weight);
        et_work = view.findViewById(R.id.et_work);
        et_city = view.findViewById(R.id.et_city);
        et_my_text = view.findViewById(R.id.et_my_text);
        switch (type) {
            case 2:
                ll_anchor.setVisibility(View.GONE);
                tv_4.setVisibility(View.GONE);
                et_my_text.setHint("请编辑个人简介，个人简介将显示在你的个人主页");
                break;

        }

    }

    private void initData() {
        startlist.addAll(MyUserInstance.getInstance().Starts());
        sexlist.addAll(MyUserInstance.getInstance().Sex());
    }

    private void showStar() {
        wheelDialog_start = new WheelDialog(getContext());
        wheelDialog_start.setLabels(startlist);
        wheelDialog_start.setOnWheelSelectListener(new WheelDialog.OnWheelSelectListener() {
            @Override
            public void onClickOk(int index, String selectLabel) {

                ToastUtils.showT(selectLabel);
                tv_choose_start.setText(selectLabel);
                wheelDialog_start.cancel();
            }
        });
        wheelDialog_start.show();
    }

    private void showSex() {
        wheelDialog_sex = new WheelDialog(getContext());
        wheelDialog_sex.setLabels(sexlist);
        wheelDialog_sex.setOnWheelSelectListener(new WheelDialog.OnWheelSelectListener() {
            @Override
            public void onClickOk(int index, String selectLabel) {
                tv_choose_sex.setText(selectLabel);
                wheelDialog_sex.cancel();
            }
        });
        wheelDialog_sex.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_choose_start:
                showStar();
                break;
            case R.id.tv_choose_sex:
                showSex();
                break;

            case R.id.tv_submit:
                //提交
                editUserInfo();
                break;
        }
    }


    //有料认证
    private void youliaoAuth() {
        String signature = et_my_text.getText().toString();
        if (signature.equals("")) {

            ToastUtils.showT("请编辑个性签名");
            return;
        }

//        HttpUtils.getInstance().identityAuth(act.real_name, act.user_id, act.ali_account, act.ali_num, act.id_font + "," + act.id_back + "," + act.id_all, new StringCallback() {
//            @Override
//            public void onSuccess(Response<String> response) {
//                JSONObject jsonObject = JSON.parseObject(response.body());
//                if (jsonObject.getString("status").equals("0")) {
//                    updateUserInfo();
//
//                }
//            }
//        });

    }

    String sex = "";

    //编辑用户资料
    private void editUserInfo() {

        String age = et_age.getText().toString();
        String height = et_high.getText().toString();
        String weight = et_weight.getText().toString();
        String constellation = tv_choose_start.getText().toString();
        String career = et_work.getText().toString();
        String city = et_city.getText().toString();
        String signature = et_my_text.getText().toString();

        if (tv_choose_sex.getText().toString().equals("男")) {
            sex = "1";
        }
        if (tv_choose_sex.getText().toString().equals("女")) {
            sex = "0";
        }
        if (sex.equals("")) {

            ToastUtils.showT("请选择性别");
        }

        if (age.equals("")) {

            ToastUtils.showT("请输入年龄");
            return;
        }
        if (height.equals("")) {

            ToastUtils.showT("请输入身高(cm)");
            return;
        }
        if (weight.equals("")) {

            ToastUtils.showT("请输入体重(kg)");
            return;
        }
        if (constellation.equals("")) {

            ToastUtils.showT("请选择星座");
            return;
        }
        if (career.equals("")) {

            ToastUtils.showT("请输入行业");
            return;
        }
        if (city.equals("")) {

            ToastUtils.showT("请输入城市");
            return;
        }
        if (signature.equals("")) {

            ToastUtils.showT("请编辑个性签名");
            return;
        }


//        HttpUtils.getInstance().identityAuth(act.real_name, act.user_id, act.ali_account, act.ali_num, act.id_font + "," + act.id_back + "," + act.id_all, new StringCallback() {
//            @Override
//            public void onSuccess(Response<String> response) {
//                JSONObject jsonObject = JSON.parseObject(response.body());
//                if (jsonObject.getString("status").equals("0")) {
//
//                    HttpUtils.getInstance().editUserInfo2(sex, age, height, weight, constellation, career, city, signature, new StringCallback() {
//                        @Override
//                        public void onSuccess(Response<String> response) {
//                            JSONObject jsonObject = JSON.parseObject(response.body());
//                            if (jsonObject.getString("status").equals("0")) {
//                                initDialog();
//                                updateUserInfo();
//                            }
//                        }
//                    });
//
//                }
//            }


//            @Override
//            public void onError(Response<String> response) {
//                super.onError(response);
//            }
//        });
//

    }

    private void initDialog() {
        SuccessDialog dialog = new SuccessDialog(getActivity());
        dialog.show();
    }

}

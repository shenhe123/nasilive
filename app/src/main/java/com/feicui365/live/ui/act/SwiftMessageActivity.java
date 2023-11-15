package com.feicui365.live.ui.act;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.live.adapter.SwiftMessageAdapter;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.Gold;
import com.feicui365.live.model.entity.SwiftMessageBean;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.adapter.ToPayAdapter;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.ToastUtils;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SwiftMessageActivity extends BaseMvpActivity<HomePresenter> implements HomeContract.View, View.OnClickListener {

    @BindView(R.id.sm_rlv)
    RecyclerView mSmRlv;
    @BindView(R.id.sm_et1)
    EditText mSmEt;
    @BindView(R.id.sm_bt)
    Button mSmbt;
    @BindView(R.id.sm_save_message)
    Button mSmSaveMessage;
    List<SwiftMessageBean> swiftMessageBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_swift_message;
    }

    @Override
    protected void initData() {
        loadData();
    }

    @Override
    protected void initView() {
        setTitle("快捷消息");
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        mSmbt.setOnClickListener(this);
        mSmSaveMessage.setOnClickListener(this);
    }




    private void loadData() {
        HttpUtils.getInstance().getQuickMessage(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                hideLoading();
                try {
                    // JSONArray data = (JSONArray) HttpUtils.getInstance().check(response).getJSONArray("data");
                    JSONObject jsonObject = HttpUtils.getInstance().check(response);
                    if (HttpUtils.getInstance().swtichStatus(jsonObject)) {
                        JSONArray data = jsonObject.getJSONArray("data");

                        if (null != data) {
                           swiftMessageBean = JSONObject.parseArray(data.toJSONString(), SwiftMessageBean.class);//

                            SwiftMessageAdapter swiftMessageAdapter = new SwiftMessageAdapter(getApplicationContext());
                            mSmRlv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false));
                            swiftMessageAdapter.addData(swiftMessageBean);
                            mSmRlv.setAdapter(swiftMessageAdapter);

                            swiftMessageAdapter.setOnSwiftMessageClickListener(new SwiftMessageAdapter.OnSwiftMessageClickListener() {
                                @Override
                                public void onItemClick(String id) {
                                    HttpUtils.getInstance().deQuickMessage(id,new StringCallback() {
                                        @Override
                                        public void onSuccess(Response<String> response) {
                                            hideLoading();
                                            try {
                                                // JSONArray data = (JSONArray) HttpUtils.getInstance().check(response).getJSONArray("data");
                                               // JSONObject jsonObject = HttpUtils.getInstance().check(response);
                                                loadData();
                                            } catch (Exception e) {
                                                Log.e("Exception", e.toString());
                                            }
                                        }

                                        @Override
                                        public void onError(Response<String> response) {
                                            super.onError(response);
                                            hideLoading();
                                        }
                                    });

                                }
                            });


                            hideLoading();
                        }
                    }
                } catch (Exception e) {
                    Log.e("Exception", e.toString());
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                hideLoading();
            }
        });
    }


    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sm_bt:
                if(swiftMessageBean==null||(swiftMessageBean!=null&&swiftMessageBean.size()<3)) {
                    String quickMessage = mSmEt.getText().toString().trim();
                    HttpUtils.getInstance().saveQuickMessage(quickMessage, new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            hideLoading();
                            try {
                                // JSONArray data = (JSONArray) HttpUtils.getInstance().check(response).getJSONArray("data");
                                JSONObject jsonObject = HttpUtils.getInstance().check(response);
                                String msg = jsonObject.getString("msg");
                                ToastUtils.showT(msg);
                                mSmEt.setText("");
                                loadData();
                            } catch (Exception e) {
                                Log.e("Exception", e.toString());
                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            hideLoading();
                        }
                    });
                }else{
                    ToastUtils.showT(R.string.shortcut_key_num);
                }
                break;
            case R.id.sm_save_message:
                finish();
                break;
        }
    }
}

package com.feicui365.live.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.androidkun.xtablayout.XTabLayout;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.live.adapter.InComeDetailAdapter;
import com.feicui365.live.model.entity.AccountDetailBean;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.util.HttpUtils;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.List;

import butterknife.BindView;

public class DetailFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.detail_rlv)
    RecyclerView mDetailRlv;
    private InComeDetailAdapter inComeDetailAdapter;

    @Override
    protected void lazyLoad() {

    }

    private void loadData(int type) {
        HttpUtils.getInstance().userBalanceList(type,new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                hideLoading();
                try {
                    // JSONArray data = (JSONArray) HttpUtils.getInstance().check(response).getJSONArray("data");
                    JSONObject jsonObject = HttpUtils.getInstance().check(response);
                    if (HttpUtils.getInstance().swtichStatus(jsonObject)) {
                        JSONArray data = jsonObject.getJSONArray("data");

                        if (null != data) {
                            List<AccountDetailBean> accountDetailBeans = JSONObject.parseArray(data.toJSONString(), AccountDetailBean.class);//
                            inComeDetailAdapter = new InComeDetailAdapter(getActivity());
                            mDetailRlv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                            inComeDetailAdapter.addData(accountDetailBeans);
                            mDetailRlv.setAdapter(inComeDetailAdapter);

                            hideLoading();
                        }
                    }
                } catch (Exception e) {
                    Log.e("Exception", e.toString());
                }
            }
        });
    }

    @Override
    protected void initView(View view) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            int detail_type = bundle.getInt("detail_type");
            loadData(detail_type);
        }



    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {

    }
}

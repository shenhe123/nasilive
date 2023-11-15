package com.feicui365.live.ui.act;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.model.entity.Topic;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.adapter.TopicAdapter;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.widget.Dialogs;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchTalkActivity extends BaseMvpActivity<HomePresenter> implements HomeContract.View {
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.rl_null)
    RelativeLayout rl_null;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @BindView(R.id.rv_talk)
    RecyclerView rv_talk;



    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    ArrayList<Topic> talks = new ArrayList<>();
    int page = 1;
    String keyword = "";
    TopicAdapter topicAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.search_talk_activity;
    }

    @Override
    protected void initData() {
        showLoading();
        mPresenter.getTopicList(page + "", keyword);


        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mPresenter.getTopicList(page + "", keyword);
            }
        });
    }

    @Override
    protected void initView() {
        hideTitle(true);
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    if (et_search.getText().toString().equals("")) {
                        ToastUtils.showT("请输入搜索内容");
                        return false;
                    } else {
                        keyword = et_search.getText().toString();
                        page = 1;
                        mPresenter.getTopicList(page + "", keyword);
                    }
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //得到InputMethodManager的实例
                    if (imm.isActive()) {//如果开启
                        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
                    }
                }
                return false;
            }
        });

        refreshLayout.setEnableRefresh(false);

    }


    @OnClick({R.id.iv_close, R.id.tv_cancel})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                et_search.setText("");
                break;
            case R.id.tv_cancel:
                finish();
                break;

        }
    }


    @Override
    public void getTopicList(ArrayList<Topic> bean) {
        hideLoading();
        refreshLayout.finishLoadMore(true);
        if (page == 1) {
            talks.clear();
            if(topicAdapter!=null){
                topicAdapter.notifyDataSetChanged();
            }

            if (bean == null) {
                rl_null.setVisibility(View.VISIBLE);
                return;
            }
            if (bean.size() == 0) {
                rl_null.setVisibility(View.VISIBLE);
                return;
            }

        } else if (page > 1) {
            if (bean.size() == 0) {
                page--;
                refreshLayout.setEnableLoadMore(false);
                return;
            }
        }
        rl_null.setVisibility(View.GONE);
        talks.addAll(bean);
        if(topicAdapter==null){
            topicAdapter=new TopicAdapter(context,R.layout.topic_item,talks);
            topicAdapter.setHasStableIds(true);
            topicAdapter.setOnItemClickListener(new TopicAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(Topic topic) {
                    Intent intent = new Intent();
                    intent.putExtra("TOPIC", topic);
                    setResult(33, intent);
                    finish();
                }
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            rv_talk.setLayoutManager(layoutManager);
            rv_talk.setAdapter(topicAdapter);
        }else{
            topicAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onError(Throwable throwable) {
        hideLoading();
    }

    private Dialog dialog;

    @Override
    public void showLoading() {
        hideLoading();
        dialog = Dialogs.createLoadingDialog(this);
        dialog.show();
    }

    @Override
    public void hideLoading() {
        if (null != dialog) {
            dialog.dismiss();
        }
    }

}

/*
package com.feicui365.live.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lxj.xpopup.XPopup;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.model.entity.Banners;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.Guardian;
import com.feicui365.live.model.entity.GuardianInfo;
import com.feicui365.live.model.entity.Trend;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.act.SuperPlayerActivity;
import com.feicui365.live.ui.act.UserTrendsActivity;
import com.feicui365.live.ui.act.WebViewActivity;
import com.feicui365.live.ui.adapter.GuardianListAdapter;
import com.feicui365.live.ui.adapter.GuardianListItemAdapter;
import com.feicui365.live.ui.adapter.TrendsAdapter;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.widget.CommentGuardianList;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class GuardianListFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {


    @BindView(R.id.rv_guardian_list)
    RecyclerView rv_guardian_list;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_pay_guardian)
    TextView tv_pay_guardian;
    @BindView(R.id.rl_root)
    RelativeLayout rl_root;


    String anchorid;
    int page = 1;
    boolean is_ver=false;
    GuardianListAdapter guardianList_adapter;
    GuardianListItemAdapter guardianList_top_item;
    GuardianListItemAdapter guardianList_top_top;

    private GuardianInfo guardianInfo;
    ArrayList<Guardian> guardians_temp = new ArrayList<>();
    ArrayList<Guardian> guardians_top3 = new ArrayList<>();
    ArrayList<Guardian> guardians_other = new ArrayList<>();

    public GuardianListFragment(String anchorid) {
        this.anchorid = anchorid;
    }

    public GuardianListFragment() {

    }

    @Override
    protected void lazyLoad() {

    }

    public static GuardianListFragment newInstance(String param1,boolean param2) {
        GuardianListFragment fragment = new GuardianListFragment();
        Bundle args = new Bundle();
        args.putString("anchorid", param1);
        args.putBoolean("is_ver", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void getGuardInfo(BaseResponse<GuardianInfo> baseResponse) {
        guardianInfo = baseResponse.getData();
    }
    @Override
    protected void initView(View view) {
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        if (getArguments() != null) {
            anchorid = getArguments().getString("anchorid");
            is_ver=getArguments().getBoolean("is_ver");
        }

        if(is_ver){
            tv_pay_guardian.setVisibility(View.GONE);
        }else{
            tv_pay_guardian.setVisibility(View.VISIBLE);
            rl_root.setBackgroundResource(R.color.color_F4F7FF);
            getGuardianInfo();
        }
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mPresenter.getGuardianList(anchorid, page + "");
            }
        });
        initData();
        tv_pay_guardian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initGuardianlist(99);
            }
        });
    }

    public void initData() {
        page=1;
        guardianList_adapter = new GuardianListAdapter(getContext(), guardians_temp, "1");
        rv_guardian_list.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_guardian_list.setAdapter(guardianList_adapter);
        mPresenter.getGuardianList(anchorid, page + "");
    }


    @Override
    public void getGuardianList(BaseResponse<ArrayList<Guardian>> response) {
        refreshLayout.finishLoadMore();



        if (page == 1) {
            guardians_temp.clear();
            guardians_top3.clear();
            guardians_other.clear();
            if(guardianList_adapter!=null){
                guardianList_adapter.removeAllHeaderView();
            }
            if (response.getData() == null) {

                return;
            }
            if (response.getData().size() == 0) {

                return;
            }
        } else {

            if (response.getData() == null) {
                page--;
                return;
            }
            if (response.getData().size() == 0) {
                page--;
                return;
            }
        }


        //判断页码跟数据量

        if (page == 1) {
            if (response.getData().size() <=3) {
                //添加一个位置给标题
                guardians_top3.add(new Guardian());
                guardians_top3.addAll(response.getData());
                guardianList_adapter.addHeaderView(initTop());

            } else {
                //添加两个位置分别给头部和标题
                guardians_top3.add(new Guardian());
                guardians_other.add(new Guardian());
                for (int i = 0; i < response.getData().size(); i++) {
                    if (i < 3) {
                        guardians_top3.add(response.getData().get(i));
                    } else {
                        guardians_other.add(response.getData().get(i));
                    }
                }
                guardianList_adapter.addHeaderView(initTop());
                guardianList_adapter.addHeaderView(initItem());
            }

        } else {
            Log.e("page",page+"    "+response.getData().get(0).getUid());
            //如果第一次打开前三名不够3个
            if (guardians_top3.size() < 4) {

                for (int i = 0; i < response.getData().size(); i++) {
                    if (guardians_top3.size() < 4) {
                        guardians_top3.add(response.getData().get(i));
                    } else {
                        if (guardians_other.size() == 0) {
                            guardians_other.add(new Guardian());
                        }
                        guardians_other.add(response.getData().get(i));
                        if(guardianList_top_item==null){
                            guardianList_adapter.addHeaderView(initItem());
                        }
                    }
                }


            } else {
                if (guardians_other.size() == 0) {
                    guardians_other.add(new Guardian());
                }
                guardians_other.addAll(response.getData());
                if(guardianList_top_item==null){
                    guardianList_adapter.addHeaderView(initItem());
                }
            }

        }

        if (guardianList_top_top != null) {
            guardianList_top_top.notifyDataSetChanged();
        }
        if (guardianList_top_item != null) {
            guardianList_top_item.notifyDataSetChanged();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_guardian_list;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {
        String a = throwable.toString();
    }



    private View initTop() {
        View v = getLayoutInflater().inflate(R.layout.fragment_guardian_top, rv_guardian_list, false);
        RecyclerView rv_guardian_top = v.findViewById(R.id.rv_guardian_list);
        RelativeLayout rl_top=v.findViewById(R.id.rl_top);
        guardianList_top_top = new GuardianListItemAdapter(getContext(), guardians_top3, "1");

        rv_guardian_top.setLayoutManager(new LinearLayoutManager(getContext()));

        rv_guardian_top.setAdapter(guardianList_top_top);

        if(!is_ver){
            rv_guardian_top.setBackground(null);
            rl_top.setBackground(null);
        }

        return v;
    }


    private View initItem() {
        View v = getLayoutInflater().inflate(R.layout.fragment_guardian_top, rv_guardian_list, false);
        RecyclerView rv_guardian_top = v.findViewById(R.id.rv_guardian_list);
        RelativeLayout rl_top=v.findViewById(R.id.rl_top);
        guardianList_top_item = new GuardianListItemAdapter(getContext(), guardians_other, "2");
        rv_guardian_top.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_guardian_top.setAdapter(guardianList_top_item);

        if(!is_ver){
            rv_guardian_top.setBackground(null);
            rl_top.setBackground(null);
        }
        return v;
    }

    public void initGuardianlist(int postion) {
        CommentGuardianList commentGuardianList = new CommentGuardianList(getContext(), anchorid, guardianInfo, is_ver);
        new XPopup.Builder(getContext())

                .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                .asCustom(commentGuardianList*/
/*.enableDrag(false)*//*
)
                .show();
        commentGuardianList.setBuyFinish(new CommentGuardianList.BuyFinish() {
            @Override
            public void BuySuccess(GuardianInfo guardianInfo) {

                initData();
            }
        });
    }

    private void getGuardianInfo() {
        if (MyUserInstance.getInstance().hasToken()) {
            mPresenter.getGuardInfo(anchorid);
        }
    }
}
*/

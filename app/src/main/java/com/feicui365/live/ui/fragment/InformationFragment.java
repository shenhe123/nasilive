package com.feicui365.live.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.model.entity.PersonalAnchorInfo;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.act.IntimacyListActivity;
import com.feicui365.live.ui.adapter.PersonalGiftAdapter;
import com.feicui365.live.ui.adapter.PersonalRankAdapter;
import com.feicui365.live.util.FormatCurrentData;
import com.feicui365.live.widget.HorizontalListView;

import butterknife.BindView;

public class InformationFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View{

    @BindView(R.id.tv_last_time)
    TextView tv_last_time;
    @BindView(R.id.tv_industry)
    TextView tv_industry;
    @BindView(R.id.tv_height)
    TextView tv_height;
    @BindView(R.id.tv_weight)
    TextView tv_weight;
    @BindView(R.id.tv_city)
    TextView tv_city;
    @BindView(R.id.tv_constellation)
    TextView tv_constellation;
    @BindView(R.id.rank_ll)
    LinearLayout rank_ll;
    @BindView(R.id.rank_list)
    HorizontalListView rank_list;
    @BindView(R.id.gift_list)
    HorizontalListView gift_list;

    private static InformationFragment fragment;

    public static InformationFragment newInstance(String authorid) {
        Bundle args = new Bundle();
        fragment = new InformationFragment();
        args.putString("authorid", authorid);
        fragment.setArguments(args);
        return fragment;
    }

    public static InformationFragment getInstance() {
        return fragment;
    }


    public void setPersonalAnchorInfo(PersonalAnchorInfo personalAnchorInfo){
        //最后登录时间
        if (!TextUtils.isEmpty(personalAnchorInfo.getLast_online())) {
            tv_last_time.setText(FormatCurrentData.getTimeRange(personalAnchorInfo.getLast_online()));
        }
        //行业
        if (!TextUtils.isEmpty(personalAnchorInfo.getProfile().getCareer())) {
            tv_industry.setText(personalAnchorInfo.getProfile().getCareer());
        }
        //身高
        if (!TextUtils.isEmpty(personalAnchorInfo.getProfile().getHeight())){
            tv_height.setText(personalAnchorInfo.getProfile().getHeight()+"CM");
        }
        //体重
        if (!TextUtils.isEmpty(personalAnchorInfo.getProfile().getWeight())){
            tv_weight.setText(personalAnchorInfo.getProfile().getWeight()+"KG");
        }
        //城市
        if (!TextUtils.isEmpty(personalAnchorInfo.getProfile().getCity())){
            tv_city.setText(personalAnchorInfo.getProfile().getCity());
        }
        //星座
        if (!TextUtils.isEmpty(personalAnchorInfo.getProfile().getConstellation())){
            tv_constellation.setText(personalAnchorInfo.getProfile().getConstellation());
        }

        rank_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), IntimacyListActivity.class);
                i.putExtra("authorid",personalAnchorInfo.getId());
                startActivity(i);
            }
        });
        if(personalAnchorInfo.getIntimacys()!=null){
            PersonalRankAdapter personalRankAdapter = new PersonalRankAdapter(getContext(),personalAnchorInfo.getIntimacys());
            rank_list.setAdapter(personalRankAdapter);
        }

        if(personalAnchorInfo.getGift_show()!=null){
            PersonalGiftAdapter personalGiftAdapter = new PersonalGiftAdapter(getContext(),personalAnchorInfo.getGift_show());
            gift_list.setAdapter(personalGiftAdapter);
        }

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_information;
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

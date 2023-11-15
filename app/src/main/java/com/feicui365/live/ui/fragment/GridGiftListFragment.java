/*
package com.feicui365.live.ui.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.contract.SuperPlayerContrat;
import com.feicui365.live.presenter.SuperPlayerPresenter;
import com.feicui365.live.ui.adapter.GiftGridViewAdapter;
import com.tencent.liteav.demo.play.bean.GiftData;

import java.util.ArrayList;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class GridGiftListFragment extends BaseMvpFragment<SuperPlayerPresenter> implements SuperPlayerContrat.View {

    @BindView(R.id.gift_grid)
    GridView gift_grid;


    private ArrayList<GiftData> giftList = new ArrayList<>();
    private GiftGridViewAdapter giftGridViewAdapter;
    private ChatFragment.HideGiftList hideGiftLis;


    public GridGiftListFragment(ChatFragment.HideGiftList hideGiftList) {
        this.hideGiftLis  = hideGiftList;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initView(View view) {
        giftGridViewAdapter = new GiftGridViewAdapter(getActivity());
        giftGridViewAdapter.setData(giftList);
        gift_grid.setAdapter(giftGridViewAdapter);
        gift_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (GiftData data : giftList){
                    data.setTAG(0);
                }
                giftList.get(position).setTAG(1);
                giftGridViewAdapter.notifyDataSetChanged();
                hideGiftLis.selectedGift(giftList.get(position));
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_grid_gift_list;
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



    @Override
    public void setGiftList(ArrayList<GiftData> giftList) {
        if (null != giftList) {
            this.giftList.addAll(giftList);
        }
    }

    public void unSelectedGift(){
        for (GiftData data : giftList){
            data.setTAG(0);
        }
        giftGridViewAdapter.notifyDataSetChanged();
    }



}

*/

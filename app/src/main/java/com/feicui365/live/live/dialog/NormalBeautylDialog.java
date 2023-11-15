package com.feicui365.live.live.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;


import com.tencent.live2.V2TXLivePusher;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseDialogFragment;
import com.feicui365.live.base.MyApp;
import com.feicui365.live.live.adapter.FilterAdapter;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.live.utils.NormalBeautyUtils;
import com.feicui365.live.model.entity.Filters;
import com.feicui365.live.widget.HorizontalListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class NormalBeautylDialog extends BaseDialogFragment {


    @BindView(R.id.filter_rl)
    RelativeLayout filter_rl;
    @BindView(R.id.mopi_seek_bar)
    SeekBar mopi_seek_bar;
    @BindView(R.id.meibai_seek_bar)
    SeekBar meibai_seek_bar;
    @BindView(R.id.hongrun_seek_bar)
    SeekBar hongrun_seek_bar;
    @BindView(R.id.filter_seek_bar)
    SeekBar filter_seek_bar;
    @BindView(R.id.mopi_tv)
    TextView mopi_tv;
    @BindView(R.id.meibai_tv)
    TextView meibai_tv;
    @BindView(R.id.hongrun_tv)
    TextView hongrun_tv;
    @BindView(R.id.filter_list)
    HorizontalListView filter_list;
    @BindView(R.id.meiyan_tv)
    TextView meiyan_tv;
    @BindView(R.id.meiyan_view)
    View meiyan_view;
    @BindView(R.id.filter_tv)
    TextView filter_tv;
    @BindView(R.id.filter_view)
    View filter_view;
    @BindView(R.id.filter_ll)
    LinearLayout filter_ll;
    @BindView(R.id.meiyan_ll)
    LinearLayout meiyan_ll;

    private int index = 0;
    private FilterAdapter filterAdapter;
    private ArrayList<Filters> filters = new ArrayList<>();


    V2TXLivePusher v2TXLivePusher;

    public NormalBeautylDialog() {
    }

    public void setLivePusher(V2TXLivePusher v2TXLivePusher) {
        this.v2TXLivePusher = v2TXLivePusher;
        //美颜
        v2TXLivePusher.getBeautyManager().setBeautyStyle(0);
        v2TXLivePusher.getBeautyManager().setBeautyLevel(MyApp.mopiLevel);
        //美白
        v2TXLivePusher.getBeautyManager().setWhitenessLevel(MyApp.meibaiLevel);
        //红润
        v2TXLivePusher.getBeautyManager().setRuddyLevel(MyApp.hongrunLevel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.normal_beauty_dialog;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.transparentBackgroundDiaolg;
    }

    @Override
    protected boolean canCancel() {

        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;

        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBeautySeekBar();
    }

    private void initBeautySeekBar() {
        mopi_seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mopi_tv.setText(String.valueOf(progress));
                MyApp.mopiLevel = progress;

                //美颜
                v2TXLivePusher.getBeautyManager().setBeautyStyle(0);
                v2TXLivePusher.getBeautyManager().setBeautyLevel(MyApp.mopiLevel);
                //美白
                v2TXLivePusher.getBeautyManager().setWhitenessLevel(MyApp.meibaiLevel);
                //红润
                v2TXLivePusher.getBeautyManager().setRuddyLevel(MyApp.hongrunLevel);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        meibai_seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                meibai_tv.setText(String.valueOf(progress));
                MyApp.meibaiLevel = progress;
                //美颜
                v2TXLivePusher.getBeautyManager().setBeautyStyle(0);
                v2TXLivePusher.getBeautyManager().setBeautyLevel(MyApp.mopiLevel);
                //美白
                v2TXLivePusher.getBeautyManager().setWhitenessLevel(MyApp.meibaiLevel);
                //红润
                v2TXLivePusher.getBeautyManager().setRuddyLevel(MyApp.hongrunLevel);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        hongrun_seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                hongrun_tv.setText(String.valueOf(progress));
                MyApp.hongrunLevel = progress;
                //美颜
                v2TXLivePusher.getBeautyManager().setBeautyStyle(0);
                v2TXLivePusher.getBeautyManager().setBeautyLevel(MyApp.mopiLevel);
                //美白
                v2TXLivePusher.getBeautyManager().setWhitenessLevel(MyApp.meibaiLevel);
                //红润
                v2TXLivePusher.getBeautyManager().setRuddyLevel(MyApp.hongrunLevel);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        filter_seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float specialRatio = progress / 10f;
                //  mLivePusher.setSpecialRatio(specialRatio);
                v2TXLivePusher.getBeautyManager().setFilterStrength(specialRatio);
                filters.get(index).setSpecialRatio(progress);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        initHorizontalList();
    }


    private void initHorizontalList() {
        //添加滤镜
        filters.addAll(NormalBeautyUtils.initFilterList());
        filterAdapter = new FilterAdapter(getContext());
        filter_list.setAdapter(filterAdapter);
        filterAdapter.setData(filters);
        filter_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (Filters filter : filters) {
                    filter.setTag(2);
                }
                filters.get(position).setTag(1);
                index = position;

                v2TXLivePusher.getBeautyManager().setFilter(filters.get(index).getFilterBitmap());
                v2TXLivePusher.getBeautyManager().setFilterStrength(filters.get(index).getSpecialRatio() / 10f);


                filter_seek_bar.setProgress((int) filters.get(index).getSpecialRatio());
                filterAdapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick({R.id.meiyan_option, R.id.filter_option})
    public void onClick(View view) {
        if (!ArmsUtils.canClick()) {
            return;
        }
        switch (view.getId()) {

            case R.id.meiyan_option:
                meiyan_tv.setTextColor(getResources().getColor(R.color.color_F06E1E));
                meiyan_view.setVisibility(View.VISIBLE);
                meiyan_ll.setVisibility(View.VISIBLE);

                filter_tv.setTextColor(getResources().getColor(R.color.color_FFFFFF));
                filter_view.setVisibility(View.INVISIBLE);
                filter_ll.setVisibility(View.GONE);
                break;
            case R.id.filter_option:
                meiyan_tv.setTextColor(getResources().getColor(R.color.color_FFFFFF));
                meiyan_view.setVisibility(View.INVISIBLE);
                meiyan_ll.setVisibility(View.GONE);

                filter_tv.setTextColor(getResources().getColor(R.color.color_F06E1E));
                filter_view.setVisibility(View.VISIBLE);
                filter_ll.setVisibility(View.VISIBLE);
                break;

        }
    }
}

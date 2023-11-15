package com.feicui365.live.ui.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import android.support.v4.app.Fragment;


import com.example.zhouwei.library.CustomPopWindow;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.sv.TCPicturePickerActivity;
import com.feicui365.live.sv.TCVideoFollowRecordActivity;
import com.feicui365.live.sv.TCVideoPickerActivity;
import com.feicui365.live.sv.TCVideoRecordActivity;
import com.feicui365.live.sv.TCVideoTripleScreenActivity;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.model.entity.LiveCategory;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.adapter.TabFragmentAdapter;
import com.feicui365.live.util.WordUtil;

import com.feicui365.live.widget.ScaleTransitionPagerTitleView;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.qcloud.ugckit.UGCKitConstants;
import com.tencent.qcloud.ugckit.utils.BackgroundTasks;
import com.tencent.qcloud.ugckit.utils.DownloadUtil;
import com.tencent.qcloud.ugckit.utils.ToastUtil;


import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeShortVideoFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {


    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @BindView(R.id.iv_publish)
    ImageView iv_publish;

    @BindView(R.id.magic_indicator)
    MagicIndicator magic_indicator;

    int now_page = 0;
    public boolean action = false;
    private ArrayList<LiveCategory> mTitles = new ArrayList<>();
    public List<Fragment> mFragments = new ArrayList<>();
    private TabFragmentAdapter mTabFragmentAdapter;
    private ProgressDialog mDownloadProgressDialog;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initView(View view) {

        mPresenter = new HomePresenter();
        mPresenter.attachView(this);


        LiveCategory liveCategory = new LiveCategory();
        liveCategory.setTitle(WordUtil.getString(R.string.For_U));
        mTitles.add(liveCategory);
        LiveCategory liveCategory3 = new LiveCategory();
        liveCategory3.setTitle(WordUtil.getString(R.string.Hot));
        mTitles.add(liveCategory3);
        mFragments.add(new ShortVideoFragment(this, 0));
        mFragments.add(new ShortVideoFragment(this, 1));


        mTabFragmentAdapter = new TabFragmentAdapter(mFragments, mTitles, getChildFragmentManager(), getActivity());
        mViewPager.setOffscreenPageLimit(mFragments.size());// 设置预加载Fragment个数
        mViewPager.setAdapter(mTabFragmentAdapter);
        mViewPager.setCurrentItem(0);// 设置当前显示标签页为第一页
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("onPageScrolled", position + "");
            }

            @Override
            public void onPageSelected(int position) {
                now_page = position;
                ShortVideoFragment shortVideoFragment = (ShortVideoFragment) mFragments.get(position);
                shortVideoFragment.checkPlay();
                for (int i = 0; i < mFragments.size(); i++) {
                    if (i != position) {
                        mFragments.get(i).onPause();
                    }
                }

            }

            @SuppressLint("LongLogTag")
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        initTab(view);


        iv_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyUserInstance.getInstance().visitorIsLogin()) {
                    View contentView = LayoutInflater.from(getContext()).inflate(R.layout.custom_short_popup, null);
                    CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(getContext())
                            .setView(contentView)//显示的布局
                            .create()//创建PopupWindow
                            .showAsDropDown(iv_publish, 0, 0, Gravity.LEFT);//显示PopupWindow
                    InitPopWindow(contentView, popWindow);
                }
            }
        });
        mDownloadProgressDialog = new ProgressDialog(getActivity());
        mDownloadProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // 设置进度条的形式为圆形转动的进度条
        mDownloadProgressDialog.setCancelable(false);                           // 设置是否可以通过点击Back键取消
        mDownloadProgressDialog.setCanceledOnTouchOutside(false);               // 设置在点击Dialog外是否取消Dialog进度条

    }


    private void InitPopWindow(View v, CustomPopWindow popWindow) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popWindow != null) {
                    popWindow.dissmiss();
                }

                switch (v.getId()) {
                    case R.id.ll_1:
                        startActivity(new Intent(getContext(), TCVideoRecordActivity.class));
                        break;
                    case R.id.ll_2:

                        prepareToDownload(false);


                        break;
                    case R.id.ll_3:
                        prepareToDownload(true);

                        break;
                    case R.id.ll_4:
                        Intent intent = new Intent(getContext(), TCVideoPickerActivity.class);
                        getContext().startActivity(intent);
                        break;
                    case R.id.ll_5:
                        Intent intent2 = new Intent(getContext(), TCPicturePickerActivity.class);
                        getContext().startActivity(intent2);
                        break;
                }

            }
        };
        v.findViewById(R.id.ll_1).setOnClickListener(listener);
        v.findViewById(R.id.ll_2).setOnClickListener(listener);
        v.findViewById(R.id.ll_3).setOnClickListener(listener);
        v.findViewById(R.id.ll_4).setOnClickListener(listener);
        v.findViewById(R.id.ll_5).setOnClickListener(listener);
    }
    private void prepareToDownload(final boolean isTriple) {
        if (getActivity() == null) {
            return;
        }
        File sdcardDir = getActivity().getExternalFilesDir(null);
        if (sdcardDir == null) {
            TXCLog.e("TAG", "prepareToDownload sdcardDir is null");
            return;
        }
        File downloadFileFolder = new File(sdcardDir, UGCKitConstants.OUTPUT_DIR_NAME);
        File downloadFile = new File(downloadFileFolder, DownloadUtil.getNameFromUrl(UGCKitConstants.CHORUS_URL));
        if (downloadFile.exists()) {
            mDownloadProgressDialog.dismiss();
            if (isTriple) {
                startTripleActivity(downloadFile.getAbsolutePath());
            } else {
                startRecordActivity(downloadFile.getAbsolutePath());
            }
            return;
        }
        if (mDownloadProgressDialog != null) {
            mDownloadProgressDialog.show();
        }
        DownloadUtil.get(getActivity()).download(UGCKitConstants.CHORUS_URL, UGCKitConstants.OUTPUT_DIR_NAME, new DownloadUtil.DownloadListener() {
            @Override
            public void onDownloadSuccess(final String path) {
                BackgroundTasks.getInstance().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDownloadProgressDialog.dismiss();
                        if (isTriple) {
                            startTripleActivity(path);
                        } else {
                            startRecordActivity(path);
                        }
                    }
                });
            }

            @Override
            public void onDownloading(final int progress) {
                BackgroundTasks.getInstance().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDownloadProgressDialog.setMessage("正在下载..." + progress + "%");
                    }
                });
            }

            @Override
            public void onDownloadFailed() {
                BackgroundTasks.getInstance().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDownloadProgressDialog.dismiss();

                        ToastUtil.toastShortMessage("下载失败");
                    }
                });
            }
        });
    }
    private void initTab(View view) {
        magic_indicator = view.findViewById(R.id.magic_indicator);
        CommonNavigator commonNavigator = new CommonNavigator(getContext());

        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitles == null ? 0 : mTitles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView colorTransitionPagerTitleView = new ScaleTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#ffffff"));
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));
                colorTransitionPagerTitleView.setTextSize(20);
                colorTransitionPagerTitleView.setText(mTitles.get(index).getTitle());
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPager.setCurrentItem(index);

                    }
                });

                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineWidth(UIUtil.dip2px(context, 10));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setColors(Color.parseColor("#ffffff"));
                return indicator;
            }

        });
        magic_indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magic_indicator, mViewPager);
        mViewPager.setCurrentItem(0);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_short_video;
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
    public void onResume() {
        super.onResume();
        if (mFragments.size() != 0) {
            ShortVideoFragment shortVideoFragment = (ShortVideoFragment) mFragments.get(now_page);
            shortVideoFragment.action = action;
            shortVideoFragment.onResume2();
        }
    }

    public void paseAll() {
        for (int i = 0; i < mFragments.size(); i++) {
            ShortVideoFragment shortVideoFragment = (ShortVideoFragment) mFragments.get(i);
            shortVideoFragment.onPause();
        }
    }

    private void startRecordActivity(String path) {
        Intent intent = new Intent(getActivity(), TCVideoFollowRecordActivity.class);
        intent.putExtra(UGCKitConstants.VIDEO_PATH, path);
        startActivity(intent);
    }

    private void startTripleActivity(String path) {
        Intent intent = new Intent(getActivity(), TCVideoTripleScreenActivity.class);//TCVideoPreviewActivity
        intent.putExtra(UGCKitConstants.VIDEO_PATH, path);
        startActivity(intent);

    }

}

package com.feicui365.live.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.ui.act.UserTrendsActivity;
import com.feicui365.live.model.entity.Trend;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.adapter.LiveTrendsAdapter;
import com.feicui365.live.ui.adapter.TrendsAdapter;
import com.feicui365.live.dialog.UnlockDialog;
import com.feicui365.live.util.LevelUtils;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.widget.MyRecyclerView;
import com.feicui365.live.widget.ViewPagerForScrollView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserTrendsFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View, BGANinePhotoLayout.Delegate {


    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.rv_trends)
    MyRecyclerView rv_trends;
    @BindView(R.id.rl_nothing)
    FrameLayout rl_nothing;

    private int type;
    private int page = 1;
    List<Trend> moments = new ArrayList<>();
    private int mPosition;
    private VideoView videoView;

    private String status = "";
    private String keyword = "";
    private String roomid = "";
    private String topic = "";
    private String topic_type = "";
    private UserRegist authorInfo;
    private String authorId;

    private TrendsAdapter mMomentAdapter;
    private LiveTrendsAdapter liveTrendsAdapter;
    private int _firstItemPosition = -1, _lastItemPosition;
    private View fistView, lastView;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initView(View view) {
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        if (getArguments() != null) {
            type = getArguments().getInt("type");

            if (getArguments().getString("keyword") != null) {
                keyword = getArguments().getString("keyword");
            }
            if (getArguments().getString("status") != null) {
                status = getArguments().getString("status");
            }
            if (getArguments().getSerializable("authorInfo") != null) {
                authorInfo = (UserRegist) getArguments().getSerializable("authorInfo");
            }
            if (getArguments().getString("authorId") != null) {
                authorId = getArguments().getString("authorId");
            }
            if (getArguments().getString("roomid") != null) {
                roomid = getArguments().getString("roomid");
            }
            if (getArguments().getString("topic") != null) {
                topic = getArguments().getString("topic");
            }
            if (getArguments().getString("topic_type") != null) {
                topic_type = getArguments().getString("topic_type");
            }
        }


        initRecycle();
        if (null != viewPagerForScrollView) {
            viewPagerForScrollView.setObjectForPosition(view, 1);
        }


    }

    ViewPagerForScrollView viewPagerForScrollView;

    @SuppressLint("ValidFragment")
    public UserTrendsFragment(ViewPagerForScrollView viewPagerForScrollView) {
        this.viewPagerForScrollView = viewPagerForScrollView;
    }

    public UserTrendsFragment() {
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trends;
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void unlockMoment() {

        moments.get(mPosition).setUnlocked("1");
        if (type == 15) {
            moments.get(mPosition).setUnlocked("1");
            liveTrendsAdapter.notifyItemChanged(mPosition);
        } else {

            mMomentAdapter.notifyItemChanged(mPosition);
        }


    }

    @Override
    public void showMgs(String msg) {
        if (msg.contains("参数")) {
            refreshLayout.finishLoadMore(true);
            refreshLayout.finishRefresh(true);
            return;
        }
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(Throwable throwable) {
        if (page == 1) {
            moments.clear();
            if (type == 15) {
                liveTrendsAdapter.notifyDataSetChanged();
            } else {
                mMomentAdapter.notifyDataSetChanged();
            }
            refreshLayout.finishRefresh(true);
        } else {
            page--;
            refreshLayout.finishLoadMore(true);
        }

    }


    private void initRecycle() {

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                moments.clear();
                if (type == 15) {
                    liveTrendsAdapter.notifyDataSetChanged();
                } else {
                    mMomentAdapter.notifyDataSetChanged();
                }
                refreshLayout.setEnableLoadMore(true);
                getData();
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getData();
            }
        });
        addNetImageTestData();
        if(type!=13){
            getData();
        }



    }

    public void reDate() {
        page = 1;
        getData();
    }

    private void getData() {


        switch (type) {

            case 0:
                mPresenter.getMomentAttent(page);
                break;
            case 1:

                break;
            case 2:
                mPresenter.getMomentHot(page);
                break;
            case 13:
                rl_nothing.setVisibility(View.VISIBLE);
                mPresenter.searchMoment(page, keyword);
                break;
            case 14:

                mPresenter.getMyTrendList(status, page);
                break;
            case 15:
                if (authorInfo == null) {
                    return;
                }
                mPresenter.getListByUser(authorInfo.getId(), page);
                break;
            case 16:
                if (authorId == null) {
                    return;
                }
                refreshLayout.setEnableRefresh(false);
                refreshLayout.setEnableLoadMore(false);
                mPresenter.getListByUser(authorId, page);
                break;
            case 17:
                mPresenter.getCollection(page + "");
                break;
            case 18:
                mPresenter.momentListInTopic(topic, topic_type,page+"");
                break;
        }
    }

    public void search(String word) {
        keyword = word;
        moments.clear();
        mMomentAdapter.notifyDataSetChanged();
        refreshLayout.setEnableLoadMore(true);
        page = 1;
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1004) {
            Trend my_data = (Trend) data.getSerializableExtra("data");
            if (my_data == null) {
                return;
            }
            for (int i = 0; i < moments.size(); i++) {
                if (moments.get(i).getId().equals(my_data.getId())) {
                    moments.set(i, my_data);
                    break;

                }
            }
            if (type == 15) {
                liveTrendsAdapter.notifyDataSetChanged();
            } else {
                mMomentAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void setMoment(ArrayList<Trend> bean) {
        refreshLayout.finishLoadMore(true);
        refreshLayout.finishRefresh(true);
        if (page == 1) {
            moments.clear();
            refreshLayout.finishRefresh(true);
            if (bean == null) {
                rl_nothing.setVisibility(View.VISIBLE);
                return;
            }
            if (bean.size() == 0) {
                rl_nothing.setVisibility(View.VISIBLE);
                return;
            }

        } else if (page > 1) {
            if (bean.size() == 0) {
                page--;
                refreshLayout.setEnableLoadMore(false);
                return;
            }
        }


        rl_nothing.setVisibility(View.GONE);
        for (Trend data : bean) {
            if ((TextUtils.isEmpty(data.getUnlocked()) || data.getUnlocked().equals("0")) &&
                    !data.getUnlock_price().equals("0")) {
                if (!data.getType().equals("3")) {
                    if (!TextUtils.isEmpty(data.getBlur_image_url())) {
                        data.setPhotos(new ArrayList<>());
                        String[] images = data.getBlur_image_url().split(",");
                        for (String image : images) {
                            data.getPhotos().add(image);
                        }
                    }
                } else {
                    if (!TextUtils.isEmpty(data.getImage_url())) {
                        data.setPhotos(new ArrayList<>());
                        String[] images = data.getImage_url().split(",");
                        for (String image : images) {
                            data.getPhotos().add(image);
                        }
                    }
                }
            } else {
                if (!TextUtils.isEmpty(data.getImage_url())) {
                    data.setPhotos(new ArrayList<>());
                    String[] images = data.getImage_url().split(",");
                    for (String image : images) {
                        data.getPhotos().add(image);
                    }
                }
            }
        }
        moments.addAll(bean);
        if (type == 15) {
            liveTrendsAdapter.notifyDataSetChanged();
        } else {
            mMomentAdapter.notifyDataSetChanged();
        }
        if (type != 16) {
            if (page == 1) {
                refreshLayout.finishRefresh(true);
            } else {
                refreshLayout.finishLoadMore(true);
            }

        }
    }


    private void addNetImageTestData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_trends.setLayoutManager(layoutManager);

        //解决数据加载不完的问题
        if (type == 15) {
            liveTrendsAdapter = new LiveTrendsAdapter(getContext(), moments, this);
            liveTrendsAdapter.setHasStableIds(true);
            liveTrendsAdapter.addHeaderView(initAnchor(authorInfo));
            liveTrendsAdapter.setOnItemClick(new LiveTrendsAdapter.OnItemClick() {
                @Override
                public void onItemclickListener(int position, Trend trend) {
                    if(!isFastClick()) {
                        startActivityForResult(new Intent(getContext(), UserTrendsActivity.class).putExtra("momentData", trend), 1004);
                    }
                }
            });
            liveTrendsAdapter.showPriceDialogClick(new LiveTrendsAdapter.ShowPriceDialogClick() {
                @Override
                public void showPriceDialog(int position) {
                    mPosition = position;
                    showUnlockDialog(moments.get(position));
                }
            });
            rv_trends.setAdapter(liveTrendsAdapter);
          //  liveTrendsAdapter.notifyDataSetChanged();
        } else {
            mMomentAdapter = new TrendsAdapter(getContext(), moments, this);
            mMomentAdapter.setHasStableIds(true);

            mMomentAdapter.setOnItemClick(new TrendsAdapter.OnItemClick() {
                @Override
                public void onItemclickListener(int position, Trend trend) {
                    if(!isFastClick()) {
                        startActivityForResult(new Intent(getContext(), UserTrendsActivity.class).putExtra("momentData", trend), 1004);
                    }
                }
            });
            mMomentAdapter.showPriceDialogClick(new TrendsAdapter.ShowPriceDialogClick() {
                @Override
                public void showPriceDialog(int position) {
                    mPosition = position;
                    showUnlockDialog(moments.get(position));
                }
            });

            rv_trends.setAdapter(mMomentAdapter);
        }


        rv_trends.setItemAnimator(null);
        rv_trends.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    //获取可见view的总数
                    int visibleItemCount = linearManager.getChildCount();

                    if (_firstItemPosition < firstItemPosition) {
                        _firstItemPosition = firstItemPosition;
                        _lastItemPosition = lastItemPosition;
                        GCView(fistView);
                        fistView = recyclerView.getChildAt(0);
                        lastView = recyclerView.getChildAt(visibleItemCount - 1);

                    } else if (_lastItemPosition > lastItemPosition) {
                        _firstItemPosition = firstItemPosition;
                        _lastItemPosition = lastItemPosition;
                        GCView(lastView);
                        fistView = recyclerView.getChildAt(0);
                        lastView = recyclerView.getChildAt(visibleItemCount - 1);

                    }
                }
            }


        });


    }

    private View initAnchor(UserRegist anchorInfo) {

        View v = getLayoutInflater().inflate(R.layout.live_trend_top, rv_trends, false);
        CircleImageView civ_avatar = v.findViewById(R.id.civ_avatar);
        TextView tv_name = v.findViewById(R.id.tv_name);
        ImageView iv_anchor_level = v.findViewById(R.id.iv_anchor_level);
        TextView tv_sex_age = v.findViewById(R.id.tv_sex_age);
        TextView tv_sign = v.findViewById(R.id.tv_sign);
        ImageView iv_vip = v.findViewById(R.id.iv_vip);
        TextView tv_room_id = v.findViewById(R.id.tv_room_id);

        tv_room_id.setText("房间号："+anchorInfo.getId());
        Glide.with(getContext()).applyDefaultRequestOptions(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.zhanwei)).load(anchorInfo.getAvatar()).into(civ_avatar);
        tv_name.setText(anchorInfo.getNick_name());
        Glide.with(getContext()).load(LevelUtils.getUserLevel(anchorInfo.getAnchor_level())).into(iv_anchor_level);


        tv_sex_age.setText(anchorInfo.getProfile().getAge());
        if (anchorInfo.getProfile().getGender()==1) {
            tv_sex_age.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.mipmap.boy_transparent), null, null, null);
            tv_sex_age.setBackgroundResource(R.drawable.shape_corner_age_boy);
        }
        tv_sign.setText(anchorInfo.getProfile().getSignature());


            if (anchorInfo.getVip_level()!=0) {

                if (MyUserInstance.getInstance().OverTime(anchorInfo.getVip_date())) {
                    Glide.with(getContext()).load(ArmsUtils.getVipLevelIcon(anchorInfo.getVip_level(),anchorInfo.getVip_date())).into(iv_vip);
                    iv_vip.setVisibility(View.VISIBLE);
                } else {
                    iv_vip.setVisibility(View.GONE);
                }
            } else {
                iv_vip.setVisibility(View.GONE);
            }



        return v;
    }

    /**
     * 回收播放器
     */
    public void GCView(View gcView) {
        if (gcView != null && gcView.findViewById(R.id.player) != null) {
            videoView = (VideoView) gcView
                    .findViewById(R.id.player);
            if (videoView != null) {
                VideoViewManager.instance().addVideoView(videoView);

                List<VideoView> list = VideoViewManager.instance().getVideoViews();

                if (null != list) {
                    if (list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).isPlaying()) {
                                list.get(i).release();
                            }
                        }
                    }
                }
            }


        }
    }


    @Override
    public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {

    }


    @Override
    public void onPause() {
        super.onPause();
        VideoViewManager.instance().pause();
    }


    private void showUnlockDialog(Trend trend) {
        UnlockDialog unlockDialog = new UnlockDialog(getActivity(), trend);
        unlockDialog.show();
        unlockDialog.setUnLockListener(new UnlockDialog.UnLockListener() {
            @Override
            public void unLockTrend(String id) {
                mPresenter.unlockMoment(id);
            }
        });
    }
}

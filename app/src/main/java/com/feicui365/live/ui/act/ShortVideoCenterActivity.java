package com.feicui365.live.ui.act;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.im.TxImUtils;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.CheckAttend;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.ui.fragment.ShortVideoListFragment;
import com.feicui365.live.ui.fragment.UserTrendsFragment;
import com.feicui365.live.util.LevelUtils;
import com.feicui365.live.util.WordUtil;
import com.feicui365.live.widget.Dialogs;
import com.feicui365.live.model.entity.UserInfo;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.adapter.PersonalCenterAdapter;

import com.feicui365.live.util.MyUserInstance;

import butterknife.BindView;

public class ShortVideoCenterActivity extends BaseMvpActivity<HomePresenter> implements HomeContract.View {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    @BindView(R.id.sliding_tabs)
    TabLayout sliding_tabs;
    @BindView(R.id.iv_attention)
    ImageView iv_attention;
    @BindView(R.id.avatar_iv)
    ImageView avatar_iv;
    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.iv_user_level)
    ImageView iv_user_level;
    @BindView(R.id.age_tv)
    TextView age_tv;
    @BindView(R.id.user_id_tv)
    TextView user_id_tv;
    @BindView(R.id.signature_tv)
    TextView signature_tv;
    @BindView(R.id.tv_zan)
    TextView tv_zan;
    @BindView(R.id.guanzhu_tv)
    TextView guanzhu_tv;
    @BindView(R.id.fans_tv)
    TextView fans_tv;
    @BindView(R.id.rl_attention)
    View rl_attention;

    @BindView(R.id.iv_close2)
    ImageView iv_close2;
    @BindView(R.id.rl_chat)
    RelativeLayout rl_chat;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rl_back2)
    RelativeLayout rl_back2;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    private UserRegist author;
    private Dialog dialog;
    private String type;
    PersonalCenterAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_center;
    }

    @Override
    protected void initData() {
        rl_title.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getShortUserInfo(author.getId());
        mPresenter.checkAttent(author.getId());
    }

    @Override
    public void checkAttent(CheckAttend response) {


        if (response.getAttented() == 0) {
            iv_attention.setImageResource(R.mipmap.button_guanzhu_tittle);
            author.setIsattent(0);
            type = "0";
        } else {
            iv_attention.setImageResource(R.mipmap.personal_center_yiguanzhu);
            author.setIsattent(1);
            type = "1";
        }
    }

    private void init(UserInfo bean) {
        Glide.with(this).load(author.getAvatar()).into(avatar_iv);
        if (author.getIsattent()==1) {
            type = "0";
            iv_attention.setImageResource(R.mipmap.personal_center_yiguanzhu);
            rl_attention.setBackgroundResource(R.mipmap.button_center_yiguanzhu);
        } else {
            type = "1";
            iv_attention.setImageResource(R.mipmap.button_guanzhu_tittle);
            rl_attention.setBackgroundResource(R.mipmap.button_center_guanzhu);
        }
        rl_attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyUserInstance.getInstance().visitorIsLogin()) {
                    attendAnchor(author.getId());
                }
            }
        });
        user_name.setText(author.getNick_name());
        iv_user_level.setImageResource(LevelUtils.getUserLevel(author.getUser_level()));
        age_tv.setText(author.getProfile().getAge());
        if (author.getProfile().getGender()==1) {
            age_tv.setCompoundDrawablesWithIntrinsicBounds(ShortVideoCenterActivity.this.getResources().getDrawable(R.mipmap.boy_transparent), null, null, null);
            age_tv.setBackgroundResource(R.drawable.shape_corner_age_boy);
        }


        user_id_tv.setText("ID:" + author.getId());
        signature_tv.setText(author.getProfile().getSignature());


        setupViewPager(viewpager, bean);
        viewpager.setOffscreenPageLimit(3);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                VideoViewManager.instance().release();
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        rl_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyUserInstance.getInstance().visitorIsLogin()) {
                   TxImUtils.getSocketManager().startChat(author.getId(),author.getNick_name(),author.getAvatar());
                }
            }
        });

        iv_close2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("author_id", author.getId());
                intent.putExtra("type", type);
                setResult(0x001, intent);
                finish();
            }
        });


        toolbar_title.setText(author.getNick_name());
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();

                }
                if (scrollRange + verticalOffset == 0) {
                    toolbar_title.setVisibility(View.VISIBLE);
                    toolbar.setBackgroundColor(Color.WHITE);
                    rl_back2.setVisibility(View.VISIBLE);
                    toolbar_title.setVisibility(View.VISIBLE);
                    isShow = true;
                } else if (isShow) {
                    if (verticalOffset > -500) {
                        rl_back2.setVisibility(View.GONE);
                        toolbar_title.setVisibility(View.GONE);
                        toolbar.setBackgroundColor(Color.TRANSPARENT);
                        isShow = false;
                    }
                }
            }
        });

        rl_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("author_id", author.getId());
                intent.putExtra("type", type);
                setResult(0x001, intent);
                finish();
            }
        });

    }

    @Override
    protected void initView() {
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        Intent intent = getIntent();
        author = (UserRegist) intent.getSerializableExtra("authorInfo");
    }

    ShortVideoListFragment anchorWorksFragment;
    UserTrendsFragment userTrendsFragment;
    ShortVideoListFragment likeFragment;

    private void reDate() {
        anchorWorksFragment.reDate();
        userTrendsFragment.reDate();
        likeFragment.reDate();
    }

    private void setupViewPager(ViewPager mViewPager, UserInfo bean) {
        if (adapter == null) {


            adapter = new PersonalCenterAdapter(getSupportFragmentManager());
            anchorWorksFragment = new ShortVideoListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", 1);
            bundle.putString("authorId", author.getId());
            anchorWorksFragment.setArguments(bundle);


            userTrendsFragment = new UserTrendsFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putInt("type", 16);
            bundle1.putString("authorId", author.getId());
            userTrendsFragment.setArguments(bundle1);


            likeFragment = new ShortVideoListFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putInt("type", 5);
            bundle2.putString("authorId", author.getId());
            likeFragment.setArguments(bundle2);

            adapter.addFragment(anchorWorksFragment, WordUtil.getString(R.string.Videos) + " " + bean.getVideo_count());
            adapter.addFragment(userTrendsFragment, WordUtil.getString(R.string.Moment) + " " + bean.getMoment_count());
            adapter.addFragment(likeFragment, WordUtil.getString(R.string.Liked) + " " + bean.getLike_video_count());
            mViewPager.setAdapter(adapter);
        }

    }


    private void attendAnchor(String anchorid) {

        mPresenter.attentAnchor(anchorid, type);
      //  reDate();
    }

    @Override
    public void attentAnchor(BaseResponse response) {
        if (type.equals("0")) {

            iv_attention.setImageResource(R.mipmap.button_guanzhu_tittle);
            rl_attention.setBackgroundResource(R.mipmap.button_center_guanzhu);
            type = "1";
        } else {

            iv_attention.setImageResource(R.mipmap.personal_center_yiguanzhu);
            rl_attention.setBackgroundResource(R.mipmap.button_center_yiguanzhu);
            type = "0";
        }
    }

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

    @Override
    public void setShortUserInfo(UserInfo bean) {
        if (bean.getStar_count() > 10000) {
            tv_zan.setText(bean.getStar_count() / 10000 + "w");
        } else {
            tv_zan.setText(bean.getStar_count() + "");
        }
        if (bean.getStar_count() > 10000) {
            guanzhu_tv.setText(bean.getAttent_count() / 10000 + "w");
        } else {
            guanzhu_tv.setText(bean.getStar_count() + "");
        }
        if (bean.getStar_count() > 10000) {
            fans_tv.setText(bean.getFans_count() / 10000 + "w");
        } else {
            fans_tv.setText(bean.getFans_count() + "");
        }

        init(bean);
        if (sliding_tabs.getTabCount() == 0) {
            sliding_tabs.addTab(sliding_tabs.newTab().setText(WordUtil.getString(R.string.Videos) + " " + bean.getVideo_count()));
            sliding_tabs.addTab(sliding_tabs.newTab().setText(WordUtil.getString(R.string.Moment) + " " + bean.getMoment_count()));
            sliding_tabs.addTab(sliding_tabs.newTab().setText(WordUtil.getString(R.string.Liked) + " " + bean.getLike_video_count()));
            sliding_tabs.setTabTextColors(Color.parseColor("#777777"), Color.parseColor("#000000"));
            sliding_tabs.setupWithViewPager(viewpager);
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra("author_id", author.getId());
            if (type.equals("0")) {
                intent.putExtra("type", "1");
            } else {
                intent.putExtra("type", "0");
            }

            setResult(0x001, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

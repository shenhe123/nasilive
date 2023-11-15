package com.feicui365.live.ui.act;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.dialog.ReplayTrendDialog;
import com.feicui365.live.interfaces.OnFaceClickListener;
import com.feicui365.live.model.entity.MomentDetail;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.adapter.ImChatFacePagerAdapter;
import com.feicui365.live.ui.adapter.UserReplayTrendAdapter;
import com.feicui365.live.util.FormatCurrentData;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.LevelUtils;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.TextRender;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import de.hdodenhof.circleimageview.CircleImageView;

public class ReplayTrendActivity extends BaseMvpActivity<HomePresenter> implements View.OnClickListener, HomeContract.View, BGANinePhotoLayout.Delegate, UserReplayTrendAdapter.OnItemClickListener {
    @BindView(R.id.cv_trends)
    RecyclerView cv_trends;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_comment)
    TextView tv_comment;
    @BindView(R.id.iv_emoji)
    ImageView iv_emoji;
    MomentDetail into_recomment;


    @BindView(R.id.civ_avatar)
    CircleImageView civ_avatar;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_content)
    TextView tv_content;

    @BindView(R.id.tv_zan_num)
    TextView tv_zan_num;
    @BindView(R.id.tv_remessage_num)
    TextView tv_remessage_num;
    @BindView(R.id.age_tv)
    TextView age_tv;
    @BindView(R.id.ll_zan)
    RelativeLayout ll_zan;
    @BindView(R.id.iv_zan)
    ImageView iv_zan;
    @BindView(R.id.iv_user_level)
    ImageView iv_user_level;


    UserReplayTrendAdapter userTrendAdapter;
    //传进来的列表
    ArrayList<MomentDetail> into_comments = new ArrayList<>();
    String lastid = "";
    public boolean is_show_input = false;

    public ReplayTrendDialog inputDialogFragment4;
    public int mFaceViewHeight;//表情控件高度

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_replay;
    }

    @Override
    protected void initData() {
        mPresenter.getMomentCommentReplys(MyUserInstance.getInstance().getUserinfo().getId(), MyUserInstance.getInstance().getUserinfo().getToken(), into_recomment.getId(), lastid, "20");

    }

    @Override
    protected void initView() {

        setTitle("消息回复");
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        into_recomment = (MomentDetail) getIntent().getSerializableExtra("MomentDetail");

        if (into_recomment == null) {
            finish();
        }

        initIncoming(into_recomment);
        refreshLayout.setEnableRefresh(false);
        iv_emoji.setOnClickListener(this);
        tv_comment.setOnClickListener(this);

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getMomentCommentReplys(MyUserInstance.getInstance().getUserinfo().getId(), MyUserInstance.getInstance().getUserinfo().getToken(), into_recomment.getId(), lastid, "20");
            }
        });

        View decorView = getWindow().getDecorView();
        View contentView = findViewById(Window.ID_ANDROID_CONTENT);
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(getGlobalLayoutListener(decorView, contentView));
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("data", into_recomment);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private ViewTreeObserver.OnGlobalLayoutListener getGlobalLayoutListener(final View decorView, final View contentView) {
        return new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                decorView.getWindowVisibleDisplayFrame(r);
                int height = decorView.getContext().getResources().getDisplayMetrics().heightPixels;
                int diff = height - r.bottom;
                if (diff > 0) {
                    if (!is_show_input) {
                        if (inputDialogFragment4 != null) {
                            is_show_input = true;
                            inputDialogFragment4.openInput();
                        }
                    }
                } else {
                    //这个时候是收进去的
                    //只有确定软键盘是打开的时候
                    if (is_show_input) {
                        is_show_input = false;
                    }
                }
            }
        };
    }

    @Override
    public void setMomentDetail(ArrayList<MomentDetail> bean) {

        //获取评论
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        into_comments.addAll(bean);
        if (lastid.equals("")) {
            userTrendAdapter = new UserReplayTrendAdapter(into_recomment, into_comments, this, this);
            userTrendAdapter.setOnItemClickListener(ReplayTrendActivity.this);
            cv_trends.setLayoutManager(new LinearLayoutManager(this));
            cv_trends.setAdapter(userTrendAdapter);
            lastid = into_comments.get(into_comments.size() - 1).getId();
        } else {
            into_comments.addAll(bean);
            userTrendAdapter.notifyDataSetChanged();
            lastid = into_comments.get(into_comments.size() - 1).getId();
        }


    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {

    }

    @Override
    public void onClick(MomentDetail comment) {
        showInput(true, comment);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.iv_emoji:
                showInput(true, null);
                break;

            case R.id.tv_comment:
                showInput(false, null);

                break;

        }


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("data", into_recomment);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    private void initIncoming(MomentDetail comment) {
        Glide.with(context).applyDefaultRequestOptions(new RequestOptions().placeholder(R.mipmap.moren)).load(comment.getUser().getAvatar()).into(civ_avatar);

        tv_name.setText(comment.getUser().getNick_name());
        tv_time.setText(FormatCurrentData.getTimeRange2(comment.getCreate_time()));
        age_tv.setText(comment.getUser().getAge());
        if (comment.getUser().getProfile().getGender()==1) {
            age_tv.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.boy_transparent), null, null, null);

            age_tv.setBackgroundResource(R.drawable.shape_corner_age_boy);
        } else {
            age_tv.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.gir_transparentl), null, null, null);

            age_tv.setBackgroundResource(R.drawable.gender_bg);
        }
        Glide.with(context).load(LevelUtils.getUserLevel(comment.getUser().getUser_level())).into(iv_user_level);

        if (null != comment.getTouser()) {
            if (!comment.getTocommentid().equals(comment.getRootid())) {
                tv_content.setText(TextRender.renderChatMessage2(TextRender.repOther(comment.getTouser().getNick_name(), comment.getContent())));
            } else {
                tv_content.setText(TextRender.renderChatMessage(comment.getContent()));
            }
        } else {
            tv_content.setText(TextRender.renderChatMessage(comment.getContent()));
        }

        if (Integer.parseInt(comment.getLike_count()) > 0) {
            tv_zan_num.setText(comment.getLike_count());
        }
        tv_remessage_num.setVisibility(View.GONE);


        if (null == comment.getLiked()) {
            ll_zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    HttpUtils.getInstance().likeMomentComment(comment.getId(), new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {

                            JSONObject data = HttpUtils.getInstance().check(response);
                            if (data.get("status").toString().equals("0")) {

                                iv_zan.setImageDrawable(getResources().getDrawable(R.mipmap.short_zan2));
                                tv_zan_num.setText(data.getJSONObject("data").getString("like_count"));
                                into_recomment.setLiked("1");
                                into_recomment.setLike_count(data.getJSONObject("data").getString("like_count"));
                            }
                        }
                    });
                }
            });
        } else {

            iv_zan.setImageDrawable(getResources().getDrawable(R.mipmap.short_zan2));
        }
    }

    private void showInput(boolean is_face, MomentDetail re_momentDetail) {
        if (null != inputDialogFragment4) {
            inputDialogFragment4.dismiss();
        }
        ReplayTrendDialog dialogFragment = new ReplayTrendDialog(into_recomment.getMomentid());
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.VIDEO_FACE_OPEN, is_face);

        if (null != re_momentDetail) {
            bundle.putSerializable("re_momentDetail", re_momentDetail);
        }
        if (null != into_recomment) {
            bundle.putSerializable("chose_momentDetail", into_recomment);
        }
        dialogFragment.setArguments(bundle);
        dialogFragment.setArguments(bundle);
        inputDialogFragment4 = dialogFragment;
        inputDialogFragment4.setChose_momentDetail(into_recomment);
        inputDialogFragment4.show(getSupportFragmentManager(), "myAlert");
        inputDialogFragment4.setOnComentSendListener(new ReplayTrendDialog.OnComentSendListener() {
            @Override
            public void onSendSucess(MomentDetail comment) {

                into_comments.add(0, comment);
                into_recomment.setReply_count(((Integer.parseInt(into_recomment.getReply_count()) + 1) + ""));
                userTrendAdapter.notifyDataSetChanged();
                inputDialogFragment4.dismiss();
            }
        });
    }

    /**
     * 初始化表情控件
     */
    public View initFaceView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.view_chat_face, null);
        v.measure(0, 0);
        mFaceViewHeight = v.getMeasuredHeight();
        v.findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputDialogFragment4 != null) {
                    inputDialogFragment4.sendMessage();
                }
            }
        });
        final RadioGroup radioGroup = v.findViewById(R.id.radio_group);
        ViewPager viewPager = v.findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(10);
        ImChatFacePagerAdapter adapter = new ImChatFacePagerAdapter(this, new OnFaceClickListener() {
            @Override
            public void onFaceClick(String str, int faceImageRes) {
                if (inputDialogFragment4 != null) {
                    inputDialogFragment4.onFaceClick(str, faceImageRes);
                }
            }

            @Override
            public void onFaceDeleteClick() {
                if (inputDialogFragment4 != null) {
                    inputDialogFragment4.onFaceDeleteClick();
                }

            }
        });
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) radioGroup.getChildAt(position)).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0, pageCount = adapter.getCount(); i < pageCount; i++) {
            RadioButton radioButton = (RadioButton) inflater.inflate(R.layout.view_chat_indicator, radioGroup, false);
            radioButton.setId(i + 10000);
            if (i == 0) {
                radioButton.setChecked(true);
            }
            radioGroup.addView(radioButton);
        }
        return v;
    }

}

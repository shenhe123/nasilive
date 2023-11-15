package com.feicui365.live.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.VerticalRecyclerView;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.base.Constants;
import com.feicui365.live.dialog.RePlayCommentDIalog;
import com.feicui365.live.interfaces.OnFaceClickListener;
import com.feicui365.live.util.FormatCurrentData;
import com.feicui365.live.model.entity.Comment;
import com.feicui365.live.ui.adapter.CommentAdapter;
import com.feicui365.live.ui.adapter.ImChatFacePagerAdapter;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.LevelUtils;
import com.feicui365.live.util.TextRender;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class CommentPopup extends BottomPopupView implements View.OnClickListener, CommentAdapter.OnItemClickListener, RePlayCommentDIalog.OnComentSendListener {
    VerticalRecyclerView recyclerView;

    private CommentAdapter commonAdapter;
    //传进来的列表
    ArrayList<Comment> into_comments;

    ArrayList<Comment> comments;
    //二级列表
    ArrayList<Comment> replys_comments;
    SmartRefreshLayout refreshLayout;
    String replys_lastId = "";
    TextView tv_comment, tv_1;
    LinearLayout ll_top_comment;
    RelativeLayout ll_zan;
    TextView tv_name, tv_content, tv_time, tv_remessage_num, tv_zan_num, tv_comment_count;
    CircleImageView civ_avatar;
    ImageView iv_zan;
    RelativeLayout rl_back, rl_close;
    //表情相关
    ImageView iv_emoji, iv_user_level;
    TextView age_tv;
    Comment chose_comment;
    public int mFaceViewHeight;//表情控件高度
    AppCompatActivity homeActivity = (AppCompatActivity) getContext();
    public RePlayCommentDIalog inputDialogFragment;

    String comment_count = "0";
    int level = 1;
    String video_id = "";
    UpDateNum upDateNum;
    public boolean is_show_input = false;

    //表情结束
    public CommentPopup(@NonNull Context context, ArrayList<Comment> comments, String comment_count, String video_id) {
        super(context);
        this.comments = comments;
        this.video_id = video_id;
        this.comment_count = comment_count;
    }

    public interface UpDateNum {
        void onUpDateNum(String num);
    }

    public void setUpDateNum(UpDateNum upDateNum) {
        this.upDateNum = upDateNum;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_bottom_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        replys_comments = new ArrayList<>();
        into_comments = new ArrayList<>();
        into_comments.addAll(comments);
        initview();

    }

    private void initview() {
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(false);
        iv_user_level = findViewById(R.id.iv_user_level);
        age_tv = findViewById(R.id.age_tv);
        recyclerView = findViewById(R.id.recyclerView);
        iv_emoji = findViewById(R.id.iv_emoji);
        iv_emoji.setOnClickListener(this);
        tv_comment = findViewById(R.id.tv_comment);
        tv_comment.setOnClickListener(this);
        ll_top_comment = findViewById(R.id.ll_top_comment);
        ll_top_comment.setVisibility(GONE);
        recyclerView.setHasFixedSize(true);
        commonAdapter = new CommentAdapter(comments, getContext());
        commonAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(commonAdapter);
        tv_1 = findViewById(R.id.tv_1);
        tv_name = findViewById(R.id.tv_name);
        tv_content = findViewById(R.id.tv_content);
        tv_time = findViewById(R.id.tv_time);
        tv_remessage_num = findViewById(R.id.tv_remessage_num);
        tv_zan_num = findViewById(R.id.tv_zan_num);
        civ_avatar = findViewById(R.id.civ_avatar);
        tv_name = findViewById(R.id.tv_name);
        rl_back = findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        rl_close = findViewById(R.id.rl_close);
        rl_close.setOnClickListener(this);
        tv_comment_count = findViewById(R.id.tv_comment_count);
        tv_comment_count.setText("全部" + comment_count + "条评论");
        ll_zan = findViewById(R.id.ll_zan);
        iv_zan = findViewById(R.id.iv_zan);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (into_comments.size() == 0) {
                    refreshLayout.finishLoadMore();
                    return;
                }


                if (level == 1) {
                    HttpUtils.getInstance().getCommentShort(video_id, into_comments.get(into_comments.size() - 1).getId(), new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            refreshLayout.finishLoadMore();
                            JSONObject data = (JSONObject) HttpUtils.getInstance().check(response);
                            if (null != data) {
                                ArrayList<Comment> temp = (ArrayList<Comment>) JSON.parseArray(data.getJSONArray("data").toJSONString(), Comment.class);
                                if (temp.size() != 0) {
                                    into_comments.addAll(temp);
                                    comments.clear();
                                    comments.addAll(into_comments);
                                    commonAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
                }

                if (level == 2) {
                    int temp = 0;
                    if (replys_comments.size() == 0) {
                        temp = 0;
                    } else if (replys_comments.size() > 0) {
                        temp = replys_comments.size() - 1;
                    }
                    HttpUtils.getInstance().getCommentReplys(chose_comment.getId(), replys_comments.get(temp).getId(), new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            hideLoading();
                            level = 2;
                            refreshLayout.finishLoadMore();
                            JSONObject data = HttpUtils.getInstance().check(response);
                            if (data.get("status").toString().equals("0")) {
                                if (null != data.getJSONArray("data")) {

                                    JSONArray datas = data.getJSONArray("data");
                                    ArrayList<Comment> replys_comments_temp = (ArrayList<Comment>) JSON.parseArray(datas.toJSONString(), Comment.class);
                                    replys_comments.addAll(replys_comments_temp);
                                    comments.addAll(replys_comments_temp);
                                    commonAdapter.notifyDataSetChanged();
                                    tv_comment_count.setText("全部" + chose_comment.getReply_count() + "条评论");

                                }
                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            hideLoading();

                        }
                    });
                }

            }
        });

        View decorView = homeActivity.getWindow().getDecorView();
        View contentView = findViewById(Window.ID_ANDROID_CONTENT);
        onGlobalLayoutListener = getGlobalLayoutListener(decorView, contentView);
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;

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
                        if (inputDialogFragment != null) {
                            is_show_input = true;
                            inputDialogFragment.openInput();
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

    //完全可见执行
    @Override
    protected void onShow() {
        super.onShow();
    }

    //完全消失执行
    @Override
    protected void onDismiss() {
        if (homeActivity != null) {
            View decorView = homeActivity.getWindow().getDecorView();
            if (decorView != null) {
                if (onGlobalLayoutListener != null) {
                    decorView.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
                }

            }
        }

    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getWindowHeight(getContext()) * .85f);
    }

    private Dialog dialog;

    public void showLoading() {
        hideLoading();
        dialog = Dialogs.createLoadingDialog(getContext());
        dialog.show();
    }


    public void hideLoading() {
        if (null != dialog) {
            dialog.dismiss();
        }
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
            case R.id.rl_back:
                level = 1;
                comments.clear();
                comments.addAll(into_comments);
                commonAdapter.setNow_chose("");
                commonAdapter.setLevel(level);
                commonAdapter.notifyDataSetChanged();
                ll_top_comment.setVisibility(GONE);
                tv_1.setVisibility(GONE);
                rl_back.setVisibility(GONE);
                tv_comment_count.setText("全部" + comment_count + "条评论");
                chose_comment = null;

                break;
            case R.id.rl_close:
                level = 1;
                chose_comment = null;
                commonAdapter.setNow_chose("");
                commonAdapter.setLevel(level);
                dismiss();

                break;
        }


    }

    private void showInput(boolean is_face, Comment comment) {
        if (null != inputDialogFragment) {
            inputDialogFragment.dismiss();
        }
        RePlayCommentDIalog dialogFragment = new RePlayCommentDIalog(this, video_id);

        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.VIDEO_FACE_OPEN, is_face);
        if (null != comment) {
            bundle.putSerializable(Constants.VIDEO_COMMENT, comment);
        }
        if (null != chose_comment) {
            bundle.putSerializable(Constants.VIDEO_CHOSE_COMMENT, chose_comment);
        }
        dialogFragment.setArguments(bundle);
        inputDialogFragment = dialogFragment;
        inputDialogFragment.setOnComentSendListener(this);
        inputDialogFragment.show(homeActivity.getSupportFragmentManager(), "myAlert");
    }

    /**
     * 初始化表情控件
     */
    public View initFaceView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.view_chat_face, null);
        v.measure(0, 0);
        mFaceViewHeight = v.getMeasuredHeight();
        v.findViewById(R.id.btn_send).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputDialogFragment != null) {
                    inputDialogFragment.sendMessage();
                }
            }
        });
        final RadioGroup radioGroup = v.findViewById(R.id.radio_group);
        ViewPager viewPager = v.findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(10);
        ImChatFacePagerAdapter adapter = new ImChatFacePagerAdapter(getContext(), new OnFaceClickListener() {
            @Override
            public void onFaceClick(String str, int faceImageRes) {
                if (inputDialogFragment != null) {
                    inputDialogFragment.onFaceClick(str, faceImageRes);
                }
            }

            @Override
            public void onFaceDeleteClick() {
                if (inputDialogFragment != null) {
                    inputDialogFragment.onFaceDeleteClick();
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


    @Override
    public void onClick(Comment comment) {


        switch (level) {
            case 1:
                showLoading();
                HttpUtils.getInstance().getCommentReplys(comment.getId(), replys_lastId, new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        hideLoading();
                        level = 2;
                        JSONObject data = HttpUtils.getInstance().check(response);
                        if (data.get("status").toString().equals("0")) {
                            if (null != data.getJSONArray("data")) {

                                JSONArray datas = data.getJSONArray("data");
                                replys_comments = (ArrayList<Comment>) JSON.parseArray(datas.toJSONString(), Comment.class);
                                comments.clear();
                                comments.addAll(replys_comments);
                                commonAdapter.setNow_chose(comment.getUser().getId() + "");
                                commonAdapter.setLevel(level);
                                commonAdapter.notifyDataSetChanged();
                                tv_comment_count.setText("全部" + comment.getReply_count() + "条评论");

                                recyclerView.scrollToPosition(0);
                                ll_top_comment.setVisibility(VISIBLE);
                                tv_1.setVisibility(VISIBLE);
                                rl_back.setVisibility(VISIBLE);
                                chose_comment = comment;
                                Glide.with(getContext()).load(comment.getUser().getAvatar()).into(civ_avatar);
                                tv_name.setText(comment.getUser().getNick_name());
                                tv_time.setText(FormatCurrentData.getTimeRange2(comment.getCreate_time()));

                                Glide.with(getContext()).load(LevelUtils.getUserLevel(comment.getUser().getUser_level())).into(iv_user_level);
                                age_tv.setText(comment.getUser().getAge());

                                if (comment.getUser().getProfile().getGender()==1) {

                                    age_tv.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.mipmap.boy_transparent), null, null, null);

                                    age_tv.setBackgroundResource(R.drawable.shape_corner_age_boy);
                                } else {
                                    age_tv.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.mipmap.gir_transparentl), null, null, null);
                                    age_tv.setBackgroundResource(R.drawable.gender_bg);
                                }
                                tv_content.setText(TextRender.renderChatMessage(comment.getContent()));

                                if (Integer.parseInt(comment.getLike_count()) > 0) {
                                    tv_zan_num.setText(comment.getLike_count());
                                }

                                if (null == comment.getLiked() || comment.getLiked().equals("null") || comment.getLiked().equals("0")) {
                                    ll_zan.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            HttpUtils.getInstance().likeComment(comment.getId(), new StringCallback() {
                                                @Override
                                                public void onSuccess(Response<String> response) {

                                                    JSONObject data = HttpUtils.getInstance().check(response);
                                                    if (data.get("status").toString().equals("0")) {
                                                        for (int i = 0; i < into_comments.size(); i++) {
                                                            if (into_comments.get(i).getId().equals(comment.getId())) {
                                                                into_comments.get(i).setLiked("1");
                                                                into_comments.get(i).setLike_count(data.getJSONObject("data").getString("like_count"));
                                                                tv_zan_num.setText(data.getJSONObject("data").getString("like_count"));
                                                                Glide.with(getContext()).load(R.mipmap.short_zan2).into(iv_zan);
                                                                break;
                                                            }

                                                        }
                                                    }
                                                }
                                            });
                                        }
                                    });
                                } else {
                                    Glide.with(getContext()).load(R.mipmap.short_zan2).into(iv_zan);
                                }


                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        hideLoading();

                    }
                });
                break;
            case 2:
                showInput(false, comment);

                break;
        }

    }


    @Override
    public void onSendSucess(Comment comment) {
        if (null == comments) {
            return;
        }
        if (null == commonAdapter) {
            return;
        }
        comments.add(0, comment);
        for (int i = 0; i < into_comments.size(); i++) {
            if (into_comments.get(i).getId().equals(comment.getRootid())) {
                into_comments.get(i).setReply_count((Integer.parseInt(into_comments.get(i).getReply_count()) + 1) + "");
            }
        }
        String temp = tv_comment_count.getText().toString();

        temp = temp.replace("全部", "");
        temp = temp.replace("条评论", "");
        tv_comment_count.setText("全部" + (Integer.parseInt(temp) + 1) + "条评论");
        inputDialogFragment.dismiss();
        commonAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(0);
        comment_count = (Integer.parseInt(comment_count) + 1) + "";
        if (upDateNum != null) {
            upDateNum.onUpDateNum(comment_count);
        }
    }


}